
public class Paivays {

    private int paiva;
    private int kuukausi;
    private int vuosi;

    public Paivays(int paiva, int kuukausi, int vuosi) {
        this.paiva = paiva;
        this.kuukausi = kuukausi;
        this.vuosi = vuosi;
    }

    public int getPaiva() {
        return this.paiva;
    }

    public int getKuukausi() {
        return this.kuukausi;
    }

    public int getVuosi() {
        return this.vuosi;
    }

    public boolean equals(Object verrattava) {
        // jos muuttujat sijaitsevat samassa paikassa, ovat ne samat
        if (this == verrattava) {
            return true;
        }

        // jos verrattava olio ei ole Paivays-tyyppinen, oliot eivät ole samat
        if (!(verrattava instanceof Paivays)) {
            return false;
        }

        // muunnetaan oli Paivays-olioksi
        Paivays verrattavaPaivays = (Paivays) verrattava;

        // jos olioiden oliomuuttujien arvot ovat samat, ovat oliot samat
        if (this.paiva == verrattavaPaivays.paiva
                && this.kuukausi == verrattavaPaivays.kuukausi
                && this.vuosi == verrattavaPaivays.vuosi) {
            return true;
        }

        // muulloin oliot eivät ole samat
        return false;
    }

    @Override
    public String toString() {
        return this.paiva + "." + this.kuukausi + "." + this.vuosi;
    }
}
