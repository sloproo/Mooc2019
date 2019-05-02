
public class Main {

    public static void main(String[] args) {
        // käytä tätä pääohjelmaa luokkiesi testaamiseen!
        Lahja kirja = new Lahja("Aapiskukko", 2);
        System.out.println("kirja luotu");
        System.out.println("luodaan paketti");
        Pakkaus paketti = new Pakkaus();
        System.out.println("paketti luotu");
        System.out.println("lisataan kirja pakettiin");
        paketti.lisaaLahja(kirja);
        System.out.println("kirja lisatty");
        System.out.println("tulostetaan yhteispaino");
        System.out.println(paketti.yhteispaino());
    }
    
}
