/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pyry
 */
public class Kirja {
    private String nimi;
    private int alaika;
    
    public Kirja(String nimi, int alaika) {
        this.nimi = nimi;
        this.alaika = alaika;
    }

    public String getNimi() {
        return nimi;
    }

    public int getAlaika() {
        return alaika;
    }
    
    @Override
    public String toString() {
        return this.nimi + " (" + this.alaika + "-vuotiaille ja vanhemmille)";
    }
    
}
