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
public class MaksimipainollinenLaatikko extends Laatikko {
    
    private int maksimipaino;
    
    public MaksimipainollinenLaatikko(int maksimipaino) {
        this.maksimipaino = maksimipaino;
        this.tavarat = new ArrayList<>();
    }
    
    public int sisallonPaino() {
        int sisallonPaino = 0;
        for (int i = 0; i < tavarat.size(); i++) {
            sisallonPaino += tavarat.get(i).getPaino();
        }
        return sisallonPaino;
    }
    
    
    
    @Override
    public void lisaa(Tavara tavara) {
        if (!(sisallonPaino() + tavara.getPaino() > this.maksimipaino)) {
            tavarat.add(tavara);
        }
    }
    
    @Override
    public boolean onkoLaatikossa(Tavara tavara) {
    for (int i = 0; i < tavarat.size(); i++) {
            if (tavara.equals(tavarat.get(i))) return true;
        }
        return false;
    }
    
}
