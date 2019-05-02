
import java.util.Scanner;

public class SamatSanat {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);

        // Toteuta ohjelmasi tähän. 
        System.out.println("Syötä ensimmäinen merkkijono:");
        String eka = lukija.nextLine();
        System.out.println("Syötä toinen merkkijono:");
        String toka = lukija.nextLine();
        if (eka.equals(toka)) {
            System.out.println("Samat sanat");
        } else {
            System.out.println("Ei sitten");
        }
    }
}
