package sovellus;

import java.util.Map;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PyorailijaTilastotSovellus extends Application {

    @Override
    public void start(Stage ikkuna) {

        PyorailijaTilasto tilasto = new PyorailijaTilasto("helsingin-pyorailijamaarat.csv");

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.add(new Label("Valitse tarkasteltava paikka"), 0, 0);

        ObservableList<String> data = FXCollections.observableArrayList();
        data.addAll(tilasto.paikat());

        ListView<String> lista = new ListView<>(data);
        gridPane.add(lista, 0, 1);

        CategoryAxis xAkseli = new CategoryAxis();
        NumberAxis yAkseli = new NumberAxis();
        xAkseli.setLabel("Vuosi / Kuukausi");
        yAkseli.setLabel("Pyöräilijöitä");

        
        BarChart<String, Number> kaavio = new BarChart<>(xAkseli, yAkseli);
        kaavio.setLegendVisible(false);

        lista.setOnMouseClicked((MouseEvent event) -> {
            String valittu = lista.getSelectionModel().getSelectedItem();
            Map<String, Integer> arvot = tilasto.pyorailijoitaKuukausittain(valittu);
            kaavio.getData().clear();
            XYChart.Series chartData = new XYChart.Series();

            arvot.keySet().stream().forEach(aika -> {
                chartData.getData().add(new XYChart.Data(aika, arvot.get(aika)));
            });

            kaavio.getData().add(chartData);
        });

        gridPane.add(kaavio, 1, 0, 1, 2);

        Scene nakyma = new Scene(gridPane);

        ikkuna.setScene(nakyma);
        ikkuna.show();
    }

    public static void main(String[] args) {
        launch(PyorailijaTilastotSovellus.class);
    }

}
