package sovellus;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

@Points("12-09")
public class UseampiNakymaTest extends ApplicationTest {

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
        UseampiNakyma sovellus = new UseampiNakyma();

        try {
            Application app = Application.class.cast(sovellus);
        } catch (Throwable t) {
            fail("Periihän luokka UseampiNakyma luokan Application.");
        }

        try {
            Reflex.reflect(UseampiNakyma.class).method("start").returningVoid().taking(Stage.class).invokeOn(sovellus, stage);
        } catch (Throwable ex) {
            fail("Onhan luokalla UseampiNakyma metodi start, joka saa parametrina Stage-olion. Jos on, tarkista että metodi toimii. Virhe: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    public void ekaNakyma() {
        Scene scene = stage.getScene();
        assertNotNull("Stage-oliolla pitäisi olla Scene-olio. Nyt start-metodin suorituksen jälkeen stagelle tehty kutsu getScene palautti null-viitteen.", scene);
        Parent elementtienJuuri = scene.getRoot();
        assertNotNull("Ensimmäistä näkymää vastaavalle Scene-oliolle tulee antaa parametrina käyttöliittymäkomponenttien asetteluun tarkoitettu olio (tässä BorderPane). Nyt Scene-oliolta ei löytynyt komponentteja sisältävää oliota.", elementtienJuuri);

        BorderPane asettelu = null;
        try {
            asettelu = BorderPane.class.cast(elementtienJuuri);
        } catch (Throwable t) {
            fail("Käytäthän ensimmäisen näkymän käyttöliittymäkomponenttien asetteluun BorderPane-luokkaa.");
        }

        assertNotNull("Ensimmäisestä näkymästä vastuussa olevalle Scene-oliolle tulee antaa parametrina käyttöliittymäkomponenttien asetteluun tarkoitettu BorderPane-olio.", asettelu);
        assertTrue("BorderPanen keskellä pitäisi olla Button-olio. Nyt siellä oli: " + asettelu.getCenter(), asettelu.getCenter().getClass().isAssignableFrom(Button.class));
        assertTrue("BorderPanen ylälaidassa pitäisi olla Label-olio. Nyt siellä oli: " + asettelu.getTop(), asettelu.getTop().getClass().isAssignableFrom(Label.class));

        clickOn(".button");
        Scene toka = stage.getScene();
        assertNotEquals("Kun käyttöliittymän nappia painetaan, näytetyn näkymän tulee vaihtua. Nyt napin painalluksen jälkeen Stage-olion Scene-olio oli sama kuin ennen painallusta.", scene, toka);
    }

    @Test
    public void tokaNakyma() {
        clickOn(".button");

        Scene scene = stage.getScene();
        assertNotNull("Stage-oliolla pitäisi olla Scene-olio. Nyt napin painalluksen jälkeen stagelle tehty kutsu getScene palautti null-viitteen.", scene);
        Parent elementtienJuuri = scene.getRoot();
        assertNotNull("Toista näkymää vastaavalle Scene-oliolle tulee antaa parametrina käyttöliittymäkomponenttien asetteluun tarkoitettu olio (tässä VBox). Nyt Scene-oliolta ei löytynyt komponentteja sisältävää oliota.", elementtienJuuri);

        VBox asettelu = null;
        try {
            asettelu = VBox.class.cast(elementtienJuuri);
        } catch (Throwable t) {
            fail("Käytäthän toisen näkymän käyttöliittymäkomponenttien asetteluun VBox-luokkaa.");
        }

        assertNotNull("Toisesta näkymästä vastuussa olevalle Scene-oliolle tulee antaa parametrina käyttöliittymäkomponenttien asetteluun tarkoitettu VBox-olio.", asettelu);

        assertEquals("VBox-olioon pitäisi olla lisättynä kaksi komponenttia. Nyt niitä oli: " + asettelu.getChildren().size(), 2, asettelu.getChildren().size());

        assertTrue("VBox-olion ensimmäisen alkion pitäisi olla Button-olio. Nyt siellä oli: " + asettelu.getChildren().get(0), asettelu.getChildren().get(0).getClass().isAssignableFrom(Button.class));
        assertTrue("VBox-olion toisen alkion pitäisi olla Label-olio. Nyt siellä oli: " + asettelu.getChildren().get(1), asettelu.getChildren().get(1).getClass().isAssignableFrom(Label.class));

        clickOn(".button");
        Scene toka = stage.getScene();
        assertNotEquals("Kun käyttöliittymän nappia painetaan, näytetyn näkymän tulee vaihtua. Nyt toisessa näkymässä tapahtuneen napin painalluksen jälkeen Stage-olion Scene-olio oli sama kuin ennen painallusta.", scene, toka);
    }

    @Test
    public void kolmasNakyma() {
        Scene eka = stage.getScene();
        clickOn(".button");
        clickOn(".button");

        Scene scene = stage.getScene();
        assertNotNull("Stage-oliolla pitäisi olla Scene-olio. Nyt napin painalluksen jälkeen stagelle tehty kutsu getScene palautti null-viitteen.", scene);
        Parent elementtienJuuri = scene.getRoot();
        assertNotNull("Kolmatta näkymää vastaavalle Scene-oliolle tulee antaa parametrina käyttöliittymäkomponenttien asetteluun tarkoitettu olio (tässä GridPane). Nyt Scene-oliolta ei löytynyt komponentteja sisältävää oliota.", elementtienJuuri);

        GridPane asettelu = null;
        try {
            asettelu = GridPane.class.cast(elementtienJuuri);
        } catch (Throwable t) {
            fail("Käytäthän kolmannen näkymän käyttöliittymäkomponenttien asetteluun GridPane-luokkaa.");
        }

        assertNotNull("Kolmannesta näkymästä vastuussa olevalle Scene-oliolle tulee antaa parametrina käyttöliittymäkomponenttien asetteluun tarkoitettu GridPane-olio.", asettelu);

        assertEquals("GridPane-olioon pitäisi olla lisättynä kaksi komponenttia. Nyt niitä oli: " + asettelu.getChildren().size(), 2, asettelu.getChildren().size());

        assertTrue("GridPane-olion ensimmäisen alkion pitäisi olla Label-olio. Nyt siellä oli: " + asettelu.getChildren().get(0), asettelu.getChildren().get(0).getClass().isAssignableFrom(Label.class));
        assertTrue("GridPane-olion toisen alkion pitäisi olla Button-olio. Nyt siellä oli: " + asettelu.getChildren().get(1), asettelu.getChildren().get(1).getClass().isAssignableFrom(Button.class));

        clickOn(".button");
        Scene toka = stage.getScene();
        assertEquals("Kun kolmannessa näkymässä painetaan nappia, pitäisi päätyä ensimmäiseen näkymään. Nyt napin painallusta seurannut näkymä ei ollut sama kuin ensimmäinen näkymä.", eka, toka);
    }
}
