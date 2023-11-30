import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @Test
    public void spielfelderNummeriertBefüllen() throws FileNotFoundException {

        Main.sudokuListe = Main.readFile("resources/sudokus.csv");
        int x = 0;
        int y = 0;
        String[][] sudoku = new String[81][Main.sudokuListe.size()];
        String[] sudokuAufgeteilt = new String[Main.sudokuListe.size()];
        String[][] alleSpielfelder = new String[Main.sudokuListe.size()][81];
        int[][] alleSpielfelderInt = new int[Main.sudokuListe.size()][81];
        int[][][] alleSpielfelderNummeriert = new int[Main.sudokuListe.size()][9][9];

        for (int i = 0; i < Main.sudokuListe.size(); i++) {
            sudoku[i] = Main.sudokuListe.get(i).split(",");
            sudokuAufgeteilt[i] = sudoku[i][x];
            alleSpielfelder[i] = sudokuAufgeteilt[i].split("");

            for (int n = 0; n < 81; n++)
                alleSpielfelderInt[i][n] = Integer.parseInt(alleSpielfelder[i][n]);
        }

        for (int z = 0; z < Main.sudokuListe.size(); z++) {
            for (int i = 0; i < 9; i++) {
                for (int n = 0; n < 9; n++) {
                    alleSpielfelderNummeriert[z][i][n] = alleSpielfelderInt[z][n + y];
                }
                y += 9;
            }
            y = 0;
        }

        assertEquals(4, alleSpielfelderNummeriert[0][0][3]);

    }

    @Test
    void lösungÜberprüfung() {

    }
}