
import java.util.Scanner;

public class Itseisarvo {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        int juuh = Integer.valueOf(lukija.nextLine());
        if (juuh < 0) {
            juuh = (juuh * -1);
        }
        System.out.println(juuh);

    }
}
