package schelling;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.ArrayList;
import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;

public class EriytymismalliTest {

    private Eriytymismalli malli;

    private String luonti = "Eritymismalli malli = new Eriytymismalli(10, 10, 5, 2, 70.0);\n";

    @Before
    public void setup() {
        this.malli = new Eriytymismalli(10, 10, 5, 2, 70.0);
    }

    @Test
    @Points("14-03.1")
    public void arvojenAsettaminenJaHakeminenToimii() {
        asetaArvo(malli, 0, 0, 1);
        asetaArvo(malli, 7, 5, 1);
        asetaArvo(malli, 5, 7, 1);
        asetaArvo(malli, 1, 2, 1);

        asetaArvo(malli, 7, 5, 0);
        asetaArvo(malli, 5, 7, 0);
        asetaArvo(malli, 1, 2, 0);
    }

    @Test
    @Points("14-03.1")
    public void tyhjatPaikatPalauttaaKaikkiArvotJosAsetettuTyhjaksi() {
        assertTrue("Kun luodaan malli, jonka leveys on 10 ja korkeus on 10, ja kaikki kohdat alustetaan tyhjäksi, tulee metodin tyhjatPaikat palauttaa 100 tyhjää paikkaa. Nyt tyhjiä paikkoja palautettiin: " + malli.tyhjatPaikat().size(), malli.tyhjatPaikat().size() == 100);

        asetaArvo(malli, 0, 0, 1);

        assertTrue("Kun luodaan malli, jonka leveys on 10 ja korkeus on 10, ja jossa yksi paikka ei ole tyhjä, tulee metodin tyhjatPaikat palauttaa 99 tyhjää paikkaa. Nyt tyhjiä paikkoja palautettiin: " + malli.tyhjatPaikat().size(), malli.tyhjatPaikat().size() == 99);

        for (Piste tyhja : malli.tyhjatPaikat()) {
            assertFalse("Kun mallin kohta 0, 0 on asetettu ei-tyhjäksi, ei se saa olla tyhjien paikkojen listalla.", (int) tyhja.getX() == 0 && (int) tyhja.getY() == 0);
        }
    }

    @Test
    @Points("14-03.1")
    public void tyhjatPaikatPalautetaanOikein() {
        assertTrue("Kun luodaan malli, jonka leveys on 10 ja korkeus on 10, ja kaikki kohdat alustetaan tyhjäksi, tulee metodin tyhjatPaikat palauttaa 100 tyhjää paikkaa. Nyt tyhjiä paikkoja palautettiin: " + malli.tyhjatPaikat().size(), malli.tyhjatPaikat().size() == 100);

        Taulukko t = malli.getData();
        int arvojaAsetettu = 0;
        for (int x = 0; x < t.getLeveys(); x++) {
            for (int y = 0; y < t.getKorkeus(); y++) {
                asetaArvo(malli, x, y, 1);
                arvojaAsetettu++;
                assertTrue("Kun luodaan malli, jonka leveys on 10 ja korkeus on 10, ja johon on asetettu " + arvojaAsetettu + " arvoa, tulee metodin tyhjatPaikat palauttaa " + (100 - arvojaAsetettu) + " tyhjää paikkaa. Nyt tyhjiä paikkoja palautettiin: " + malli.tyhjatPaikat().size(), malli.tyhjatPaikat().size() == (100 - arvojaAsetettu));
            }
        }
    }

    @Test
    @Points("14-03.1")
    public void satunnaisetPaikatJaTyhjat() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            ArrayList<Piste> pisteet = new ArrayList<>();

            for (int j = 0; j < 5 + random.nextInt(); j++) {
                Piste p = new Piste(random.nextInt(10), random.nextInt(10));

                if (pisteet.contains(p)) {
                    continue;
                }

                pisteet.add(p);
            }

            pisteet.stream().forEach(p -> {
                asetaArvo(malli, p.getX(), p.getY(), 1);
            });

            tyhjenna(malli);

            assertTrue("Kun luodaan malli, jonka leveys on 10 ja korkeus on 10, ja kaikki kohdat alustetaan tyhjäksi, tulee metodin tyhjatPaikat palauttaa 100 tyhjää paikkaa. Nyt tyhjiä paikkoja palautettiin: " + malli.tyhjatPaikat().size(), malli.tyhjatPaikat().size() == 100);

            pisteet.stream().forEach(p -> {
                asetaArvo(malli, p.getX(), p.getY(), 1);
            });

            assertTrue("Kun luodaan malli, jonka leveys on 10 ja korkeus on 10, ja jossa on " + pisteet.size() + " ei tyhjää paikkaa, tulee metodin tyhjatPaikat palauttaa " + (100 - pisteet.size()) + " tyhjää paikkaa. Nyt tyhjiä paikkoja palautettiin: " + malli.tyhjatPaikat().size(), malli.tyhjatPaikat().size() == 100 - pisteet.size());

