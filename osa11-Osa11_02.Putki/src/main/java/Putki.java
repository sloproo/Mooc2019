/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pyry
 * @param <T>
 */
import java.util.ArrayList;

public class Putki<T> {
    private ArrayList<T> putki;
    
    public Putki() {
        this.putki = new ArrayList<>();
    }
    
    public void lisaaPutkeen(T arvo) {
        putki.add(arvo);
    }
    
    public T otaPutkesta() {
        if (putki.isEmpty()) return null;
        else {
            T palautettava = putki.get(0);
            putki.remove(0);
            return palautettava;
        }
    }
    
    public boolean onkoPutkessa() {
        if ((this.putki != null)) {
            if (this.putki.isEmpty()) {
                return false;
            } else return true;
        } else return false;
    }
}
