package sovellus;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TervehtijaSovellus extends Application {
    
    @Override
    public void start(Stage ikkuna) /*throws Exception */{
        Label kirjoitaKasky = new Label("Kirjoita nimesi ja aloita.");
        TextField nimiKentta = new TextField();
        Button nimenOtto = new Button("Aloita");
        
        GridPane nimenOttoAsettelu = new GridPane();
        nimenOttoAsettelu.add(kirjoitaKasky, 0, 0);
        nimenOttoAsettelu.add(nimiKentta, 0, 1);
        nimenOttoAsettelu.add(nimenOtto, 0, 2);
        
        nimenOttoAsettelu.setPrefSize(300, 300);
        nimenOttoAsettelu.setAlignment(Pos.CENTER);
        nimenOttoAsettelu.setVgap(10);
        nimenOttoAsettelu.setHgap(10);
        
        
        Scene nimenOttoRuutu = new Scene(nimenOttoAsettelu);
        
        String nimi = "";
        
        StackPane tervetuloAsettelu = new StackPane();
        tervetuloAsettelu.setPrefSize(300, 300);
        tervetuloAsettelu.setAlignment(Pos.CENTER);
                        
        Scene tervetuloRuutu = new Scene(tervetuloAsettelu);
        
        nimenOtto.setOnAction((event) -> {
            Label tervetuloa = new Label("Tervetuloa " + nimiKentta.getText() + "!");
            tervetuloAsettelu.getChildren().add(tervetuloa);
            ikkuna.setScene(tervetuloRuutu);
        });
        
        ikkuna.setScene(nimenOttoRuutu);
        ikkuna.show();
    }    

    
    public static void main(String[] args) {
        launch(TervehtijaSovellus.class);
    }
}
