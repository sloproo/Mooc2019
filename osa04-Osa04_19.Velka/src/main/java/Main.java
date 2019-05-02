
public class Main {

    public static void main(String[] args) {
        // Kokeile täällä tehtävässä luomasi Velka-luokan toimintaa

        Velka asuntolaina = new Velka(120000.0, 1.20);
        asuntolaina.tulostaSaldo();
//
        asuntolaina.odotaVuosi();
        asuntolaina.tulostaSaldo();
//
        int vuosia = 0;
//
        while (vuosia < 20) {
            asuntolaina.odotaVuosi();
            vuosia++;
        }
//
        asuntolaina.tulostaSaldo();
    }
}
