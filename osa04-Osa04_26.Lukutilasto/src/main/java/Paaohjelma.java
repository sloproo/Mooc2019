
import java.util.Scanner;

public class Paaohjelma {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        // voit tehdä testikoodia tänne
        // poista kaikki ylimääräinen koodi kuitenkin tehtävän viimeisiä osia tehdessäsi

        // Jotta testi toimisi, on oliot luotava pääohjelmassa oikeassa järjestyksessä 
        //  eli ensin kaikkien summan laskeva olio, toisena parillisten summan laskeva 
        //  ja viimeisenä parittomien summan laskeva olio)!
        Lukutilasto tilasto = new Lukutilasto();
        Lukutilasto parillisia = new Lukutilasto();
        Lukutilasto parittomia = new Lukutilasto();
        System.out.println("Anna lukuja:");
        while (true) {
            int lisattava = Integer.valueOf(lukija.nextLine());
            if (lisattava == -1) {
                break;
            }
            tilasto.lisaaLuku(lisattava);
            if (lisattava % 2 == 0) {
                parillisia.lisaaLuku(lisattava);
            } else parittomia.lisaaLuku(lisattava);
        }
        
        System.out.println("Summa: " + tilasto.summa());
        System.out.println("Parillisten summa: " + parillisia.summa());
        System.out.println("Parittomien summa: " + parittomia.summa());
        
    }
}
    

