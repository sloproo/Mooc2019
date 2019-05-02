/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pyry
 */
public class Maksukortti {
    private double saldo;
    
    public Maksukortti(double alkusaldo) {
        this.saldo = alkusaldo;
    }
    
    public String toString() {
        return "Kortilla on rahaa " + saldo + " euroa";
    }
    
    public void syoEdullisesti() {
        if (this.saldo >= 2.6) {
            this.saldo = this.saldo - 2.6;
        }
        
    }
    
    public void syoMaukkaasti() {
        if (this.saldo >= 4.6) {
            this.saldo = this.saldo - 4.6;
        }
    }
    
    public void lataaRahaa(double rahamaara) {
        if (rahamaara >= 0) {
            this.saldo = this.saldo + rahamaara;
            if (this.saldo > 150) {
                this.saldo = 150;
            }
        }
    }
    
    
}
