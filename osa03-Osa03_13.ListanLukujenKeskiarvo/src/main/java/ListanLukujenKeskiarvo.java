
import java.util.ArrayList;
import java.util.Scanner;

public class ListanLukujenKeskiarvo {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);

        // toteuta tänne ohjelma, joka ensin lukee käyttäjältä
        // lukuja listalle kunnes käyttäjä syöttää luvun -1.
        // ohjelma laskee tämän jälkeen listalla olevien lukujen
        // keskiarvon, ja tulostaa sen käyttäjän näkyville
        ArrayList<Integer> luvut = new ArrayList <>();
        while (true) {
            int ota = Integer.valueOf(lukija.nextLine());
            if (ota == -1) {
                break;
            } 
            luvut.add(ota);
        }
        int summa = 0;
        for (int luku: luvut) {
            summa = summa + luku;
        }
        System.out.println("Keskiarvo: " + (1.0  * summa / luvut.size()));
        
    }
}
