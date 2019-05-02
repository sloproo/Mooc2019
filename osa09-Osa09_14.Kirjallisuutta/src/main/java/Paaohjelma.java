
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Paaohjelma {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        List<Kirja> kirjat = new ArrayList<>();
        while (true) {
            System.out.print("Syötä kirjan nimi, tyhjä lopettaa: ");
            String syoteNimi = lukija.nextLine();
            if (syoteNimi.equals("")) break;
            System.out.print("Syötä kirjan pienin kohdeikä: ");
            int syoteIka = Integer.valueOf(lukija.nextLine());
            kirjat.add(new Kirja(syoteNimi, syoteIka));
            System.out.println("");
        }
        
        System.out.println("Yhteensä " + kirjat.size() + " kirjaa.");
        System.out.println("");
        System.out.println("Kirjat:");
        
        Comparator<Kirja> kirjavertailu = Comparator.comparing(Kirja::getAlaika)
                .thenComparing(Kirja::getNimi);
        
        Collections.sort(kirjat, kirjavertailu);
        kirjat.stream().forEach(k -> System.out.println(k));
    }
        

    

}
