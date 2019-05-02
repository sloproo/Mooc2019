
public class Main {

    public static void main(String[] args) {
        // testaa luokkasi toimintaa täällä!
            Paivays pvm = new Paivays(13, 2, 2015);
    System.out.println("Tarkistellun viikon perjantai on " + pvm);

    Paivays uusiPvm = pvm.paivienPaasta(7);
        System.out.println("Onnistuneesti kasvatettu, ");
    int vk = 1;
    while (vk <= 7) {
        System.out.println("Perjantai " + vk + " viikon kuluttua on " + uusiPvm);
        uusiPvm = uusiPvm.paivienPaasta(7);

        vk = vk + 1;
    }


    System.out.println("Päivämäärä 790:n päivän päästä tarkistellusta perjantaista on ... kokeile itse!");
    System.out.println("Kokeile " + pvm.paivienPaasta(790));
}
    
}
