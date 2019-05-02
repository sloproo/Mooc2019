
public class Main {
    public static void main(String[] args) {
        Ruokalista exactum = new Ruokalista();
        
        // Kun olet luonut metodin lisaaAteria(String ateria), 
        // voit poistaa allaolevat kommentit
        
        exactum.lisaaAteria("Pariloitua lohta ja kuhaa, basilikalla maustettua valkoviinivoikastiketta.");
        exactum.lisaaAteria("Kesäinen vihersalaatti ja omena-hunajavinegretti.");
        exactum.lisaaAteria("Paahdettua karitsan seläkettä ja punaviinikastiketta.");
        exactum.lisaaAteria("Vegaaninen höystö ja keitetyt pakastevihannekset");
        
        // Kun olet luonut metodin tulostaAteriat(), 
        // voit poistaa allaolevan kommentin
        
        exactum.tulostaAteriat();
        
        // Kun olet luonut metodin tyhjennaRuokalista(), voit poistaa 
        // allaolevat kommentit
        exactum.tyhjennaRuokalista();
        System.out.println("Tulostetaan tyhjennetty ruokalista");
        exactum.tulostaAteriat();
    }
}
