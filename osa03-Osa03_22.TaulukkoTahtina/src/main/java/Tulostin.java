
public class Tulostin {

    public static void main(String[] args) {
        // Tässä voit testata metodia
        int[] taulukko = {5, 1, 3, 4, 2};
        tulostaTaulukkoTahtina(taulukko);
    }

    public static void tulostaTaulukkoTahtina(int[] taulukko) {
        // Kirjoita tulostuskoodi tänne
        int i = 0;
        while (i < taulukko.length) {
            tulostaTahtia(taulukko[i]);
            i++;
        }
    }
    
    public static void tulostaTahtia(int tahtienmaara) {
        int i = 0;
        while (i < tahtienmaara) {
            System.out.print("*");
            i++;
        }
        System.out.println("");
    }
}
