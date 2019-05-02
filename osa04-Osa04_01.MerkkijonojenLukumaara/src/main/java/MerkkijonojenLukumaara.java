
import java.util.ArrayList;
import java.util.Scanner;

public class MerkkijonojenLukumaara {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        int luettuja = 0;
        while (true) {
            String luettu = lukija.nextLine();
            if (luettu.equals("loppu")) {
                break;
            }
            luettuja = luettuja + 1;
        }
        System.out.println(luettuja);
    }
}
