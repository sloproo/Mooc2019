
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.junit.*;
import static org.junit.Assert.*;

public class ArvosanatilastotTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    @Points("07-07.1")
    public void kaKaikki1() {
        io.setSysIn(syote(-42, 24, 42, 72, 80, 52) + "-1\n");
        try {
            Paaohjelma.main(new String[0]);
        } catch (Throwable t) {

        }

        String[] lines = io.getSysOut().split("\n");
        List<String> kaikkiKaRivi = Arrays.stream(lines).filter(l -> l.contains("kaikki") && l.contains("keskiarvo")).collect(Collectors.toList());
        assertTrue("Onhan tulostuksessasi rivi, jossa lukee \"Pisteiden keskiarvo (kaikki):\"?\nTulostus oli:\n" + io.getSysOut(), kaikkiKaRivi.size() == 1);
        assertTrue("Kun syöte on -42, 24, 42, 72, 80, 52, -1, tulostuksen pitäisi olla \"Pisteiden keskiarvo (kaikki): 54.0\". Nyt tulostus oli:\n" + kaikkiKaRivi.get(0), "Pisteiden keskiarvo (kaikki): 54.0".equals(kaikkiKaRivi.get(0).trim()));
    }

    @Test
    @Points("07-07.1")
    public void kaKaikki2() {
        io.setSysIn(syote(50, 51, 52) + "-1\n");
        try {
            Paaohjelma.main(new String[0]);
        } catch (Throwable t) {

        }

        String[] lines = io.getSysOut().split("\n");
        List<String> kaikkiKaRivi = Arrays.stream(lines).filter(l -> l.contains("kaikki") && l.contains("keskiarvo")).collect(Collectors.toList());
        assertTrue("Onhan tulostuksessasi rivi, jossa lukee \"Pisteiden keskiarvo (kaikki):\"?\nTulostus oli:\n" + io.getSysOut(), kaikkiKaRivi.size() == 1);
        assertTrue("Kun syöte on 50, 51, 52, -1, tulostuksen pitäisi olla \"Pisteiden keskiarvo (kaikki): 51.0\". Nyt tulostus oli:\n" + kaikkiKaRivi.get(0), "Pisteiden keskiarvo (kaikki): 51.0".equals(kaikkiKaRivi.get(0).trim()));
    }

    @Test
    @Points("07-07.2")
    public void kaHyvaksytyt1() {
        io.setSysIn(syote(-42, 24, 42, 72, 80, 52) + "-1\n");
        try {
            Paaohjelma.main(new String[0]);
        } catch (Throwable t) {

        }

        String[] lines = io.getSysOut().split("\n");
        List<String> hyvaksytytKaRivi = Arrays.stream(lines).filter(l -> l.contains("sytyt") && l.contains("keskiarvo")).collect(Collectors.toList());
        assertTrue("Onhan tulostuksessasi rivi, jossa lukee \"Pisteiden keskiarvo (hyväksytyt):\"?\nTulostus oli:\n" + io.getSysOut(), hyvaksytytKaRivi.size() == 1);
        assertTrue("Kun syöte on -42, 24, 42, 72, 80, 52, -1, tulostuksen pitäisi olla \"Pisteiden keskiarvo (hyväksytyt): 68.0\". Nyt tulostus oli:\n" + hyvaksytytKaRivi.get(0), hyvaksytytKaRivi.get(0).trim().endsWith("68.0"));
    }

    @Test
    @Points("07-07.2")
    public void kaHyvaksytyt2() {
        io.setSysIn(syote(69, 70, 71) + "-1\n");
        try {
            Paaohjelma.main(new String[0]);
        } catch (Throwable t) {

        }

        String[] lines = io.getSysOut().split("\n");
        List<String> hyvaksytytKaRivi = Arrays.stream(lines).filter(l -> l.contains("sytyt") && l.contains("keskiarvo")).collect(Collectors.toList());
        assertTrue("Onhan tulostuksessasi rivi, jossa lukee \"Pisteiden keskiarvo (hyväksytyt):\"?\nTulostus oli:\n" + io.getSysOut(), hyvaksytytKaRivi.size() == 1);
        assertTrue("Kun syöte on 69, 70, 71, -1, tulostuksen pitäisi olla \"Pisteiden keskiarvo (hyväksytyt): 70.0\". Nyt tulostus oli:\n" + hyvaksytytKaRivi.get(0), hyvaksytytKaRivi.get(0).trim().endsWith("70.0"));
    }

    @Test
    @Points("07-07.3")
    public void hyvProsentti1() {
        io.setSysIn("102\n"
                + "-4\n"
                + "33\n"
                + "77\n"
                + "99\n"
                + "1\n"
                + "-1\n");
        try {
            Paaohjelma.main(new String[0]);
        } catch (Throwable t) {

        }

        String[] lines = io.getSysOut().split("\n");
        List<String> hyvaksytytKaRivi = Arrays.stream(lines).filter(l -> l.contains("prosentti")).collect(Collectors.toList());
        assertTrue("Onhan tulostuksessasi rivi, jossa lukee \"Hyväksymisprosentti:\"?\nTulostus oli:\n" + io.getSysOut(), hyvaksytytKaRivi.size() == 1);
        assertTrue("Kun syöte on 102, -4, 33, 77, 99, 1, -1, tulostuksen pitäisi olla \"Hyväksymisprosentti: 50.0\". Nyt tulostus oli:\n" + hyvaksytytKaRivi.get(0), hyvaksytytKaRivi.get(0).trim().endsWith("50.0"));
    }

    @Test
    @Points("07-07.3")
    public void hyvProsentti2() {
        io.setSysIn(syote(49, 50, 51) + "-1\n");
        try {
            Paaohjelma.main(new String[0]);
        } catch (Throwable t) {

        }

        String[] lines = io.getSysOut().split("\n");
        List<String> hyvaksytytKaRivi = Arrays.stream(lines).filter(l -> l.contains("prosentti")).collect(Collectors.toList());
        assertTrue("Onhan tulostuksessasi rivi, jossa lukee \"Hyväksymisprosentti:\"?\nTulostus oli:\n" + io.getSysOut(), hyvaksytytKaRivi.size() == 1);
        assertTrue("Kun syöte on 49, 50, 51, -1, tulostuksessa pitäisi olla \"Hyväksymisprosentti: 66.666\". Nyt tulostus oli:\n" + hyvaksytytKaRivi.get(0), hyvaksytytKaRivi.get(0).trim().contains("66.666"));
    }

    @Test
    @Points("07-07.4")
    public void testi1() {
        int[] luvut = {70};
        int[] jakauma = {0, 0, 0, 1, 0, 0};
        double pros = 100;
        testaa(luvut, jakauma, pros);
    }

    @Test
    @Points("07-07.4")
    public void testi2() {
        int[] luvut = {62, 70};
        int[] jakauma = {0, 0, 1, 1, 0, 0};
        double pros = 100;
        testaa(luvut, jakauma, pros);
    }

    @Test
    @Points("07-07.4")
    public void testi3() {
        int[] luvut = {75};
        int[] jakauma = {0, 0, 0, 1, 0, 0};
        double pros = 100;
        testaa(luvut, jakauma, pros);
    }

    @Test
    @Points("07-07.4")
    public void testi4() {
        int[] luvut = {88};
        int[] jakauma = {0, 0, 0, 0, 1, 0};
        double pros = 100;
        testaa(luvut, jakauma, pros);
    }

    @Test
    @Points("07-07.4")
    public void testi5() {
        int[] luvut = {94};
        int[] jakauma = {0, 0, 0, 0, 0, 1};
        double pros = 100;
        testaa(luvut, jakauma, pros);
    }

    @Test
    @Points("07-07.4")
    public void testiMonta3() {
        int[] luvut = {44, 12, 81, 29, 70};
        int[] jakauma = {3, 0, 0, 1, 1, 0};
        double pros = 40;
        testaa(luvut, jakauma, pros);
    }

    @Test
    @Points("07-07.4")
    public void testiMonta4() {
        int[] luvut = {52, 12, 72, 82, 92};
        int[] jakauma = {1, 1, 0, 1, 1, 1};
        double pros = 80;
        testaa(luvut, jakauma, pros);
    }

    @Test
    @Points("07-07.4")
    public void testiMonta5() {
        int[] luvut = {34, 53, 62, 62, 61, 72, 73, 92, 96, 11};
        int[] jakauma = {2, 1, 3, 2, 0, 2};
        double pros = 80;
        testaa(luvut, jakauma, pros);
    }

    @Test
    @Points("07-07.4")
    public void vaaraSyote() {
        int[] luvut = {42, 71, 15, 72, -2};
        int[] jakauma = {2, 0, 0, 2, 0, 0};
        double pros = 50;
        try {
            testaa(luvut, jakauma, pros);
        } catch (AssertionError e) {
            fail("Muistathan että syötteet jotka eivät ole välillä 0-100 pitää jättää huomiotta!\n" + e);
        }
    }

    /*
     * helpers
     */
    private void testaa(int[] luvut, int[] jakauma, double pros) {
        io.setSysIn(syote(luvut) + "-1\n");
        Paaohjelma.main(new String[0]);
        String[] rivit = io.getSysOut().split("\n");

        String pros2 = ("" + pros).replace('.', ',');

        String rivi = haeRivi(rivit, "prosentti");
        try {
            assertTrue("syötteellä " + toS(luvut) + " hyväksymisprosentin tulisi olla " + pros + ", tulostui: \"" + rivi + "\"",
                    rivi.contains("" + pros) || rivi.contains(pros2));
        } catch (NullPointerException e) {
            fail("Tulostithan hyväksymisprosentin?\nsyötteellä " + toS(luvut) + " hyväksymisprosentin tulisi olla " + pros + ", tulostui: \"" + rivi + " \"");
        }
        assertFalse("tarkasta että ohjelma tulostaa rivin jolla teksti \"Hyväksymisprosentti:\"", rivi == null);
        for (int i = 0; i < 6; i++) {
            rivi = haeRivi(rivit, i + ":");
            tarkastaArvosana(i, jakauma[i], rivi, luvut);
        }
    }

    private void tarkastaArvosana(int i, int odotettu, String rivi, int[] luvut) {
        if (odotettu == 0) {
            assertFalse("syötteellä " + toS(luvut) + " arvosanan " + i + " ilmoittavalla rivillä ei saisi olla tähtiä, "
                    + "nyt tulostui \"" + rivi + "\"", rivi.contains("*"));
            return;
        }

        String tahdet = "";
        for (int j = 0; j < odotettu; j++) {
            tahdet += "*";
        }

        assertTrue("syötteellä " + toS(luvut) + " pitäisi tulostaa rivi \"" + i + ": " + tahdet
                + "  nyt tulostui \"" + rivi + "\"", rivi.contains(tahdet));
        assertFalse("syötteellä " + toS(luvut) + " pitäisi tulostaa rivi \"" + i + ": " + tahdet
                + "  nyt tulostui \"" + rivi + "\"", rivi.contains(tahdet + "*"));
    }

    private void testaaTuloste(int[] luvut) {
        io.setSysIn(syote(luvut) + "-1\n");
        Paaohjelma.main(new String[0]);
        String[] rivit = io.getSysOut().split("\n");

        String rivi = haeRivi(rivit, "jakauma");
        assertFalse("tarkasta että ohjelma tulostaa rivin jolla teksti \"Arvosanajakauma:\"", rivi == null);
        rivi = haeRivi(rivit, "sentti");
        assertFalse("tarkasta että ohjelma tulostaa rivin jolla teksti \"Hyväksymisprosentti:\"", rivi == null);
        for (int i = 0; i < 6; i++) {
            rivi = haeRivi(rivit, i + ":");
            assertFalse("tarkasta että ohjelma tulostaa rivin jolla teksti \"" + i + ":\"", rivi == null);
        }
    }

    private void testaaSyote(int[] luvut) {
        io.setSysIn(syote(luvut) + "-1\n");
        try {
            Paaohjelma.main(new String[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("ohjelmasi viittaa taulukon tai listan ulkopuolelle syötteellä " + toS(luvut));
        } catch (NoSuchElementException e) {
            fail("ohjelmasi pitäisi pysähtyä syötteellä " + toS(luvut));
        } catch (ArithmeticException e) {
            if (toS(luvut).equals("-1")) {
                fail("ohjelmasi tekee nollallajaon kun syötteenä on pelkkä -1 eli yhtään numeroa ei anneta."
                        + "\nVika lienee hyväksymisprosentin laskemisessa. Siellä tulee ottaa huomioon tilanne jossa"
                        + "yhtään syötettyä numeroa ei ole");
            } else {
                fail("ohjelmasi tekee nollallajaon syötteellä " + toS(luvut));
            }
        } catch (Exception e) {
            fail("jotain odottamatonta tapahtui syötteellä " + toS(luvut) + " lisätietoa " + e.getMessage());
        }
    }

    private String syote(int... luvut) {
        String mj = "";

        for (int luku : luvut) {
            mj += luku + "\n";
        }

        return mj;
    }

    private String toS(int[] luvut) {
        if (luvut.length == 0) {
            return "-1";
        }

        return Arrays.toString(luvut).replace("[", "").replace("]", "") + ", -1";
    }

    private String haeRivi(String[] rivit, String haettava) {
        for (String rivi : rivit) {
            if (rivi.contains(haettava)) {
                return rivi;
            }
        }

        return null;
    }
}
