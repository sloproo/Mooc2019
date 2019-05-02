
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

@Points("04-19")
public class VelkaTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Velka";

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
        Reflex.MethodRef2<Object, Object, Double, Double> cc = klass.constructor().taking(double.class, double.class).withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "(double saldoAlussa, double korkokerroinAlussa)", cc.isPublic());
        cc.invoke(1000.0, 1.05);
    }

    @Test
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus();
    }

    @Test
    public void onkoMetodiaTulostaSaldo() throws Throwable {
        Reflex.ClassRef<Object> velkaClass = Reflex.reflect(klassName);
        Object velka = velkaClass.constructor().taking(double.class, double.class).invoke(1000.0, 2.0);

        try {
            velkaClass.method(velka, "tulostaSaldo")
                    .returningVoid()
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Virhe: " + ae + "\n eli tee luokalle " + klassName + " metodi public void tulostaSaldo()");
        }

        assertTrue("Metodin tulostaSaldo tulee olla public eli määritelty public void tulostaSaldo()", velkaClass.method(velka, "tulostaSaldo")
                .returningVoid()
                .takingNoParams().isPublic());
    }

    @Test
    public void testaaTulostus() throws Throwable {
        MockInOut mio = new MockInOut("");

        Object velka = klass.constructor().taking(double.class, double.class).invoke(1000.0, 1.0);
        klass.method(velka, "tulostaSaldo").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        mio.close();

        assertTrue("Et tulostanut saldoa metodista tulostaSaldo! Tulostus oli:\n" + out + "\nKun tehtiin:\nVelka v new Velka(1000.0, 1.0);\nv.tulostaSaldo();", out.contains("1000") && !out.contains("1."));
    }

    @Test
    public void testaaTulostus2() throws Throwable {
        MockInOut mio = new MockInOut("");

        Object velka = klass.constructor().taking(double.class, double.class).invoke(1500.0, 2.0);
        klass.method(velka, "tulostaSaldo").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        mio.close();

        assertTrue("Et tulostanut saldoa metodista tulostaSaldo! Tulostus oli:\n" + out + "\nKun tehtiin:\nVelka v new Velka(1500.0, 2.0);\nv.tulostaSaldo();", out.contains("1500") && !out.contains("2."));
    }

    @Test
    public void testaaTulostus3() throws Throwable {
        MockInOut mio = new MockInOut("");

        Object velka = klass.constructor().taking(double.class, double.class).invoke(1500.0, 2.0);

        String out = mio.getOutput();

        mio.close();

        assertFalse("Älä tulosta saldoa konstruktorissa! Tulostus oli:\n" + out + "\nKun tehtiin:\nVelka v new Velka(1500.0, 2.0);", out.contains("1500"));
    }

    @Test
    public void onkoMetodiaOdotaVuosi() throws Throwable {
        Reflex.ClassRef<Object> velkaClass = Reflex.reflect(klassName);
        Object velka = velkaClass.constructor().taking(double.class, double.class).invoke(1000.0, 2.0);

        try {
            velkaClass.method(velka, "odotaVuosi")
                    .returningVoid()
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Virhe: " + ae + "\n eli tee luokalle " + klassName + " metodi public void odotaVuosi()");
        }

        assertTrue("Metodin odotaVuosi tulee olla public eli määritelty public void odotaVuosi()", velkaClass.method(velka, "odotaVuosi")
                .returningVoid()
                .takingNoParams().isPublic());
    }

    @Test
    public void testaaOdotaVuosiEiTulostaMitaan() throws Throwable {
        MockInOut mio = new MockInOut("");

        Object velka = klass.constructor().taking(double.class, double.class).invoke(1500.0, 2.0);
        klass.method(velka, "odotaVuosi").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        mio.close();

        assertFalse("Älä tulosta saldoa metodissa odotaVuosi!", out.contains("1500"));
        assertFalse("Älä tulosta saldoa metodissa odotaVuosi!", out.contains("3000"));
        assertFalse("Älä tulosta mitään metodissa odotaVuosi!", out.contains("2"));
    }

    @Test
    public void testaaOdotaVuosiMuuttaaSaldoa() throws Throwable {
        MockInOut mio = new MockInOut("");

        Object velka = klass.constructor().taking(double.class, double.class).invoke(1500.0, 2.0);
        klass.method(velka, "odotaVuosi").returningVoid().takingNoParams().invoke();
        klass.method(velka, "tulostaSaldo").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        mio.close();

        double odotettu = 1500.0 * 2.0;
        assertTrue("Saldon pitäisi kasvaa kun odotetaan vuosi! Odotettiin " + odotettu + " kun suoritettiin:\nVelka velka = new Velka(1500.0, 2.0);\nvelka.odotaVuosi();\nvelka.tulostaSaldo();\nTulostus oli:\n" + out, out.contains("" + odotettu));
    }

    @Test
    public void testaaOdotaVuosiMuuttaaSaldoa2() throws Throwable {
        MockInOut mio = new MockInOut("");

        Object velka = klass.constructor().taking(double.class, double.class).invoke(1500.0, 2.0);
        klass.method(velka, "odotaVuosi").returningVoid().takingNoParams().invoke();
        klass.method(velka, "odotaVuosi").returningVoid().takingNoParams().invoke();
        klass.method(velka, "tulostaSaldo").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        mio.close();

        double odotettu = 1500.0 * 2.0 * 2.0;
        assertTrue("Saldon pitäisi kasvaa kun odotetaan vuosi! Odotettiin " + odotettu + " kun suoritettiin:\nVelka velka = new Velka(1500.0, 2.0);\nvelka.odotaVuosi();\nvelka.odotaVuosi();\nvelka.tulostaSaldo();\nTulostus oli:\n" + out, out.contains("" + odotettu));
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
            assertTrue("Tarvitset luokalle " + klassName + " vain kaksi oliomuuttujaa. Poista ylimääräiset.", var < 3);
        }
    }

    private String kentta(String toString) {
        return toString.replace(klassName + ".", "");
    }
}
