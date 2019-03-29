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
public class Paikka {
    private String tunnus;
    
    public Paikka(String tunnus) {
        this.tunnus = tunnus.trim();
    }
    
    @Override
    public String toString() {
        return this.tunnus;
    }
}
