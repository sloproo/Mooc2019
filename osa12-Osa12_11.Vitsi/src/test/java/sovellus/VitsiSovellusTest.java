package sovellus;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import javafx.application.Application;
import javafx.stage.Stage;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;

@Points("12-11")
public class VitsiSovellusTest extends ApplicationTest {

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
        VitsiSovellus sovellus = new VitsiSovellus();

        try {
            Application app = Application.class.cast(sovellus);
        } catch (Throwable t) {
            fail("Periihän luokka VitsiSovellus luokan Application.");
        }

        try {
            Reflex.reflect(VitsiSovellus.class).method("start").returningVoid().taking(Stage.class).invokeOn(sovellus, stage);
        } catch (Throwable ex) {
            fail("Onhan luokalla VitsiSovellus metodi start, joka saa parametrina Stage-olion. Jos on, tarkista että metodi toimii. Virhe: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    public void tarkista() {
        FxAssert.verifyThat(".label", LabeledMatchers.hasText("What do you call a bear with no teeth?"));
        clickOn("Vitsi");
        FxAssert.verifyThat(".label", LabeledMatchers.hasText("What do you call a bear with no teeth?"));
        clickOn("Vastaus");
        FxAssert.verifyThat(".label", LabeledMatchers.hasText("A gummy bear."));
        clickOn("Vitsi");
        FxAssert.verifyThat(".label", LabeledMatchers.hasText("What do you call a bear with no teeth?"));
        clickOn("Selitys");
        FxAssert.verifyThat(".label", NodeMatchers.isNotNull());
    }
}
