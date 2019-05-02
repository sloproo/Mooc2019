
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.Arrays;
import java.util.HashMap;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

@Points("06-05")
public class HajautustaulunTulosteluaTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void tulostaAvaimet() throws Throwable {
        Reflex.reflect(Ohjelma.class).staticMethod("tulostaAvaimet").returningVoid().taking(HashMap.class).requirePublic();
        HashMap<String, String> data = hm("a", "b", "c", "d", "e", "f");
        Reflex.reflect(Ohjelma.class).staticMethod("tulostaAvaimet").returningVoid().taking(HashMap.class).invoke(data);

        String koodi = "HashMap<String, String> hm = new HashMap<>();\n"
                + "hm.put(\"a\", \"b\");\n"
                + "hm.put(\"c\", \"d\");\n"
                + "hm.put(\"e\", \"f\");\n"
                + "tulostaAvaimet(hm);";

        for (String s : Arrays.asList("a", "c", "e")) {
            assertTrue("Tulostus ei ollut toivottu. Kokeile koodia:\n" + koodi, io.getSysOut().contains(s));
        }

        for (String s : Arrays.asList("b", "d", "f")) {
            assertFalse("Tulostus ei ollut toivottu. Kokeile koodia:\n" + koodi, io.getSysOut().contains(s));
        }
    }

    @Test
    public void tulostaAvaimetJoissa1() throws Throwable {
        Reflex.reflect(Ohjelma.class).staticMethod("tulostaAvaimetJoissa").returningVoid().taking(HashMap.class, String.class).requirePublic();
        HashMap<String, String> data = hm("a", "b", "c", "d", "e", "f");
        Reflex.reflect(Ohjelma.class).staticMethod("tulostaAvaimetJoissa").returningVoid().taking(HashMap.class, String.class).invoke(data, "a");

        String koodi = "HashMap<String, String> hm = new HashMap<>();\n"
                + "hm.put(\"a\", \"b\");\n"
                + "hm.put(\"c\", \"d\");\n"
                + "hm.put(\"e\", \"f\");\n"
                + "tulostaAvaimetJoissa(hm, \"a\");";

        for (String s : Arrays.asList("a")) {
            assertTrue("Tulostus ei ollut toivottu. Kokeile koodia:\n" + koodi, io.getSysOut().contains(s));
        }

        for (String s : Arrays.asList("b", "d", "f", "c", "e")) {
            assertFalse("Tulostus ei ollut toivottu. Kokeile koodia:\n" + koodi, io.getSysOut().contains(s));
        }
    }

    @Test
    public void tulostaAvaimetJoissa2() throws Throwable {
        Reflex.reflect(Ohjelma.class).staticMethod("tulostaAvaimetJoissa").returningVoid().taking(HashMap.class, String.class).requirePublic();
        HashMap<String, String> data = hm("abcd", "jkl", "def", "mno", "ghi", "pqr");
        Reflex.reflect(Ohjelma.class).staticMethod("tulostaAvaimetJoissa").returningVoid().taking(HashMap.class, String.class).invoke(data, "d");

        String koodi = "HashMap<String, String> hm = new HashMap<>();\n"
                + "hm.put(\"abcd\", \"jkl\");\n"
                + "hm.put(\"def\", \"mno\");\n"
                + "hm.put(\"ghi\", \"pqr\");\n"
                + "tulostaAvaimetJoissa(hm, \"a\");";

        for (String s : Arrays.asList("abcd", "def")) {
            assertTrue("Tulostus ei ollut toivottu. Kokeile koodia:\n" + koodi, io.getSysOut().contains(s));
        }

        for (String s : Arrays.asList("ghi", "jkl", "mno", "pqr")) {
            assertFalse("Tulostus ei ollut toivottu. Kokeile koodia:\n" + koodi, io.getSysOut().contains(s));
        }
    }

    @Test
    public void tulostaArvotJosAvaimessa1() throws Throwable {
        Reflex.reflect(Ohjelma.class).staticMethod("tulostaArvotJosAvaimessa").returningVoid().taking(HashMap.class, String.class).requirePublic();
        HashMap<String, String> data = hm("abcd", "jkl", "def", "mno", "ghi", "pqr");
        Reflex.reflect(Ohjelma.class).staticMethod("tulostaArvotJosAvaimessa").returningVoid().taking(HashMap.class, String.class).invoke(data, "a");

        String koodi = "HashMap<String, String> hm = new HashMap<>();\n"
                + "hm.put(\"abcd\", \"jkl\");\n"
                + "hm.put(\"def\", \"mno\");\n"
                + "hm.put(\"ghi\", \"pqr\");\n"
                + "tulostaArvotJosAvaimessa(hm, \"a\");";

        for (String s : Arrays.asList("jkl")) {
            assertTrue("Tulostus ei ollut toivottu. Kokeile koodia:\n" + koodi, io.getSysOut().contains(s));
        }

        for (String s : Arrays.asList("abcd", "def", "ghi", "mno", "pqr")) {
            assertFalse("Tulostus ei ollut toivottu. Kokeile koodia:\n" + koodi, io.getSysOut().contains(s));
        }
    }

    @Test
    public void tulostaArvotJosAvaimessa2() throws Throwable {
        Reflex.reflect(Ohjelma.class).staticMethod("tulostaArvotJosAvaimessa").returningVoid().taking(HashMap.class, String.class).requirePublic();
        HashMap<String, String> data = hm("abcd", "jkl", "def", "mno", "ghi", "pqr");
        Reflex.reflect(Ohjelma.class).staticMethod("tulostaArvotJosAvaimessa").returningVoid().taking(HashMap.class, String.class).invoke(data, "d");

        String koodi = "HashMap<String, String> hm = new HashMap<>();\n"
                + "hm.put(\"abcd\", \"jkl\");\n"
                + "hm.put(\"def\", \"mno\");\n"
                + "hm.put(\"ghi\", \"pqr\");\n"
                + "tulostaArvotJosAvaimessa(hm, \"a\");";

        for (String s : Arrays.asList("jkl", "mno")) {
            assertTrue("Tulostus ei ollut toivottu. Kokeile koodia:\n" + koodi, io.getSysOut().contains(s));
        }

        for (String s : Arrays.asList("abcd", "def", "ghi", "pqr")) {
            assertFalse("Tulostus ei ollut toivottu. Kokeile koodia:\n" + koodi, io.getSysOut().contains(s));
        }
    }

    private static HashMap<String, String> hm(String... data) {
        HashMap<String, String> map = new HashMap<>();

        for (int i = 0; i < data.length; i += 2) {
            map.put(data[i], data[i + 1]);
        }

        return map;
    }

}
