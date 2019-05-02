
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

@Points("08-08")
public class MapMetodinParametrinaTest {

    @Test
    public void onMetodiPalautaKoko() {
        Reflex.reflect(Paaohjelma.class).staticMethod("palautaKoko").returning(int.class).taking(Map.class).requireExists();
    }

    @Test
    public void metodiPalauttaaAnnetunMapinKoon() throws Throwable {
        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            int koko = rnd.nextInt(10);
            Map<String, String> map = null;

            if (rnd.nextBoolean()) {
                map = new HashMap<>();
            } else {
                map = new TreeMap<>();
            }

            for (int j = 0; j < koko; j++) {
                map.put(UUID.randomUUID().toString(), UUID.randomUUID().toString());
            }

            int arvoja = (int) Reflex.reflect(Paaohjelma.class).staticMethod("palautaKoko").returning(int.class).taking(Map.class).invoke(map);
            assertEquals("Kun metodille palautaKoko annetaan " + koko + ":n alkion kokoinen map-olio, tulisi palautetun arvon olla " + koko + ". Nyt palautettu arvo oli " + arvoja, koko, arvoja);
        }
    }
}
