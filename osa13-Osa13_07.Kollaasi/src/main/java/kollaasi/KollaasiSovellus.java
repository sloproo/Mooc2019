package kollaasi;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class KollaasiSovellus extends Application {

    @Override
    public void start(Stage stage) {

        // esimerkki avaa kuvan, luo uuden kuvan, ja kopioi avatun kuvan 
        // uuteen kuvaan pikseli kerrallaan
        Image lahdeKuva = new Image("file:monalisa.png");

        PixelReader kuvanLukija = lahdeKuva.getPixelReader();

        int leveys = (int) lahdeKuva.getWidth();
        int korkeus = (int) lahdeKuva.getHeight();

        WritableImage kohdeKuva = new WritableImage(leveys, korkeus);
        PixelWriter kuvanKirjoittaja = kohdeKuva.getPixelWriter();

        int yKoordinaatti = 0;
        while (yKoordinaatti < korkeus) {
            int xKoordinaatti = 0;
            while (xKoordinaatti < leveys) {

                Color vari = kuvanLukija.getColor(xKoordinaatti, yKoordinaatti);
                double punainen = 1.0 - vari.getRed();
                double vihrea = 1.0 - vari.getGreen();
                double sininen = 1 - vari.getBlue();
                double lapinakyvyys = vari.getOpacity();

                Color uusiVari = new Color(punainen, vihrea, sininen, lapinakyvyys);

                // Jos seuraavan rivin uncommenttaa, niin alimmat kaksi pikku-
                // negatiivia ei näy. En ymmärrä miksi mutta sou not.
                //  kuvanKirjoittaja.setColor(xKoordinaatti, yKoordinaatti, uusiVari);
                
                if (xKoordinaatti % 2 == 0 && yKoordinaatti % 2 == 0) {
                    kuvanKirjoittaja.setColor(xKoordinaatti /2 , yKoordinaatti /2, uusiVari);
                    kuvanKirjoittaja.setColor(xKoordinaatti /2 + leveys / 2, yKoordinaatti /2, uusiVari);
                    kuvanKirjoittaja.setColor(xKoordinaatti /2 , yKoordinaatti /2 + korkeus /2, uusiVari);
                    kuvanKirjoittaja.setColor(xKoordinaatti /2 + leveys / 2, yKoordinaatti /2 + korkeus /2, uusiVari);
                }
                
                xKoordinaatti++;
            }

            yKoordinaatti++;
        }

        ImageView kuva = new ImageView(kohdeKuva);

        Pane pane = new Pane();
        pane.getChildren().add(kuva);

        stage.setScene(new Scene(pane));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
