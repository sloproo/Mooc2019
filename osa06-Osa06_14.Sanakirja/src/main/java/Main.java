
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
Sanakirja sanakirja = new Sanakirja();
sanakirja.lisaa("apina", "monkey");
sanakirja.lisaa("banaani", "banana");
sanakirja.lisaa("cembalo", "harpsichord");

ArrayList<String> kaannokset = sanakirja.kaannoksetListana();

for (String kaannos: kaannokset) {
    System.out.println(kaannos);
}

}
}
