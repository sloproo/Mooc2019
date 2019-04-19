package sovellus;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class SaastolaskuriTest extends ApplicationTest {

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
        SaastolaskuriSovellus sovellus = new SaastolaskuriSovellus();

        try {
            Application app = Application.class.cast(sovellus);
        } catch (Throwable t) {
            fail("Periihän luokka SaastolaskuriSovellus luokan Application.");
        }

        try {
            Reflex.reflect(SaastolaskuriSovellus.class).method("start").returningVoid().taking(Stage.class).invokeOn(sovellus, stage);
        } catch (Throwable ex) {
            fail("Onhan luokalla SaastolaskuriSovellus metodi start, joka saa parametrina Stage-olion. Jos on, tarkista että metodi toimii. Virhe: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    @Points("13-03.1")
    public void kayttoliittymanPaaAsettelunaBorderPaneTest() {
        Scene scene = stage.getScene();
        assertNotNull("Stage-oliolla pitäisi olla Scene-olio. Nyt start-metodin suorituksen jälkeen stagelle tehty kutsu getScene palautti null-viitteen.", scene);
        Parent elementtienJuuri = scene.getRoot();

        assertNotNull("Ensimmäistä näkymää vastaavalle Scene-oliolle tulee antaa BorderPane-asettelu. Nyt Scene-oliolta ei löytynyt BorderPanea tai muuta komponentteja sisältävää oliota.", elementtienJuuri);

        BorderPane asettelu = null;
        try {
            asettelu = BorderPane.class.cast(elementtienJuuri);
        } catch (Throwable t) {
            fail("Käytäthän Scene-oliolle annettuna asetteluna BorderPane-luokan ilmentymää.");
        }

        assertNotNull("Scene-oliolle tulee antaa konstruktorin parametrina BorderPane-luokan ilmentymä.", asettelu);
    }

    @Test
    @Points("13-03.1")
    public void kayttoliittymanViivakaavioTest() {
        Scene scene = stage.getScene();
        assertNotNull("Stage-oliolla pitäisi olla Scene-olio. Nyt start-metodin suorituksen jälkeen stagelle tehty kutsu getScene palautti null-viitteen.", scene);
        Parent elementtienJuuri = scene.getRoot();

        assertNotNull("Ensimmäistä näkymää vastaavalle Scene-oliolle tulee antaa BorderPane-asettelu. Nyt Scene-oliolta ei löytynyt BorderPanea tai muuta komponentteja sisältävää oliota.", elementtienJuuri);

        BorderPane asettelu = null;
        try {
            asettelu = BorderPane.class.cast(elementtienJuuri);
        } catch (Throwable t) {
            fail("Käytäthän Scene-oliolle annettuna asetteluna BorderPane-luokan ilmentymää.");
        }

        assertNotNull("Scene-oliolle tulee antaa konstruktorin parametrina BorderPane-luokan ilmentymä.", asettelu);

        // viivakaavio keskellä
        assertNotNull("BorderPane-asettelun keskellä tulee olla viivakaavio.", asettelu.getCenter());

        LineChart kaavio = null;
        try {
            kaavio = LineChart.class.cast(asettelu.getCenter());
        } catch (Throwable t) {
            fail("Käytäthän BorderPane-asettelun keskelle asetettavana kaaviona LineChart-luokan ilmentymää.");
        }

        assertNotNull("BorderPane-asettelun keskelle tulee asettaa LineChart-olio.", kaavio);

        NumberAxis xAkseli = null;
        try {
            xAkseli = NumberAxis.class.cast(kaavio.getXAxis());
        } catch (Throwable t) {
            fail("Käytäthän kaavion x-akselin luomiseen NumberAxis-luokkaa.");
        }

        assertNotNull("Käytäthän kaavion x-akselin luomiseen NumberAxis-luokkaa.", xAkseli);

        NumberAxis yAkseli = null;
        try {
            yAkseli = NumberAxis.class.cast(kaavio.getYAxis());
        } catch (Throwable t) {
            fail("Käytäthän kaavion y-akselin luomiseen NumberAxis-luokkaa.");
        }

        assertNotNull("Käytäthän kaavion y-akselin luomiseen NumberAxis-luokkaa.", yAkseli);
    }

    @Test
    @Points("13-03.1")
    public void kayttoliittymanYlaosaTest() {
        Scene scene = stage.getScene();
        assertNotNull("Stage-oliolla pitäisi olla Scene-olio. Nyt start-metodin suorituksen jälkeen stagelle tehty kutsu getScene palautti null-viitteen.", scene);
        Parent elementtienJuuri = scene.getRoot();

        assertNotNull("Ensimmäistä näkymää vastaavalle Scene-oliolle tulee antaa BorderPane-asettelu. Nyt Scene-oliolta ei löytynyt BorderPanea tai muuta komponentteja sisältävää oliota.", elementtienJuuri);

        BorderPane asettelu = null;
        try {
            asettelu = BorderPane.class.cast(elementtienJuuri);
        } catch (Throwable t) {
            fail("Käytäthän Scene-oliolle annettuna asetteluna BorderPane-luokan ilmentymää.");
        }

        assertNotNull("Scene-oliolle tulee antaa konstruktorin parametrina BorderPane-luokan ilmentymä.", asettelu);

        // viivakaavio keskellä
        assertNotNull("BorderPane-asettelun ylälaidassa tulee olla VBox-olio.", asettelu.getTop());

        VBox ylalaita = null;
        try {
            ylalaita = VBox.class.cast(asettelu.getTop());
        } catch (Throwable t) {
            fail("BorderPane-asettelun ylälaidassa tulee olla VBox-olio.");
        }

        assertNotNull("BorderPane-asettelun ylälaidassa tulee olla VBox-olio.", ylalaita);

        assertTrue("BorderPane-asettelun ylälaidassa olevan VBox-olion tulee sisältää kaksi BorderPane-asettelua.", ylalaita.getChildren().size() == 2);

        for (Node node : ylalaita.getChildren()) {
            BorderPane pane = null;
            try {
                pane = BorderPane.class.cast(node);
            } catch (Throwable t) {
                fail("BorderPane-asettelun ylälaidassa olevan VBox-olion tulee sisältää kaksi BorderPane-asettelua.");
            }

            assertNotNull("BorderPane-asettelun ylälaidassa olevan VBox-olion tulee sisältää kaksi BorderPane-asettelua.", pane);
        }

        BorderPane tallennus = (BorderPane) ylalaita.getChildren().get(0);

        assertNotNull("Ylälaidan ylemmän BorderPane-asettelun vasemmalla laidalla tulee tekstielementti (Label).", tallennus.getLeft());
        assertNotNull("Ylälaidan ylemmän BorderPane-asettelun oikealla laidalla tulee tekstielementti (Label).", tallennus.getRight());

        assertTrue("Ylälaidan ylemmän BorderPane-asettelun vasemmalla laidalla tulee tekstielementti (Label).", tallennus.getLeft().getClass().isAssignableFrom(Label.class));
        assertTrue("Ylälaidan ylemmän BorderPane-asettelun oikealla laidalla tulee tekstielementti (Label).", tallennus.getRight().getClass().isAssignableFrom(Label.class));

        assertNotNull("Ylälaidan ylemmän BorderPane-asettelun keskellä tulee olla liukurielementti (Slider).", tallennus.getCenter());
        assertTrue("Ylälaidan ylemmän BorderPane-asettelun keskellä tulee olla liukurielementti (Slider).", tallennus.getCenter().getClass().isAssignableFrom(Slider.class));

        assertEquals("Ylälaidan ylemmän BorderPane-asettelun vasemmalla olevan tekstin tulee olla \"Kuukausittainen tallennus\".", "Kuukausittainen tallennus", ((Label) tallennus.getLeft()).getText());

        BorderPane korko = (BorderPane) ylalaita.getChildren().get(1);
        assertNotNull("Ylälaidan alemman BorderPane-asettelun vasemmalla laidalla tulee tekstielementti (Label).", korko.getLeft());
        assertNotNull("Ylälaidan alemman BorderPane-asettelun oikealla laidalla tulee tekstielementti (Label).", korko.getRight());

        assertTrue("Ylälaidan alemman BorderPane-asettelun vasemmalla laidalla tulee tekstielementti (Label).", korko.getLeft().getClass().isAssignableFrom(Label.class));
        assertTrue("Ylälaidan alemman BorderPane-asettelun oikealla laidalla tulee tekstielementti (Label).", korko.getRight().getClass().isAssignableFrom(Label.class));

        assertNotNull("Ylälaidan alemman BorderPane-asettelun keskellä tulee olla liukurielementti (Slider).", korko.getCenter());
        assertTrue("Ylälaidan alemman BorderPane-asettelun keskellä tulee olla liukurielementti (Slider).", korko.getCenter().getClass().isAssignableFrom(Slider.class));

        assertEquals("Ylälaidan alemman BorderPane-asettelun vasemmalla olevan tekstin tulee olla \"Kuukausittainen tallennus\".", "Vuosittainen korko", ((Label) korko.getLeft()).getText());

        Slider tallennusLiukuri = (Slider) tallennus.getCenter();
        assertEquals("Asetathan tallennusta kuvaavan liukurin minimiarvoksi 25. Nyt minimiarvo oli " + tallennusLiukuri.getMin(), 25, tallennusLiukuri.getMin(), 0.001);
        assertEquals("Asetathan tallennusta kuvaavan liukurin maksimiarvoksi 250. Nyt minimiarvo oli " + tallennusLiukuri.getMax(), 250, tallennusLiukuri.getMax(), 0.001);

        Slider korkoLiukuri = (Slider) korko.getCenter();
        assertEquals("Asetathan korkoa kuvaavan liukurin minimiarvoksi 0. Nyt minimiarvo oli " + korkoLiukuri.getMin(), 0, korkoLiukuri.getMin(), 0.001);
        assertEquals("Asetathan korkoa kuvaavan liukurin maksimiarvoksi 10. Nyt minimiarvo oli " + korkoLiukuri.getMax(), 10, korkoLiukuri.getMax(), 0.001);
    }

    @Test
    @Points("13-03.2")
    public void tallennuksenKertymanLaskeminenMinimiarvolla() {
        Slider tallennusSlider = sliderit().get(0);

        siirraMinimiin(tallennusSlider, 25);

        sleep(100);

        tarkasteleKaavionViivoja("Kertymän näyttö ei ole oikein kun tallennuksen kertymistä tarkastellaan kuukausittaisella minimitallennuksella (25 / kk).", tallennusMinimi());
    }

    @Test
    @Points("13-03.2")
    public void tallennuksenKertymanLaskeminenMaksimiarvolla() {
        Slider tallennusSlider = sliderit().get(0);

        siirraMaksimiin(tallennusSlider, 250);

        sleep(100);

        tarkasteleKaavionViivoja("Kertymän näyttö ei ole oikein kun tallennuksen kertymistä tarkastellaan kuukausittaisella maksimitallennuksella (250 / kk).", tallennusMaksimi());
    }

    @Test
    @Points("13-03.3")
    public void koronLaskeminenMinimitalletuksellaJaMaksimiKorolla() {
        Slider tallennusSlider = sliderit().get(0);

        siirraMinimiin(tallennusSlider, 25);

        Slider korkoSlider = sliderit().get(1);

        siirraMaksimiin(korkoSlider, 10);

        tarkasteleKaavionViivoja("Koron sisältävän kertymän näyttö ei ole oikein kun tallennuksen kertymistä tarkastellaan minimitallennuksella ja maksimikorolla.", tallennusMinimiKorkoMaksimi());
    }

    @Test
    @Points("13-03.3")
    public void koronLaskeminenMaksimitalletuksellaJaMaksimiKorolla() {
        Slider tallennusSlider = sliderit().get(0);

        siirraMaksimiin(tallennusSlider, 250);

        Slider korkoSlider = sliderit().get(1);

        siirraMaksimiin(korkoSlider, 10);

        tarkasteleKaavionViivoja("Koron sisältävän kertymän näyttö ei ole oikein kun tallennuksen kertymistä tarkastellaan maksimitalletuksella ja maksimikorolla.", tallennusMaksimiKorkoMaksimi());
    }

    private void siirraMaksimiin(Slider slider, int maksimi) {

        clickOn(slider.getChildrenUnmodifiable().get(2)).type(KeyCode.RIGHT, 4);

        Bounds b = slider.getBoundsInLocal();
        b = slider.localToScreen(b);

        moveTo(new Point2D(b.getMinX(), (b.getMinY() + b.getMaxY()) / 2));
        press(MouseButton.PRIMARY);
        drag(b.getMaxX(), (b.getMinY() + b.getMaxY()) / 2, MouseButton.PRIMARY);
        release(MouseButton.PRIMARY);

        clickOn(slider.getChildrenUnmodifiable().get(2)).type(KeyCode.RIGHT, 4);

        while (slider.getValue() < maksimi) {
            drag(slider.getChildrenUnmodifiable().get(2)).dropBy(5, 0);
        }

    }

    private void siirraMinimiin(Slider slider, int minimi) {

        clickOn(slider.getChildrenUnmodifiable().get(2)).type(KeyCode.LEFT, 4);

        Bounds b = slider.getBoundsInLocal();
        b = slider.localToScreen(b);

        moveTo(new Point2D(b.getMaxX(), (b.getMinY() + b.getMaxY()) / 2));
        press(MouseButton.PRIMARY);
        drag(b.getMinX(), (b.getMinY() + b.getMaxY()) / 2, MouseButton.PRIMARY);
        release(MouseButton.PRIMARY);

        clickOn(slider.getChildrenUnmodifiable().get(2)).type(KeyCode.LEFT, 4);

        while (slider.getValue() > minimi) {
            drag(slider.getChildrenUnmodifiable().get(2)).dropBy(-5, 0);
        }

    }

    private void tarkasteleKaavionViivoja(String viesti, String data) {

        LineChart kaavio = viivakaavio();

        List<Map<Integer, Double>> kaavionViivojenArvot = haeArvotKaaviosta(kaavio);

        boolean kaikkiLoytyi = true;

        for (Map<Integer, Double> map : kaavionViivojenArvot) {
            kaikkiLoytyi = true;

            for (String rivi : data.split("\n")) {
                String[] palat = rivi.split(", ");

                int x = Integer.parseInt(palat[0]);
                double y = Double.parseDouble(palat[1]);

                if (map.containsKey(x) && map.get(x) != null && Math.abs(map.get(x) - y) < 0.001) {
                    continue;
                }

                kaikkiLoytyi = false;
                break;
            }

            if (kaikkiLoytyi) {
                break;
            }
        }

        assertTrue(viesti, kaikkiLoytyi);
    }

    private List<Map<Integer, Double>> haeArvotKaaviosta(LineChart kaavio) {
        List<Map<Integer, Double>> kaavionViivojenArvot = new ArrayList<>();

        for (int i = 0; i < kaavio.getData().size(); i++) {

            XYChart.Series data = (XYChart.Series) kaavio.getData().get(i);
            List<XYChart.Data> datapisteet = new ArrayList<>();
            data.getData().stream().forEach(d -> datapisteet.add(XYChart.Data.class.cast(d)));

            Map<Integer, Double> viivanArvot = new HashMap<>();
            for (XYChart.Data piste : datapisteet) {
                int x = (int) piste.getXValue();
                double y = 0;

                try {
                    y = (double) piste.getYValue();
                } catch (Throwable t) {
                    try {
                        y = (int) piste.getYValue();
                    } catch (Throwable t2) {
                    }
                }

                viivanArvot.put(x, y);
            }

            kaavionViivojenArvot.add(viivanArvot);
        }

        return kaavionViivojenArvot;
    }

    private List<Slider> sliderit() {
        kayttoliittymanYlaosaTest();

        Scene scene = stage.getScene();
        Parent elementtienJuuri = scene.getRoot();

        BorderPane asettelu = null;
        try {
            asettelu = BorderPane.class.cast(elementtienJuuri);
        } catch (Throwable t) {
            fail("Käytäthän Scene-oliolle annettuna asetteluna BorderPane-luokan ilmentymää.");
        }

        VBox ylalaita = null;
        try {
            ylalaita = VBox.class.cast(asettelu.getTop());
        } catch (Throwable t) {
            fail("BorderPane-asettelun ylälaidassa tulee olla VBox-olio.");
        }

        BorderPane tallennus = (BorderPane) ylalaita.getChildren().get(0);
        BorderPane korko = (BorderPane) ylalaita.getChildren().get(1);

        List<Slider> sliderit = new ArrayList<>();

        Slider tallennusLiukuri = (Slider) tallennus.getCenter();
        Slider korkoLiukuri = (Slider) korko.getCenter();

        sliderit.add(tallennusLiukuri);
        sliderit.add(korkoLiukuri);

        return sliderit;
    }

    private LineChart viivakaavio() {
        Scene scene = stage.getScene();
        Parent elementtienJuuri = scene.getRoot();

        BorderPane asettelu = null;
        try {
            asettelu = BorderPane.class.cast(elementtienJuuri);
        } catch (Throwable t) {
            fail("Käytäthän Scene-oliolle annettuna asetteluna BorderPane-luokan ilmentymää.");
        }

        assertNotNull("Scene-oliolle tulee antaa konstruktorin parametrina BorderPane-luokan ilmentymä.", asettelu);

        // viivakaavio keskellä
        assertNotNull("BorderPane-asettelun keskellä tulee olla viivakaavio.", asettelu.getCenter());

        LineChart kaavio = null;
        try {
            kaavio = LineChart.class.cast(asettelu.getCenter());
        } catch (Throwable t) {
            fail("Käytäthän BorderPane-asettelun keskelle asetettavana kaaviona LineChart-luokan ilmentymää.");
        }

        return kaavio;
    }

    private String tallennusMinimi() {
        return "0, 0\n"
                + "1, 300.0\n"
                + "2, 600.0\n"
                + "3, 900.0\n"
                + "4, 1200.0\n"
                + "5, 1500.0\n"
                + "6, 1800.0\n"
                + "7, 2100.0\n"
                + "8, 2400.0\n"
                + "9, 2700.0\n"
                + "10, 3000.0\n"
                + "11, 3300.0\n"
                + "12, 3600.0\n"
                + "13, 3900.0\n"
                + "14, 4200.0\n"
                + "15, 4500.0\n"
                + "16, 4800.0\n"
                + "17, 5100.0\n"
                + "18, 5400.0\n"
                + "19, 5700.0\n"
                + "20, 6000.0\n"
                + "21, 6300.0\n"
                + "22, 6600.0\n"
                + "23, 6900.0\n"
                + "24, 7200.0\n"
                + "25, 7500.0\n"
                + "26, 7800.0\n"
                + "27, 8100.0\n"
                + "28, 8400.0\n"
                + "29, 8700.0\n"
                + "30, 9000.0";
    }

    private String tallennusMaksimi() {
        return "0, 0\n"
                + "1, 3000.0\n"
                + "2, 6000.0\n"
                + "3, 9000.0\n"
                + "4, 12000.0\n"
                + "5, 15000.0\n"
                + "6, 18000.0\n"
                + "7, 21000.0\n"
                + "8, 24000.0\n"
                + "9, 27000.0\n"
                + "10, 30000.0\n"
                + "11, 33000.0\n"
                + "12, 36000.0\n"
                + "13, 39000.0\n"
                + "14, 42000.0\n"
                + "15, 45000.0\n"
                + "16, 48000.0\n"
                + "17, 51000.0\n"
                + "18, 54000.0\n"
                + "19, 57000.0\n"
                + "20, 60000.0\n"
                + "21, 63000.0\n"
                + "22, 66000.0\n"
                + "23, 69000.0\n"
                + "24, 72000.0\n"
                + "25, 75000.0\n"
                + "26, 78000.0\n"
                + "27, 81000.0\n"
                + "28, 84000.0\n"
                + "29, 87000.0\n"
                + "30, 90000.0";
    }

    private String tallennusMinimiKorkoMaksimi() {
        return "0, 0.0\n"
                + "1, 330.0\n"
                + "2, 693.0\n"
                + "3, 1092.3000000000002\n"
                + "4, 1531.5300000000004\n"
                + "5, 2014.6830000000007\n"
                + "6, 2546.1513000000014\n"
                + "7, 3130.766430000002\n"
                + "8, 3773.8430730000023\n"
                + "9, 4481.227380300003\n"
                + "10, 5259.350118330003\n"
                + "11, 6115.285130163004\n"
                + "12, 7056.8136431793055\n"
                + "13, 8092.495007497237\n"
                + "14, 9231.74450824696\n"
                + "15, 10484.918959071658\n"
                + "16, 11863.410854978825\n"
                + "17, 13379.751940476708\n"
                + "18, 15047.72713452438\n"
                + "19, 16882.499847976822\n"
                + "20, 18900.749832774505\n"
                + "21, 21120.824816051958\n"
                + "22, 23562.907297657155\n"
                + "23, 26249.198027422874\n"
                + "24, 29204.117830165163\n"
                + "25, 32454.529613181683\n"
                + "26, 36029.98257449985\n"
                + "27, 39962.98083194984\n"
                + "28, 44289.27891514483\n"
                + "29, 49048.20680665932\n"
                + "30, 54283.02748732526";
    }

    private String tallennusMaksimiKorkoMaksimi() {
        return "0, 0.0\n"
                + "1, 3300.0000000000005\n"
                + "2, 6930.000000000001\n"
                + "3, 10923.0\n"
                + "4, 15315.300000000001\n"
                + "5, 20146.830000000005\n"
                + "6, 25461.513000000006\n"
                + "7, 31307.664300000008\n"
                + "8, 37738.43073000001\n"
                + "9, 44812.27380300001\n"
                + "10, 52593.50118330002\n"
                + "11, 61152.85130163002\n"
                + "12, 70568.13643179303\n"
                + "13, 80924.95007497234\n"
                + "14, 92317.44508246958\n"
                + "15, 104849.18959071655\n"
                + "16, 118634.10854978822\n"
                + "17, 133797.51940476705\n"
                + "18, 150477.27134524376\n"
                + "19, 168824.99847976814\n"
                + "20, 189007.49832774498\n"
                + "21, 211208.2481605195\n"
                + "22, 235629.07297657145\n"
                + "23, 262491.9802742286\n"
                + "24, 292041.1783016515\n"
                + "25, 324545.2961318167\n"
                + "26, 360299.8257449984\n"
                + "27, 399629.80831949826\n"
                + "28, 442892.7891514481\n"
                + "29, 490482.06806659297\n"
                + "30, 542830.2748732523";
    }
}
