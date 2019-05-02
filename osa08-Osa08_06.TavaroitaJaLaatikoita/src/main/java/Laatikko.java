/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pyry
 */
import java.util.ArrayList;

public class Laatikko implements Talletettava {
    private ArrayList<Talletettava> sisalto;
    private double kapasiteetti;
    
    public Laatikko(double kapasiteetti) {
        this.kapasiteetti = kapasiteetti;
        this.sisalto = new ArrayList<>();
    }
    
    public double paino() {
        double paino = 0.0;
        for (Talletettava tavara: sisalto) {
            paino += tavara.paino();
        }
        return paino;
    }
    
    public void lisaa(Talletettava tavara) {
        if (!(this.paino() + tavara.paino() > this.kapasiteetti)) {
            sisalto.add(tavara);
        }
    }
    
    public String sisalto() {
        String tulostettavaSisalto = "";
        for (Talletettava tavara : this.sisalto) {
            tulostettavaSisalto += String.valueOf(tavara) + "\n"; 
        }
        return tulostettavaSisalto;
            
    }
    
    @Override
    public String toString() {
        return "Laatikko: " + this.sisalto.size() + " esinettä, paino yhteensä "
                + this.paino() + " kiloa";
    }
}
