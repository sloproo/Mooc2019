/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pyry
 * Kuin hienoa olisi jos joku lukisi näitä. Jos lukee, niin XOXO sinulle <3!
 */
import java.util.ArrayList;

public class Matkalaukku {
    private int maxpaino;
    private ArrayList<Tavara> sisalto;
    
    public Matkalaukku(int maxpaino) {
        this.sisalto = new ArrayList<>();
        this.maxpaino = maxpaino;
    }
    
    public void lisaaTavara(Tavara tavara) {
        if (!(this.yhteispaino() + tavara.getPaino() > this.maxpaino)) {
            this.sisalto.add(tavara);
        }
    }
    
    public int yhteispaino() {
        int kokonaispaino = 0;
        int i = 0;
        while (i < this.sisalto.size()) {
            kokonaispaino = kokonaispaino + this.sisalto.get(i).getPaino();
            i++;
        }
        return kokonaispaino;
    }
    
    public String tavaramaaraSanaksi() {
        if (this.sisalto.isEmpty()) return "ei tavaroita";
        if (this.sisalto.size() == 1) return "1 tavara";
        if (this.sisalto.size() >= 2) return this.sisalto.size() + " tavaraa";
        else return "no ohhoh";        
    }
    
    public void tulostaTavarat() {
        int i = 0;
        while (i < this.sisalto.size()) {
            System.out.println(this.sisalto.get(i));
            i++;
        }
    }
    
    public Tavara raskainTavara() {
        int raskaimmantavaranindeksi = 0;
        int i = 0;
        if (this.sisalto.isEmpty()) return null;
        if (this.sisalto.size() == 1) return this.sisalto.get(0);
        while (i < this.sisalto.size()) {
            if (this.sisalto.get(i).getPaino() > 
                    this.sisalto.get(raskaimmantavaranindeksi).getPaino()) {
                raskaimmantavaranindeksi = i;
            }
            i++;
        }
    return this.sisalto.get(raskaimmantavaranindeksi);
    }

    @Override
    public String toString() {
        return this.tavaramaaraSanaksi() + " (" + this.yhteispaino() + " kg)";
    }
    
    
}
