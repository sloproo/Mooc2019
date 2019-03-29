/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lentokentta.domain;

/**
 *
 * @author Pyry
 */
public class Lento {
    private Lentokone lentokone;
    private Paikka lahto;
    private Paikka kohde;
    
    public Lento(Lentokone kone, Paikka lahtopaikka, Paikka kohdepaikka) {
        this.lentokone = kone;
        this.lahto = lahtopaikka;
        this.kohde = kohdepaikka;
    }
    
    @Override
    public String toString() {
        return this.lentokone.toString() + " (" + this.lahto.toString() + "-" 
                + this.kohde.toString() + ")";
    }
    
}
