package schelling;


import java.util.HashMap;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SchellingSovellus extends Application {

    private Eriytymismalli eriytymismalli;

    private Slider ryhmia;
    private Slider prosAlueestaKaytossa;
    private Slider tyytyvaisyysRaja;

    private Button kaynnistaNappi;

    public SchellingSovellus() {
        super();

        this.eriytymismalli = new Eriytymismalli(80, 80, 5, 2, 70.0);

        this.ryhmia = new Slider(2, 5, 2);
        this.ryhmia.setShowTickLabels(true);
        this.ryhmia.setShowTickMarks(true);
        this.ryhmia.setMajorTickUnit(1.0);
        this.ryhmia.setMinorTickCount(0);
        this.ryhmia.setSnapToTicks(true);

        this.prosAlueestaKaytossa = new Slider(0, 100, 70);
        this.prosAlueestaKaytossa.setShowTickLabels(true);
        this.prosAlueestaKaytossa.setShowTickMarks(true);
        this.prosAlueestaKaytossa.setMajorTickUnit(25.0);
        this.prosAlueestaKaytossa.setMinorTickCount(4);

        this.tyytyvaisyysRaja = new Slider(0, 9, 5);
        this.tyytyvaisyysRaja.setShowTickLabels(true);
        this.tyytyvaisyysRaja.setShowTickMarks(true);
        this.tyytyvaisyysRaja.setMajorTickUnit(1.0);
        this.tyytyvaisyysRaja.setMinorTickCount(0);
        this.tyytyvaisyysRaja.setSnapToTicks(true);

        this.kaynnistaNappi = new Button("Alusta ja käynnistä");
    }

    @Override
    public void start(Stage stage) {
        final int leveys = 400;
        final int korkeus = 400;

        stage.setTitle("Schelling");

        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(70);

        // luodaan käyttöliittymän komponentit ja asetetaan ne paikalleen
        Canvas piirtoalusta = new Canvas(leveys, korkeus);

        GridPane.setConstraints(piirtoalusta, 0, 0);
        GridPane.setColumnSpan(piirtoalusta, 2);
        grid.getChildren().add(piirtoalusta);

        Label ryhmiaTeksti = new Label("Ryhmien lukumäärä: ");
        GridPane.setConstraints(ryhmiaTeksti, 0, 1);
        grid.getChildren().add(ryhmiaTeksti);
        GridPane.setConstraints(ryhmia, 1, 1);
        grid.getChildren().add(ryhmia);

        Label tyhjyysProsTeksti = new Label("Alueesta käytössä (%): ");
        GridPane.setConstraints(tyhjyysProsTeksti, 0, 2);
        grid.getChildren().add(tyhjyysProsTeksti);
        GridPane.setConstraints(prosAlueestaKaytossa, 1, 2);
        grid.getChildren().add(prosAlueestaKaytossa);

        Label tyytyvaisyysraja = new Label("Tyytyväisyysraja: ");
        GridPane.setConstraints(tyytyvaisyysraja, 0, 3);
        grid.getChildren().add(tyytyvaisyysraja);
        GridPane.setConstraints(tyytyvaisyysRaja, 1, 3);
        grid.getChildren().add(tyytyvaisyysRaja);

        GridPane.setConstraints(kaynnistaNappi, 0, 4);
        GridPane.setColumnSpan(kaynnistaNappi, 2);
        grid.getChildren().add(kaynnistaNappi);

        root.getChildren().add(grid);

        // mitä tehdään kun liukureiden arvoja muutetaan?
        ryhmia.valueProperty().addListener((ObservableValue<? extends Number> ov, Number vanhaArvo, Number uusiArvo) -> {
            this.eriytymismalli.asetaRyhmienLukumaara(uusiArvo.intValue());
        });

        prosAlueestaKaytossa.valueProperty().addListener((ObservableValue<? extends Number> ov, Number vanhaArvo, Number uusiArvo) -> {
            this.eriytymismalli.asetaProsAlueestaKaytossa(uusiArvo.doubleValue());
        });

        tyytyvaisyysRaja.valueProperty().addListener((ObservableValue<? extends Number> ov, Number vanhaArvo, Number uusiArvo) -> {
            this.eriytymismalli.asetaTyytyvaisyysRaja(uusiArvo.intValue());
        });

        kaynnistaNappi.setOnAction((ActionEvent e) -> {
            if (eriytymismalli.onKaynnissa()) {
                eriytymismalli.sammuta();
                kaynnistaNappi.setText("Alusta ja käynnistä");
            } else {
                eriytymismalli.alusta();
                eriytymismalli.kaynnista();
                kaynnistaNappi.setText("Sammuta");
            }
        });

        // luodaan piirtämiseen tarvittavat komponentit
        GraphicsContext piirturi = piirtoalusta.getGraphicsContext2D();

        new AnimationTimer() {
            // päivitetään animaatiota noin 100 millisekunnin välein
            private long sleepNanoseconds = 100 * 1000000;
            private long prevTime = 0;

            public void handle(long currentNanoTime) {
                // päivitetään animaatiota noin 100 millisekunnin välein
                if ((currentNanoTime - prevTime) < sleepNanoseconds) {
                    return;
                }

                if (!eriytymismalli.onKaynnissa()) {
                    return;
                }

                // piirretään alusta
                piirturi.setFill(Color.WHITE);
                piirturi.clearRect(0, 0, leveys, korkeus);

                // piirretään eriytymismalli
                Taulukko taulukko = eriytymismalli.getData();

                for (int x = 0; x < taulukko.getLeveys(); x++) {
                    for (int y = 0; y < taulukko.getKorkeus(); y++) {
                        int arvo = taulukko.hae(x, y);

                        // valitaan väri joukolle -- korkeintaan 5 eri joukkoa
                        if (arvo == 1) {
                            piirturi.setFill(Color.RED);
                        } else if (arvo == 2) {
                            piirturi.setFill(Color.BLUE);
                        } else if (arvo == 3) {
                            piirturi.setFill(Color.SANDYBROWN);
                        } else if (arvo == 4) {
                            piirturi.setFill(Color.GRAY);
                        } else if (arvo == 5) {
                            piirturi.setFill(Color.GREEN);
                        } else {
                            piirturi.setFill(Color.WHITE);
                        }

                        // piirretään yksittäistä alkiota kuvaava neliö
                        piirturi.fillRect(x * 5, y * 5, 5, 5);

                    }

                }

                // kutsutaan schellingin paivita-metodia
                eriytymismalli.paivita();

                // älä muuta tätä -- käytetään ajastamiseen
                prevTime = currentNanoTime;
            }
        }.start();

        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
