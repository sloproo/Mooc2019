
import java.util.Scanner;

public class SekunnitVuorokaudessa {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);

        // toteuta ohjelma tänne
        System.out.println("Kuinka monen vuorokauden sekunnit tulostetaan?");
        int dayz = Integer.valueOf(lukija.nextLine());
        System.out.println(dayz * 3600 * 24);

    }
}
