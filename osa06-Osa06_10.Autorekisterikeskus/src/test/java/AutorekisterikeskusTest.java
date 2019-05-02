
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import static org.junit.Assert.*;

public class AutorekisterikeskusTest {

    String klassName = "Ajoneuvorekisteri";
    Reflex.ClassRef<Object> klass;

    @Test
    @Points("06-10.1")
    public void eiYlimaaraisiaMuuttujiaRekisterinumerolle() {
        saniteettitarkastus("Rekisterinumero", 2, "valmiina olevat maatunnuksen ja varsinaisen rekisterinumeron muistavat oliomuuttujan");
    }

    @Points("06-10.1")
    @Test
    public void rekisterinumeronEquals() {
        testaaEquals("FI", "ABC-123", "FI", "ABC-123", true);
        testaaEquals("FI", "ABC-123", "FI", "ABC-122", false);
        testaaEquals("FI", "ABC-123", "F", "ABC-123", false);
        testaaEquals("D", "B IFK-333", "D", "B IFK-333", true);
        testaaEquals("D", "B IFK-33", "D", "B IFK-333", false);
        testaaEquals("D", "B IFK-33", "G", "B IFK-333", false);
    }

    @Points("06-10.1")
    @Test
    public void rekisterinumeronHashCode() {
        testaaHash("FI", "ABC-123", "FI", "ABC-123");
        testaaHash("D", "B IFK-333", "D", "B IFK-333");
        testaaHash("FI", "TUX-100", "FI", "TUX-100");
        testaaHash("FI", "UKK-999", "FI", "UKK-999");

        Rekisterinumero r1 = new Rekisterinumero("FI", "AAA-111");
        Rekisterinumero r2 = new Rekisterinumero("B", "ZZ-22 A");
        Rekisterinumero r3 = new Rekisterinumero("QQ", "joopajoo");
        assertFalse("metodi hashCode näyttää palauttavan kaikille rekisterinumeroille saman arvon " + r1.hashCode(),
                r1.hashCode() == r2.hashCode() && r2.hashCode() == r3.hashCode());
    }

    @Points("06-10.2")
    @Test
    public void luokkaJulkinen() {
        klass = Reflex.reflect(klassName);
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    @Points("06-10.2")
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus(klassName, 1, "ajoneuvotiedot tallentavan HashMap<Rekisterinumero, String>-tyyppisen oliomuuttujan");
    }

    @Test
    @Points("06-10.2")
    public void onHashMap() {
        Field[] kentat = ReflectionUtils.findClass(klassName).getDeclaredFields();
        assertTrue("Lisää luokalle " + klassName + " HashMap<Rekisterinumero, String>-tyyppinen oliomuuttuja", kentat.length == 1);
        assertTrue("Luokalla " + klassName + " tulee olla HashMap<Rekisterinumero, String>-tyyppinen oliomuuttuja", kentat[0].toString().contains("HashMap"));
    }

