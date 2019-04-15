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
public class Syottonakyma {
    private Sanakirja sanakirja;
    
    public Syottonakyma(Sanakirja sanakirja) {
        this.sanakirja = sanakirja;
    }
    
    public Parent getNakyma() {
        GridPane asettelu = new GridPane();
        Label sanaTeksti = new Label("Sana:");
        TextField sanaRuutu = new TextField();
        Label kaannosTeksti = new Label("Käänös");
        TextField kaannosRuutu = new TextField();
        Button lisaysNappi = new Button("Lisää sana & käännös");
        
        asettelu.setAlignment(Pos.CENTER);
        asettelu.setVgap(10);
        asettelu.setHgap(10);
        asettelu.setPadding(new Insets(10, 10, 10, 10));
        
        asettelu.add(sanaTeksti, 0, 0);
        asettelu.add(sanaRuutu, 0, 1);
        asettelu.add(kaannosTeksti, 1, 0);
        asettelu.add(kaannosRuutu, 1, 1);
        asettelu.add(lisaysNappi, 2,1);
        
        lisaysNappi.setOnMouseClicked((event) -> {
            String sana = sanaRuutu.getText();
            String kaannos = sanaRuutu.getText();
            
            sanakirja.lisaaSana(sana, kaannos);
            sanaRuutu.clear();
            kaannosRuutu.clear();
        });
        
        return asettelu;
        
    }
}
