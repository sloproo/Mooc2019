
import java.util.Scanner;

public class YkkostenOsuus {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        int ykkosia = 0;
        int lukuja = 0;
        while (true){
            int syote = Integer.valueOf(lukija.nextLine());
            if (syote == 0){
                break;
            } else if (syote == 1) {
                ykkosia = ykkosia + 1;
            }
            lukuja = lukuja + 1;
            
        }
        if (lukuja == 0) {
            System.out.println("ykkösten osuutta ei voida laskea");
        } else System.out.println(1.0 * ykkosia / lukuja);

    }
}
