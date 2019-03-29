package validointi;

public class Henkilo {

    private String nimi;
    private int ika;

    public Henkilo(String nimi, int ika) {

        this.nimi = nimi;
        this.ika = ika;
    }

    public String getNimi() {
        return nimi;
    }

    public int getIka() {
        return ika;
    }
    
    @Override
    public String toString() {
        return this.nimi + ", " + this.ika + " vuotta. Hip hei.";
    }
}
