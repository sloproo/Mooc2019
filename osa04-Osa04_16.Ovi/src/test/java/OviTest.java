
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

@Points("04-16")
public class OviTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Ovi";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void luokkaJulkinen() {
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus();
    }

    @Test
    public void onkoMetodia() throws Throwable {
        Reflex.ClassRef<Object> oviClass = Reflex.reflect(klassName);
        Object ovi = oviClass.constructor().takingNoParams().invoke();

        try {
            oviClass.method(ovi, "koputa")
                    .returningVoid()
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Virhe: " + ae + "\n eli tee luokalle " + klassName + " metodi public void koputa()");
        }

        assertTrue("Metodin koputa tulee olla public eli määritelty public void koputa()", oviClass.method(ovi, "koputa")
                .returningVoid()
                .takingNoParams().isPublic());
    }

    @Test
    public void testaaKoputus() throws Throwable {
        MockInOut mio = new MockInOut("");

        Reflex.ClassRef<Object> oviClass = Reflex.reflect(klassName);
        Object ovi = oviClass.constructor().takingNoParams().invoke();
        klass.method(ovi, "koputa").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        mio.close();

        assertTrue("Et tulostanut viestiä 'Who's there?' metodista koputa!", out.contains("Who's there?"));
    }

    @Test
    public void testaaKoputus2() throws Throwable {
        MockInOut mio = new MockInOut("");

        Reflex.ClassRef<Object> oviClass = Reflex.reflect(klassName);
        Object ovi = oviClass.constructor().takingNoParams().invoke();

        String out = mio.getOutput();

        mio.close();

        assertFalse("Tulostuksen ei tule tapahtua konstruktorissa!", out.contains("Who's there?"));
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
            assertTrue("et tarvitse " + klassName + "-luokalle kuin nimen muistavan oliomuuttujan, poista ylimääräiset", var < 1);
        }
    }

    private String kentta(String toString) {
        return toString.replace(klassName + ".", "");
    }
}
