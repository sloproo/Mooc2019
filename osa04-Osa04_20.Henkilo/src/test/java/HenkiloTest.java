
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

@Points("04-20")
public class HenkiloTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Henkilo";

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
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "(String nimiAlussa)", cc.isPublic());
        cc.invoke("Ada");
    }

    @Test
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus();
    }

    @Test
    public void testaaTulostaHenkilo() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class).invoke("Grace");

        try {
            klass.method(instance, "tulostaHenkilo")
                    .returningVoid()
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Virhe: " + ae + "\n eli tee luokalle " + klassName + " metodi public void tulostaHenkilo()");
        }

        assertTrue("Metodin tulostaHenkilo tulee olla public eli määritelty public void tulostaHenkilo()", klass.method(instance, "tulostaHenkilo")
                .returningVoid()
                .takingNoParams().isPublic());
    }

    @Test
    public void testaaTulostaHenkiloTulostus() throws Throwable {
        MockInOut mio = new MockInOut("");

        Object instance = klass.constructor().taking(String.class).invoke("Omena");
        klass.method(instance, "tulostaHenkilo").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        mio.close();

        assertTrue("Et tulostanut henkilön nimeä metodista tulostaHenkilo!", out.contains("Omena"));
        assertTrue("Kun henkilö luodaan, iän pitäisi olla alussa 0. Ikä ei ole oikein tai et tulostanut ikää metodista tulostaHenkilo!", out.contains("0"));
    }

    @Test
    public void testaaTulostaHenkiloTulostus2() throws Throwable {
        MockInOut mio = new MockInOut("");

        Object instance = klass.constructor().taking(String.class).invoke("Omena");
//        klass.method(instance, "tulostaHenkilo").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        mio.close();

        assertFalse("Konstruktorissa ei tule tulostaa henkilön nimeä!", out.contains("Omena"));
        assertFalse("Konstruktorissa ei tule tulostaa ikää!", out.contains("0"));
    }

    @Test
    public void testaaTulostaHenkiloTulostus3() throws Throwable {
        MockInOut mio = new MockInOut("");

        Object instance = klass.constructor().taking(String.class).invoke("Pihla");
        klass.method(instance, "tulostaHenkilo").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        mio.close();

        assertTrue("Et tulostanut henkilön nimeä metodista tulostaHenkilo!", out.contains("Pihla"));
        assertTrue("Kun henkilö luodaan, iän pitäisi olla alussa 0. Ikä ei ole oikein tai et tulostanut ikää metodista tulostaHenkilo!", out.contains("0"));
    }

    @Test
    public void testaaPalautaIka() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class).invoke("Grace");

        try {
            klass.method(instance, "palautaIka").returning(int.class)
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Virhe: " + ae + "\n eli tee luokalle " + klassName + " metodi public int palautaIka()");
        }

        assertTrue("Metodin palautaIka tulee olla public eli määritelty public int palautaIka()", klass.method(instance, "palautaIka")
                .returning(int.class)
                .takingNoParams().isPublic());

        int ika = klass.method(instance, "palautaIka").returning(int.class).takingNoParams().invoke();

        assertEquals("Uuden henkilön iän pitäisi olla alussa 0.", 0, ika);
    }

    @Test
    public void testaaVanhene() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class).invoke("Grace");

        try {
            klass.method(instance, "vanhene")
                    .returningVoid()
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Virhe: " + ae + "\n eli tee luokalle " + klassName + " metodi public void vanhene()");
        }

        assertTrue("Metodin vanhene tulee olla public eli määritelty public void vanhene()", klass.method(instance, "vanhene")
                .returningVoid()
                .takingNoParams().isPublic());

        try {
            klass.method(instance, "palautaIka").returning(int.class).takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Virhe: " + ae + "\n eli tee luokalle " + klassName + " metodi public int palautaIka()");
        }

        int ika = klass.method(instance, "palautaIka").returning(int.class).takingNoParams().invoke();

        assertEquals("Henkilön palautetun iän pitäisi olla 1 kun metodia vanhene on kutsuttu kerran.", 1, ika);

        for (int i = 0; i < 10; i++) {
            try {
                klass.method(instance, "vanhene")
                        .returningVoid()
                        .takingNoParams().invoke();
            } catch (AssertionError ae) {
                fail("Virhe: " + ae + "\n eli tee luokalle " + klassName + " metodi public void vanhene()");
            }
        }

        ika = klass.method(instance, "palautaIka").returning(int.class).takingNoParams().invoke();

        assertEquals("Henkilön palautetun iän pitäisi olla 11 kun metodia vanhene on kutsuttu 11 kertaa.", 11, ika);
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
            assertTrue("et tarvitse " + klassName + "-luokalle kuin nimen ja iän. Poista ylimääräiset muuttujat", var < 3);
        }
    }

    private String kentta(String toString) {
        return toString.replace(klassName + ".", "");
    }
}
