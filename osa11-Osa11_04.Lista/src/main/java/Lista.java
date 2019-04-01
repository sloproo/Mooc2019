/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pyry
 * @param <T>
 */
public class Lista<T> {
    private T[] arvot;
    private int ekaTyhja;
    
    public Lista(){
        this.arvot = (T[]) new Object[10];
        this.ekaTyhja = 0;
    }
    
    public void lisaa (T arvo) {
        if (this.ekaTyhja >= this.arvot.length) {
            this.kasvata();
        }
        this.arvot[this.ekaTyhja] = arvo;
        this.ekaTyhja++;
    }
    
    private void kasvata() {
        T[] isompiLista = (T[]) new Object[(this.arvot.length + this.arvot.length / 2)];
        for (int i = 0; i < this.ekaTyhja; i++) {
            isompiLista[i] = this.arvot[i];
        }
        this.arvot = isompiLista;
    }
    
    public boolean sisaltaa(T arvo) {
        return arvonIndeksi(arvo) >= 0;
    }
    
    public int arvonIndeksi(T haettava) {
        for (int i = 0; i < this.ekaTyhja; i++) {
            if (this.arvot[i].equals(haettava)) return i;
        }
        return -1;
    }
    
    public void poista(T arvo) {
        int haetunIndeksi = arvonIndeksi(arvo);
        if (haetunIndeksi < 0) return;
        this.pakitaAlkaen(haetunIndeksi);
    }
    
    private void pakitaAlkaen(int lahto) {
        if (lahto >= ekaTyhja) throw new ArrayIndexOutOfBoundsException("Taulukon ulkopuolella");
        for (int i = lahto; i < this.ekaTyhja - 1; i++) {
            this.arvot[i] = this.arvot[i+1];
        }
        this.ekaTyhja--;
    }
    
    public T arvo(int indeksi) {
        if (indeksi >= this.ekaTyhja) throw new IndexOutOfBoundsException(
                "Liian suuri indeksi");
        else return this.arvot[indeksi];
    }
    
    public int koko() {
        return this.ekaTyhja;
    }
    
    
}
