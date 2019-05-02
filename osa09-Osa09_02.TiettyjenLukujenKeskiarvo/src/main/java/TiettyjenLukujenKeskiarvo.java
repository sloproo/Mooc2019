
import java.util.ArrayList;
import java.util.Scanner;

public class TiettyjenLukujenKeskiarvo {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        // toteuta ohjelmasi tänne
        ArrayList<String> luettu = new ArrayList<>();
        while (true) {
            String syote = lukija.nextLine();
            if (syote.equals("")) continue;
            if (syote.equals("loppu")) break;
            luettu.add(syote);
        }
        String negavaiposi = "";
        while (!((negavaiposi.equals("p")) || negavaiposi.equals("n"))) {
            System.out.println("Tulostetaanko negatiivisten vai positiivisten"
                + "lukujen keskiarvo? (n/p)");
            negavaiposi = lukija.nextLine();
        }
        
        if (negavaiposi.equals("n")) {
            double keskiarvo = luettu.stream()
                .mapToInt(s -> Integer.valueOf(s))
                .filter(luku -> luku <= 0)
                .average()
                .getAsDouble();
            System.out.println("Negatiivisten lukujen keskiarvo: " + keskiarvo);
        }
        
        if (negavaiposi.equals("p")) {
            double keskiarvo = luettu.stream()
                .mapToInt(s -> Integer.valueOf(s))
                .filter(luku -> luku >= 0)
                .average()
                .getAsDouble();
            System.out.println("Positiivisten lukujen keskiarvo: " + keskiarvo);
        }
        
        

    }
}
