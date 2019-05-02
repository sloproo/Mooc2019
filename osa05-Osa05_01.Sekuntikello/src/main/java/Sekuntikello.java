/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pyry
 */
public class Sekuntikello {
    private Viisari sadasosat;
    private Viisari sekuntit;
    
    public Sekuntikello() {
        this.sadasosat = new Viisari(100);
        this.sekuntit = new Viisari(60);
    }
    
    public String toString() {
        return sekuntit + ":" + sadasosat;
    }
    
    public void etene() {
        this.sadasosat.etene();
        if (this.sadasosat.arvo() == 0) {
            this.sekuntit.etene();
        }
        
    }
    
    }
