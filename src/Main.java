import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
public class Main {
    static ArrayList<String> sudokuListe = new ArrayList<>();
    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(System.in);
        ArrayList<String> aktuelleListe = readFile("resources/sudokus.csv");
        String entscheidung;
        int zufallsZahl;

        while (true) {
            zufallsZahl = (int) ((Math.random() * aktuelleListe.size()));
            spielfeldAusgabe(spielfelderNummeriertBefüllen("resources/sudokus.csv","aufgabe")[zufallsZahl]);
            aktuelleListe.remove(zufallsZahl);
            System.out.println("\nSudoku Nummer: " + (zufallsZahl + 1));
            System.out.print("\nMöchten Sie die Lösung sehen?(Irgendetwas mit Enter bestätigen): ");
            sc.next();
            System.out.println();
            spielfeldAusgabe(spielfelderNummeriertBefüllen("resources/sudokus.csv","lösung")[zufallsZahl]);
            System.out.println();
            System.out.println("\nFehler: " + lösungÜberprüfung(spielfelderNummeriertBefüllen("resources/sudokus.csv","lösung")[zufallsZahl]));
            System.out.println("\nMöchten Sie sich an ein weiteres Sudoku ran wagen?(ja/nein): ");
            entscheidung = sc.next();
            if (entscheidung.equals("nein")) {
                System.out.println("Damit wäre das Spiel beendet.");
                break;
            }
            if (aktuelleListe.isEmpty()) {
                System.out.println("Sie haben sich alle Sudokus aus der Liste angeschaut, damit wäre das Spiel beendet.");
                break;
            }
        }
    }

    public static ArrayList<String> readFile(String path) throws FileNotFoundException {
        ArrayList<String> lines = new ArrayList<>();
        Scanner sc = new Scanner(new File(path));

        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }

        sc.close();

        return lines;
    }
    public static void spielfeldAusgabe(int[][] spielFeld) {
        int g = 4;
        int t = 1;
        System.out.println("----------------------------------");
        System.out.println(" || a  b  c | d  e  f | g  h  i");
        System.out.println("----------------------------------");

        for (int n = 0; n < 9; n++) {
            if (t > 3) {
                System.out.println("----------------------------------");
                t = 1;
                System.out.print((n + 1) + "|");
            } else {
                System.out.print((n + 1) + "|");
            }
            for (int i = 0; i < 9; i++) {

                if (g > 3) {
                    System.out.print("|");
                    g = 1;
                }
                if (spielFeld[n][i] == 0) {
                    System.out.print(" _ ");
                    g++;
                    continue;
                }
                System.out.print(" " + spielFeld[n][i] + " ");
                g++;

            }
            System.out.println();
            t++;
        }

    }
    public static int[][][] spielfelderNummeriertBefüllen(String path, String aufgabeOderLösung) throws FileNotFoundException {

        sudokuListe = readFile(path);
        int x;
        int y = 0;
        String[][] sudoku = new String[81][sudokuListe.size()];
        String[] sudokuAufgeteilt = new String[sudokuListe.size()];
        String[][] alleSpielfelder = new String[sudokuListe.size()][81];
        int[][] alleSpielfelderInt = new int[sudokuListe.size()][81];
        int[][][] alleSpielfelderNummeriert = new int[sudokuListe.size()][9][9];

        if(aufgabeOderLösung.equals("aufgabe")||aufgabeOderLösung.equals("Aufgabe")||aufgabeOderLösung.equals("AUFGABE"))
            x=0;
        else
            x=1;

        for (int i = 0; i < sudokuListe.size(); i++) {
            sudoku[i] = sudokuListe.get(i).split(",");
            sudokuAufgeteilt[i] = sudoku[i][x];
            alleSpielfelder[i] = sudokuAufgeteilt[i].split("");

            for (int n = 0; n < 81; n++)
                alleSpielfelderInt[i][n] = Integer.parseInt(alleSpielfelder[i][n]);
        }

        for (int z = 0; z < sudokuListe.size(); z++) {
            for (int i = 0; i < 9; i++) {
                for (int n = 0; n < 9; n++) {
                    alleSpielfelderNummeriert[z][i][n] = alleSpielfelderInt[z][n + y];
                }
                y += 9;
            }
            y = 0;
        }

        return alleSpielfelderNummeriert;
    }
    public static int lösungÜberprüfung(int[][] spielFeld) {

        int fehler = 0;

        //.1

        for (int y = 0; y < 9; y++) {
            if (spielFeld.length != 9 && spielFeld[y].length != 9) {
                fehler++;
                System.out.println("Größenfehler,");
            }
        }

        //.2

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (spielFeld[y][x] < 1 || spielFeld[y][x] > 9 || (spielFeld[y][x] + '0') < '1' || (spielFeld[y][x] + '0') > '9') {
                    fehler++;
                    spielFeld[y][x] = 0;
                    System.out.println("Zeichen-/Zahlenfehler,");
                }
            }
        }

        //.3

        boolean[][] verwendeteZahlen = new boolean[10][10];

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (verwendeteZahlen[y][spielFeld[y][x]]) {
                    fehler++;
                    System.out.println("Zeilenfehler,");
                } else
                    verwendeteZahlen[y][spielFeld[y][x]] = true;
            }
        }

        //.4

        for (boolean[] y : verwendeteZahlen)
            Arrays.fill(y, false);


        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (verwendeteZahlen[y][spielFeld[x][y]]) {
                    fehler++;
                    System.out.println("Spaltenfehler,");
                } else
                    verwendeteZahlen[y][spielFeld[x][y]] = true;
            }
        }

        //.5

        for (boolean[] y : verwendeteZahlen)
            Arrays.fill(y, false);

        int i = 0;

        for (int z = 0; z < 9; z += 3) {
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3; x++) {
                    if (verwendeteZahlen[i][spielFeld[y + z][x]]) {
                        fehler++;
                        System.out.println("Boxfehler,");
                    } else
                        verwendeteZahlen[i][spielFeld[y + z][x]] = true;
                }
            }
            i++;
            for (int y = 0; y < 3; y++) {
                for (int x = 3; x < 6; x++) {
                    if (verwendeteZahlen[i][spielFeld[y + z][x]]) {
                        fehler++;
                        System.out.println("Boxfehler,");
                    } else
                        verwendeteZahlen[i][spielFeld[y + z][x]] = true;
                }
            }
            i++;
            for (int y = 0; y < 3; y++) {
                for (int x = 6; x < 9; x++) {
                    if (verwendeteZahlen[i][spielFeld[y + z][x]]) {
                        fehler++;
                        System.out.println("Boxfehler,");
                    } else
                        verwendeteZahlen[i][spielFeld[y + z][x]] = true;
                }
            }
            i++;
        }
        return fehler;
    }
}