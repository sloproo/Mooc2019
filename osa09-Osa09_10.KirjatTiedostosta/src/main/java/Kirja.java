
public class Kirja {

    private String nimi;
    private int julkaisuvuosi;
    private int sivuja;
    private String kirjoittaja;

    public Kirja(String nimi, int julkaisuvuosi, int sivuja, String kirjoittaja) {
        this.nimi = nimi;
        this.julkaisuvuosi = julkaisuvuosi;
        this.sivuja = sivuja;
        this.kirjoittaja = kirjoittaja;
    }

    public String getNimi() {
        return nimi;
    }

    public int getJulkaisuvuosi() {
        return julkaisuvuosi;
    }

    public String getKirjoittaja() {
        return kirjoittaja;
    }

    public int getSivuja() {
        return sivuja;
    }

}