    @Test
    @Points("06-10.2")
    public void tyhjaKonstruktori() throws Throwable {
        klass = Reflex.reflect(klassName);
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "()", ctor.isPublic());
        String v = "virheen aiheutti koodi new Ajoneuvorekisteri();";
        ctor.withNiceError(v).invoke();
    }

    @Test
    @Points("06-10.2")
    public void lisaaMetodi() throws Throwable {
        String metodi = "lisaa";

        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public boolean " + metodi + "(Rekisterinumero rekkari, String omistaja) ",
                klass.method(olio, metodi)
                .returning(boolean.class).taking(Rekisterinumero.class, String.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi\n Ajoneuvorekisteri ar = new Ajoneuvorekisteri(); "
                + "ar.lisaa( new Rekisterinumero(\"FI\", \"AAA-111\"), \"Arto\");";

        assertEquals("Ajoneuvorekisteri ar = new Ajoneuvorekisteri(); \n"
                + "ar.lisaa( new Rekisterinumero(\"FI\", \"AAA-111\"), \"Arto\");",
                true, klass.method(olio, metodi)
                .returning(boolean.class).taking(Rekisterinumero.class, String.class).
                withNiceError(v).invoke(new Rekisterinumero("FI", "AAA-111"), "Arto"));

        assertEquals("Jo rekisterissä olevan lisäyksen pitäisi palauttaa false\n"
                + "Ajoneuvorekisteri ar = new Ajoneuvorekisteri(); \n"
                + "ar.lisaa( new Rekisterinumero(\"FI\", \"AAA-111\"), \"Arto\");\n"
                + "ar.lisaa( new Rekisterinumero(\"FI\", \"AAA-111\"), \"Arto\");",
                false, klass.method(olio, metodi)
                .returning(boolean.class).taking(Rekisterinumero.class, String.class).invoke(new Rekisterinumero("FI", "AAA-111"), "Arto"));

        assertEquals("Jo rekisterissä olevan lisäyksen pitäisi palauttaa false\n"
                + "Ajoneuvorekisteri ar = new Ajoneuvorekisteri(); \n"
                + "ar.lisaa( new Rekisterinumero(\"FI\", \"AAA-111\"), \"Arto\");\n"
                + "ar.lisaa( new Rekisterinumero(\"FI\", \"AAA-111\"), \"Arto\");\n"
                + "ar.lisaa( new Rekisterinumero(\"FI\", \"AAA-111\"), \"Pekka\");",
                false, klass.method(olio, metodi)
                .returning(boolean.class).taking(Rekisterinumero.class, String.class).invoke(new Rekisterinumero("FI", "AAA-111"), "Pekka"));
    }

    @Test
    @Points("06-10.2")
    public void haeMetodi() throws Throwable {
        String metodi = "hae";

        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public String " + metodi + "(Rekisterinumero rekkari)",
                klass.method(olio, metodi)
                .returning(String.class).taking(Rekisterinumero.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi"
                + "\n Ajoneuvorekisteri ar = new Ajoneuvorekisteri(); "
                + "ar.hae(new Rekisterinumero(\"FI\", \"AAA-111\"));";

        assertEquals("Rekisterissä olemattoman haun pitää palauttaa null\n"
                + "Ajoneuvorekisteri ar = new Ajoneuvorekisteri(); \n"
                + "ar.hae(new Rekisterinumero(\"FI\", \"AAA-111\"));",
                null, klass.method(olio, metodi)
                .returning(String.class).
                taking(Rekisterinumero.class).withNiceError(v).
                invoke(new Rekisterinumero("FI", "AAA-111")));

        lisaa(olio, "FI", "AAA-111", "Arto");

        assertEquals("rekisterissä olevan haun pitää palauttaa omistaja\n"
                + "Ajoneuvorekisteri ar = new Ajoneuvorekisteri(); \n"
                + "ar.lisaa( new Rekisterinumero(\"FI\", \"AAA-111\"), \"Arto\");\n"
                + "ar.hae( new Rekisterinumero(\"FI\", \"AAA-111\"));",
                "Arto", klass.method(olio, metodi)
                .returning(String.class).taking(Rekisterinumero.class).invoke(new Rekisterinumero("FI", "AAA-111")));

        lisaa(olio, "FI", "XX-999", "Arto");
        assertEquals("rekisterissä olevan haun pitää palauttaa omistaja\n"
                + "Ajoneuvorekisteri ar = new Ajoneuvorekisteri(); \n"
                + "ar.lisaa( new Rekisterinumero(\"FI\", \"AAA-111\"), \"Arto\");\n"
                + "ar.lisaa( new Rekisterinumero(\"FI\", \"XX-999\"), \"Arto\");\n"
                + "ar.hae( new Rekisterinumero(\"FI\", \"AAA-111\"));",
                "Arto", klass.method(olio, metodi)
                .returning(String.class).taking(Rekisterinumero.class).invoke(new Rekisterinumero("FI", "AAA-111")));

        assertEquals("rekisterissä olevan haun pitää palauttaa omistaja\n"
                + "Ajoneuvorekisteri ar = new Ajoneuvorekisteri(); \n"
                + "ar.lisaa( new Rekisterinumero(\"FI\", \"AAA-111\"), \"Arto\");\n"
                + "ar.lisaa( new Rekisterinumero(\"FI\", \"XX-999\"), \"Arto\");\n"
                + "ar.hae( new Rekisterinumero(\"FI\", \"XX-999\"));",
                "Arto", klass.method(olio, metodi)
                .returning(String.class).taking(Rekisterinumero.class).invoke(new Rekisterinumero("FI", "XX-999")));
    }

    @Test
    @Points("06-10.2")
    public void poistaMetodi() throws Throwable {
        String metodi = "poista";

        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public boolean " + metodi + "(Rekisterinumero rekkari) ",
                klass.method(olio, metodi)
                .returning(boolean.class).taking(Rekisterinumero.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi\n Ajoneuvorekisteri ar = new Ajoneuvorekisteri(); "
                + "ar.poista( new Rekisterinumero(\"FI\", \"AAA-111\"));";

        assertEquals("Jos poistettavaa ei ole, tulee operaation palauttaa false. Tarkista koodi\n"
                + "Ajoneuvorekisteri ar = new Ajoneuvorekisteri(); \n"
                + "ar.poista( new Rekisterinumero(\"FI\", \"AAA-111\"));",
                false, klass.method(olio, metodi)
                .returning(boolean.class).taking(Rekisterinumero.class).withNiceError(v).invoke(new Rekisterinumero("FI", "AAA-111")));

        lisaa(olio, "FI", "AAA-111", "Arto");

        assertEquals("Rekisterissä olevan poiston pitäisi palauttaa true\n"
                + "Ajoneuvorekisteri ar = new Ajoneuvorekisteri(); \n"
                + "ar.lisaa( new Rekisterinumero(\"FI\", \"AAA-111\"), \"Arto\");\n"
                + "ar.poista( new Rekisterinumero(\"FI\", \"AAA-111\"));",
                true, klass.method(olio, metodi)
                .returning(boolean.class).taking(Rekisterinumero.class).invoke(new Rekisterinumero("FI", "AAA-111")));
    }

    @Test
    @Points("06-10.2")
    public void lisaysHakuPoisto() throws Throwable {
        Object olio = luo();

        lisaa(olio, "FI", "AAA-111", "Arto");
        lisaa(olio, "FI", "BBB-222", "Pekka");

        String o = hae(olio, "FI", "AAA-111");

        String v = "Ajoneuvorekisteri ar = new Ajoneuvorekisteri(); \n"
                + "ar.lisaa( new Rekisterinumero(\"FI\", \"AAA-111\"), \"Arto\");\n"
                + "ar.lisaa( new Rekisterinumero(\"FI\", \"BBB-222\"), \"Pekka\");\n";

        assertEquals(v + "ar.hae(new Rekisterinumero(\"FI\", \"AAA-111\"));", "Arto", o);

        o = hae(olio, "FI", "BBB-222");

        assertEquals(v + "ar.hae(new Rekisterinumero(\"FI\", \"BBB-222\"));\n", "Pekka", o);

        poista(olio, "FI", "AAA-111");
        o = hae(olio, "FI", "AAA-111");

        assertEquals(v + "ar.poista(new Rekisterinumero(\"FI\", \"AAA-111\"));\n"
                + "ar.hae(new Rekisterinumero(\"FI\", \"AAA-111\"));\n", null, o);
    }

    @Test
    @Points("06-10.3")
    public void tulostaRekisterinumerotMetodi() throws Throwable {
        String metodi = "tulostaRekisterinumerot";
        MockInOut io = new MockInOut("");
        Object olio = luo();

        lisaa(olio, "FI", "AAA-111", "Arto");
        lisaa(olio, "FI", "BBB-222", "Pekka");
        lisaa(olio, "FI", "CCC-333", "Jukka");

        String v = "Ajoneuvorekisteri ar = new Ajoneuvorekisteri(); \n"
                + "ar.lisaa( new Rekisterinumero(\"FI\", \"AAA-111\"), \"Arto\");\n"
                + "ar.lisaa( new Rekisterinumero(\"FI\", \"BBB-222\"), \"Pekka\");\n"
                + "ar.lisaa( new Rekisterinumero(\"FI\", \"CCC-333\"), \"Jukka\");\n"
                + "ar.tulostaRekisterinumerot()";

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "() ",
                klass.method(olio, metodi)
                .returningVoid().takingNoParams().isPublic());

        klass.method(olio, metodi)
                .returningVoid().takingNoParams().withNiceError(v).invoke();

        String out = io.getOutput();
        assertTrue("Tulostettuja rivejä olisi pitänyt olla kolme koodilla:\n"+v+"\ntulostit\n"+out,out.split("\n").length>2);

        assertTrue("Tulostus ei mene oikein koodilla: \n"+v+"\ntulostit:\n"+out, out.contains("AAA-111"));
        assertTrue("Tulostus ei mene oikein koodilla: \n"+v+"\ntulostit:\n"+out, out.contains("BBB-222"));
        assertTrue("Tulostus ei mene oikein koodilla: \n"+v+"\ntulostit:\n"+out, out.contains("CCC-333"));
        assertTrue("Tulostus ei mene oikein koodilla: \n"+v+"\ntulostit:\n"+out, !out.contains("Arto"));
        assertTrue("Tulostus ei mene oikein koodilla: \n"+v+"\ntulostit:\n"+out, !out.contains("Pekka"));
        assertTrue("Tulostus ei mene oikein koodilla: \n"+v+"\ntulostit:\n"+out, !out.contains("Jukka"));
    }

    @Test
    @Points("06-10.3")
    public void tulostaRekisterinumerotMetodi2() throws Throwable {
        String metodi = "tulostaRekisterinumerot";
        MockInOut io = new MockInOut("");
        Object olio = luo();

        lisaa(olio, "FI", "AAA-111", "Arto");
        lisaa(olio, "FI", "BBB-222", "Pekka");
        lisaa(olio, "FI", "CCC-333", "Arto");

        String v = "Ajoneuvorekisteri ar = new Ajoneuvorekisteri(); \n"
                + "ar.lisaa( new Rekisterinumero(\"FI\", \"AAA-111\"), \"Arto\");\n"
                + "ar.lisaa( new Rekisterinumero(\"FI\", \"BBB-222\"), \"Pekka\");\n"
                + "ar.lisaa( new Rekisterinumero(\"FI\", \"CCC-333\"), \"Arto\");\n"
                + "ar.tulostaRekisterinumerot()";

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "() ",
                klass.method(olio, metodi)
                .returningVoid().takingNoParams().isPublic());

        klass.method(olio, metodi)
                .returningVoid().takingNoParams().withNiceError(v).invoke();

        String out = io.getOutput();

        assertTrue("Tulostettuja rivejä olisi pitänyt olla kolme koodilla:\n"+v+"\ntulostit\n"+out,out.split("\n").length>2);

        assertTrue("Tulostus ei mene oikein koodilla: \n"+v+"\ntulostit:\n"+out, out.contains("AAA-111"));
        assertTrue("Tulostus ei mene oikein koodilla: \n"+v+"\ntulostit:\n"+out, out.contains("BBB-222"));
        assertTrue("Tulostus ei mene oikein koodilla: \n"+v+"\ntulostit:\n"+out, out.contains("CCC-333"));
        assertTrue("Tulostus ei mene oikein koodilla: \n"+v+"\ntulostit:\n"+out, !out.contains("Arto"));
        assertTrue("Tulostus ei mene oikein koodilla: \n"+v+"\ntulostit:\n"+out, !out.contains("Pekka"));
        assertTrue("Tulostus ei mene oikein koodilla: \n"+v+"\ntulostit:\n"+out, !out.contains("Arto"));
    }

    @Test
    @Points("06-10.3")
    public void tulostaOmistajat() throws Throwable {
        String metodi = "tulostaOmistajat";
        MockInOut io = new MockInOut("");
        Object olio = luo();

        lisaa(olio, "FI", "AAA-111", "Arto");
        lisaa(olio, "FI", "BBB-222", "Pekka");
        lisaa(olio, "FI", "CCC-333", "Arto");

        String v = "Ajoneuvorekisteri ar = new Ajoneuvorekisteri(); \n"
                + "ar.lisaa( new Rekisterinumero(\"FI\", \"AAA-111\"), \"Arto\");\n"
                + "ar.lisaa( new Rekisterinumero(\"FI\", \"BBB-222\"), \"Pekka\");\n"
                + "ar.lisaa( new Rekisterinumero(\"FI\", \"CCC-333\"), \"Arto\");\n"
                + "ar.tulostaOmistajat()";

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "() ",
                klass.method(olio, metodi)
                .returningVoid().takingNoParams().isPublic());

        klass.method(olio, metodi)
                .returningVoid().takingNoParams().withNiceError(v).invoke();

        String out = io.getOutput();

        assertTrue("Tulostettuja rivejä olisi pitänyt olla kaksi koodilla:\n"+v+"\ntulostit\n"+out,out.split("\n").length>1);

        assertTrue("Tulostus ei mene oikein koodilla: \n"+v+"\ntulostit:\n"+out, !out.contains("AAA-111"));
        assertTrue("Tulostus ei mene oikein koodilla: \n"+v+"\ntulostit:\n"+out, !out.contains("BBB-222"));
        assertTrue("Tulostus ei mene oikein koodilla: \n"+v+"\ntulostit:\n"+out, !out.contains("CCC-333"));
        assertTrue("Tulostus ei mene oikein koodilla: \n"+v+"\ntulostit:\n"+out, out.contains("Arto"));
        assertTrue("Tulostus ei mene oikein koodilla: \n"+v+"\ntulostit:\n"+out, out.contains("Pekka"));
        String jaa = out.substring( out.indexOf("Arto")+1 );
        assertFalse("Samaa nimeä ei saa tulostaa kahta kertaa, tulostus ei toimi koodilla: "
                + "\n"+v+"\ntulostit:\n"+out, jaa.contains("Arto"));
    }

    /*
     *
     */
    public Object luo() throws Throwable {
        klass = Reflex.reflect(klassName);
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        return ctor.invoke();
    }

    private void testaaEquals(String m1, String r1, String m2, String r2, boolean vast) {
        Rekisterinumero rr1 = new Rekisterinumero(m1, r1);
        Rekisterinumero rr2 = new Rekisterinumero(m2, r2);

        String v = "Rekisterinumero r1 = new Rekisterinumero(\"" + m1 + "\", \"" + r1 + "\");\n"
                + "Rekisterinumero r2 = new Rekisterinumero(\"" + m2 + "\", \"" + r2 + "\");\n"
                + "r1.equals(r2)";
        assertEquals(v, vast, rr1.equals(rr2));
    }

    private void testaaHash(String m1, String r1, String m2, String r2) {
        Rekisterinumero rr1 = new Rekisterinumero(m1, r1);
        Rekisterinumero rr2 = new Rekisterinumero(m2, r2);

        String v = "Rekisterinumero r1 = new Rekisterinumero(\"" + m1 + "\", \"" + r1 + "\");\n"
                + "Rekisterinumero r2 = new Rekisterinumero(\"" + m2 + "\", \"" + r2 + "\");\n"
                + "r2.hashCode() == r2.HashCode()";
        assertEquals(v, true, rr1.hashCode() == rr2.hashCode());
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

    private void lisaa(Object olio, String maa, String rek, String om) throws Throwable {
        klass.method(olio, "lisaa")
                .returning(boolean.class).taking(Rekisterinumero.class, String.class).invoke(new Rekisterinumero(maa, rek), om);
    }

    private void poista(Object olio, String maa, String rek) throws Throwable {
        klass.method(olio, "poista")
                .returning(boolean.class).taking(Rekisterinumero.class).invoke(new Rekisterinumero(maa, rek));
    }

    private String hae(Object olio, String f, String r) throws Throwable {
        return klass.method(olio, "hae")
                .returning(String.class).
                taking(Rekisterinumero.class).
                invoke(new Rekisterinumero(f, r));
    }
}
