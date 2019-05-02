
public class Suurin {
    public static int suurempi(int luku1, int luku2) {
        if (luku1 > luku2) {
            return luku1;
        } else return luku2;
    }

    public static int suurin(int luku1, int luku2, int luku3) {
        return suurempi(luku1,suurempi(luku2, luku3));
    }

    public static void main(String[] args) {
        int vastaus = suurin(2, 7, 3);
        System.out.println("Suurin: " + vastaus);
    }
}
