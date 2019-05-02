

public class Varasto {

    private double tilavuus;
    private double saldo;

    public Varasto(double tilavuus) {
        if (tilavuus > 0.0) {
            this.tilavuus = tilavuus;
        } else {
            this.tilavuus = 0.0;
        }

        this.saldo = 0.0;
    }

    public double getSaldo() {
        return this.saldo;
    }

    public double getTilavuus() {
        return this.tilavuus;
    }

    public double paljonkoMahtuu() {
        return this.tilavuus - this.saldo;
    }

    public void lisaaVarastoon(double maara) {
        if (maara < 0) {
            return;
        }
        if (maara <= paljonkoMahtuu()) {
            this.saldo = this.saldo + maara;
        } else {
            this.saldo = this.tilavuus;
        }
    }

    public double otaVarastosta(double maara) {
        if (maara < 0) {
            return 0.0;
        }
        if (maara > this.saldo) {
            double kaikkiMitaVoidaan = this.saldo;
            this.saldo = 0.0;
            return kaikkiMitaVoidaan;
        }

        this.saldo = this.saldo - maara;
        return maara;
    }

    public String toString() {
        return "saldo = " + this.saldo + ", tilaa " + paljonkoMahtuu();
    }
}
