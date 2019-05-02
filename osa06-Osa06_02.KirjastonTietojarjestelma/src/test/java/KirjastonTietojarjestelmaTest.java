
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import fi.helsinki.cs.tmc.edutestutils.Reflex.ClassRef;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KirjastonTietojarjestelmaTest<_Kirja> {

    private String kirjaLuokka = "Kirja";
    private String nimekeMetodi = "nimeke";
    private String julkaisijaMetodi = "julkaisija";
    private String julkaisuvuosiMetodi = "julkaisuvuosi";
    private String kirjastoLuokka = "Kirjasto";
    private String lisaaKirjaMetodi = "lisaaKirja";
    private String tulostaKirjatMetodi = "tulostaKirjat";
    private String haeKirjaNimekkeellaMetodi = "haeKirjaNimekkeella";
    private String haeKirjaJulkaisijallaMetodi = "haeKirjaJulkaisijalla";
    private String haeKirjaJulkaisuvuodellaMetodi = "haeKirjaJulkaisuvuodella";
    private String stringUtilsLuokka = "StringUtils";
    private String sisaltaaMetodi = "sisaltaa";
    private Class kirjaClass;
    private Class kirjastoClass;
    private Class stringUtilsClass;

    @Before
    public void setup() {
        try {
            kirjaClass = ReflectionUtils.findClass(kirjaLuokka);
            kirjastoClass = ReflectionUtils.findClass(kirjastoLuokka);
            stringUtilsClass = ReflectionUtils.findClass(stringUtilsLuokka);
        } catch (Throwable t) {
        }
    }

    @Test
    @Points("06-02.1")
    public void onLuokkaKirja() {
        String klassName = kirjaLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    @Points("06-02.1")
    public void eiYlimaaraisiaMuuttujiaLuokassaKirja() {
        saniteettitarkastus(kirjaLuokka, 3, "nimekkeen, julkaisijan ja julkaisuvuoden muistavat oliomuuttujat");
    }

    @Test
    @Points("06-02.1")
    public void luokallaKirjaKolmiparametrinenKonstruktori() throws Throwable {
        String klassName = kirjaLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Reflex.MethodRef3<Object, Object, String, String, Integer> ctor =
                klass.constructor().taking(String.class, String.class, int.class).withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "(String nimeke, String julkaisija, int julkaisuvuosi)", ctor.isPublic());
        ctor.invoke("Kalevala", "Karjalan Perinneseura", 1700);
    }

    @Test
    @Points("06-02.1")
    public void kirjanNimekeMetodi() throws Throwable {
        String metodi = nimekeMetodi;
        String klassName = kirjaLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Object olio = klass.constructor().taking(String.class, String.class, int.class).invoke("Kalevala", "Karjalan Perinneseura", 1700);;

        assertTrue("tee luokalle " + klassName + " metodi public String " + metodi + "() ", klass.method(olio, metodi)
                .returning(String.class).takingNoParams().isPublic());

        String c =
                "Kirja k = new Kirja(\"Kalevala\", \"Karjalan Perinneseura\", 1700); "
                + "k.nimeke();";

        String v = "\nVirheen aiheuttanut koodi " + c;

        assertEquals(c, "Kalevala", klass.method(olio, metodi).returning(String.class).takingNoParams().withNiceError(v).invoke());
    }

    @Test
    @Points("06-02.1")
    public void kirjanNimekeMetodi2() throws Throwable {
        String metodi = nimekeMetodi;
        String klassName = kirjaLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Object olio = klass.constructor().taking(String.class, String.class, int.class).
                invoke("Core Java", "Addison Wesley", 2012);

        String c =
                "Kirja k = new Kirja(\"Core Java\", \"Addison Wesley\", 2012); "
                + "k.nimeke();";

        String v = "\nVirheen aiheuttanut koodi " + c;

        assertEquals(c, "Core Java", klass.method(olio, metodi).returning(String.class).takingNoParams().withNiceError(v).invoke());
    }

    @Test
    @Points("06-02.1")
    public void kirjanJulkaisijaMetodi() throws Throwable {
        String metodi = julkaisijaMetodi;
        String klassName = kirjaLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Object olio = klass.constructor().taking(String.class, String.class, int.class).invoke("Kalevala", "Karjalan Perinneseura", 1700);;

        assertTrue("tee luokalle " + klassName + " metodi public String " + metodi + "() ", klass.method(olio, metodi)
                .returning(String.class).takingNoParams().isPublic());

        String c =
                "Kirja k = new Kirja(\"Kalevala\", \"Karjalan Perinneseura\", 1700); "
                + "k.haeJulkaisija();";

        String v = "\nVirheen aiheuttanut koodi " + c;

        assertEquals(c, "Karjalan Perinneseura", klass.method(olio, metodi).returning(String.class).takingNoParams().withNiceError(v).invoke());
    }

    @Test
    @Points("06-02.1")
    public void kirjanJulkasijaMetodi2() throws Throwable {
        String metodi = julkaisijaMetodi;
        String klassName = kirjaLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Object olio = klass.constructor().taking(String.class, String.class, int.class).
                invoke("Core Java", "Addison Wesley", 2012);

        String c =
                "Kirja k = new Kirja(\"Core Java\", \"Addison Wesley\", 2012); "
                + "k.haeJulkaisija();";

        String v = "\nVirheen aiheuttanut koodi " + c;

        assertEquals(c, "Addison Wesley", klass.method(olio, metodi).returning(String.class).takingNoParams().withNiceError(v).invoke());
    }

    @Test
    @Points("06-02.1")
    public void kirjanJulkaisuvuosiMetodi() throws Throwable {
        String metodi = julkaisuvuosiMetodi;
        String klassName = kirjaLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Object olio = klass.constructor().taking(String.class, String.class, int.class).invoke("Kalevala", "Karjalan Perinneseura", 1700);;

        assertTrue("tee luokalle " + klassName + " metodi public int " + metodi + "() ", klass.method(olio, metodi)
                .returning(int.class).takingNoParams().isPublic());

        String c =
                "Kirja k = new Kirja(\"Kalevala\", \"Karjalan Perinneseura\", 1700); "
                + "k.haeJulkaisuvuosi()();";

        String v = "\nVirheen aiheuttanut koodi " + c;

        assertEquals(c, 1700, (int) klass.method(olio, metodi).returning(int.class).takingNoParams().withNiceError(v).invoke());
    }

    @Test
    @Points("06-02.1")
    public void kirjanJulkasuvuosiMetodi2() throws Throwable {
        String metodi = julkaisuvuosiMetodi;
        String klassName = kirjaLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Object olio = klass.constructor().taking(String.class, String.class, int.class).
                invoke("Core Java", "Addison Wesley", 2012);

        String c =
                "Kirja k = new Kirja(\"Core Java\", \"Addison Wesley\", 2012); "
                + "k.haeJulkaisuvuosi();";

        String v = "\nVirheen aiheuttanut koodi " + c;

        assertEquals(c, 2012, (int) klass.method(olio, metodi).returning(int.class).takingNoParams().withNiceError(v).invoke());
    }

    @Test
    @Points("06-02.1")
    public void kirjallaJulkaisuvuosiMetodi() {
        try {
            if (ReflectionUtils.requireMethod(kirjaClass, julkaisuvuosiMetodi).getReturnType() != int.class) {
                fail("noes!");
            }
        } catch (Throwable t) {
            fail("Onhan luokalla " + kirjaLuokka + " metodi public int " + julkaisuvuosiMetodi + "()?");
        }
    }

    @Test
    @Points("06-02.1")
    public void kirjanMetodiToStringAntaaKirjanTiedot() throws Throwable {
        assertFalse("Tee luokalle Kirja toString-metodi", luoKirja("nimeke", "julkaisija", 51).toString().contains("@"));

        try {
            Object kirja = luoKirja("nimeke", "julkaisija", 51);
            String tulos = ReflectionUtils.invokeMethod(String.class, ReflectionUtils.requireMethod(kirjaClass, "toString"), kirja);

            Assert.assertTrue(tulos.contains("nimeke") && tulos.contains("julkaisija") && tulos.contains("" + 51));
        } catch (Throwable t) {
            fail("Tarkista että luokan Kirja metodi toString() palauttaa kirjan tiedot.");
        }
    }

    /*
     *
     */
    @Test
    @Points("06-02.2")
    public void onLuokkaKirjasto() {
        String klassName = kirjastoLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    @Points("06-02.2")
    public void eiYlimaaraisiaMuuttujiaLuokassaKirjasto() {
        saniteettitarkastus(kirjastoLuokka, 1, "kirjat tallettavan oliomuuttujan");
    }

    @Test
    @Points("06-02.2")
    public void luokallaKirjastoParametritonKonstruktori() throws Throwable {
        String klassName = kirjastoLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Reflex.MethodRef0<Object, Object> ctor =
                klass.constructor().takingNoParams().withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "()", ctor.isPublic());
        ctor.invoke();
    }

    @Test
    @Points("06-02.2")
    public void kirjastonLisaaKirjaMetodi() throws Throwable {
        String metodi = lisaaKirjaMetodi;
        String klassName = kirjastoLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Reflex.ClassRef<_Kirja> _KirjaRef = Reflex.reflect(kirjaLuokka);

        Object k = newKirjasto(klass);

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "(Kirja uusiKirja) ", klass.method(k, metodi)
                .returningVoid().taking(_KirjaRef.cls()).isPublic());

        _Kirja kirja = newKirja(_KirjaRef, "Kalevala", "Karjalan Perinneseura", 1700);

        String c = ""
                + "Kirjasto k = new Kirjasto(); \n"
                + "Kirja kirja = new Kirja(\"Kalevala\", \"Karjalan Perinneseura\", 1700);\n"
                + "k.lisaaKirja(kirja)";


        String v = "\nVirheen aiheuttanut koodi " + c;

        klass.method(k, metodi).returningVoid().taking(_KirjaRef.cls()).withNiceError(v).invoke(kirja);
    }

    @Test
    @Points("06-02.2")
    public void kirjastollaTulostaKirjatMetodi() throws Throwable {
        String metodi = tulostaKirjatMetodi;
        String klassName = kirjastoLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Object k = newKirjasto(klass);

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "() ", klass.method(k, metodi)
                .returningVoid().takingNoParams().isPublic());

        String c = ""
                + "Kirjasto k = new Kirjasto(); \n"
                + "k.tulostaKirjat()";

        String v = "\nVirheen aiheuttanut koodi " + c;

        klass.method(k, metodi).returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("06-02.2")
    public void kirjastonTulostaKirjat1() throws Throwable {
        String metodi = lisaaKirjaMetodi;
        String klassName = kirjastoLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Reflex.ClassRef<_Kirja> _KirjaRef = Reflex.reflect(kirjaLuokka);

        Object k = newKirjasto(klass);

        _Kirja kirja = newKirja(_KirjaRef, "Kalevala", "Karjalan Perinneseura", 1700);

        klass.method(k, metodi).returningVoid().taking(_KirjaRef.cls()).withNiceError().invoke(kirja);

        String c = ""
                + "Kirjasto k = new Kirjasto(); \n"
                + "Kirja kirja = new Kirja(\"Kalevala\", \"Karjalan Perinneseura\", 1700);\n"
                + "k.lisaaKirja(kirja);\n"
                + "k.tulostaKirat();\n";

        String v = "\nVirheen aiheuttanut koodi " + c;

        MockInOut io = new MockInOut("");

        klass.method(k, tulostaKirjatMetodi).returningVoid().takingNoParams().withNiceError(v).invoke();

        String out = io.getOutput();

        assertFalse("Koodi " + c + "ei tulosta mitään", out.isEmpty());
        assertTrue("Koodin " + c + "pitäisi tulostaa vaan yksi rivi. Nyt tulostui\n" + out, out.split("\n").length < 2);

        assertTrue("Tarkista että koodi " + c + "tulostaa lisätyn kirjan tiedot. Nyt tulostui\n" + out, out.contains("Kalevala"));
        assertTrue("Tarkista että koodi " + c + "tulostaa lisätyn kirjan tiedot. Nyt tulostui\n" + out, out.contains("Karjalan Perinneseura"));
        assertTrue("Tarkista että koodi " + c + "tulostaa lisätyn kirjan tiedot. Nyt tulostui\n" + out, out.contains("170"));

    }

    @Test
    @Points("06-02.2")
    public void kirjastonTulostaKirjat2() throws Throwable {
        String metodi = lisaaKirjaMetodi;
        String klassName = kirjastoLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Reflex.ClassRef<_Kirja> _KirjaRef = Reflex.reflect(kirjaLuokka);

        Object k = newKirjasto(klass);

        _Kirja kirja1 = newKirja(_KirjaRef, "Core Java", "Addison Wesley", 2001);
        _Kirja kirja2 = newKirja(_KirjaRef, "Test Driven Development", "Addison Wesley", 2001);
        _Kirja kirja3 = newKirja(_KirjaRef, "Java Poweruser Guide", "Prentice Hall", 2012);

        klass.method(k, metodi).returningVoid().taking(_KirjaRef.cls()).withNiceError().invoke(kirja1);
        klass.method(k, metodi).returningVoid().taking(_KirjaRef.cls()).withNiceError().invoke(kirja2);
        klass.method(k, metodi).returningVoid().taking(_KirjaRef.cls()).withNiceError().invoke(kirja3);

        String c = ""
                + "Kirjasto k = new Kirjasto(); \n"
                + "Kirja kirja1 = new Kirja(\"Core Java\", \"Addison Wesley\", 2001);\n"
                + "Kirja kirja2 = new Kirja(\"Test Driven Development\", \"Addison Wesley\", 2001);\n"
                + "Kirja kirja3 = new Kirja(\"Java Poweruser Guide\", \"Prentice Hall\", 2012);\n"
                + "k.lisaaKirja(kirja1);\n"
                + "k.lisaaKirja(kirja2);\n"
                + "k.lisaaKirja(kirja3);\n"
                + "k.tulostaKirat();\n";

        String v = "\nVirheen aiheuttanut koodi " + c;

        MockInOut io = new MockInOut("");

        klass.method(k, tulostaKirjatMetodi).returningVoid().takingNoParams().withNiceError(v).invoke();

        String out = io.getOutput();

        assertFalse("Koodi " + c + "ei tulosta mitään", out.isEmpty());
        assertTrue("Koodin " + c + "pitäisi tulostaa kolme riviä. Nyt tulostui\n" + out, out.split("\n").length==3);

        assertTrue("Tarkista että koodi " + c + "tulostaa lisätyn kirjan tiedot. Nyt tulostui\n" + out, out.contains("Prentice Hall"));
        assertTrue("Tarkista että koodi " + c + "tulostaa lisätyn kirjan tiedot. Nyt tulostui\n" + out, out.contains("Java Poweruser Guide"));
        assertTrue("Tarkista että koodi " + c + "tulostaa lisätyn kirjan tiedot. Nyt tulostui\n" + out, out.contains("Core Java"));
        assertTrue("Tarkista että koodi " + c + "tulostaa lisätyn kirjan tiedot. Nyt tulostui\n" + out, out.contains("Test Driven Development"));
    }

    /*
     *
     */

    @Test
    @Points("06-02.3")
    public void kirjastollaHaeNimekkeellaMetodi() throws Throwable {
        String metodi = haeKirjaNimekkeellaMetodi;
        String klassName = kirjastoLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Object k = newKirjasto(klass);

        assertTrue("tee luokalle " + klassName + " metodi public ArrayList<Kirja> " + metodi + "(String nimeke) ", klass.method(k, metodi)
                .returning(ArrayList.class).taking(String.class).isPublic());

        String c = ""
                + "Kirjasto k = new Kirjasto(); \n"
                + "k.haeNimekkeella(\"Java\")";

        String v = "\nVirheen aiheuttanut koodi " + c;

        klass.method(k, metodi).returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("Java");
    }

    @Test
    @Points("06-02.3")
    public void kirjastonHaeKirjaNimekkeella() throws Throwable {
        String metodi = lisaaKirjaMetodi;
        String klassName = kirjastoLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Reflex.ClassRef<_Kirja> _KirjaRef = Reflex.reflect(kirjaLuokka);

        Object k = newKirjasto(klass);

        _Kirja kirja1 = newKirja(_KirjaRef, "Core Java", "Addison Wesley", 2001);
        _Kirja kirja2 = newKirja(_KirjaRef, "Test Driven Development", "Addison Wesley", 2001);
        _Kirja kirja3 = newKirja(_KirjaRef, "Java Poweruser Guide", "Prentice Hall", 2012);

        klass.method(k, metodi).returningVoid().taking(_KirjaRef.cls()).withNiceError().invoke(kirja1);
        klass.method(k, metodi).returningVoid().taking(_KirjaRef.cls()).withNiceError().invoke(kirja2);
        klass.method(k, metodi).returningVoid().taking(_KirjaRef.cls()).withNiceError().invoke(kirja3);

        String c = ""
                + "Kirjasto k = new Kirjasto(); \n"
                + "Kirja kirja1 = new Kirja(\"Core Java\", \"Addison Wesley\", 2001);\n"
                + "Kirja kirja2 = new Kirja(\"Test Driven Development\", \"Addison Wesley\", 2001);\n"
                + "Kirja kirja3 = new Kirja(\"Java Poweruser Guide\", \"Prentice Hall\", 2012);\n"
                + "k.lisaaKirja(kirja1);\n"
                + "k.lisaaKirja(kirja2);\n"
                + "k.lisaaKirja(kirja3);\n";

        String code = c+"k.haeNimekkeella(\"Java\")\n";

        String v = "\nVirheen aiheuttanut koodi " + code;

        ArrayList lista = klass.method(k, haeKirjaNimekkeellaMetodi).returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("Java");

        assertFalse("Koodin\n"+code+"palauttaman lista on null", lista==null);
        assertEquals("Koodin\n"+code+"palauttaman listan pituus väärä",2, lista.size());
        assertTrue("Koodin\n"+code+"palauttama lista ei ole oikea. Lista on nyt "+lista.toString(),
                lista.toString().contains("Core Java") && lista.toString().contains("Java Poweruser Guide") &&
                lista.toString().contains("Addison Wesley") );

        code = c+"k.haeNimekkeella(\"Kahvi\")\n";

        v = "\nVirheen aiheuttanut koodi " + code;

        lista = klass.method(k, haeKirjaNimekkeellaMetodi).returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("Kahvi");

        assertFalse("Koodin\n"+code+"palauttaman lista on null", lista==null);
        assertEquals("Koodin\n"+code+"palauttaman listan pituus väärä",0, lista.size());

        code = c+"k.haeNimekkeella(\"Test Driven\")\n";

        v = "\nVirheen aiheuttanut koodi " + code;

        lista = klass.method(k, haeKirjaNimekkeellaMetodi).returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("Test Driven");

        assertFalse("Koodin\n"+code+"palauttaman lista on null", lista==null);
        assertEquals("Koodin\n"+code+"palauttaman listan pituus väärä",1, lista.size());
        assertTrue("Koodin\n"+code+"palauttama lista ei ole oikea. Lista on nyt "+lista.toString(),
                !lista.toString().contains("Core Java") && !lista.toString().contains("Java Poweruser Guide") &&
                lista.toString().contains("Test Driven Development") );
    }

    @Test
    @Points("06-02.3")
    public void kirjastollaHaeNimekkeellaJulkaisijalla() throws Throwable {
        String metodi = haeKirjaJulkaisijallaMetodi;
        String klassName = kirjastoLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Object k = newKirjasto(klass);

        assertTrue("tee luokalle " + klassName + " metodi public ArrayList<Kirja> " + metodi + "(String julkaisija) ", klass.method(k, metodi)
                .returning(ArrayList.class).taking(String.class).isPublic());

        String c = ""
                + "Kirjasto k = new Kirjasto(); \n"
                + "k.haeNimekkeella(\"Microsoft Press\")";

        String v = "\nVirheen aiheuttanut koodi " + c;

        klass.method(k, metodi).returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("Microsoft Press");
    }

    @Test
    @Points("06-02.3")
    public void kirjastonHaeJulkaisijalla() throws Throwable {
        String metodi = lisaaKirjaMetodi;
        String klassName = kirjastoLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Reflex.ClassRef<_Kirja> _KirjaRef = Reflex.reflect(kirjaLuokka);

        Object k = newKirjasto(klass);

        _Kirja kirja1 = newKirja(_KirjaRef, "Core Java", "Addison Wesley", 2001);
        _Kirja kirja2 = newKirja(_KirjaRef, "Test Driven Development", "Addison Wesley", 2001);
        _Kirja kirja3 = newKirja(_KirjaRef, "Java Poweruser Guide", "Prentice Hall", 2012);

        klass.method(k, metodi).returningVoid().taking(_KirjaRef.cls()).withNiceError().invoke(kirja1);
        klass.method(k, metodi).returningVoid().taking(_KirjaRef.cls()).withNiceError().invoke(kirja2);
        klass.method(k, metodi).returningVoid().taking(_KirjaRef.cls()).withNiceError().invoke(kirja3);

        String c = ""
                + "Kirjasto k = new Kirjasto(); \n"
                + "Kirja kirja1 = new Kirja(\"Core Java\", \"Addison Wesley\", 2001);\n"
                + "Kirja kirja2 = new Kirja(\"Test Driven Development\", \"Addison Wesley\", 2001);\n"
                + "Kirja kirja3 = new Kirja(\"Java Poweruser Guide\", \"Prentice Hall\", 2012);\n"
                + "k.lisaaKirja(kirja1);\n"
                + "k.lisaaKirja(kirja2);\n"
                + "k.lisaaKirja(kirja3);\n";

        String code = c+"k.haeKirjaJulkaisijalla(\"Addison Wesley\")\n";

        String v = "\nVirheen aiheuttanut koodi " + code;

        ArrayList lista = klass.method(k, haeKirjaJulkaisijallaMetodi).returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("Addison Wesley");

        assertFalse("Koodin\n"+code+"palauttaman lista on null", lista==null);
        assertEquals("Koodin\n"+code+"palauttaman listan pituus väärä",2, lista.size());
        assertTrue("Koodin\n"+code+"palauttama lista ei ole oikea. Lista on nyt "+lista.toString(),
                lista.toString().contains("Core Java") && lista.toString().contains("Test Driven Development") &&
                lista.toString().contains("Addison Wesley") );

        code = c+"k.haeKirjaJulkaisijalla(\"Springer Verlag\")\n";

        v = "\nVirheen aiheuttanut koodi " + code;

        lista = klass.method(k, haeKirjaJulkaisijallaMetodi).returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("Springer Verlag");

        assertFalse("Koodin\n"+code+"palauttaman lista on null", lista==null);
        assertEquals("Koodin\n"+code+"palauttaman listan pituus väärä",0, lista.size());

        code = c+"k.haeKirjaJulkaisijalla(\"Prentice Hall\")\n";

        v = "\nVirheen aiheuttanut koodi " + code;

        lista = klass.method(k, haeKirjaJulkaisijallaMetodi).returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("Prentice Hall");

        assertFalse("Koodin\n"+code+"palauttaman lista on null", lista==null);
        assertEquals("Koodin\n"+code+"palauttaman listan pituus väärä",1, lista.size());
        assertTrue("Koodin\n"+code+"palauttama lista ei ole oikea. Lista on nyt "+lista.toString(),
                !lista.toString().contains("Core Java") && lista.toString().contains("Java Poweruser Guide") &&
                !lista.toString().contains("Test Driven Development") );
    }

    @Test
    @Points("06-02.3")
    public void kirjastollaHaeNimekkeellaJulkaisuvuodella() throws Throwable {
        String metodi = haeKirjaJulkaisuvuodellaMetodi;
        String klassName = kirjastoLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Object k = newKirjasto(klass);

        assertTrue("tee luokalle " + klassName + " metodi public ArrayList<Kirja> " + metodi + "(int julkaisuvuosi) ", klass.method(k, metodi)
                .returning(ArrayList.class).taking(int.class).isPublic());

        String c = ""
                + "Kirjasto k = new Kirjasto(); \n"
                + "k.haeNimekkeella(\"2012\")";

        String v = "\nVirheen aiheuttanut koodi " + c;

        klass.method(k, metodi).returning(ArrayList.class).taking(int.class).withNiceError(v).invoke(2012);
    }

    @Test
    @Points("06-02.3")
    public void kirjastonHaeJulkaisuvuodella() throws Throwable {
        String metodi = lisaaKirjaMetodi;
        String klassName = kirjastoLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Reflex.ClassRef<_Kirja> _KirjaRef = Reflex.reflect(kirjaLuokka);

        Object k = newKirjasto(klass);

        _Kirja kirja1 = newKirja(_KirjaRef, "Core Java", "Addison Wesley", 2012);
        _Kirja kirja2 = newKirja(_KirjaRef, "Test Driven Development", "Addison Wesley", 2001);
        _Kirja kirja3 = newKirja(_KirjaRef, "Java Poweruser Guide", "Prentice Hall", 2001);

        klass.method(k, metodi).returningVoid().taking(_KirjaRef.cls()).withNiceError().invoke(kirja1);
        klass.method(k, metodi).returningVoid().taking(_KirjaRef.cls()).withNiceError().invoke(kirja2);
        klass.method(k, metodi).returningVoid().taking(_KirjaRef.cls()).withNiceError().invoke(kirja3);

        String c = ""
                + "Kirjasto k = new Kirjasto(); \n"
                + "Kirja kirja1 = new Kirja(\"Core Java\", \"Addison Wesley\", 2012);\n"
                + "Kirja kirja2 = new Kirja(\"Test Driven Development\", \"Addison Wesley\", 2001);\n"
                + "Kirja kirja3 = new Kirja(\"Java Poweruser Guide\", \"Prentice Hall\", 2001);\n"
                + "k.lisaaKirja(kirja1);\n"
                + "k.lisaaKirja(kirja2);\n"
                + "k.lisaaKirja(kirja3);\n";

        String code = c+"k.haeJulkaisuvuodella(2001)\n";

        String v = "\nVirheen aiheuttanut koodi " + code;

        ArrayList lista = klass.method(k, haeKirjaJulkaisuvuodellaMetodi).returning(ArrayList.class).taking(int.class).withNiceError(v).invoke(2001);

        assertFalse("Koodin\n"+code+"palauttaman lista on null", lista==null);
        assertEquals("Koodin\n"+code+"palauttaman listan pituus väärä",2, lista.size());
        assertTrue("Koodin\n"+code+"palauttama lista ei ole oikea. Lista on nyt "+lista.toString(),
                lista.toString().contains("Java Poweruser Guide") && lista.toString().contains("Test Driven Development") &&
                lista.toString().contains("Addison Wesley") );

        code = c+"k.haeJulkaisuvuodella(1952)\n";

        v = "\nVirheen aiheuttanut koodi " + code;

        lista = klass.method(k, haeKirjaJulkaisuvuodellaMetodi).returning(ArrayList.class).taking(int.class).withNiceError(v).invoke(1952);

        assertFalse("Koodin\n"+code+"palauttaman lista on null", lista==null);
        assertEquals("Koodin\n"+code+"palauttaman listan pituus väärä",0, lista.size());

        code = c+"k.haeJulkaisuvuodella(2012)\n";

        v = "\nVirheen aiheuttanut koodi " + code;

        lista = klass.method(k, haeKirjaJulkaisuvuodellaMetodi).returning(ArrayList.class).taking(int.class).withNiceError(v).invoke(2012);

        assertFalse("Koodin\n"+code+"palauttaman lista on null", lista==null);
        assertEquals("Koodin\n"+code+"palauttaman listan pituus väärä",1, lista.size());
        assertTrue("Koodin\n"+code+"palauttama lista ei ole oikea. Lista on nyt "+lista.toString(),
                lista.toString().contains("Core Java") && !lista.toString().contains("Java Poweruser Guide") &&
                !lista.toString().contains("Test Driven Development") );
    }

    /*
     *
     */

    @Test
    @Points("06-02.4")
    public void onLuokkaStringUtils() {
        String klassName = stringUtilsLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", klass.isPublic());
    }



    @Test
    @Points("06-02.4")
    public void luokallaStringUtilsMetodiSisaltaa() throws Throwable {
        String metodi = sisaltaaMetodi;
        String klassName = stringUtilsLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        assertTrue("Tee luokalle StringUtils public static boolean sisaltaa(String sana, String haettava)",klass.staticMethod(metodi).returning(boolean.class).taking(String.class, String.class).isPublic());

        String v = "Ongelman aiheutti StringUtils.sisaltaa(\"Vihavainen\", \"avain\");";
        klass.staticMethod(metodi).returning(boolean.class).taking(String.class, String.class).withNiceError(v).invoke("Vihavainen", "avain");
    }

    @Test
    @Points("06-02.4")
    public void luokallaStringUtilsStaattinenMetodiSisaltaa() {
        try {
            Method m = ReflectionUtils.requireMethod(stringUtilsClass, sisaltaaMetodi, String.class, String.class);

            if (!Modifier.isStatic(m.getModifiers())) {
                fail("ei-static");
            }
        } catch (Throwable t) {
            fail("Onhan luokan " + stringUtilsLuokka + " metodi public static boolean sisaltaa(String sana, String haettava) varmasti staattinen?");
        }
    }

    @Test
    @Points("06-02.4")
    public void luokanStringUtilsMetodiSisaltaaParametritOikeinPain() {
        try {
            Method m = ReflectionUtils.requireMethod(stringUtilsClass, sisaltaaMetodi, String.class, String.class);

            if (ReflectionUtils.invokeMethod(boolean.class, m, null, "abba", "ABBABBA")) {
                fail("ei-ole");
            }

        } catch (Throwable t) {
            fail("Metodikutsun " + stringUtilsLuokka + ".sisaltaa(\"abba\", \"ABBABBA\") pitäisi palauttaa false. Merkkijono abba ei sisällä merkkijonoa ABBABBA");
        }
    }

    @Test
    @Points("06-02.4")
    public void luokanStringUtilsMetodiSisaltaaEiValitaMerkkienKoosta() {
        try {
            Method m = ReflectionUtils.requireMethod(stringUtilsClass, sisaltaaMetodi, String.class, String.class);

            if (!ReflectionUtils.invokeMethod(boolean.class, m, null, "ABBABBA", "abba")) {
                fail("ei-ole");
            }

        } catch (Throwable t) {
            fail("Metodikutsun " + stringUtilsLuokka + ".sisaltaa(\"ABBABBA\", \"abba\") pitäisi palauttaa true. Merkkijono ABBABBA sisältää merkkijonon abba kun merkkien koosta ei välitetä.");
        }
    }

    @Test
    @Points("06-02.4")
    public void luokanStringUtilsMetodiSisaltaaTrimmaa() {
        try {
            Method m = ReflectionUtils.requireMethod(stringUtilsClass, sisaltaaMetodi, String.class, String.class);

            if (!ReflectionUtils.invokeMethod(boolean.class, m, null, "ABBABBA", "  abba")) {
                fail("ei-ole");
            }

        } catch (Throwable t) {
            fail("Metodikutsun " + stringUtilsLuokka + ".sisaltaa(\"ABBABBA\", \"  abba\") pitäisi palauttaa true. Merkkijono ABBABBA sisältää merkkijonon abba kun ylimääräiset välilyönnit on poistettu.");
        }
    }

    @Test
    @Points("06-02.4")
    public void stringUtilsKayttaaTrimMetodia() {
        stringUtilsKayttaaTrimMetodiaLukija();
    }

    @Test
    @Points("06-02.4")
    public void kirjastoKayttaaStringUtilsia() {
        kirjastoKayttaaStringUtilsiaLukija();
    }

    private Object luoKirja(String nimeke, String julkaisija, int julkaisuvuosi) throws Throwable {
        Constructor kirjaConstructor = ReflectionUtils.requireConstructor(kirjaClass, String.class, String.class, int.class);
        return ReflectionUtils.invokeConstructor(kirjaConstructor, nimeke, julkaisija, julkaisuvuosi);
    }

    private Object luoKirjasto() throws Throwable {
        return ReflectionUtils.invokeConstructor(ReflectionUtils.requireConstructor(kirjastoClass));
    }

    private void lisaaKirjastoon(Object kirjasto, Object kirja) throws Throwable {
        Method lisaaKirja = ReflectionUtils.requireMethod(kirjastoClass, lisaaKirjaMetodi, kirjaClass);
        ReflectionUtils.invokeMethod(void.class, lisaaKirja, kirjasto, kirja);
    }

    private Object luoKirjastoKirjoilla() throws Throwable {
        Object kirjasto = luoKirjasto();
        lisaaKirjastoon(kirjasto, luoKirja("How to Write a How to Write Book", "Brian Paddock", 1955));
        lisaaKirjastoon(kirjasto, luoKirja("The Broken Window", "Eva Brick", 1974));
        lisaaKirjastoon(kirjasto, luoKirja("TheUpperCaseAndLowerCaseMYSTERY", "El Barto", 2012));
        return kirjasto;
    }

    private void kirjastoKayttaaStringUtilsiaLukija() {
        try {
            Scanner lukija = new Scanner(new File("src/main/java/Kirjasto.java"));
            boolean stringUtils = false;
            while (lukija.hasNext()) {
                String rivi = lukija.nextLine();
                if (rivi.contains("StringUtils")) {
                    stringUtils = true;
                    break;
                }
            }

            if (!stringUtils) {
                fail("Muunna luokkaa Kirjasto siten, että se käyttää juuri luomaasi StringUtils-apuluokkaa kirjojen tietojen tarkistamiseen.");
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    private void stringUtilsKayttaaTrimMetodiaLukija() {
        try {
            Scanner lukija = new Scanner(new File("src/main/java/StringUtils.java"));
            boolean trim = false;
            while (lukija.hasNext()) {
                String rivi = lukija.nextLine();
                if (rivi.contains("trim")) {
                    trim = true;
                    break;
                }
            }

            if (!trim) {
                fail("Käytä String-luokan trim-metodia merkkijonojen alussa ja lopussa olevien välilyöntien poistamiseen!");
            }
        } catch (Exception e) {
            fail(e.getMessage());
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
        return toString.replace(klassName + ".", "").replace("java.lang.", "");
    }

    private Object newKirjasto(ClassRef<Object> klass) throws Throwable {
        Object olio = klass.constructor().takingNoParams().invoke();
        return olio;
    }

    private _Kirja newKirja(ClassRef<_Kirja> _KirjaRef, String n, String j, int v) throws Throwable {
        return _KirjaRef.constructor().taking(String.class, String.class, int.class).invoke(n, j, v);
    }
}
