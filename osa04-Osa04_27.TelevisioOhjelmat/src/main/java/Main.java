
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // toteuta luokkaa TelevisioOhjelma käyttävä ohjelmasi tänne

        ArrayList<TelevisioOhjelma> ohjelmat = new ArrayList<>();
        Scanner lukija = new Scanner(System.in);
        String ohjelmanNimi = "";
        int ohjelmanPituus = 0;
        int maksimiPituus = 0;
        while (true) {
            System.out.print("Nimi: ");
            ohjelmanNimi = lukija.nextLine();
            if (ohjelmanNimi.equals("")) {
                break;
            } 
            System.out.print("Pituus: ");
            ohjelmanPituus = Integer.valueOf(lukija.nextLine());
            ohjelmat.add(new TelevisioOhjelma(ohjelmanNimi, ohjelmanPituus));
        }
        System.out.println("");
        System.out.print("Ohjelman maksimipituus? ");
        maksimiPituus = Integer.valueOf(lukija.nextLine());
        for (TelevisioOhjelma ohjelma: ohjelmat) {
            if (ohjelma.getPituus() <= maksimiPituus) {
                System.out.println(ohjelma.getNimi() + ", " + ohjelma.getPituus()
                        + " minuuttia");
            }
        }

    }
}
