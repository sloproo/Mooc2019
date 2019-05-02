
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class AsuntoTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Asunto";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect(klassName);
    }

    @Points("05-11.1")
    @Test
    public void onkoMetodiSuurempi() throws Throwable {
        String metodi = "suurempi";

        Asunto olio = new Asunto(1, 20, 1000);
        Asunto olio2 = new Asunto(2, 30, 2000);

        assertTrue("tee luokalle " + klassName + " metodi public boolean " + metodi + "(Asunto verrattava) ", klass.method(olio, metodi)
                .returning(boolean.class).taking(Asunto.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi Asunto a = new Asunto(1, 20, 1000); Asunto b = new Asunto(2, 30, 100); "
                + "a.suurempi(b)";

        klass.method(olio, metodi)
                .returning(boolean.class).taking(Asunto.class).withNiceError(v).invoke(olio2);

    }

    @Points("05-11.1")
    @Test
    public void suurempiToimii1() throws Throwable {
        String metodi = "suurempi";

        Asunto a1 = new Asunto(1, 20, 1000);
        Asunto a2 = new Asunto(2, 30, 2000);
        Asunto a3 = new Asunto(2, 25, 1500);

        assertEquals("Asunto a1 = new Asunto(1,20,1000); Asunto a2 = new Asunto(2,30,2000); Asunto a3 = new Asunto(2, 23, 1500);\n"
                + "a1.suurempi(a2)",
                false, klass.method(a1, metodi).returning(boolean.class).taking(Asunto.class).invoke(a2));
        assertEquals("Asunto a1 = new Asunto(1,20,1000); Asunto a2 = new Asunto(2,30,2000); Asunto a3 = new Asunto(2, 23, 1500);\n"
                + "a2.suurempi(a1)",
                true, klass.method(a2, metodi).returning(boolean.class).taking(Asunto.class).invoke(a1));
        assertEquals("Asunto a1 = new Asunto(1,20,1000); Asunto a2 = new Asunto(2,30,2000); Asunto a3 = new Asunto(2, 23, 1500);\n"
                + "a2.suurempi(a3)",
                true, klass.method(a2, metodi).returning(boolean.class).taking(Asunto.class).invoke(a3));
        assertEquals("Asunto a1 = new Asunto(1,20,1000); Asunto a2 = new Asunto(2,30,2000); Asunto a3 = new Asunto(2, 23, 1500);\n"
                + "a3.suurempi(a2)",
                false, klass.method(a3, metodi).returning(boolean.class).taking(Asunto.class).invoke(a2));
        assertEquals("Asunto a1 = new Asunto(1,20,1000); Asunto a2 = new Asunto(2,30,2000); Asunto a3 = new Asunto(2, 23, 1500);\n"
                + "a1.suurempi(a3)",
                false, klass.method(a1, metodi).returning(boolean.class).taking(Asunto.class).invoke(a3));
        assertEquals("Asunto a1 = new Asunto(1,20,1000); Asunto a2 = new Asunto(2,30,2000); Asunto a3 = new Asunto(2, 23, 1500);\n"
                + "a3.suurempi(a1)",
                true, klass.method(a3, metodi).returning(boolean.class).taking(Asunto.class).invoke(a1));
    }

    @Points("05-11.2")
    @Test
    public void onkoMetodiHintaero() throws Throwable {
        String metodi = "hintaero";

        Asunto olio = new Asunto(1, 20, 1000);
        Asunto olio2 = new Asunto(2, 30, 2000);

        assertTrue("tee luokalle " + klassName + " metodi public int " + metodi + "(Asunto verrattava) ", klass.method(olio, metodi)
                .returning(int.class).taking(Asunto.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi Asunto a = new Asunto(1, 20, 1000); Asunto b = new Asunto(2, 30, 100); "
                + "a.hintaero(b)";

        klass.method(olio, metodi)
                .returning(int.class).taking(Asunto.class).withNiceError(v).invoke(olio2);
    }

    @Points("05-11.2")
    @Test
    public void hintaeroToimii1() throws Throwable {
        String metodi = "hintaero";

        Asunto a1 = new Asunto(1, 20, 1000);
        Asunto a2 = new Asunto(2, 30, 2000);
        Asunto a3 = new Asunto(2, 25, 1500);
        
        int res = klass.method(a1, metodi).returning(int.class).taking(Asunto.class).invoke(a2);
        assertEquals("Asunto a1 = new Asunto(1,20,1000); Asunto a2 = new Asunto(2,30,2000); Asunto a3 = new Asunto(2, 23, 1500);\n"
                + "a1.hintaero(a2)", 40000, res);
        
        res = klass.method(a2, metodi).returning(int.class).taking(Asunto.class).invoke(a1);        
        assertEquals("Asunto a1 = new Asunto(1,20,1000); Asunto a2 = new Asunto(2,30,2000); Asunto a3 = new Asunto(2, 23, 1500);\n"
                + "a2.hintaero(a1)",40000, res);
        res = klass.method(a2, metodi).returning(int.class).taking(Asunto.class).invoke(a3);
        assertEquals("Asunto a1 = new Asunto(1,20,1000); Asunto a2 = new Asunto(2,30,2000); Asunto a3 = new Asunto(2, 23, 1500);\n"
                + "a2.hintaero(a3)",22500, res);
        res = klass.method(a3, metodi).returning(int.class).taking(Asunto.class).invoke(a2);
        assertEquals("Asunto a1 = new Asunto(1,20,1000); Asunto a2 = new Asunto(2,30,2000); Asunto a3 = new Asunto(2, 23, 1500);\n"
                + "a3.hintaero(a2)", 22500, res);
        res = klass.method(a1, metodi).returning(int.class).taking(Asunto.class).invoke(a3);
        assertEquals("Asunto a1 = new Asunto(1,20,1000); Asunto a2 = new Asunto(2,30,2000); Asunto a3 = new Asunto(2, 23, 1500);\n"
                + "a1.hintaero(a3)",17500, res);
        res = klass.method(a3, metodi).returning(int.class).taking(Asunto.class).invoke(a1);
        assertEquals("Asunto a1 = new Asunto(1,20,1000); Asunto a2 = new Asunto(2,30,2000); Asunto a3 = new Asunto(2, 23, 1500);\n"
                + "a3.hintaero(a1)", 17500, res);
    }

    @Points("05-11.3")
    @Test
    public void onkoMetodiKalliimpi() throws Throwable {
        String metodi = "kalliimpi";

        Asunto olio = new Asunto(1, 20, 1000);
        Asunto olio2 = new Asunto(2, 30, 2000);

        assertTrue("tee luokalle " + klassName + " metodi public boolean " + metodi + "(Asunto verrattava) ", klass.method(olio, metodi)
                .returning(boolean.class).taking(Asunto.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi Asunto a = new Asunto(1, 20, 1000); Asunto b = new Asunto(2, 30, 100); "
                + "a.kalliimpi(b)";

        klass.method(olio, metodi)
                .returning(boolean.class).taking(Asunto.class).withNiceError(v).invoke(olio2);

    }

    @Points("05-11.3")
    @Test
    public void kalliimpiToimii1() throws Throwable {
        String metodi = "kalliimpi";

        Asunto a1 = new Asunto(1, 20, 1000);
        Asunto a2 = new Asunto(2, 30, 2000);
        Asunto a3 = new Asunto(2, 25, 1500);

        assertEquals("Asunto a1 = new Asunto(1,20,1000); Asunto a2 = new Asunto(2,30,2000); Asunto a3 = new Asunto(2, 23, 1500);\n"
                + "a1.kalliimpi(a2)",
                false, klass.method(a1, metodi).returning(boolean.class).taking(Asunto.class).invoke(a2));
        assertEquals("Asunto a1 = new Asunto(1,20,1000); Asunto a2 = new Asunto(2,30,2000); Asunto a3 = new Asunto(2, 23, 1500);\n"
                + "a2.kalliimpi(a1)",
                true, klass.method(a2, metodi).returning(boolean.class).taking(Asunto.class).invoke(a1));
        assertEquals("Asunto a1 = new Asunto(1,20,1000); Asunto a2 = new Asunto(2,30,2000); Asunto a3 = new Asunto(2, 23, 1500);\n"
                + "a2.kalliimpi(a3)",
                true, klass.method(a2, metodi).returning(boolean.class).taking(Asunto.class).invoke(a3));
        assertEquals("Asunto a1 = new Asunto(1,20,1000); Asunto a2 = new Asunto(2,30,2000); Asunto a3 = new Asunto(2, 23, 1500);\n"
                + "a3.kalliimpi(a2)",
                false, klass.method(a3, metodi).returning(boolean.class).taking(Asunto.class).invoke(a2));
        assertEquals("Asunto a1 = new Asunto(1,20,1000); Asunto a2 = new Asunto(2,30,2000); Asunto a3 = new Asunto(2, 23, 1500);\n"
                + "a1.kalliimpi(a3)",
                false, klass.method(a1, metodi).returning(boolean.class).taking(Asunto.class).invoke(a3));
        assertEquals("Asunto a1 = new Asunto(1,20,1000); Asunto a2 = new Asunto(2,30,2000); Asunto a3 = new Asunto(2, 23, 1500);\n"
                + "a3.kalliimpi(a1)",
                true, klass.method(a3, metodi).returning(boolean.class).taking(Asunto.class).invoke(a1));
    }
}
