package sovellus;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


// END SOLUTION
public class SanaharjoitteluSovellus extends Application {
    
    private Sanakirja sanakirja;
    
    @Override
    public void init() throws Exception {
        this.sanakirja = new Sanakirja();
    }
    
    @Override
    public void start(Stage ikkuna) throws Exception {
        Harjoittelunakyma harjoittelunakyma = new Harjoittelunakyma(sanakirja);
        Syottonakyma syottonakyma = new Syottonakyma(sanakirja);
        
        BorderPane asettelu = new BorderPane();
        
        HBox valikko = new HBox();
        valikko.setPadding(new Insets(20, 20, 20, 20));
        valikko.setSpacing(10);
        
        Button lisaysNappi = new Button("Lisää sanoja");
        Button harjoitteluNappi = new Button("Harjoittele");
        
        valikko.getChildren().addAll(lisaysNappi, harjoitteluNappi);
        asettelu.setTop(valikko);
        
        lisaysNappi.setOnAction((event) -> {
        asettelu.setCenter(syottonakyma.getNakyma());
        });
        
        harjoitteluNappi.setOnAction((event) -> {
            asettelu.setCenter(harjoittelunakyma.getNakyma());
        });
        
        asettelu.setCenter(syottonakyma.getNakyma());
        
        Scene nakyma = new Scene(asettelu, 400, 300);
        
        ikkuna.setScene(nakyma);
        ikkuna.show();
        
    }

    public static void main(String[] args) {
        launch(SanaharjoitteluSovellus.class);
    }
}
