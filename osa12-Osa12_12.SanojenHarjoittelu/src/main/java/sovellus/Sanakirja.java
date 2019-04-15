/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sovellus;


import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Pyry
 */
public class Sanakirja {
    private ArrayList<String> sanat;
    private HashMap<String, String> kaannokset;
    
    public Sanakirja() {
        this.sanat = new ArrayList<>();
        this.kaannokset = new HashMap<>();
        
        lisaaSana("Sana", "Word");
    }
    
    public void lisaaSana(String sana, String kaannos) {
        if (!(this.kaannokset.containsKey(sana))) {
            this.sanat.add(sana);
        }
        this.kaannokset.put(sana, kaannos);
    }
    
    public String getKaannos(String sana) {
        return this.kaannokset.get(sana);
    }
    
    public String randomSana() {
        Random satunnainen = new Random();
        return this.sanat.get(satunnainen.nextInt(this.sanat.size()));
    }
    
    
    
}
