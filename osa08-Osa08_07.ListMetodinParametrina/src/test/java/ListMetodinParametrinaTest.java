
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

@Points("08-07")
public class ListMetodinParametrinaTest {

    @Test
    public void onMetodiPalautaKoko() {
        Reflex.reflect(Paaohjelma.class).staticMethod("palautaKoko").returning(int.class).taking(List.class).requireExists();
    }

    @Test
    public void metodiPalauttaaAnnetunListanKoon() throws Throwable {

        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            int koko = rnd.nextInt(10);
            List<String> list = null;

            if (rnd.nextBoolean()) {
                list = new ArrayList<>();
            } else {
                list = new LinkedList<>();
            }

            for (int j = 0; j < koko; j++) {
                list.add(UUID.randomUUID().toString());
            }

            int arvoja = (int) Reflex.reflect(Paaohjelma.class).staticMethod("palautaKoko").returning(int.class).taking(List.class).invoke(list);
            assertEquals("Kun metodille palautaKoko annetaan " + koko + ":n alkion kokoinen lista, tulisi palautetun arvon olla " + koko + ". Nyt palautettu arvo oli " + arvoja, koko, arvoja);
        }

    }

}
