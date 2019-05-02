
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

@Points("06-07")
public class VelkakirjaTest {

    String klassName = "Velkakirja";
    Reflex.ClassRef<Object> klass;

    @Before
    public void setup() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void luokkaJulkinen() {
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus(klassName, 1, "velat tallentavan HashMap<String, Double>-tyyppisen oliomuuttujan");
    }

    @Test
    public void onHashMap() {
        Field[] kentat = ReflectionUtils.findClass(klassName).getDeclaredFields();
        assertTrue("Lisää luokalle " + klassName + " HashMap<String, Double> -tyyppinen oliomuuttuja", kentat.length == 1);
        assertTrue("Luokalla " + klassName + " tulee olla HashMap<String, Double> -tyyppinen oliomuuttuja", kentat[0].toString().contains("HashMap"));
    }

    @Test
    public void testaaJoukkueKonstruktori() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "()", ctor.isPublic());
        String v = "virheen aiheutti koodi new Velkakirja();";
        ctor.withNiceError(v).invoke();
    }

    public Object luo() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        return ctor.invoke();
    }

    @Test
    public void asetaLaina() throws Throwable {
        String metodi = "asetaLaina";

        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "(String kuka, double maara) ",
                klass.method(olio, metodi)
                        .returningVoid().taking(String.class, double.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi Velkakirja velat = new Velkakirja(); "
                + "v.asetaLaina(\"Pekka\", 5.0);";

        klass.method(olio, metodi)
                .returningVoid().taking(String.class, double.class).withNiceError(v).invoke("Pekka", 5.0);
    }

    @Test
    public void paljonkoVelkaa() throws Throwable {
        String metodi = "paljonkoVelkaa";

        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public double " + metodi + "(String kuka) ",
                klass.method(olio, metodi)
                        .returning(double.class).taking(String.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi Velkakirja velat = new Velkakirja(); "
                + "v.paljonkoVelkaa(\"Pekka\");";

        klass.method(olio, metodi)
                .returning(double.class).taking(String.class).withNiceError(v).invoke("Pekka");
    }

    @Test
    public void testaaVelkakirjaa() throws Exception {
        Object velkakirja = luoVelkakirja();
        testaaVelka(velkakirja, "Arto", 919.83);
        testaaVelka(velkakirja, "Matti", 32.1);
        testaaVelka(velkakirja, "Joel", -5);
        testaaAsettamatonVelka(velkakirja, "Mikael");
    }

    private void testaaVelka(Object velkakirja, String kenelle, double maara) {
        asetaLaina(velkakirja, kenelle, maara);
        double velka = paljonkoVelkaa(velkakirja, kenelle);
        if (velka <= (maara - 0.1) || velka >= (maara + 0.1)) {
            fail("Henkilölle " + kenelle + " asetettiin laina " + maara
                    + ", mutta velaksi palautettiin: " + velka);
        }
    }

    private void testaaAsettamatonVelka(Object velkakirja, String kenelle) {
        double velka = paljonkoVelkaa(velkakirja, kenelle);
        if (velka != 0) {
            fail("Henkilölle " + kenelle + " ei ole asetettu lainaa, "
                    + "mutta velaksi palautettiin: " + velka);
        }
    }

    private Object luoVelkakirja() throws Exception {
        return Class.forName("Velkakirja").getConstructor().newInstance();
    }

    private void asetaLaina(Object velkakirja, String kenelle, double maara) {
        Method metodi;
        try {
            metodi = velkakirja.getClass().getMethod("asetaLaina", String.class, double.class);
        } catch (Exception e) {
            fail("Velkakirja-luokalla ei ole metodia: public void asetaLaina(String kenelle, double maara).");
            return;
        }

        if (!metodi.getReturnType().equals(void.class)) {
            fail("Velkakirja-luokan metodilla asetaLaina(String kenelle, double maara) ei tule olla paluuarvoa.");
            return;
        }

        try {
            metodi.invoke(velkakirja, kenelle, maara);
        } catch (Exception e) {
            fail("Velkakirja-luokan asetaLaina-metodissa tapahtui poikkeus: " + e.toString());
        }
    }

    private double paljonkoVelkaa(Object velkakirja, String kuka) {
        Method metodi;
        try {
            metodi = velkakirja.getClass().getMethod("paljonkoVelkaa", String.class);
        } catch (Exception e) {
            fail("Velkakirja-luokalla ei ole metodia: public double paljonkoVelkaa(String kuka).");
            return -1;
        }

        if (!metodi.getReturnType().equals(double.class)) {
            fail("Velkakirja-luokan metodin paljonkoVelkaa(String kuka) tulee palauttaa double-tyyppinen paluuarvo.");
            return -1;
        }

        try {
            return (Double) metodi.invoke(velkakirja, kuka);
        } catch (java.lang.reflect.InvocationTargetException e) {
            fail("Tarkistathan ettei null-tyyppisiä viitteitä yritetä muuttaa alkeistyyppisiksi muuttujiksi.");
            return -1;
        } catch (Exception e) {
            fail("Velkakirja-luokan paljonkoVelkaa-metodissa tapahtui poikkeus: " + e.toString());
            return -1;
        }
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
