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
import java.util.List;

public class Ostoskori {
    private List<Ostos> ostokset;
    
    public Ostoskori() {
        this.ostokset = new ArrayList<>();
    }
    
    public void lisaa(String tuote, int hinta) {
        Ostos lisattavaOstos = new Ostos(tuote, 1, hinta);
        if (this.ostokset.contains(lisattavaOstos)) {
            this.ostokset.get(this.ostokset.indexOf(lisattavaOstos)).kasvataMaaraa();
        } else this.ostokset.add(lisattavaOstos);
    }
    
    public int hinta() {
        int kokonaishinta = 0;
        for (Ostos ostos: this.ostokset) {
            kokonaishinta += ostos.hinta();
        }
        return kokonaishinta;
    }
    
    public void tulosta() {
        for (Ostos ostos: this.ostokset) {
            System.out.println(ostos);
        }
    }
    
    public boolean onkoJo(Ostos ostos) {
        for (Ostos vanha: this.ostokset) {
            if (ostos.equals(vanha)) return true;
        } return false;
    }
    
    
    
}
