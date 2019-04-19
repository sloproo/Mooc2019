package sovellus;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

@Points("13-02")
public class PuolueetSovellusTest extends ApplicationTest {

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
        PuolueetSovellus sovellus = new PuolueetSovellus();

        try {
            Application app = Application.class.cast(sovellus);
        } catch (Throwable t) {
            fail("Periihän luokka PuolueetSovellus luokan Application.");
        }

        try {
            Reflex.reflect(PuolueetSovellus.class).method("start").returningVoid().taking(Stage.class).invokeOn(sovellus, stage);
        } catch (Throwable ex) {
            fail("Onhan luokalla PuolueetSovellus metodi start, joka saa parametrina Stage-olion. Jos on, tarkista että metodi toimii. Virhe: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    public void kaavioTest() {
        Scene scene = stage.getScene();
        assertNotNull("Stage-oliolla pitäisi olla Scene-olio. Nyt start-metodin suorituksen jälkeen stagelle tehty kutsu getScene palautti null-viitteen.", scene);
        Parent elementtienJuuri = scene.getRoot();
        assertNotNull("Ensimmäistä näkymää vastaavalle Scene-oliolle tulee antaa parametrina kaavio. Nyt Scene-oliolta ei löytynyt kaaviota tai muuta komponentteja sisältävää oliota.", elementtienJuuri);

        LineChart kaavio = null;
        try {
            kaavio = LineChart.class.cast(elementtienJuuri);
        } catch (Throwable t) {
            fail("Käytäthän kaaviona LineChart-luokkaa.");
        }

        assertNotNull("Scene-oliolle tulee antaa konstruktorin parametrina LineChart-olio.", kaavio);

        NumberAxis xAkseli = null;
        try {
            xAkseli = NumberAxis.class.cast(kaavio.getXAxis());
        } catch (Throwable t) {
            fail("Käytäthän kaavion x-akselin luomiseen NumberAxis-luokkaa.");
        }

        assertTrue("Luo x-akselia kuvaava NumberAxis-olio siten, että annat sille parametrina näkyvän arvon ajarajaa rajaavan arvon. Kun datan ensimmäinen piste on kohdassa 1968, kannattaa ensimmäinen näytettävä x-akselin kohta olla hieman ennen kyseistä kohtaa.", xAkseli.getLowerBound() > 1960 && xAkseli.getLowerBound() <= 1968);
        assertTrue("Luo x-akselia kuvaava NumberAxis-olio siten, että annat sille parametrina näkyvän arvon ylärajaa rajaavan arvon. Kun datan viimeinen piste on kohdassa 2008, kannattaa viimeinen näytettävä x-akselin kohdan olla hieman kyseisen kohdan jälkeen.", xAkseli.getUpperBound() >= 2008 && xAkseli.getUpperBound() <= 2020);

        assertEquals("Kaaviossa pitäisi olla seitsemän viivaa. Nyt niitä oli " + kaavio.getData().size(), 7, kaavio.getData().size());

        Map<String, XYChart.Series> datat = new TreeMap<>();
        for (int i = 0; i < kaavio.getData().size(); i++) {
            XYChart.Series data = (XYChart.Series) kaavio.getData().get(i);

            assertNotNull("Annathan jokaiselle viivalle puolueen nimen. Nyt löytyi XYChart.Series-olio, jonka nimi on null.", data.getName());
            datat.put(data.getName(), data);
        }

        List<String> puolueet = new ArrayList<>(Arrays.asList("KOK", "SDP", "KESK", "VIHR", "VAS", "PS", "RKP"));
        puolueet.removeAll(datat.keySet());
        assertTrue("Kaaviosta puuttui puolue: " + puolueet.toString().replace("[", "").replace("]", ""), puolueet.isEmpty());

        Map<String, Map<Integer, Double>> odotetutPisteet = new TreeMap<>();
        odotetutPisteet.put("KOK", new TreeMap<>());
        odotetutPisteet.get("KOK").put(1972, 18.1);
        odotetutPisteet.get("KOK").put(1992, 19.1);

        odotetutPisteet.put("VAS", new TreeMap<>());
        odotetutPisteet.get("VAS").put(1972, 17.5);
        odotetutPisteet.get("VAS").put(1992, 11.7);

        odotetutPisteet.put("RKP", new TreeMap<>());
        odotetutPisteet.get("RKP").put(1968, 5.6);
        odotetutPisteet.get("RKP").put(2008, 4.7);

        for (String puolue : odotetutPisteet.keySet()) {
            assertTrue("Datasta ei löytynyt puoluetta " + puolue, datat.containsKey(puolue));

            XYChart.Series data = datat.get(puolue);
            List<Data> datapisteet = new ArrayList<>();
            data.getData().stream().forEach(d -> datapisteet.add(XYChart.Data.class.cast(d)));

            for (Map.Entry<Integer, Double> entry : odotetutPisteet.get(puolue).entrySet()) {

                Optional<Data> optional = datapisteet.stream().filter(p -> p.getXValue().equals(entry.getKey())).findFirst();
                assertTrue("Vuodelle " + entry.getKey() + " ei löytynyt pistettä puolueen " + puolue + " datasta. Lisäsithän dataan pisteen new XYChart.Data(" + entry.getKey() + ", " + entry.getValue() + ");", optional.isPresent());
                assertEquals("Vuoden " + entry.getKey() + " piste oli väärä puolueelle " + puolue + ". Lisäsithän dataan pisteen new XYChart.Data(" + entry.getKey() + ", " + entry.getValue() + ");", entry.getValue().doubleValue(), (double) optional.get().getYValue(), 0.1);
            }
        }

    }

}
