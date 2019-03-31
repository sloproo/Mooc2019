
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import static java.lang.System.out;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;

import org.junit.Test;

@Points("11-09")
public class TaulukkoMerkkijononaTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void metodi() {
        assertTrue("Luokassa Ohjelma tulee olla metodi public static String taulukkoMerkkijonona(int[][] taulukko).", Reflex.reflect(Ohjelma.class).staticMethod("taulukkoMerkkijonona").returning(String.class).taking(int[][].class).isPublic());
    }

    @Test
    public void metodiEiTulostaMitaan() {
        String out = io.getSysOut();
        int[][] matriisi = {
            {0, 5, 0},
            {3, 0, 7}
        };

        try {
            Reflex.reflect(Ohjelma.class).staticMethod("taulukkoMerkkijonona").returning(String.class).taking(int[][].class).invoke(matriisi);
        } catch (Throwable t) {

        }

        assertTrue("Metodin taulukkoMerkkijonona ei tule tulostaa mitään.", out.equals(io.getSysOut()));
    }

    @Test
    public void test1() {

        int[][] matriisi = {
            {0, 5, 0},
            {3, 0, 7}
        };

        String palautus = null;
        try {
            palautus = Reflex.reflect(Ohjelma.class).staticMethod("taulukkoMerkkijonona").returning(String.class).taking(int[][].class).invoke(matriisi);
        } catch (Throwable t) {

        }

        palautus = palautus.trim();
        assertEquals("Kokeile metodia koodilla:\nint[][] matriisi = {\n"
                + "  {0, 5, 0},\n"
                + "  {3, 0, 7}\n"
                + "};\n\n"
                + "System.out.println(taulukkoMerkkijonona(matriisi));", "050\n307", palautus);

    }

    @Test
    public void test2() {
        int[][] matriisi = {
            {3, 2, 7, 6},
            {2, 4, 1, 0},
            {3, 2, 1, 0}
        };

        String palautus = null;
        try {
            palautus = Reflex.reflect(Ohjelma.class).staticMethod("taulukkoMerkkijonona").returning(String.class).taking(int[][].class).invoke(matriisi);
        } catch (Throwable t) {

        }

        palautus = palautus.trim();
        assertEquals("Kokeile metodia koodilla:\nint[][] matriisi = {\n"
                + "  {3, 2, 7, 6},\n"
                + "  {2, 4, 1, 0},\n"
                + "  {3, 2, 1, 0}\n"
                + "};\n\n"
                + "System.out.println(taulukkoMerkkijonona(matriisi));",
                "3276\n"
                + "2410\n"
                + "3210", palautus);
    }

    @Test
    public void test3() {
        int[][] matriisi = {
            {1},
            {2, 2},
            {3, 3, 3},
            {4, 4, 4, 4}
        };

        String palautus = null;
        try {
            palautus = Reflex.reflect(Ohjelma.class).staticMethod("taulukkoMerkkijonona").returning(String.class).taking(int[][].class).invoke(matriisi);
        } catch (Throwable t) {

        }

        palautus = palautus.trim();
        assertEquals("Kokeile metodia koodilla:\nint[][] matriisi = {\n"
                + "  {1},\n"
                + "  {2, 2},\n"
                + "  {3, 3, 3},\n"
                + "  {4, 4, 4, 4}\n"
                + "};\n\n"
                + "System.out.println(taulukkoMerkkijonona(matriisi));",
                "1\n"
                + "22\n"
                + "333\n"
                + "4444", palautus);
    }

}
