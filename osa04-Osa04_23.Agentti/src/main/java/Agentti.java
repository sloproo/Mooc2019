

public class Agentti {

    private String etunimi;
    private String sukunimi;
    
    public Agentti(String etunimiAlussa, String sukunimiAlussa) {
        this.etunimi = etunimiAlussa;
        this.sukunimi = sukunimiAlussa;
    }
    
    public String toString() {
        return "My name is " + this.sukunimi + ", " + this.etunimi +
                " " + this.sukunimi;
    }
    
}
