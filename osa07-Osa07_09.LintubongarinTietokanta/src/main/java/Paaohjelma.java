
import java.util.Scanner;
import java.util.ArrayList;

public class Paaohjelma {

    public static void main(String[] args) {
        // HUOM! Älä luo ohjelmassa muita Scanner-olioita. Jos ja toivottavasti
        // kun teet muita luokkia, anna allaoleva Scanner-olio niille
        // tarvittaessa parametrina.

        Scanner lukija = new Scanner(System.in);
        ArrayList <Lintu> linnut = new ArrayList<>();
        
        while(true) {
            System.out.print("?");
            String komento = lukija.nextLine();
            if (komento.equals("Lopeta")) break;
            if (komento.equals("Lisaa")){
                System.out.print("Nimi: ");
                String nimi = lukija.nextLine();
                System.out.print("Latinankielinen nimi: ");
                String latinaksi = lukija.nextLine();
                Lintu lisattava = new Lintu(nimi, latinaksi);
                linnut.add(lisattava);
                continue;
            }
            if (komento.equals("Havainto")) {
                System.out.print("Mikä havaittu? ");
                String havaittu = lukija.nextLine();
                for (Lintu lintu : linnut) {
                    if (lintu.getLaji().equals(havaittu)) {
                        lintu.havainto();
                    }
                } continue;
            }
            if (komento.equals("Tilasto")) {
                for (Lintu lintu : linnut) {
                    System.out.println(lintu);
                } continue;
            }
            if (komento.equals("Nayta")) {
                System.out.print("Mikä? ");
                String naytettava = lukija.nextLine();
                for (Lintu lintu : linnut) {
                    if (lintu.getLaji().equals(naytettava)) {
                        System.out.println(lintu);
                        ;
                    }
                }
        }
    }

}
    
}


