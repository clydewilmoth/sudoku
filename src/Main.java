import java.util.*;
import java .io.*;
public class Main {
    static ArrayList<String> sudokuListe = new ArrayList<>();
    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<String> aktuelleListe = readFile("resources/sudokus.csv");
        Scanner sc = new Scanner(System.in);
        String sehen = "";
        String entscheidung = "ja";
        int f=0;

        while(true){

            f = (int) ((Math.random()*aktuelleListe.size()));
            spielfeldAusgabe(spielfelderAufgabeBefüllen("resources/sudokus.csv"), f);
            aktuelleListe.remove(f);
            System.out.println("\nSudoku Nummer "+ (f+1));
            System.out.print("\nMöchten Sie die Lösung sehen?(Irgendetwas mit Enter bestätigen um fortzufahren): ");
            sehen = sc.next();
            System.out.println();
            spielfeldAusgabe(spielfelderLösungBefüllen("resources/sudokus.csv"), f);
            System.out.println("\nMöchten Sie sich an ein weiteres Sudoku ran wagen?(ja/nein): ");
            entscheidung = sc.next();
            if(entscheidung.equals("nein")) {
                System.out.println("Damit wäre das Spiel beendet.");
                break;
            }
            if(aktuelleListe.isEmpty()) {
                System.out.println("Sie haben sich alle Sudokus aus der Liste angeschaut, damit wäre das Spiel beendet.");
                break;
            }
            System.out.println("\n Fehler: "+lösungÜberprüfung(spielfelderLösungBefüllen("resources/sudokus.csv"), f));
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
    public static void spielfeldAusgabe(int[][][] spielFeld, int z) {
        int g = 4;
        int t = 1;
        System.out.println("----------------------------------");
        System.out.println(" || a  b  c | d  e  f | g  h  i");
        System.out.println("----------------------------------");

        for (int n = 0; n < 9; n++) {
            if(t>3) {
                System.out.println("----------------------------------");
                t = 1;
                System.out.print((n + 1) + "|");
            } else {
                System.out.print((n + 1) + "|");
            }
            for (int i = 0; i < 9; i++) {

                if(g>3) {
                    System.out.print("|");
                    g = 1;
                }
                if(spielFeld[z][n][i] == 0){
                    System.out.print(" _ ");
                    g++;
                    continue;
                }
                System.out.print(" "+ spielFeld[z][n][i]+" ");
                g++;

            }
            System.out.println();
            t++;
        }

    }
    public static int[][][] spielfelderAufgabeBefüllen(String path) throws FileNotFoundException {

        int y = 0;
        sudokuListe = readFile(path);
        String[][] sudoku = new String[81][sudokuListe.size()];
        String[] sudokuAufgeteilt = new String[sudokuListe.size()];
        String[][] alleSpielfelder = new String[sudokuListe.size()][81];
        int[][] alleSpielfelderInt = new int[sudokuListe.size()][81];
        int[][][] alleSpielfelderNummeriert = new int[sudokuListe.size()][9][9];

        for(int i = 0; i<sudokuListe.size(); i++){

            sudoku[i] = sudokuListe.get(i).split(",");
            sudokuAufgeteilt[i] = sudoku[i][0];

        }

        for(int i = 0; i<sudokuListe.size(); i++){

            alleSpielfelder[i] = sudokuAufgeteilt[i].split("");

        }

        for (int i = 0; i<sudokuListe.size(); i++){
            for (int n = 0; n < 81; n++){

                alleSpielfelderInt[i][n] = Integer.parseInt(alleSpielfelder[i][n]);

            }
        }

        for(int z = 0; z<alleSpielfelderInt.length; z++) {

            for (int i = 0; i < 9; i++) {
                for (int n = 0; n < 9; n++) {
                    alleSpielfelderNummeriert[z][i][n] = alleSpielfelderInt[z][n+y];
                }
                y = y+9;
            }
            y=0;
        }

        return alleSpielfelderNummeriert;
    }
    public static int[][][] spielfelderLösungBefüllen(String path) throws FileNotFoundException {

        int y = 0;
        sudokuListe = readFile(path);
        String[][] sudoku = new String[81][sudokuListe.size()];
        String[] sudokuAufgeteilt = new String[sudokuListe.size()];
        String[][] alleSpielfelder = new String[sudokuListe.size()][81];
        int[][] alleSpielfelderInt = new int[sudokuListe.size()][81];
        int[][][] alleSpielfelderNummeriert = new int[sudokuListe.size()][9][9];

        for(int i = 0; i<sudokuListe.size(); i++){

            sudoku[i] = sudokuListe.get(i).split(",");
            sudokuAufgeteilt[i] = sudoku[i][1];

        }

        for(int i = 0; i<sudokuListe.size(); i++){

            alleSpielfelder[i] = sudokuAufgeteilt[i].split("");

        }

        for (int i = 0; i<sudokuListe.size(); i++){
            for (int n = 0; n < 81; n++){

                alleSpielfelderInt[i][n] = Integer.parseInt(alleSpielfelder[i][n]);

            }
        }

        for(int z = 0; z<alleSpielfelderInt.length; z++) {

            for (int i = 0; i < 9; i++) {
                for (int n = 0; n < 9; n++) {
                    alleSpielfelderNummeriert[z][i][n] = alleSpielfelderInt[z][n+y];
                }
                y = y+9;
            }
            y=0;
        }

        return alleSpielfelderNummeriert;
    }
    public static int lösungÜberprüfung(int[][][] spielFeld, int z) {

        int fehler = 0;

        for(int y = 0; y<9; y++) {
            if (spielFeld[z].length != 9 && spielFeld[z][y].length != 9)
                fehler++;
        }

        for(int y = 0; y<9; y++) {
            for(int x = 0; x<9; x++) {
                if (spielFeld[z][y][x]<1||spielFeld[z][y][x]>9||(spielFeld[z][y][x]+'0')<'1'||(spielFeld[z][y][x]+'0')>'9')
                    fehler++;
            }
        }



        return fehler;
    }
}