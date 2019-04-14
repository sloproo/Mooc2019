package tekstitilastointia;

import java.util.Arrays;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;





public class TekstitilastointiaSovellus extends Application {
    
    @Override
    public void start(Stage ikkuna) {
        BorderPane asettelu = new BorderPane();
        
        TextArea tekstikentta = new TextArea();
        
        HBox tekstikomponentit = new HBox();
        tekstikomponentit.setSpacing(10);
        
        Label kirjainIlmaisin = new Label("Kirjaimia: ");
        Label sanojenIlmaisin = new Label("Sanoja: ");
        Label pisimmanSananIlmaisin = new Label("Pisin sana on: ");
        
        tekstikomponentit.getChildren().add(kirjainIlmaisin);
        tekstikomponentit.getChildren().add(sanojenIlmaisin);
        tekstikomponentit.getChildren().add(pisimmanSananIlmaisin);
        
        
        
        tekstikentta.textProperty().addListener((muutos, vanhaArvo, uusiArvo) -> {
            int kirjaimia = uusiArvo.length();
            String[] palat = uusiArvo.split(" ");
            int sanoja = palat.length;
            String pisinSana = Arrays.stream(palat)
                    .sorted((s1, s2) -> s2.length() - s1.length())
                    .findFirst()
                    .get();
            kirjainIlmaisin.setText("Kirjaimia: " + String.valueOf(kirjaimia));
            sanojenIlmaisin.setText("Sanoja: " + String.valueOf(sanoja));
            pisimmanSananIlmaisin.setText("Pisin sana on: "+ pisinSana);
        });
        
        
        
        
        
        asettelu.setCenter(tekstikentta);
        asettelu.setBottom(tekstikomponentit);
        
        Scene nakyma = new Scene(asettelu);
        
        ikkuna.setScene(nakyma);
        ikkuna.show();
        
    }


    public static void main(String[] args) {
        launch(TekstitilastointiaSovellus.class);
    }

}
