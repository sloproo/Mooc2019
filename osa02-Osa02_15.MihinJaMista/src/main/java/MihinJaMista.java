
import java.util.Scanner;

public class MihinJaMista {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);

        // KIRJOITA OHJELMASI TÄNNE
        System.out.println("Mihin asti?");
        int mihin = Integer.valueOf(lukija.nextLine());
        System.out.println("Mistä lähtien?");
        int mista = Integer.valueOf(lukija.nextLine());
        while (mista <= mihin) {
            System.out.println(mista);
            mista++;
        }
        
    }
}
