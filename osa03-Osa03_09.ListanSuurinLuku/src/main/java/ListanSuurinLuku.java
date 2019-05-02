
import java.util.ArrayList;
import java.util.Scanner;

public class ListanSuurinLuku {

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

        // toteuta listan suurimman luvun selvittäminen tänne
        int i = 1;
        int suurin = lista.get(0);
        while (i <= (lista.size()-1)) {
            suurin = suurempi(suurin, lista.get(i));
            i++;
        }
        System.out.println(suurin);
    }
    
    public static int suurempi (int luku1, int luku2) {
        if (luku1 > luku2) {
            return luku1;
        } else return luku2;
    }
}
