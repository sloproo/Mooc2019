/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pyry
 */
import java.util.ArrayList;
import java.util.HashMap;
        
public class UseanKaannoksenSanakirja {
    private HashMap<String, ArrayList<String>> kaannos;
    
    public UseanKaannoksenSanakirja() {
        this.kaannos = new HashMap<>();
    }
    
    public void lisaa(String sana, String kaannos) {
            this.kaannos.putIfAbsent(sana, new ArrayList<>());
            this.kaannos.get(sana).add(kaannos);
    }
    
    public ArrayList<String> kaanna(String sana) {
        ArrayList<String> tyhjapalautus = new ArrayList<>();
        if (this.kaannos.get(sana) == null) {
            return tyhjapalautus;
        }
        if (this.kaannos.isEmpty()) {
            return tyhjapalautus;
        }
        return this.kaannos.get(sana);
    }
    
    public void poista(String sana) {
        this.kaannos.remove(sana);
    }



}
