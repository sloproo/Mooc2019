package sovellus;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class EpareiluaMainontaaSovellus extends Application {

    @Override
    public void start(Stage ikkuna) {
        CategoryAxis xAkseli = new CategoryAxis();
        NumberAxis yAkseli = new NumberAxis(0.0, 77.5, 5.0);
        yAkseli.setTickLabelsVisible(true);
        yAkseli.setLabel("Suurempi parempi!");

        BarChart<String, Number> pylvaskaavio = new BarChart<>(xAkseli, yAkseli);

        pylvaskaavio.setTitle("Liittymän nopeus");
        pylvaskaavio.setLegendVisible(true);

        XYChart.Series asukasluvut = new XYChart.Series();
        asukasluvut.getData().add(new XYChart.Data("NDA", 77.4));
        asukasluvut.getData().add(new XYChart.Data("Tomera", 77.2));
        asukasluvut.getData().add(new XYChart.Data("Liisa", 77.1));
        asukasluvut.getData().add(new XYChart.Data("Ratiolinja", 77.1));

        pylvaskaavio.getData().add(asukasluvut);
        Scene nakyma = new Scene(pylvaskaavio, 400, 300);
        ikkuna.setScene(nakyma);
        ikkuna.show();
    }

    public static void main(String[] args) {
        launch(EpareiluaMainontaaSovellus.class);
    }

}
