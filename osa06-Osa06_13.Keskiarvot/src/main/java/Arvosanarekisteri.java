
import java.util.HashMap;

public class Arvosanarekisteri {

    private HashMap<Integer, Integer> arvosanat;
    private int pisteita;
    private int tuloksia;

    public Arvosanarekisteri() {
        this.arvosanat = new HashMap<>();
        this.pisteita = 0;
        this.tuloksia = 0;
    }

    public void lisaaArvosanaPisteidenPerusteella(int pisteet) {
        int arvosana = pisteetArvosanaksi(pisteet);

        int lkm = this.arvosanat.getOrDefault(arvosana, 0);
        this.arvosanat.put(arvosana, lkm + 1);
        this.pisteita = this.pisteita + pisteet;
        this.tuloksia = this.tuloksia + 1;
    }

    public int montakoSaanutArvosanan(int arvosana) {
        return this.arvosanat.getOrDefault(arvosana, 0);
    }


    public static int pisteetArvosanaksi(int pisteet) {

        int arvosana = 0;
        if (pisteet < 50) {
            arvosana = 0;
        } else if (pisteet < 60) {
            arvosana = 1;
        } else if (pisteet < 70) {
            arvosana = 2;
        } else if (pisteet < 80) {
            arvosana = 3;
        } else if (pisteet < 90) {
            arvosana = 4;
        } else {
            arvosana = 5;
        }

        return arvosana;
    }
    
    public double koepisteidenKeskiarvo() {
        return 1.0 * this.pisteita / this.tuloksia;
    }
    
    public double arvosanojenKeskiarvo() {
        int summa = 0;
        int arvosanoja = 0;
        for (int i = 0; i < 6; i++) {
            summa = summa + i * arvosanat.getOrDefault(i, 0);
            arvosanoja = arvosanoja + arvosanat.getOrDefault(i, 0);
        }
        return 1.0 * summa / arvosanoja;
    }
}
