
public class Ihminen implements Comparable<Ihminen> {

    private int palkka;
    private String nimi;

    public Ihminen(String nimi, int palkka) {
        this.nimi = nimi;
        this.palkka = palkka;
    }

    public String getNimi() {
        return nimi;
    }

    public int getPalkka() {
        return palkka;
    }


    @Override
    public String toString() {
        return nimi + " " + palkka;
    }
    
    @Override
    public int compareTo(Ihminen verrokki) {
        return verrokki.getPalkka() - this.palkka;
    }
    
    
}
