
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class PositiivisetLuvut {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        // testaa metodia täällä

    }
    
    public static List<Integer> positiiviset(List<Integer> luvut) {
        return luvut.stream().filter(luku -> luku >= 0)
                .collect(Collectors.toList());
    }

}
