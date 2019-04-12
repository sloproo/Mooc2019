package sovellukseni;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.testfx.framework.junit.ApplicationTest;

public class SovellukseniTest extends ApplicationTest {

    private Stage stage;

    static {
        if (Boolean.getBoolean("SERVER")) {
            System.setProperty("java.awt.headless", "true");
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("glass.platform", "Monocle");
            System.setProperty("monocle.platform", "Headless");
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Sovellukseni sovellukseni = new Sovellukseni();

        try {
            Application app = Application.class.cast(sovellukseni);
        } catch (Throwable t) {
            fail("Periihän luokka Sovellukseni luokan Application.");
        }

        try {
            Reflex.reflect(Sovellukseni.class).method("start").returningVoid().taking(Stage.class).invokeOn(sovellukseni, stage);
        } catch (Throwable ex) {
            fail("Onhan luokalla Sovellukseni metodi start, joka saa parametrina Stage-olion. Jos on, tarkista että metodi toimii. Virhe: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    @Points("12-01")
    public void otsikkoOikein() {
        assertEquals("Sovellukseni", stage.getTitle());
    }

    @Test
    @Points("12-01")
    public void ikkunaNakyy() {
        assertTrue("Kutsuthan metodia show() ikkunan näyttämiseksi. Nyt testien mukaan ikkuna ei näy.", stage.isShowing());
    }

}
