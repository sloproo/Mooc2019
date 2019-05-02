
import java.util.Arrays;


public class Paaohjelma {

    public static void main(String[] args) {
        // tee testikoodia tänne
        
    }
    
    public static int pienin(int[] taulukko) {
        int pieninluku = taulukko[0];
        int i = 1;
        while (i < taulukko.length) {
            if (taulukko[i] < pieninluku) pieninluku = taulukko[i];
            i++;
        }
        return pieninluku;
    }
    
    public static int pienimmanIndeksi(int[] taulukko) {
        int pienimmanIndeksi = 0;
        int i = 1;
        while (i < taulukko.length) {
            if (taulukko[i] < taulukko[pienimmanIndeksi]) pienimmanIndeksi = i;
            i++;
        }
        return pienimmanIndeksi;
    }
    
    public static int pienimmanIndeksiAlkaen(int[] taulukko, int aloitusIndeksi) {
        int pienimmanIndeksi = aloitusIndeksi;
        int i = aloitusIndeksi + 1;
        while (i < taulukko.length) {
            if (taulukko[i] < taulukko[pienimmanIndeksi]) pienimmanIndeksi = i;
            i++;
        }
        return pienimmanIndeksi;
    }
    
    public static void vaihda(int[] taulukko, int indeksi1, int indeksi2) {
        int kakkoseen = taulukko[indeksi1];
        taulukko[indeksi1] = taulukko[indeksi2];
        taulukko[indeksi2] = kakkoseen;
    }
    
    public static void jarjesta(int[] taulukko) {
        int i = 0;
        System.out.println(Arrays.toString(taulukko));
        while (i < taulukko.length) {
            Paaohjelma.vaihda(taulukko, Paaohjelma.pienimmanIndeksiAlkaen(taulukko, i), i);
            System.out.println(Arrays.toString(taulukko));
            i++;
        }
        
    }
    
    

}
