
public class Ohjelma {

    public static void main(String[] args) {
        // Testaa metodiasi täällä
        int[][] matriisi = {
    {3, 2, 7, 6},
    {2, 4, 1, 0},
    {3, 2, 1, 0}
};

System.out.println(taulukkoMerkkijonona(matriisi));
    }
    
    public static String taulukkoMerkkijonona(int[][] taulukko) {
        StringBuilder palautettavat = new StringBuilder();
        for (int rivi = 0; rivi < taulukko.length; rivi++) {
            for (int sarake = 0; sarake < taulukko[rivi].length; sarake++) {
            palautettavat.append(taulukko[rivi][sarake]);
            }
        palautettavat.append("\n");
            
        }
        return palautettavat.toString();
        
        
        // for (int rivi = 0; rivi < taulukko.length; rivi++) {
        // String palautettava = new StringBuilder().app
        
        /*for (int rivi = 0; rivi < taulukko.length; rivi++) {
            String rivii = 
                    new StringBuilder().append(taulukko[][], 0, taulukko[].length);
            new StringBuilder().
            }*/
        
    }

}
