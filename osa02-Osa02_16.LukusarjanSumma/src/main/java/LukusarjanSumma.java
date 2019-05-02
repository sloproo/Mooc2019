
import java.util.Scanner;

public class LukusarjanSumma {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        System.out.println("Mihin asti?");
        int mihin = Integer.valueOf(lukija.nextLine());
        int tulos = 0;
        int i = 1;
        while (i <= mihin) {
            tulos = (tulos + i);
            i++;
        }
        System.out.println("Summa on " + tulos);
                

    }
}
