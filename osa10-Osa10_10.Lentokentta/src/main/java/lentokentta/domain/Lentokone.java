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
public class Lentokone {
    private String tunnus;
    private int kapasiteetti;
    
    public Lentokone(String tunnus, int kapasiteetti) {
        this.tunnus = tunnus;
        this.kapasiteetti = kapasiteetti;
    }
    
    public String getTunnus() {
        return this.tunnus;
    }
    
    @Override
    public String toString() {
        return this.tunnus +" (" + this.kapasiteetti + " henkilöä)";
    }
    
}
