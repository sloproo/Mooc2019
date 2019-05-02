
import java.util.Arrays;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

@Points("08-06.1")
public class Osa1TalletettavaTest {

    static final String kirjaNimi = "Kirja";
    static final String levyNimi = "CDLevy";
    static final double eps = 0.00000001;
    Class tallc;
    Reflex.ClassRef<Object> klass;

    @Before
    public void haeTalletettava() {
        try {
            tallc = ReflectionUtils.findClass("Talletettava");
        } catch (AssertionError e) {
            fail("Lisää rajapinta Talletettava!");
        }

        String klassName = "Talletettava";
        klass = Reflex.reflect(klassName);

        assertTrue("Määrittele rajapinta " + klassName + " seuraavasti \npublic interface " + klassName + " {...\n}", klass.isPublic());

    }

    @Test
    public void talletettavaOikein() {

        assertTrue("Talletettavan on oltava rajapintaluokka!", tallc.isInterface());

        Method ms[] = tallc.getDeclaredMethods();

        assertEquals("Rajapinnassa Talletettava pitäisi määritellä yksi metodi!", 1, ms.length);

        assertEquals("Rajapinnassa Talletettava pitäisi olla metodi double paino()",
                "public abstract double Talletettava.paino()",
                ms[0].toString());
    }

    public void toteuttaaTalletettavan(String nimi) {
        String klassName = nimi;
        klass = Reflex.reflect(klassName);
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", klass.isPublic());

        Class kl = null;
        try {
            kl = ReflectionUtils.findClass(nimi);
        } catch (Throwable t) {
            fail("Tarkista että olet luonut luokan nimeltä " + nimi);
        }

        Class is[] = kl.getInterfaces();
        Class oikein[] = {tallc};

        assertTrue("Varmista että " + nimi + " toteuttaa (vain!) rajapinnan Talletettava",
                Arrays.equals(is, oikein));

    }

    @Test
    public void kirjaToteuttaaTalletettavan() {
        toteuttaaTalletettavan(kirjaNimi);
    }

    @Test
    public void kirjaToimii() throws Throwable {
        String klassName = "Kirja";
        klass = Reflex.reflect(klassName);

        Reflex.MethodRef3<Object, Object, String, String, Double> ctor = klass.constructor().
                taking(String.class, String.class, double.class).withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public "
                + klassName + "(String kirjoittaja, String nimi, double paino)", ctor.isPublic());
        String v = "virheen aiheutti koodi new Kirja(\"Sepe Susi\", \"Maukasta porsaasta\", 9000.0);";
        Object olio = ctor.withNiceError(v).invoke("Sepe Susi", "Maukasta porsaasta", 9000.0);

        String metodi = "paino";

        assertTrue("tee luokalle " + klassName + " metodi public double " + metodi + "() ",
                klass.method(olio, metodi)
                .returning(double.class).takingNoParams().isPublic());

        v = "\nVirheen aiheuttanut koodi new Kirja k = Kirja(\"Sepe Susi\", \"Maukasta porsaasta\", 9000.0); "
                + "k.paino();";

        double p = klass.method(olio, metodi)
                .returning(double.class).takingNoParams().withNiceError(v).invoke();

        assertEquals(" new Kirja k = Kirja(\"Sepe Susi\", \"Maukasta porsaasta\", 9000.0); "
                + "k.paino();", 9000., p, 0.01);

        assertFalse("tee luokalle Kirja tehtävänannon mukainen toString-metodi", olio.toString().contains("@"));

        assertEquals("toString ei palauta oikeaa asiaa", "Sepe Susi: Maukasta porsaasta", olio.toString());

    }

    @Test
    public void cdLevyToteuttaaTalletettavan() {
        toteuttaaTalletettavan(levyNimi);
    }

    @Test
    public void cdLevyToimii() throws Throwable {
        String klassName = "CDLevy";
        klass = Reflex.reflect(klassName);

        Reflex.MethodRef3<Object, Object, String, String, Integer> ctor = klass.constructor().
                taking(String.class, String.class, int.class).withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public "
                + klassName + "(String artisti, String nimi, int levytysvuosi)", ctor.isPublic());
        String v = "virheen aiheutti koodi new CDLevy(\"Sepe Susi\", \"Porsas-rock\", 1830);";

        Object olio = ctor.withNiceError(v).invoke("Sepe Susi", "Porsas-rock", 1830);

        String metodi = "paino";

        assertTrue("tee luokalle " + klassName + " metodi public double " + metodi + "() ",
                klass.method(olio, metodi)
                .returning(double.class).takingNoParams().isPublic());

        v = "\nVirheen aiheuttanut koodi CDLevy cd = new CDLevy(\"Sepe Susi\", \"Porsas-rock\", 1830); "
                + "cd.paino();";

        double p = klass.method(olio, metodi)
                .returning(double.class).takingNoParams().withNiceError(v).invoke();

        assertEquals("CDLevy cd = new CDLevy(\"Sepe Susi\", \"Porsas-rock\", 1830); "
                + "cd.paino();", .1, p,  0.001);

        assertFalse("tee luokalle CDLevy tehtävänannon mukainen toString-metodi", olio.toString().contains("@"));

        assertEquals("toString ei palauta oikeaa asiaa", "Sepe Susi: Porsas-rock (1830)", olio.toString());


    }
}
