package sovellus;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

@Points("13-01")
public class ShanghaiSovellusTest extends ApplicationTest {

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
        ShanghaiSovellus sovellus = new ShanghaiSovellus();

        try {
            Application app = Application.class.cast(sovellus);
        } catch (Throwable t) {
            fail("Periihän luokka ShanghaiSovellus luokan Application.");
        }

        try {
            Reflex.reflect(ShanghaiSovellus.class).method("start").returningVoid().taking(Stage.class).invokeOn(sovellus, stage);
        } catch (Throwable ex) {
            fail("Onhan luokalla ShanghaiSovellus metodi start, joka saa parametrina Stage-olion. Jos on, tarkista että metodi toimii. Virhe: " + ex.getMessage());
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

        assertTrue("Luo x-akselia kuvaava NumberAxis-olio siten, että annat sille parametrina näkyvän arvon ajarajaa rajaavan arvon. Kun datan ensimmäinen piste on kohdassa 2007, kannattaa ensimmäinen näytettävä x-akselin kohta olla hieman ennen kyseistä kohtaa.", xAkseli.getLowerBound() > 2000 && xAkseli.getLowerBound() <= 2007);
        assertTrue("Luo x-akselia kuvaava NumberAxis-olio siten, että annat sille parametrina näkyvän arvon ylärajaa rajaavan arvon. Kun datan viimeinen piste on kohdassa 2016, kannattaa viimeinen näytettävä x-akselin kohdan olla hieman kyseisen kohdan jälkeen.", xAkseli.getUpperBound() >= 2016 && xAkseli.getUpperBound() <= 2025);

        assertEquals("Kaaviossa pitäisi olla yksi viiva. Nyt niitä oli " + kaavio.getData().size(), 1, kaavio.getData().size());

        XYChart.Series data = (XYChart.Series) kaavio.getData().get(0);

        List<XYChart.Data> pisteet = new ArrayList<>();
        for (int i = 0; i < data.getData().size(); i++) {
            Object piste = data.getData().get(i);
            try {
                pisteet.add(XYChart.Data.class.cast(piste));
            } catch (Throwable t) {
                fail("Käytäthän pisteiden kuvaamiseen XYChart.Data-luokkaa. Virhe: " + t.getMessage());
            }
        }

        assertTrue("Vuodelle 2007 ei löytynyt pistettä datasta. Lisäsithän dataan pisteen new XYChart.Data(2007, 73);", pisteet.stream().filter(p -> p.getXValue().equals(2007)).findFirst().isPresent());
        assertTrue("Vuoden 2007 piste oli väärä. Lisäsithän dataan pisteen new XYChart.Data(2007, 73);", pisteet.stream().filter(p -> p.getXValue().equals(2007)).findFirst().get().getYValue().equals(73));

        assertTrue("Vuodelle 2011 ei löytynyt pistettä datasta. Lisäsithän dataan pisteen new XYChart.Data(2011, 74);", pisteet.stream().filter(p -> p.getXValue().equals(2011)).findFirst().isPresent());
        assertTrue("Vuoden 2011 piste oli väärä. Lisäsithän dataan pisteen new XYChart.Data(2011, 74);", pisteet.stream().filter(p -> p.getXValue().equals(2011)).findFirst().get().getYValue().equals(74));

        assertTrue("Vuodelle 2016 ei löytynyt pistettä datasta. Lisäsithän dataan pisteen new XYChart.Data(2011, 56);", pisteet.stream().filter(p -> p.getXValue().equals(2016)).findFirst().isPresent());
        assertTrue("Vuoden 2016 piste oli väärä. Lisäsithän dataan pisteen new XYChart.Data(2011, 56);", pisteet.stream().filter(p -> p.getXValue().equals(2016)).findFirst().get().getYValue().equals(56));

    }

}
