
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

@Points("04-25")
public class KertojaTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Kertoja";

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
        Reflex.MethodRef1<Object, Object, Integer> ctor = klass.constructor().taking(int.class).withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "(int arvoAlussa)", ctor.isPublic());
        ctor.invoke(4);
    }

    @Test
    public void onkoMetodia() throws Throwable {
        String metodi = "kerro";

        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassName);
        Object olio = tuoteClass.constructor().taking(int.class).invoke(4);

        assertTrue("tee luokalle " + klassName + " metodi public int " + metodi + "(int toinenLuku) ", tuoteClass.method(olio, metodi)
                .returning(int.class).taking(int.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi Kertoja k = new Kertoja(4); k.kerro(2);";

        tuoteClass.method(olio, metodi)
                .returning(int.class).taking(int.class).withNiceError(v).invoke(2);

    }

    @Test
    public void testaaMetodi() throws Throwable {
        String metodi = "kerro";

        Object o1 = klass.constructor().taking(int.class).invoke(4);
        Object o2 = klass.constructor().taking(int.class).invoke(1);
        Object o3 = klass.constructor().taking(int.class).invoke(7);

        int out = klass.method(o1, metodi).returning(int.class).taking(int.class).invoke(2);
        int out2 = klass.method(o2, metodi).returning(int.class).taking(int.class).invoke(3);
        int out3 = klass.method(o3, metodi).returning(int.class).taking(int.class).invoke(8);

        assertEquals("Palautit väärän arvon kun Kertoja(4):lle kutsuttiin kerro(2)", 8, out);
        assertEquals("Palautit väärän arvon kun Kertoja(1):lle kutsuttiin kerro(3)", 3, out2);
        assertEquals("Palautit väärän arvon kun Kertoja(7):lle kutsuttiin kerro(8)", 56, out3);
    }
    
    @Test
    public void eiYlimaaraisiaMuuttujia(){
        saniteettitarkastus(1, "kerrottavan luvun muistavan oliomuuttujan");
    }

    private void saniteettitarkastus(int n, String m) throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("et tarvitse \"stattisia muuttujia\", poista " + kentta(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("luokan kaikkien oliomuuttujien näkyvyyden tulee olla private, muuta löytyi: " + kentta(field.toString()), field.toString().contains("private"));
        }

        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("et tarvitse " + klassName + "-luokalle kuin "+m+", poista ylimääräiset", var <= n);
        }
    }

    private String kentta(String toString) {
        return toString.replace(klassName + ".", "");
    }
}
