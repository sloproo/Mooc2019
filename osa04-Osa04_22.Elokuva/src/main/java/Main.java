
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        // Tämä on vain tyhjä main-metodi jossa voit kokeilla
        // Elokuva-luokkaasi. Kokeile esim:

        Elokuva chipmunks = new Elokuva("Alvin and the Chipmunks: The Squeakquel", 18);
//
        Scanner lukija = new Scanner(System.in);
//
        System.out.println("Minkä ikäinen olet?");
        int ika = Integer.valueOf(lukija.nextLine());
//
        System.out.println();
        if (ika >= chipmunks.ikaraja()) {
            System.out.println("Saat katsoa elokuvan " + chipmunks.nimi());
        } else {
            System.out.println("Et saa katsoa elokuvaa " + chipmunks.nimi());
        }
    }
}
