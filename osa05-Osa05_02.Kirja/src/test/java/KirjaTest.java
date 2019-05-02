
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

@Points("05-02")
public class KirjaTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Kirja";

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
        Reflex.MethodRef3<Object, Object, String, String, Integer> cc = klass.constructor().taking(String.class, String.class, int.class).withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "(String kirjailija, String nimi, int sivuja)", cc.isPublic());
        cc.invoke("Marie Kondo", "KonMari", 222);
    }

    @Test
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus();
    }

    @Test
    public void onkoMetodit() throws Throwable {
        Reflex.MethodRef3<Object, Object, String, String, Integer> kirjaClass = klass.constructor().taking(String.class, String.class, int.class).withNiceError();
        Object kirja = kirjaClass.invoke("Marie Kondo", "KonMari", 222);

        try {
            klass.method(kirja, "getKirjailija")
                    .returning(String.class)
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Virhe: " + ae + "\n eli tee luokalle " + klassName + " metodi public String getKirjailija()");
        }

        assertTrue("Metodin getKirjailija tulee olla julkinen eli määritelty public void getKirjailija()", klass.method(kirja, "getKirjailija")
                .returning(String.class)
                .takingNoParams().isPublic());

        try {
            klass.method(kirja, "getNimi")
                    .returning(String.class)
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Virhe: " + ae + "\n eli tee luokalle " + klassName + " metodi public String getNimi()");
        }

        assertTrue("Metodin getNimi tulee olla julkinen eli määritelty public void getNimi()", klass.method(kirja, "getNimi")
                .returning(String.class)
                .takingNoParams().isPublic());

        try {
            klass.method(kirja, "getSivuja")
                    .returning(int.class)
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Virhe: " + ae + "\n eli tee luokalle " + klassName + " metodi public int getSivuja()");
        }

        assertTrue("Metodin getSivuja tulee olla julkinen eli määritelty public void getSivuja()", klass.method(kirja, "getSivuja")
                .returning(int.class)
                .takingNoParams().isPublic());
    }

    @Test
    public void testaaKirjailijanPalautus() throws Throwable {
        MockInOut mio = new MockInOut("");
        Reflex.MethodRef3<Object, Object, String, String, Integer> kirjaClass = klass.constructor().taking(String.class, String.class, int.class).withNiceError();
        Object kirja = kirjaClass.invoke("Marie Kondo", "KonMari", 222);

        String kirjailija = klass.method(kirja, "getKirjailija")
                .returning(String.class)
                .takingNoParams().invoke();

        assertEquals("Kun suoritetaan\n Kirja k = new Kirja(\"Marie Kondo\", \"KonMari\", 222);\nString s = k.getKirjailija();\nTulee muuttujassa s olla arvo \"Marie Kondo\".", "Marie Kondo", kirjailija);

        String out = mio.getOutput();

        mio.close();

        assertTrue("Konstruktorin ja metodin getKirjailija ei tule tulostaa mitään.", out.trim().isEmpty());
    }

    @Test
    public void testaaKirjailijanPalautus2() throws Throwable {
        MockInOut mio = new MockInOut("");
        Reflex.MethodRef3<Object, Object, String, String, Integer> kirjaClass = klass.constructor().taking(String.class, String.class, int.class).withNiceError();
        Object kirja = kirjaClass.invoke("Karie Mondo", "MonKari", 222);

        String kirjailija = klass.method(kirja, "getKirjailija")
                .returning(String.class)
                .takingNoParams().invoke();

        assertEquals("Kun suoritetaan\n Kirja k = new Kirja(\"Karie Mondo\", \"MonKari\", 222);\nString s = k.getKirjailija();\nTulee muuttujassa s olla arvo \"Karie Mondo\".", "Karie Mondo", kirjailija);

        String out = mio.getOutput();

        mio.close();

        assertTrue("Konstruktorin ja metodin getKirjailija ei tule tulostaa mitään.", out.trim().isEmpty());
    }

    @Test
    public void testaaNimenPalautus() throws Throwable {
        MockInOut mio = new MockInOut("");
        Reflex.MethodRef3<Object, Object, String, String, Integer> kirjaClass = klass.constructor().taking(String.class, String.class, int.class).withNiceError();
        Object kirja = kirjaClass.invoke("Marie Kondo", "KonMari", 222);

        String nimi = klass.method(kirja, "getNimi")
                .returning(String.class)
                .takingNoParams().invoke();

        assertEquals("Kun suoritetaan\n Kirja k = new Kirja(\"Marie Kondo\", \"KonMari\", 222);\nString s = k.getNimi();\nTulee muuttujassa s olla arvo \"KonMari\".", "KonMari", nimi);

        String out = mio.getOutput();

        mio.close();

        assertTrue("Konstruktorin ja metodin getNimi ei tule tulostaa mitään.", out.trim().isEmpty());
    }

    @Test
    public void testaaNimenPalautus2() throws Throwable {
        MockInOut mio = new MockInOut("");
        Reflex.MethodRef3<Object, Object, String, String, Integer> kirjaClass = klass.constructor().taking(String.class, String.class, int.class).withNiceError();
        Object kirja = kirjaClass.invoke("Karie Mondo", "MonKari", 222);

        String nimi = klass.method(kirja, "getNimi")
                .returning(String.class)
                .takingNoParams().invoke();

        assertEquals("Kun suoritetaan\n Kirja k = new Kirja(\"Karie Mondo\", \"MonKari\", 222);\nString s = k.getNimi();\nTulee muuttujassa s olla arvo \"MonKari\".", "MonKari", nimi);

        String out = mio.getOutput();

        mio.close();

        assertTrue("Konstruktorin ja metodin getNimi ei tule tulostaa mitään.", out.trim().isEmpty());
    }

    @Test
    public void testaaSivujenPalautus() throws Throwable {
        MockInOut mio = new MockInOut("");
        Reflex.MethodRef3<Object, Object, String, String, Integer> kirjaClass = klass.constructor().taking(String.class, String.class, int.class).withNiceError();
        Object kirja = kirjaClass.invoke("Marie Kondo", "KonMari", 222);

        int sivuja = klass.method(kirja, "getSivuja")
                .returning(int.class)
                .takingNoParams().invoke();

        assertEquals("Kun suoritetaan\n Kirja k = new Kirja(\"Marie Kondo\", \"KonMari\", 222);\nint s = k.getSivuja();\nTulee muuttujassa s olla arvo 222.", 222, sivuja);

        String out = mio.getOutput();

        mio.close();

        assertTrue("Konstruktorin ja metodin getSivuja ei tule tulostaa mitään.", out.trim().isEmpty());
    }

    @Test
    public void testaaSivujenPalautus2() throws Throwable {
        MockInOut mio = new MockInOut("");
        Reflex.MethodRef3<Object, Object, String, String, Integer> kirjaClass = klass.constructor().taking(String.class, String.class, int.class).withNiceError();
        Object kirja = kirjaClass.invoke("Marie Kondo", "KonMari", 2222);

        int sivuja = klass.method(kirja, "getSivuja")
                .returning(int.class)
                .takingNoParams().invoke();

        assertEquals("Kun suoritetaan\n Kirja k = new Kirja(\"Marie Kondo\", \"KonMari\", 222);\nint s = k.getSivuja();\nTulee muuttujassa s olla arvo 2222.", 2222, sivuja);

        String out = mio.getOutput();

        mio.close();

        assertTrue("Konstruktorin ja metodin getSivuja ei tule tulostaa mitään.", out.trim().isEmpty());
    }

    @Test
    public void testaaToString() throws Throwable {
        MockInOut mio = new MockInOut("");
        Reflex.MethodRef3<Object, Object, String, String, Integer> kirjaClass = klass.constructor().taking(String.class, String.class, int.class).withNiceError();
        Object kirja = kirjaClass.invoke("Marie Kondo", "KonMari", 222);

        String toString = kirja.toString();

        assertEquals("Kun suoritetaan\n Kirja k = new Kirja(\"Marie Kondo\", \"KonMari\", 222);\nString s = k.toString();\nTulee muuttujassa s olla arvo \"Marie Kondo, KonMari, 222 sivua\".", "Marie Kondo, KonMari, 222 sivua", toString);

        String out = mio.getOutput();

        mio.close();

        assertTrue("Konstruktorin ja metodin toString ei tule tulostaa mitään.", out.trim().isEmpty());
    }

    @Test
    public void testaaToString2() throws Throwable {
        MockInOut mio = new MockInOut("");
        Reflex.MethodRef3<Object, Object, String, String, Integer> kirjaClass = klass.constructor().taking(String.class, String.class, int.class).withNiceError();
        Object kirja = kirjaClass.invoke("Karie Mondo", "MonKari", 2222);

        String toString = kirja.toString();

        assertEquals("Kun suoritetaan\n Kirja k = new Kirja(\"Karie Mondo\", \"MonKari\", 2222);\nString s = k.toString();\nTulee muuttujassa s olla arvo \"Karie Mondo, MonKari, 2222 sivua\".", "Karie Mondo, MonKari, 2222 sivua", toString);

        String out = mio.getOutput();

        mio.close();

        assertTrue("Konstruktorin ja metodin toString ei tule tulostaa mitään.", out.trim().isEmpty());
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
            assertTrue("Tarvitset luokalle " + klassName + " kolme oliomuuttujaa, poista ylimääräiset", var < 4);
        }
    }

    private String kentta(String toString) {
        return toString.replace(klassName + ".", "");
    }
}
