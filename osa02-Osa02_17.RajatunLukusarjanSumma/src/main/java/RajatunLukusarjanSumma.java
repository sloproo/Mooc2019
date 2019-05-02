
import java.util.Scanner;

public class RajatunLukusarjanSumma {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        System.out.println("Ensimmäinen: ");
        int mista = Integer.valueOf(lukija.nextLine());
        System.out.println("Viimeinen: ");
        int mihin = Integer.valueOf(lukija.nextLine());
        int kasvaja = mista;
        while (mista < mihin) {
            mista = mista + 1;
            kasvaja = kasvaja + mista;
        }
        System.out.println("Summa on " + kasvaja);
        

    }
}
