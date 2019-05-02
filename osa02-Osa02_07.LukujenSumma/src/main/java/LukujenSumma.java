
import java.util.Scanner;

public class LukujenSumma {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        int summa = 0;
        while (true) {
            int syote = Integer.valueOf(lukija.nextLine());
            if (syote == 0) {
                break;
            } else summa = (summa + syote);
        }
        System.out.println(summa);

    }
}
