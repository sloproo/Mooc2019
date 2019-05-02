
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import fi.helsinki.cs.tmc.edutestutils.Reflex.ClassRef;
import java.lang.reflect.Field;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class KellariTest {

    String klassName = "Kellari";
    Reflex.ClassRef<Object> klass;

    @Before
    public void setUp() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void luokkaJulkinen() {
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    public void tyhjaKonstruktori() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        assertTrue("Määrittele luokalle " + s(klassName) + " julkinen konstruktori: "
                + "public " + s(klassName) + "()", ctor.isPublic());
        String v = "virheen aiheutti koodi new " + klassName + "();";
        ctor.withNiceError(v).invoke();
    }

    public Object luo() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        return ctor.invoke();
    }


    /*
     *
     */
    @Test
    @Points("06-12.1")
    public void lisaaMetodi() throws Throwable {
        String metodi = "lisaa";

        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "(String komero, String tavara) ",
                klass.method(olio, metodi)
                        .returningVoid().taking(String.class, String.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi " + klassName + " k = new " + klassName + "();\n"
                + "k.lisaa(\"a111\",\"luistimet\");";

        klass.method(olio, metodi)
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("a111", "luistimet");
    }

    @Test
    @Points("06-12.1")
    public void sisaltoMetodi() throws Throwable {
        String metodi = "sisalto";

        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public ArrayList<String> " + metodi + "(String komero) ",
                klass.method(olio, metodi)
                        .returning(ArrayList.class)
                        .taking(String.class)
                        .isPublic());

        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).invoke("a111", "hiiri");

        String v = "\nVirheen aiheuttanut koodi \n"
                + klassName + " k = new " + klassName + "();\n"
                + "k.lisaa(\"a111\",\"hiiri\");\n"
                + "System.out.println(s." + metodi + "(\"a111\"));\n";

        ArrayList vast = new ArrayList();
        vast.add("hiiri");

        assertEquals(v, vast, klass.method(olio, metodi)
                .returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("a111"));
    }

    @Test
    @Points("06-12.1")
    public void sisaltoMetodiEiKomeroa() throws Throwable {
        String metodi = "sisalto";

        Object olio = luo();

        String v = "\nVirheen aiheuttanut koodi \n"
                + klassName + " k = new " + klassName + "();\n"
                + "k.lisaa(\"a111\",\"hiiri\");\n"
                + "System.out.println(s." + metodi + "(\"olematon\"));\n";

        assertNotNull(v, klass.method(olio, metodi)
                .returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("olematon"));
    }

    @Test
    @Points("06-12.1")
    public void sisaltoMetodiKaksiTavaraaKomerossa() throws Throwable {
        String metodi = "sisalto";

        Object olio = luo();

        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).invoke("a111", "hiiri");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).invoke("a111", "juusto");

        String v = "\nVirheen aiheuttanut koodi \n"
                + klassName + " k = new " + klassName + "();\n"
                + "k.lisaa(\"a111\",\"hiiri\");\n"
                + "k.lisaa(\"a111\",\"juusto\");\n"
                + "k.sisalto(\"a11\");\n";

        ArrayList vast = new ArrayList();
        vast.add("hiiri");
        vast.add("juusto");

        assertEquals(v, vast, klass.method(olio, metodi)
                .returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("a111"));
    }

    @Test
    @Points("06-12.1")
    public void sisaltoMetodiMontaKomeroa() throws Throwable {
        String metodi = "sisalto";

        Object olio = luo();

        String v = "\nVirheen aiheuttanut koodi \n"
                + klassName + " k = new " + klassName + "();\n"
                + "k.lisaa(\"a111\",\"hiiri\");\n"
                + "k.lisaa(\"a111\",\"juusto\");\n"
                + "k.lisaa(\"b123\",\"piirtoheitin\");\n"
                + "k.lisaa(\"g63\",\"luistimet\");\n"
                + "k.sisalto(\"a111\");\n";

        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("a111", "hiiri");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("a111", "juusto");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("b123", "piirtoheitin");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("g63", "luistimet");

        ArrayList vast = new ArrayList();
        vast.add("hiiri");
        vast.add("juusto");

        assertEquals(v, vast, klass.method(olio, metodi)
                .returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("a111"));

        v = "\nVirheen aiheuttanut koodi \n"
                + klassName + " k = new " + klassName + "();\n"
                + "k.lisaa(\"a111\",\"hiiri\");\n"
                + "k.lisaa(\"a111\",\"juusto\");\n"
                + "k.lisaa(\"b123\",\"piirtoheitin\");\n"
                + "k.lisaa(\"g63\",\"luistimet\");\n"
                + "k.sisalto(\"b123\");\n";

        vast = new ArrayList();
        vast.add("piirtoheitin");
        assertEquals(v, vast, klass.method(olio, metodi)
                .returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("b123"));

        v = "\nVirheen aiheuttanut koodi \n"
                + klassName + " k = new " + klassName + "();\n"
                + "k.lisaa(\"a111\",\"hiiri\");\n"
                + "k.lisaa(\"a111\",\"juusto\");\n"
                + "k.lisaa(\"b123\",\"piirtoheitin\");\n"
                + "k.lisaa(\"g63\",\"luistimet\");\n"
                + "k.sisalto(\"g63\");\n";

        assertNotNull(v, klass.method(olio, metodi)
                .returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("g63"));
    }

    /*
     *
     */
    @Test
    @Points("06-12.2")
    public void poistaMetodi() throws Throwable {
        String metodi = "poista";

        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public String " + metodi + "(String komero, String sisalto) ",
                klass.method(olio, metodi)
                        .returningVoid()
                        .taking(String.class, String.class)
                        .isPublic());
    }

    @Test
    @Points("06-12.2")
    public void poistaOlemassaoleva() throws Throwable {

        Object olio = luo();

        String v = "\nVirheen aiheuttanut koodi \n"
                + klassName + " k = new " + klassName + "();\n"
                + "k.lisaa(\"a111\",\"hiiri\");\n"
                + "k.lisaa(\"a111\",\"juusto\");\n"
                + "k.lisaa(\"b123\",\"piirtoheitin\");\n"
                + "k.lisaa(\"g63\",\"luistimet\");\n"
                + "k.poista(\"g63\",\"luistimet\");\n"
                + "k.sisalto(\"g63\");\n";

        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("a111", "hiiri");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("a111", "juusto");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("b123", "piirtoheitin");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("g63", "luistimet");

        klass.method(olio, "poista")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("g63", "luistimet");

        assertNotNull(v, klass.method(olio, "sisalto")
                .returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("g63"));

        ArrayList vast = new ArrayList();

        assertEquals(v, vast, klass.method(olio, "sisalto")
                .returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("g63"));
    }

    @Test
    @Points("06-12.2")
    public void poistaVainYksiJosUseita() throws Throwable {
        String metodi = "poista";

        Object olio = luo();

        String v = "\nVirheen aiheuttanut koodi \n"
                + klassName + " k = new " + klassName + "();\n"
                + "k.lisaa(\"a111\",\"hiiri\");\n"
                + "k.lisaa(\"a111\",\"juusto\");\n"
                + "k.lisaa(\"a111\",\"juusto\");\n"
                + "k.lisaa(\"b123\",\"piirtoheitin\");\n"
                + "k.lisaa(\"g63\",\"luistimet\");\n"
                + "k.poista(\"a111\",\"juusto\");\n"
                + "k.sisalto(\"a111\");\n";

        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("a111", "hiiri");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("a111", "juusto");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("a111", "juusto");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("b123", "piirtoheitin");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("g63", "luistimet");

        klass.method(olio, metodi)
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("a111", "juusto");

        assertNotNull(v, klass.method(olio, "sisalto")
                .returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("a111"));

        ArrayList vast = new ArrayList();
        vast.add("hiiri");
        vast.add("juusto");

        assertEquals(v, vast, klass.method(olio, "sisalto")
                .returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("a111"));
    }

    @Test
    @Points("06-12.2")
    public void montaLisaystaSisallonTarkisteluaJaPoistoa() throws Throwable {

        Object olio = luo();

        String v = "\nVirheen aiheuttanut koodi \n"
                + klassName + " k = new " + klassName + "();\n"
                + "k.lisaa(\"a111\",\"hiiri\");\n"
                + "k.lisaa(\"a111\",\"juusto\");\n"
                + "k.lisaa(\"a111\",\"juusto\");\n"
                + "k.lisaa(\"b123\",\"piirtoheitin\");\n"
                + "k.lisaa(\"g63\",\"luistimet\");\n"
                + "k.poista(\"a111\",\"juusto\");\n"
                + "k.lisaa(\"a111\",\"juusto\");\n"
                + "k.sisalto(\"a111\");\n";

        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("a111", "hiiri");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("a111", "juusto");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("a111", "juusto");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("b123", "piirtoheitin");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("g63", "luistimet");
        klass.method(olio, "poista")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("a111", "juusto");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("a111", "juusto");

        ArrayList vast = new ArrayList();
        vast.add("hiiri");
        vast.add("juusto");
        vast.add("juusto");

        assertEquals(v, vast, klass.method(olio, "sisalto")
                .returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("a111"));
    }

    @Test
    @Points("06-12.2")
    public void komerotMetodi() throws Throwable {
        String metodi = "komerot";

        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public ArrayList<String> " + metodi + "() ",
                klass.method(olio, metodi)
                        .returning(ArrayList.class)
                        .takingNoParams().isPublic());
    }

    @Test
    @Points("06-12.2")
    public void komerotListalla1() throws Throwable {
        String metodi = "komerot";

        Object olio = luo();

        String v = "\nVirheen aiheuttanut koodi \n"
                + klassName + " k = new " + klassName + "();\n"
                + "k.lisaa(\"a111\",\"hiiri\");\n"
                + "k.lisaa(\"a111\",\"juusto\");\n"
                + "k.lisaa(\"a111\",\"juusto\");\n"
                + "k.lisaa(\"b123\",\"piirtoheitin\");\n"
                + "k.lisaa(\"g63\",\"luistimet\");\n"
                + "k.poista(\"a111\",\"juusto\");\n"
                + "k.lisaa(\"a111\",\"juusto\");\n"
                + "k.sisalto(\"a111\");\n"
                + "k.komerot();\n";

        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("a111", "hiiri");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("a111", "juusto");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("a111", "juusto");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("b123", "piirtoheitin");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("g63", "luistimet");
        klass.method(olio, "poista")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("a111", "juusto");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("a111", "juusto");

        ArrayList vast = new ArrayList();
        vast.add("a111");
        vast.add("b123");
        vast.add("g63");

        assertTrue(v, vast.containsAll(klass.method(olio, "komerot")
                .returning(ArrayList.class).takingNoParams().withNiceError(v).invoke()));

        assertTrue(v, klass.method(olio, "komerot")
                .returning(ArrayList.class).takingNoParams().withNiceError(v).invoke().containsAll(vast));
    }
    
    @Test
    @Points("06-12.2")
    public void tyhjaKomeroEiListauksessa() throws Throwable {
        String metodi = "komerot";

        Object olio = luo();

        String v = "\nVirheen aiheuttanut koodi \n"
                + klassName + " k = new " + klassName + "();\n"
                + "k.lisaa(\"a111\",\"hiiri\");\n"
                + "k.lisaa(\"a111\",\"juusto\");\n"
                + "k.lisaa(\"a111\",\"juusto\");\n"
                + "k.lisaa(\"b123\",\"piirtoheitin\");\n"
                + "k.lisaa(\"g63\",\"luistimet\");\n"
                + "k.poista(\"b123\",\"piirtoheitin\");\n"
                + "k.komerot();\n";

        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("a111", "hiiri");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("a111", "juusto");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("a111", "juusto");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("b123", "piirtoheitin");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("g63", "luistimet");
        klass.method(olio, "poista")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("b123", "piirtoheitin");

        ArrayList vast = new ArrayList();
        vast.add("a111");
        vast.add("g63");

        assertTrue(v, vast.containsAll(klass.method(olio, "komerot")
                .returning(ArrayList.class).takingNoParams().withNiceError(v).invoke()));

        assertTrue(v, klass.method(olio, "komerot")
                .returning(ArrayList.class).takingNoParams().withNiceError(v).invoke().containsAll(vast));
    }

    private String s(String klassName) {
        return klassName.substring(klassName.lastIndexOf(".") + 1);
    }

    private String kentta(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "").replace("java.io.", "");
    }
}
