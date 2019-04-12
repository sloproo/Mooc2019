package tekstitilastointia;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.testfx.framework.junit.ApplicationTest;

@Points("12-05")
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
        TekstitilastointiaSovellus sovellus = new TekstitilastointiaSovellus();

        try {
            Application app = Application.class.cast(sovellus);
        } catch (Throwable t) {
            fail("Periihän luokka TekstitilastointiaSovellus luokan Application.");
        }

        try {
            Reflex.reflect(TekstitilastointiaSovellus.class).method("start").returningVoid().taking(Stage.class).invokeOn(sovellus, stage);
        } catch (Throwable ex) {
            fail("Onhan luokalla TekstitilastointiaSovellus metodi start, joka saa parametrina Stage-olion. Jos on, tarkista että metodi toimii. Virhe: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
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
        assertTrue("BorderPanessa tulee olla asetettuna keskiosaan TextArea-olio. Nyt keskellä oli: " + borderPane.getCenter(), borderPane.getCenter() != null && borderPane.getCenter().getClass().isAssignableFrom(TextArea.class));
        assertTrue("BorderPanessa tulee olla asetettuna alaosaan HBox-olio. Nyt alaosassa oli: " + borderPane.getBottom(), borderPane.getBottom() != null && borderPane.getBottom().getClass().isAssignableFrom(HBox.class));

        HBox box = (HBox) borderPane.getBottom();
        assertEquals("Odotettiin, että alalaidassa on kolme tekstielementtiä. Nyt elementtejä oli: " + box.getChildren().size(), 3, box.getChildren().size());

        List<Node> tekstielementit = box.getChildren();
        for (Node node : tekstielementit) {
            assertTrue("HBox-oliolle lisätyt elementit pitäisi olla Label-elementtejä. Nyt eivät olleet. Löytyi: " + node, node.getClass().isAssignableFrom(Label.class));
        }

        assertEquals("Ensimmäisessä tekstielementissä piti olla teksti \"Kirjaimia: 0\". Nyt teksti oli: \"" + ((Label) tekstielementit.get(0)).getText() + "\"", "Kirjaimia: 0", ((Label) tekstielementit.get(0)).getText());
        assertEquals("Toisessa tekstielementissä piti olla teksti \"Sanoja: 0\". Nyt teksti oli: \"" + ((Label) tekstielementit.get(1)).getText() + "\"", "Sanoja: 0", ((Label) tekstielementit.get(1)).getText());
        assertEquals("Kolmannessa tekstielementissä piti olla teksti \"Pisin sana on:\". Nyt teksti oli: \"" + ((Label) tekstielementit.get(2)).getText() + "\"", "Pisin sana on:", ((Label) tekstielementit.get(2)).getText().trim());
    }

}
