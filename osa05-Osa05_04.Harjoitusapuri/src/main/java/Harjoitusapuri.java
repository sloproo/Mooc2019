/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pyry
 */
public class Harjoitusapuri {
    private int ika;
    private int leposyke;
    
    public Harjoitusapuri(int ika, int leposyke) {
        this.ika = ika;
        this. leposyke = leposyke;
    }
    
    public double tavoitesyke(double prosenttiaMaksimista) {
        return (this.maksimisyke() - this.leposyke) * (prosenttiaMaksimista) + 
                this.leposyke;
    }
                
    
    public double maksimisyke() {
        return (206.3 - (0.711 * this.ika));
    }
    
    
    
}
