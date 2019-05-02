
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class B_PaivaysTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Paivays";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect(klassName);
    }

    @Points("05-14.3")
    @Test
    public void onMetodiPaivienPaasta() throws Throwable {
        String metodi = "paivienPaasta";

        Object olio = new Paivays(1, 1, 2011);

        assertTrue("tee luokalle " + klassName + " metodi public Paivays " + metodi + "(int paivia) ", klass.method(olio, metodi)
                .returning(Paivays.class).taking(int.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi Paivays p = new Paivays(1, 1, 2011); "
                + "p.paivienPaasta(7);";


        klass.method(olio, metodi)
                .returning(Paivays.class).taking(int.class).withNiceError(v).invoke(7);

    }

    @Points("05-14.3")
    @Test
    public void metodiPaivienPaastaLuoUudenOlion() {
        Class c = Paivays.class;
        String metodi = "paivienPaasta";
        String virhe = "Tee luokalle Paivays metodi public Paivays paivienPaasta(int paivia)";
        Method m = null;
        try {
            m = ReflectionUtils.requireMethod(c, metodi, int.class);
        } catch (Throwable t) {
            fail(virhe);
        }
        assertTrue(virhe, m.toString().contains("public"));
        assertFalse(virhe, m.toString().contains("static"));


        Paivays muokattava = new Paivays(30, 12, 2011);
        Paivays uusi = null;
        try {
            uusi = ReflectionUtils.invokeMethod(Paivays.class, m, muokattava, 4);
        } catch (Throwable t) {
            fail(virhe);
        }

        Assert.assertEquals("Tarkista että paivienPaasta-metodi ei muuta alkuperäisen olion arvoja.",
                "30.12.2011",
                muokattava.toString());

        Assert.assertFalse("Nyt paivienPaasta-metodi palauttaa arvon null.\n Kutsu Paivays muokattava = new Paivays(30, 12, 2011); Paivays uusi = muokattava.paivienPaasta(5); Uuden tulee olla vuoden 2012 tammikuussa.",
                 uusi==null);        
        
        Assert.assertEquals("Tarkista että paivienPaasta-metodi siirtää uuden olion päiviä annetulla määrällä. \n"
                + "Kutsu Paivays muokattava = new Paivays(30, 12, 2011); Paivays uusi = muokattava.paivienPaasta(5); Uuden tulee olla vuoden 2012 tammikuussa.",
                "4.1.2012", uusi.toString());
    }

    private void saniteettitarkastus(String luokanNimi, int muuttujia, String msg) throws SecurityException {

        Field[] kentat = ReflectionUtils.findClass(luokanNimi).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("et tarvitse \"stattisia muuttujia\", poista " + kentta(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("luokan kaikkien oliomuuttujien näkyvyyden tulee olla private, muuta " + kentta(field.toString()), field.toString().contains("private"));
        }

        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue(msg, var <= muuttujia);
        }
    }

    private String kentta(String toString) {
        return toString.replace("Paivays" + ".", "");
    }
}
