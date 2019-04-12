package nappijatekstielementti;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;




public class NappiJaTekstielementtiSovellus extends Application {
    
    @Override
    public void start(Stage ikkuna) {
        Button nappi = new Button("Juu tämne nappi täsä näi");
        Label teksti = new Label("Tekstiäkin juuh, ou boi");
        
        FlowPane asettelu = new FlowPane();
        asettelu.getChildren().add(nappi);
        asettelu.getChildren().add(teksti);
        
        Scene nakyma = new Scene(asettelu);
        
        ikkuna.setScene(nakyma);
        ikkuna.show();
    }
    
    
    public static void main(String[] args) {
        launch(NappiJaTekstielementtiSovellus.class);
    }

}
