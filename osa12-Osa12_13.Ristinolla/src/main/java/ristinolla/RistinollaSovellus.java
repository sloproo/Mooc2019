package ristinolla;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.scene.text.Font;




public class RistinollaSovellus extends Application {
    
    private String vuoro;
    private ArrayList<Button> napit;
    
    @Override
    public void start(Stage ikkuna) throws Exception {
        
        this.vuoro = "X";
        napit = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            napit.add(new Button(" "));
            napit.get(i).setFont(Font.font("Monospaced", 40));
        }
        
                
                BorderPane asettelu = new BorderPane();
        
        Label pelinStatus = new Label("Vuoro: " + this.vuoro);
        GridPane ruudukko = new GridPane();
        
        for (int i = 0; i < 9; i++) {
            if (i < 3) ruudukko.add(napit.get(i), 0, i);
            else if (i > 2 && i < 6) ruudukko.add(napit.get(i), 1, i-3);
            else if (i > 5) ruudukko.add(napit.get(i), 2, i-6);
        }
        
        napit.stream().forEach(n -> n.setOnMouseClicked((event) -> {
            if (!pelinStatus.getText().equals("Loppu!")) {
                if (n.getText().equals(" ")) {
                    n.setText(vuoro);
                    vuoro = vaihdaVuoroa(vuoro);
                    pelinStatus.setText("Vuoro: " + vuoro);
                    if (ratkesiko() == true) pelinStatus.setText("Loppu!");
                }
            }
            
        }));
        
        
        asettelu.setTop(pelinStatus);
        asettelu.setCenter(ruudukko);
        
        Scene nakyma = new Scene(asettelu);
        
        ikkuna.setScene(nakyma);
        ikkuna.show();
        
        }
    
    
        
    
    


    public static void main(String[] args) {
        launch(RistinollaSovellus.class);
    }
    
    public String vaihdaVuoroa(String vanha) {
        if (vanha.equals("X")) return "O";
        else return "X";
    }
    
    public boolean ratkesiko() {
        if (napit.get(0).getText().equals(napit.get(1).getText()) && 
                napit.get(0).getText().equals(napit.get(2).getText()) && 
                !napit.get(0).getText().equals(" ")) return true;
        if (napit.get(3).getText().equals(napit.get(4).getText()) && 
                napit.get(3).getText().equals(napit.get(5).getText()) && 
                !napit.get(3).getText().equals(" ")) return true;
        if (napit.get(6).getText().equals(napit.get(7).getText()) && 
                napit.get(6).getText().equals(napit.get(8).getText()) && 
                !napit.get(6).getText().equals(" ")) return true;
        if (napit.get(0).getText().equals(napit.get(3).getText()) && 
                napit.get(0).getText().equals(napit.get(6).getText()) && 
                !napit.get(0).getText().equals(" ")) return true;
        if (napit.get(1).getText().equals(napit.get(4).getText()) && 
                napit.get(1).getText().equals(napit.get(7).getText()) && 
                !napit.get(1).getText().equals(" ")) return true;
        if (napit.get(2).getText().equals(napit.get(5).getText()) && 
                napit.get(2).getText().equals(napit.get(8).getText()) && 
                !napit.get(2).getText().equals(" ")) return true;
        if (napit.get(0).getText().equals(napit.get(4).getText()) && 
                napit.get(0).getText().equals(napit.get(8).getText()) && 
                !napit.get(0).getText().equals(" ")) return true;
        if (napit.get(2).getText().equals(napit.get(4).getText()) && 
                napit.get(2).getText().equals(napit.get(5).getText()) && 
                !napit.get(2).getText().equals(" ")) return true;
        else return false;
    }

}
