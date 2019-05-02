
import java.util.Scanner;

public class KolmellaJaolliset {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        System.out.println("Anna luvut:");
        int mista = Integer.valueOf(lukija.nextLine());
        int mihin = Integer.valueOf(lukija.nextLine());
        kolmellaJaollisetValilta(mista, mihin);

    }
    public static void kolmellaJaollisetValilta(int luku1, int luku2) {
        while (luku1 <= luku2) {
            if (luku1 % 3 == 0) {
                System.out.println(luku1);
            }
            luku1++;
            
        }
    }

}
