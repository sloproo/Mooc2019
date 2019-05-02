
import java.util.Scanner;

public class SanatRiveittain {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        while (true) {
            String luettu = lukija.nextLine();
            if (luettu.equals("")) {
                break;
            }
            String palaset[] = luettu.split("\\s+");
            for (String sana: palaset) {
                System.out.println(sana);
            }
        }


    }
}
