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
public class Kirjasto {
    private ArrayList<Kirja> kirjat;
    
    public Kirjasto() {
        this.kirjat = new ArrayList<>();
    }
    
    public void lisaaKirja(Kirja uusikirja) {
        kirjat.add(uusikirja);
    }
    
    public void tulostaKirjat() {
        for (int i = 0; i < kirjat.size(); i++) {
            System.out.println(kirjat.get(i));
        }
    }
    
    public ArrayList<Kirja> haeKirjaNimekkeella(String haettavanimike) {
        return haeKirjaStringilla(haettavanimike, "nimeke");
    }
    
    public ArrayList<Kirja> haeKirjaStringilla(String merkkijono, String tyyppi) {
        ArrayList<Kirja> palautettavat = new ArrayList<>();
        if (tyyppi.equals("nimeke")) {
            for (int i = 0; i < kirjat.size(); i++) {
                if (StringUtils.sisaltaa(kirjat.get(i).nimeke(), merkkijono)) {
                    palautettavat.add(kirjat.get(i));
                }
            }
        }
        if (tyyppi.equals("julkaisija")) {
            for (int i = 0; i < kirjat.size(); i++) {
                if (StringUtils.sisaltaa(kirjat.get(i).julkaisija(), merkkijono)) {
                    palautettavat.add(kirjat.get(i));
                }
            }
        }
        return palautettavat;
        }
        
    
    
    public ArrayList<Kirja> haeKirjaJulkaisijalla(String haettavajulkaisija) {
        return haeKirjaStringilla(haettavajulkaisija, "julkaisija");
    }
    
    public ArrayList<Kirja> haeKirjaJulkaisuvuodella(int julkaisuvuosi) {
        ArrayList<Kirja> palautettavat = new ArrayList<>();
        for (int i = 0; i < kirjat.size(); i++) {
            if (kirjat.get(i).julkaisuvuosi() == julkaisuvuosi) {
                palautettavat.add(kirjat.get(i));
            }
        }
        return palautettavat;
    }
    
}
