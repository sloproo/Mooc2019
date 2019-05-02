
import java.util.Scanner;

public class IanTarkistus {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        System.out.println("Kuinka vanha olet?");
        int ika = Integer.valueOf(lukija.nextLine());
        if (ika >= 0 && ika < 121) {
            System.out.println("OK");
        } else System.out.println("Mahdotonta!");

    }
}
