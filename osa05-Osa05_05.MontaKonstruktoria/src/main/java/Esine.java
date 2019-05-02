
public class Esine {

    private String nimi;
    private String sijainti;
    private int paino;

    public Esine(String nimi, String sijainti, int paino) {
        this.nimi = nimi;
        this.sijainti = sijainti;
        this.paino = paino;
    }
    
    public Esine(String nimi) {
        this(nimi, "pientavarahylly", 1);
    }
    
    public Esine(String nimi, String sijainti) {
        this(nimi, sijainti, 1);
    }
    
    public Esine(String nimi, int paino) {
        this(nimi, "varasto", paino);
    }

    
    public String getNimi() {
        return nimi;
    }

    public int getPaino() {
        return paino;
    }

    public String getSijainti() {
        return sijainti;
    }

    @Override
    public String toString() {
        return this.nimi + " (" + this.paino + " kg) löytyy sijainnista " + this.sijainti;
    }
}
