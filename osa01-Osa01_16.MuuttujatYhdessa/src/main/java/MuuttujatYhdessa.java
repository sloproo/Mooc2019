
import java.util.Scanner;

public class MuuttujatYhdessa {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);

        // toteuta ohjelma tänne
        System.out.println("Syötä merkkijono!");
        String merkit = lukija.nextLine();
        System.out.println("Syötä kokonaisluku!");
        int kokonaisl = Integer.valueOf(lukija.nextLine());
        System.out.println("Syötä liukuluku!");
        double liukuri = Double.valueOf(lukija.nextLine());
        System.out.println("Syötä totuusarvo!");
        boolean totuus = Boolean.valueOf(lukija.nextLine());
        System.out.println("Syötit merkkijonon " + merkit);
        System.out.println("Syötit kokonaisluvun " + kokonaisl);
        System.out.println("Syötit liukuluvun " + liukuri);
        System.out.println("Syötit totuusarvon " + totuus);
        

    }
}
