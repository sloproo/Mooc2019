
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;

public class RuokalistaTest {

    @Rule
    public MockStdio stdio = new MockStdio();

    @Test
    @Points("05-16.1")
    public void onkoMetodiaMetodiLisaaAteria() throws Throwable {
        String klassName = "Ruokalista";

        String metodi = "lisaaAteria";

        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassName);
        Object olio = tuoteClass.constructor().takingNoParams().invoke();

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "(String ateria) ", tuoteClass.method(olio, metodi)
                .returningVoid().taking(String.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi: rl = new Ruokalista(); rl.lisaaAteria(\"Bratwurst\");";

        tuoteClass.method(olio, metodi)
                .returningVoid().taking(String.class).withNiceError(v).invoke("Bratwurst");
    }

    @Test
    @Points("05-16.1")
    public void lisaaAteriaLisaaUudenAterian() {
        Field ateriatField = null;
        try {
            ateriatField = Ruokalista.class.getDeclaredField("ateriat");
        } catch (NoSuchFieldException ex) {
            fail("Varmista että luokassa Ruokalista on attribuutti private ArrayList<String> ateriat;");
        }

        Ruokalista lista = new Ruokalista();
        ateriatField.setAccessible(true);

        Method m = ReflectionUtils.requireMethod(Ruokalista.class, "lisaaAteria", String.class);

        try {
            ReflectionUtils.invokeMethod(void.class, m, lista, "eka");
        } catch (Throwable ex) {
            fail("Varmista että metodi lisaaAteria on void-tyyppinen, eli ei palauta arvoa.");
        }
        try {
            ArrayList<String> ateriat = (ArrayList<String>) ateriatField.get(lista);
            if (ateriat.size() != 1) {
                fail("Ruokalistan lisaaAteria-metodin kutsumisen pitäisi lisätä ateria ateriat-listalle.");
            }
            try {
                ReflectionUtils.invokeMethod(void.class, m, lista, "toka");
            } catch (Throwable ex) {
                Logger.getLogger(RuokalistaTest.class.getName()).log(Level.SEVERE, null, ex);

            }
            if (ateriat.size() != 2) {
                fail("Kun ruokalistaan lisätään kaksi erinimistä ateriaa, listalla pitäisi olla kaksi ateriaa.");
            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(RuokalistaTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(RuokalistaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    @Points("05-16.1")
    public void samanNimisetAteriatLisataanVainKerran() {
        Field ateriatField = null;
        try {
            ateriatField = Ruokalista.class.getDeclaredField("ateriat");
        } catch (NoSuchFieldException ex) {
            fail("Varmista että luokassa Ruokalista on attribuutti private ArrayList<String> ateriat;");
        }

        ateriatField.setAccessible(true);

        Ruokalista lista = new Ruokalista();
        Method m = ReflectionUtils.requireMethod(Ruokalista.class, "lisaaAteria", String.class);
        try {
            ReflectionUtils.invokeMethod(void.class, m, lista, "eka");
            ReflectionUtils.invokeMethod(void.class, m, lista, "eka");
        } catch (Throwable ex) {
            Logger.getLogger(RuokalistaTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<String> ateriat;
        try {
            ateriat = (ArrayList<String>) ateriatField.get(lista);
            if (ateriat.size() != 1) {
                fail("Sama ruoka saa olla listalla vain kerran.");
            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(RuokalistaTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(RuokalistaTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    @Points("05-16.2")
    public void onkoMetodiaMetodiTulostaAteria() throws Throwable {
        String klassName = "Ruokalista";

        String metodi = "tulostaAteriat";

        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassName);
        Object olio = tuoteClass.constructor().takingNoParams().invoke();

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "() ", tuoteClass.method(olio, metodi)
                .returningVoid().takingNoParams().isPublic());

        String v = "\nVirheen aiheuttanut koodi: rl = new Ruokalista(); rl.tulostaAteriat();";

        tuoteClass.method(olio, metodi)
                .returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("05-16.2")
    public void metodiTulostaAteriatTulostaaListan() {
        String porkkanaSoppa = "Le porkkanaSuppa";
        String kinkkukiusaus = "Kinkkukiusaus";

        Ruokalista lista = new Ruokalista();

        Method lisaaAteria = ReflectionUtils.requireMethod(Ruokalista.class, "lisaaAteria", String.class);

        try {
            ReflectionUtils.invokeMethod(void.class, lisaaAteria, lista, porkkanaSoppa);
            ReflectionUtils.invokeMethod(void.class, lisaaAteria, lista, kinkkukiusaus);
        } catch (Throwable ex) {
            fail("Varmista että aterian lisääminen uuteen ruokalistaan onnistuu.");
        }

        Method m = ReflectionUtils.requireMethod(Ruokalista.class, "tulostaAteriat");
        try {
            ReflectionUtils.invokeMethod(void.class, m, lista);
        } catch (Throwable ex) {
            fail("Varmista että aterioiden tulostaminen onnistuu kun aterioita on enemmän kuin yksi.");
        }

        String out = stdio.getSysOut();
        assertTrue("Aterioiden tulostamisen tulee tulostaa kaikki lisätyt ateriat.", out.contains(porkkanaSoppa) && out.contains(kinkkukiusaus));
        assertTrue("Jokaisen aterian pitää tulostua omalle rivilleen. Nyt tulostuu " + out, out.split("\n").length > 1);

    }

    @Test
    @Points("05-16.2")
    public void onkoMetodiaMetodiTyhjennaRuokalista() throws Throwable {
        String klassName = "Ruokalista";

        String metodi = "tyhjennaRuokalista";

        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassName);
        Object olio = tuoteClass.constructor().takingNoParams().invoke();

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "() ", tuoteClass.method(olio, metodi)
                .returningVoid().takingNoParams().isPublic());

        String v = "\nVirheen aiheuttanut koodi: rl = new Ruokalista(); rl.tyhjennaRuokalista();";

        tuoteClass.method(olio, metodi)
                .returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("05-16.3")
    public void ruokalistanTyhjennysTyhjentaaRuokalistan() {
        Field ateriatField = null;
        try {
            ateriatField = Ruokalista.class.getDeclaredField("ateriat");
        } catch (NoSuchFieldException ex) {
            fail("Varmista että luokassa Ruokalista on attribuutti private ArrayList<String> ateriat;");
        }

        ateriatField.setAccessible(true);

        Ruokalista lista = new Ruokalista();
        Method lisaaAteria = ReflectionUtils.requireMethod(Ruokalista.class, "lisaaAteria", String.class);

        try {
            ReflectionUtils.invokeMethod(void.class, lisaaAteria, lista, "eka");
            ReflectionUtils.invokeMethod(void.class, lisaaAteria, lista, "toka");
        } catch (Throwable ex) {
            fail("Varmista että metodi lisaaAteria on void-tyyppinen, eli ei palauta arvoa.");
        }

        Method tyhjenna = ReflectionUtils.requireMethod(Ruokalista.class, "tyhjennaRuokalista");
        try {
            ReflectionUtils.invokeMethod(void.class, tyhjenna, lista);
        } catch (Throwable ex) {
            fail("Varmista että metodi tyhjennaRuokalista on void-tyyppinen, eli ei palauta arvoa. Varmista myös että se toimii.");
        }

        try {
            ArrayList<String> ateriat = (ArrayList<String>) ateriatField.get(lista);
            if (ateriat == null) {
                fail("Älä tyhjennä ruokalistaa asettamalla ateriat-muuttujan arvoa null:iksi");
            }

            if (!ateriat.isEmpty()) {
                fail("Ruokalistan tulee olla tyhjä kun metodia tyhjennaRuokalista on kutsuttu.");
            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(RuokalistaTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(RuokalistaTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Method m = ReflectionUtils.requireMethod(Ruokalista.class, "tulostaAteriat");
        try {
            ReflectionUtils.invokeMethod(void.class, m, lista);
        } catch (Throwable ex) {
            fail("Varmista että aterioiden tulostaminen onnistuu kun aterioita on enemmän kuin yksi.");
        }

        String out = stdio.getSysOut();
        out = out.trim();
        if (!out.isEmpty()) {
            fail("Tyhjennetyn ruokalistan tulostamisen ei pitäisi tulostaa mitään.");
        }
    }
}
