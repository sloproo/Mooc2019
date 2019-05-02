
import java.util.Scanner;

public class JokaToinenSana {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        
        while (true) {
            String teksti = lukija.nextLine();
            if (teksti.equals("")) {
                break;
            }
            String sanat[] = teksti.split("\\s+");
            int i = 1;
            while (i < sanat.length) {
                System.out.println(sanat[i]);
                i = i+2;
            }
        }


    }
}
