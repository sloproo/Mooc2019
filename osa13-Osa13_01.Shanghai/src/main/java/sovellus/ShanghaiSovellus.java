package sovellus;

import java.io.File;
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.chart.NumberAxis;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;



public class ShanghaiSovellus extends Application {

    public static void main(String[] args) {
        launch(ShanghaiSovellus.class);
    }
    
    @Override
    public void start(Stage ikkuna) {
        NumberAxis xAkseli = new NumberAxis(2005, 2020, 1);
        NumberAxis yAkseli = new NumberAxis();
        
        xAkseli.setLabel("Vuosi");
        yAkseli.setLabel("Sijoitus");
        
        LineChart<Number, Number> viivakaavio = new LineChart<>(xAkseli, yAkseli);
        
        /*String data = "";
        try (Scanner tiedostonlukija = new Scanner(new File("shanghai_HY.txt"))) {
            while (tiedostonlukija.hasNextLine()) {
                data += tiedostonlukija.nextLine() + " ";
            }
            
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
        }
        */
        
        String data = "2007 73 " +
"2008 68 " +
"2009 72 " +
"2010 72 " +
"2011 74 " +
"2012 73 " +
"2013 76 " +
"2014 73 " +
"2015 67 " +
"2016 56 " +
"2017 56 ";
        
        XYChart.Series HYShanghai = new XYChart.Series();
        HYShanghai.setName("Helsingin yliopisto");
        
        String[] dataTaulukko = data.split(" ");
        for (int i = 0; i < dataTaulukko.length; i += 2) {
            HYShanghai.getData().add(new XYChart.Data(Integer.valueOf(dataTaulukko[i]), Integer.valueOf(dataTaulukko[i+1])));
        }
        
        viivakaavio.getData().add(HYShanghai);
        
        Scene nakyma = new Scene(viivakaavio);
        ikkuna.setScene(nakyma);
        ikkuna.show();
        
    }

}
