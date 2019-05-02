
public class Henkilo {

    private String nimi;
    private Lemmikki lemmikki;

    public Henkilo(String nimi, Lemmikki lemmikki) {
        this.nimi = nimi;
        this.lemmikki = lemmikki;
    }

    public Henkilo(String nimi) {
        this(nimi, new Lemmikki("Karvinen", "kissa"));
    }

    public Henkilo() {
        this("Caliban", new Lemmikki("Tuntematon", "mielikuvituskaveri"));
    }

    @Override
    public String toString() {
        return this.nimi + ", kaverina " + this.lemmikki.getNimi() + ", joka on "
                + this.lemmikki.getRotu();
    }

}
