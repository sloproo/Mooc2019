
import java.util.Scanner;

public class Poistutaanko {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        
        while (true) {
            System.out.println("Poistutaanko?");
            String noh = lukija.nextLine();
            if (noh.equals("kyllä")) {
                break;
            }
            
        }

    }
}
