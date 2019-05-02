
import java.io.File;
import java.util.Scanner;

public class MittauksetTiedostosta {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);

        System.out.print("Tiedosto? ");
        String tiedosto = lukija.nextLine();
        System.out.print("Alaraja? ");
        int alaraja = Integer.valueOf(lukija.nextLine());
        System.out.print("Yläraja? ");
        int ylaraja = Integer.valueOf(lukija.nextLine());
        int loytyneita = 0;
        
        try (Scanner tiedostonLukija = new Scanner(new File (tiedosto))) {
            while (tiedostonLukija.hasNextLine()) {
                int rivi = Integer.valueOf(tiedostonLukija.nextLine());
                if (rivi >= alaraja && ylaraja >= rivi) {
                    loytyneita = loytyneita + 1;
                }
            }
        } catch (Exception e) {
            System.out.println("Virhe: " + e);
        }
        System.out.println("Lukuja: " + loytyneita);

    }

}
