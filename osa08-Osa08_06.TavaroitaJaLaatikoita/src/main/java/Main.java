
public class Main {

    public static void main(String[] args) {
        // testaa täällä luokkiesi toimintaa
        Laatikko kirjoille = new Laatikko(10);
        Laatikko levyille = new Laatikko(4);
        Laatikko laatikoille = new Laatikko(20);

    kirjoille.lisaa(new Kirja("Fedor Dostojevski", "Rikos ja Rangaistus", 2)) ;
    kirjoille.lisaa(new Kirja("Robert Martin", "Clean Code", 1));
    kirjoille.lisaa(new Kirja("Kent Beck", "Test Driven Development", 0.7));

    levyille.lisaa(new CDLevy("Pink Floyd", "Dark Side of the Moon", 1973));
    levyille.lisaa(new CDLevy("Wigwam", "Nuclear Nightclub", 1975));
    levyille.lisaa(new CDLevy("Rendezvous Park", "Closer to Being Here", 2012));
    
        System.out.println(levyille);    
    System.out.println(levyille.sisalto());
    
        System.out.println(kirjoille);
        System.out.println(kirjoille.sisalto());
        
        System.out.println("Levylaatikko laatikkoon");
        laatikoille.lisaa(levyille);
        System.out.println(laatikoille);
        System.out.println(laatikoille.sisalto());
        

    
    }

}
