/*
 *  Älä muuta tämän luokan toimintaa!
 */

public class Henkilo {

    private String nimi;
    private int ika;
    private int pituus;
    private int paino;

    public Henkilo(String nimi, int ika, int pituus, int paino) {
        this.nimi = nimi;
        this.ika = ika;
        this.pituus = pituus;
        this.paino = paino;
    }

    public void tulostaHenkilo() {
        System.out.println(this.nimi + " olen " + this.ika + " vuotta vanha");
    }

    public void vanhene() {
        this.ika++;
    }

    public boolean taysiIkainen() {
        if (this.ika > 17) {
            return true;
        }

        return false;
    }

    public void setPituus(int pituus) {
        this.pituus = pituus;
    }

    public int getPituus() {
        return this.pituus;
    }

    public int getPaino() {
        return this.paino;
    }

    public void setPaino(int paino) {
        this.paino = paino;
    }

    public double painoindeksi() {
        double pituusMetreina = this.pituus / 100.0;

        return this.paino / (pituusMetreina * pituusMetreina);
    }

    public String getNimi() {
        return this.nimi;
    }

    @Override
    public String toString() {
        return this.nimi + " olen " + this.ika + " vuotta vanha, painoindeksini on " + this.painoindeksi();
    }
}
