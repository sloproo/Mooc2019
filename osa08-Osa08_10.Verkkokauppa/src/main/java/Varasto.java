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
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class Varasto {
    private Map<String, Integer> hinta;
    private Map<String, Integer> saldo;
    
    public Varasto() {
        this.hinta = new HashMap<>();
        this.saldo = new HashMap<>();
    }
    
    public void lisaaTuote(String tuote, int hinta, int saldo) {
        this.hinta.put(tuote, hinta);
        this.saldo.put(tuote, saldo);
    }
    
    public int hinta(String tuote) {
        if (this.onkoTuotetta(tuote)){
            return this.hinta.get(tuote);
        } else return -99;
    }
    
    public int saldo(String tuote) {
        if (this.saldo.keySet().contains(tuote)) {
            return this.saldo.get(tuote); 
        } else return 0;
    }
    
    public boolean onkoTuotetta(String tuote) {
        return this.saldo.keySet().contains(tuote);
    }
    
    public boolean ota(String tuote) {
        if (onkoTuotetta(tuote) && this.saldo(tuote) > 0) {
            this.saldo.put(tuote, this.saldo(tuote) -1 );
            return true;
        } else return false;
        
    }
    
    public Set<String> tuotteet() {
        Set<String> tuotelistaus = new HashSet<>();
        for (String avain: this.saldo.keySet()) {
            tuotelistaus.add(avain);
        }
        return tuotelistaus;
    }
    
    
}
