
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

@Points("11-03")
public class RajatutTaulukossaTest {

    @Test
    public void metodiOn() {
        assertTrue("Metodia public static int summa(int[] taulukko, int mista, int mihin, int pienin, int suurin) ei löytynyt luokasta Ohjelma.", Reflex.reflect(Ohjelma.class).staticMethod("summa").returning(int.class).taking(int[].class, int.class, int.class, int.class, int.class).isPublic());
    }

    @Test
    public void huonotRajatKasitellaan() {
        Reflex.reflect(Ohjelma.class).staticMethod("summa").returning(int.class).taking(int[].class, int.class, int.class, int.class, int.class);

        int[] arvot = {1};
        try {
            Reflex.reflect(Ohjelma.class).staticMethod("summa").returning(int.class).taking(int[].class, int.class, int.class, int.class, int.class).invoke(arvot, -1, 3, 0, 5);
        } catch (Throwable t) {
            fail("Varmista että epäkelvot alkuindeksit ja loppuindeksit käsitellään oikein. Kokeile:\n"
                    + "int[] arvot = {1};\n"
                    + "System.out.println(summa(arvot, -2, 3, 0, 999));");
        }
    }

    @Test
    public void kutsu1() {
        Reflex.reflect(Ohjelma.class).staticMethod("summa").returning(int.class).taking(int[].class, int.class, int.class, int.class, int.class);

        String virhe = "Virhe metodia kutsuttaessa. Kokeile:\n"
                + "int[] arvot = {8, 2, 9, 1, 1};\n"
                + "System.out.println(summa(arvot, 0, arvot.length, 0, 999));";
        int[] arvot = {8, 2, 9, 1, 1};

        int summa = 0;
        try {
            summa = Reflex.reflect(Ohjelma.class).staticMethod("summa").returning(int.class).taking(int[].class, int.class, int.class, int.class, int.class).invoke(arvot, 0, arvot.length, 0, 999);
        } catch (Throwable t) {
            fail(virhe);
        }

        assertTrue(virhe, summa == 21);
    }

    @Test
    public void kutsu2() {
        Reflex.reflect(Ohjelma.class).staticMethod("summa").returning(int.class).taking(int[].class, int.class, int.class, int.class, int.class);

        String virhe = "Virhe metodia kutsuttaessa. Kokeile:\n"
                + "int[] arvot = {8, -2, 3, 1, 1};\n"
                + "System.out.println(summa(arvot, 0, arvot.length, 0, 999));";
        int[] arvot = {8, -2, 3, 1, 1};

        int summa = 0;
        try {
            summa = Reflex.reflect(Ohjelma.class).staticMethod("summa").returning(int.class).taking(int[].class, int.class, int.class, int.class, int.class).invoke(arvot, 0, arvot.length, 0, 999);
        } catch (Throwable t) {
            fail(virhe);
        }

        assertTrue(virhe, summa == 13);
    }
    
    @Test
    public void kutsu3() {
        Reflex.reflect(Ohjelma.class).staticMethod("summa").returning(int.class).taking(int[].class, int.class, int.class, int.class, int.class);

        String virhe = "Virhe metodia kutsuttaessa. Kokeile:\n"
                + "int[] arvot = {8, -2, 3, 1, 1, 1, 2, 3};\n"
                + "System.out.println(summa(arvot, 5, arvot.length, 0, 999));";
        int[] arvot = {8, -2, 3, 1, 1, 1, 2, 3};

        int summa = 0;
        try {
            summa = Reflex.reflect(Ohjelma.class).staticMethod("summa").returning(int.class).taking(int[].class, int.class, int.class, int.class, int.class).invoke(arvot, 5, arvot.length, 0, 999);
        } catch (Throwable t) {
            fail(virhe);
        }

        assertTrue(virhe, summa == 6);
    }

}
