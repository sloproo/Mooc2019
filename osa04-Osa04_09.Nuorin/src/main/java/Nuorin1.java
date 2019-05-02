
import java.util.Scanner;

public class Nuorin1 {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        int nuorinika = 1000000;
        String nuorimmannimi = "";
        while (true) {
            String luetturivi = lukija.nextLine();
            if (luetturivi.equals("")) {
                break;
            }
            String[] palat = luetturivi.split(",");
            if (Integer.valueOf(palat[1]) < nuorinika) {
                nuorinika = Integer.valueOf(palat[1]);
                nuorimmannimi = palat[0];
            }
            
            
        }
        System.out.println("Nuorin oli: " + nuorimmannimi);

    }
}
