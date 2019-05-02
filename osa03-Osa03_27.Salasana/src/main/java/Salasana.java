
import java.util.Scanner;

public class Salasana {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        String salasana = "porkkana"; // käytä porkkanaa salasanana testejä ajaessasi!

        // Toteuta ohjelmasi tähän.
        while (true) {
            System.out.print("Anna salasana: ");
            if (salasana.equals(lukija.nextLine())) {
                System.out.println("Oikein!");
                System.out.println("");
                System.out.println("Salaisuus on: znvavbfgv grugl!");
                break;
            } else {
                System.out.println("Väärin!");
            }
        }
        
            
        
    }
}
