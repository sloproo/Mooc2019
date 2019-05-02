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

public class Lauma implements Siirrettava {
    private ArrayList<Siirrettava> elikot;
    
    public Lauma() {
        this.elikot = new ArrayList<>();
    }
    
    public void lisaaLaumaan(Siirrettava siirrettava) {
        this.elikot.add(siirrettava);
    }
    
    public void siirra(int dx, int dy) {
        for (Siirrettava otus: this.elikot) {
            otus.siirra(dx, dy);
        }
    }
    
    @Override
    public String toString() {
        String palautettava = "";
        for (Siirrettava otus: elikot) {
            palautettava += otus + "\n";
        }
        return palautettava;
    }
}
