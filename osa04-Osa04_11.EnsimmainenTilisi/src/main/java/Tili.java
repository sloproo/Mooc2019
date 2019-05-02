/* 
 * ÄLÄ KOSKE TÄÄLLÄ OLEVAAN KOODIIN!
 */

public class Tili {

    private double saldo;
    private String omistaja;

    public Tili(String omistaja, double saldo) {
        this.saldo = saldo;
        this.omistaja = omistaja;
    }

    public void pano(double maara) {
        this.saldo = this.saldo + maara;
    }

    public void otto(double maara) {
        this.saldo = this.saldo - maara;
    }

    public double saldo() {
        return this.saldo;
    }

    @Override
    public String toString() {
        return this.omistaja + " saldo: " + this.saldo;
    }
}
