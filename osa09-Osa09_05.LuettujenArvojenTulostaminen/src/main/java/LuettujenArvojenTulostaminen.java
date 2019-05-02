
import java.util.ArrayList;
import java.util.Scanner;

public class LuettujenArvojenTulostaminen {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        ArrayList<String> luettu = new ArrayList<>();
        while (true) {
            String syote = lukija.nextLine();
            if (syote.equals("")) break;
            luettu.add(syote);
        }
        luettu.stream().forEach(listattu -> System.out.println(listattu));

    }
}
