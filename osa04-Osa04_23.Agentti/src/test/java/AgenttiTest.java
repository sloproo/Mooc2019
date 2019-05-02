
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

@Points("04-23")
public class AgenttiTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Agentti";

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
        Reflex.MethodRef2<Object, Object, String, String> cc = klass.constructor().taking(String.class, String.class).withNiceError();
        assertTrue("Luokalla " + klassName + " tulee olla julkinen konstruktori: public " + klassName + "(String etunimiAlussa, String sukunimiAlussa)", cc.isPublic());
        cc.invoke("James", "Pond");
    }

    @Test
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus();
    }

    @Test
    public void metodiTulostaPoistettu() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class, String.class).invoke("James", "Pond");

        try {
            klass.method(instance, "tulosta")
                    .returningVoid()
                    .takingNoParams().invoke();
            fail("Poista luokasta " + klassName + " metodi public void tulosta()");
        } catch (AssertionError ae) {
        }
    }

    @Test
    public void testaaKonstruktoriEiTulostaNimea() throws Throwable {
        MockInOut mio = new MockInOut("");

        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class, String.class).invoke("James", "Pond");

        String out = mio.getOutput();

        mio.close();

        assertTrue("Konstruktorin ei tule tulostaa mitään.", !out.contains("James") && !out.contains("Pond"));
    }

    @Test
    public void testaaToStringEiTulostaNimea() throws Throwable {
        MockInOut mio = new MockInOut("");

        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class, String.class).invoke("James", "Pond");
        instance.toString();

        String out = mio.getOutput();

        mio.close();

        assertTrue("Luokan toString-metodin ei tule tulostaa mitään.", !out.contains("James") && !out.contains("Pond"));
    }
    
    @Test
    public void testaaToStringPalauttaaMerkkijonon() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class, String.class).invoke("James", "Pond");
        String output = instance.toString();
        
        assertEquals("Luokan toString-metodin palauttaman merkkijonoesityksen tulee olla täsmälleen sama kuin aiemman tulostus-metodin tulostus.", "My name is Pond, James Pond", output);
    }
    
    @Test
    public void testaaToStringPalauttaaMerkkijonon2() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class, String.class).invoke("Grace", "Hopper");
        String output = instance.toString();
        
        assertEquals("Luokan toString-metodin palauttaman merkkijonoesityksen tulee olla täsmälleen sama kuin aiemman tulostus-metodin tulostus.", "My name is Hopper, Grace Hopper", output);
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
            assertTrue("et tarvitse " + klassName + "-luokalle kuin etunimen ja sukunimen. Poista ylimääräiset muuttujat", var < 3);
        }
    }

    private String kentta(String toString) {
        return toString.replace(klassName + ".", "");
    }
}
