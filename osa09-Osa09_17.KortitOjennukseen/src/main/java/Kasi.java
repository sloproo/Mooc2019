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
import java.util.stream.IntStream;
import java.util.Collections;

public class Kasi implements Comparable<Kasi> {
    private ArrayList<Kortti> kasi;
    
    public Kasi() {
        this.kasi = new ArrayList<>();
    }
    
    public void lisaa(Kortti kortti) {
        this.kasi.add(kortti);
    }
    
    public ArrayList<Kortti> getKasi() {
        return this.kasi;
    }
    
    public void tulosta() {
        this.kasi.stream().forEach(kortti -> System.out.println(kortti));
    }
    
    /*public void jarjesta() {
        Kasi jarjestelyTemp = new Kasi();
        this.kasi.stream().sorted().forEach(k -> jarjestelyTemp.lisaa(k));
        this.kasi.clear();
        jarjestelyTemp.getKasi().stream().forEach(k -> this.kasi.add(k));
    }
    */
    
    public void jarjesta() {
        Collections.sort(this.kasi);
    }
    
    public int getSumma() {
        int summa = 0;
        return (this.kasi.stream().mapToInt(k -> k.getArvo()).sum());
    }
    
    public void jarjestaMaittain() {
        Collections.sort(this.kasi, new SamatMaatVierekkainArvojarjestykseen());
    }
    
    @Override
    public int compareTo(Kasi kasi) {
        return this.getSumma() - kasi.getSumma();
    }
    
}
