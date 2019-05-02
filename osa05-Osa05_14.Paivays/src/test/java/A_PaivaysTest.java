
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class A_PaivaysTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Paivays";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect(klassName);
    }

    @Points("05-14.1")
    @Test
    public void eiLiikaaOliomuuttujiaKassaan1() {
        saniteettitarkastus("Paivays", 3, "Älä lisää Paivays-luokalle uusia oliomuuttujia, niitä ei tarvita");
    }

    @Points("05-14.1")
    @Test
    public void onMetodiEtene() throws Throwable {
        String metodi = "etene";

        Object olio = new Paivays(1, 1, 2011);

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "() ", klass.method(olio, metodi)
                .returningVoid().takingNoParams().isPublic());

        String v = "\nVirheen aiheuttanut koodi Paivays p = new Paivays(1, 1, 2011); "
                + "p.etene();";

        klass.method(olio, metodi)
                .returningVoid().takingNoParams().withNiceError(v).invoke();

    }

    @Points("05-14.1")
    @Test
    public void metodiEteneSiirtaaPaivalla() {
        Class c = Paivays.class;
        String metodi = "etene";
        String virhe = "Tee luokalle Paivays metodi public void etene()";
        Method etene = null;
        try {
            etene = ReflectionUtils.requireMethod(c, metodi);
        } catch (Throwable t) {
            fail(virhe);
        }
        assertTrue(virhe, etene.toString().contains("public"));
        assertFalse(virhe, etene.toString().contains("static"));

        Paivays muokattava = new Paivays(26, 12, 2011);

        try {
            ReflectionUtils.invokeMethod(void.class, etene, muokattava);
        } catch (Throwable t) {
            fail("Varmista että luokalla Paivays on metodi public void etene().");
        }

        virhe = "Tarkista että etene-metodin kutsuminen siirtää päivää yhdellä. \n"
                + "Kun luot olion Paivays paivays = new Paivays(26, 12, 2011); ja kutsut metodia paivays.etene() kerran, tulee päivän olla 27.12.2011.\n";
        Assert.assertEquals(virhe, "27.12.2011", muokattava.toString());
    }

    @Points("05-14.1")
    @Test
    public void metodiEteneVaihtaaKuukaudenOikein() {
        Class c = Paivays.class;
        String metodi = "etene";
        String virhe = "Tee luokalle Paivays metodi public void etene()";
        Method etene = null;
        try {
            etene = ReflectionUtils.requireMethod(c, metodi);
        } catch (Throwable t) {
            fail(virhe);
        }
        assertTrue(virhe, etene.toString().contains("public"));
        assertFalse(virhe, etene.toString().contains("static"));

        Paivays muokattava = new Paivays(29, 12, 2011);

        try {
            ReflectionUtils.invokeMethod(void.class, etene, muokattava);
        } catch (Throwable t) {
            fail("Varmista että luokalla Paivays on metodi public void etene().");
        }

        virhe = "Tarkista että etene-metodin kutsuminen siirtää päivää yhdellä. \n"
                + "Kun luot olion Paivays paivays = new Paivays(29, 12, 2011); ja kutsut metodia paivays.etene() kerran, tulee päivän olla 27.12.2011.\n";
        Assert.assertEquals(virhe, "30.12.2011", muokattava.toString());

        try {
            ReflectionUtils.invokeMethod(void.class, etene, muokattava);
        } catch (Throwable t) {
            fail("Varmista että luokalla Paivays on metodi public void etene().");
        }

        virhe = "Tarkista että etene-metodin kutsuminen siirtää päivää yhdellä. \n"
                + "Kun luot olion Paivays paivays = new Paivays(29, 12, 2011); "
                + "ja kutsut metodia paivays.etene() kaksi kertaa, tulee päivän olla 1.1.2012.\n";
        Assert.assertEquals(virhe, "1.1.2012", muokattava.toString());
    }

    @Points("05-14.1")
    @Test
    public void metodinEteneToistuvaKutsuminenSiirtaaMyosKuukausia() {
        Class c = Paivays.class;
        String metodi = "etene";
        String virhe = "Tee luokalle Paivays metodi public void etene()";
        Method etene = null;
        try {
            etene = ReflectionUtils.requireMethod(c, metodi);
        } catch (Throwable t) {
            fail(virhe);
        }
        assertTrue(virhe, etene.toString().contains("public"));
        assertFalse(virhe, etene.toString().contains("static"));

        Paivays muokattava = new Paivays(30, 12, 2011);

        try {
            for (int i = 0; i < 500; i++) {
                ReflectionUtils.invokeMethod(void.class, etene, muokattava);
            }
        } catch (Throwable t) {
            fail("Varmista että luokalla Paivays on metodi public void etene().");
        }

        virhe = "Tarkista että etene-metodin kutsuminen siirtää päivää yhdellä. \n"
                + "Kun luot olion Paivays paivays = new Paivays(30, 12, 2011); ja kutsut metodia paivays.etene() 500 kertaa, päiväyksen tulee olla vuodessa 2013.\nTulostit: " + muokattava.toString();

        Assert.assertTrue(virhe, muokattava.toString().contains("2013"));
    }

    @Points("05-14.2")
    @Test
    public void onParametrillinenMetodiEtene() throws Throwable {
        String metodi = "etene";

        Object olio = new Paivays(1, 1, 2011);

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "(int paivia) ",
                klass.method(olio, metodi)
                        .returningVoid().taking(int.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi Paivays p = new Paivays(1, 1, 2011); "
                + "p.etene(23);";

        klass.method(olio, metodi)
                .returningVoid().taking(int.class).withNiceError(v).invoke(23);

    }

    @Points("05-14.2")
    @Test
    public void parametrillinenMetodiEteneSiirtaaPaivalla() {
        Class c = Paivays.class;
        String metodi = "etene";
        String virhe = "Tee luokalle Paivays metodi public void etene(int paivia)";
        Method etene = null;
        try {
            etene = ReflectionUtils.requireMethod(c, metodi, int.class);
        } catch (Throwable t) {
            fail(virhe);
        }
        assertTrue(virhe, etene.toString().contains("public"));
        assertFalse(virhe, etene.toString().contains("static"));

        Paivays muokattava = new Paivays(30, 12, 2011);
        try {
            ReflectionUtils.invokeMethod(void.class, etene, muokattava, 3);
        } catch (Throwable t) {
            fail("Varmista että luokalla Paivays on metodi public void etene(int paivia).");
        }

        Paivays menneisyys = new Paivays(2, 1, 2012);
        Paivays tulevaisuus = new Paivays(4, 1, 2012);

        Assert.assertEquals("Tarkista että parametrillinen etene-metodi siirtää päiviä annetulla määrällä. \n"
                + "Kutsu Paivays paivays = new Paivays(30, 12, 2011); paivays.etene(3); Nyt paivayksen tulee olla 3.1.2012.\n",
                "3.1.2012",
                muokattava.toString());
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
