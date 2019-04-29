package hiekkaranta;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HiekkarantaSovellus extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        int leveys = 200;
        int korkeus = 200;
        Simulaatio simulaatio = new Simulaatio(leveys, korkeus);

        final ToggleGroup valintaryhma = new ToggleGroup();

        RadioButton metalli = new RadioButton("Metalli");
        metalli.setToggleGroup(valintaryhma);
        metalli.setSelected(true);

        RadioButton hiekka = new RadioButton("Hiekka");
        hiekka.setToggleGroup(valintaryhma);

        RadioButton vesi = new RadioButton("Vesi");
        vesi.setToggleGroup(valintaryhma);

        Canvas piirtoalusta = new Canvas(leveys, korkeus);

        GraphicsContext piirturi = piirtoalusta.getGraphicsContext2D();

        piirtoalusta.setOnMouseDragged((event) -> {
            double kohtaX = event.getX();
            double kohtaY = event.getY();

            for (int x = (int) kohtaX - 4; x < (int) kohtaX + 4; x++) {
                for (int y = (int) kohtaY - 4; y < (int) kohtaY + 4; y++) {
                    if (Math.random() < 0.4) {
                        continue;
                    }

                    if (valintaryhma.getSelectedToggle() == metalli) {
                        simulaatio.lisaa(x, y, Tyyppi.METALLI);
                    }
                    if (valintaryhma.getSelectedToggle() == hiekka) {
                        simulaatio.lisaa(x, y, Tyyppi.HIEKKA);
                    }
                    if (valintaryhma.getSelectedToggle() == vesi) {
                        simulaatio.lisaa(x, y, Tyyppi.VESI);
                    }

                }
            }
        }
        );

        // piirtäminen
        new AnimationTimer() {
            long edellinen = 0;

            @Override
            public void handle(long nykyhetki) {
                if (nykyhetki - edellinen < 100000000) {
                    return;
                }

                piirturi.setFill(Color.BLACK);
                piirturi.fillRect(0, 0, leveys, korkeus);

                for (int x = 0; x < leveys; x++) {
                    for (int y = 0; y < korkeus; y++) {
                        Tyyppi sisalto = simulaatio.sisalto(x, y);
                        if (sisalto == null || sisalto == Tyyppi.TYHJA) {
                            continue;
                        }

                        if (sisalto == Tyyppi.METALLI) {
                            piirturi.setFill(Color.WHITE);
                        } else if (sisalto == Tyyppi.HIEKKA) {
                            piirturi.setFill(Color.ORANGE);
                        } else if (sisalto == Tyyppi.VESI) {
                            piirturi.setFill(Color.LIGHTBLUE);
                        }

                        piirturi.fillRect(x, y, 1, 1);
                    }
                }

                this.edellinen = nykyhetki;
            }
        }.start();

        // animointi
        new AnimationTimer() {
            private long edellinen;

            @Override
            public void handle(long nykyhetki) {
                if (nykyhetki - edellinen < 1000000) {
                    return;
                }

                simulaatio.paivita();
                this.edellinen = nykyhetki;
            }
        }.start();

        BorderPane asettelu = new BorderPane();
        asettelu.setCenter(piirtoalusta);

        VBox napit = new VBox();
        napit.getChildren().addAll(metalli, hiekka, vesi);

        asettelu.setRight(napit);
        Scene nakyma = new Scene(asettelu);

        stage.setScene(nakyma);
        stage.show();
    }

    public static void main(String[] args) {
        launch(HiekkarantaSovellus.class);
    }
}
