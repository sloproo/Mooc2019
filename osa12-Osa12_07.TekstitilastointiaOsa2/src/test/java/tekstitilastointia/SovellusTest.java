package tekstitilastointia;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.Arrays;
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

@Points("12-07")
public class SovellusTest extends ApplicationTest {

    private Stage stage;

    static {
        if (Boolean.getBoolean("SERVER")) {

            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("glass.platform", "Monocle");
            System.setProperty("monocle.platform", "Headless");
            System.setProperty("java.awt.headless", "false");
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
    public void tayttoMuuttaaTilastoja1() {
        syotaJaTarkista("You miss 100 percent of the shots you never take. - Gretzky");
    }

    @Test
    public void tayttoMuuttaaTilastoja2() {
        syotaJaTarkista("We are what we repeatedly do; excellence, then, is not an act but a habit. - Aristotle");
    }

    @Test
    public void tayttoMuuttaaTilastoja3() {
        syotaJaTarkista("You must be the change you wish to see in the world. - Gandhi");
    }

    private void syotaJaTarkista(String syotettava) {
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

        clickOn(borderPane.getCenter());
        write(syotettava);

        HBox box = (HBox) borderPane.getBottom();
        assertEquals("Odotettiin, että alalaidassa on kolme tekstielementtiä. Nyt elementtejä oli: " + box.getChildren().size(), 3, box.getChildren().size());

        List<Node> tekstielementit = box.getChildren();
        for (Node node : tekstielementit) {
            assertTrue("HBox-oliolle lisätyt elementit pitäisi olla Label-elementtejä. Nyt eivät olleet. Löytyi: " + node, node.getClass().isAssignableFrom(Label.class));
        }

        int pituus = syotettava.length();
        int sanoja = syotettava.split(" ").length;
        String pisin = Arrays.stream(syotettava.split(" "))
                .sorted((s1, s2) -> s2.length() - s1.length())
                .findFirst()
                .get();

        assertEquals("Ensimmäisessä tekstielementissä piti olla teksti \"Kirjaimia: " + pituus + "\". Nyt teksti oli: \"" + ((Label) tekstielementit.get(0)).getText() + "\"", "Kirjaimia: " + pituus, ((Label) tekstielementit.get(0)).getText());
        assertEquals("Toisessa tekstielementissä piti olla teksti \"Sanoja: " + sanoja + "\". Nyt teksti oli: \"" + ((Label) tekstielementit.get(1)).getText() + "\"", "Sanoja: " + sanoja, ((Label) tekstielementit.get(1)).getText());
        assertEquals("Kolmannessa tekstielementissä piti olla teksti \"Pisin sana on: " + pisin + "\". Nyt teksti oli: \"" + ((Label) tekstielementit.get(2)).getText() + "\"", "Pisin sana on: " + pisin, ((Label) tekstielementit.get(2)).getText().trim());

    }

}
