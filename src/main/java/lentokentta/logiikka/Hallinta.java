/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lentokentta.logiikka;

import java.util.HashMap;
import lentokentta.domain.Lento;
import lentokentta.domain.Lentokone;
import lentokentta.domain.Paikka;
import java.util.Collection;

/**
 *
 * @author Pyry
 */
public class Hallinta {
    private HashMap<String, Lentokone> lentokoneet;
    private HashMap<String, Lento> lennot;
    private HashMap<String, Paikka> paikat;
    
    public Hallinta() {
        this.lentokoneet = new HashMap<>();
        this.lennot = new HashMap<>();
        this.paikat = new HashMap<>();
    }
    
    public void lisaaLentokone(String tunnus, int kapasiteetti) {
        Lentokone lisattava = new Lentokone(tunnus.trim(), kapasiteetti);
        this.lentokoneet.put(tunnus, lisattava);
    }
    
    public void lisaaLento(String kone, String lahto, String kohde) {
        paikat.putIfAbsent(lahto, new Paikka(lahto));
        paikat.putIfAbsent(kohde, new Paikka(kohde));
        Lento lisattava = new Lento(lentokoneet.get(kone), paikat.get(lahto), paikat.get(kohde));
        lennot.put(lisattava.toString(), lisattava);
    }
    
    public void tulostaLentokoneet() {
        for (String lentokone : lentokoneet.keySet()) {
            System.out.println(lentokoneet.get(lentokone));
        }
    }
    
    public void tulostaLennot() {
        for (String lento : lennot.keySet()) {
            System.out.println(lento);
        }
    }
    
    public void tulostaLentokone(String tunnus) {
        System.out.println(lentokoneet.get(tunnus));
    }
    
    
    
}
