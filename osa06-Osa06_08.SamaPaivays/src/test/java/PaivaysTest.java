
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import org.junit.*;
import static org.junit.Assert.*;

@Points("06-08")
public class PaivaysTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Paivays";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void onMetodiEquals() throws Throwable {
        String metodi = "equals";

        Paivays olio = new Paivays(1, 1, 2011);

        assertTrue("tee luokalle " + klassName + " metodi public boolean " + metodi + "(Object verrattava) ",
                klass.method(olio, metodi).returning(boolean.class).taking(Object.class).isPublic());

    }

    @Test
    public void equalsFalseJosVuosiEri() {
        Paivays eka = new Paivays(3, 7, 2011);
        Paivays toka = new Paivays(3, 7, 2010);

        assertFalse("Kun kutsutaan:\nboolean onko = new Paivays(3, 7, 2011).equals(new Paivays(3, 7, 2010));\n tulee muuttujan onko arvon olla false.", eka.equals(toka));
    }

    @Test
    public void equalsFalseJosKuukausiEri() {
        Paivays eka = new Paivays(3, 7, 2011);
        Paivays toka = new Paivays(3, 6, 2011);

        assertFalse("Kun kutsutaan:\nboolean onko = new Paivays(3, 7, 2011).equals(new Paivays(3, 6, 2011));\n tulee muuttujan onko arvon olla false.", eka.equals(toka));
    }

    @Test
    public void equalsFalseJosPaivaEri() {
        Paivays eka = new Paivays(3, 7, 2011);
        Paivays toka = new Paivays(4, 7, 2011);

        assertFalse("Kun kutsutaan:\nboolean onko = new Paivays(3, 7, 2011).equals(new Paivays(4, 7, 2011));\n tulee muuttujan onko arvon olla false.", eka.equals(toka));
    }

    @Test
    public void equalsTrueJosPaivaSama() {
        Paivays eka = new Paivays(3, 7, 2011);
        Paivays toka = new Paivays(3, 7, 2011);

        assertTrue("Kun kutsutaan:\nboolean onko = new Paivays(3, 7, 2011).equals(new Paivays(3, 7, 2011));\n tulee muuttujan onko arvon olla true.", eka.equals(toka));
    }

}
