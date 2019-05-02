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
    private int sivumaara;
    private int vuosi;
    
    public Kirja(String nimi, int sivuja, int vuosi) {
        this.nimi = nimi;
        this.sivumaara = sivuja;
        this.vuosi = vuosi;
    }
    
    public String getNimi() {
        return nimi;
    }
    public int getSivumaara() {
        return sivumaara;
    }
    public int getVuosi() {
        return vuosi;
    }
    public void printKaikki() {
        System.out.println(nimi + ", " + sivumaara + " sivua, " + 
                vuosi);
    }
    
    
    
}
