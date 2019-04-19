package hymio;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.service.support.Capture;

@Points("13-06")
public class HymioTest extends ApplicationTest {

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
        HymioSovellus sovellus = new HymioSovellus();

        try {
            Application app = Application.class.cast(sovellus);
        } catch (Throwable t) {
            fail("Periihän luokka HymioSovellus luokan Application.");
        }

        try {
            Reflex.reflect(HymioSovellus.class).method("start").returningVoid().taking(Stage.class).invokeOn(sovellus, stage);
        } catch (Throwable ex) {
            fail("Onhan luokalla HymioSovellus metodi start, joka saa parametrina Stage-olion. Jos on, tarkista että metodi toimii. Virhe: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    public void stagenScenellaBorderPane() {
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
        assertTrue("BorderPanen keskellä pitäisi olla Canvas-olio. Nyt siellä oli: " + asettelu.getCenter(), asettelu.getCenter().getClass().isAssignableFrom(Canvas.class));
    }

    @Test
    public void canvasiinPiirrettyJotain() {

        stagenScenellaBorderPane();
        Scene scene = stage.getScene();
        Parent elementtienJuuri = scene.getRoot();
        BorderPane asettelu = BorderPane.class.cast(elementtienJuuri);

        Canvas piirtoalusta = Canvas.class.cast(asettelu.getCenter());
        Capture kuvakaappaus = capture(piirtoalusta);

        Image kuva = kuvakaappaus.getImage();

        PixelReader kuvanlukija = kuva.getPixelReader();

        double w = piirtoalusta.getWidth();
        double h = piirtoalusta.getHeight();

        boolean whiteSeen = false;
        boolean blackSeen = false;

        for (int x = 0; x < w; x++) {

            for (int y = 0; y < h; y++) {
                if (kuvanlukija.getColor(x, y).equals(Color.WHITE)) {
                    whiteSeen = true;
                }
                if (kuvanlukija.getColor(x, y).equals(Color.BLACK)) {
                    blackSeen = true;
                }
            }
        }

        assertTrue("Käytäthän piirrustuksessa valkoista väriä (Color.WHITE).", whiteSeen);
        assertTrue("Käytäthän piirrustuksessa mustaa väriä (Color.BLACK).", blackSeen);
    }

}
