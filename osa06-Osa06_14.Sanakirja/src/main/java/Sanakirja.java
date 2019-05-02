/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pyry
 */
import java.util.HashMap;
import java.util.ArrayList;
        
public class Sanakirja {
    private HashMap<String, String> sanat;
    
    
    public Sanakirja() {
        this.sanat = new HashMap<>();
    }
    
    public void lisaa(String sana, String kaannos) {
        this.sanat.put(sana, kaannos);
    }
    
    public String kaanna(String sana) {
        return this.sanat.get(sana);
    }
    
    public int sanojenLukumaara() {
        return this.sanat.size();
    }
    
    public ArrayList<String> kaannoksetListana() {
        ArrayList<String> kaannokset = new ArrayList<>();
        for (String sana : sanat.keySet()) {
            String lisattavaString = sana + " = " + this.sanat.get(sana);
            kaannokset.add(lisattavaString);
        }
        return kaannokset;
    }
    
    
}
