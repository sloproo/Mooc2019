/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pyry
 */
public class Lintu {
    private String laji;
    private String latinaksi;
    private int havaintoja;
    
    public Lintu(String laji, String latinaksi) {
        this.laji = laji;
        this.latinaksi = latinaksi;
        this.havaintoja = 0;
    }
    
    public Lintu(String laji) {
            System.out.println(this.laji + " (" + latinaksi + "): " + this.havaintoja + " havaintoa");
    }
    
    public void havainto() {
        this.havaintoja = this.havaintoja + 1;
    }
    
    public String getLaji() {
        return this.laji;
    }

    @Override
    public String toString() {
        return this.laji + " (" + latinaksi + "): " + this.havaintoja + " havaintoa";
    }
    
    
    
    
}
