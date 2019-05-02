import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

public class B_OstosTest {

    String klassName = "Ostos";
    Reflex.ClassRef<Object> klass;
    Class c;

    @Before
    public void setup() {
        klass = Reflex.reflect(klassName);
        try {
            c = ReflectionUtils.findClass(klassName);
        } catch (Throwable e) {
        }
    }

    @Test
    @Points("08-10.4")
    public void luokkaJulkinen() {
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    @Points("08-10.4")
    public void onKonstruktori() throws Throwable {
        Reflex.MethodRef3<Object, Object, String, Integer, Integer> ctor = klass.constructor().taking(String.class, int.class, int.class).withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "(String tuote, int kpl, int yksikkohinta)", ctor.isPublic());
        String v = "virheen aiheutti koodi new Ostos(\"maito\",2,4);";
        ctor.withNiceError(v).invoke("maito", 2, 4);
    }

    public Object luo(String tuote, int hinta, int lkm) throws Throwable {
        Reflex.MethodRef3<Object, Object, String, Integer, Integer> ctor = klass.constructor().taking(String.class, int.class, int.class).withNiceError();
        return ctor.invoke(tuote, hinta, lkm);
    }

    @Test
    @Points("08-10.4")
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus(klassName, 3, "tuotteen nimen, hinnan ja kappalemäärän tallettavat oliomuuttujat");
    }

    @Test
    @Points("08-10.4")
    public void metodiHinta() throws Throwable {
        String metodi = "hinta";

        Object olio = luo("maito", 2, 3);

        assertTrue("tee luokalle " + klassName + " metodi public int " + metodi + "()",
                klass.method(olio, metodi)
                .returning(int.class).takingNoParams().isPublic());

        String v = "Ostos ostos = new Ostos(\"maito\",2,3);\n"
                + "ostos.hinta();";

        assertEquals(v, 6, (int)klass.method(olio, metodi)
                .returning(int.class).takingNoParams().withNiceError("virheen aiheutti koodi \n"+v).invoke());
    }

    @Test
    @Points("08-10.4")
    public void hintaLasketaanOikein() throws Throwable {
        String k = "o = new Ostos(\"leipa\", 1, 5); o.hinta()";

        Object ostos = newOstos("leipa", 1, 5);
        int hinta = hinta(ostos);
        assertEquals(k, 5, hinta);
    }

    /*
     *
     */

    @Test
    @Points("08-10.4")
    public void onMetodiKasvataMaaraa() throws Throwable {
        String metodi = "kasvataMaaraa";

        Object olio = luo("maito", 2, 3);

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "()",
                klass.method(olio, metodi)
                .returningVoid().takingNoParams().isPublic());

        String v = "Ostos ostos = new Ostos(\"maito\",1,2);\n"
                + "ostos.kasvataMaaraa();";

        klass.method(olio, metodi)
                .returningVoid().takingNoParams().withNiceError("virheen aiheutti koodi \n"+v).invoke();

        klass.method(olio, metodi)
                .returningVoid().takingNoParams().withNiceError(v+"\nostos.hinta();").invoke();
    }

    @Test
    @Points("08-10.4")
    public void maaraKasvaa() throws Throwable {
        String k = "o = new Ostos(\"leipa\", 1, 5); o.kasvataMaaraa(), o.hinta()";

        Object ostos = newOstos("leipa", 1, 5);
        kasvataMaaraa(ostos);
        int hinta = hinta(ostos);
        assertEquals(k, 10, hinta);
    }

    @Test
    @Points("08-10.4")
    public void toStringTehty() {
        Object ostos = newOstos("leipa", 1, 5);
        assertFalse("toteuta luokalle Ostos metodi public String toString()", ostos.toString().contains("@"));
    }

    @Test
    @Points("08-10.4")
    public void toStringOikein() throws Throwable {
        String k = "o = new Ostos(\"maito\", 2, 4); System.out.println( o )";

        Object ostos = newOstos("maito", 2, 4);
        assertEquals("tarkasta että toString:in palauttama merkkijono on muotoa \"tuote: kpl\""
                + "\n" + k, "maito: 2", ostos.toString());
    }

    @Test
    @Points("08-10.4")
    public void toStringOikein2() throws Throwable {
        String k = "o = new Ostos(\"juusto\", 17, 3); System.out.println( o )";

        Object ostos = newOstos("juusto", 17, 3);
        assertEquals("tarkasta että toString:in palauttama merkkijono on muotoa \"tuote: kpl\""
                + "\n" + k, "juusto: 17", ostos.toString());
    }

    /*
     *
     */
    private Object newOstos(String tuote, int kpl, int hinta) {
        try {
            c = ReflectionUtils.findClass(klassName);

            Constructor[] cc = c.getConstructors();
            return cc[0].newInstance(tuote, kpl, hinta);
        } catch (Throwable t) {
            fail("tee luokalle " + klassName + " konstruktori Ostos(String tuote, int kpl, int yksikkohinta)\n"
                    + "muita konstruktoreja ei tarvita!");
        }
        return null;
    }

    private int hinta(Object olio) throws Throwable {
        try {
            Method metodi = ReflectionUtils.requireMethod(c, "hinta");
            return ReflectionUtils.invokeMethod(int.class, metodi, olio);
        } catch (Throwable t) {
            throw t;
        }
    }

    private void kasvataMaaraa(Object olio) throws Throwable {
        try {
            Method metodi = ReflectionUtils.requireMethod(c, "kasvataMaaraa");
            ReflectionUtils.invokeMethod(void.class, metodi, olio);
        } catch (Throwable t) {
            throw t;
        }
    }

    private void saniteettitarkastus(String klassName, int n, String m) throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("et tarvitse \"stattisia muuttujia\", poista luokalta " + klassName + " muuttuja " + kentta(field.toString(), klassName), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("luokan kaikkien oliomuuttujien näkyvyyden tulee olla private, muuta luokalta " + klassName + " löytyi: " + kentta(field.toString(), klassName), field.toString().contains("private"));
        }

        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("et tarvitse " + klassName + "-luokalle kuin " + m + ", poista ylimääräiset", var <= n);
        }
    }

    private String kentta(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }

}