            pisteet.stream().forEach(p -> {
                assertFalse("Kun mallin kohta " + p.getX() + ", " + p.getY() + " on asetettu ei-tyhjäksi, ei se saa olla tyhjien paikkojen listalla.", malli.tyhjatPaikat().contains(p));
            });
        }
    }

    @Test
    @Points("14-03.2")
    public void haeTyytymattomat() {
        malli.asetaTyytyvaisyysRaja(5);

        asetaArvo(malli, 0, 0, 1);
        asetaArvo(malli, 1, 0, 1);
        asetaArvo(malli, 0, 1, 1);
        asetaArvo(malli, 1, 1, 1);

        asetaArvo(malli, 1, 2, 2);
        asetaArvo(malli, 2, 1, 2);
        asetaArvo(malli, 2, 2, 2);
        asetaArvo(malli, 3, 2, 2);
        asetaArvo(malli, 2, 3, 2);
        asetaArvo(malli, 3, 3, 2);
        asetaArvo(malli, 2, 0, 2);
        asetaArvo(malli, 0, 2, 2);

        assertTrue("Jos tyytyväisyysraja on 5 ja pisteellä on kolme samantyyppisiä naapuria, sen pitäisi olla tyytymätön.\n" + raportti(0, 0), malli.haeTyytymattomat().contains(new Piste(0, 0)));
        assertTrue("Jos tyytyväisyysraja on 5 ja pisteellä on kolme samantyyppistä naapuria, sen pitäisi olla tyytymätön.\n" + raportti(1, 1), malli.haeTyytymattomat().contains(new Piste(1, 1)));
        assertFalse("Jos tyytyväisyysraja on 5 ja henkilöllä on viisi samantyyppistä naapuria, ei sen pisteen pitäisi olla tyytymätön.\n" + raportti(2, 2), malli.haeTyytymattomat().contains(new Piste(2, 2)));
    }

    @Test
    @Points("14-03.2")
    public void haeTyytymattomat2() {
        malli.asetaTyytyvaisyysRaja(3);

        asetaArvo(malli, 0, 0, 1);
        asetaArvo(malli, 1, 0, 1);
        asetaArvo(malli, 0, 1, 1);
        asetaArvo(malli, 1, 1, 1);
        asetaArvo(malli, 2, 0, 1);

        asetaArvo(malli, 1, 2, 2);
        asetaArvo(malli, 2, 1, 2);
        asetaArvo(malli, 2, 2, 2);
        asetaArvo(malli, 3, 2, 2);
        asetaArvo(malli, 2, 3, 2);
        asetaArvo(malli, 3, 3, 2);

        assertFalse("Jos tyytyväisyysraja on 3 ja pisteellä on kolme samantyyppisiä naapuria, ei sen pitäisi olla tyytymätön.\n" + raportti(0, 0), malli.haeTyytymattomat().contains(new Piste(0, 0)));
        assertTrue("Jos tyytyväisyysraja on 3, ja pisteellä on kaksi samantyyppistä naapuria, pitäisi sen olla tyytymätön. Älä laske pistettä mukaan naapureihin!\n" + raportti(2, 0), malli.haeTyytymattomat().contains(new Piste(2, 0)));
        assertFalse("Jos tyytyväisyysraja on 3, ja pisteellä on viisi samantyyppistä naapuria, ei pisteen pitäisi olla tyytymätön.\n" + raportti(2, 2), malli.haeTyytymattomat().contains(new Piste(2, 2)));
    }

    private String raportti(int pisteX, int pisteY) {

        String tulostus = "Haettiin kohtaa " + pisteX + ", " + pisteY + " taulukosta:\n";
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                tulostus += annaArvo(malli, x, y) + " ";
            }
            tulostus += "\n";
        }

        tulostus += "(y-koordinaatin lasku alkaa vasemmasta yläkulmasta alaspäin)\n";
        return tulostus;

    }

    private void asetaArvo(Eriytymismalli malli, double x, double y, int arvo) {
        if (malli.getData() == null) {
            fail("Ennustemalli-luokan metodi getData palautti null-viitteen. Näin ei tule käydä.");
            return;
        }

        int ix = (int) x;
        int iy = (int) y;

        malli.getData().aseta(ix, iy, arvo);

        assertEquals("Arvon asettaminen ja hakeminen ei toiminut odotetusti. Kokeile:\n"
                + luonti
                + "malli.getData().aseta(" + ix + ", " + iy + ", " + arvo + ");\n"
                + "System.out.println(malli.getData().hae(" + ix + ", " + iy + ");\n", arvo, annaArvo(malli, ix, iy));
    }

    private int annaArvo(Eriytymismalli malli, double x, double y) {
        if (malli.getData() == null) {
            fail("Ennustemalli-luokan metodi getData palautti null-viitteen. Näin ei tule käydä.");
        }

        return malli.getData().hae((int) x, (int) y);
    }

    private void tyhjenna(Eriytymismalli malli) {
        if (malli.getData() == null) {
            fail("Ennustemalli-luokan metodi getData palautti null-viitteen. Näin ei tule käydä.");
        }

        Taulukko t = malli.getData();
        for (int x = 0; x < t.getLeveys(); x++) {
            for (int y = 0; y < t.getKorkeus(); y++) {
                asetaArvo(malli, x, y, 0);
            }
        }
    }
}
