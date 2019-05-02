
import java.util.ArrayList;
import java.util.Scanner;

public class RajatutLuvut {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        ArrayList<Integer> luvut = new ArrayList<>();
        while (true) {
            int syote = Integer.valueOf(lukija.nextLine());
            if (syote < 0) break;
            luvut.add(syote);
        }
        luvut.stream()
                .filter(s -> s >= 1 && s <= 5)
                .forEach(luku -> System.out.println(luku));

    }
}
