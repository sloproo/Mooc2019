package sovellus;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

@Points("13-05")
public class PyorailijaTilastoTest extends ApplicationTest {

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
        PyorailijaTilastotSovellus sovellus = new PyorailijaTilastotSovellus();

        try {
            Application app = Application.class.cast(sovellus);
        } catch (Throwable t) {
            fail("Periihän luokka PyorailijaTilastotSovellus luokan Application.");
        }

        try {
            Reflex.reflect(PyorailijaTilastotSovellus.class).method("start").returningVoid().taking(Stage.class).invokeOn(sovellus, stage);
        } catch (Throwable ex) {
            fail("Onhan luokalla PyorailijaTilastotSovellus metodi start, joka saa parametrina Stage-olion. Jos on, tarkista että metodi toimii. Virhe: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    public void eiLineChartia() throws Throwable {
        long riveja = Files.lines(Paths.get("src/main/java/sovellus/PyorailijaTilastotSovellus.java")).filter(l -> l.contains("LineChart")).count();
        assertEquals("Merkkijonon LineChart ei tule esiintyä tiedostossa PyorailijaTilastotSovellus.java. Nyt esiintymiä oli ainakin " + riveja, 0, riveja);
    }

    @Test
    public void vainBarChart() {
        Scene scene = stage.getScene();
        assertNotNull("Stage-oliolla pitäisi olla Scene-olio. Nyt napin painalluksen jälkeen stagelle tehty kutsu getScene palautti null-viitteen.", scene);
        Parent elementtienJuuri = scene.getRoot();
        assertNotNull("Toista näkymää vastaavalle Scene-oliolle tulee antaa parametrina käyttöliittymäkomponenttien asetteluun tarkoitettu olio (tässä GridPane). Nyt Scene-oliolta ei löytynyt komponentteja sisältävää oliota.", elementtienJuuri);

        GridPane asettelu = null;
        try {
            asettelu = GridPane.class.cast(elementtienJuuri);
        } catch (Throwable t) {
            fail("Käytäthän toisen näkymän käyttöliittymäkomponenttien asetteluun GridPane-luokkaa.");
        }

        assertNotNull("Toisesta näkymästä vastuussa olevalle Scene-oliolle tulee antaa parametrina käyttöliittymäkomponenttien asetteluun tarkoitettu GridPane-olio.", asettelu);

        Optional<Node> chartObject = asettelu.getChildren().stream().filter(child -> {
            try {
                BarChart.class.cast(child);
            } catch (Throwable t) {
                return false;
            }

            return true;
        }).findFirst();

        assertTrue("Vaihdoithan LineChart-olion BarChart-olioon. Nyt BarChart-oliota ei löytynyt GridPane-asettelusta.", chartObject.isPresent());
        BarChart kaavio = (BarChart) chartObject.get();
    }
}
