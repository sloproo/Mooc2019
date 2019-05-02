
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

@Points("04-17")
public class TuoteTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Tuote";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect("Tuote");
    }

    @Test
    public void luokkaJulkinen() {
        assertTrue("Luokan Tuote pitää olla julkinen, eli se tulee määritellä\npublic class Tuote {...\n}", klass.isPublic());
    }

    @Test
    public void testaaKonstruktori() throws Throwable {
        Reflex.MethodRef3<Object, Object, String, Double, Integer> cc = klass.constructor().taking(String.class, double.class, int.class).withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "(String nimiAlussa, double hintaAlussa, int maaraAlussa)", cc.isPublic());
        cc.invoke("Banaani", 1.1, 13);
    }

    @Test
    public void eiYlimaaraisiaMuuttujia(){
        saniteettitarkastus();
    }
    
    @Test
    public void onkoMetodia() throws Throwable {
        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect("Tuote");
        Object tuote = tuoteClass.constructor().taking(String.class, double.class, int.class).invoke("Banaani", 1.1, 13);

        try {
            tuoteClass.method(tuote, "tulostaTuote")
                    .returningVoid()
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Virhe: " + ae + "\n eli tee luokalle Tuote metodi public void tulostaTuote()");
        }

        assertTrue("Metodin tulostaTuote tulee olla public eli määritelty public void tulostaTuote()", tuoteClass.method(tuote, "tulostaTuote")
                .returningVoid()
                .takingNoParams().isPublic());
    }

    @Test
    public void testaaTulostus() throws Throwable {
        MockInOut mio = new MockInOut("");

        Object tuote = klass.constructor().taking(String.class, double.class, int.class).invoke("Omena", 0.1, 4);
        klass.method(tuote, "tulostaTuote").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        mio.close();

        assertTrue("Et tulostanut tuotteen nimeä metodista tulostaTuote!", out.contains("Omena"));
        assertTrue("Et tulostanut tuotteen hintaa metodista tulostaTuote!", out.contains("0.1"));
        assertTrue("Et tulostanut tuotteen lukumäärää metodista tulostaTuote!", out.contains("4"));
    }

    @Test
    public void testaaTulostus2() throws Throwable {

        MockInOut mio = new MockInOut("");

        Object tuote = klass.constructor().taking(String.class, double.class, int.class).invoke("Muna", 9000.0, 1);
        klass.method(tuote, "tulostaTuote").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        assertTrue("Et tulostanut tuotteen nimeä metodista tulostaTuote!", out.contains("Muna"));
        assertTrue("Et tulostanut tuotteen hintaa metodista tulostaTuote!", out.contains("9000"));
        assertTrue("Et tulostanut tuotteen lukumäärää metodista tulostaTuote!", out.contains("1"));
    }
    
    String luokanNimi = "Tuote";

    private void saniteettitarkastus() throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(luokanNimi).getDeclaredFields();

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
            assertTrue("et tarvitse " + luokanNimi + "-luokalle kuin tuotteen nimen, hinnan ja lukumäärän muistavat oliomuuttujat, poista ylimääräiset", var < 4);
        }
    }

    private String kentta(String toString) {
        return toString.replace(luokanNimi + ".", "");
    }
}
