
public class Main {

    public static void main(String[] args) {
        // voit kokeilla luomiesi luokkien toimintaa täällä
     
Aanteleva koira = new Koira();
koira.aantele();

Aanteleva kissa = new Kissa("Karvinen");
kissa.aantele();
Kissa k = (Kissa) kissa;
k.mourua();
k.aantele();

}
}
