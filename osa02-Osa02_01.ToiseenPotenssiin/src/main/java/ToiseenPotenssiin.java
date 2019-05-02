
import java.util.Scanner;

public class ToiseenPotenssiin {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
int lukuja = 0;
int summa = 0;

while (true) {
    System.out.println("-- lukuja: " + lukuja + ", summa: " + summa);

    System.out.println("Syötä luku, negatiivinen luku lopettaa");
    int luku = Integer.valueOf(lukija.nextLine());
    System.out.println("-- syötetty luku: " + luku);
    if (luku < 0) {
        System.out.println("-- luku negatiivinen, poistutaan toistolauseesta");
        break;
    }

    lukuja = lukuja + 1;
    summa = summa + luku;
}

System.out.println("-- toistolauseesta poistuttu");
System.out.println("-- lukuja: " + lukuja + ", summa: " + summa);

if (summa == 0) {
    System.out.println("Lukujen keskiarvoa ei voitu laskea.");
} else {
    System.out.println("Lukujen keskiarvo: " + (1.0 * summa / lukuja));
}
    }
}
