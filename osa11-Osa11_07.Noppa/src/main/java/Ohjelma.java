
public class Ohjelma {

    public static void main(String[] args) {
        // testipääohjelma  
        Noppa noppa = new Noppa(6);

        int i = 0;
        while (i < 10) {
            System.out.println(noppa.heita());
            i++;
        }

    }
}
