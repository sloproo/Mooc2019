import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SanakirjaTest {

    private Class sanakirjaClass;
    private Constructor sanakirjaConstructor;
    private Method kaannaMethod;
    private Method lisaaMethod;
    private Method kaannoksetListanaMethod;
    private Method sanojenLukumaaraMethod;
    String klassName = "Sanakirja";
    Reflex.ClassRef<Object> klass;

    @Before
    public void setUp() {
        klass = Reflex.reflect(klassName);
        try {
            sanakirjaClass = ReflectionUtils.findClass("Sanakirja");
            sanakirjaConstructor = ReflectionUtils.requireConstructor(sanakirjaClass);
            kaannaMethod = ReflectionUtils.requireMethod(sanakirjaClass, "kaanna", String.class);
            lisaaMethod = ReflectionUtils.requireMethod(sanakirjaClass, "lisaa", String.class, String.class);
            sanojenLukumaaraMethod = ReflectionUtils.requireMethod(sanakirjaClass, "sanojenLukumaara");
            kaannoksetListanaMethod = ReflectionUtils.requireMethod(sanakirjaClass, "kaannoksetListana");

        } catch (Throwable t) {
        }
    }

    @Test
    @Points("06-14.1")
    public void luokkaJulkinen() {
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    @Points("06-14.1 06-14.2 06-14.3")
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus(klassName, 1, "käännökset tallentavan HashMap-tyyppisen oliomuuttujan");
    }

    @Test
    @Points("06-14.1 06-14.2 06-14.3")
    public void onHashMap() {
        Field[] kentat = ReflectionUtils.findClass(klassName).getDeclaredFields();
        assertTrue("Lisää luokalle " + klassName + " HashMap<String, String> -tyyppinen oliomuuttuja", kentat.length == 1);
        assertTrue("Luokalla " + klassName + " tulee olla HashMap<String, String> -tyyppinen oliomuuttuja", kentat[0].toString().contains("HashMap"));
    }

    @Test
    @Points("06-14.1")
    public void tyhjaKonstruktori() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "()", ctor.isPublic());
        String v = "virheen aiheutti koodi new Sanakirja();";
        ctor.withNiceError(v).invoke();
    }

    public Object luo() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        return ctor.invoke();
    }

    @Test
    @Points("06-14.1")
    public void lisaaMetodi() throws Throwable {
        String metodi = "lisaa";

        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "(String sana, String kaannos) ",
                klass.method(olio, metodi)
                .returningVoid().taking(String.class, String.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi Sanakirja s = new Sanakirja(); "
                + "s.lisaa(\"apina\",\"monkey\");";

        klass.method(olio, metodi)
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("apina", "monkey");
    }

    @Test
    @Points("06-14.1")
    public void kaannaMetodi() throws Throwable {
        String metodi = "kaanna";

        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public String " + metodi + "(String sana) ",
                klass.method(olio, metodi)
                .returning(String.class)
                .taking(String.class)
                .isPublic());

        String v = "\nVirheen aiheuttanut koodi Sanakirja s = new Sanakirja(); "
                + "s.kaanna(\"apina\");";

        klass.method(olio, metodi)
                .returning(String.class).taking(String.class).withNiceError(v).invoke("apina");
    }

    @Test
    @Points("06-14.1")
    public void sanakirjanHakuPalauttaaNullinJosEiLoydy() {
        Object sanakirja = luoSanakirjaSanoilla("jakkara", "avföring");
        if (sanakirja == null) {
            fail("Onnistuuhan sanakirjaan lisääminen?");
        }

        try {
            Assert.assertNotNull("Jos metodia kaanna kutsutaan tunnetulla sanalla, tulee sanakirjan palauttaa sanan käännös. Varmista myös että lisaa-metodi lisää sanan ja käännöksen oikein hajautustauluun.", kaannaMethod.invoke(sanakirja, "jakkara"));
        } catch (Throwable t) {
            fail(t.getMessage());
        }

        try {
            Assert.assertNull("Jos metodia kaanna kutsutaan tuntemattomalla sanalla, tulee sanakirjan palauttaa null.", kaannaMethod.invoke(sanakirja, "kuppi"));
        } catch (Throwable t) {
            fail(t.getMessage());
        }
    }

    @Test
    @Points("06-14.1")
    public void sanakirjanHakuPalauttaaHalutunMerkkijonon() {
        String[] sanat = new String[20];
        for (int i = 0; i < sanat.length; i++) {
            sanat[i] = "" + i;
        }

        Object sanakirja = luoSanakirjaSanoilla(sanat);
        if (sanakirja == null) {
            fail("Onnistuuhan sanakirjaan lisääminen?");
        }

        for (int i = 0; i < sanat.length; i += 2) {
            String sana = sanat[i];
            String kaannos = sanat[i + 1];

            try {
                Assert.assertEquals(kaannos, kaannaMethod.invoke(sanakirja, sana));
            } catch (Throwable t) {
                fail("Jos sanakirjaan lisätään 10 sanaa ja niiden käännökset, jokaisen sanan ja siihen sopivan käännöksen tulisi löytyä sanakirjasta.");
            }
        }
    }

    @Test
    @Points("06-14.2")
    public void metodiSanojenLukumaara() throws Throwable {
        String metodi = "sanojenLukumaara";

        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public int " + metodi + "() ",
                klass.method(olio, metodi)
                .returning(int.class)
                .takingNoParams()
                .isPublic());

        String v = "\nVirheen aiheuttanut koodi Sanakirja s = new Sanakirja(); "
                + "s.sanojenLukumaara();";

        klass.method(olio, metodi)
                .returning(int.class).takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("06-14.2")
    public void metodiSanojenLukumaaraOikeinKunKaksiSanaa() throws Exception {
        Object sanakirja = luoSanakirjaSanoilla("a", "b", "c", "d");

        int sanoja = (Integer) sanojenLukumaaraMethod.invoke(sanakirja);

        assertEquals("Metodisi sanojenLukumaara() ei toimi kun sanakirjassa on kaksi sanaa", 2, sanoja);

    }

    @Test
    @Points("06-14.2")
    public void metodiSanojenLukumaaraOikeinKunKolmeSanaa() throws Exception {
        Object sanakirja = luoSanakirjaSanoilla("a", "b", "c", "d", "e", "f");

        int sanoja = (Integer) sanojenLukumaaraMethod.invoke(sanakirja);

        assertEquals("Metodisi sanojenLukumaara() ei toimi kun sanakirjassa on kolme sanaa", 3, sanoja);

    }

    @Test
    @Points("06-14.3")
    public void metodiKaannoksetListana() throws Throwable {
        String metodi = "kaannoksetListana";

        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public ArrayList<String> " + metodi + "() ",
                klass.method(olio, metodi)
                .returning(ArrayList.class)
                .takingNoParams()
                .isPublic());

        String v = "\nVirheen aiheuttanut koodi Sanakirja s = new Sanakirja(); "
                + "s.kaannokestListana();";

        klass.method(olio, metodi)
                .returning(ArrayList.class).takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("06-14.3")
    public void metodiKaannoksetListanaToimiiOikein() {
        String[] sanat = new String[20];
        for (int i = 0; i < sanat.length; i++) {
            sanat[i] = "" + i;
        }

        Object sanakirja = luoSanakirjaSanoilla(sanat);
        if (sanakirja == null) {
            fail("Onnistuuhan sanakirjaan lisääminen?");
        }

        ArrayList<String> kaannoksetListana = null;
        try {
            kaannoksetListana = (ArrayList<String>) kaannoksetListanaMethod.invoke(sanakirja);
        } catch (Throwable t) {
            fail("Onhan metodilla kaannoksetListana määre public, ja palauttaahan se aina ArrayList-olion?");
        }

        assertFalse("Metodi kaannoksetListana palauttaa null:in. Metodin tulee aina palauttaa ArrayList<String>-olio!", kaannoksetListana == null);

        for (int i = 0; i < sanat.length; i += 2) {
            String sana = sanat[i];
            String kaannos = sanat[i + 1];

            boolean loytyi = false;
            for (String kaannospari : kaannoksetListana) {
                if (kaannospari.contains(sana) && kaannospari.contains(kaannos) && kaannospari.contains("=")) {
                    loytyi = true;
                    break;
                }
            }

            if (!loytyi) {
                fail("Tarkista että metodi kaannoksetListana palauttaa listan \"avain = arvo\" - pareja.");
            }
        }
    }

    /*
     *
     */
    private Object luoSanakirjaSanoilla(String... sanatJaKaannokset) {
        try {
            Object sanakirja = ReflectionUtils.invokeConstructor(sanakirjaConstructor);
            for (int i = 0; i < sanatJaKaannokset.length; i += 2) {
                lisaaMethod.invoke(sanakirja, sanatJaKaannokset[i], sanatJaKaannokset[i + 1]);
            }
            return sanakirja;
        } catch (Throwable ex) {
            return null;
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
