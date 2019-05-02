
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import fi.helsinki.cs.tmc.edutestutils.Reflex.ClassRef;
import java.lang.reflect.Field;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RahaTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Raha";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect(klassName);
    }

    @Points("05-15.1 05-15.2 05-15.3")
    @Test
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus("Raha", 2, " euro- ja senttimäärän muistavat oliomuuttujat");
    }

    @Points("05-15.1")
    @Test
    public void metodiPlus() throws Throwable {
        String metodi = "plus";

        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Raha r1 = new Raha(1, 0);

        assertTrue("tee luokalle " + klassName + " metodi public Raha " + metodi + "(Raha lisattava) ",
                klass.method(r1, metodi).returning(Raha.class).taking(Raha.class).isPublic());

        Raha r2 = new Raha(2, 0);

        String v = "Raha r1 = new Raha(1, 0);"
                + "Raha r2 = new Raha(2, 0);"
                + "Raha r3 = r1.plus(r2);";

        Raha r3 = klass.method(r1, metodi).returning(Raha.class).taking(Raha.class).
                withNiceError("Virheen aiheuttanut koodi " + v).invoke(r2);

        assertFalse("Kun suoritettiin " + v + " plus-operaation palauttama r3 olio on null", r3 == null);

        assertEquals(v + " r3.eurot();", 3, r3.eurot());
        assertEquals(v + " r3.sentit(); ", 0, r3.sentit());
        assertEquals(v + " System.out.println(r3)", "3.00e", r3.toString());
        assertEquals("Alkuperäiset oliot eivät saa muuttua:\n" + v + " System.out.println(r1)", "1.00e", r1.toString());
        assertEquals("Alkuperäiset oliot eivät saa muuttua:\n" + v + " System.out.println(r2)", "2.00e", r2.toString());

        r1 = new Raha(7, 50);
        r2 = new Raha(1, 60);

        v = "Raha r1 = new Raha(7, 50);"
                + "Raha r2 = new Raha(1, 60);"
                + "Raha r3 = r1.plus(r2);";

        r3 = klass.method(r1, metodi).returning(Raha.class).taking(Raha.class).
                withNiceError("Virheen aiheuttanut koodi " + v).invoke(r2);

        assertEquals(v + " System.out.println(r3)", "9.10e", r3.toString());
        assertEquals("Alkuperäiset oliot eivät saa muuttua:\n" + v + " System.out.println(r1)", "7.50e", r1.toString());
        assertEquals("Alkuperäiset oliot eivät saa muuttua:\n" + v + " System.out.println(r2)", "1.60e", r2.toString());

    }

    @Points("05-15.2")
    @Test
    public void metodiVahemman() throws Throwable {
        String metodi = "vahemman";

        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Raha r1 = new Raha(1, 0);

        assertTrue("tee luokalle " + klassName + " metodi public boolean " + metodi + "(Raha verrattava) ",
                klass.method(r1, metodi).returning(boolean.class).taking(Raha.class).isPublic());

        Raha r2 = new Raha(1, 5);
        Raha r3 = new Raha(-3, 5);
        Raha r4 = new Raha(2, 0);

        String v = "Kun r1 = new Raha(1, 0); "
                + "r2 = new Raha(1, 50); "
                + "r3 = new Raha(-3,5); "
                + "r4 = new Raha(2,0); ";

        check(r1, r2, true, v + " r1.vahemman(r2);", klass);
        check(r2, r1, false, v + " r2.vahemman(r1);", klass);

        check(r1, r3, false, v + " r1.vahemman(r3);", klass);
        check(r3, r1, true, v + " r3.vahemman(r1);", klass);

        check(r2, r3, false, v + " r2.vahemman(r3);", klass);
        check(r3, r2, true, v + " r3.vahemman(r2);", klass);

        check(r2, r4, true, v + " r2.vahemman(r4);", klass);
        check(r4, r2, false, v + " r4.vahemman(r2);", klass);

        check(r3, r4, true, v + " r3.vahemman(r4);", klass);
        check(r4, r3, false, v + " r4.vahemman(r3);", klass);
    }

    private void check(Raha e, Raha t, boolean res, String v, ClassRef<Object> klass) throws Throwable {

        assertEquals(v, res, klass.method(e, "vahemman").returning(boolean.class).taking(Raha.class).invoke(t));
    }

    @Points("05-15.3")
    @Test
    public void metodiMiinus() throws Throwable {
        String metodi = "miinus";

        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Raha r1 = new Raha(10, 0);

        assertTrue("tee luokalle " + klassName + " metodi public Raha " + metodi + "(Raha vahennettava) ", klass.method(r1, metodi)
                .returning(Raha.class).taking(Raha.class).isPublic());

        Raha r2 = new Raha(2, 0);

        String v = "Raha r1 = new Raha(10, 0);"
                + "Raha r2 = new Raha(2, 0);"
                + "Raha r3 = r1.miinus(r2);";

        Raha r3 = klass.method(r1, metodi).returning(Raha.class).taking(Raha.class).
                withNiceError("Virheen aiheuttanut koodi " + v).invoke(r2);

        assertFalse("Kun suoritettiin " + v + " miinus-operaation palauttama r3 olio on null", r3 == null);

        assertEquals(v + " r3.eurot();", 8, r3.eurot());
        assertEquals(v + " r3.sentit(); ", 0, r3.sentit());
        assertEquals(v + " System.out.println(r3)", "8.00e", r3.toString());
        assertEquals("Alkuperäiset oliot eivät saa muuttua:\n" + v + " System.out.println(r1)", "10.00e", r1.toString());
        assertEquals("Alkuperäiset oliot eivät saa muuttua:\n" + v + " System.out.println(r2)", "2.00e", r2.toString());

        r2 = new Raha(7, 40);

        v = "Raha r1 = new Raha(10, 0);"
                + "Raha r2 = new Raha(7, 40);"
                + "Raha r3 = r1.miinus(r2);";

        r3 = klass.method(r1, metodi).returning(Raha.class).taking(Raha.class).
                withNiceError("Virheen aiheuttanut koodi " + v).invoke(r2);

        assertEquals(v + " r3.eurot();", 2, r3.eurot());
        assertEquals(v + " r3.sentit(); ", 60, r3.sentit());
        assertEquals(v + " System.out.println(r3)", "2.60e", r3.toString());
        assertEquals("Alkuperäiset oliot eivät saa muuttua:\n" + v + " System.out.println(r1)", "10.00e", r1.toString());
        assertEquals("Alkuperäiset oliot eivät saa muuttua:\n" + v + " System.out.println(r2)", "7.40e", r2.toString());

        r1 = new Raha(1, 00);
        r2 = new Raha(2, 00);

        v = "Muista, että rahan arvo ei saa tulla negatiiviseksi: testaa koodi \n"
                + "Raha r1 = new Raha(1, 0);"
                + "Raha r2 = new Raha(2, 0);"
                + "Raha r3 = r1.miinus(r2);";

        r3 = klass.method(r1, metodi).returning(Raha.class).taking(Raha.class).
                withNiceError("Virheen aiheuttanut koodi " + v).invoke(r2);

        assertEquals(v + " r3.eurot();", 0, r3.eurot());
        assertEquals(v + " r3.sentit(); ", 0, r3.sentit());
        assertEquals(v + " System.out.println(r3)", "0.00e", r3.toString());
        assertEquals("Alkuperäiset oliot eivät saa muuttua:\n" + v + " System.out.println(r1)", "1.00e", r1.toString());
        assertEquals("Alkuperäiset oliot eivät saa muuttua:\n" + v + " System.out.println(r2)", "2.00e", r2.toString());


        r1 = new Raha(1, 50);
        r2 = new Raha(2, 00);

        v = "Muista, että rahan arvo ei saa tulla negatiiviseksi: testaa koodi \nRaha r1 = new Raha(1, 50);"
                + "Raha r2 = new Raha(2, 0);"
                + "Raha r3 = r1.miinus(r2);";

        r3 = klass.method(r1, metodi).returning(Raha.class).taking(Raha.class).
                withNiceError("Virheen aiheuttanut koodi " + v).invoke(r2);

        assertEquals(v + " r3.eurot();", 0, r3.eurot());
        assertEquals(v + " r3.sentit(); ", 0, r3.sentit());
        assertEquals(v + " System.out.println(r3)", "0.00e", r3.toString());
        assertEquals("Alkuperäiset oliot eivät saa muuttua:\n" + v + " System.out.println(r1)", "1.50e", r1.toString());
        assertEquals("Alkuperäiset oliot eivät saa muuttua:\n" + v + " System.out.println(r2)", "2.00e", r2.toString());

        r1 = new Raha(2, 50);
        r2 = new Raha(2, 00);

        v = "Muista ottaa sentit huomioon: testaa koodi \nRaha r1 = new Raha(2, 50);"
                + "Raha r2 = new Raha(2, 0);"
                + "Raha r3 = r1.miinus(r2);";

        r3 = klass.method(r1, metodi).returning(Raha.class).taking(Raha.class).
                withNiceError("Virheen aiheuttanut koodi " + v).invoke(r2);

        assertEquals(v + " r3.eurot();", 0, r3.eurot());
        assertEquals(v + " r3.sentit(); ", 50, r3.sentit());
        assertEquals(v + " System.out.println(r3)", "0.50e", r3.toString());
        assertEquals("Alkuperäiset oliot eivät saa muuttua:\n" + v + " System.out.println(r1)", "2.50e", r1.toString());
        assertEquals("Alkuperäiset oliot eivät saa muuttua:\n" + v + " System.out.println(r2)", "2.00e", r2.toString());

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
}
