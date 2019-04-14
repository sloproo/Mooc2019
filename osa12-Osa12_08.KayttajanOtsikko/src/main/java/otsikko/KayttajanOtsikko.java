package otsikko;

import javafx.application.Application;
import javafx.stage.Stage;

public class KayttajanOtsikko extends Application {

    @Override
    public void start(Stage ikkuna) {
        ikkuna.setTitle(getParameters().getNamed().get("title"));
        ikkuna.show();
    }
    


}
