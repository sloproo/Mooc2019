/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sovellus;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Pyry
 */
public class Harjoittelunakyma {
    
    private Sanakirja sanakirja;
    private String sana;
    
    public Harjoittelunakyma(Sanakirja sanakirja) {
        this.sanakirja = sanakirja;
        this.sana = sanakirja.randomSana();
    }
    
    public Parent getNakyma() {
        GridPane asettelu = new GridPane();
        
        Label sanaTeksti = new Label("Käännä sana '" + this.sana + "'.");
        TextField kaannosKentta = new TextField();
        Button tarkistusNappi = new Button("Tarkista");
        Label palaute = new Label("");
        
        asettelu.add(sanaTeksti, 0, 0);
        asettelu.add(kaannosKentta, 0, 1);
        asettelu.add(tarkistusNappi, 0, 2);
        asettelu.add(palaute, 0, 3);
        
        tarkistusNappi.setOnMouseClicked((event) -> {
            String kaannos = kaannosKentta.getText();
            if (sanakirja.getKaannos(sana).equals(kaannos)) {
                palaute.setText("Oikein!");
            } else {
                palaute.setText("Väärin! Sanan '" + sana + "' käännös on "+ 
                    sanakirja.getKaannos(sana) + ".");
            return;
            }
            
            this.sana = this.sanakirja.randomSana();
            kaannosKentta.clear();
        });
    
        return asettelu;
    
    }
}
