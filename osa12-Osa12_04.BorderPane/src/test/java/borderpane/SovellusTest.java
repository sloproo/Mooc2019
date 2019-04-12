package borderpane;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.testfx.framework.junit.ApplicationTest;

public class SovellusTest extends ApplicationTest {

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
        BorderPaneSovellus sovellus = new BorderPaneSovellus();

        try {
            Application app = Application.class.cast(sovellus);
        } catch (Throwable t) {
            fail("Periihän luokka BorderPaneSovellus luokan Application.");
        }

        try {
            Reflex.reflect(BorderPaneSovellus.class).method("start").returningVoid().taking(Stage.class).invokeOn(sovellus, stage);
        } catch (Throwable ex) {
            fail("Onhan luokalla BorderPaneSovellus metodi start, joka saa parametrina Stage-olion. Jos on, tarkista että metodi toimii. Virhe: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    @Points("12-04")
    public void loytyyHalututElementit() {
        Scene scene = stage.getScene();
        assertNotNull("Stage-oliolla pitäisi olla Scene-olio. Nyt start-metodin suorituksen jälkeen stagelle tehty kutsu getScene palautti null-viitteen.", scene);
        Parent elementtienJuuri = scene.getRoot();
        assertNotNull("Scene-oliolle tulee antaa parametrina käyttöliittymäkomponenttien asetteluun tarkoitettu olio (tässä BorderPane). Nyt Scene-oliolta ei löytynyt komponentteja sisältävää oliota.", elementtienJuuri);

        BorderPane borderPane = null;
        try {
            borderPane = BorderPane.class.cast(elementtienJuuri);
        } catch (Throwable t) {
            fail("Käytäthän käyttöliittymäkomponenttien asetteluun BorderPane-luokkaa.");
        }

        assertNotNull("Scene-oliolle tulee antaa parametrina käyttöliittymäkomponenttien asetteluun tarkoitettu BorderPane-olio.", borderPane);
        assertTrue("BorderPanessa tulee olla asetettuna yläosaan tekstikenttä. Nyt yläosassa oli: " + borderPane.getTop(), borderPane.getTop() != null && borderPane.getTop().getClass().isAssignableFrom(Label.class));
        assertTrue("BorderPanessa tulee olla asetettuna oikeaan laitaan tekstikenttä. Nyt oikeassa laidassa oli: " + borderPane.getRight(), borderPane.getRight() != null && borderPane.getRight().getClass().isAssignableFrom(Label.class));
        assertTrue("BorderPanessa tulee olla asetettuna alaosaan tekstikenttä. Nyt alaosassa oli: " + borderPane.getBottom(), borderPane.getBottom() != null && borderPane.getBottom().getClass().isAssignableFrom(Label.class));

        assertEquals("Ylälaidassa tulee olla teksti \"NORTH\". Nyt teksti oli: " + ((Label) borderPane.getTop()).getText(), "NORTH", ((Label) borderPane.getTop()).getText());
        assertEquals("Oikeassa laidassa tulee olla teksti \"EAST\". Nyt teksti oli: " + ((Label) borderPane.getRight()).getText(), "EAST", ((Label) borderPane.getRight()).getText());
        assertEquals("Alalaidassa tulee olla teksti \"SOUTH\". Nyt teksti oli: " + ((Label) borderPane.getBottom()).getText(), "SOUTH", ((Label) borderPane.getBottom()).getText());
    }

}
