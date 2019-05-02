
import java.util.Scanner;

public class Kertoma {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        System.out.println("Anna luku: ");
        int mihin = Integer.valueOf(lukija.nextLine());
        int i = 1;
        int kertoma = 1;
        while (i <= mihin) {
            kertoma = kertoma * i;
            i++;
        }
        System.out.println("Kertoma on " + kertoma);
        
    }
    
}
