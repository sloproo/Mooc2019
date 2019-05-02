
import java.util.Scanner;

public class KolmenLuvunKeskiarvo {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);

        // toteuta ohjelma tänne
        System.out.println("Syötä ensimmäinen luku!");
        int eka = Integer.valueOf(lukija.nextLine());
        System.out.println("Syötä toinen luku!");
        int toka = Integer.valueOf(lukija.nextLine());
        System.out.println("Syötä kolmas luku!");
        int kolmas = Integer.valueOf(lukija.nextLine());
        System.out.println("Syötettyjen lukujen keskiarvo on " + (eka + toka + kolmas) / 3.0);

    }
}
