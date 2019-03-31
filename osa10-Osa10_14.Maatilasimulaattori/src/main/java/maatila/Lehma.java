/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maatila;

/**
 *
 * @author Pyry
 */
import java.util.Random;
import java.lang.Math;

public class Lehma implements Eleleva, Lypsava {
    private String nimi;
    private double tilavuus;
    private double maara;
    private Random r = new Random();
    private static final String[] NIMIA = new String[]{
    "Anu", "Arpa", "Essi", "Heluna", "Hely",
    "Hento", "Hilke", "Hilsu", "Hymy", "Matti", "Ilme", "Ilo",
    "Jaana", "Jami", "Jatta", "Laku", "Liekki",
    "Mainikki", "Mella", "Mimmi", "Naatti",
    "Nina", "Nyytti", "Papu", "Pullukka", "Pulu",
    "Rima", "Soma", "Sylkki", "Valpu", "Virpi"};
    
    public Lehma() {
        this.nimi = NIMIA[r.nextInt(NIMIA.length)];
        this.tilavuus = 15 + r.nextInt(26);
        this.maara = 0;
        
    }
    
    public Lehma(String nimi) {
        this();
        this.nimi = nimi;
    }

    public String getNimi() {
        return nimi;
    }

    public double getTilavuus() {
        return tilavuus;
    }

    public double getMaara() {
        return maara;
    }
    
    @Override
    public String toString() {
        return this.nimi + " " + Math.ceil(this.maara) + "/" + this.tilavuus;
    }

    @Override
    public void eleleTunti() {
       this.maara += 0.7 + r.nextDouble()*1.3;
    }

    @Override
    public double lypsa() {
        double lypsy = this.maara;
        this.maara = 0;
        return lypsy;
    }
    
}
