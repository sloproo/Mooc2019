
public class Ohjelma {

    public static void main(String[] args) {
        // Testaa ohjelmaasi täällä
Kellari k = new Kellari();
k.lisaa("a111","hiiri");
k.lisaa("a111","juusto");
k.lisaa("b123","piirtoheitin");
k.lisaa("g63","luistimet");
k.poista("g63","luistimet");
        System.out.println(k.sisalto("g63"));
k.sisalto("g63");

    }
}
