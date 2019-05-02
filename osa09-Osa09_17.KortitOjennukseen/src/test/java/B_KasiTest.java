

import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class B_KasiTest<_Kasi> {

    String klassName = "Kasi";
    String fullName = klassName;
    Reflex.ClassRef<Object> klass;
    Class kasiClass;

    @Before
    public void setUp() {
        klass = Reflex.reflect(fullName);

        try {
            kasiClass = ReflectionUtils.findClass(fullName);
        } catch (Throwable t) {
            fail("Onhan tehtävässä luokka \"" + klassName + "\"?");
        }

        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    @Points("09-17.2")
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus(fullName, 1, "kortit tallentavan oliomuuttujan");
    }

    @Test
    @Points("09-17.2")
    public void onKonstruktori() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "()", ctor.isPublic());
        String v = "virheen aiheutti koodi new Kasi();";
        ctor.withNiceError(v).invoke();
    }

    public Object luo() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        return ctor.invoke();
    }

    @Test
    @Points("09-17.2")
    public void metodiLisaa() throws Throwable {
        String metodi = "lisaa";

        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "(Kortti k)",
                klass.method(olio, metodi)
                        .returningVoid().taking(Kortti.class).isPublic());

        String v = "Kasi kasi = new Kasi();\n"
                + "Kortti kortti = new Kortti(12,Maa.HERTTA );  \n"
                + "kasi.lisaa(kortti);";

        klass.method(olio, metodi)
                .returningVoid().taking(Kortti.class).withNiceError("virheen aiheutti koodi \n" + v).
                invoke(new Kortti(12, Maa.HERTTA));
    }

    private void lisaa(Object olio, Kortti k) throws Throwable {
        klass.method(olio, "lisaa")
                .returningVoid()
                .taking(Kortti.class)
                .invoke(k);
    }

    @Test
    @Points("09-17.2")
    public void metodiTulosta() throws Throwable {
        String metodi = "tulosta";

        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "()",
                klass.method(olio, metodi)
                        .returningVoid().takingNoParams().isPublic());

        String v = "Kasi kasi = new Kasi();\n"
                + "kasi.tulosta();";

        klass.method(olio, metodi)
                .returningVoid().takingNoParams().withNiceError("virheen aiheutti koodi \n" + v).
                invoke();
    }

    @Test
    @Points("09-17.2")
    public void tulostusToimii() throws Throwable {
        MockInOut io = new MockInOut("");

        Object olio = luo();

        Kortti k = new Kortti(12, Maa.HERTTA);

        lisaa(olio, k);

        String v = "Kasi kasi = new Kasi();\n"
                + "Kortti kortti = new Kortti(12,Maa.HERTTA); \n"
                + "kasi.lisaa(kortti);\n"
                + "kasi.tulosta()\n";

        klass.method(olio, "tulosta")
                .returningVoid().takingNoParams().withNiceError("virheen aiheutti koodi \n" + v).
                invoke();

        String out = io.getOutput();

        assertTrue("Koodilla \n" + v + "tulostui\n" + out, out.contains(k.toString()));

        io = new MockInOut("");
        Kortti k2 = new Kortti(2, Maa.PATA);

        lisaa(olio, k2);
        Kortti k3 = new Kortti(14, Maa.RUUTU);

        lisaa(olio, k3);

        v = "Kasi kasi = new Kasi();\n"
                + "kasi.lisaa( new Kortti(12,Maa.HERTTA) ); \n"
                + "kasi.lisaa( new Kortti(2,Maa.PATA) ); \n"
                + "kasi.lisaa( new Kortti(14,Maa.RUUTU) ); \n"
                + "kasi.tulosta()\n";

        klass.method(olio, "tulosta")
                .returningVoid().takingNoParams().withNiceError("virheen aiheutti koodi \n" + v).
                invoke();

        out = io.getOutput();

        assertTrue("Koodilla \n" + v + "pitäisi tulostaa 3 riviä, nyt tulostus oli\n" + out, out.split("\n").length > 2);

        assertTrue("Koodilla \n" + v + "tulostui\n" + out, out.contains(k.toString()));
        assertTrue("Koodilla \n" + v + "tulostui\n" + out, out.contains(k2.toString()));
        assertTrue("Koodilla \n" + v + "tulostui\n" + out, out.contains(k3.toString()));

    }

    /*
     *
     */
    @Test
    @Points("09-17.3")
    public void metodiJarjesta() throws Throwable {
        String metodi = "jarjesta";

        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "()",
                klass.method(olio, metodi)
                        .returningVoid().takingNoParams().isPublic());

        String v = "Kasi kasi = new Kasi();\n"
                + "kasi.tulosta();";

        klass.method(olio, metodi)
                .returningVoid().takingNoParams().withNiceError("virheen aiheutti koodi \n" + v).
                invoke();
    }

    @Test
    @Points("09-17.3")
    public void jarjestettyTulostusToimii() throws Throwable {
        MockInOut io = new MockInOut("");

        Object olio = luo();

        Kortti k = new Kortti(12, Maa.HERTTA);
        Kortti k3 = new Kortti(14, Maa.RUUTU);
        Kortti k2 = new Kortti(2, Maa.PATA);
        Kortti k4 = new Kortti(2, Maa.RISTI);

        lisaa(olio, k);
        lisaa(olio, k3);
        lisaa(olio, k2);
        lisaa(olio, k4);

        String v = "Kasi kasi = new Kasi();\n"
                + "kasi.lisaa( new Kortti(12,Maa.HERTTA) ); \n"
                + "kasi.lisaa( new Kortti(14,Maa.RUUTU) ); \n"
                + "kasi.lisaa( new Kortti(2,Maa.PATA) ); \n"
                + "kasi.lisaa( new Kortti(2,Maa.RISTI) ); \n"
                + "kasi.jarjesta();\n"
                + "kasi.tulosta()\n";

        klass.method(olio, "jarjesta")
                .returningVoid().takingNoParams().withNiceError("virheen aiheutti koodi \n" + v).
                invoke();

        klass.method(olio, "tulosta")
                .returningVoid().takingNoParams().withNiceError("virheen aiheutti koodi \n" + v).
                invoke();

        String out = io.getOutput();

        assertTrue("Koodilla \n" + v + "pitäisi tulostaa 4 riviä, nyt tulostus oli\n" + out, out.split("\n").length > 3);

        int j1 = out.indexOf(k4.toString());
        int j2 = out.indexOf(k2.toString());
        int j3 = out.indexOf(k.toString());
        int j4 = out.indexOf(k3.toString());

        assertTrue("Jokainen kortti ei tulostunut koodilla \ntulostus oli\n" + out, j1 > -1 && j2 > -1 && j3 > -1 && j4 > -1);

        assertTrue("Koodilla \n" + v + "ensin pitäisi tulostua " + k4 + "\ntulostus oli\n" + out, j1 < j2 && j1 < j3 && j1 < j4);
        assertTrue("Koodilla \n" + v + "toisena pitäisi tulostua " + k2 + "\ntulostus oli\n" + out, j2 < j3 && j2 < j4);
        assertTrue("Koodilla \n" + v + "kolmantena pitäisi tulostua " + k + "\ntulostus oli\n" + out, j3 < j4);
    }

    /*
     *  käsi comparable
     */
    @Points("09-17.4")
    @Test
    public void onImplementComparableKasi() {
        Class kl;
        try {
            kl = ReflectionUtils.findClass(fullName);
            Class is[] = kl.getInterfaces();
            Class oikein[] = {java.lang.Comparable.class};
            for (int i = 0; i < is.length; i++) {
            }
            assertTrue("Varmista että " + klassName + " toteuttaa (vain!) rajapinnan Comparable",
                    Arrays.equals(is, oikein));

        } catch (Throwable t) {
            fail("Toteuttaahan luokka " + klassName + " rajapinnan \"Comparable<Kasi>\"");
        }
    }

    @Points("09-17.4")
    @Test
    public void onCompareToMetodi() throws Throwable {
        String metodi = "compareTo";

        Reflex.ClassRef<_Kasi> _KasiRef = Reflex.reflect(fullName);

        _Kasi k1 = _KasiRef.constructor().takingNoParams().invoke();
        _Kasi k2 = _KasiRef.constructor().takingNoParams().invoke();

        assertTrue("tee luokalle " + klassName + " metodi public int " + metodi + "(Kasi verrattava)",
                klass.method(k1, metodi)
                        .returning(int.class).taking(_KasiRef.cls()).isPublic());

        String v = "\nVirheen aiheuttanut koodi\n"
                + "Kasi kasi1 = new Kasi();\n"
                + "Kasi kasi2 = new Kasi();\n"
                + "kasi1.compareTo(kasi2);";

        klass.method(k1, metodi)
                .returning(int.class).taking(_KasiRef.cls()).withNiceError(v).invoke(k2);

    }

    @Points("09-17.4")
    @Test
    public void vertailuToimii1() throws Throwable {
        Reflex.ClassRef<_Kasi> _KasiRef = Reflex.reflect(fullName);

        _Kasi kasi1 = _KasiRef.constructor().takingNoParams().invoke();
        _Kasi kasi2 = _KasiRef.constructor().takingNoParams().invoke();

        Kortti k = new Kortti(12, Maa.HERTTA);
        Kortti k2 = new Kortti(14, Maa.RUUTU);

        lisaa(kasi1, k);
        lisaa(kasi1, k2);

        Kortti k3 = new Kortti(3, Maa.PATA);

        lisaa(kasi2, k3);

        String v = "Kasi kasi1 = new Kasi();\n"
                + "kasi1.lisaa( new Kortti(12,Maa.HERTTA) ); \n"
                + "kasi1.lisaa( new Kortti(14,Maa.RUUTU) ); \n"
                + "Kasi kasi2 = new Kasi();\n"
                + "kasi2.lisaa( new Kortti(3,Maa.HERTTA) ); \n"
                + "kasi1.compareTo(kasi2)\n"
                + "tulos oli: ";

        int vast = klass.method(kasi1, "compareTo")
                .returning(int.class).taking(_KasiRef.cls()).withNiceError(v).invoke(kasi2);

        String od = "Tuloksen olisi pitänyt olla positiivinen koodilla:\n";

        assertTrue(od + v + vast, vast > 0);
    }

    @Points("09-17.4")
    @Test
    public void vertailuToimii2() throws Throwable {
        Reflex.ClassRef<_Kasi> _KasiRef = Reflex.reflect(fullName);

        _Kasi kasi1 = _KasiRef.constructor().takingNoParams().invoke();
        _Kasi kasi2 = _KasiRef.constructor().takingNoParams().invoke();

        Kortti k = new Kortti(12, Maa.HERTTA);
        Kortti k2 = new Kortti(14, Maa.RUUTU);

        lisaa(kasi1, k);
        lisaa(kasi1, k2);

        Kortti k3 = new Kortti(3, Maa.PATA);

        lisaa(kasi2, k3);

        String v = "Kasi kasi1 = new Kasi();\n"
                + "kasi1.lisaa( new Kortti(12,Maa.HERTTA) ); \n"
                + "kasi1.lisaa( new Kortti(14,Maa.RUUTU) ); \n"
                + "Kasi kasi2 = new Kasi();\n"
                + "kasi2.lisaa( new Kortti(3,Maa.HERTTA) ); \n"
                + "kasi2.compareTo(kasi1)\n"
                + "tulos oli: ";

        int vast = klass.method(kasi2, "compareTo")
                .returning(int.class).taking(_KasiRef.cls()).withNiceError(v).invoke(kasi1);

        String od = "Tuloksen olisi pitänyt olla negatiivinen koodilla:\n";

        assertTrue(od + v + vast, vast < 0);
    }

    @Points("09-17.4")
    @Test
    public void vertailuToimii3() throws Throwable {
        Reflex.ClassRef<_Kasi> _KasiRef = Reflex.reflect(fullName);

        _Kasi kasi1 = _KasiRef.constructor().takingNoParams().invoke();
        _Kasi kasi2 = _KasiRef.constructor().takingNoParams().invoke();

        Kortti k = new Kortti(12, Maa.HERTTA);

        lisaa(kasi1, k);

        Kortti k3 = new Kortti(3, Maa.PATA);
        Kortti k2 = new Kortti(9, Maa.PATA);

        lisaa(kasi2, k3);
        lisaa(kasi2, k2);

        String v = "Kasi kasi1 = new Kasi();\n"
                + "kasi1.lisaa( new Kortti(12,Maa.HERTTA) ); \n"
                + "Kasi kasi2 = new Kasi();\n"
                + "kasi2.lisaa( new Kortti(3,Maa.PATA) ); \n"
                + "kasi2.lisaa( new Kortti(9,Maa.PATA) ); \n"
                + "kasi2.compareTo(kasi1)\n";

        int vast = klass.method(kasi2, "compareTo")
                .returning(int.class).taking(_KasiRef.cls()).withNiceError(v).invoke(kasi1);

        assertEquals(v, 0, vast);
    }

    @Points("09-17.4")
    @Test
    public void vertailuToimii4() throws Throwable {
        Reflex.ClassRef<_Kasi> _KasiRef = Reflex.reflect(fullName);

        _Kasi kasi1 = _KasiRef.constructor().takingNoParams().invoke();
        _Kasi kasi2 = _KasiRef.constructor().takingNoParams().invoke();

        Kortti k = new Kortti(12, Maa.HERTTA);
        Kortti k2 = new Kortti(14, Maa.RUUTU);

        lisaa(kasi1, k);
        lisaa(kasi1, k2);

        Kortti k3 = new Kortti(3, Maa.PATA);
        Kortti k4 = new Kortti(8, Maa.RUUTU);
        Kortti k5 = new Kortti(7, Maa.RISTI);
        Kortti k6 = new Kortti(9, Maa.HERTTA);

        lisaa(kasi2, k3);
        lisaa(kasi2, k4);
        lisaa(kasi2, k5);
        lisaa(kasi2, k6);

        String v = "Kasi kasi1 = new Kasi();\n"
                + "kasi1.lisaa( new Kortti(12,Maa.HERTTA) ); \n"
                + "kasi1.lisaa( new Kortti(14,Maa.RUUTU) ); \n"
                + "Kasi kasi2 = new Kasi();\n"
                + "kasi2.lisaa( new Kortti(3,Maa.PATA) ); \n"
                + "kasi2.lisaa( new Kortti(8,Maa.RUUTU) ); \n"
                + "kasi2.lisaa( new Kortti(7,Maa.RISTI) ); \n"
                + "kasi2.lisaa( new Kortti(9,Maa.HERTTA) ); \n"
                + "kasi2.compareTo(kasi1)\n"
                + "tulos oli: ";

        int vast = klass.method(kasi2, "compareTo")
                .returning(int.class).taking(_KasiRef.cls()).withNiceError(v).invoke(kasi1);

        String od = "Tuloksen olisi pitänyt olla positiivinen koodilla:\n";

        assertTrue(od + v + vast, vast > 0);
    }

    @Points("09-17.4")
    @Test
    public void vertailuToimii5() throws Throwable {
        Reflex.ClassRef<_Kasi> _KasiRef = Reflex.reflect(fullName);

        _Kasi kasi1 = _KasiRef.constructor().takingNoParams().invoke();
        _Kasi kasi2 = _KasiRef.constructor().takingNoParams().invoke();

        Kortti k = new Kortti(12, Maa.HERTTA);
        Kortti k2 = new Kortti(14, Maa.RUUTU);

        lisaa(kasi1, k);
        lisaa(kasi1, k2);

        Kortti k3 = new Kortti(3, Maa.PATA);
        Kortti k4 = new Kortti(8, Maa.RUUTU);
        Kortti k5 = new Kortti(7, Maa.RISTI);
        Kortti k6 = new Kortti(9, Maa.HERTTA);

        lisaa(kasi2, k3);
        lisaa(kasi2, k4);
        lisaa(kasi2, k5);
        lisaa(kasi2, k6);

        String v = "Kasi kasi1 = new Kasi();\n"
                + "kasi1.lisaa( new Kortti(12,Maa.HERTTA) ); \n"
                + "kasi1.lisaa( new Kortti(14,Maa.RUUTU) ); \n"
                + "Kasi kasi2 = new Kasi();\n"
                + "kasi2.lisaa( new Kortti(3,Maa.PATA) ); \n"
                + "kasi2.lisaa( new Kortti(8,Maa.RUUTU) ); \n"
                + "kasi2.lisaa( new Kortti(7,Maa.RISTI) ); \n"
                + "kasi2.lisaa( new Kortti(9,Maa.HERTTA) ); \n"
                + "kasi1.compareTo(kasi2)\n"
                + "tulos oli: ";

        int vast = klass.method(kasi1, "compareTo")
                .returning(int.class).taking(_KasiRef.cls()).withNiceError(v).invoke(kasi2);

        String od = "Tuloksen olisi pitänyt olla negatiivinen koodilla:\n";

        assertTrue(od + v + vast, vast < 0);
    }

    /*
     * komparaattorit
     */
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
