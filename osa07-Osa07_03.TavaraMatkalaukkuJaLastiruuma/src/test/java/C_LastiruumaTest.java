
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
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class C_LastiruumaTest<_Tavara, _Matkalaukku, _Lastiruuma> {

    private Class tavaraClass;
    private Constructor tavaraConstructor;
    private Method tavaraToString;
    private Class matkalaukkuClass;
    private Constructor matkalaukkuConstructor;
    private Class lastiruumaClass;
    private Constructor lastiruumaConstructor;
    String klassName = "Lastiruuma";
    Reflex.ClassRef<_Lastiruuma> _LastiruumaRef;
    Reflex.ClassRef<_Matkalaukku> _MatkalaukkuRef;
    Reflex.ClassRef<_Tavara> _TavaraRef;

    @Before
    public void setup() {
        _MatkalaukkuRef = Reflex.reflect("Matkalaukku");
        _TavaraRef = Reflex.reflect("Tavara");
        _LastiruumaRef = Reflex.reflect("Lastiruuma");

        try {
            tavaraClass = ReflectionUtils.findClass("Tavara");
            tavaraConstructor = ReflectionUtils.requireConstructor(tavaraClass, String.class, int.class);
            tavaraToString = ReflectionUtils.requireMethod(tavaraClass, "toString");

            matkalaukkuClass = ReflectionUtils.findClass("Matkalaukku");
            matkalaukkuConstructor = ReflectionUtils.requireConstructor(matkalaukkuClass, int.class);

            lastiruumaClass = ReflectionUtils.findClass("Lastiruuma");
            lastiruumaConstructor = ReflectionUtils.requireConstructor(lastiruumaClass, int.class);
        } catch (Throwable t) {
        }
    }

    @Test
    @Points("07-03.6")
    public void luokkaJulkinen() {
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", _MatkalaukkuRef.isPublic());
    }

    @Test
    @Points("07-03.6")
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus(klassName, 3, "maksimipainon sekä matkalaukut ja niiden painon tallettavat oliomuuttujat. Painon tallettava muuttujakaan ei ole ihan välttämätön!");
    }

    @Test
    @Points("07-03.6")
    public void testaaLastiruumaKonstruktori() throws Throwable {
        Reflex.MethodRef1<_Lastiruuma, _Lastiruuma, Integer> ctor = _LastiruumaRef.constructor().taking(int.class).withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "(int maksimipaino)", ctor.isPublic());
        String v = "virheen aiheutti koodi new Lastiruuma(10);";
        ctor.withNiceError(v).invoke(10);
    }

    public _Matkalaukku luoM(int paino) throws Throwable {
        return _MatkalaukkuRef.constructor().taking(int.class).withNiceError().invoke(paino);
    }

    public _Tavara luoT(String nimi, int paino) throws Throwable {
        return _TavaraRef.constructor().taking(String.class, int.class).withNiceError().invoke(nimi, paino);
    }

    public _Lastiruuma luoL(int paino) throws Throwable {
        return _LastiruumaRef.constructor().taking(int.class).withNiceError().invoke(paino);
    }

    @Test
    @Points("07-03.6")
    public void lastiruumanLisaaMatkalaukkuMetodi() throws Throwable {
        _Matkalaukku laukku = luoM(10);
        _Lastiruuma ruuma = luoL(100);

        String v = "\n"
                + "Matkalaukku m = new Matkalaukku(10);\n"
                + "Lastiruuma r = new Lastiruuma(100;\n)"
                + "r.lisaaMatkalaukku(m);";

        assertTrue("Luokalla Lastiruuma tulee olla metodi public void lisaaMatkalaukku(Matkalaukku laukku)", _LastiruumaRef.method(ruuma, "lisaaMatkalaukku").returningVoid().taking(_MatkalaukkuRef.cls()).withNiceError(v).isPublic());

        _LastiruumaRef.method(ruuma, "lisaaMatkalaukku").returningVoid().taking(_MatkalaukkuRef.cls()).withNiceError(v).invoke(laukku);
    }

    @Test
    @Points("07-03.6")
    public void lastiruumaJosEiMahduEiLisata() {
        try {
            Object lastiruuma = luoRuuma(20);
            Object ekaLaukku = luoMatkalaukku(10);
            lisaaLaukkuun(ekaLaukku, luoTavara("Porsas", 7));
            lisaaLaukkuun(ekaLaukku, luoTavara("Siili", 2));

            lisaaRuumaan(lastiruuma, ekaLaukku);

            Object tokaLaukku = luoMatkalaukku(10);
            lisaaLaukkuun(tokaLaukku, luoTavara("Kana", 5));
            lisaaLaukkuun(tokaLaukku, luoTavara("Kettu", 3));

            lisaaRuumaan(lastiruuma, tokaLaukku);

            Object kolmasLaukku = luoMatkalaukku(10);
            lisaaLaukkuun(kolmasLaukku, luoTavara("Kana", 5));
            lisaaLaukkuun(kolmasLaukku, luoTavara("Kettu", 3));

            lisaaRuumaan(lastiruuma, kolmasLaukku);

            Object laukut = oliomuuttujaLista(lastiruumaClass, lastiruuma);
            if (laukut == null) {
                fail("Varmista että luokalla Lastiruuma on oliomuuttujana lista (esim. ArrayList), johon matkalaukut lisätään.");
            }

            List<Object> ruumanLaukut = (List<Object>) laukut;

            if (!ruumanLaukut.contains(tokaLaukku)) {
                fail("Tarkista että laukut lisätään ruuman sisäiseen listarakenteeseen.");
            }

            if (ruumanLaukut.contains(kolmasLaukku)) {
                fail("Tarkista että ruumaan ei lisätä enempää kuin sinne mahtuu.");
            }

        } catch (Throwable t) {
            junit.framework.Assert.fail(t.getMessage());
        }
    }

    @Test
    @Points("07-03.6")
    public void lastiruumaVoidaanLisataMaksimipainoonAsti() {
        try {
            Object lastiruuma = luoRuuma(20);
            Object laukku = luoMatkalaukku(20);
            lisaaLaukkuun(laukku, luoTavara("Tiili", 20));
            lisaaRuumaan(lastiruuma, laukku);

            Object laukut = oliomuuttujaLista(lastiruumaClass, lastiruuma);
            if (laukut == null) {
                fail("Varmista että luokalla Lastiruuma on oliomuuttujana lista (esim. ArrayList), johon matkalaukut lisätään.");
            }

            List<Object> ruumanLaukut = (List<Object>) laukut;

            if (!ruumanLaukut.contains(laukku)) {
                fail("Tarkista että ruumaan voi lisätä matkalaukkuja ruuman maksimipainoon asti.");
            }
        } catch (Throwable t) {
            junit.framework.Assert.fail(t.getMessage());
        }
    }

    @Test
    @Points("07-03.6")
    public void tarkistaTulostus() {
        try {
            Object lastiruuma = luoRuuma(128);

            Object ekaLaukku = luoMatkalaukku(10);
            lisaaLaukkuun(ekaLaukku, luoTavara("Porsas", 7));
            lisaaLaukkuun(ekaLaukku, luoTavara("Siili", 2));

            lisaaRuumaan(lastiruuma, ekaLaukku);

            Object tokaLaukku = luoMatkalaukku(10);
            lisaaLaukkuun(tokaLaukku, luoTavara("Kana", 5));
            lisaaLaukkuun(tokaLaukku, luoTavara("Kettu", 3));

            lisaaRuumaan(lastiruuma, tokaLaukku);

            Object kolmasLaukku = luoMatkalaukku(10);
            lisaaLaukkuun(kolmasLaukku, luoTavara("Kana", 5));
            lisaaLaukkuun(kolmasLaukku, luoTavara("Kettu", 3));

            lisaaRuumaan(lastiruuma, kolmasLaukku);

            Method toString = ReflectionUtils.requireMethod(ReflectionUtils.findClass("Lastiruuma"), "toString");
            String palautus = ReflectionUtils.invokeMethod(String.class, toString, lastiruuma);

            if (!sisaltaa(palautus, "3", "matkalaukkua", "25", "kg")) {
                throw new Exception();
            }

        } catch (Throwable t) {
            junit.framework.Assert.fail("Tarkista että kolme matkalaukkua sisältävän lastiruuman toString-metodi palauttaa merkkijonon \"3 matkalaukkua (<paino> kg)\", missä <paino> on lastiruuman sisältämien laukkujen yhteispaino.");
        }
    }

    @Test
    @Points("07-03.7")
    public void lastiruumaTulostaTavaratMetodi() throws Throwable {

        _Matkalaukku laukku = luoM(10);
        _Lastiruuma ruuma = luoL(100);

        String v = "\n"
                + "Lastiruuma r = new Lastiruuma(100;\n)"
                + "r.tulostaTavarat();";

        assertTrue("Luokalla Lastiruuma tulee olla metodi public void tulostaTavarat()",
                _LastiruumaRef.method(ruuma, "tulostaTavarat").returningVoid().takingNoParams().withNiceError(v).isPublic());

        _LastiruumaRef.method(ruuma, "tulostaTavarat").returningVoid().takingNoParams().withNiceError(v).invoke();

    }

    @Test
    @Points("07-03.7")
    public void lastiruumaTarkistaTulostus() {
        MockInOut io = new MockInOut("");

        try {
            Object lastiruuma = luoRuuma(128);

            Object matkalaukku = luoMatkalaukku(10);
            lisaaLaukkuun(matkalaukku, luoTavara("Porsas", 7));
            lisaaLaukkuun(matkalaukku, luoTavara("Siili", 2));

            lisaaRuumaan(lastiruuma, matkalaukku);

            matkalaukku = luoMatkalaukku(10);
            lisaaLaukkuun(matkalaukku, luoTavara("Kana", 5));
            lisaaLaukkuun(matkalaukku, luoTavara("Kettu", 3));

            lisaaRuumaan(lastiruuma, matkalaukku);

            matkalaukku = luoMatkalaukku(10);
            lisaaLaukkuun(matkalaukku, luoTavara("Silli", 5));
            lisaaLaukkuun(matkalaukku, luoTavara("Siika", 3));

            lisaaRuumaan(lastiruuma, matkalaukku);

            Method m = ReflectionUtils.requireMethod(ReflectionUtils.findClass("Lastiruuma"), "tulostaTavarat");
            ReflectionUtils.invokeMethod(void.class, m, lastiruuma);

            if (!sisaltaa(io.getOutput(), "Porsas", "Siili", "Siika", "Silli", "Kana", "Kettu")) {
                throw new Exception();
            }

        } catch (Throwable t) {
            junit.framework.Assert.fail("Tarkista että Lastiruuman metodi tulostaTavarat tulostaa jokaisen ruumassa olevan laukun sisällön.");
        }
    }

    private void lisaaRuumaan(Object ruuma, Object laukku) {
        try {
            Method lisaaRuumaanMeto = ReflectionUtils.requireMethod(ReflectionUtils.findClass("Lastiruuma"), "lisaaMatkalaukku", ReflectionUtils.findClass("Matkalaukku"));
            ReflectionUtils.invokeMethod(void.class, lisaaRuumaanMeto, ruuma, laukku);
        } catch (Throwable ex) {
        }
    }

    private void lisaaLaukkuun(Object laukku, Object tavara) {
        try {
            Method lisaaTavaraMeto = ReflectionUtils.requireMethod(ReflectionUtils.findClass("Matkalaukku"), "lisaaTavara", ReflectionUtils.findClass("Tavara"));
            ReflectionUtils.invokeMethod(void.class, lisaaTavaraMeto, laukku, tavara);
        } catch (Throwable ex) {
        }
    }

    private Object luoRuuma(int kapasiteetti) {
        try {

            return ReflectionUtils.invokeConstructor(lastiruumaConstructor, kapasiteetti);
        } catch (Throwable ex) {
            return null;
        }
    }

    private Object luoMatkalaukku(int kapasiteetti) {
        try {
            return ReflectionUtils.invokeConstructor(matkalaukkuConstructor, kapasiteetti);
        } catch (Throwable ex) {
            return null;
        }
    }

    private Object luoTavara(String nimi, int paino) {
        try {
            return ReflectionUtils.invokeConstructor(tavaraConstructor, nimi, paino);
        } catch (Throwable ex) {
            return null;
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
                    Logger.getLogger(C_LastiruumaTest.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(C_LastiruumaTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (f.getType().equals(ArrayList.class)) {
                f.setAccessible(true);
                try {
                    return f.get(container);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(C_LastiruumaTest.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(C_LastiruumaTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (f.getType().equals(LinkedList.class)) {
                f.setAccessible(true);
                try {
                    return f.get(container);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(C_LastiruumaTest.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(C_LastiruumaTest.class.getName()).log(Level.SEVERE, null, ex);
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
