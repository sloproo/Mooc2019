package sovellus;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SaastolaskuriSovellus extends Application {
    
    public static void main(String[] args) {
        launch(SaastolaskuriSovellus.class);
    }
    
    @Override
    public void start(Stage ikkuna) {
        
        BorderPane reunaRuutu = new BorderPane();
        NumberAxis xAkseli = new NumberAxis(0, 30, 5);
        NumberAxis yAkseli = new NumberAxis();
        LineChart viivaKaavio = new LineChart(xAkseli, yAkseli);
        VBox ylaLaatikko = new VBox();
        BorderPane talletusRuutu = new BorderPane();
        BorderPane korkoRuutu = new BorderPane();
        Slider tallennusLiuku = new Slider(25, 250, 25);
        Slider korkoLiuku = new Slider(0, 10, 2);
        Label tallennus = new Label("Kuukausittainen tallennus");
        Label tallennuksenArvo = new Label(String.valueOf(tallennusLiuku.getValue()));
        Label korko = new Label("Vuosittainen korko");
        Label koronArvo = new Label(String.valueOf(korkoLiuku.getValue()));
        
        talletusRuutu.setLeft(tallennus);
        talletusRuutu.setCenter(tallennusLiuku);
        talletusRuutu.setRight(tallennuksenArvo);
        korkoRuutu.setLeft(korko);
        korkoRuutu.setCenter(korkoLiuku);
        korkoRuutu.setRight(koronArvo);
        reunaRuutu.setCenter(viivaKaavio);
        ylaLaatikko.getChildren().addAll(talletusRuutu, korkoRuutu);
        reunaRuutu.setTop(ylaLaatikko);
        koronArvo.setMinWidth(40);
        koronArvo.setMaxWidth(40);
        tallennuksenArvo.setMinWidth(40);
        tallennuksenArvo.setMaxWidth(40);
        
        tallennusLiuku.setShowTickMarks(true);
        tallennusLiuku.setShowTickLabels(true);
        tallennusLiuku.setMajorTickUnit(5.0);
        tallennusLiuku.setSnapToTicks(true);
        tallennusLiuku.setMinorTickCount(0);
        tallennusLiuku.setBlockIncrement(5);

        korkoLiuku.setShowTickMarks(true);
        korkoLiuku.setShowTickLabels(true);
        korkoLiuku.setMajorTickUnit(0.1);
        korkoLiuku.setBlockIncrement(0.1);
        korkoLiuku.setSnapToTicks(true);
        tallennusLiuku.setMinorTickCount(0);
        
        XYChart.Series tallennuskuvaaja = new XYChart.Series();
        tallennuskuvaaja.setName("Tallennukset");
        for (int i = 0; i <= 30; i++) {
            tallennuskuvaaja.getData().add(new XYChart.Data(i, i * tallennusLiuku.getValue() * 12));
        }
        
        tallennusLiuku.valueProperty().addListener((muutos, vanhaArvo, uusiArvo) -> {
            tallennuksenArvo.setText(String.valueOf(uusiArvo));
            tallennuskuvaaja.getData().clear();
            for (int i = 0; i <= 30; i++) {
            tallennuskuvaaja.getData().add(new XYChart.Data(i, i * tallennusLiuku.getValue() * 12));
        }
        });
        
        ArrayList<Double> saldoKorkoineen = new ArrayList<>();
        
        
        
        XYChart.Series korkokuvaaja = new XYChart.Series();
        korkokuvaaja.setName("Tallennukset + korko");
       // korkokuvaaja.getData().addAll((Number) tallennusLiuku.getValue(), (Number) korkoLiuku.getValue());
       
        
        korkoLiuku.valueProperty().addListener((muutos, vanhaArvo, uusiArvo) -> {
            koronArvo.setText(String.valueOf(uusiArvo));
            korkokuvaaja.getData().clear();
     //       korkokuvaaja.getData().addAll((int) tallennusLiuku.getValue(), (int) korkoLiuku.getValue()));
        });
        
        viivaKaavio.setTitle("Säästölaskuri");
              
        viivaKaavio.getData().add(tallennuskuvaaja);
//        viivaKaavio.getData().add(korkokuvaaja);
        
                
        Scene nakyma = new Scene(reunaRuutu);
        ikkuna.setScene(nakyma);
        ikkuna.show();
    }
    
    public ArrayList<Double> laskeKorko(int tallennus, double korko) {
        ArrayList<Double> palautettava = new ArrayList<>();
        palautettava.add(0.0);
        for (int i = 1; i <= 30; i++) {
            palautettava.add(palautettava.get(i-0) + tallennus * 12 + tallennus 
                    * 12 * korko / 100 );
        }
        return palautettava;
    }
}
