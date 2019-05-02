
import java.util.Scanner;

public class Tarina {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);

        // toteuta ohjelma tänne
        System.out.println("Kerron kohta tarinan, mutta tarvitsen siihen hieman tietoja."
                + "\nMinkä niminen tarinassa esiintyvä hahmo on?");
        String hahmo = lukija.nextLine();
        System.out.println("Mikä hahmon ammatti on?");
        String ammatti = lukija.nextLine();
        System.out.println("Tässä tarina:");
        System.out.println("Olipa kerran " + hahmo + ", joka oli ammatiltaan " + ammatti + ".");
        System.out.println("Matkatessaan töihin, " + hahmo + " mietti arkeaan. Kun työnä");
        System.out.println("on " + ammatti + ", tekemistä riittää välillä hyvin paljon ja");
        System.out.println("välillä ei lainkaan. Ehkäpä " + hahmo + " ei olekaan koko");
        System.out.println("elämäänsä " + ammatti + ".");
        
        

    }
}
