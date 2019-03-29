/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sovellus;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.List;
/**
 *
 * @author Pyry
 */
public class Keskiarvosensori implements Sensori {
    private ArrayList<Sensori> sensorit;
    private ArrayList<Integer> mittaustulokset;
    
    public Keskiarvosensori() {
        this.sensorit = new ArrayList<>();
        this.mittaustulokset = new ArrayList<>();
        
    }

    @Override
    public boolean onPaalla() {
        for (Sensori s: sensorit) {
            if (s.onPaalla() == false) return false;
        }
        return true;
    }

    @Override
    public void paalle() {
        for (Sensori s: sensorit) {
            if (s.onPaalla() == false) s.paalle();
        }
    }

    @Override
    public void poisPaalta() {
        sensorit.get(new Random().nextInt(sensorit.size())).poisPaalta();
        
    }

    @Override
    public int mittaa() {
        if (sensorit.isEmpty()) throw new IllegalStateException("Ei mittareita");
        if (this.onPaalla() == false) throw new IllegalStateException(
                "Kaikki sensorit ei päällä");
        int tulos = (int)sensorit.stream().mapToInt(s -> s.mittaa()).average().getAsDouble();
        mittaustulokset.add(tulos);
        return tulos;
    }
    
    public void lisaaSensori(Sensori lisattava) {
        sensorit.add(lisattava);
    }
    
    public List<Integer> mittaukset() {
        return (mittaustulokset);
    }
    
}
