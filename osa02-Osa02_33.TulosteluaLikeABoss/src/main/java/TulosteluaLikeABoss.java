
public class TulosteluaLikeABoss {

    public static void tulostaTahtia(int maara) {
        // tehtävän osa 1
        int i = 0;
        while (i < maara) {
            System.out.print("*");
            i++;
        }
        System.out.println("");
        
    }

    public static void tulostaTyhjaa(int maara) {
        // tehtävän osa 1
        int i = 0;
        while (i < maara) {
            System.out.print(" ");
            i++;
        }
    }

    public static void tulostaKolmio(int koko) {
        // tehtävän osa 2
        int i = 0;
        while (i < koko) {
            i++;
            tulostaTyhjaa(koko - i);
            tulostaTahtia(i);
        }
    }

    public static void jouluKuusi(int korkeus) {
        // tehtävän osa 3
        int i = 0;
        while (i < korkeus) {
            i++;
            tulostaTyhjaa(korkeus - i);
            tulostaTahtia(i + i -1);
        }
        i = 2;
        int n = 0;
        while (n < 2) {
            tulostaTyhjaa(korkeus - i);
            tulostaTahtia(i + i - 1);
            n++;
        }
        
        
    }

    public static void main(String[] args) {
        // Testit eivät katso main-metodia, voit muutella tätä vapaasti.

        tulostaKolmio(5);
        System.out.println("---");
        jouluKuusi(4);
        System.out.println("---");
        jouluKuusi(16);
    }
}
