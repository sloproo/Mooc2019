package sovellus;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;



public class UseampiNakyma extends Application {
    
    @Override
    public void start(Stage ikkuna) {
        
        BorderPane ekanBPane = new BorderPane();
        Label eka = new Label("Eka näkymä!");
        Button tokaan = new Button("Tokaan näkymään!");
        ekanBPane.setTop(eka);
        ekanBPane.setCenter(tokaan);
        
        
        VBox toisenBoksi = new VBox();
        Button kolmanteen = new Button("Kolmanteen näkymään!");
        Label toka = new Label("Toka näkymä!");
        toisenBoksi.getChildren().add(kolmanteen);
        toisenBoksi.getChildren().add(toka);
        
        GridPane kolmannenGPane = new GridPane();
        Label kolmas = new Label("Kolmas näkymä!");
        Button ekaan = new Button("Ekaan näkymään!");
        kolmannenGPane.add(kolmas, 0, 0);
        kolmannenGPane.add(ekaan, 1, 1);

        
        Scene ekaScene = new Scene(ekanBPane);
        Scene tokaScene = new Scene(toisenBoksi);
        Scene kolmasScene = new Scene(kolmannenGPane);
        
        ekaan.setOnAction((event) -> {
            ikkuna.setScene(ekaScene);
        });
        tokaan.setOnAction((event) -> {
            ikkuna.setScene(tokaScene);
        });

        kolmanteen.setOnAction((event) -> {
            ikkuna.setScene(kolmasScene);
        });

        
        
        
        ikkuna.setScene(ekaScene);
        ikkuna.show();
        
    }

    public static void main(String[] args) {
        launch(UseampiNakyma.class);
    }

}
