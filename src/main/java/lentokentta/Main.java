package lentokentta;
import lentokentta.logiikka.Hallinta;
import lentokentta.ui.Ui;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        // Kirjoita pääohjelma tänne. Omien luokkien tekeminen on hyödyllistä.
        Scanner lukija = new Scanner(System.in);
        Ui ui = new Ui(new Hallinta(), lukija);
        System.out.println("Ihan random muutos vaan");
        ui.kaynnista();
    }
}
