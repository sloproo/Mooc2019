
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class A_VarastoTest {

    String klassName = "Varasto";
    Class c;
    Reflex.ClassRef<Object> klass;

    @Before
    public void setup() {
        klass = Reflex.reflect(klassName);
        try {
            c = ReflectionUtils.findClass(klassName);
        } catch (Throwable e) {
        }
    }

    @Test
    @Points("08-10.1")
    public void luokkaJulkinen() {
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    @Points("08-10.1")
    public void konstruktori() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "()", ctor.isPublic());
        String v = "virheen aiheutti koodi new Varasto();";
        ctor.withNiceError(v).invoke();
    }

    public Object luo() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        return ctor.invoke();
    }

    @Points("08-10.1")
    @Test
    public void onMap() {
        Field[] kentat = ReflectionUtils.findClass(klassName).getDeclaredFields();

        int map = 0;
        for (Field field : kentat) {
            assertFalse("Talleta " + klassName + ":n tavaroihen hinnat oliomuuttujaan Map<String, Integer> hinnat; \n"
                    + "eli muuta " + kentta(field.toString()) + " oikean tyyppiseksi", field.toString().contains("HashMap"));
            assertFalse("Et tarvitse luokalle " + klassName + " listatyyppistä oliomuuttujaa!, poista " + kentta(field.toString()), field.toString().matches("/.*java\\.util\\.([A-Za-zöäåÖÄÅ]*List).*/"));
            if (field.toString().contains("Map")) {
                map++;
            }
        }
        assertTrue("Talleta " + klassName + ":n tavaroiden hinnat oliomuuttujaan Map<String, Integer> hinnat;", map > 0 && map < 3);

    }

    @Test
    @Points("08-10.1")
    public void onMetodiLisaaTuote() throws Throwable {
        String metodi = "lisaaTuote";

        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "(String tuote, int hinta, int saldo)",
                klass.method(olio, metodi)
                .returningVoid().taking(String.class, int.class, int.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi Varasto v = new Varasto(); "
                + "v.lisaaTuote(\"kahvi\",2, 25);";

        klass.method(olio, metodi)
                .returningVoid().taking(String.class, int.class, int.class).withNiceError(v).invoke("kahvi", 2, 25);
    }

    private void lisaaTuote(Object olio, String tuote, int hinta, int lkm) throws Throwable {
        klass.method(olio, "lisaaTuote")
                .returningVoid().taking(String.class, int.class, int.class).invoke(tuote, hinta, lkm);
    }

    @Test
    @Points("08-10.1")
    public void onMetodiHinta() throws Throwable {
        String metodi = "hinta";
        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public int " + metodi + "(String tuote)",
                klass.method(olio, metodi)
                .returning(int.class).taking(String.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi Varasto v = new Varasto(); \n"
                + "v.lisaaTuote(\"kahvi\",2, 25);\n"
                + "v.lisaaTuote(\"maito\",3, 10);\n"
                + "v.hinta(\"kahvi\");";

        lisaaTuote(olio, "kahvi", 2, 25);
        lisaaTuote(olio, "maito", 3, 10);

        klass.method(olio, metodi)
                .returning(int.class).taking(String.class).withNiceError(v).invoke("kahvi");

    }

    @Points("08-10.1")
    @Test
    public void onnistuvaHintakysely() throws Throwable {
        String koodi = "v = new Varasto(); \n"
                + "v.lisaaTuote(\"maito\", 3, 10); \n"
                + "v.lisaaTuote(\"kahvi\", 5, 7);\n"
                + "v.hinta(\"maito\"); ";

        Object v = newVarasto();
        lisaa(v, "maito", 3, 10);
        lisaa(v, "kahvi", 5, 7);

        int t = hinta(v, "maito");
        assertEquals(koodi, 3, t);

        koodi += "v.hinta(\"kahvi\"); ";
        t = hinta(v, "kahvi");
        assertEquals(koodi, 5, t);
    }

    @Points("08-10.1")
    @Test
    public void eiPoikkeuksiaEpaonnistuvaHintakysely() throws Throwable {
        String metodi = "hinta";
        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public int " + metodi + "(String tuote)",
                klass.method(olio, metodi)
                .returning(int.class).taking(String.class).isPublic());

        String v = "\nMuista käsitellä tilanne jossa kysytään varastossa olemattoman tuotteen hintaa!"
                + "\nVirheen aiheuttanut koodi Varasto v = new Varasto(); \n"
                + "v.lisaaTuote(\"kahvi\",2, 25);\n"
                + "v.lisaaTuote(\"maito\",3, 10);\n"
                + "v.hinta(\"juusto\");";

        lisaaTuote(olio, "kahvi", 2, 25);
        lisaaTuote(olio, "maito", 3, 10);

        klass.method(olio, metodi)
                .returning(int.class).taking(String.class).withNiceError(v).invoke("juusto");
    }

    @Points("08-10.1")
    @Test
    public void epaonnistuvaHintakysely() throws Throwable {
        String koodi = "v = new Varasto(); \n"
                + "v.lisaaTuote(\"maito\", 3, 10); \n"
                + "v.lisaaTuote(\"kahvi\", 5, 7); \n"
                + "v.hinta(\"leipa\"); ";

        Object v = newVarasto();
        lisaa(v, "maito", 3, 10);
        lisaa(v, "kahvi", 5, 7);

        int t = hinta(v, "leipa");
        assertEquals("jos tuotetta ei ole varastossa, pitäisi hinnaksi ilmoittaa -99, " + koodi, -99, t);
    }

    /*
     *
     */
    @Points("08-10.2")
    @Test
    public void onMapit() {
        Field[] kentat = ReflectionUtils.findClass(klassName).getDeclaredFields();

        int map = 0;
        for (Field field : kentat) {
            assertFalse("Talleta " + klassName + ":n tavaroiden hinnat oliomuuttujaan Map<String, Integer> saldot; \n"
                    + "eli muuta " + kentta(field.toString()) + " oikean tyyppiseksi", field.toString().contains("HashMap"));
            assertFalse("Et tarvitse luokalle " + klassName + " listatyyppistä oliomuuttujaa!, poista " + kentta(field.toString()), field.toString().matches("/.*java\\.util\\.([A-Za-zöäåÖÄÅ]*List).*/"));
            if (field.toString().contains("Map")) {
                map++;
            }
        }
        assertTrue("Talleta " + klassName + ":n tavaroiden varastosaldot oliomuuttujaan Map<String, Integer> saldot;\n"
                + "Tarvitset siis luokallesi kaksi Map:ia, et enempää etkä vähempää!", map > 1 && map < 3);

    }

    @Test
    @Points("08-10.2")
    public void onMetodiSaldo() throws Throwable {
        String metodi = "saldo";
        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public int " + metodi + "(String tuote)",
                klass.method(olio, metodi)
                .returning(int.class).taking(String.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi Varasto v = new Varasto(); \n"
                + "v.lisaaTuote(\"kahvi\",2, 25);\n"
                + "v.lisaaTuote(\"maito\",3, 10);\n"
                + "v.saldo(\"kahvi\");";

        lisaaTuote(olio, "kahvi", 2, 25);
        lisaaTuote(olio, "maito", 3, 10);

        klass.method(olio, metodi)
                .returning(int.class).taking(String.class).withNiceError(v).invoke("kahvi");
    }

    @Points("08-10.2")
    @Test
    public void onnistuvaSaldokysely() throws Throwable {
        String koodi = "v = new Varasto(); v.lisaaTuote(\"maito\", 3, 10); v.lisaaTuote(\"kahvi\", 5, 7); "
                + "v.saldo(\"maito\"); ";

        Object v = newVarasto();
        lisaa(v, "maito", 3, 10);
        lisaa(v, "kahvi", 5, 7);

        int t = saldo(v, "maito");
        assertEquals(koodi, 10, t);

        koodi += "v.saldo(\"kahvi\"); ";
        t = saldo(v, "kahvi");
        assertEquals(koodi, 7, t);
    }

    @Test
    @Points("08-10.2")
    public void olemattomanSaldo() throws Throwable {
        String metodi = "saldo";
        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public int " + metodi + "(String tuote)",
                klass.method(olio, metodi)
                .returning(int.class).taking(String.class).isPublic());

        String v = "Muista käsitellä tilanne jossa kysytään varastossa olemattoman tuotteen saldoa!\n"
                + "Varasto v = new Varasto(); \n"
                + "v.lisaaTuote(\"kahvi\",2, 25);\n"
                + "v.lisaaTuote(\"maito\",3, 10);\n"
                + "v.saldo(\"juusto\");";

        lisaaTuote(olio, "kahvi", 2, 25);
        lisaaTuote(olio, "maito", 3, 10);

        assertEquals(v, 0, (int) klass.method(olio, metodi)
                .returning(int.class).taking(String.class).withNiceError("Virheen aiheuttanut koodi \n" + v).invoke("juusto"));
    }

    @Test
    @Points("08-10.2")
    public void onMetodiOta() throws Throwable {
        String metodi = "ota";
        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public boolean " + metodi + "(String tuote)",
                klass.method(olio, metodi)
                .returning(boolean.class).taking(String.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi Varasto v = new Varasto(); \n"
                + "v.lisaaTuote(\"kahvi\",2, 25);\n"
                + "v.lisaaTuote(\"maito\",3, 10);\n"
                + "v.ota(\"kahvi\");";

        lisaaTuote(olio, "kahvi", 2, 25);
        lisaaTuote(olio, "maito", 3, 10);

        klass.method(olio, metodi)
                .returning(boolean.class).taking(String.class).withNiceError(v).invoke("kahvi");

        v = "\nVirheen aiheuttanut koodi Varasto v = new Varasto(); \n"
                + "v.lisaaTuote(\"kahvi\",2, 25);\n"
                + "v.lisaaTuote(\"maito\",3, 10);\n"
                + "v.ota(\"juusto\");";

        klass.method(olio, metodi)
                .returning(boolean.class).taking(String.class).withNiceError(v).invoke("juusto");
    }

    @Points("08-10.2")
    @Test
    public void otaVahentaa() throws Throwable {
        String koodi = "v = new Varasto(); \n"
                + "v.lisaaTuote(\"maito\", 3, 10); \n"
                + "v.lisaaTuote(\"kahvi\", 5, 7); \n"
                + "v.ota(\"kahvi\");\n";

        Object v = newVarasto();
        lisaa(v, "maito", 3, 10);
        lisaa(v, "kahvi", 5, 7);

        boolean b = ota(v, "kahvi");

        assertEquals(koodi, true, b);

        koodi += "v.saldo(\"kahvi\"); ";

        int t = saldo(v, "kahvi");
        assertEquals(koodi, 6, t);
    }

    @Points("08-10.2")
    @Test
    public void otaEiOnnistuJosLoppuu() throws Throwable {
        String koodi = "v = new Varasto(); \n"
                + "v.lisaaTuote(\"maito\", 3, 10); \n"
                + "v.lisaaTuote(\"kahvi\", 5, 1); \n"
                + "v.ota(\"maito\");\n"
                + "v.ota(\"maito\");\n";

        Object v = newVarasto();
        lisaa(v, "maito", 3, 10);
        lisaa(v, "kahvi", 5, 1);

        ota(v, "kahvi");
        boolean b = ota(v, "kahvi");

        assertEquals(koodi, false, b);

        koodi += "v.saldo(\"kahvi\"); ";

        int t = saldo(v, "kahvi");
        assertEquals(koodi, 0, t);
    }

    @Points("08-10.2")
    @Test
    public void onolemattomanOtaPalauttaaFalse() throws Throwable {
        String koodi = "v = new Varasto(); \n"
                + "v.lisaaTuote(\"maito\", 3, 10); \n"
                + "v.lisaaTuote(\"kahvi\", 5, 7);\n"
                + "v.ota(\"leipa\");";

        Object v = newVarasto();
        lisaa(v, "maito", 3, 10);
        lisaa(v, "kahvi", 5, 7);

        boolean b = ota(v, "leipa");
        assertFalse(koodi, b);
    }

    /*
     *
     */
    @Test
    @Points("08-10.3")
    public void onMetodiTuotteet() throws Throwable {
        String metodi = "tuotteet";
        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public Set<String>  " + metodi + "()",
                klass.method(olio, metodi)
                .returning(Set.class).takingNoParams().isPublic());

        String v = "\nVirheen aiheuttanut koodi Varasto v = new Varasto(); \n"
                + "v.lisaaTuote(\"kahvi\",2, 25);\n"
                + "v.lisaaTuote(\"maito\",3, 10);\n"
                + "v.tuotteet();";

        lisaaTuote(olio, "kahvi", 2, 25);
        lisaaTuote(olio, "maito", 3, 10);

        klass.method(olio, metodi)
                .returning(Set.class).takingNoParams().withNiceError(v).invoke();
    }

    @Points("08-10.3")
    @Test
    public void tuotteetToimii() throws Throwable {
        String koodi = "v = new Varasto(); \n"
                + "v.lisaaTuote(\"maito\", 3, 10); \n"
                + "v.lisaaTuote(\"kahvi\", 5, 7); \n"
                + "v.lisaaTuote(\"sokeri\", 2, 25);\n"
                + "v.tuotteet();";

        Object v = newVarasto();
        lisaa(v, "maito", 3, 10);
        lisaa(v, "kahvi", 5, 7);
        lisaa(v, "sokeri", 2, 25);
        Set<String> t = tuotteet(v);

        assertFalse(koodi + " palauttu joukon joka on null", t == null);

        assertEquals(koodi + " palauttu joukon +" + t + " joukon koko ", 3, t.size());
        assertEquals(koodi + " palauttu joukon +" + t + " \"maito\" sisältyy joukkoon ", true, t.contains("maito"));
        assertEquals(koodi + " palauttu joukon +" + t + " \"kahvi\" sisältyy joukkoon ", true, t.contains("kahvi"));
        assertEquals(koodi + " palauttu joukon +" + t + " \"sokeri\" sisältyy joukkoon ", true, t.contains("sokeri"));
    }

    @Test
    @Points("08-10.1 08-10.2 08-10.3")
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus(klassName, 2, "saldot ja hinnat tallettavat oliomuuttujat");
    }

    /*
     *
     */
    private int hinta(Object olio, String tuote) throws Throwable {
        try {
            Method metodi = ReflectionUtils.requireMethod(c, "hinta", String.class);
            return ReflectionUtils.invokeMethod(int.class, metodi, olio, tuote);
        } catch (Throwable t) {
            throw t;
        }
    }

    private int saldo(Object olio, String tuote) throws Throwable {
        try {
            Method metodi = ReflectionUtils.requireMethod(c, "saldo", String.class);
            return ReflectionUtils.invokeMethod(int.class, metodi, olio, tuote);
        } catch (Throwable t) {
            throw t;
        }
    }

    private boolean ota(Object olio, String tuote) throws Throwable {
        try {
            Method metodi = ReflectionUtils.requireMethod(c, "ota", String.class);
            return ReflectionUtils.invokeMethod(boolean.class, metodi, olio, tuote);
        } catch (Throwable t) {
            throw t;
        }
    }

    private Set<String> tuotteet(Object olio) throws Throwable {
        try {
            Method metodi = ReflectionUtils.requireMethod(c, "tuotteet");
            Set<String> vast = (Set<String>) metodi.invoke(olio);
            return vast;
        } catch (Throwable t) {
            throw t;
        }
    }

    private void lisaa(Object olio, String tuote, int hinta, int saldo) throws Throwable {
        try {
            Method metodi = ReflectionUtils.requireMethod(c, "lisaaTuote", String.class, int.class, int.class);
            List<String> l = null;

            ReflectionUtils.invokeMethod(void.class, metodi, olio, tuote, hinta, saldo);
        } catch (Throwable t) {
            throw t;
        }
    }

    private Object newVarasto() throws Throwable {
        try {
            c = ReflectionUtils.findClass(klassName);
            return ReflectionUtils.invokeConstructor(c.getConstructor());
        } catch (Throwable t) {
            fail("tee luokalle " + klassName + " parametriton julkinen konstruktori");
        }
        return null;
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

    private String kentta(String toString) {
        return toString.replace(klassName + ".", "");
    }
}
