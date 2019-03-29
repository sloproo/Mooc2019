package validointi;

public class Henkilo {

    private String nimi;
    private int ika;

    public Henkilo(String nimi, int ika) {
        if (nimi == null || nimi.isEmpty() ||  nimi.equals("") 
                 || nimi.length() > 40) {
            throw new IllegalArgumentException("Nimi pielessä!");
        }
        if (!(ika >= 0 && ika <= 120)) {
            throw new IllegalArgumentException("Ikä pielessä!");
        }

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
