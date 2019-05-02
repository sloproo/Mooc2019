/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pyry
 */
public class CDLevy implements Talletettava{
    private String artisti;
    private String nimi;
    private int julkaisuvuosi;
    
    public CDLevy(String artisti, String nimi, int julkaisuvuosi) {
        this.artisti = artisti;
        this.nimi = nimi;
        this.julkaisuvuosi = julkaisuvuosi;
    }
    
    public double paino() {
        return 0.1;
    }
    
    @Override
    public String toString() {
        return this.artisti + ": " + this.nimi + " (" + this.julkaisuvuosi + ")";
    }
    
    
}
