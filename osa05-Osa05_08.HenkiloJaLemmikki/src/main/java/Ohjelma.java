
import java.util.Scanner;

public class Ohjelma {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);

        Lemmikki hulda = new Lemmikki("Hulda", "Sekarotuinen koira");
        Henkilo leevi = new Henkilo("Leevi", hulda);

        System.out.println(leevi);
    }
}
