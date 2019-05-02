
import java.util.Scanner;

public class Totuusarvo {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);

        // toteuta ohjelma tänne
        System.out.println("Syötä jotain!");
        boolean totuusarvo = Boolean.valueOf(lukija.nextLine());
        System.out.println("Totta vaiko ei? " + totuusarvo);

    }
}
