
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class TaikanelioTest {

    @Test
    @Points("11-10.1")
    public void rivienSummatOikein() {
        int[][] t = {{1, 2}, {3, 4}};

        Taikanelio tn = luoTaikanelio(t);

        ArrayList<Integer> rivienSummat = tn.rivienSummat();
        assertEquals("Kun taikaneliön leveys ja korkeus on 2, rivien summat-metodin pitäisi palauttaa lista, jossa on kaksi arvoa. Nyt listalla oli " + rivienSummat.size() + " arvoa.", 2, rivienSummat.size());
        assertTrue("Kun taikaneliö on:\n" + tn + "\nTulee rivienSummat-metodin palauttamalla listalla olla arvot 3 ja 7. Nyt ei ollut.", rivienSummat.contains(3));
        assertTrue("Kun taikaneliö on:\n" + tn + "\nTulee rivienSummat-metodin palauttamalla listalla olla arvot 3 ja 7. Nyt ei ollut.", rivienSummat.contains(7));
    }

    @Test
    @Points("11-10.1")
    public void rivienSummatOikein2() {
        int[][] t = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        Taikanelio tn = luoTaikanelio(t);

        ArrayList<Integer> rivienSummat = tn.rivienSummat();
        assertEquals("Kun taikaneliön leveys ja korkeus on 3, rivien summat-metodin pitäisi palauttaa lista, jossa on kolme arvoa. Nyt listalla oli " + rivienSummat.size() + " arvoa.", 3, rivienSummat.size());
        assertTrue("Kun taikaneliö on:\n" + tn + "\nTulee rivienSummat-metodin palauttamalla listalla olla arvot 6, 15 ja 24. Nyt ei ollut.", rivienSummat.contains(6));
        assertTrue("Kun taikaneliö on:\n" + tn + "\nTulee rivienSummat-metodin palauttamalla listalla olla arvot 6, 15 ja 24. Nyt ei ollut.", rivienSummat.contains(15));
        assertTrue("Kun taikaneliö on:\n" + tn + "\nTulee rivienSummat-metodin palauttamalla listalla olla arvot 6, 15 ja 24. Nyt ei ollut.", rivienSummat.contains(24));
    }

    @Test
    @Points("11-10.2")
    public void sarakkeidenSummatOikein() {
        int[][] t = {{1, 2}, {3, 4}};

        Taikanelio tn = luoTaikanelio(t);

        ArrayList<Integer> sarakkeidenSummat = tn.sarakkeidenSummat();
        assertEquals("Kun taikaneliön leveys ja korkeus on 2, sarakkeidenSummat-metodin pitäisi palauttaa lista, jossa on kaksi arvoa. Nyt listalla oli " + sarakkeidenSummat.size() + " arvoa.", 2, sarakkeidenSummat.size());
        assertTrue("Kun taikaneliö on:\n" + tn + "\nTulee sarakkeidenSummat-metodin palauttamalla listalla olla arvot 4 ja 6. Nyt ei ollut.", sarakkeidenSummat.contains(4));
        assertTrue("Kun taikaneliö on:\n" + tn + "\nTulee sarakkeidenSummat-metodin palauttamalla listalla olla arvot 4 ja 6. Nyt ei ollut.", sarakkeidenSummat.contains(6));
    }

    @Test
    @Points("11-10.2")
    public void sarakkeidenSummatOikein2() {
        int[][] t = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        Taikanelio tn = luoTaikanelio(t);

        ArrayList<Integer> sarakkeidenSummat = tn.sarakkeidenSummat();
        assertEquals("Kun taikaneliön leveys ja korkeus on 3, sarakkeidenSummat-metodin pitäisi palauttaa lista, jossa on kolme arvoa. Nyt listalla oli " + sarakkeidenSummat.size() + " arvoa.", 3, sarakkeidenSummat.size());
        assertTrue("Kun taikaneliö on:\n" + tn + "\nTulee sarakkeidenSummat-metodin palauttamalla listalla olla arvot 12, 15 ja 18. Nyt ei ollut.", sarakkeidenSummat.contains(12));
        assertTrue("Kun taikaneliö on:\n" + tn + "\nTulee sarakkeidenSummat-metodin palauttamalla listalla olla arvot 12, 15 ja 18. Nyt ei ollut.", sarakkeidenSummat.contains(15));
        assertTrue("Kun taikaneliö on:\n" + tn + "\nTulee sarakkeidenSummat-metodin palauttamalla listalla olla arvot 12, 15 ja 18. Nyt ei ollut.", sarakkeidenSummat.contains(18));
    }

    @Test
    @Points("11-10.3")
    public void lavistajienSummatOikein() {
        int[][] t = {{1, 2}, {3, 4}};

        Taikanelio tn = luoTaikanelio(t);

        ArrayList<Integer> lavistajienSummat = tn.lavistajienSummat();
        assertEquals("Kun taikaneliön leveys ja korkeus on 2, lavistajienSummat-metodin pitäisi palauttaa lista, jossa on kaksi arvoa. Nyt listalla oli " + lavistajienSummat.size() + " arvoa.", 2, lavistajienSummat.size());
        assertTrue("Kun taikaneliö on:\n" + tn + "\nTulee lavistajienSummat-metodin palauttamalla listalla olla arvot 5 ja 5. Nyt ei ollut.", lavistajienSummat.contains(5));

        Collections.sort(lavistajienSummat);
        assertEquals("Kun taikaneliö on:\n" + tn + "\nTulee lavistajienSummat-metodin palauttamalla listalla olla arvot 5 ja 5. Nyt ei ollut.", lavistajienSummat.get(0), lavistajienSummat.get(1));
    }

    @Test
    @Points("11-10.3")
    public void lavistajienSummatOikein2() {
        int[][] t = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        Taikanelio tn = luoTaikanelio(t);

        ArrayList<Integer> lavistajienSummat = tn.lavistajienSummat();
        assertEquals("Kun taikaneliön leveys ja korkeus on 3, lavistajienSummat-metodin pitäisi palauttaa lista, jossa on kaksi arvoa. Nyt listalla oli " + lavistajienSummat.size() + " arvoa.", 2, lavistajienSummat.size());
        assertTrue("Kun taikaneliö on:\n" + tn + "\nTulee lavistajienSummat-metodin palauttamalla listalla olla arvot 15 ja 15. Nyt ei ollut.", lavistajienSummat.contains(15));

        Collections.sort(lavistajienSummat);
        assertEquals("Kun taikaneliö on:\n" + tn + "\nTulee lavistajienSummat-metodin palauttamalla listalla olla arvot 15 ja 15. Nyt ei ollut.", lavistajienSummat.get(0), lavistajienSummat.get(1));
    }

    @Test
    @Points("11-10.3")
    public void lavistajienSummatOikein3() {
        int[][] t = {{1, 1}, {5, 7}};

        Taikanelio tn = luoTaikanelio(t);

        ArrayList<Integer> lavistajienSummat = tn.lavistajienSummat();
        assertEquals("Kun taikaneliön leveys ja korkeus on 2, lavistajienSummat-metodin pitäisi palauttaa lista, jossa on kaksi arvoa. Nyt listalla oli " + lavistajienSummat.size() + " arvoa.", 2, lavistajienSummat.size());
        assertTrue("Kun taikaneliö on:\n" + tn + "\nTulee lavistajienSummat-metodin palauttamalla listalla olla arvot 6 ja 8. Nyt ei ollut.", lavistajienSummat.contains(6));
        assertTrue("Kun taikaneliö on:\n" + tn + "\nTulee lavistajienSummat-metodin palauttamalla listalla olla arvot 6 ja 8. Nyt ei ollut.", lavistajienSummat.contains(8));
    }

    @Test
    @Points("11-10.4")
    public void testaaTehdas() {
        taikanelioTehdas(3);
    }

    @Test
    @Points("11-10.4")
    public void testaaTehdas2() {
        taikanelioTehdas(9);
    }

    public void taikanelioTehdas(int koko) {
        rivienSummatOikein();
        sarakkeidenSummatOikein();
        lavistajienSummatOikein();

        Taikaneliotehdas t = new Taikaneliotehdas();
        Taikanelio tn = t.luoTaikanelio(koko);

        ArrayList<Integer> summat = new ArrayList<>();
        summat.addAll(tn.rivienSummat());
        summat.addAll(tn.sarakkeidenSummat());
        summat.addAll(tn.lavistajienSummat());

        assertTrue("Taikaneliötehtään luoman taikaneliön summa-metodit eivät toimi oikein.", summat.size() > 2);

        for (int i = 1; i < summat.size(); i++) {
            assertEquals("Rivien, sarakkeiden ja lävistäjien summien pitäisi olla taikaneliössä samat.", summat.get(i - 1), summat.get(i));
        }

        ArrayList<Integer> luvut = tn.annaKaikkiNumerot();
        assertTrue("Taikaneliön annaKaikkiNumerot-metodin tulee palauttaa lista, missä on kaikki taikaneliön luvut.", luvut.size() > 2);
        Collections.sort(luvut);

        for (int i = 1; i < luvut.size(); i++) {
            Assert.assertNotSame("Jokaisen taikaneliössa olevan luvun pitäisi olla erilainen. Nyt " + koko + " kokoisessa taikaneliössä näin ei ollut:\n" + tn, luvut.get(i - 1), luvut.get(i));
        }

    }

    public Taikanelio luoTaikanelio(int[][] luvut) {
        Taikanelio t = new Taikanelio(luvut.length);
        for (int y = 0; y < luvut.length; y++) {
            for (int x = 0; x < luvut[y].length; x++) {
                t.asetaArvo(x, y, luvut[y][x]);
            }
        }

        return t;
    }
}
