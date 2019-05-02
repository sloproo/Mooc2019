
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-29")
public class PieninTest {

    @Test
    public void normaalitapaus() {
        testaa(2, 7, 2);
    }

    @Test
    public void negatiivinenToinen() {
        testaa(-5, 4, -5);
    }

    @Test
    public void hyvinsuuretArvot() {
        testaa(Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    @Test
    public void normaalitapausToisinPain() {
        testaa(7, 2, 2);
    }

    private void testaa(int luku1, int luku2, int odotus) {
        assertEquals(getVirhe(luku1, luku2), odotus, Pienin.pienin(luku1, luku2));
    }

    private String getVirhe(int luku1, int luku2) {
        return "Väärä vastaus syötteellä luku1=" + luku1 + " ja luku2=" + luku2;
    }
}
