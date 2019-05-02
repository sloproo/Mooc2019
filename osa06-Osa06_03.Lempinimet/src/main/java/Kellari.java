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

public class Kellari {

    private HashMap<String, ArrayList<String>> koppi;

    
    public Kellari() {
        this.koppi = new HashMap<>();
    }
    
    public void lisaa(String komero, String tavara) {
        this.koppi.putIfAbsent(komero, new ArrayList<>());
        this.koppi.get(komero).add(tavara);
    }
    
    public ArrayList<String> sisalto(String komero) {
        ArrayList<String> palautettavalista = new ArrayList<>();
        if (this.koppi == null) return palautettavalista;
        if (this.koppi.isEmpty()) return palautettavalista;
        return this.koppi.get(komero);
    }
    
    public void poista(String komero, String tavara) {
        if (this.koppi.get(komero).contains(tavara)) {
            this.koppi.get(komero).remove(tavara);
        }
        if (this.koppi.get(komero).isEmpty()) {
            this.koppi.clear();
        }
    }
    
    public ArrayList<String> komerot() {
        ArrayList<String> palautettavat = new ArrayList<>();
        for (String listattavat : this.koppi.keySet()) {
            palautettavat.add(listattavat);
        }
        System.out.println(palautettavat);
        return palautettavat;
    }
}
