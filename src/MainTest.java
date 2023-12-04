import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MainTest {

    @Test
    public void spielfelderNummeriertBefüllenTest() throws FileNotFoundException {

        for(int i=0; i<Main.spielfelderNummeriertBefüllen("resources/sudokus.csv", "lösung").length; i++) {
            assertNotEquals(0, Main.spielfelderNummeriertBefüllen("resources/sudokus.csv", "lösung")[i][0][0]);
        }
    } //Hier wird getestet, ob keine der Lösungen eine 0 beinhaltet.

    @Test
    public void lösungÜberprüfungTest() throws FileNotFoundException {

        for(int i=0; i<Main.spielfelderNummeriertBefüllen("resources/sudokus.csv", "lösung").length; i++) {
            assertEquals(0, Main.lösungÜberprüfung(Main.spielfelderNummeriertBefüllen("resources/sudokus.csv", "lösung")[i]));
        }
        //Hier wird getestet, ob die lösungÜberprüfung Methode 0 Fehler in allen Sudoku Lösungen ausgibt.
    } //Hier wird getestet, ob die lösungÜberprüfung Methode 0 Fehler in allen Sudoku Lösungen ausgibt.
}