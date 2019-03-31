
import java.util.Scanner;

public class Ohjelma {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        // voit testata luokkaasi täällä
        Putki<String> putki = new Putki<>();
        putki.lisaaPutkeen("dibi");
        putki.lisaaPutkeen("dab");
        putki.lisaaPutkeen("dab");
        putki.lisaaPutkeen("daa");
        while(putki.onkoPutkessa()) {
            System.out.println(putki.otaPutkesta());
        }
    }
}
