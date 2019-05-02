
public class Ohjelma {

    public static void main(String[] args) {
        // Testaa sanakirjaa täällä
        UseanKaannoksenSanakirja sanakirja = new UseanKaannoksenSanakirja();
        sanakirja.lisaa("kuusi", "six");
        sanakirja.lisaa("kuusi", "spruce");

        sanakirja.lisaa("pii", "silicon");
        sanakirja.lisaa("pii", "pi");
//
        System.out.println(sanakirja.kaanna("kuusi"));
        sanakirja.poista("pii");
        System.out.println(sanakirja.kaanna("pii"));
    }
}
