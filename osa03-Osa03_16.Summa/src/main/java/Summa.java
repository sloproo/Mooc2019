
import java.util.ArrayList;

public class Summa {

    public static void main(String[] args) {
        // kokeile toteuttamasi metodin toimintaa täällä
    }
    public static int summa(ArrayList<Integer> luvut) {
        int summa = 0;
        for (int luku: luvut) {
            summa = summa + luku;
        }
        return summa;
    }

}
