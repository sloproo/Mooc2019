/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pyry
 */
public class MuistavaTuotevarasto extends Tuotevarasto {
    private Muutoshistoria historia;
    
    public MuistavaTuotevarasto(String tuotenimi, double tilavuus, double alkuSaldo) {
        super(tuotenimi, tilavuus);
        this.historia = new Muutoshistoria();
        super.lisaaVarastoon(alkuSaldo);
        historia.lisaa(alkuSaldo);
    }
    
    public String historia() {
        return this.historia.toString();
    }
    
    @Override
    public void lisaaVarastoon(double maara) {
        super.lisaaVarastoon(maara);
        this.historia.lisaa(super.getSaldo());
    }
    
    @Override
    public double otaVarastosta(double maara) {
        double palaute = super.otaVarastosta(maara);
        this.historia.lisaa(super.getSaldo());
        return palaute;
    }
    
    public void tulostaAnalyysi() {
        System.out.println("Tuote: " + super.getNimi());
        System.out.println("Historia: " + this.historia());
        System.out.println("Suurin tuotemäärä: " + this.historia.maxArvo());
        System.out.println("Pienin tuotemäärä: " + this.historia.minArvo());
        System.out.println("Keskiarvo: " + this.historia.keskiarvo());
    }
    
    
}
