
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

@Points("05-04")
public class HarjoitusapuriTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Harjoitusapuri";

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
        Reflex.MethodRef2<Object, Object, Integer, Integer> cc = klass.constructor().taking(int.class, int.class).withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "(int ika, int leposyke)", cc.isPublic());
        cc.invoke(2, 5);
    }

    @Test
    public void testaaMaksimisyke() throws Throwable {
        assertTrue("Luokalla " + klass + " tulee olla metodi public double tavoitesyke(double prosenttiaMaksimista). Nty metodia ei löytynyt.", klass.method("tavoitesyke").returning(double.class).taking(double.class).exists());
    }

    @Test
    public void testaaMaksimisyke1() throws Throwable {
        testaaKonstruktori();
        testaaMaksimisyke();

        Reflex.MethodRef2<Object, Object, Integer, Integer> cc = klass.constructor().taking(int.class, int.class).withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "(int ika, int leposyke)", cc.isPublic());
        Object apuri = cc.invoke(30, 60);
        double tavoite = klass.method("tavoitesyke").returning(double.class).taking(double.class).invokeOn(apuri, 0.5);

        assertEquals("Kun ikä on 30 ja leposyke 60, tavoitesykkeen tulee olla noin 122.485 kun tavoitteena on puolet maksimista.", 122.485, tavoite, 0.1);

        tavoite = klass.method("tavoitesyke").returning(double.class).taking(double.class).invokeOn(apuri, 0.7);

        assertEquals("Kun ikä on 30 ja leposyke 60, tavoitesykkeen tulee olla noin 147.479 kun tavoitteena on 70% maksimista.", 147.479, tavoite, 0.1);
    }

    @Test
    public void testaaMaksimisyke2() throws Throwable {
        testaaKonstruktori();
        testaaMaksimisyke();

        Reflex.MethodRef2<Object, Object, Integer, Integer> cc = klass.constructor().taking(int.class, int.class).withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "(int ika, int leposyke)", cc.isPublic());
        Object apuri = cc.invoke(60, 70);
        double tavoite = klass.method("tavoitesyke").returning(double.class).taking(double.class).invokeOn(apuri, 0.6);

        assertEquals("Kun ikä on 60 ja leposyke 70, tavoitesykkeen tulee olla noin 126.184 kun tavoitteena on 60% maksimista.", 126.184, tavoite, 0.1);

        tavoite = klass.method("tavoitesyke").returning(double.class).taking(double.class).invokeOn(apuri, 0.8);

        assertEquals("Kun ikä on 60 ja leposyke 70, tavoitesykkeen tulee olla noin 144.912 kun tavoitteena on 80% maksimista.", 144.912, tavoite, 0.1);
    }

    @Test
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus();
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

            assertTrue("et tarvitse " + klassName + "-luokalle kuin iän ja leposykkeen. Poista ylimääräiset muuttujat", var < 3);
        }
    }

    private String kentta(String toString) {
        return toString.replace(klassName + ".", "");
    }
}
