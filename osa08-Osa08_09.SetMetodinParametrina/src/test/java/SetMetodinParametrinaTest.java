
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

@Points("08-09")
public class SetMetodinParametrinaTest {

    @Test
    public void onMetodiPalautaKoko() {
        Reflex.reflect(Paaohjelma.class).staticMethod("palautaKoko").returning(int.class).taking(Set.class).requireExists();
    }

    @Test
    public void metodiPalauttaaAnnetunSetinKoon() throws Throwable {
        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            int koko = rnd.nextInt(10);
            Set<String> set = null;

            if (rnd.nextBoolean()) {
                set = new HashSet<>();
            } else {
                set = new TreeSet<>();
            }

            for (int j = 0; j < koko; j++) {
                set.add(UUID.randomUUID().toString());
            }

            int arvoja = (int) Reflex.reflect(Paaohjelma.class).staticMethod("palautaKoko").returning(int.class).taking(Set.class).invoke(set);
            assertEquals("Kun metodille palautaKoko annetaan " + koko + ":n alkion kokoinen set-olio, tulisi palautetun arvon olla " + koko + ". Nyt palautettu arvo oli " + arvoja, koko, arvoja);
        }

    }

}
