package nappijatekstielementti;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.testfx.framework.junit.ApplicationTest;

@Points("12-02")
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
        NappiJaTekstielementtiSovellus sovellus = new NappiJaTekstielementtiSovellus();

        try {
            Application app = Application.class.cast(sovellus);
        } catch (Throwable t) {
            fail("Periihän luokka NappiJaTekstielementtiSovellus luokan Application.");
        }

        try {
            Reflex.reflect(NappiJaTekstielementtiSovellus.class).method("start").returningVoid().taking(Stage.class).invokeOn(sovellus, stage);
        } catch (Throwable ex) {
            fail("Onhan luokalla NappiJaTekstielementtiSovellus metodi start, joka saa parametrina Stage-olion. Jos on, tarkista että metodi toimii. Virhe: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    public void loytyyHalututElementit() {
        Scene scene = stage.getScene();
        assertNotNull("Stage-oliolla pitäisi olla Scene-olio. Nyt start-metodin suorituksen jälkeen stagelle tehty kutsu getScene palautti null-viitteen.", scene);
        Parent elementtienJuuri = scene.getRoot();
        assertNotNull("Scene-oliolle tulee antaa parametrina käyttöliittymäkomponenttien asetteluun tarkoitettu olio (esim. FlowPane-olio). Nyt Scene-oliolta ei löytynyt komponentteja sisältävää oliota.", elementtienJuuri);
        List<Node> children = new ArrayList(elementtienJuuri.getChildrenUnmodifiable());
        boolean labelLoytyi = false;
        boolean buttonLoytyi = false;

        while (!children.isEmpty()) {
            Node node = children.get(0);
            if (node instanceof Label) {
                labelLoytyi = true;
            }

            if (node instanceof Button) {
                buttonLoytyi = true;
            }

            if (node instanceof Parent) {
                Parent p = (Parent) node;
                children.addAll(p.getChildrenUnmodifiable());
            }

            children.remove(node);
        }

        assertTrue("Scene-oliosta ei löytynyt Label-elementtiä.", labelLoytyi);
        assertTrue("Scene-oliosta ei löytynyt Button-elementtiä.", buttonLoytyi);

    }

}
