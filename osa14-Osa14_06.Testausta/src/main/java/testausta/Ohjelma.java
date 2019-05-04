package testausta;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ohjelma {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        System.out.println(suorita(lukija));
    }

    public static String suorita(Scanner lukija) {
        String syote = "";
        int oppilaita = 0;
        int pulloja = 0;
        
        while (!syote.equals("-1")) {
            System.out.println("Montako pulloa oppilas keräsi? (-1 lopettaa)");
            syote = lukija.nextLine();
            if (Integer.valueOf(syote) >= 0) {
                pulloja += Integer.valueOf(syote);
                oppilaita++;
            }
        }
        
        return palautusYhteenveto(pulloja, oppilaita);
    }
    
    public static String palautusYhteenveto(int pulloja, int oppilaita) {
        String keskiarvoString = "";
        if (pulloja == 0) {
            keskiarvoString = "Keskiarvoa ei voida laskea";
        } else {
            keskiarvoString = "Keskiarvo: " + pulloja * 1.0 / oppilaita;
        }
        
        String palautettava = "Pulloja: " + pulloja + "\n"
                + "Oppilaita: " + oppilaita + "\n"
                + keskiarvoString;
        
        return palautettava;
    }
}
