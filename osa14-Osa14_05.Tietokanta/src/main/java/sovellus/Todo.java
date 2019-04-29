package sovellus;

public class Todo {

    private int id;
    private String nimi;
    private String kuvaus;
    private Boolean valmis;

    public Todo(int id, String nimi, String kuvaus, Boolean valmis) {
        this.id = id;
        this.nimi = nimi;
        this.kuvaus = kuvaus;
        this.valmis = valmis;
    }

    public Todo(String nimi, String kuvaus, Boolean valmis) {
        this(-1, nimi, kuvaus, valmis);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getKuvaus() {
        return kuvaus;
    }

    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    public Boolean getValmis() {
        return valmis;
    }

    public void setValmis(Boolean valmis) {
        this.valmis = valmis;
    }

    @Override
    public String toString() {
        return "Todo{" + "id=" + id + ", nimi=" + nimi + ", kuvaus=" + kuvaus + ", valmis=" + valmis + '}';
    }

}
