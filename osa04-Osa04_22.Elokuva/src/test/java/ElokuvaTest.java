
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

@Points("04-22")
public class ElokuvaTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Elokuva";

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
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "(String elokuvanNimi, int elokuvanIkaraja)", cc.isPublic());
        cc.invoke("Testielokuva", 5);
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

        assertEquals("Kun kutsutaan:\nElokuva kuva = new Elokuva(\"Grace\", 25);\nString nimi = kuva.nimi();\nTulee muuttujassa nimi olla arvo \"Grace\".\nNyt arvo oli: " + out, "Grace", out);

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

        assertEquals("Kun kutsutaan:\nElokuva kuva = new Elokuva(\"Beathoven\", 16);\nString nimi = kuva.nimi();\nTulee muuttujassa nimi olla arvo \"Beathoven\".\nNyt arvo oli: " + out, "Beathoven", out);
    }

    @Test
    public void testaaIkaraja() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class, int.class).invoke("Beathoven", 16);

        try {
            klass.method(instance, "ikaraja")
                    .returning(int.class)
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Virhe: " + ae + "\n eli tee luokalle " + klassName + " metodi public int ikaraja()");
        }

        assertTrue("Metodin ikaraja tulee olla public eli määritelty public int ikaraja()", klass.method(instance, "ikaraja")
                .returning(int.class)
                .takingNoParams().isPublic());

        int out = klass.method(instance, "ikaraja").returning(int.class).takingNoParams().invoke();

        assertEquals("Kun kutsutaan:\nElokuva kuva = new Elokuva(\"Beathoven\", 16);\nint ikaraja = kuva.ikaraja();\nTulee muuttujassa ikaraja olla arvo 16.\nNyt arvo oli: " + out, 16, out);
    }
    
    @Test
    public void testaaIkaraja2() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class, int.class).invoke("Karvinen", 7);

        try {
            klass.method(instance, "ikaraja")
                    .returning(int.class)
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Virhe: " + ae + "\n eli tee luokalle " + klassName + " metodi public int ikaraja()");
        }

        assertTrue("Metodin ikaraja tulee olla public eli määritelty public int ikaraja()", klass.method(instance, "ikaraja")
                .returning(int.class)
                .takingNoParams().isPublic());

        int out = klass.method(instance, "ikaraja").returning(int.class).takingNoParams().invoke();

        assertEquals("Kun kutsutaan:\nElokuva kuva = new Elokuva(\"Karvinen\", 7);\nint ikaraja = kuva.ikaraja();\nTulee muuttujassa ikaraja olla arvo 7.\nNyt arvo oli: " + out, 7, out);
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
            assertTrue("et tarvitse " + klassName + "-luokalle kuin nimen ja ikärajan. Poista ylimääräiset muuttujat", var < 3);
        }
    }

    private String kentta(String toString) {
        return toString.replace(klassName + ".", "");
    }
}
