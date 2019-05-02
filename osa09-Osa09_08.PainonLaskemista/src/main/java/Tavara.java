

public class Tavara {

    private String nimi;
    private int paino;

    public Tavara(String nimi, int paino) {
        this.nimi = nimi;
        this.paino = paino;
    }

    public String getNimi() {
        return this.nimi;
    }

    public int getPaino() {
        return this.paino;
    }

    @Override
    public String toString() {
        return this.nimi + ": (" + this.paino + " kg)";
    }
}
