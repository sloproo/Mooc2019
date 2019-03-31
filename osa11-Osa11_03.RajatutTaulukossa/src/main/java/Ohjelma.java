
import java.util.Scanner;

public class Ohjelma {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        // voit testata luokkaasi täällä
        int[] luvut = {3, -1, 8, 4};

System.out.println(summa(luvut, 0, 0, 0, 0));
System.out.println(summa(luvut, 0, 0, 0, 10));
System.out.println(summa(luvut, 0, 1, 0, 10));
System.out.println(summa(luvut, 0, 1, -10, 10));
System.out.println(summa(luvut, -1, 999, -10, 10));

    }
    
    public static int summa(int[] taulukko, int mista, int mihin, int pienin, 
            int suurin) {
        int palautettavaSumma = 0;
        if (mista < 0) mista = 0;
        if (mihin > taulukko.length - 1) mihin = taulukko.length -1;
        for (int i = mista; i <= mihin; i++) {
            if (taulukko[i] >= pienin && taulukko[i] <= suurin) 
                palautettavaSumma += taulukko[i];
        }
        return palautettavaSumma;
        
    }

}
