/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Pyry
 * @param <K>
 * @param <V>
 */
public class Pari<K,V> {
    private K avain;
    private V arvo;
    
    public Pari(K avain, V arvo) {
        this.avain = avain;
        this.arvo = arvo;
    }

    public K getAvain() {
        return avain;
    }

    public V getArvo() {
        return arvo;
    }

    public void setArvo(V arvo) {
        this.arvo = arvo;
    }
    
    
    
    
    
    
}