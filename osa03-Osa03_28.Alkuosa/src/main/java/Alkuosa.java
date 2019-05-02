
import java.util.Scanner;

public class Alkuosa {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        System.out.print("Anna sana: ");
        String materiaali = lukija.nextLine();
        System.out.print("Alkuosan pituus: ");
        int alunpituus = Integer.valueOf(lukija.nextLine());
        if (alunpituus > materiaali.length()) {
            System.out.println("Liian lyhyt sana");
        }else {
            System.out.println("Tulos: " + materiaali.substring(0, alunpituus));
        }
    }
}
