
import java.util.Scanner;

public class PositiivistenLukujenKeskiarvo {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        int positiivisia = 0;
        int posiensumma = 0;
        while (true) {
            int sisaan = Integer.valueOf(lukija.nextLine());
            if (sisaan == 0) {
                break;
            }
            if (sisaan > 0) {
                positiivisia = positiivisia + 1;
                posiensumma = posiensumma + sisaan;
            }
        }
        if (positiivisia == 0) {
            System.out.println("keskiarvon laskeminen ei ole mahdollista");
        }else System.out.println(1.0 * posiensumma / positiivisia);

    }
}
