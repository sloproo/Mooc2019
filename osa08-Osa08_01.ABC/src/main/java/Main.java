import java.util.HashMap;
public class Main {

    public static void main(String[] args) {
        // tee tänne testikoodia
        String merkkijono = " Bätmään!";

        if (merkkijono.matches("(nä)+ Bätmään!")) {
            System.out.println("Muoto on oikea.");
        } else {
        System.out.println("Muoto ei ole oikea.");
}
    }
}
