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
import java.util.ArrayList;

public class Maatila implements Eleleva {
    private String omistaja;
    private Navetta navetta;
    private ArrayList<Lehma> lehmat;

    public Maatila(String omistaja, Navetta navetta) {
        this.omistaja = omistaja;
        this.navetta = navetta;
        this.lehmat = new ArrayList<>();
    }
    
    public void lisaaLehma(Lehma lehma) {
        this.lehmat.add(lehma);
    }
    
    public String getOmistaja() {
        return this.omistaja;
    }
    
    public String getLehmat() {
        if (this.lehmat.isEmpty()) return "Ei lehmiä.";
        else {
            /* Oma toteutus
            String lehmatString = "";
            for (Lehma l: lehmat) {
                lehmatString += l.toString() + "\n";
            }
            */

            //virtana by NetBeans
            String lehmatString = "";
            lehmatString = lehmat.stream().map((l) -> l.toString() + "\n").reduce(lehmatString, String::concat);
            
            return "Lehmät: \n" + lehmatString;
        }
    }
    
    
    
    @Override
    public String toString() {
        return "Maatilan omistaja: " + this.omistaja + "\nNavetan maitosäiliö: "
                + this.navetta.getMaitosailio().toString() + "\n" + 
                this.getLehmat();
    }
    
    @Override
    public void eleleTunti() {
        lehmat.stream().forEach(l -> l.eleleTunti());
    }

    public void asennaNavettaanLypsyrobotti(Lypsyrobotti robo) {
        this.navetta.asennaLypsyrobotti(robo);
    }

    public void hoidaLehmat() {
        this.lehmat.stream().forEach(l -> this.navetta.hoida(l));
    }
    
    
    
    
    
}
