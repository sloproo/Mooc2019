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
public class HukkaavaLaatikko extends Laatikko {
    
    public HukkaavaLaatikko() {
        this.tavarat = new ArrayList<>();
    }
    
    @Override
    public boolean onkoLaatikossa(Tavara tavara) {
            return false;
    }
    
    @Override
    public void lisaa(Tavara tavara) {
        this.tavarat.add(tavara);
    }
    
    
    
    
    
}
