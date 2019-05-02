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
import java.util.Iterator;
import java.util.List;

public class Tyontekijat {
    private ArrayList<Henkilo> tyontekijat;
    //private Iterator<Henkilo> iteraattori;
    
    public Tyontekijat() {
        this.tyontekijat = new ArrayList<>();
        //this.iteraattori = this.tyontekijat.iterator();
    }
    
    public void lisaa(Henkilo lisattava) {
        this.tyontekijat.add(lisattava);
    }
    
    public void lisaa(List<Henkilo> lisattavat) {
        this.tyontekijat.addAll(lisattavat);
    }
    
    public void tulosta() {
        Iterator<Henkilo> iteraattori = this.tyontekijat.iterator();
        while (iteraattori.hasNext()) {
            System.out.println(iteraattori.next());
        }
    }
    
    public void tulosta(Koulutus koulutus) {
        Iterator<Henkilo> iteraattori = this.tyontekijat.iterator();
        while (iteraattori.hasNext()) {
            Henkilo matchattava = iteraattori.next();
            if (matchattava.getKoulutus().equals(koulutus)) {
                System.out.println(matchattava);
            }
        }
    }
    
    public void irtisano(Koulutus koulutus) {
        Iterator<Henkilo> iteraattori = this.tyontekijat.iterator();
        while (iteraattori.hasNext()) {
            if (iteraattori.next().getKoulutus().equals(koulutus)) {
                iteraattori.remove();
            }
        }
    }
    
    
    
}
