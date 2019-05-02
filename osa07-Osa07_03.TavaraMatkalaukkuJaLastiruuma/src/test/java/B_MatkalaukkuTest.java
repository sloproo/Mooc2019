
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class B_MatkalaukkuTest<_Tavara, _Matkalaukku> {

    private Class tavaraClass;
    private Constructor tavaraConstructor;
    private Class matkalaukkuClass;
    private Constructor matkalaukkuConstructor;
    String klassName = "Matkalaukku";
    Reflex.ClassRef<_Matkalaukku> _MatkalaukkuRef;
    Reflex.ClassRef<_Tavara> _TavaraRef;

    @Before
    public void setup() {
        _MatkalaukkuRef = Reflex.reflect("Matkalaukku");
        _TavaraRef = Reflex.reflect("Tavara");

        try {
            tavaraClass = ReflectionUtils.findClass("Tavara");
            tavaraConstructor = ReflectionUtils.requireConstructor(tavaraClass, String.class, int.class);

            matkalaukkuClass = ReflectionUtils.findClass("Matkalaukku");
            matkalaukkuConstructor = ReflectionUtils.requireConstructor(matkalaukkuClass, int.class);

        } catch (Throwable t) {
        }
    }

    @Test
    @Points("07-03.2")
    public void luokkaJulkinen() {
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", _MatkalaukkuRef.isPublic());
    }

    @Test
    @Points("07-03.2")
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus(klassName, 3, "maksimipainon sekä tavarat ja niiden painon tallettavat oliomuuttujat. Painon tallettava muuttujakaan ei ole ihan välttämätön!");
    }

    @Test
    @Points("07-03.2")
    public void testaaMatkalaukkuKonstruktori() throws Throwable {
        Reflex.MethodRef1<_Matkalaukku, _Matkalaukku, Integer> ctor = _MatkalaukkuRef.constructor().taking(int.class).withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "(int maksimipaino)", ctor.isPublic());
        String v = "virheen aiheutti koodi new Matkalaukku( 10);";
        ctor.withNiceError(v).invoke(10);
    }

    public _Matkalaukku luoM(int paino) throws Throwable {
        return _MatkalaukkuRef.constructor().taking(int.class).withNiceError().invoke(paino);
    }

    public _Tavara luoT(String nimi, int paino) throws Throwable {
        return _TavaraRef.constructor().taking(String.class, int.class).withNiceError().invoke(nimi, paino);
    }

    @Test
    @Points("07-03.2")
    public void lisaaTavaraMetodi() throws Throwable {
        _Tavara tavara = luoT("kirja", 1);
        _Matkalaukku laukku = luoM(10);

        String v = "\nTavara t = new Tavara(\"kirja\",1); \n"
                + "Matkalaukku m = new Matkalaukku(10);\n"
                + "m.lisaaTavara(t);";

        assertTrue("Luokalla Matkalaukku tulee olla metodi public void lisaaTavara(Tavara tavara)", _MatkalaukkuRef.method(laukku, "lisaaTavara").returningVoid().taking(_TavaraRef.cls()).withNiceError(v).isPublic());

        _MatkalaukkuRef.method(laukku, "lisaaTavara").returningVoid().taking(_TavaraRef.cls()).withNiceError(v).invoke(tavara);
    }

    @Test
    @Points("07-03.2")
    public void tarkistaLisaaTavaranToiminta() {
        try {
            Object matkalaukku = ReflectionUtils.invokeConstructor(matkalaukkuConstructor, 64);

            Method lisaaTavaraMeto = ReflectionUtils.requireMethod(matkalaukkuClass, "lisaaTavara", ReflectionUtils.findClass("Tavara"));

            Object tiili = ReflectionUtils.invokeConstructor(tavaraConstructor, "Tiili", 8);
            Object hammas = ReflectionUtils.invokeConstructor(tavaraConstructor, "Hammas", 8);
            ReflectionUtils.invokeMethod(void.class, lisaaTavaraMeto, matkalaukku, tiili);
            ReflectionUtils.invokeMethod(void.class, lisaaTavaraMeto, matkalaukku, hammas);

            List<Object> tavarat = (List<Object>) oliomuuttujaLista(matkalaukkuClass, matkalaukku);
            if (!tavarat.contains(tiili)) {
                fail("asd");
            }

            if (!tavarat.contains(hammas)) {
                fail("asd");
            }

        } catch (Throwable t) {
            junit.framework.Assert.fail("Tarkista että Matkalaukku-luokan metodi lisaaTavara lisää tavaroita matkalaukkuun. Onhan luokalle määritelty myös ArrayList, joka on alustettu?");
        }
    }

    @Test
    @Points("07-03.2")
    public void tarkistaLisaaTavaranToimintaKunEiMahdu() {
        try {
            Object matkalaukku = ReflectionUtils.invokeConstructor(matkalaukkuConstructor, 20);

            Method lisaaTavaraMeto = ReflectionUtils.requireMethod(matkalaukkuClass, "lisaaTavara", ReflectionUtils.findClass("Tavara"));

            Object tiili = ReflectionUtils.invokeConstructor(tavaraConstructor, "Tiili", 8);
            Object hammas = ReflectionUtils.invokeConstructor(tavaraConstructor, "Hammas", 8);
            Object porkkana = ReflectionUtils.invokeConstructor(tavaraConstructor, "Porkkana", 8);
            ReflectionUtils.invokeMethod(void.class, lisaaTavaraMeto, matkalaukku, tiili);
            ReflectionUtils.invokeMethod(void.class, lisaaTavaraMeto, matkalaukku, hammas);
            ReflectionUtils.invokeMethod(void.class, lisaaTavaraMeto, matkalaukku, porkkana);

            List<Object> tavarat = (List<Object>) oliomuuttujaLista(matkalaukkuClass, matkalaukku);
            if (tavarat.contains(porkkana)) {
                fail("asd");
            }

        } catch (Throwable t) {
            fail("Tarkista että Matkalaukku-luokan metodi lisaaTavara ei lisää uutta tavaraa jos laukkuun ei mahdu.");
        }
    }

    @Test
    @Points("07-03.2")
    public void tarkistaLisaaTavaranToimintaKunTasmalleenSamanpainoinen() {
        try {
            Object matkalaukku = ReflectionUtils.invokeConstructor(matkalaukkuConstructor, 20);

            Method lisaaTavaraMeto = ReflectionUtils.requireMethod(matkalaukkuClass, "lisaaTavara", ReflectionUtils.findClass("Tavara"));

            Object tiili = ReflectionUtils.invokeConstructor(tavaraConstructor, "Tiili", 20);
            ReflectionUtils.invokeMethod(void.class, lisaaTavaraMeto, matkalaukku, tiili);

            List<Object> tavarat = (List<Object>) oliomuuttujaLista(matkalaukkuClass, matkalaukku);
            if (!tavarat.contains(tiili)) {
                fail("asd");
            }

        } catch (Throwable t) {
            fail("Tarkista että Matkalaukku-luokan metodi lisaaTavara hyväksyy tavaran jonka lisäämisen jälkeen laukun yhteispaino on täsmälleen laukun maksimipaino.");
        }
    }

    @Test
    @Points("07-03.2")
    public void matkalaukkuToString() {
        String palautus = "";
        try {
            Object porkkanaLaatikko64 = ReflectionUtils.invokeConstructor(matkalaukkuConstructor, 20);
            Method matkalaukkuToString = ReflectionUtils.requireMethod(matkalaukkuClass, "toString");


            Method lisaaTavaraMeto = ReflectionUtils.requireMethod(matkalaukkuClass, "lisaaTavara", ReflectionUtils.findClass("Tavara"));
            ReflectionUtils.invokeMethod(void.class, lisaaTavaraMeto, porkkanaLaatikko64, ReflectionUtils.invokeConstructor(tavaraConstructor, "Uusi", 8));
            ReflectionUtils.invokeMethod(void.class, lisaaTavaraMeto, porkkanaLaatikko64, ReflectionUtils.invokeConstructor(tavaraConstructor, "Uusi", 8));
            ReflectionUtils.invokeMethod(void.class, lisaaTavaraMeto, porkkanaLaatikko64, ReflectionUtils.invokeConstructor(tavaraConstructor, "Uusi", 8));

            palautus = ReflectionUtils.invokeMethod(String.class, matkalaukkuToString, porkkanaLaatikko64);

            if (!sisaltaa(palautus, "2", "tavaraa", "16", "kg")) {
                fail("");
            }

        } catch (Throwable t) {
            junit.framework.Assert.fail("Tarkista että Matkalaukku-luokan metodi toString tulostaa matkalaukussa olevien tavaroiden tiedot (esim: \"3 tavaraa (15 kg)\".\n"
                    + "Kolmen 8 kilon tavaran lisäys 20:n maksimipainoiseen laukun toString: " + palautus);
        }
    }

    @Test
    @Points("07-03.3")
    public void matkalaukkuKielenhuolto() {
        try {
            Object porkkanaLaatikko64 = ReflectionUtils.invokeConstructor(matkalaukkuConstructor, 20);
            Method matkalaukkuToString = ReflectionUtils.requireMethod(matkalaukkuClass, "toString");
            Method lisaaTavaraMeto = ReflectionUtils.requireMethod(matkalaukkuClass, "lisaaTavara", ReflectionUtils.findClass("Tavara"));


            String palautus = ReflectionUtils.invokeMethod(String.class, matkalaukkuToString, porkkanaLaatikko64);
            if (!sisaltaa(palautus, "ei", "tavaroita", "0", "kg") && !sisaltaa(palautus, "tyhj", "0", "kg")) {
                fail("Varmista että tyhjän matkalaukun tulostus on muotoa \"ei tavaroita (0 kg)\"");
            }

            ReflectionUtils.invokeMethod(void.class, lisaaTavaraMeto, porkkanaLaatikko64, ReflectionUtils.invokeConstructor(tavaraConstructor, "Uusi", 8));

            palautus = ReflectionUtils.invokeMethod(String.class, matkalaukkuToString, porkkanaLaatikko64);
            if (!sisaltaa(palautus, "tavara", "8", "kg") || sisaltaa(palautus, "tavaraa")) {
                fail("Kun matkalaukussa on yksi tavara tulostuksen tulee olla muotoa \"1 tavara (<paino> kg)\", missä <paino> on matkalaukun paino.");
            }

            ReflectionUtils.invokeMethod(void.class, lisaaTavaraMeto, porkkanaLaatikko64, ReflectionUtils.invokeConstructor(tavaraConstructor, "Uusi", 8));
            palautus = ReflectionUtils.invokeMethod(String.class, matkalaukkuToString, porkkanaLaatikko64);

            if (!sisaltaa(palautus, "2", "tavaraa", "16", "kg")) {
                fail("Kun matkalaukussa on kaksi tavaraa tulostuksen tulee olla muotoa \"2 tavaraa (<paino> kg)\", missä <paino> on matkalaukun paino.");
            }

        } catch (Throwable t) {
            junit.framework.Assert.fail(t.getMessage());
        }
    }

    @Test
    @Points("07-03.4")
    public void tulostaTavaratMetodi() throws Throwable {
        _Matkalaukku laukku = luoM(10);

        String v = ""
                + "Matkalaukku m = new Matkalaukku(10);\n"
                + "m.tulostaTavarat();";

        assertTrue("Luokalla Matkalaukku tulee olla metodi public void tulostaTavarat()", _MatkalaukkuRef.method(laukku, "tulostaTavarat").returningVoid().takingNoParams().withNiceError(v).isPublic());

        _MatkalaukkuRef.method(laukku, "tulostaTavarat").returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("07-03.4")
    public void tulostaTavaratTulostaaOikein() {
        MockInOut io = new MockInOut("");

        try {
            Object matkalaukku = ReflectionUtils.invokeConstructor(matkalaukkuConstructor, 20);


            Class tavara = ReflectionUtils.findClass("Tavara");
            Constructor tavaraConst = ReflectionUtils.requireConstructor(tavara, String.class, int.class);

            Object porkkana = ReflectionUtils.invokeConstructor(tavaraConst, "Porkkana", 2);
            Object nauris = ReflectionUtils.invokeConstructor(tavaraConst, "Nauris", 4);
            Object kaali = ReflectionUtils.invokeConstructor(tavaraConst, "Kaali", 8);

            Method lisaaTavaraMeto = ReflectionUtils.requireMethod(matkalaukkuClass, "lisaaTavara", ReflectionUtils.findClass("Tavara"));
            ReflectionUtils.invokeMethod(void.class, lisaaTavaraMeto, matkalaukku, porkkana);
            ReflectionUtils.invokeMethod(void.class, lisaaTavaraMeto, matkalaukku, nauris);
            ReflectionUtils.invokeMethod(void.class, lisaaTavaraMeto, matkalaukku, kaali);


            Method yhteisPainoMeto = ReflectionUtils.requireMethod(matkalaukkuClass, "tulostaTavarat");
            ReflectionUtils.invokeMethod(void.class, yhteisPainoMeto, matkalaukku);

            if (!sisaltaa(io.getOutput(), "Porkkana", "Nauris", "Kaali", "2", "4", "8", "kg")) {
                throw new Exception();
            }

            if (io.getOutput().split("\n").length < 2) {
                throw new Exception();
            }

        } catch (Throwable t) {
            junit.framework.Assert.fail("Tarkista että metodi tulostaTavarat toimii oikein: se tulostaa tavarat yksitellen ruudulle."
                    + "\nLaukkuun lisättiin kolme tavaraa ja kutsuttiin tulostaTavarat(), tulostui:\n" + io.getOutput());
        }
    }

    @Test
    @Points("07-03.4")
    public void matkalaukkuYhteispainoMetodi() throws Throwable {
        _Matkalaukku laukku = luoM(10);

        String v = ""
                + "Matkalaukku m = new Matkalaukku(10);\n"
                + "m.yhteispaino();";

        assertTrue("Luokalla Matkalaukku tulee olla metodi public int yhteispaino()", _MatkalaukkuRef.method(laukku, "yhteispaino").returning(int.class).takingNoParams().withNiceError(v).isPublic());

        _MatkalaukkuRef.method(laukku, "yhteispaino").returning(int.class).takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("07-03.4")
    public void matkalaukkuYhteispainoMetodiAntaaOikeanArvon() {
        try {
            Object matkalaukku = ReflectionUtils.invokeConstructor(matkalaukkuConstructor, 20);

            Method lisaaTavaraMeto = ReflectionUtils.requireMethod(matkalaukkuClass, "lisaaTavara", ReflectionUtils.findClass("Tavara"));
            ReflectionUtils.invokeMethod(void.class, lisaaTavaraMeto, matkalaukku, ReflectionUtils.invokeConstructor(tavaraConstructor, "T1", 8));
            ReflectionUtils.invokeMethod(void.class, lisaaTavaraMeto, matkalaukku, ReflectionUtils.invokeConstructor(tavaraConstructor, "T2", 6));
            ReflectionUtils.invokeMethod(void.class, lisaaTavaraMeto, matkalaukku, ReflectionUtils.invokeConstructor(tavaraConstructor, "T3", 4));
            ReflectionUtils.invokeMethod(void.class, lisaaTavaraMeto, matkalaukku, ReflectionUtils.invokeConstructor(tavaraConstructor, "T3", 4));

            Method yhteisPainoMeto = ReflectionUtils.requireMethod(matkalaukkuClass, "yhteispaino");

            int arvo = ReflectionUtils.invokeMethod(int.class, yhteisPainoMeto, matkalaukku);
            if (arvo != 18) {
                throw new Exception();
            }
        } catch (Throwable t) {
            junit.framework.Assert.fail("Tarkista että luokan Matkalaukku yhteispaino toimii oikein. Sen tulee palauttaa laukussa olevien tavaroiden painojen summa.");
        }
    }

    @Test
    @Points("07-03.5")
    public void raskainTavaraTesti() throws Throwable {
        _Matkalaukku laukku = luoM(10);


        String v = "\nVirheen aiheuttanut koodi\n"
                + "Matkalaukku m = new Matkalaukku(10); "
                + "m.raskainTavara();";

        assertTrue("Luokalla Matkalaukku tulee olla metodi public Tavara raskainTavara()", _MatkalaukkuRef.method(laukku, "raskainTavara").returning(_TavaraRef.cls()).takingNoParams().withNiceError(v).isPublic());

        _MatkalaukkuRef.method(laukku, "raskainTavara").returning(_TavaraRef.cls()).takingNoParams().withNiceError(v).invoke();

    }

    @Test
    @Points("07-03.5")
    public void raskainTavaraLoytaaRaskaimman() {
        Object ret = null;
        try {
            Object matkalaukku = ReflectionUtils.invokeConstructor(matkalaukkuConstructor, 20);
            Constructor tavaraConst = ReflectionUtils.requireConstructor(tavaraClass, String.class, int.class);

            Object porkkana = ReflectionUtils.invokeConstructor(tavaraConst, "Porkkana", 2);
            Object nauris = ReflectionUtils.invokeConstructor(tavaraConst, "Nauris", 4);
            Object kaali = ReflectionUtils.invokeConstructor(tavaraConst, "Kaali", 8);

            Method lisaaTavaraMeto = ReflectionUtils.requireMethod(matkalaukkuClass, "lisaaTavara", ReflectionUtils.findClass("Tavara"));
            ReflectionUtils.invokeMethod(void.class, lisaaTavaraMeto, matkalaukku, porkkana);
            ReflectionUtils.invokeMethod(void.class, lisaaTavaraMeto, matkalaukku, kaali);
            ReflectionUtils.invokeMethod(void.class, lisaaTavaraMeto, matkalaukku, nauris);

            Method matkalaukkuRaskainTavara = ReflectionUtils.requireMethod(matkalaukkuClass, "raskainTavara");

            ret = ReflectionUtils.invokeMethod(tavaraClass, matkalaukkuRaskainTavara, matkalaukku);

            if (ret != kaali) {
                throw new Exception();
            }
        } catch (Throwable t) {
            junit.framework.Assert.fail("Metodin raskainTavara tulee palauttaa raskain tavara. Tarkasta koodi:\n"
                    + "Matkalaukku matkalaukku = new Matkalaukku(20);\n"
                    + "matkalaukku.lisaaTavara(new Tavara(\"Porkkana\", 2));\n"
                    + "matkalaukku.lisaaTavara(new Tavara(\"Kaali\", 8));\n"
                    + "matkalaukku.lisaaTavara(new Tavara(\"Nauris\", 4));\n"
                    + "Tavara raskain = matkalaukku.raskainTavara();\n"
                    + "palauttamasi tavara: "+ret);
        }
    }

    @Test
    @Points("07-03.5")
    public void raskainTavaraPalauttaaNullJosLaukkuTyhja() {
        try {
            Object matkalaukku = ReflectionUtils.invokeConstructor(matkalaukkuConstructor, 20);
            Method matkalaukkuRaskainTavara = ReflectionUtils.requireMethod(matkalaukkuClass, "raskainTavara");

            Object ret = matkalaukkuRaskainTavara.invoke(matkalaukku);

            if (ret != null) {
                throw new Exception();
            }
        } catch (Throwable t) {
            junit.framework.Assert.fail(t.getMessage() + "Metodin raskainTavara tulee palauttaa null-viite kun matkalaukku on tyhjä.");
        }
    }

    private boolean sisaltaa(String palautus, String... oletetutArvot) {
        for (String arvo : oletetutArvot) {
            if (!palautus.contains(arvo)) {
                return false;
            }
        }

        return true;
    }

    private Object oliomuuttujaLista(Class clazz, Object container) {
        for (Field f : clazz.getDeclaredFields()) {
            if (f.getType().equals(List.class)) {
                f.setAccessible(true);
                try {
                    return f.get(container);
                } catch (IllegalArgumentException ex) {
                } catch (IllegalAccessException ex) {
                }
            }

            if (f.getType().equals(ArrayList.class)) {
                f.setAccessible(true);
                try {
                    return f.get(container);
                } catch (IllegalArgumentException ex) {
                } catch (IllegalAccessException ex) {
                }
            }

            if (f.getType().equals(LinkedList.class)) {
                f.setAccessible(true);
                try {
                    return f.get(container);
                } catch (IllegalArgumentException ex) {
                } catch (IllegalAccessException ex) {
                }
            }
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
}
