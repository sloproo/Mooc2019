package gameoflife;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameOfLifeSovellus extends Application {

    public void start(Stage stage) {
        final int leveys = 800;
        final int korkeus = 800;

        stage.setTitle("Game of Life");

        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        Canvas piirtoalusta = new Canvas(leveys, korkeus);
        root.getChildren().add(piirtoalusta);

        GraphicsContext piirturi = piirtoalusta.getGraphicsContext2D();

        GameOfLife gol = new GameOfLife(200, 200);
        gol.alustaSatunnaisesti();

        new AnimationTimer() {
            // päivitetään animaatiota noin 100 millisekunnin välein
            private long sleepNanoseconds = 100 * 1000000;
            private long prevTime = 0;

            public void handle(long currentNanoTime) {
                // päivitetään animaatiota noin 200 millisekunnin välein
                if ((currentNanoTime - prevTime) < sleepNanoseconds) {
                    return;
                }

                // piirretään alusta
                piirturi.setFill(Color.WHITE);
                piirturi.clearRect(0, 0, leveys, korkeus);

                // piirretään peli
                piirturi.setFill(Color.BLACK);

                int[][] taulukko = gol.getTaulukko();
                for (int x = 0; x < taulukko.length; x++) {
                    for (int y = 0; y < taulukko[x].length; y++) {
                        if (taulukko[x][y] == 1) {
                            piirturi.fillRect(x * 4, y * 4, 4, 4);
                        }
                    }
                }

                // kutsutaan game of lifelle kehity-metodia
                gol.kehity();

                // älä muuta tätä
                prevTime = currentNanoTime;
            }
        }.start();

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
