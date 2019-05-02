
import java.util.Scanner;

public class NollastaLukuun {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        int luku = Integer.valueOf(lukija.nextLine());
        int kasvava = 0;
        while (kasvava <= luku) {
            System.out.println(kasvava);
            kasvava++;
        }

    }
}
