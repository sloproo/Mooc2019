package sanakirja;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MuistavaSanakirjaTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "sanakirja.MuistavaSanakirja";

    @Before
    public void setUp() {
        klass = Reflex.reflect(klassName);
        teeTiedosto();
    }

    @Points("10-13.1")
    @Test
    public void luokkaJulkinen() {
        assertTrue("Luokan " + s(klassName) + " pitää olla julkinen, eli se tulee määritellä\npackage sanakirja;\npublic class " + s(klassName) + " {...\n}", klass.isPublic());
    }

    @Points("10-13.1")
    @Test
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus(klassName, 10, "");
    }

    @Test
    @Points("10-13.1")
    public void tyhjaKonstruktori() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        assertTrue("Määrittele luokalle " + s(klassName) + " julkinen konstruktori: public " + klassName + "()", ctor.isPublic());
        String v = "virheen aiheutti koodi new MuistavaSanakirja();";
        ctor.withNiceError(v).invoke();
    }

    @Test
    @Points("10-13.1")
    public void lisaaMetodi() throws Throwable {
        String metodi = "lisaa";

        Object olio = luo();

        assertTrue("tee luokalle " + s(klassName) + " metodi public void " + metodi + "(String sana, String kaannos) ",
                klass.method(olio, metodi)
                        .returningVoid().taking(String.class, String.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi\n"
                + "MuistavaSanakirja s = new MuistavaSanakirja();\n"
                + "s.lisaa(\"apina\",\"monkey\");\n";

        klass.method(olio, metodi)
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("apina", "monkey");
    }

    @Test
    @Points("10-13.1")
    public void kaannaMetodi() throws Throwable {
        String metodi = "kaanna";

        Object olio = luo();

        assertTrue("tee luokalle " + s(klassName) + " metodi public String " + metodi + "(String sana) ",
                klass.method(olio, metodi)
                        .returning(String.class)
                        .taking(String.class)
                        .isPublic());

        String v = "\nVirheen aiheuttanut koodi \n"
                + "MuistavaSanakirja s = new MuistavaSanakirja();\n"
                + "s.kaanna(\"apina\");\n";

        klass.method(olio, metodi)
                .returning(String.class).taking(String.class).withNiceError(v).invoke("apina");
    }

    @Test
    @Points("10-13.1")
    public void lisaysJaKaannosToimii() throws Throwable {
        String v = "\n"
                + "MuistavaSanakirja s = new MuistavaSanakirja();\n"
                + "s.lisaa(\"apina\", \"monkey\");\n"
                + "s.lisaa(\"tietokone\", \"computer\");\n";

        Object o = luo();
        lisaa(o, "apina", "monkey", v);
        lisaa(o, "tietokone", "computer", v);

        String w = v + "s.kaanna(\"apina\");\n";
        assertEquals(w, "monkey", kaanna(o, "apina", w));
        w = v + "s.kaanna(\"tietokone\");\n";
        assertEquals(w, "computer", kaanna(o, "tietokone", w));
        w = v + "s.kaanna(\"monkey\");\n";
        assertEquals(w, "apina", kaanna(o, "monkey", w));
        w = v + "s.kaanna(\"computer\");\n";
        assertEquals(w, "tietokone", kaanna(o, "computer", w));

        w = v + "s.kaanna(\"ihminen\");\n";
        assertEquals(w, null, kaanna(o, "ihminen", w));

        lisaa(o, "apina", "apfe", v);
        w = v + "s.lisaa(\"apina\", \"apfe\");\n "
                + "s.kaanna(\"apina\");\n";
        assertEquals(w, "monkey", kaanna(o, "apina", w));
    }

    /*
     *
     */
    @Test
    @Points("10-13.2")
    public void poistaMetodi() throws Throwable {
        String metodi = "poista";

        Object olio = luo();

        assertTrue("tee luokalle " + s(klassName) + " metodi public void " + metodi + "(String sana) ",
                klass.method(olio, metodi)
                        .returningVoid()
                        .taking(String.class)
                        .isPublic());

        String v = "\nVirheen aiheuttanut koodi \n"
                + "MuistavaSanakirja s = new MuistavaSanakirja();\n"
                + "s.lisaa(\"apina\", \"monkey\");\n"
                + "s.poista(\"apina\");\n";

        lisaa(olio, "apina", "monkey", v);

        klass.method(olio, metodi)
                .returningVoid().taking(String.class).withNiceError(v).invoke("apina");
    }

    @Test
    @Points("10-13.2")
    public void poistoToimii() throws Throwable {
        String v = "\n"
                + "MuistavaSanakirja s = new MuistavaSanakirja();\n"
                + "s.lisaa(\"apina\", \"monkey\");\n"
                + "s.lisaa(\"tietokone\", \"computer\");\n"
                + "s.poista(\"apina\");\n";

        Object o = luo();
        lisaa(o, "apina", "monkey", v);
        lisaa(o, "tietokone", "computer", v);

        poista(o, "apina", v);

        String w = v + "s.kaanna(\"apina\");\n";
        assertEquals(w, null, kaanna(o, "apina", w));
        w = v + "s.kaanna(\"tietokone\");\n";
        assertEquals(w, "computer", kaanna(o, "tietokone", w));
        w = v + "s.kaanna(\"monkey\");\n";
        assertEquals(w, null, kaanna(o, "monkey", w));
        w = v + "s.kaanna(\"computer\");\n";
        assertEquals(w, "tietokone", kaanna(o, "computer", w));

        w = v + "s.kaanna(\"ihminen\");\n";
        assertEquals(w, null, kaanna(o, "ihminen", w));

        lisaa(o, "apina", "apfe", v);
        w = v + "s.lisaa(\"apina\", \"apfe\");\n "
                + "s.kaanna(\"apina\n);\n";
        assertEquals(w, "apfe", kaanna(o, "apina", w));
    }

    /*
     *
     */
    @Test
    @Points("10-13.3")
    public void parametrillinenKonstruktori() throws Throwable {
        Reflex.MethodRef1<Object, Object, String> ctor = klass.constructor().taking(String.class).withNiceError();
        assertTrue("Määrittele luokalle " + s(klassName) + " julkinen konstruktori: public " + s(klassName) + "(String tiedosto)", ctor.isPublic());
        String v = "virheen aiheutti koodi new MuistavaSanakirja(\"sanat.txt\");";
        ctor.withNiceError(v).invoke("sanat.txt");
    }

    @Test
    @Points("10-13.3")
    public void metodiLataa() throws Throwable {
        String metodi = "lataa";

        String v = "MuistavaSanakirja s = new MuistavaSanakirja(\"sanat.txt\");\n";

        Object olio = luo("sanat.txt", v);

        assertTrue("tee luokalle " + s(klassName) + " metodi public boolean " + metodi
                + "() ",
                klass.method(olio, metodi)
                        .returning(boolean.class)
                        .takingNoParams()
                        .isPublic());

        Class[] e = klass.method(olio, metodi)
                .returning(boolean.class)
                .takingNoParams().getMethod().getExceptionTypes();

        assertFalse("Luokan " + s(klassName) + " metodi public boolean " + metodi
                + "() ei saa heittää poikkeusta!\n"
                + "", e.length > 0);

        v = ""
                + "MuistavaSanakirja s = new MuistavaSanakirja(\"sanat.txt\");\n"
                + "s.lataa();\n";

        assertEquals(v, true, (boolean) klass.method(olio, metodi)
                .returning(boolean.class).takingNoParams()
                .withNiceError("\nVirheen aiheuttanut koodi" + v).invoke());
    }

    @Test
    @Points("10-13.3")
    public void ladattuSanakirjaTomii() throws Throwable {

        String v = ""
                + "MuistavaSanakirja s = new MuistavaSanakirja(\"sanat.txt\");\n"
                + "s.lataa();\n";

        Object o = luo("sanat.txt", v);

        assertEquals(v, true, lataa(o, v));

        String w = v + "s.kaanna(\"apina\");\n";
        assertEquals(w, "monkey", kaanna(o, "apina", w));
        w = v + "s.kaanna(\"alla oleva\");\n";
        assertEquals(w, "below", kaanna(o, "alla oleva", w));
        w = v + "s.kaanna(\"monkey\");\n";
        assertEquals(w, "apina", kaanna(o, "monkey", w));
        w = v + "s.kaanna(\"below\");\n";
        assertEquals(w, "alla oleva", kaanna(o, "below", w));
        w = v + "s.kaanna(\"ihminen\");\n";
        assertEquals(w, null, kaanna(o, "ihminen", w));

        v += "s.lisaa(\"ohjelmointi\", \"programming\");\n";

        lisaa(o, "ohjelmointi", "programming", v);

        w = v + "s.kaanna(\"ohjelmointi\");\n";
        assertEquals(v, "programming", kaanna(o, "ohjelmointi", v));
        w = v + "s.kaanna(\"programming\");\n";
        assertEquals(v, "ohjelmointi", kaanna(o, "programming", v));

        v += "s.poista(\"olut\")\n";

        poista(o, "olut", v);
        w = v + "s.kaanna(\"below\");\n";
        assertEquals(v, "alla oleva", kaanna(o, "below", v));
        w = v + "s.kaanna(\"beer\");\n";
        assertEquals(v, null, kaanna(o, "beer", v));
        w = v + "s.kaanna(\"olut\");\n";
        assertEquals(v, null, kaanna(o, "olut", v));
    }

    @Test
    @Points("10-13.3")
    public void eiLadataKuinVastaMetodissa() throws Throwable {

        String v = "Huomaa, että sanakirjaa ei ole vielä ladattu ja sanoja ei saisi löytyä!\n"
                + "MuistavaSanakirja s = new MuistavaSanakirja(\"sanat.txt\");\n";

        Object o = luo("sanat.txt", v);

        String w = v + "s.kaanna(\"apina\");\n";
        assertEquals(w, null, kaanna(o, "apina", w));
        w = v + "s.kaanna(\"alla oleva\");\n";
        assertEquals(w, null, kaanna(o, "alla oleva", w));
        w = v + "s.kaanna(\"monkey\");\n";
        assertEquals(w, null, kaanna(o, "monkey", w));
        w = v + "s.kaanna(\"below\");\n";
        assertEquals(w, null, kaanna(o, "below", w));
        w = v + "s.kaanna(\"ihminen\");\n";
        assertEquals(w, null, kaanna(o, "ihminen", w));
    }

    @Points("10-13.3")
    public void olematonSanakirjaTiedosto() throws Throwable {
        String v = ""
                + "MuistavaSanakirja s = new MuistavaSanakirja(\"tiedostoJotaEiOleOlemassa.txt\");\n"
                + "s.lataa();\n";

        Object o = luo("tiedostoJotaEiOleOlemassa.txt", v);

        assertEquals(v, true, lataa(o, v));

        String w = v + "s.kaanna(\"apina\");\n";
        assertEquals(w, null, kaanna(o, "apina", w));
        w = v + "s.kaanna(\"alla oleva\");\n";
        assertEquals(w, null, kaanna(o, "alla oleva", w));
        w = v + "s.kaanna(\"monkey\");\n";
        assertEquals(w, null, kaanna(o, "monkey", w));
        w = v + "s.kaanna(\"below\");\n";
        assertEquals(w, null, kaanna(o, "below", w));
        w = v + "s.kaanna(\"ihminen\");\n";
        assertEquals(w, null, kaanna(o, "ihminen", w));

        v += "s.lisaa(\"apina\", \"monkey\");\n";

        lisaa(o, "apina", "monkey", v);
        lisaa(o, "tietokone", "computer", v);

        w = v + "s.kaanna(\"apina\");\n";
        assertEquals(w, "monkey", kaanna(o, "apina", w));

    }

    /*
     *
     */
    @Test
    @Points("10-13.4")
    public void metodiTallenna() throws Throwable {
        String metodi = "tallenna";

        String v = "MuistavaSanakirja s = new MuistavaSanakirja(\"sanat.txt\");\n";

        Object olio = luo("sanat.txt", v);

        assertTrue("tee luokalle " + s(klassName) + " metodi public boolean " + metodi
                + "() ",
                klass.method(olio, metodi)
                        .returning(boolean.class)
                        .takingNoParams()
                        .isPublic());

        Class[] e = klass.method(olio, metodi)
                .returning(boolean.class)
                .takingNoParams().getMethod().getExceptionTypes();

        assertFalse("Luokan " + s(klassName) + " metodi public boolean " + metodi
                + "() ei saa heittää poikkeusta!\n"
                + "", e.length > 0);

        v = ""
                + "MuistavaSanakirja s = new MuistavaSanakirja(\"sanat.txt\");\n"
                + "s.lataa();\n"
                + "s.tallenna();\n";

        assertEquals(v, true, (boolean) klass.method(olio, metodi)
                .returning(boolean.class).takingNoParams()
                .withNiceError("\nVirheen aiheuttanut koodi" + v).invoke());
    }

    @Test
    @Points("10-13.4")
    public void sanakirjaTallentuuJosTiedostoaEiVielaOle() throws Throwable {
        String tiedosto = teeNimi();

        String v = ""
                + "MuistavaSanakirja s = new MuistavaSanakirja(\"" + tiedosto + "\");\n"
                + "s.lisaa(\"tietokone\", \"computer\");\n"
                + "s.tallenna();\n";

        Object o = luo(tiedosto, v);
        lisaa(o, "tietokone", "computer", v);
        assertEquals(v, true, tallenna(o, v));

        File f = new File(tiedosto);
        assertTrue("Seuraavan koodin pitäisi tallentaa sanakirja tiedostoon " + tiedosto + "\n"
                + v + "\n"
                + "tiedostoa ei luotu!", f.exists() && f.canRead());

        List<String> sisalto = lue(tiedosto);

        assertEquals("Koodilla\n" + v + "tallennettiin tiedostoon\n"
                + "--\n" + flatten(sisalto) + "--\n"
                + "rivien määrä ei ollut oikea", 1, sisalto.size());

        String rivi = sisalto.get(0).trim();

        assertTrue("Koodilla\n" + v + "tallennettiin tiedostoon\n"
                + "--\n" + flatten(sisalto) + "--\n"
                + "sisältö ei ollut oikea", rivi.equals("tietokone:computer") || rivi.equals("computer:tietokone"));
    }

    @Test
    @Points("10-13.4")
    public void olemassaolevassaSanakirjatiedostossaSailyvatSamatTiedot() throws Throwable {
        String tiedosto = "sanat.txt";

        String v = ""
                + "MuistavaSanakirja s = new MuistavaSanakirja(\"" + tiedosto + "\");\n"
                + "s.lataa();\n"
                + "s.kaanna(\"olut\");\n"
                + "s.tallenna();\n";

        teeTiedosto(tiedosto);

        Object o = luo(tiedosto, v);
        lataa(o, v);

        kaanna(o, "olut", v);

        assertEquals(v, true, tallenna(o, v));
        List<String> sisalto = lue(tiedosto);

        assertEquals("Koodilla\n" + v + "tallennettiin tiedostoon\n"
                + "--\n" + flatten(sisalto) + "--\n"
                + "rivien määrä ei ollut oikea", 3, sisalto.size());

        assertTrue("Koodilla\n" + v + "tallennettiin tiedostoon\n"
                + "--\n" + flatten(sisalto) + "--\n"
                + "sisältö ei ollut oikea sillä olut --> beer ei löydy", loytyy(sisalto, "olut", "beer"));

        assertTrue("Koodilla\n" + v + "tallennettiin tiedostoon\n"
                + "--\n" + flatten(sisalto) + "--\n"
                + "sisältö ei ollut oikea sillä apina --> monkey ei löydy", loytyy(sisalto, "apina", "monkey"));

        assertTrue("Koodilla\n" + v + "tallennettiin tiedostoon\n"
                + "--\n" + flatten(sisalto) + "--\n"
                + "sisältö ei ollut oikea sillä alla oleva --> below ei löydy", loytyy(sisalto, "alla oleva", "below"));
    }

    @Test
    @Points("10-13.4")
    public void muutoksetOlemassaolevaanSanakirjatiedostoonTallentuvat() throws Throwable {
        int luku = new Random().nextInt(10000) + 50;
        String tiedosto = "sanat-" + luku + ".txt";

        String data = "Kun tiedoston " + tiedosto + " sisältö on:\n"
                + "apina:monkey\n"
                + "alla oleva:below\n"
                + "olut:beer\n\n";
        String v = "MuistavaSanakirja s = new MuistavaSanakirja(\"" + tiedosto + "\");\n"
                + "s.lataa();\n"
                + "s.poista(\"below\");\n"
                + "s.lisaa(\"tieokone\", \"computer\");\n"
                + "s.tallenna();\n";

        teeTiedosto(tiedosto);

        Object o = luo(tiedosto, v);

        lataa(o, v);

        poista(o, "below", v);
        lisaa(o, "tietokone", "computer", v);

        assertEquals(v, true, tallenna(o, v));
        List<String> sisalto = lue(tiedosto);
        
        try {
            new File(tiedosto).delete();
        } catch (Exception e) {
            
        }

        assertEquals(data + "Koodilla\n" + v + "tallennettiin tiedostoon\n"
                + "--\n" + flatten(sisalto) + "--\n"
                + "rivien määrä ei ollut oikea", 3, sisalto.size());

        assertTrue(data + "Koodilla\n" + v + "tallennettiin tiedostoon\n"
                + "--\n" + flatten(sisalto) + "--\n"
                + "sisältö ei ollut oikea sillä olut --> beer ei löydy", loytyy(sisalto, "olut", "beer"));

        assertTrue(data + "Koodilla\n" + v + "tallennettiin tiedostoon\n"
                + "--\n" + flatten(sisalto) + "--\n"
                + "sisältö ei ollut oikea sillä apina --> monkey ei löydy", loytyy(sisalto, "apina", "monkey"));

        assertTrue(data + "Koodilla\n" + v + "tallennettiin tiedostoon\n"
                + "--\n" + flatten(sisalto) + "--\n"
                + "sisältö ei ollut oikea sillä tietokone --> computer ei löydy", loytyy(sisalto, "tietokone", "computer"));
    }

    /*
     *
     */
    public Object luo() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        return ctor.invoke();
    }

    public Object luo(String t, String v) throws Throwable {
        Reflex.MethodRef1<Object, Object, String> ctor = klass.constructor().taking(String.class).withNiceError();
        return ctor.withNiceError(v).invoke(t);
    }

    private void lisaa(Object o, String s, String w, String v) throws Throwable {
        klass.method(o, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke(s, w);
    }

    private String kaanna(Object o, String s, String v) throws Throwable {
        return klass.method(o, "kaanna")
                .returning(String.class).taking(String.class).withNiceError(v).invoke(s);
    }

    private boolean lataa(Object o, String v) throws Throwable {
        return klass.method(o, "lataa")
                .returning(boolean.class).takingNoParams().withNiceError(v).invoke();
    }

    private boolean tallenna(Object o, String v) throws Throwable {
        return klass.method(o, "tallenna")
                .returning(boolean.class).takingNoParams().withNiceError(v).invoke();
    }

    private void poista(Object o, String s, String v) throws Throwable {
        klass.method(o, "poista")
                .returningVoid().taking(String.class).withNiceError(v).invoke(s);
    }

    /*
     *
     */
    private String s(String klassName) {
        return klassName.substring(klassName.lastIndexOf(".") + 1);
    }

    private void saniteettitarkastus(String klassName, int n, String m) throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("et tarvitse \"stattisia muuttujia\", poista luokalta " + s(klassName) + " muuttuja " + kentta(field.toString(), s(klassName)), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("luokan kaikkien oliomuuttujien näkyvyyden tulee olla private, muuta luokalta " + s(klassName) + " löytyi: " + kentta(field.toString(), klassName), field.toString().contains("private"));
        }

        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("et tarvitse " + s(klassName) + "-luokalle kuin " + m + ", poista ylimääräiset", var <= n);
        }
    }

    private String kentta(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "").replace("java.io.", "");
    }

    private void teeTiedosto() {
        teeTiedosto("sanat.txt");
    }

    private void teeTiedosto(String tiedosto) {

        try {
            FileWriter kirjoittaja = new FileWriter(tiedosto);
            kirjoittaja.write("apina:monkey\n");
            kirjoittaja.write("alla oleva:below\n");
            kirjoittaja.write("olut:beer\n");
            kirjoittaja.close();
        } catch (Exception e) {
            fail("Testitiedoston " + tiedosto + " luominen epäonnistui.\nKokeile:\nFileWriter kirjoittaja = new FileWriter(\"" + tiedosto + "\");\n"
                    + "kirjoittaja.write(\"apina:monkey\\n\");\n"
                    + "kirjoittaja.write(\"alla oleva:below\\n\");\n"
                    + "kirjoittaja.write(\"olut:beer\\n\");\n"
                    + "kirjoittaja.close();");
        }
    }

    private List<String> lue(String tiedosto) throws FileNotFoundException {
        Scanner s = new Scanner(new File(tiedosto));
        ArrayList<String> rivit = new ArrayList<String>();

        while (s.hasNextLine()) {
            rivit.add(s.nextLine());
        }
        return rivit;
    }

    private String teeNimi() {
        Random arpa = new Random();
        int arvottu = arpa.nextInt(100000);
        return "test-" + arvottu + ".txt";
    }

    private String flatten(List<String> s) {
        String t = "";
        for (String string : s) {
            t += string + "\n";
        }
        return t;
    }

    private boolean loytyy(List<String> lista, String s, String w) {
        for (String rivi : lista) {
            if (rivi.equals(s + ":" + w)) {
                return true;
            }
            if (rivi.equals(w + ":" + s)) {
                return true;
            }
        }

        return false;
    }
}
