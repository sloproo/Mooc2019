
public class Paivays {

    private int paiva;
    private int kuukausi;
    private int vuosi;

    public Paivays(int paiva, int kuukausi, int vuosi) {
        this.paiva = paiva;
        this.kuukausi = kuukausi;
        this.vuosi = vuosi;
    }

    @Override
    public String toString() {
        return this.paiva + "." + this.kuukausi + "." + this.vuosi;
    }

    public void etene() {
        this.paiva = this.paiva +1;
        if (this.paiva > 30) {
            this.paiva = this.paiva -30;
            this.kuukausi = this.kuukausi + 1;
            if (this.kuukausi > 12) {
                this.kuukausi = this.kuukausi - 12;
                this.vuosi = this.vuosi + 1;
            }
        }
    }
    
    public void etene(int montakoPaivaa) {
        int i = 0;
        while (i < montakoPaivaa) {
            this.etene();
            i = i + 1;
        }
    }
    
    public Paivays paivienPaasta(int paivia) {
        Paivays uusiPaivays = new Paivays(this.paiva, this.kuukausi, this.vuosi);
        uusiPaivays.etene(paivia);
        return uusiPaivays;
                
    }
    
    public boolean aiemmin(Paivays verrattava) {
        if (this.vuosi < verrattava.vuosi) {
            return true;
        }

        if (this.vuosi == verrattava.vuosi && this.kuukausi < verrattava.kuukausi) {
            return true;
        }

        if (this.vuosi == verrattava.vuosi && this.kuukausi == verrattava.kuukausi &&
                 this.paiva < verrattava.paiva) {
            return true;
        }

        return false;
    }

}
