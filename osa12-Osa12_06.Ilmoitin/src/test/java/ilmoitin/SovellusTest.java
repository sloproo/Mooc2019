package ilmoitin;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.testfx.framework.junit.ApplicationTest;

@Points("12-06")
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
        IlmoitinSovellus sovellus = new IlmoitinSovellus();

        try {
            Application app = Application.class.cast(sovellus);
        } catch (Throwable t) {
            fail("Periihän luokka IlmoitinSovellus luokan Application.");
        }

        try {
            Reflex.reflect(IlmoitinSovellus.class).method("start").returningVoid().taking(Stage.class).invokeOn(sovellus, stage);
        } catch (Throwable ex) {
            fail("Onhan luokalla IlmoitinSovellus metodi start, joka saa parametrina Stage-olion. Jos on, tarkista että metodi toimii. Virhe: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    public void toimiiOletetusti() {
        Scene scene = stage.getScene();
        assertNotNull("Stage-oliolla pitäisi olla Scene-olio. Nyt start-metodin suorituksen jälkeen stagelle tehty kutsu getScene palautti null-viitteen.", scene);
        Parent elementtienJuuri = scene.getRoot();
        assertNotNull("Scene-oliolle tulee antaa parametrina käyttöliittymäkomponenttien asetteluun tarkoitettu olio (tässä VBox). Nyt Scene-oliolta ei löytynyt komponentteja sisältävää oliota.", elementtienJuuri);

        VBox vbox = null;
        try {
            vbox = VBox.class.cast(elementtienJuuri);
        } catch (Throwable t) {
            fail("Käytäthän käyttöliittymäkomponenttien asetteluun VBox-luokkaa.");
        }

        assertNotNull("Scene-oliolle tulee antaa parametrina käyttöliittymäkomponenttien asetteluun tarkoitettu VBox-olio.", vbox);

        assertEquals("Odotettiin, että käyttöliittymässä on kolme tekstielementtiä. Nyt elementtejä oli: " + vbox.getChildren().size(), 3, vbox.getChildren().size());

        Node eka = vbox.getChildren().get(0);
        Node toka = vbox.getChildren().get(1);
        Node kolmas = vbox.getChildren().get(2);
        assertTrue("Ensimmäisen VBox-oliolle lisätyn elementin pitäisi olla TextField-olio. Nyt ei ollut. Löytyi: " + eka, eka.getClass().isAssignableFrom(TextField.class));
        assertTrue("Toisen VBox-oliolle lisätyn elementin pitäisi olla Button-olio. Nyt ei ollut. Löytyi: " + toka, toka.getClass().isAssignableFrom(Button.class));
        assertTrue("Kolmannen VBox-oliolle lisätyn elementin pitäisi olla Label-olio. Nyt ei ollut. Löytyi: " + kolmas, kolmas.getClass().isAssignableFrom(Label.class));

        TextField tekstikentta = (TextField) vbox.getChildren().get(0);
        Button nappi = (Button) vbox.getChildren().get(1);
        Label tekstielementti = (Label) vbox.getChildren().get(2);

        tekstikentta.setText("Hei maailma!");

        clickOn(nappi);

        assertEquals("Kun tekstikentässä on teksti \"Hei maailma!\" ja nappia painetaan, tekstielementtiin pitäisi kopioitua sama teksti. Nyt tekstielementissä oli: " + tekstielementti.getText(), "Hei maailma!", tekstielementti.getText());

        tekstikentta.setText("Ja mualima!");
        clickOn(nappi);
        assertEquals("Kun tekstikentässä on teksti \"Ja mualima!\" ja nappia painetaan, tekstielementtiin pitäisi kopioitua sama teksti. Nyt tekstielementissä oli: " + tekstielementti.getText(), "Ja mualima!", tekstielementti.getText());
    }
}
