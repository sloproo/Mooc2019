package ristinolla;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
        RistinollaSovellus sovellus = new RistinollaSovellus();

        try {
            Application app = Application.class.cast(sovellus);
        } catch (Throwable t) {
            fail("Periihän luokka RistinollaSovellus luokan Application.");
        }

        try {
            Reflex.reflect(RistinollaSovellus.class).method("start").returningVoid().taking(Stage.class).invokeOn(sovellus, stage);
        } catch (Throwable ex) {
            fail("Onhan luokalla RistinollaSovellus metodi start, joka saa parametrina Stage-olion. Jos on, tarkista että metodi toimii. Virhe: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    @Points("12-13.1")
    public void osatKohdallaan() {
        Scene scene = stage.getScene();
        assertNotNull("Stage-oliolla pitäisi olla Scene-olio. Nyt start-metodin suorituksen jälkeen stagelle tehty kutsu getScene palautti null-viitteen.", scene);
        Parent elementtienJuuri = scene.getRoot();
        assertNotNull("Scene-oliolle tulee antaa parametrina käyttöliittymäkomponenttien asetteluun tarkoitettu olio (tässä BorderPane). Nyt Scene-oliolta ei löytynyt komponentteja sisältävää oliota.", elementtienJuuri);

        BorderPane asettelu = null;
        try {
            asettelu = BorderPane.class.cast(elementtienJuuri);
        } catch (Throwable t) {
            fail("Käytäthän käyttöliittymäkomponenttien asetteluun BorderPane-luokkaa.");
        }

        assertNotNull("Scene-oliolle tulee antaa parametrina käyttöliittymäkomponenttien asetteluun tarkoitettu BorderPane-olio.", asettelu);
        assertTrue("BorderPanen ylälaidassa pitäisi olla Label-olio. Nyt siellä oli: " + asettelu.getTop(), asettelu.getTop().getClass().isAssignableFrom(Label.class));
        assertTrue("BorderPanen keskellä pitäisi olla GridPane-olio. Nyt siellä oli: " + asettelu.getCenter(), asettelu.getCenter().getClass().isAssignableFrom(GridPane.class));

        GridPane ruudukko = null;
        try {
            ruudukko = GridPane.class.cast(asettelu.getCenter());
        } catch (Throwable t) {
            fail("Käytäthän BorderPanen keskellä GridPane-luokkaa.");
        }

        assertNotNull("Käytäthän BorderPanen keskellä GridPane-luokkaa. Nyt keskellä oli " + ruudukko, ruudukko);

        assertEquals("Odotettiin, että gridpanessa on yhdeksän nappia. Nyt elementtejä oli: " + ruudukko.getChildren().size(), 9, ruudukko.getChildren().size());

        for (Node node : ruudukko.getChildren()) {
            try {
                Button button = Button.class.cast(node);
            } catch (Throwable t) {
                fail("Odotettiin, että jokainen ruudukon olio on Button. Nyt ei ollut. Virhe: " + t.getMessage());
            }
        }

    }

    @Test
    @Points("12-13.2")
    public void vuoroMuuttuuPelatessa() {
        tarkistaVuoronMuutos(0, 1);
    }

    @Test
    @Points("12-13.2")
    public void vuoroMuuttuuPelatessa2() {
        tarkistaVuoronMuutos(5, 2);
    }

    @Test
    @Points("12-13.2")
    public void vuoroMuuttuuPelatessa3() {
        tarkistaVuoronMuutos(4, 7);
    }

    private void tarkistaVuoronMuutos(int eka, int toka) {
        Label tekstikentta = tekstikentta();
        assertEquals("Pelin alussa tekstikentässä pitäisi lukea teksti \"Vuoro: X\". Nyt luki: \"" + tekstikentta.getText() + "\".", "Vuoro: X", tekstikentta.getText());
        List<Button> napit = napit();
        assertTrue("Pelissä pitäisi olla 9 nappia. Nyt nappeja oli " + napit.size(), napit.size() == 9);

        assertTrue("Kun peli alkaa, napeissa ei pitäisi olla tekstiä. Nyt löytyi teksti: " + napit.get(eka).getText(), napit.get(eka).getText().trim().isEmpty());
        clickOn(napit.get(eka));
        assertTrue("Kun nappia klikataan ja on X:n vuoro, napin tekstiksi pitäisi tulla X. Nyt napin tekstiksi tuli: " + napit.get(eka).getText(), napit.get(eka).getText().trim().equals("X"));

        tekstikentta = tekstikentta();
        assertEquals("Kun X:n vuoro on pelattu, seuraavaksi pitäisi olla O:n vuoro. Tekstikentässä pitäisi lukea teksti \"Vuoro: O\". Nyt luki: \"" + tekstikentta.getText() + "\".", "Vuoro: O", tekstikentta.getText());
        clickOn(napit.get(toka));
        assertTrue("Kun nappia klikataan ja on O:n vuoro, napin tekstiksi pitäisi tulla O. Nyt tekstiksi tuli: " + napit.get(toka).getText(), napit.get(toka).getText().trim().equals("O"));
        tekstikentta = tekstikentta();
        assertEquals("Kun O:n vuoro on pelattu, seuraavaksi pitäisi olla X:n vuoro. Tekstikentässä pitäisi lukea teksti \"Vuoro: X\". Nyt luki: \"" + tekstikentta.getText() + "\".", "Vuoro: X", tekstikentta.getText());
    }

    @Test
    @Points("12-13.2")
    public void samaaKohtaaEiVoiPelataKahdesti() {
        tarkistaEtteiSamaaVuoroaVoiPelataKahdesti(0);
    }

    @Test
    @Points("12-13.2")
    public void samaaKohtaaEiVoiPelataKahdesti2() {
        tarkistaEtteiSamaaVuoroaVoiPelataKahdesti(5);
    }

    @Test
    @Points("12-13.2")
    public void samaaKohtaaEiVoiPelataKahdesti3() {
        tarkistaEtteiSamaaVuoroaVoiPelataKahdesti(8);
    }

    @Test
    @Points("12-13.3")
    public void pelinVoiVoittaaTaiHavita() {
        Label tekstikentta = tekstikentta();
        assertEquals("Pelin alussa tekstikentässä pitäisi lukea teksti \"Vuoro: X\". Nyt luki: \"" + tekstikentta.getText() + "\".", "Vuoro: X", tekstikentta.getText());
        List<Button> napit = napit();
        assertTrue("Pelissä pitäisi olla 9 nappia. Nyt nappeja oli " + napit.size(), napit.size() == 9);
        Collections.shuffle(napit);

        String vuoro = "X";
        for (int i = 0; i < napit.size(); i++) {
            clickOn(napit.get(i));
            assertTrue("Kun nappia klikataan ja on " + vuoro + ":n vuoro, napin tekstiksi pitäisi tulla " + vuoro + ". Nyt tekstiksi tuli: " + napit.get(i).getText(), napit.get(i).getText().trim().equals(vuoro));

            String edellinenVuoro = vuoro;
            vuoro = vuoro.equals("X") ? "O" : "X";
            tekstikentta = tekstikentta();
            if (tekstikentta.getText().equals("Loppu!")) {
                return;
            }

            if (i == 8) {
                fail("Kun peli on pelattu loppuun, tekstikentässä pitäisi olla teksti \"Loppu!\". Nyt teksti oli \"" + tekstikentta.getText() + "\".");
            }

            if (!tekstikentta.getText().toLowerCase().contains("vuoro")) {
                assertEquals("Varmista, että pelin loppuminen ilmaistaan tekstillä \"Loppu!\". Nyt teksti oli \"" + tekstikentta.getText() + "\".", "Loppu!", tekstikentta.getText());
            }

            assertEquals("Kun pelattiin " + edellinenVuoro + ":n vuoro, seuraavaksi vuorossa pitäisi olla " + vuoro + ". Nyt luki: \"" + tekstikentta.getText() + "\".", "Vuoro: " + vuoro, tekstikentta.getText());
        }

        tekstikentta = tekstikentta();
        if (!tekstikentta.getText().equals("Loppu!")) {
            fail("Kun peli loppuu, tekstikentässä pitäisi lukea \"Loppu!\". Nyt luki \"" + tekstikentta.getText() + "\"");
        }
    }

    private void tarkistaEtteiSamaaVuoroaVoiPelataKahdesti(int kohta) {
        Label tekstikentta = tekstikentta();
        assertEquals("Pelin alussa tekstikentässä pitäisi lukea teksti \"Vuoro: X\". Nyt luki: \"" + tekstikentta.getText() + "\".", "Vuoro: X", tekstikentta.getText());
        List<Button> napit = napit();
        assertTrue("Pelissä pitäisi olla 9 nappia. Nyt nappeja oli " + napit.size(), napit.size() == 9);

        assertTrue("Kun peli alkaa, napeissa ei pitäisi olla tekstiä. Nyt löytyi teksti: " + napit.get(kohta).getText(), napit.get(kohta).getText().trim().isEmpty());
        clickOn(napit.get(kohta));
        tekstikentta = tekstikentta();
        assertTrue("Kun nappia klikataan ja on X:n vuoro, napin tekstiksi pitäisi tulla X. Nyt tekstiksi tuli: " + napit.get(kohta).getText(), napit.get(kohta).getText().trim().equals("X"));

        assertEquals("Kun X:n vuoro on pelattu, seuraavaksi pitäisi olla O:n vuoro. Tekstikentässä pitäisi lukea teksti \"Vuoro: O\". Nyt luki: \"" + tekstikentta.getText() + "\".", "Vuoro: O", tekstikentta.getText());
        clickOn(napit.get(kohta));
        tekstikentta = tekstikentta();
        assertTrue("Kun jo pelattua nappia klikataan ja on O:n vuoro, napin tekstin ei pitäisi muuttua ja vuoron pitäisi olla yhä O:npitäisi tulla O. Nyt tekstiksi tuli: " + napit.get(kohta).getText(), napit.get(kohta).getText().trim().equals("X"));
        assertEquals("Kun O klikkaa varattua kohtaa, vuoron ei tule muuttua. Tekstikentässä tulee yhä lukea teksti \"Vuoro: O\". Nyt luki: \"" + tekstikentta.getText() + "\".", "Vuoro: O", tekstikentta.getText());

    }

    private Label tekstikentta() {
        Scene scene = stage.getScene();
        if (scene == null) {
            fail("Scene-oliota ei löytynyt Stage-olion sisältä.");
        }

        Parent elementtienJuuri = scene.getRoot();
        if (elementtienJuuri == null) {
            fail("Scene-olion konstruktorin parametriksi oli asetettu null-viite.");
        }

        BorderPane asettelu = null;
        try {
            asettelu = BorderPane.class.cast(elementtienJuuri);
        } catch (Throwable t) {
            fail("Käyttöliittymäkomponenttien asettelussa ei käytetty BorderPane-luokkaa.");
        }

        if (asettelu.getTop() == null || !asettelu.getTop().getClass().isAssignableFrom(Label.class)) {
            fail("Käyttöliittymässä olleen BorderPanen ylälaidasta ei löytynyt Label-oliota.");
        }

        return (Label) asettelu.getTop();
    }

    private List<Button> napit() {
        Scene scene = stage.getScene();
        if (scene == null) {
            fail("Scene-oliota ei löytynyt Stage-olion sisältä.");
        }

        Parent elementtienJuuri = scene.getRoot();
        if (elementtienJuuri == null) {
            fail("Scene-olion konstruktorin parametriksi oli asetettu null-viite.");
        }

        BorderPane asettelu = null;
        try {
            asettelu = BorderPane.class.cast(elementtienJuuri);
        } catch (Throwable t) {
            fail("Käyttöliittymäkomponenttien asettelussa ei käytetty BorderPane-luokkaa.");
        }

        if (asettelu.getTop() == null || !asettelu.getCenter().getClass().isAssignableFrom(GridPane.class)) {
            fail("Käyttöliittymässä olleen BorderPanen keskeltä ei löytynyt GridPane-oliota.");
        }

        GridPane ruudukko = (GridPane) asettelu.getCenter();
        assertEquals("Odotettiin, että gridpanessa on yhdeksän nappia. Nyt elementtejä oli: " + ruudukko.getChildren().size(), 9, ruudukko.getChildren().size());
        List<Button> napit = new ArrayList<>();

        for (Node node : ruudukko.getChildren()) {
            try {
                Button button = Button.class.cast(node);
                napit.add(button);
            } catch (Throwable t) {
                fail("Odotettiin, että jokainen ruudukon olio on Button. Nyt ei ollut. Virhe: " + t.getMessage());
            }
        }

        return napit;
    }
}
