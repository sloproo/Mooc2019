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

public class Muutoshistoria {
    private ArrayList<Double> historia;
    
    public Muutoshistoria() {
        this.historia = new ArrayList<>();
    }
    
    public void lisaa(double tilanne) {
        this.historia.add(tilanne);
    }
    
    public void nollaa() {
        this.historia.clear();
    }
    
    public double maxArvo() {
        if (this.historia.isEmpty()) return 0.0;
        double suurin = this.historia.get(0);
        for (int i = 1; i < this.historia.size(); i++) {
            if (this.historia.get(i) > suurin) suurin = this.historia.get(i);
        }
        return suurin;
    }
    
    public double minArvo() {
        if (this.historia.isEmpty()) return 0.0;
        Double pienin = this.historia.get(0);
        for (int i = 1; i < this.historia.size(); i++) {
            if (this.historia.get(i) < pienin) pienin = this.historia.get(i);
        }
        return pienin;
    }
    
    public double keskiarvo() {
        if (this.historia.isEmpty()) return 0.0;
        Double summa = 0.0;
        for (int i = 0; i < this.historia.size(); i++) {
            summa += this.historia.get(i);
        }
        return summa / this.historia.size();
    }
    
    @Override
    public String toString() {
        String palautus = "";
        for (int i = 0; i < this.historia.size(); i++) {
            palautus += this.historia.get(i);
            if (!(i == this.historia.size() -1)) palautus += ", ";
        }
        return "[" + palautus + "]";
        //return String.valueOf(this.historia);
    }
    
}
