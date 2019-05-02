
import java.util.Scanner;

public class YkkostenLukumaara {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        int ykkosia = 0;
        while (true) {
            int sisaan = Integer.valueOf(lukija.nextLine());
            if (sisaan == 0){
                break;
            } else if (sisaan == 1) {
                ykkosia = ykkosia + 1;
            }
        }
        System.out.println(ykkosia);

    }
}
