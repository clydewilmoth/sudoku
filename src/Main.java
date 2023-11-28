import java.util.*;
import java .io.*;
public class Main {
    static ArrayList<String> sudokuListe = new ArrayList<>();
    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(System.in);
        String sehen = "";
        String entscheidung = "ja";
        int f=0;

        while(true){

            spielfeldAusgabe(spielfelderAufgabeBefüllen("resources/sudokus.csv"), f);
            f = (int) ((Math.random()*sudokuListe.size())+1);
            System.out.println("\nSudoku Nummer "+ f);
            System.out.print("\nMöchten Sie die Lösung sehen?(irgendetwas mit Enter bestätigen um fortzufahren): ");
            sehen = sc.next();
            System.out.println();
            spielfeldAusgabe(spielfelderLösungBefüllen("resources/sudokus.csv"), f);
            System.out.println("\nMöchten Sie sich an das nächste Sudoku ranwagen?(ja/nein): ");
            entscheidung = sc.next();
            if(entscheidung.equals("nein"))
                break;
            System.out.println();
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
        System.out.println("    a  b  c | d  e  f | g  h  i");
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
}