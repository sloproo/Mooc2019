
import java.util.Scanner;

public class Ohjelma {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        // voit testata luokkaasi täällä
        Putki<Integer> numeroputki = new Putki<>();
numeroputki.lisaaPutkeen(1);
numeroputki.lisaaPutkeen(2);
numeroputki.lisaaPutkeen(3);

int summa = 0;
while(numeroputki.onkoPutkessa()) {
    summa = summa + numeroputki.otaPutkesta();
}
System.out.println(summa);
System.out.println(numeroputki.otaPutkesta());
    }
}
