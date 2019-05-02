
import java.util.ArrayList;
import java.util.Scanner;

public class KysytynLuvunIndeksi {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);

        ArrayList<Integer> lista = new ArrayList<>();
        while (true) {
            int luettu = Integer.valueOf(lukija.nextLine());
            if (luettu == -1) {
                break;
            }

            lista.add(luettu);
        }

        System.out.println("");

        // toteuta tänne toiminnallisuus luvun etsimiseen
        System.out.println("Mitä etsitään? ");
        int kohde = Integer.valueOf(lukija.nextLine());
        int i = 0;
        while (i < lista.size()) {
            if (lista.get(i) == kohde) {
                System.out.println("Luku " + kohde + " on indeksissä " + i);
            }
            i++;
        }
    }
}
