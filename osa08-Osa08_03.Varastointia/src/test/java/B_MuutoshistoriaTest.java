
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;

public class B_MuutoshistoriaTest {

    String klassName = "Muutoshistoria";
    Reflex.ClassRef<Object> classRef;

    @Before
    public void setup() {
        classRef = Reflex.reflect(klassName);
    }

    @Test
    @Points("08-03.3")
    public void luokkaOlemassa() {
        assertTrue("Luokan " + s(klassName) + " pitää olla julkinen, eli se tulee määritellä\n"
                + "public class " + klassName + " {...\n}", classRef.isPublic());
    }

    @Test
    @Points("08-03.3")
    public void eiPeriKetaan() {
        Class c = ReflectionUtils.findClass(klassName);

        Class sc = c.getSuperclass();
        assertTrue("Luokan Muutoshistoria ei tulee periä mitään luokkaa!", sc.getName().equals("java.lang.Object"));
    }

    @Test
    @Points("08-03.3")
    public void eiYlimaaraisiaOliomuuttujia() {
        saniteettitarkastus(klassName, 1, "muita oliomuuttujia kuin doubleja tallettavan listan");
    }

    @Test
    @Points("08-03.3")
    public void onKonstruktori() throws Throwable {
        newMuutoshistoria();
    }

    @Test
    @Points("08-03.3")
    public void onMetodilisaa() throws Throwable {
        String metodi = "lisaa";
        String virhe = "tee luokalle Muutoshistoria metodi public void lisaa(double tilanne)";

        Object o = newMuutoshistoria();

        assertTrue(virhe,
                classRef.method(o, metodi).returningVoid().taking(double.class).isPublic());

        String v = "Muutoshistoria m = new Muutoshistoria();\n"
                + "m.lisaa(99);\n";

        classRef.method(o, metodi).returningVoid().taking(double.class).withNiceError(v).invoke(99.0);
    }

    @Test
    @Points("08-03.3")
    public void onMetodiNollaa() throws Throwable {
        String metodi = "nollaa";
        String virhe = "tee luokalle Muutoshistoria metodi public void nollaa()";

        Object o = newMuutoshistoria();

        assertTrue(virhe,
                classRef.method(o, metodi).returningVoid().takingNoParams().isPublic());

        String v = "Muutoshistoria m = new Muutoshistoria();\n"
                + "m.nollaa();\n";

        classRef.method(o, metodi).returningVoid().takingNoParams().withNiceError(v).invoke();

    }

    @Test
    @Points("08-03.3")
    public void onToString() throws Throwable {
        Object mh = newMuutoshistoria();
        String v = "Muutoshistoria m = new Muutoshistoria();\n"
                + "m.toString();\n";

        assertFalse("tee luokalle Muutoshistoria toString-metodi tehtävänannon ohjeen "
                + "mukaan\neli kutsumalla Muutoshistorian oliomuuttujana olevan "
                + "listan toStringiä", toString(mh, v).contains("@"));
    }

