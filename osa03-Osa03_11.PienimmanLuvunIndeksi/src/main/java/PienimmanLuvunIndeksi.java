
import java.util.ArrayList;
import java.util.Scanner;

public class PienimmanLuvunIndeksi {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);

        // toteuta tänne ohjelma, joka lukee käyttäjältä lukuja
        // kunnes käyttäjä syöttää luvun 9999
        
        // tämän jälkeen ohjelma tulostaa pienimmän luvun
        // sekä sen indeksin -- pienin luku voi esiintyä
        // useammassa indeksissä
        ArrayList<Integer> lista = new ArrayList <>();
        System.out.println("Aloitetaan lukujen vastaanotto");
        while (true) {
            int sisaan = Integer.valueOf(lukija.nextLine());
            if (sisaan == 9999) {
                break;
            } else lista.add(sisaan);
        }
        System.out.println("Luvut vastaanotettu");
        
        int i = 1;
        if (lista.isEmpty()) {
            System.out.println("Tyhjä lista.");
        } else {
                int pienin = lista.get(0);
                while ((i) < lista.size()) {
                pienin = pienempi(pienin, lista.get(i));
                i++;
                }
        System.out.println("Pienin luku on " + pienin);
        int j = 0;
        while (j < lista.size()) {
            if (lista.get(j) == pienin) {
                System.out.println("Pienin luku löytyy indeksistä " + j);
                } 
            j++;
            }
        }
                
        
    }
        
    public static Integer pienempi(int luku1, int luku2) {
        if (luku1 < luku2) {
            return luku1;
        } else return luku2;
    }
}
