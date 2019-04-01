
import java.util.Scanner;

public class Ohjelma {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        // voit testata luokkaasi täällä
        //No ni nyt ihan kunnolla :)
        
        Lista<String> lista = new Lista<>();
lista.lisaa("hei");
lista.lisaa("maailma");

for(int i = 0; i < lista.koko(); i++) {
    System.out.println(lista.arvo(i));
}

    }

}
