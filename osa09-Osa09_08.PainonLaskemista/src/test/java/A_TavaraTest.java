
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

@Points("09-08.1 09-08.2")
public class A_TavaraTest extends TestUtils {

    String klassName = "Tavara";
    Reflex.ClassRef<Object> klass;

    @Before
    public void setup() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void luokkaJulkinen() {
        assertTrue("Luokan " + s(klassName) + " pitää olla julkinen, eli se tulee määritellä\npublic class " + s(klassName) + " {...\n}", klass.isPublic());
    }

    @Test
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus(klassName, 2, "nimen ja painon tallettavat oliomuuttujat");
    }

    @Test
    public void testaaKonstruktori() throws Throwable {
        Reflex.MethodRef2<Object, Object, String, Integer> ctor = klass.constructor().taking(String.class, int.class).withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "(String nimi, int paino)", ctor.isPublic());
        String v = "virheen aiheutti koodi new Tavara(\"Puhelin\", 1);";
        ctor.withNiceError(v).invoke("Puhelin", 1);
    }

    public Object luo(String nimi, int paino) throws Throwable {
        Reflex.MethodRef2<Object, Object, String, Integer> ctor = klass.constructor().taking(String.class, int.class).withNiceError();
        return ctor.invoke(nimi, paino);
    }

    @Test
    public void tavaraGetNimiMetodi() throws Throwable {
        String metodi = "getNimi";
        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassName);

        Object olio = luo("Kirja", 2);

        assertTrue("tee luokalle " + s(klassName) + " metodi public String " + metodi + "() ", tuoteClass.method(olio, metodi)
                .returning(String.class).takingNoParams().isPublic());

        String v = "\nVirheen aiheuttanut koodi Tavara t = new Tavara(\"Kirja\", 2); "
                + "t.getNimi();";

        assertEquals("Tarkasta koodi Tavara t = new Tavara(\"Kirja\", 2); "
                + "t.getNimi();", "Kirja", tuoteClass.method(olio, metodi)
                        .returning(String.class).takingNoParams().withNiceError(v).invoke());

        olio = luo("Auto", 800);

        v = "\nVirheen aiheuttanut koodi Tavara t = new Tavara(\"Auto\", 800); "
                + "t.getNimi();";

        assertEquals("Tarkasta koodi Tavara t = new Tavara(\"Auto\", 800);  "
                + "t.getNimi();", "Auto", tuoteClass.method(olio, metodi)
                        .returning(String.class).takingNoParams().withNiceError(v).invoke());
    }

    @Test
    public void tavaraGetPainoMetodi() throws Throwable {
        String metodi = "getPaino";
        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassName);

        Object olio = luo("Kirja", 2);

        assertTrue("tee luokalle " + s(klassName) + " metodi public int " + metodi + "() ", tuoteClass.method(olio, metodi)
                .returning(int.class).takingNoParams().isPublic());

        String v = "\nVirheen aiheuttanut koodi Tavara t = new Tavara(\"Kirja\", 2); "
                + "t.getPaino();";

        assertEquals("Tarkasta koodi Tavara t = new Tavara(\"Kirja\", 2); "
                + "t.getPaino();", 2, (int) tuoteClass.method(olio, metodi)
                        .returning(int.class).takingNoParams().withNiceError(v).invoke());

        olio = luo("Auto", 800);

        v = "\nVirheen aiheuttanut koodi Tavara t = new Tavara(\"Auto\", 800); "
                + "t.getPaino();";

        assertEquals("Tarkasta koodi Tavara t = new Tavara(\"Auto\", 800);  "
                + "t.getPaino();", 800, (int) tuoteClass.method(olio, metodi)
                        .returning(int.class).takingNoParams().withNiceError(v).invoke());
    }

    @Test
    public void tavaraToString() throws Throwable {
        Object olio = luo("Kirja", 2);

        assertFalse("Tee luokalle Tavara tehtävämäärittelyn mukainen toString", olio.toString().contains("@"));

        assertTrue("Tarkista että toString-metodi toimii kuten vaadittu. \n"
                + "Tavara t = new Tavara(\"Kirja\", 2); t.toString();  \n" + olio.toString(), sisaltaa(olio.toString(), "Kirja", "2", "kg"));
    }
}
