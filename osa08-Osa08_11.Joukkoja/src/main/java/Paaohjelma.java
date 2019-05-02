

public class Paaohjelma {

    public static void main(String[] args) {
        //kokeile ohjelman toimintaa täällä
        Lauma lauma = new Lauma();
lauma.lisaaLaumaan(new Elio(73, 56));
lauma.lisaaLaumaan(new Elio(57, 66));
lauma.lisaaLaumaan(new Elio(46, 52));
lauma.lisaaLaumaan(new Elio(19, 107));
System.out.println(lauma);
    }
}
