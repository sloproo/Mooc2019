
import java.util.ArrayList;
import java.util.Random;

public class Lottorivi {

    private ArrayList<Integer> numerot;

    public Lottorivi() {
        // Arvo numerot heti LottoRivin luomisen yhteydessä
        this.numerot = new ArrayList<>();
        this.arvoNumerot();
    }

    public ArrayList<Integer> numerot() {
        ArrayList<Integer> palautettavat = new ArrayList<>();
        this.numerot.stream().sorted().forEach(numero -> palautettavat.add(numero));
        return palautettavat;
    }

    public void arvoNumerot() {
        // Alustetaan lista numeroille
        this.numerot = new ArrayList<>();
        // Kirjoita numeroiden arvonta tänne
        // kannattanee hyödyntää metodia sisaltaaNumeron
        while (this.numerot.size() < 7) {
            int lisattava = new Random().nextInt(40)+1;
            if (!this.sisaltaaNumeron(lisattava)) this.numerot.add(lisattava);
        }
    }

    public boolean sisaltaaNumeron(int numero) {
        // Testaa tässä onko numero jo arvottujen numeroiden joukossa
        return this.numerot.contains(numero);
    }
}
