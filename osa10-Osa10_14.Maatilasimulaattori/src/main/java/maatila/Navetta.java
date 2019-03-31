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
import java.util.List;

public class Navetta {
    private Maitosailio maitosailio;
    private Lypsyrobotti lypsyrobotti;
    
    public Navetta(Maitosailio maitosailio) {
        this.maitosailio = maitosailio;
    }

    public Maitosailio getMaitosailio() {
        return maitosailio;
    }
    
    public void asennaLypsyrobotti(Lypsyrobotti lypsyrobotti) {
        this.lypsyrobotti = lypsyrobotti;
        this.lypsyrobotti.setMaitosailio(this.maitosailio);
    }
    
    public void hoida(Lehma lehma) {
        if (this.lypsyrobotti==null) {
            throw new IllegalStateException("Ei "
                + "lypsyrobottia kytkettynä");
        } else this.lypsyrobotti.lypsa(lehma);
    }
    
    public void hoida(List<Lehma> lehmat) {
        lehmat.stream().forEach(l -> this.lypsyrobotti.lypsa(l));
    }
    
    @Override
    public String toString() {
        return this.maitosailio.toString();
    }
    
}
