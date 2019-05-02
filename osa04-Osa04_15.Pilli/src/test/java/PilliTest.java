
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

@Points("04-15")
public class PilliTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Pilli";

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
        Reflex.MethodRef1<Object, Object, String> cc = klass.constructor().taking(String.class).withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "(String pillinAani)", cc.isPublic());
        cc.invoke("Kvaak");
    }

    @Test
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus();
    }

    @Test
    public void onkoMetodia() throws Throwable {
        Reflex.ClassRef<Object> pilliClass = Reflex.reflect(klassName);
        Object pilli = pilliClass.constructor().taking(String.class).invoke("Peef");

        try {
            pilliClass.method(pilli, "soi")
                    .returningVoid()
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Virhe: " + ae + "\n eli tee luokalle " + klassName + " metodi public void soi()");
        }

        assertTrue("Metodin soi tulee olla public eli määritelty public void soi()", pilliClass.method(pilli, "soi")
                .returningVoid()
                .takingNoParams().isPublic());
    }

    @Test
    public void testaaSoitto() throws Throwable {
        MockInOut mio = new MockInOut("");

        Object pilli = klass.constructor().taking(String.class).invoke("Peef");
        klass.method(pilli, "soi").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        mio.close();

        assertTrue("Et tulostanut ääntä metodista soi!", out.contains("Peef"));
    }
    
    @Test
    public void testaaSoitto2() throws Throwable {
        MockInOut mio = new MockInOut("");

        Object pilli = klass.constructor().taking(String.class).invoke("Bleergh");
        klass.method(pilli, "soi").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        mio.close();

        assertTrue("Et tulostanut ääntä metodista soi!", out.contains("Bleergh"));
    }

    @Test
    public void testaaSoitto3() throws Throwable {
        MockInOut mio = new MockInOut("");

        Object pilli = klass.constructor().taking(String.class).invoke("Bleergh");

        String out = mio.getOutput();

        mio.close();

        assertFalse("Tulostuksen ei tule tapahtua konstruktorissa!", out.contains("Bleergh"));
    }


    private void saniteettitarkastus() throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("et tarvitse \"stattisia muuttujia\", poista " + kentta(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("luokan kaikkien oliomuuttujien näkyvyyden tulee olla private, muuta " + kentta(field.toString()), field.toString().contains("private"));
        }

        if (kentat.length >= 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("et tarvitse " + klassName + "-luokalle kuin äänen muistavan oliomuuttujan, poista ylimääräiset", var < 2);
        }
    }

    private String kentta(String toString) {
        return toString.replace(klassName + ".", "");
    }
}
