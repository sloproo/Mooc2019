
import java.io.File;
import java.util.Scanner;

public class KysytynTiedostonTulostaminen {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        String luettavatiedosto = lukija.nextLine();
        try (Scanner tiedostonLukija = new Scanner(new File(luettavatiedosto))) {
            while (tiedostonLukija.hasNextLine()) {
                System.out.println(tiedostonLukija.nextLine());
            }
        } catch (Exception e) {
                System.out.println("Virhe: " + e.getMessage());
                }

    }
}
