
import java.util.ArrayList;

public class Ohjelma {

    public static void main(String[] args) {
        Lottorivi rivi = new Lottorivi();
        ArrayList<Integer> lottonumerot = rivi.numerot();

        System.out.println("Lottonumerot:");
        for (Integer numero : lottonumerot) {
            System.out.print(numero + " ");
        }
        System.out.println("");
    }
}
