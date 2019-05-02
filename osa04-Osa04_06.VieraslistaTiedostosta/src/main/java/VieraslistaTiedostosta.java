
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class VieraslistaTiedostosta {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);

        System.out.println("Minkä niminen tiedosto luetaan? ");
        String tiedosto = lukija.nextLine();

        ArrayList<String> lista = new ArrayList<>();
        // toteuta tiedoston lukeminen tässä.
        try (Scanner tiedostonLukija = new Scanner(new File(tiedosto))) {
            while (tiedostonLukija.hasNextLine()) {
                lista.add(tiedostonLukija.nextLine());
            } 
            
        } catch (Exception e) {
                System.out.println("Virhe: " + e.getMessage());
        }
        System.out.println("");
        

        System.out.println("Syötä nimiä, tyhjä rivi lopettaa.");
        while (true) {
            String nimi = lukija.nextLine();
            if (nimi.isEmpty()) {
                break;
            }

            if (lista.contains(nimi)) {
                System.out.println("Nimi on listalla.");
            } else {
                System.out.println("Nimi ei ole listalla.");
            }
        }

        System.out.println("Kiitos!");
    }
}