    private String toString(Object o, String v) throws Throwable {
        return classRef.method(o, "toString").returning(String.class).takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("08-03.3")
    public void toStringTyhjalleHistorialle() throws Throwable {
        Object mh = newMuutoshistoria();
        ArrayList<Double> lista = new ArrayList<Double>();

        assertEquals("tee luokalle Muutoshistoria toString-metodi tehtävänannon ohjeen mukaan\n"
                + "eli kutsumalla Muutoshistorian oliomuuttujana olevan listan toStringiä", lista.toString(), mh.toString());
    }

    /*
     *
     */
    @Test
    @Points("08-03.3")
    public void toStringToimiiLisaystenYhteydessa1() throws Throwable {
        Object mh = newMuutoshistoria();

        String k = "tarkasta koodi\nmh = new Muutoshistoria();\n"
                + "mh.lisaa(5.0);\n";

        lisaa(mh, 5, k);
        ArrayList<Double> lista = new ArrayList<Double>();
        lista.add(5.0);

        k = "tarkasta koodi\nmh = new Muutoshistoria();\n"
                + "mh.lisaa(5.0);\n"
                + "System.out.println(mh)";

        assertEquals(k, lista.toString(), toString(mh, k));
    }

    @Test
    @Points("08-03.3")
    public void toStringToimiiLisaystenYhteydessa2() throws Throwable {
        Object mh = newMuutoshistoria();

        String k = "tarkasta koodi \n"
                + "mh = new Muutoshistoria();\n"
                + "mh.lisaa(5.0);\n"
                + "mh.lisaa(12.0);\n"
                + "mh.lisaa(4.0);\n";
        lisaa(mh, 5, k);
        lisaa(mh, 12, k);
        lisaa(mh, 4, k);
        ArrayList<Double> lista = new ArrayList<Double>();
        lista.add(5.0);
        lista.add(12.0);
        lista.add(4.0);

        k = "tarkasta koodi\nmh = new Muutoshistoria();\nmh.lisaa(5.0);\nmh.lisaa(12.0);\n"
                + "mh.lisaa(4.0);\nSystem.out.println(mh)\n";

        assertEquals(k, lista.toString(), toString(mh, k));

        k = "tarkasta koodi\nmh = new Muutoshistoria();\nmh.lisaa(5.0);\nmh.lisaa(12.0);\n"
                + "mh.lisaa(4.0);\nmh.nollaa();\nSystem.out.println(mh)\n";

        nollaa(mh, "tarkasta koodi\nmh = new Muutoshistoria();\nmh.lisaa(5.0);\n"
                + "mh.lisaa(12.0);\nmh.lisaa(4.0);\nmh.nollaa()\n;");
        lista.clear();

        assertEquals(k, lista.toString(), toString(mh, k));
    }

    // ************
    @Test
    @Points("08-03.4")
    public void onMetodiMinArvo() throws Throwable {
        String metodi = "minArvo";
        String virhe = "tee luokalle Muutoshistoria metodi public double minArvo()";

        Object o = newMuutoshistoria();

        assertTrue(virhe,
                classRef.method(o, metodi).returning(double.class).takingNoParams().isPublic());

        String v = "Muutoshistoria m = new Muutoshistoria();\n"
                + "m.lisaa(99);\n"
                + "m.minArvo();\n";

        lisaa(o, 99.0, v);

        assertEquals(v, 99.0, minArvo(o, v), 0.01);
    }

    @Test
    @Points("08-03.4")
    public void metodiMinArvoEiMuokkaaListaa() throws Throwable {
        String v = "Muutoshistoria m = new Muutoshistoria();\n"
                + "m.lisaa(3);\n"
                + "m.lisaa(1);\n"
                + "m.lisaa(5);\n"
                + "m.minArvo();\n";
        Object o = newMuutoshistoria();

        lisaa(o, 3.0, v);
        lisaa(o, 1.0, v);
        lisaa(o, 5.0, v);
        ArrayList<Double> lista = new ArrayList<Double>();
        lista.add(3.0);
        lista.add(1.0);
        lista.add(5.0);
        assertEquals(v, 1.0, minArvo(o, v), 0.01);
        assertEquals(v + "System.out.println(m);\nEthän muokkaa listaa minArvo metodissa!\n", lista.toString(), toString(o, v));
    }

    @Test
    @Points("08-03.4")
    public void onMetodiMaxArvo() throws Throwable {
        String metodi = "maxArvo";
        String virhe = "tee luokalle Muutoshistoria metodi public double maxArvo()";
        Object o = newMuutoshistoria();

        assertTrue(virhe,
                classRef.method(o, metodi).returning(double.class).takingNoParams().isPublic());

        String v = "Muutoshistoria m = new Muutoshistoria();\n"
                + "m.lisaa(99);\n"
                + "m.maxArvo();\n";

        classRef.method(o, "lisaa").returningVoid().taking(double.class).withNiceError(v).invoke(99.0);

        assertEquals(v, 99.0,
                classRef.method(o, metodi).returning(double.class).takingNoParams().withNiceError(v).invoke(), 0.01);
    }

    @Test
    @Points("08-03.4")
    public void metodiMaxArvoEiMuokkaaListaa() throws Throwable {
        String v = "Muutoshistoria m = new Muutoshistoria();\n"
                + "m.lisaa(3);\n"
                + "m.lisaa(5);\n"
                + "m.lisaa(1);\n"
                + "m.maxArvo();\n";
        Object o = newMuutoshistoria();

        lisaa(o, 3.0, v);
        lisaa(o, 5.0, v);
        lisaa(o, 1.0, v);
        ArrayList<Double> lista = new ArrayList<Double>();
        lista.add(3.0);
        lista.add(5.0);
        lista.add(1.0);
        assertEquals(v, 5.0, maxArvo(o, v), 0.01);
        assertEquals(v + "System.out.println(m);\nEthän muokkaa listaa maxArvo metodissa!\n", lista.toString(), toString(o, v));
    }

    @Test
    @Points("08-03.4")
    public void onMetodiKeskiarvo() throws Throwable {
        String metodi = "keskiarvo";
        String virhe = "tee luokalle Muutoshistoria metodi public double keskiarvo()";
        Object o = newMuutoshistoria();

        assertTrue(virhe,
                classRef.method(o, metodi).returning(double.class).takingNoParams().isPublic());

        String v = "Muutoshistoria m = new Muutoshistoria();\n"
                + "m.lisaa(99);\n"
                + "m.keskiarvo();\n";

        classRef.method(o, "lisaa").returningVoid().taking(double.class).withNiceError(v).invoke(99.0);

        assertEquals(v, 99.0,
                classRef.method(o, metodi).returning(double.class).takingNoParams().withNiceError(v).invoke(), 0.01);
    }

    /*
     *
     */
    @Test
    @Points("08-03.4")
    public void arvotLasketaanOikein1() throws Throwable {
        String k = "tarkasta koodi\n"
                + "mh = new Muutoshistoria();\nmh.lisaa(4.0);\nmh.lisaa(-1.0);\nmh.lisaa(3);\n";

        Object mh = newMuutoshistoria();
        lisaa(mh, 4, k);
        lisaa(mh, -1, k);
        lisaa(mh, 3, k);

        assertEquals(k + "mh.minArvo() ", -1, minArvo(mh, k + "mh.minArvo()"), 0.01);
        assertEquals(k + "mh.maxArvo() ", 4, maxArvo(mh, k + "mh.maxArvo()"), 0.01);
        assertEquals(k + "mh.keskiarvo() ", 2, keskiarvo(mh, k + "mh.keskiarvo()"), 0.01);
    }

    @Test
    @Points("08-03.4")
    public void arvotLasketaanOikeinKunNollataan() throws Throwable {
        String k = "tarkasta koodi\nmh = new Muutoshistoria();\nmh.lisaa(7.0);\nmh.nollaa();\nmh.lisaa(4.0);\nmh.lisaa(-1.0);\nmh.lisaa(3);\n";

        Object mh = newMuutoshistoria();
        lisaa(mh, 7, k);
        nollaa(mh, "tarkasta koodi\nmh = new Muutoshistoria();\nmh.lisaa(7.0);\nmh.nollaa();\n");
        lisaa(mh, 4, k);
        lisaa(mh, -1, k);
        lisaa(mh, 3, k);

        assertEquals(k + "mh.minArvo() ", -1, minArvo(mh, k + "mh.minArvo()"), 0.01);
        assertEquals(k + "mh.maxArvo() ", 4, maxArvo(mh, k + "mh.maxArvo()"), 0.01);
        assertEquals(k + "mh.keskiarvo() ", 2, keskiarvo(mh, k + "mh.keskiarvo()"), 0.01);
    }

    @Test
    @Points("08-03.4")
    public void arvotLasketaanOikein2() throws Throwable {
        for (int i = 0; i < 5; i++) {
            ArrayList<Double> luvut = arvoLukuja();

            Object mh = newMuutoshistoria();
            String lvut = "";
            for (Double luku : luvut) {
                lvut += luku + " ";
                lisaa(mh, luku, "kun muutoshistoriaan lisättiin luvut " + lvut);
            }

            assertEquals("kun muutoshistoriaan lisättiin luvut " + luvut + " minArvo ", Collections.min(luvut), minArvo(mh, "kun muutoshistoriaan lisättiin luvut " + luvut + " minArvo "), 0.01);
            assertEquals("kun muutoshistoriaan lisättiin luvut " + luvut + " maxArvo ", Collections.max(luvut), maxArvo(mh, "kun muutoshistoriaan lisättiin luvut " + luvut + " maxArvo "), 0.01);
            assertEquals("kun muutoshistoriaan lisättiin luvut " + luvut + " keskiarvo ", ka(luvut), keskiarvo(mh, "kun muutoshistoriaan lisättiin luvut " + luvut + " keskiarvo "), 0.01);
        }
    }

    /*
     *
     */
    private void lisaa(Object o, double tilavuus, String v) throws Throwable {
        classRef.method(o, "lisaa").returningVoid().taking(double.class).withNiceError(v).invoke(tilavuus);

    }

    private void nollaa(Object o, String v) throws Throwable {
        classRef.method(o, "nollaa").returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    private double minArvo(Object o, String v) throws Throwable {
        return classRef.method(o, "minArvo").returning(double.class).takingNoParams().withNiceError(v).invoke();
    }

    private double maxArvo(Object o, String v) throws Throwable {
        return classRef.method(o, "maxArvo").returning(double.class).takingNoParams().withNiceError(v).invoke();
    }

    private double keskiarvo(Object o, String v) throws Throwable {
        return classRef.method(o, "keskiarvo").returning(double.class).takingNoParams().withNiceError(v).invoke();
    }

    private double suurinMuutos(Object o, String v) throws Throwable {
        return classRef.method(o, "suurinMuutos").returning(double.class).takingNoParams().withNiceError(v).invoke();
    }

    private double varianssi(Object o, String v) throws Throwable {
        return classRef.method(o, "varianssi").returning(double.class).takingNoParams().withNiceError(v).invoke();
    }

    Random arpa = new Random();

    private ArrayList<Double> arvoLukuja() {
        ArrayList<Double> luvut = new ArrayList<Double>();

        int raja = 10 + arpa.nextInt(10);

        for (int i = 0; i < raja; i++) {
            luvut.add(20.0 - arpa.nextInt(30));
        }

        return luvut;
    }

    private double ka(ArrayList<Double> luvut) {
        double s = 0;
        for (Double luku : luvut) {
            s += luku;
        }
        return s / luvut.size();
    }

    /*
     *
     */
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
            assertTrue("et tarvitse " + s(klassName) + "-luokalle " + m + ", poista ylimääräiset", var <= n);
        }
    }

    private String kentta(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }

    private String s(String klassName) {
        if (!klassName.contains(".")) {
            return klassName;
        }

        return klassName.substring(klassName.lastIndexOf(".") + 1);
    }

    private Object newMuutoshistoria() throws Throwable {
        assertTrue("tee luokalle Muutoshistoria konstruktori Muutoshistoria()", classRef.constructor().takingNoParams().isPublic());

        Reflex.MethodRef0<Object, Object> ctor = classRef.constructor().takingNoParams().withNiceError();
        return ctor.invoke();
    }
}
