/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pyry
 */
public class Henkilo {
    private String nimi;
    private int ika;
    
    public Henkilo (String nimi) {
        this.nimi = nimi;
        this.ika = 0;
    }
    
    public void tulostaHenkilo() {
        System.out.println(this.nimi + ", ikä " + this.ika + " vuotta");
    }
    
    public void vanhene() {
        this.ika = this.ika + 1;
    }
    
    public int palautaIka() {
        return this.ika;
    }
}
