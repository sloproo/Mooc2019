
import java.util.Scanner;
import java.util.ArrayList;

public class Paaohjelma {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        // Tee sovelluksesi tänne -- kannattaa harkita sovelluksen pilkkomista
        // useampaan luokkaan.
        ArrayList<Integer> arvosanat = new ArrayList<>();
        System.out.println("Syötä yhteispisteet, -1 lopettaa:");
        while (true) {
            int syotetty = Integer.valueOf(lukija.nextLine());
            if (syotetty == -1) break;
            if ((syotetty < 0 || syotetty > 100)) continue;
            arvosanat.add(syotetty);
        }
        System.out.println("Pisteiden keskiarvo (kaikki): " + pisteidenKeskiarvot(arvosanat));
        if (hyvaksyttyjenKeskiarvot(arvosanat) == 0) {
            System.out.println("Pisteiden keskiarvo (hyväksytyt): -");
        } else System.out.println("Pisteiden keskiarvo (hyväksytyt): " + hyvaksyttyjenKeskiarvot(arvosanat));
        if (hyvaksyttyjenProsentti(arvosanat) == 0) {
            System.out.println("Hyväksymisprosentti: -");
        } else System.out.println("Hyväksymisprosentti: " + hyvaksyttyjenProsentti(arvosanat));
        arvosanajakauma(arvosanat);
        
    }
    
    public static double pisteidenKeskiarvot(ArrayList<Integer> lista) {
        int kasumma = 0;
        int kamaara = 0;
        for (int i = 0; i < lista.size(); i++) {
            kasumma = kasumma + lista.get(i);
            kamaara = kamaara + 1;
        }
        return 1.0 * kasumma / kamaara;
    }
    
    public static double hyvaksyttyjenKeskiarvot (ArrayList<Integer> lista) {
        int hykasumma = 0;
        int hykamaara = 0;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i) >= 50) hykasumma = hykasumma + lista.get(i);
            if (lista.get(i) >= 50) hykamaara = hykamaara + 1;
        }
        if (hykamaara == 0) return 0;
        return 1.0 * hykasumma / hykamaara;
    }
    
    public static double hyvaksyttyjenProsentti (ArrayList<Integer> lista) {
        int hyvmaara = 0;
        int osallistuneita = lista.size();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i) >= 50) hyvmaara = hyvmaara + 1;
        }
        if (hyvmaara == 0) return 0;
        return 100.0 * hyvmaara / osallistuneita;
    }
    
        public static void tulostaTahtia(int maara) {

        int i = 0;
        while (i < maara) {
            System.out.print("*");
            i++;
        }
        System.out.println("");
    }
    
    public static void arvosanajakauma(ArrayList<Integer> lista) {
        int nollia = 0;
        int ykkosia = 0;
        int kakkosia = 0;
        int kolmosia = 0;
        int nelosia = 0;
        int vitosia = 0;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i) < 50) {
                nollia = nollia +1;
            } else if (lista.get(i) < 60) {
                ykkosia = ykkosia + 1;
            } else if (lista.get(i) < 70) {
                kakkosia = kakkosia + 1;
            } else if (lista.get(i) < 80) {
                kolmosia = kolmosia + 1; 
            } else if (lista.get(i) < 90) {
                nelosia = nelosia + 1;
            } else if (lista.get(i) >= 90) {
                vitosia = vitosia + 1;
            }
        } 
        System.out.println("Arvosanajakauma:");      
        System.out.print("5: ");
        tulostaTahtia(vitosia);
        System.out.print("4: ");
        tulostaTahtia(nelosia);        
        System.out.print("3: ");
        tulostaTahtia(kolmosia);
        System.out.print("2: ");
        tulostaTahtia(kakkosia);
        System.out.print("1: ");
        tulostaTahtia(ykkosia);
        System.out.print("0: ");
        tulostaTahtia(nollia);
    }
    
    
}
