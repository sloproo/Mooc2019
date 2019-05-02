/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pyry
 */
public class Asevelvollinen implements Palvelusvelvollinen{
    private int aamuja;
    
    public Asevelvollinen(int palveluksenPituus) {
        this.aamuja = palveluksenPituus;
    }
    
    public int paiviaJaljella() {
        return this.aamuja;
    }
    
    public void palvele() {
        if (this.aamuja >= 1) this.aamuja -= 1;
    }
    
    
    
}
