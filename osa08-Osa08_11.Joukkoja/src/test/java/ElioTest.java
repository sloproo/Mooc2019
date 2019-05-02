

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

@Points("08-11.1")
public class ElioTest {

    private String klassName = "Elio";
    Reflex.ClassRef<Object> classRef;

    @Before
    public void setUp() {
        classRef = Reflex.reflect(klassName);
    }

    @Test
    public void luokkaOlemassa() {
        assertTrue("Luokan " + s(klassName) + " pitää olla julkinen, eli se tulee määritellä\n"
                + "public class " + klassName + " {...\n}", classRef.isPublic());
    }

    @Test
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus(klassName, 2, "sijainnin x- ja y-kordinaatit muistavat oliomuuttujat");
    }

    @Test
    public void Konstruktori() throws Throwable {

        Reflex.MethodRef2<Object, Object, Integer, Integer> ctor = classRef.constructor().taking(int.class, int.class).withNiceError();
        assertTrue("Määrittele luokalle " + s(klassName) + " julkinen konstruktori: \n"
                + "public " + s(klassName) + "(int x, int y)", ctor.isPublic());
        String v = "virheen aiheutti koodi new Elio(5,10);\n";
        ctor.withNiceError(v).invoke(5, 10);
    }

    public Siirrettava newElio(int t1, int t2) throws Throwable {
        Reflex.MethodRef2<Object, Object, Integer, Integer> ctor = classRef
                .constructor().taking(int.class, int.class).withNiceError();
        return (Siirrettava) ctor.invoke(t1, t2);
    }

    @Test
    public void onSiirrettava() {
        Class clazz = ReflectionUtils.findClass(klassName);
        boolean toteuttaaRajapinnan = false;
        Class kali = Siirrettava.class;
        for (Class iface : clazz.getInterfaces()) {
            if (iface.equals(kali)) {
                toteuttaaRajapinnan = true;
            }
        }

        if (!toteuttaaRajapinnan) {
            fail("Toteuttaahan luokka Elio rajapinnan Siirrettava?");
        }
    }

    @Test
    public void onMetodiSiirra() throws Throwable {
        Siirrettava e = newElio(5, 10);

        assertTrue("Luokalla Elio pitää olla metodi public void siirra(int dx, int dy)",
                classRef.method(e, "siirra").returningVoid().taking(int.class, int.class).isPublic());

        String v = "virheen aiheutti koodi\n"
                + "Elio e = new Elio(5,10);\n"
                + "e.siirra(1,1);\n";

        classRef.method(e, "siirra").returningVoid().taking(int.class, int.class).withNiceError(v).invoke(1, 1);
    }

    private void siirra(Object e, int dx, int dy, String v) throws Throwable {
        classRef.method(e, "siirra").returningVoid().taking(int.class, int.class).withNiceError(v).invoke(dx, dy);
    }

    @Test
    public void toStringMaaritelty() throws Throwable {
        Siirrettava e = newElio(5, 10);
        assertFalse("määrittele luokalle Elio tehtävänannon mukainen toString-metodi", e.toString().contains("@"));
        String v = "Elio e = new Elio(5,10);\n"
                + "e.toString();\n";
        assertEquals(v, "x: 5; y: 10", e.toString());

        e = newElio(1, 9);
        v = "Elio e = new Elio(1,9);\n"
                + "e.toString();\n";
        assertEquals(v, "x: 1; y: 9", e.toString());
    }

    @Test
    public void siirtyyOikein1() throws Throwable {
        String v = ""
                + "Elio e = new Elio(5,10);\n"
                + "e.siirra(1,0);\n"
                + "t.toString()";

        Siirrettava e = newElio(5,10);
        siirra(e, 1, 0, v);
        assertEquals(v, "x: 6; y: 10", e.toString());
    }

    @Test
    public void siirtyyOikein2() throws Throwable {
        String v = ""
                + "Elio e = new Elio(5,10);\n"
                + "e.siirra(0,1);\n"
                + "t.toString()";

        Siirrettava e = newElio(5,10);
        siirra(e, 0, 1, v);
        assertEquals(v, "x: 5; y: 11", e.toString());
    }

    @Test
    public void siirtyyOikein3() throws Throwable {
        String v = ""
                + "Elio e = new Elio(5,10);\n"
                + "e.siirra(2,-8);\n"
                + "t.toString()";

        Siirrettava e = newElio(5,10);
        siirra(e, 2, -8, v);
        assertEquals(v, "x: 7; y: 2", e.toString());
    }

    @Test
    public void siirtyyOikein4() throws Throwable {
        String v = ""
                + "Elio e = new Elio(0,0);\n"
                + "e.siirra(2,5);\n"
                + "e.siirra(1,4);\n"
                + "e.siirra(5,-11);\n"
                + "t.toString()";

        Siirrettava e = newElio(0,0);
        siirra(e, 2, 5, v);
        siirra(e, 1, 4, v);
        siirra(e, 5, -11, v);
        assertEquals(v, "x: 8; y: -2", e.toString());
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
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }

    private String s(String klassName) {
        return klassName.substring(klassName.lastIndexOf(".") + 1);
    }
}
