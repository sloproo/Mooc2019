
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.Arrays;
import java.util.HashMap;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

@Points("06-06")
public class HajautustaulunTulostelua2Test {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void tulostaArvot() throws Throwable {
        Reflex.reflect(Ohjelma.class).staticMethod("tulostaArvot").returningVoid().taking(HashMap.class).requirePublic();
        HashMap<String, Kirja> data = hm();
        Reflex.reflect(Ohjelma.class).staticMethod("tulostaArvot").returningVoid().taking(HashMap.class).invoke(data);

        String koodi = "HashMap<String, Kirja> taulu = new HashMap<>();\n"
                + "taulu.put(\"tunteet\", new Kirja(\"Järki\", 1811, \"...\"));\n"
                + "taulu.put(\"luulot\", new Kirja(\"Ylpeys\", 1813, \"....\"));\n"
                + "taulu.put(\"onni\", new Kirja(\"Don't let the pigeon drive the bus\", 2003, \"....\"));\n"
                + "tulostaArvot(taulu);";

        for (String s : Arrays.asList("rki", "Ylpeys", "Don't let the pigeon drive the bus", "1811", "1813", "2003")) {
            assertTrue("Tulostus ei ollut toivottu. Kokeile koodia:\n" + koodi, io.getSysOut().contains(s));
        }

        for (String s : Arrays.asList("tunteet", "luulot", "onni")) {
            assertFalse("Tulostus ei ollut toivottu. Kokeile koodia:\n" + koodi, io.getSysOut().contains(s));
        }
    }

    @Test
    public void tulostaArvoJosNimessa1() throws Throwable {
        Reflex.reflect(Ohjelma.class).staticMethod("tulostaArvoJosNimessa").returningVoid().taking(HashMap.class, String.class).requirePublic();
        HashMap<String, Kirja> data = hm();
        Reflex.reflect(Ohjelma.class).staticMethod("tulostaArvoJosNimessa").returningVoid().taking(HashMap.class, String.class).invoke(data, "peys");

        String koodi = "HashMap<String, Kirja> taulu = new HashMap<>();\n"
                + "taulu.put(\"tunteet\", new Kirja(\"Järki\", 1811, \"...\"));\n"
                + "taulu.put(\"luulot\", new Kirja(\"Ylpeys\", 1813, \"....\"));\n"
                + "taulu.put(\"onni\", new Kirja(\"Don't let the pigeon drive the bus\", 2003, \"....\"));\n"
                + "tulostaArvoJosNimessa(taulu, \"peys\");";

        for (String s : Arrays.asList("Ylpeys", "1813")) {
            assertTrue("Tulostus ei ollut toivottu. Kokeile koodia:\n" + koodi, io.getSysOut().contains(s));
        }

        for (String s : Arrays.asList("tunteet", "luulot", "onni", "rki", "Don't let the pigeon drive the bus", "1811", "2003")) {
            assertFalse("Tulostus ei ollut toivottu. Kokeile koodia:\n" + koodi, io.getSysOut().contains(s));
        }
    }

    @Test
    public void tulostaArvoJosNimessa2() throws Throwable {
        Reflex.reflect(Ohjelma.class).staticMethod("tulostaArvoJosNimessa").returningVoid().taking(HashMap.class, String.class).requirePublic();
        HashMap<String, Kirja> data = hm();
        Reflex.reflect(Ohjelma.class).staticMethod("tulostaArvoJosNimessa").returningVoid().taking(HashMap.class, String.class).invoke(data, "i");

        String koodi = "HashMap<String, Kirja> taulu = new HashMap<>();\n"
                + "taulu.put(\"tunteet\", new Kirja(\"Järki\", 1811, \"...\"));\n"
                + "taulu.put(\"luulot\", new Kirja(\"Ylpeys\", 1813, \"....\"));\n"
                + "taulu.put(\"onni\", new Kirja(\"Don't let the pigeon drive the bus\", 2003, \"....\"));\n"
                + "tulostaArvoJosNimessa(taulu, \"i\");";

        for (String s : Arrays.asList("rki", "Don't let the pigeon drive the bus", "1811", "2003")) {
            assertTrue("Tulostus ei ollut toivottu. Kokeile koodia:\n" + koodi, io.getSysOut().contains(s));
        }

        for (String s : Arrays.asList("Ylpeys", "1813", "tunteet", "luulot", "onni")) {
            assertFalse("Tulostus ei ollut toivottu. Kokeile koodia:\n" + koodi, io.getSysOut().contains(s));
        }
    }

    private static HashMap<String, Kirja> hm() {
        HashMap<String, Kirja> taulu = new HashMap<>();
        taulu.put("tunteet", new Kirja("Järki", 1811, "..."));
        taulu.put("luulot", new Kirja("Ylpeys", 1813, "...."));
        taulu.put("onni", new Kirja("Don't let the pigeon drive the bus", 2003, "...."));

        return taulu;
    }

}
