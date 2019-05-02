
import java.util.Scanner;

public class SanaSanassa {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        System.out.print("Anna 1. sana: ");
        String eka = lukija.nextLine();
        System.out.print("Anna 2. sana: ");
        String toka = lukija.nextLine();
        if (eka.indexOf(toka) != -1) {
            System.out.println("Sana '" + toka + "' on sanan '" + eka + "' osana.");
        } else {
            System.out.println("Sana '" + toka + "' ei ole sanan '" + eka + "' osana.");
        }
    }
}
