
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

@Points("06-09")
public class PaivaysTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Paivays";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void onMetodiHashcode() throws Throwable {
        String metodi = "hashCode";

        Paivays olio = new Paivays(1, 1, 2011);

        assertTrue("tee luokalle " + klassName + " metodi public int " + metodi + "() ",
                klass.method(olio, metodi).returning(int.class).takingNoParams().isPublic());
    }

    @Test
    public void kahdellaSamanlaisellaPaivayksellaAinaSamaHashCode() {
        HashMap<Integer, ArrayList<Paivays>> paivat = new HashMap<>();

        for (int vuosi = 1999; vuosi <= 2012; vuosi++) {
            for (int kuukausi = 1; kuukausi <= 12; kuukausi++) {
                for (int paiva = 1; paiva <= 31; paiva++) {

                    Paivays p = new Paivays(paiva, kuukausi, vuosi);
                    Paivays p2 = new Paivays(paiva, kuukausi, vuosi);

                    assertTrue("Kahden samanlaisen päivän hashCode-arvojen tulee olla aina samat.\nNyt päivien " + p + " ja " + p2 + " hashCode-arvot olivat eri.", p.hashCode() == p2.hashCode());
                }
            }
        }
    }

    @Test
    public void tarpeeksiEriHashCodetVuosien1900Ja2100Valilla() {
        HashMap<Integer, ArrayList<Paivays>> paivat = new HashMap<>();

        for (int vuosi = 1900; vuosi <= 2100; vuosi++) {
            for (int kuukausi = 1; kuukausi <= 12; kuukausi++) {
                for (int paiva = 1; paiva <= 31; paiva++) {
                    Paivays p = new Paivays(paiva, kuukausi, vuosi);

                    if (!paivat.containsKey(p.hashCode())) {
                        paivat.put(p.hashCode(), new ArrayList<>());
                    }

                    paivat.get(p.hashCode()).add(p);
                    if (paivat.get(p.hashCode()).size() > 20) {
                        fail("hashCode-toteutuksesi palauttaa liian samankaltaisia arvoja.\nPäivällä " + p + " on sama hajautusarvo päivien\n" + paivat.get(p.hashCode()) + "\nkanssa.");
                    }
                }
            }
        }
    }
}
