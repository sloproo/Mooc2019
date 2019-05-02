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

public class Lastiruuma {
    private int maxpaino;
    private ArrayList<Matkalaukku> sisalto;
    
    public Lastiruuma(int maxpaino) {
        this.sisalto = new ArrayList<>();
        this.maxpaino = maxpaino;
    }
    
    public void lisaaMatkalaukku(Matkalaukku matkis) {
        if (!(this.yhteispaino() + matkis.yhteispaino() > this.maxpaino)) {
            this.sisalto.add(matkis);
        }
    }
    
    public int yhteispaino() {
        int kokonaispaino = 0;
        int i = 0;
        while (i < this.sisalto.size()) {
            kokonaispaino = kokonaispaino + this.sisalto.get(i).yhteispaino();
            i++;
        }
        return kokonaispaino;
    }
    
    public String matkismaaraSanaksi() {
        if (this.sisalto.isEmpty()) return "ei matkalaukkuja";
        if (this.sisalto.size() == 1) return "1 matkalaukku";
        if (this.sisalto.size() >= 2) return this.sisalto.size() + " matkalaukkua";
        else return "no ohhoh";        
    }
    
    public void tulostaTavarat() {
        int i = 0;
        while (i < this.sisalto.size()) {
            this.sisalto.get(i).tulostaTavarat();
            i++;
        }
    }
    
    @Override
    public String toString() {
        return this.matkismaaraSanaksi() + " (" + this.yhteispaino() + " kg)";
    }
    
    
    
    
    
}
