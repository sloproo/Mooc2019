
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

public class A_TuotevarastoTest {

    String klassName = "Tuotevarasto";
    Reflex.ClassRef<Object> classRef;

    @Before
    public void setup() {
        classRef = Reflex.reflect(klassName);
    }

    @Test
    @Points("08-03.1")
    public void luokkaOlemassa() {
        assertTrue("Luokan " + s(klassName) + " pitää olla julkinen, eli se tulee määritellä\n"
                + "public class " + klassName + " {...\n}", classRef.isPublic());
    }

    @Test
    @Points("08-03.1")
    public void periiLuokanVarasto() {
        Class c = ReflectionUtils.findClass(klassName);

        Class sc = c.getSuperclass();
        assertTrue("Luokan Tuotevarasto tulee periä luokka Varasto", sc.getName().equals("Varasto"));
    }

    @Test
    @Points("08-03.1")
    public void onKaksiparametrinenKonstruktori() throws Throwable {
        newTuotevarasto("vilja", 10);
    }

    @Test
    @Points("08-03.1")
    public void eiYlimaaraisiaOliomuuttujia() {
        saniteettitarkastus(klassName, 1, "kuin oliomuuttujan tuotteen nimen tallettamiseen, sillä Tuotevarasto perii Varaston saldon ja tilavuuden");
    }

    /*
     *
     */
    @Test
    @Points("08-03.1")
    public void metodiGetNimi() throws Throwable {
        String metodi = "getNimi";
        String virhe = "tee luokalle Tuotevarasto metodi public String getNimi()";

        Object o = newTuotevarasto("olut", 10);

        assertTrue(virhe,
                classRef.method(o, metodi).returning(String.class).takingNoParams().isPublic());

        String v = "Tuotevarasto t = new Tuotevarasto(\"olut\",\"10\");\n"
                + "t.getNimi();\n";

        assertEquals(v, "olut", classRef.method(o, metodi)
                .returning(String.class)
                .takingNoParams().withNiceError("virheen aiheutti koodi\n" + v).invoke());

        o = newTuotevarasto("mehu", 7);

        assertTrue(virhe,
                classRef.method(o, metodi).returning(String.class).takingNoParams().isPublic());

        v = "Tuotevarasto t = new Tuotevarasto(\"mehu\",\"7\");\n"
                + "t.getNimi();\n";

        assertEquals(v, "mehu", classRef.method(o, metodi)
                .returning(String.class)
                .takingNoParams().withNiceError("virheen aiheutti koodi\n" + v).invoke());

    }

    //**********
    @Test
    @Points("08-03.2")
    public void metodiSetNimi() throws Throwable {
        String metodi = "setNimi";
        String virhe = "tee luokalle Tuotevarasto metodi public void setNimi(String nimi)";

        Object o = newTuotevarasto("olut", 10);

        assertTrue(virhe,
                classRef.method(o, metodi).returningVoid().taking(String.class).isPublic());

        String v = "Tuotevarasto t = new Tuotevarasto(\"olut\",\"10\");\n"
                + "t.setNimi(\"bier\");\n";

        classRef.method(o, metodi).returningVoid().taking(String.class).withNiceError(v).invoke("bier");

        v = "Tuotevarasto t = new Tuotevarasto(\"olut\",\"10\");\n"
                + "t.setNimi(\"bier\");\n"
                + "t.getNimi()";

        assertEquals(v, "bier", classRef.method(o, "getNimi")
                .returning(String.class)
                .takingNoParams().withNiceError("virheen aiheutti koodi\n" + v).invoke());

        Method m = null;
        try {
            Class c = ReflectionUtils.findClass(klassName);
            m = ReflectionUtils.requireMethod(c, void.class, metodi, String.class);
        } catch (Throwable t) {
            fail(virhe);
        }
        assertTrue(virhe, m.toString().contains("public"));
        assertFalse(virhe, m.toString().contains("static"));
    }

    @Test
    @Points("08-03.2")
    public void TuotevarastolleOmaToString() throws Throwable {
        Object tv = newTuotevarasto("suklaa", 10.0);
        String mj = tv.toString();
        assertTrue("ylikirjoita luokassa Tuotevarasto metodi toString tehtävänannon esimerkin mukaan", mj.contains("suklaa"));
    }

    @Test
    @Points("08-03.2")
    public void TuotevarastonToStringKunnossa1() throws Throwable {
        Object tv = newTuotevarasto("suklaa", 10.0);
        ((Varasto) tv).lisaaVarastoon(5);
        String mj = tv.toString();
        String v = "toimiiko Tuotevaraston toString esimerkin ohjeen mukaan? ";
        String k = "kokeile \ntv = new Tuotevarasto(\"suklaa\",10); \n"
                + "tv.lisaaVarastoon(5); \n"
                + "System.out.print(tv);\n";
        assertEquals(v + "\n" + k + "\n", "suklaa: saldo = 5.0, tilaa 5.0", mj);
    }

    @Test
    @Points("08-03.2")
    public void TuotevarastonToStringKunnossa2() throws Throwable {
        Object tv = newTuotevarasto("sinappi", 15.0);
        ((Varasto) tv).lisaaVarastoon(10);
        String mj = tv.toString();
        String v = "toimiiko Tuotevaraston toString esimerkin ohjeen mukaan? ";
        String k = "kokeile \ntv = new Tuotevarasto(\"sinappi\",15); tv.lisaaVarastoon(10);\n"
                + "System.out.print(tv);\n";
        assertEquals(v + "\n" + k + "\n", "sinappi: saldo = 10.0, tilaa 5.0", mj);
    }

    /*
     *
     */
    private Object newTuotevarasto(String tuotenimi, double tilavuus) throws Throwable {
        assertTrue("tee luokalle Tuotevarasto konstruktori Tuotevarasto(String tuotenimi, double tilavuus)", classRef.constructor().taking(String.class, double.class).isPublic());

        Reflex.MethodRef2<Object, Object, String, Double> ctor = classRef.constructor().taking(String.class, double.class).withNiceError();
        return ctor.invoke(tuotenimi, tilavuus);
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
}
