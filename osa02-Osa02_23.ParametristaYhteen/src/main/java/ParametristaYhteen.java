
import java.util.Scanner;

public class ParametristaYhteen {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        System.out.println("Anna luku:");
        tulostaLuvustaYhteen(Integer.valueOf(lukija.nextLine()));

    }
    public static void tulostaLuvustaYhteen(int lahto) {
        while (lahto >= 1) {
            System.out.println(lahto);
            lahto = lahto - 1;
        }
    }

}
