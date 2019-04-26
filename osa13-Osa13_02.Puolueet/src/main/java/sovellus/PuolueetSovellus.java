package sovellus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class PuolueetSovellus extends Application {

    public static void main(String[] args) {
        launch(PuolueetSovellus.class);
    }
    
    @Override
    public void start(Stage ikkuna) {
        NumberAxis xAkseli = new NumberAxis(1965, 2015, 5);
        NumberAxis yAkseli = new NumberAxis();
        
        LineChart<Number, Number> viivakaavio = new LineChart<>(xAkseli, yAkseli);
        
        ArrayList<String> data = new ArrayList<>();
        try (Scanner tiedostonlukija = new Scanner(new File("puoluedata.tsv"))) {
            while (tiedostonlukija.hasNextLine()) {
                data.add(tiedostonlukija.nextLine());
            }
            
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
        }
        
        HashMap<String, HashMap<Integer, Double>> puolueet = new HashMap<>();
        String[] puolueVuodet = data.get(0).split("\t");
        
        for (int i = 1; i < data.size(); i++) {
            String[] riviPaloina = data.get(i).split("\t");
            HashMap<Integer, Double> puolueenSuosio = new HashMap<>();
            for (int j = 1; j < puolueVuodet.length; j++) {
                if (!(riviPaloina[j].equals("-"))) {
                    puolueenSuosio.put(Integer.valueOf(puolueVuodet[j]), Double.valueOf(riviPaloina[j]));
                }
            }
            puolueet.put(riviPaloina[0], puolueenSuosio);
        }
        
        puolueet.keySet().stream().forEach(puolue -> {
            XYChart.Series xyData = new XYChart.Series();
            xyData.setName(puolue);
            puolueet.get(puolue).entrySet().stream().forEach(pari -> {
                xyData.getData().add(new XYChart.Data(pari.getKey(), pari.getValue()));
            });
            viivakaavio.getData().add(xyData);
        });
        
        Scene nakyma = new Scene(viivakaavio);
        ikkuna.setScene(nakyma);
        ikkuna.show();
        
    }

}
