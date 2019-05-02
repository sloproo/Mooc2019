/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pyry
 */
public class Sailio {
    private int sisalto;
    
    public Sailio() {
        this.sisalto = 0;
    }
    
    public int sisalto() {
        return this.sisalto;
    }
    
    public void lisaa (int maara){
        if (maara < 0) maara = 0;
        this.sisalto = this.sisalto + maara;
        this.eiYliTaiAli();
    }
    
    public void poista(int maara) {
        if (maara < 0) maara = 0;
        this.sisalto = this.sisalto - maara;
        this.eiYliTaiAli();
    }
    
    public String toString() {
        return this.sisalto + "/100";
    }
    
    
    
    public void eiYliTaiAli() {
        if (this.sisalto > 100 ) this.sisalto = 100;
        if (this.sisalto < 0 ) this.sisalto = 0;
    }
}
