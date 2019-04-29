package schelling;

public class Taulukko {

    private int[][] taulukko;
    private int leveys;
    private int korkeus;

    public Taulukko(int leveys, int korkeus) {
        this.leveys = leveys;
        this.korkeus = korkeus;
        this.taulukko = new int[leveys][korkeus];
    }

    public int getLeveys() {
        return leveys;
    }

    public int getKorkeus() {
        return korkeus;
    }

    public void aseta(int x, int y, int arvo) {
        if (x < 0 || x >= this.leveys || y < 0 || y >= this.korkeus) {
            return;
        }

        this.taulukko[x][y] = arvo;
    }

    public int hae(int x, int y) {
        if (x < 0 || x >= this.leveys || y < 0 || y >= this.korkeus) {
            return -1;
        }

        return this.taulukko[x][y];
    }

}
