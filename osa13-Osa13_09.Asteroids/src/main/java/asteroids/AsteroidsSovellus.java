package asteroids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public class AsteroidsSovellus extends Application {
    
    public static int LEVEYS = 300;
    public static int KORKEUS = 200;

    public static void main(String[] args) {
        launch(args);
    }

    public static int osiaToteutettu() {
        return 4;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Pane ruutu = new Pane();
        Text text = new Text(10, 20, "Points: 0");
        ruutu.getChildren().add(text);
        
        AtomicInteger pisteet = new AtomicInteger();
        
        
        
        ruutu.setPrefSize(600, 400);
        
        Alus alus = new Alus(150, 100);
        List<Asteroidi> asteroidit = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Random rnd = new Random();
            Asteroidi asteroidi = new Asteroidi(rnd.nextInt(100), rnd.nextInt(100));
            asteroidit.add(asteroidi);
        }
        List<Ammus> ammukset = new ArrayList<>();
        
        ruutu.getChildren().add(alus.getHahmo());
        asteroidit.forEach(asteroidi -> ruutu.getChildren().add(asteroidi.getHahmo()));
        
        

        Scene scene = new Scene(ruutu);
        
        Map<KeyCode, Boolean> painetutNapit = new HashMap<>();
        
        scene.setOnKeyPressed(event -> {
            painetutNapit.put(event.getCode(), Boolean.TRUE);
        });
        
        scene.setOnKeyReleased(event -> {
            painetutNapit.put(event.getCode(), Boolean.FALSE);
        });
        

         new AnimationTimer() {
            @Override
            public void handle(long nykyhetki) {
                
                        
                if(painetutNapit.getOrDefault(KeyCode.LEFT, false)) {
                    alus.kaannaVasemmalle();
                }
                if(painetutNapit.getOrDefault(KeyCode.RIGHT, Boolean.FALSE)) {
                    alus.kaannaOikealle();
                }
                if(painetutNapit.getOrDefault(KeyCode.UP, Boolean.FALSE)) {
                    alus.kiihdyta();
                }
                
                if (painetutNapit.getOrDefault(KeyCode.SPACE, Boolean.FALSE) && ammukset.size() < 3) {
                    Ammus ammus = new Ammus((int) alus.getHahmo().getTranslateX(), (int) alus.getHahmo().getTranslateY());
                    ammus.getHahmo().setRotate(alus.getHahmo().getRotate());
                    ammukset.add(ammus);
                    
                    ammus.kiihdyta();
                    ammus.setLiike(ammus.getLiike().normalize().multiply(3));
                    
                    ruutu.getChildren().add(ammus.getHahmo());
                }
                
                
                alus.liiku();
                asteroidit.forEach(asteroidi -> asteroidi.liiku());
                ammukset.forEach(ammus -> ammus.liiku());
                
                asteroidit.forEach(asteroidi -> {
                    if(alus.tormaa(asteroidi)) {
                        stop();
                    }
                });
                
                ammukset.forEach(ammus -> {
                    asteroidit.forEach(asteroidi -> {
                        if(ammus.tormaa(asteroidi)) {
                            ammus.setElossa(false);
                            asteroidi.setElossa(false);
                        }
                    });
                    if (!ammus.isElossa()) {
                        text.setText("Points: " + pisteet.addAndGet(1000));
                    }
                });
                
                ammukset.stream()
                .filter(ammus -> !ammus.isElossa())
                .forEach(ammus -> ruutu.getChildren().remove(ammus.getHahmo()));
                ammukset.removeAll(ammukset.stream()
                        .filter(ammus -> !ammus.isElossa())
                        .collect(Collectors.toList()));

                asteroidit.stream()
                    .filter(asteroidi -> !asteroidi.isElossa())
                    .forEach(asteroidi -> ruutu.getChildren().remove(asteroidi.getHahmo()));
                asteroidit.removeAll(asteroidit.stream()
                        .filter(asteroidi -> !asteroidi.isElossa())
                        .collect(Collectors.toList()));
              
                if(Math.random() < 0.005) {
                    Asteroidi asteroidi = new Asteroidi(LEVEYS, KORKEUS);
                    if (!asteroidi.tormaa(alus)) {
                        asteroidit.add(asteroidi);
                        ruutu.getChildren().addAll(asteroidi.getHahmo());
                    }
                }
            }
            
            
        }.start();

        stage.setTitle("Asteroids!");
        stage.setScene(scene);
        stage.show();
    }

}
