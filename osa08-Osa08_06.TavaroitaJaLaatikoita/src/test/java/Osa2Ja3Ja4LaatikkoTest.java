
import java.util.Arrays;
import java.lang.reflect.Constructor;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import fi.helsinki.cs.tmc.edutestutils.Reflex.*;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class Osa2Ja3Ja4LaatikkoTest<_Laatikko, _Talletettava, _Kirja, _CDLevy> {

    static final String kirjaNimi = "Kirja";
    static final String levyNimi = "CDLevy";
    static final String laatikkoNimi = "Laatikko";
    static final String talletettavaNimi = "Talletettava";
    private Class talletettavaClazz;
    String klassName = "Laatikko";
    Reflex.ClassRef<_Laatikko> klass;
    Reflex.ClassRef<_CDLevy> _CDRef;
    Reflex.ClassRef<_Kirja> _KirjaRef;
    Reflex.ClassRef<_Talletettava> _TalletettavaRef;

    @Before
    public void setUp() {
        klass = Reflex.reflect(klassName);
        _CDRef = Reflex.reflect(levyNimi);
        _KirjaRef = Reflex.reflect(kirjaNimi);
        _TalletettavaRef = Reflex.reflect(talletettavaNimi);

        try {
            talletettavaClazz = ReflectionUtils.findClass(talletettavaNimi);
        } catch (Exception e) {
        }
    }

    public _CDLevy luoCD(String n, String l, int vuosi) throws Throwable {
        return _CDRef.constructor().taking(String.class, String.class, int.class).withNiceError().invoke(n, l, vuosi);
    }

    public _Kirja luoKirja(String n, String l, double p) throws Throwable {
        return _KirjaRef.constructor().taking(String.class, String.class, double.class).withNiceError().invoke(n, l, p);
    }

    public _Laatikko luoLaatikko(double p) throws Throwable {
        return klass.constructor().taking(double.class).withNiceError().invoke(p);
    }

    @Test
    @Points("08-06.2")
    public void luokkaJulkinen() {
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    @Points("08-06.2 08-06.3")
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus(klassName, 3, "maksimipainon ja Talletettava-oliot tallettavat oliomuuttujat");
    }

    @Test
    @Points("08-06.2")
    public void laatikonKonstruktori() throws Throwable {
        Reflex.MethodRef1<_Laatikko, _Laatikko, Double> ctor = klass.constructor().taking(double.class).withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "(double maksimiPaino)", ctor.isPublic());
        String v = "virheen aiheutti koodi Laatikko laatikko = new Laatikko(10.0);";
        ctor.withNiceError(v).invoke(10.0);
    }

    @Test
    @Points("08-06.2")
    public void lisaaTavaraMetodi1() throws Throwable {
        _Laatikko l = luoLaatikko(10);
        _CDLevy cd = luoCD("Pink Floyd", "Dark side of the moon", 1972);
        _Talletettava t = (_Talletettava) cd;

        String v = "\nLaatikko laatikko = new Laatikko(10.0); \n"
                + "Talletettava t = new CDLevy(\"Pink Floyd\", \"Dark side of the moon\";)\n"
                + "l.lisaa(t);";

        assertTrue("Luokalla Laatikko tulee olla metodi public void lisaa(Talletettava t)",
                klass.method(l, "lisaa").returningVoid().taking(_TalletettavaRef.cls()).withNiceError(v).isPublic());

        klass.method(l, "lisaa").returningVoid().taking(_TalletettavaRef.cls()).withNiceError(v).invoke(t);
    }

    @Test
    @Points("08-06.2")
    public void lisaaTavaraMetodi2() throws Throwable {
        _Laatikko l = luoLaatikko(10);
        _Kirja k = luoKirja("Dostojevski", "Rikos ja Rangaistus", 1);
        _Talletettava t = (_Talletettava) k;

        String v = "\nLaatikko laatikko = new Laatikko(10.0); \n"
                + "Talletettava t = new CDLevy(\"Pink Floyd\", \"Dark side of the moon\";)\n"
                + "l.lisaa(t);";

        assertTrue("Luokalla Laatikko tulee olla metodi public void lisaa(Talletettava t)",
                klass.method(l, "lisaa").returningVoid().taking(_TalletettavaRef.cls()).withNiceError(v).isPublic());

        klass.method(l, "lisaa").returningVoid().taking(_TalletettavaRef.cls()).withNiceError(v).invoke(t);
    }

    public Object mk(double p) throws Throwable {
        Class kl = ReflectionUtils.findClass(kirjaNimi);
        Constructor c = ReflectionUtils.requireConstructor(kl, String.class, String.class, double.class);
        return ReflectionUtils.invokeConstructor(c, "ISO", "KIRJA", p);
    }

    @Test
    @Points("08-06.2")
    public void laatikkoToimii() throws Throwable {
        Class kl = ReflectionUtils.findClass(laatikkoNimi);
        Constructor c = ReflectionUtils.requireConstructor(kl, double.class);
        Method lisaa = ReflectionUtils.requireMethod(kl, "lisaa", talletettavaClazz);
        Object o = ReflectionUtils.invokeConstructor(c, 10.0);

        ReflectionUtils.invokeMethod(Void.TYPE, lisaa, o, mk(3));

        assertFalse("tee luokalle Laatikko tehtävänannon mukainen toString-metodi",o.toString().contains("@"));

        assertEquals("Tarkista että luokan " + laatikkoNimi + " toString on oikein!",
                "Laatikko: 1 esinettä, paino yhteensä 3.0 kiloa",
                o.toString());

        ReflectionUtils.invokeMethod(Void.TYPE, lisaa, o, mk(4));

        assertEquals("Tarkista että luokan " + laatikkoNimi + " toString on oikein!",
                "Laatikko: 2 esinettä, paino yhteensä 7.0 kiloa",
                o.toString());

        ReflectionUtils.invokeMethod(Void.TYPE, lisaa, o, mk(4));

        assertEquals("Tarkista että liian painavan esineen lisääminen ei onnistu!",
                "Laatikko: 2 esinettä, paino yhteensä 7.0 kiloa",
                o.toString());
    }

    @Test
    @Points("08-06.3")
    public void metodiPaino() throws Throwable {
        _Laatikko l = luoLaatikko(10);

        String v = "\nLaatikko laatikko = new Laatikko(10.0); \n"
                + "l.painot);";

        assertTrue("Luokalla Laatikko tulee olla metodi public double paino()",
                klass.method(l, "paino").returning(double.class).takingNoParams().withNiceError(v).isPublic());

        klass.method(l, "paino").returning(double.class).takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("08-06.3")
    public void laatikonMetodiPainoToimii() throws Throwable {
        Object o = Reflex.reflect(laatikkoNimi).constructor().taking(double.class).invoke(10.0);
        MethodRef0<Object, Double> paino = Reflex.reflect(laatikkoNimi).method("paino").returning(double.class).takingNoParams();
        ClassRef talletettava = Reflex.reflect(talletettavaNimi);
        MethodRef1 lisaa = Reflex.reflect(laatikkoNimi).method("lisaa").returningVoid().taking(talletettava.getReferencedClass());

        double eps = 0.001;

        assertEquals("Tyhjän laatikon painon pitäisi olla 0!",
                0,
                paino.invokeOn(o),
                eps);

        lisaa.invokeOn(o, mk(5));

        assertEquals("Painon pitäisi kasvaa kun laatikkoon lisätään esine!",
                5,
                paino.invokeOn(o),
                eps);

        lisaa.invokeOn(o, mk(0.5));

        assertEquals("Painon pitäisi kasvaa kun laatikkoon lisätään esine!",
                5.5,
                paino.invokeOn(o),
                eps);

        lisaa.invokeOn(o, mk(1000));

        assertEquals("Painon ei pitäisi kasvaa kun laatikkoon lisätään liian suuri esine!",
                5.5,
                paino.invokeOn(o),
                eps);
    }

    @Test
    @Points("08-06.4")
    public void laatikkoOnTalletettava() {
        Class talletettava = Reflex.reflect(talletettavaNimi).getReferencedClass();
        Class laatikko = Reflex.reflect(laatikkoNimi).getReferencedClass();

        Class is[] = laatikko.getInterfaces();
        Class oikein[] = {talletettava};

        assertTrue("Varmista että luokka " + laatikkoNimi + " toteuttaa (vain!) rajapinnan Talletettava",
                Arrays.equals(is, oikein));
    }

    @Test
    @Points("08-06.4")
    public void laatikkoLaskeePainonsaMetodilla() throws Throwable {
        Object o = Reflex.reflect(laatikkoNimi).constructor().taking(double.class).invoke(10.0);
        Object laatikko = Reflex.reflect(laatikkoNimi).constructor().taking(double.class).invoke(20.0);
        MethodRef0<Object, Double> paino = Reflex.reflect(laatikkoNimi).method("paino").returning(double.class).takingNoParams();
        ClassRef talletettava = Reflex.reflect(talletettavaNimi);
        MethodRef1 lisaa = Reflex.reflect(laatikkoNimi).method("lisaa").returningVoid().taking(talletettava.getReferencedClass());

        double eps = 0.001;
        lisaa.invokeOn(laatikko, o);

        assertEquals("Tyhjän laatikon painon pitäisi olla 0!",
                0,
                paino.invokeOn(o),
                eps);

        lisaa.invokeOn(o, mk(5));

        assertEquals("Painon pitäisi kasvaa kun laatikkoon lisätään esine! Tarkista koodi\n"
                + "Laatikko laatikko = new Laatikko(10); "
                + "laatikko.lisaa( new Kirja(\"Horstman\", \"Core Java\",5) );\n"
                + "laatikko.paino();\n",
                5,
                paino.invokeOn(o),
                eps);

        assertEquals("Painon pitäisi kasvaa kun laatikon sisältämään laatikkoon lisätään esine!\n"
                + "Laatikko isoLaatikko = new Laatikko(20); \n"
                + "Laatikko laatikko = new Laatikko(10); \n"
                + "isoLaatikko.lisaa(laatikko);\n"
                + "laatikko.lisaa( new Kirja(\"Horstman\", \"Core Java\",5) );\n"
                + "isoLaatikko.paino();\n",
                5,
                paino.invokeOn(laatikko),
                eps);

        lisaa.invokeOn(o, mk(0.5));

        assertEquals("Painon pitäisi kasvaa kun laatikkoon lisätään esine!\n"
                + "Laatikko laatikko = new Laatikko(10); "
                + "laatikko.lisaa( new Kirja(\"Horstman\", \"Core Java\",5) );\n"
                + "laatikko.lisaa( new Kirja(\"Beck\", \"Test Driven Development\",0.5) );\n"
                + "laatikko.paino();\n",
                5.5,
                paino.invokeOn(o),
                eps);

        assertEquals("Painon pitäisi kasvaa kun laatikon sisältämään laatikkoon lisätään esine!\n"
                + "Laatikko isoLaatikko = new Laatikko(20); \n"
                + "Laatikko laatikko = new Laatikko(10); \n"
                + "isoLaatikko.lisaa(laatikko);\n"
                + "laatikko.lisaa( new Kirja(\"Horstman\", \"Core Java\",5) );\n"
                + "laatikko.lisaa( new Kirja(\"Beck\", \"Test Driven Development\",0.5) );\n"
                + "isoLaatikko.paino();\n",
                5.5,
                paino.invokeOn(laatikko),
                eps);


        lisaa.invokeOn(o, mk(1000));

        assertEquals("Painon ei pitäisi kasvaa kun laatikkoon lisätään liian suuri esine!\n"
                + "Laatikko laatikko = new Laatikko(10); "
                + "laatikko.lisaa( new Kirja(\"Horstman\", \"Core Java\",5) );\n"
                + "laatikko.lisaa( new Kirja(\"Beck\", \"Test Driven Development\",0.5) );\n"
                + "laatikko.lisaa( new Kirja(\"Nietzsche\", \"Also spracht Zarahustra\",1000) );\n"
                + "laatikko.paino();\n",
                5.5,
                paino.invokeOn(o),
                eps);
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
