/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pyry
 */
public class Lahja {
    private String nimi;
    private int paino;
    
    public Lahja(String nimi, int paino) {
        this.nimi = nimi;
        this.paino = paino;
    }

    public String getNimi() {
        return nimi;
    }

    public int getPaino() {
        return paino;
    }

    @Override
    public String toString() {
        return this.nimi + " (" + this.paino + " kg)";
    }
    
    
    
    
}
