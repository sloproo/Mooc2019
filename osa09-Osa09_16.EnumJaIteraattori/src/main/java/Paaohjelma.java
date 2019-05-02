
public class Paaohjelma {

    public static void main(String[] args) {
        // tee testikoodia tänne
        Tyontekijat t = new Tyontekijat(); 
t.lisaa(new Henkilo("Arto", Koulutus.FT)); 
t.lisaa(new Henkilo("Juuso", Koulutus.FM));
t.tulosta(Koulutus.FT); 
    }
}
