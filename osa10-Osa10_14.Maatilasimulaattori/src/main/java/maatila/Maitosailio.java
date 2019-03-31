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
import java.lang.Math;

public class Maitosailio {
    private double tilavuus;
    private double saldo;
    
    public Maitosailio() {
        this.tilavuus = 2000.0;
        this.saldo = 0;
    }
    
    public Maitosailio(double tilavuus) {
        this.tilavuus = tilavuus;
        this.saldo = 0;
    }

    public double getTilavuus() {
        return tilavuus;
    }

    public double getSaldo() {
        return saldo;
    }
    
    public double paljonkoTilaaJaljella() {
        return tilavuus - saldo;
    }
    
    public void lisaaSailioon(double maara) {
        this.saldo = this.saldo + maara;
        if (this.saldo > this.tilavuus) this.saldo = this.tilavuus;
    }
    
    public double otaSailiosta(double maara) {
        if (maara < this.saldo) {
            this.saldo -= maara;
            return maara;
        } else {
            this.saldo = 0;
            return this.saldo;
        }
    }
    
    @Override
    public String toString() {
        return Math.ceil(this.saldo) + "/" + Math.ceil(this.tilavuus);
    }
}
