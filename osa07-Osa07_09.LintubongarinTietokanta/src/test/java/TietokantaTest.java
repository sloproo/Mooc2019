
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("07-09.1 07-09.2 07-09.3")
public class TietokantaTest {

    @Rule
    public MockStdio io = new MockStdio();

    String testaa(String in) {

        String s;
        try {
            io.setSysIn(in);
            Paaohjelma.main(new String[0]);
            s = io.getSysOut();
        } catch (NoSuchElementException e) {
            fail("Syötteen lukemisessa tapahtui virhe. Kokeile antaa ohjelmallesi syötteeksi:\n" + in);
            return null;
        } catch (Throwable t) {
            fail("Jokin meni pieleen. Kokeile antaa ohjelmallesi syötteeksi:\n" + in + "\n\nLisätietoja:\n" + t);
            return null;
        }
        return s;

    }

    @Test
    public void testaaLopeta1() {
        testaa("Lopeta\n");
    }

    @Test
    public void testaaTyhjaTilasto() {
        testaa("Tilasto\nLopeta\n");
    }

    @Test
    public void testaaNaytaEiOlemassa() {
        testaa("Nayta\nKieppi\nLopeta\n");
    }

    @Test
    public void testaaHavaintoEiOlemassa() {
        testaa("Havainto\nKeppi\nLopeta\n");
    }

    @Test
    public void testaaLisaaNayta() {
        String in = "Lisaa\nXXX\nYYY\nNayta\nXXX\nLopeta\n";
        String out = testaa(in);
        String viesti = "Kokeile antaa ohjelmallesi syötteeksi:\n" + in;

        assertTrue("Et tulostanut linnun nimeä Nayta-komennolla. " + viesti,
                out.contains("XXX"));
        assertTrue("Et tulostanut linnun latinankielistä nimeä Nayta-komennolla. " + viesti,
                out.contains("YYY"));
        assertTrue("Et tulostanut havaintojen lukumäärää Nayta-komennolla. " + viesti,
                out.contains("0"));
    }

    @Test
    public void testaaLisaaHavaintoHavaintoNayta() {
        String in = "Lisaa\nXXX\nYYY\nHavainto\nXXX\nHavainto\nXXX\nNayta\nXXX\nLopeta\n";
        String out = testaa(in);
        String viesti = "Kokeile antaa ohjelmallesi syötteeksi:\n" + in;

        assertTrue("Et tulostanut linnun nimeä Nayta-komennolla. " + viesti,
                out.contains("XXX"));
        assertTrue("Et tulostanut linnun latinankielistä nimeä Nayta-komennolla. " + viesti,
                out.contains("YYY"));
        assertTrue("Et tulostanut havaintojen lukumäärää Nayta-komennolla. " + viesti,
                out.contains("2"));

    }

    public void lintu(String viesti, String out, String a, String b, int lkm) {
        String[] lines = out.split("\n");
        boolean ok = false;
        for (String line : lines) {
            if (line.contains(a) && line.contains(b) && line.contains("" + lkm)) {
                ok = true;
            }
        }
        assertTrue("Et tulostanut linnun " + a + " (" + b + ") havaintojen määrää oikein. " + viesti,
                ok);
    }

    @Test
    public void testaaKaksiLintuaLisaaHavaintoNayta() {
        String in = "Lisaa\nXXX\nYYY\nHavainto\nXXX\n"
                + "Lisaa\nWWW\nZZZ\n"
                + "Havainto\nWWW\n"
                + "Nayta\nXXX\n"
                + "Havainto\nXXX\nHavainto\nWWW\n"
                + "Nayta\nWWW\n"
                + "Lopeta\n";
        String out = testaa(in);
        String viesti = "Kokeile antaa ohjelmallesi syötteeksi:\n" + in;

        lintu(viesti, out, "XXX", "YYY", 1);
        lintu(viesti, out, "WWW", "ZZZ", 2);
    }

    @Test
    public void testaaKaksiLintuaTilasto() {
        String in = "Lisaa\nXX\nYY\n"
                + "Lisaa\nWW\nZZ\n"
                + "Havainto\nWW\n"
                + "Havainto\nXX\nHavainto\nWW\n"
                + "Havainto\nXX\nHavainto\nWW\n"
                + "Tilasto\n"
                + "Lopeta\n";
        String out = testaa(in);
        String viesti = "Kokeile antaa ohjelmallesi syötteeksi:\n" + in;

        lintu(viesti, out, "XX", "YY", 2);
        lintu(viesti, out, "WW", "ZZ", 3);
    }

    @Test
    public void testaaTuntemattomiaKomentoja() {
        testaa("Olut\nLopeta\n");
        testaa("Hakku\nLopeta\n");
        testaa("Havaitseminen\nLopeta\n");
    }
}
