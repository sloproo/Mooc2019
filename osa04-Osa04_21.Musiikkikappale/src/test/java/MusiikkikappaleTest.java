
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

@Points("04-21")
public class MusiikkikappaleTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Musiikkikappale";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void luokkaJulkinen() {
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    public void testaaKonstruktori() throws Throwable {
        Reflex.MethodRef2<Object, Object, String, Integer> cc = klass.constructor().taking(String.class, int.class).withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "(String kappaleenNimi, int kappaleenPituus)", cc.isPublic());
        cc.invoke("Testikappale", 60);
    }

    @Test
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus();
    }

    @Test
    public void testaaNimi() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class, int.class).invoke("Grace", 25);

        try {
            klass.method(instance, "nimi")
                    .returning(String.class)
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Virhe: " + ae + "\n eli tee luokalle " + klassName + " metodi public String nimi()");
        }

        assertTrue("Metodin nimi tulee olla public eli määritelty public String nimi()", klass.method(instance, "nimi")
                .returning(String.class)
                .takingNoParams().isPublic());

        String out = klass.method(instance, "nimi").returning(String.class).takingNoParams().invoke();

        assertEquals("Kun kutsutaan:\nMusiikkikappale kappale = new Musiikkikappale(\"Grace\", 25);\nString nimi = kappale.nimi();\nTulee muuttujassa nimi olla arvo \"Grace\".\nNyt arvo oli: " + out, "Grace", out);

    }
    
    @Test
    public void testaaNimi2() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class, int.class).invoke("Beathoven", 2000);

        try {
            klass.method(instance, "nimi")
                    .returning(String.class)
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Virhe: " + ae + "\n eli tee luokalle " + klassName + " metodi public String nimi()");
        }

        assertTrue("Metodin nimi tulee olla public eli määritelty public String nimi()", klass.method(instance, "nimi")
                .returning(String.class)
                .takingNoParams().isPublic());

        String out = klass.method(instance, "nimi").returning(String.class).takingNoParams().invoke();

        assertEquals("Kun kutsutaan:\nMusiikkikappale kappale = new Musiikkikappale(\"Beathoven\", 2000);\nString nimi = kappale.nimi();\nTulee muuttujassa nimi olla arvo \"Beathoven\".\nNyt arvo oli: " + out, "Beathoven", out);
    }

    
    @Test
    public void testaaPituus() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class, int.class).invoke("Grace", 25);

        try {
            klass.method(instance, "pituus")
                    .returning(int.class)
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Virhe: " + ae + "\n eli tee luokalle " + klassName + " metodi public int pituus()");
        }

        assertTrue("Metodin pituus tulee olla public eli määritelty public int pituus()", klass.method(instance, "pituus")
                .returning(int.class)
                .takingNoParams().isPublic());

        int out = klass.method(instance, "pituus").returning(int.class).takingNoParams().invoke();

        assertEquals("Kun kutsutaan:\nMusiikkikappale kappale = new Musiikkikappale(\"Grace\", 25);\nint pituus = kappale.pituus();\nTulee muuttujassa pituus olla arvo 25.\nNyt arvo oli: " + out, 25, out);
    }
    
    @Test
    public void testaaPituus2() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class, int.class).invoke("Beathoven", 1988);

        try {
            klass.method(instance, "pituus")
                    .returning(int.class)
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Virhe: " + ae + "\n eli tee luokalle " + klassName + " metodi public int pituus()");
        }

        assertTrue("Metodin pituus tulee olla public eli määritelty public int pituus()", klass.method(instance, "pituus")
                .returning(int.class)
                .takingNoParams().isPublic());

        int out = klass.method(instance, "pituus").returning(int.class).takingNoParams().invoke();

        assertEquals("Kun kutsutaan:\nMusiikkikappale kappale = new Musiikkikappale(\"Beathoven\", 1988);\nint pituus = kappale.pituus();\nTulee muuttujassa pituus olla arvo 1988.\nNyt arvo oli: " + out, 1988, out);
    }
    
    private void saniteettitarkastus() throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("et tarvitse \"stattisia muuttujia\", poista " + kentta(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("luokan kaikkien oliomuuttujien näkyvyyden tulee olla private, muuta " + kentta(field.toString()), field.toString().contains("private"));
        }

        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("et tarvitse " + klassName + "-luokalle kuin nimen ja pituuden. Poista ylimääräiset muuttujat", var < 3);
        }
    }

    private String kentta(String toString) {
        return toString.replace(klassName + ".", "");
    }
}
