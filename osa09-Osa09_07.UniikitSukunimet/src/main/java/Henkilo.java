


public class Henkilo {

    private String etunimi;
    private String sukunimi;
    private int syntymavuosi;

    public Henkilo(String etunimi, String sukunimi, int syntymavuosi) {
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
        this.syntymavuosi = syntymavuosi;
    }

    public String getEtunimi() {
        return etunimi;
    }

    public String getSukunimi() {
        return sukunimi;
    }

    public int getSyntymavuosi() {
        return syntymavuosi;
    }

}
