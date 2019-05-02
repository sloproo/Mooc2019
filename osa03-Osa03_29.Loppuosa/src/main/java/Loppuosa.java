import java.util.Scanner;

public class Loppuosa {
    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        System.out.print("Anna sana: ");
        String materiaali = lukija.nextLine();
        System.out.print("Loppuosan pituus: ");
        int lopunpituus = Integer.valueOf(lukija.nextLine());
        if (lopunpituus > materiaali.length()) {
            System.out.println("Sana ei ole tarpeeksi pitkä!");
        } else {
            System.out.println("Tulos: " + materiaali.substring(materiaali.length()-lopunpituus));
        }
    }
}
