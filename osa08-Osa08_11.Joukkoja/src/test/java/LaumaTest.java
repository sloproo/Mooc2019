

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

@Points("08-11.2")
public class LaumaTest {

    private String laumaKlassName = "Lauma";
    private Reflex.ClassRef<Object> laumaKlassRef;
    
    private String elioKlassName = "Elio";
    private Reflex.ClassRef<Object> elioKlassRef;
    
    @Before
    public void setUp() {
        laumaKlassRef = Reflex.reflect(laumaKlassName);
        elioKlassRef = Reflex.reflect(elioKlassName);
    }

    @Test
    public void luokkaOlemassa() {
        assertTrue("Luokan " + s(laumaKlassName) + " pitää olla julkinen, eli se tulee määritellä\n"
                + "public class " + laumaKlassName + " {...\n}", laumaKlassRef.isPublic());
    }

    @Test
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus(laumaKlassName, 1, "lauman jäsenet muistavat oliomuuttujat");
    }

    @Test
    public void Konstruktori() throws Throwable {

        Reflex.MethodRef0<Object, Object> ctor = laumaKlassRef.constructor().takingNoParams().withNiceError();
        assertTrue("Määrittele luokalle " + s(laumaKlassName) + " julkinen konstruktori: \n"
                + "public " + s(laumaKlassName) + "()", ctor.isPublic());
        String v = "virheen aiheutti koodi new Lauma();\n";
        ctor.withNiceError(v).invoke();
    }

    public Siirrettava newElio(int t1, int t2) throws Throwable {

        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(elioKlassName);
        Reflex.MethodRef2<Object, Object, Integer, Integer> ctor = classRef
                .constructor().taking(int.class, int.class).withNiceError();
        return (Siirrettava) ctor.invoke(t1, t2);
    }

    public Siirrettava newLauma() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = laumaKlassRef
                .constructor().takingNoParams().withNiceError();
        return (Siirrettava) ctor.invoke();
    }

    @Test
    public void onSiirrettava() {
        Class clazz = ReflectionUtils.findClass(laumaKlassName);
        boolean toteuttaaRajapinnan = false;
        Class kali = Siirrettava.class;
        for (Class iface : clazz.getInterfaces()) {
            if (iface.equals(kali)) {
                toteuttaaRajapinnan = true;
            }
        }

        if (!toteuttaaRajapinnan) {
            fail("Toteuttaahan luokka Lauma rajapinnan Siirrettava?");
        }
    }

    @Test
    public void onMetodiSiirra() throws Throwable {
        Siirrettava e = newLauma();

        assertTrue("Luokalla Lauma pitää olla metodi public void siirra(int dx, int dy)",
                laumaKlassRef.method(e, "siirra").returningVoid().taking(int.class, int.class).isPublic());

        String v = "virheen aiheutti koodi\n"
                + "Lauma e = new Lauma();\n"
                + "e.siirra(1,1);\n";

        laumaKlassRef.method(e, "siirra").returningVoid().taking(int.class, int.class).withNiceError(v).invoke(1, 1);
    }

    private void siirra(Object e, int dx, int dy, String v) throws Throwable {
        laumaKlassRef.method(e, "siirra").returningVoid().taking(int.class, int.class).withNiceError(v).invoke(dx, dy);
    }

    @Test
    public void onMetodiLisaaLaumaan() throws Throwable {
        Siirrettava e = newLauma();

        assertTrue("Luokalla Lauma pitää olla metodi public void lisaaLaumaan(Siirrettava siirrettava)",
                laumaKlassRef.method(e, "lisaaLaumaan").returningVoid().taking(Siirrettava.class).isPublic());

        String v = "virheen aiheutti koodi\n"
                + "Lauma e = new Lauma();\n"
                + "e.lisaaLaumaan(new Elio(1,1));\n";

        laumaKlassRef.method(e, "lisaaLaumaan")
                .returningVoid()
                .taking(Siirrettava.class).withNiceError(v).invoke(newElio(1, 1));
    }

    private void lisaaLaumaan(Object e, Siirrettava s, String v) throws Throwable {
        laumaKlassRef.method(e, "lisaaLaumaan").returningVoid().taking(Siirrettava.class).withNiceError(v).invoke(s);
    }

    @Test
    public void toStringMaaritelty() throws Throwable {
        Siirrettava l = newLauma();
        assertFalse("määrittele luokalle Lauma tehtävänannon mukainen toString-metodi", l.toString().contains("@"));
        String v = "Lauma l = new Lauma();\n"
                + "l.lisaaLaumaan(new Elio(1,9));\n"
                + "l.lisaaLaumaan(new Elio(4,2));\n"
                + "System.out.println(l.toString());\n";

        Siirrettava e1 = newElio(1, 9);
        Siirrettava e2 = newElio(4, 2);

        lisaaLaumaan(l, e1, v);
        lisaaLaumaan(l, e2, v);

        String mj = l.toString();

        assertTrue("Merkkijonoesityksessä pitäisi olla 2 riviä koodin ollessa \n" + v + ""
                + "merkkijonoesitys oli\n" + mj, mj.split("\n").length > 1);
        assertTrue("Merkkijonoesityksessä pitäisi tulostua \"" + e1 + "\"\n" + v + ""
                + "merkkijonoesitys oli\n" + mj, mj.contains(e1.toString()));
        assertTrue("Merkkijonoesityksessä pitäisi tulostua \"" + e2 + "\"\n" + v + ""
                + "merkkijonoesitys oli\n" + mj, mj.contains(e1.toString()));
    }

    @Test
    public void yhdenKokoinenLaumasiirtyyOikein1() throws Throwable {
        String v = ""
                + "Lauma lauma = new Lauma();\n"
                + "lauma.lisaaLaumaan(new Elio(5,10));\n"
                + "lauma.siirra(1,0);\n"
                + "System.out.println(lauma.toString());";

        Siirrettava l = newLauma();
        lisaaLaumaan(l, newElio(5, 10), v);
        siirra(l, 1, 0, v);
        assertTrue("lauman ainoan jäsenen uuden sijainnin pitäisi olla"
                + "x: 6; y: 10 kun suoritetaan koodi\n" + v + "\n"
                + "\nkoodisi mukaan uusi sijainti on\n" + l.toString(), l.toString().contains("x: 6; y: 10"));
    }

    @Test
    public void yhdenKokoinenLaumasiirtyyOikein2() throws Throwable {
        String v = ""
                + "Lauma lauma = new Lauma();\n"
                + "lauma.lisaaLaumaan(new Elio(5,10));\n"
                + "lauma.siirra(0,1);\n"
                + "System.out.println(lauma.toString());";

        Siirrettava l = newLauma();
        lisaaLaumaan(l, newElio(5, 10), v);
        siirra(l, 0, 1, v);
        assertTrue("lauman ainoan jäsenen uuden sijainnin pitäisi olla"
                + "x: 5; y: 11 kun suoritetaan koodi\n" + v + "\n"
                + "\nkoodisi mukaan uusi sijainti on\n" + l.toString(), l.toString().contains("x: 5; y: 11"));
    }

    @Test
    public void yhdenKokoinenLaumasiirtyyOikein3() throws Throwable {
        String v = ""
                + "Lauma lauma = new Lauma();\n"
                + "lauma.lisaaLaumaan(new Elio(5,10));\n"
                + "lauma.siirra(0,1);\n"
                + "lauma.siirra(3,5);\n"
                + "lauma.siirra(-20,2);\n"
                + "lauma.siirra(9,3);\n"
                + "System.out.println(lauma.toString());";

        Siirrettava l = newLauma();
        lisaaLaumaan(l, newElio(5, 10), v);
        siirra(l, 0, 1, v);
        siirra(l, 3, 5, v);
        siirra(l, -20, 2, v);
        siirra(l, 9, 3, v);
        assertTrue("lauman ainoan jäsenen uuden sijainnin pitäisi olla"
                + "x: -3; y: 21 kun suoritetaan koodi\n" + v + "\n"
                + "\nkoodisi mukaan uusi sijainti on\n" + l.toString(),
                l.toString().contains("x: -3; y: 21"));
    }

    @Test
    public void kahdenKokoinenLaumasiirtyyOikein1() throws Throwable {
        String v = ""
                + "Lauma lauma = new Lauma();\n"
                + "lauma.lisaaLaumaan(new Elio(5,10));\n"
                + "lauma.lisaaLaumaan(new Elio(2,8));\n"
                + "lauma.siirra(1,0);\n"
                + "System.out.println(lauma.toString());";

        Siirrettava l = newLauma();
        lisaaLaumaan(l, newElio(5, 10), v);
        lisaaLaumaan(l, newElio(2, 8), v);
        siirra(l, 1, 0, v);
        assertTrue("lauman jäsenten uusien sijaintien pitäisi olla"
                + "x: 6; y: 10  ja x: 3; y: 8 kun suoritetaan koodi\n" + v + "\n"
                + "\nkoodisi mukaan uusi sijainti on\n" + l.toString(), l.toString().contains("x: 6; y: 10"));
        assertTrue("lauman jäsenten uusien sijaintien pitäisi olla"
                + "x: 6; y: 10  ja x: 3; y: 8 kun suoritetaan koodi\n" + v + "\n"
                + "\nkoodisi mukaan uusi sijainti on\n" + l.toString(), l.toString().contains("x: 3; y: 8"));
    }

    @Test
    public void kahdenKokoinenLaumasiirtyyOikein2() throws Throwable {
        String v = ""
                + "Lauma lauma = new Lauma();\n"
                + "lauma.lisaaLaumaan(new Elio(5,10));\n"
                + "lauma.lisaaLaumaan(new Elio(2,8));\n"
                + "lauma.siirra(0,1);\n"
                + "System.out.println(lauma.toString());";

        Siirrettava l = newLauma();
        lisaaLaumaan(l, newElio(5, 10), v);
        lisaaLaumaan(l, newElio(2, 8), v);
        siirra(l, 0, 1, v);
        assertTrue("lauman jäsenten uusien sijaintien pitäisi olla"
                + "x: 5; y: 11  ja x: 2; y: 9 kun suoritetaan koodi\n" + v + "\n"
                + "\nkoodisi mukaan uusi sijainti on\n" + l.toString(), l.toString().contains("x: 5; y: 11"));
        assertTrue("lauman jäsenten uusien sijaintien pitäisi olla"
                + "x: 5; y: 11  ja x: 2; y: 9 kun suoritetaan koodi\n" + v + "\n"
                + "\nkoodisi mukaan uusi sijainti on\n" + l.toString(), l.toString().contains("x: 2; y: 9"));
    }

    @Test
    public void kahdenKokoinenLaumasiirtyyOikein3() throws Throwable {
        String v = ""
                + "Lauma lauma = new Lauma();\n"
                + "lauma.lisaaLaumaan(new Elio(5,10));\n"
                + "lauma.lisaaLaumaan(new Elio(2,8));\n"
                + "lauma.siirra(0,1);\n"
                + "lauma.siirra(8,-3);\n"
                + "lauma.siirra(11,1);\n"
                + "System.out.println(lauma.toString());";

        Siirrettava l = newLauma();
        lisaaLaumaan(l, newElio(5, 10), v);
        lisaaLaumaan(l, newElio(2, 8), v);
        siirra(l, 0, 1, v);
        siirra(l, 8, -3, v);
        siirra(l, 11, 1, v);
        assertTrue("lauman jäsenten uusien sijaintien pitäisi olla"
                + "x: 24; y: 9  ja x: 21; y: 7 kun suoritetaan koodi\n" + v + "\n"
                + "\nkoodisi mukaan uusi sijainti on\n" + l.toString(),
                l.toString().contains("x: 24; y: 9"));
        assertTrue("lauman jäsenten uusien sijaintien pitäisi olla"
                + "x: 24; y: 9  ja x: 21; y: 7 kun suoritetaan koodi\n" + v + "\n"
                + "\nkoodisi mukaan uusi sijainti on\n" + l.toString(),
                l.toString().contains("x: 21; y: 7"));
    }

    @Test
    public void isoLaumaSiirtyyOikein() throws Throwable {
        String v = ""
                + "Lauma lauma = new Lauma();\n"
                + "lauma.lisaaLaumaan(new Elio(5,10));\n"
                + "lauma.lisaaLaumaan(new Elio(2,8));\n"
                + "lauma.lisaaLaumaan(new Elio(7,-4));\n"
                + "lauma.lisaaLaumaan(new Elio(99,-200));\n"
                + "lauma.siirra(5,-2);\n"
                + "lauma.siirra(1,4);\n"
                + "System.out.println(lauma.toString());";

        Siirrettava l = newLauma();
        lisaaLaumaan(l, newElio(5, 10), v);
        lisaaLaumaan(l, newElio(2, 8), v);
        lisaaLaumaan(l, newElio(7, -4), v);
        lisaaLaumaan(l, newElio(99, -200), v);
        siirra(l, 5, -2, v);
        siirra(l, 1, 4, v);
        assertTrue("lauma ei siirry oikein kun suoritetaan koodi\n" + v + "\n"
                + "lopun merkkijonoesitys oli\n" + l.toString(), l.toString().contains("x: 11; y: 12"));
        assertTrue("lauma ei siirry oikein kun suoritetaan koodi\n" + v + "\n"
                + "lopun merkkijonoesitys oli\n" + l.toString(), l.toString().contains("x: 8; y: 10"));
        assertTrue("lauma ei siirry oikein kun suoritetaan koodi\n" + v + "\n"
                + "lopun merkkijonoesitys oli\n" + l.toString(), l.toString().contains("x: 13; y: -2"));
        assertTrue("lauma ei siirry oikein kun suoritetaan koodi\n" + v + "\n"
                + "lopun merkkijonoesitys oli\n" + l.toString(), l.toString().contains("x: 105; y: -198"));


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
