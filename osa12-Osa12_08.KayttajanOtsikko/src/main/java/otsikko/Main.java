package otsikko;

import java.util.Scanner;
import javafx.application.Application;

public class Main {

    public static void main(String[] args) {
        System.out.println("Voisitko olla niin ystävällinen, että antaisit "
                + "luotavan ikkunan otsikon, kiitos? ");
        String ikkunanNimi = new Scanner(System.in).nextLine();
        Application.launch(KayttajanOtsikko.class, "--title=" + ikkunanNimi);
    }

}
