
import java.io.File;
import java.util.Scanner;

public class LoytyykoTiedostosta {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);

        System.out.println("Minkä niminen tiedosto luetaan?");
        String tiedosto = lukija.nextLine();

        System.out.println("Mitä etsitään?");
        String etsittava = lukija.nextLine();
        boolean loytynyt = false;
        try (Scanner tiedostonLukija = new Scanner (new File(tiedosto))) {
            while (tiedostonLukija.hasNextLine()) {
                String luetturivi = tiedostonLukija.nextLine();
                if (luetturivi.equals(etsittava)) {
                    loytynyt = true;
                    System.out.println("Löytyi!");
                    break;
                }
            }
            if (!loytynyt) {
                System.out.println("Ei löytynyt.");
            }
        } catch (Exception e) {
            System.out.println("Tiedoston " + tiedosto + " lukeminen epäonnistui.");
        }

    }
}
