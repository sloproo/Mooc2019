package sovellus;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class VitsiSovellus extends Application {
    @Override public void start(Stage ikkuna) {
        BorderPane asettelu = new BorderPane();
        
        HBox valikko = new HBox();
        valikko.setPadding(new Insets(20, 20, 20, 20));
        valikko.setSpacing(10);
        
        Button kysymykseen = new Button("Vitsi");
        Button vastaukseen = new Button("Vastaus");
        Button selitykseen = new Button("Selitys");
        
        valikko.getChildren().addAll(kysymykseen, vastaukseen, selitykseen);
        asettelu.setBottom(valikko);
        
        StackPane kysymysRuutu = luoNakyma("What do you call a bear with no teeth?");
        StackPane vastausRuutu = luoNakyma("A gummy bear.");
        StackPane selitysRuutu = luoNakyma("When one has no teeth, the only thing "
                + "in his or her mouth (besides tongue) is gums. Also there is a "
                + "candy called gummy bear. Ha ha!\n"
                + "Joo tiedän et rivitys olisi kiva mut en tässä kohtaa ehdi opetella :grimacing:");
         
        kysymykseen.setOnAction((event) -> asettelu.setCenter(kysymysRuutu));
        vastaukseen.setOnAction((event) -> asettelu.setCenter(vastausRuutu));
        selitykseen.setOnAction((event) -> asettelu.setCenter(selitysRuutu));
        
        asettelu.setCenter(kysymysRuutu);
        
        Scene nakyma = new Scene(asettelu);
        
        ikkuna.setScene(nakyma);
        ikkuna.show();
    }
    
    private StackPane luoNakyma(String teksti) {

        StackPane asettelu = new StackPane();
        asettelu.setPrefSize(300, 180);
        asettelu.getChildren().add(new Label(teksti));
        asettelu.setAlignment(Pos.CENTER);

        return asettelu;
    }

    public static void main(String[] args) {
        launch(VitsiSovellus.class);
    }
}
