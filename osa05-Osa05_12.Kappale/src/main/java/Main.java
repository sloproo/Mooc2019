
public class Main {

    public static void main(String[] args) {
        // voit kirjoittaa testikoodia tänne

        Kappale jackSparrow = new Kappale("The Lonely Island", "Jack Sparrow", 196);
        Kappale toinenSparrow = new Kappale("The Lonely Island", "Jack Sparrow", 196);

        if (jackSparrow.equals(toinenSparrow)) {
            System.out.println("Kappaleet olivat samat.");
        }

        if (jackSparrow.equals("Toinen olio")) {
            System.out.println("Nyt on jotain hassua.");
        }
    }
}
