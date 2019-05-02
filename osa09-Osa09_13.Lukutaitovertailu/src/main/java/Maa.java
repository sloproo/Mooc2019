/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pyry
 */
public class Maa implements Comparable<Maa> {
    private String maanNimi;
    private int datanVuosi;
    private String sukupuoli;
    private double lukutaitoProsentti;
    
        
    public Maa(String nimi, int vuosi, double prosentti, String sukupuoli) {
        this.maanNimi = nimi;
        this.datanVuosi = vuosi;
        this.lukutaitoProsentti = prosentti;
        this.sukupuoli = sukupuoli;
    }

    public String getMaanNimi() {
        return maanNimi;
    }

    public int getDatanVuosi() {
        return datanVuosi;
    }

    public String getSukupuoli() {
        return sukupuoli;
    }

    public double getLukutaitoProsentti() {
        return lukutaitoProsentti;
    }
    
    
    
    @Override
    public int compareTo(Maa muu) {
        if (this.lukutaitoProsentti < muu.getLukutaitoProsentti()) return -1;
        if (this.lukutaitoProsentti > muu.getLukutaitoProsentti()) return 1;
        else return 0;
    }
    
    @Override
    public String toString() {
        return this.maanNimi + " (" + this.datanVuosi + "), " + this.sukupuoli
                + ", " + this.lukutaitoProsentti;
    }
    
    
}


