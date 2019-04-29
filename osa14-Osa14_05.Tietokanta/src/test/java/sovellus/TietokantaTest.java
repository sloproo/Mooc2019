package sovellus;

import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

@Points("14-05")
public class TietokantaTest {

    @Rule
    public MockStdio io = new MockStdio();

    private TodoDao tietokanta;
    private String tietokantaTiedosto;

    @Before
    public void setup() {
        this.tietokantaTiedosto = "test-" + UUID.randomUUID().toString().substring(0, 6);
        tietokanta = new TodoDao("jdbc:h2:./" + tietokantaTiedosto);
    }

    @After
    public void teardown() {
        try {
            new File(tietokantaTiedosto + ".mv.db").delete();
            new File(tietokantaTiedosto + ".trace.db").delete();
        } catch (Exception e) {

        }

    }

    @Test(timeout = 2500)
    public void tietokannassaOlevaaTietoaListataan() throws SQLException {
        String syote = "1\nx\n";

        Scanner s = new Scanner(syote);
        tietokanta.lisaa(new Todo("hauki", "on kala", Boolean.FALSE));
        tietokanta.lisaa(new Todo("kuusi", "on luku", Boolean.FALSE));

        Kayttoliittyma kl = new Kayttoliittyma(s, tietokanta);

        try {
            kl.kaynnista();
        } catch (SQLException ex) {
            fail("Virhe sovelluksen suorittamisessa: " + ex.getMessage());
        }

        String virhe = "Kun tietokantaan on lisätty kaksi tehtävää: hauki ja kuusi, tulee niiden ja niiden kuvausten esiintyä listauksessa.\n"
                + "Syöte oli:\n" + syote + "\n"
                + "Kokeile listata tehtävät koodilla:\n"
                + "Scanner s = new Scanner(System.in);\n"
                + "TodoDao td = new TodoDao(\"jdbc:h2:./tietokantatiedosto\");\n"
                + "td.lisaa(new Todo(\"hauki\", \"on kala\", Boolean.FALSE));\n"
                + "td.lisaa(new Todo(\"kuusi\", \"on luku\", Boolean.FALSE));\n"
                + "Kayttoliittyma kl = new Kayttoliittyma(s, td);\n"
                + "kl.kaynnista();";

        assertTrue(virhe, lines().stream().filter(l -> l.contains("hauki") && l.contains("on kala")).count() == 1);
        assertTrue(virhe, lines().stream().filter(l -> l.contains("kuusi") && l.contains("on luku")).count() == 1);
    }

    @Test(timeout = 2500)
    public void tietokannassaOlevaaTietoaListataanSatunnainen() throws SQLException {
        String syote = "1\nx\n";

        Scanner s = new Scanner(syote);
        String s1 = UUID.randomUUID().toString().substring(0, 4);
        String s2 = UUID.randomUUID().toString().substring(0, 4);

        tietokanta.lisaa(new Todo(s1, s1 + " on jotain", Boolean.FALSE));
        tietokanta.lisaa(new Todo(s2, s2 + " on muuta", Boolean.FALSE));
        Kayttoliittyma kl = new Kayttoliittyma(s, tietokanta);

        try {
            kl.kaynnista();
        } catch (SQLException ex) {
            fail("Virhe sovelluksen suorittamisessa: " + ex.getMessage());
        }

        String virhe = "Kun tietokantaan on lisätty kaksi tehtävää: " + s1 + " ja " + s2 + ", tulee niiden ja niiden kuvausten esiintyä listauksessa.\n"
                + "Syöte oli:\n" + syote + "\n"
                + "Kokeile listata tehtävät koodilla:\n"
                + "Scanner s = new Scanner(System.in);\n"
                + "TodoDao td = new TodoDao(\"jdbc:h2:./tietokantatiedosto\");\n"
                + "td.lisaa(new Todo(\"" + s1 + "\", \"" + s1 + " on jotain\", Boolean.FALSE));\n"
                + "td.lisaa(new Todo(\"" + s2 + "\", \"" + s2 + " on muuta\", Boolean.FALSE));\n"
                + "Kayttoliittyma kl = new Kayttoliittyma(s, td);\n"
                + "kl.kaynnista();";

        assertTrue(virhe, lines().stream().filter(l -> l.contains(s1) && l.contains(s1 + " on jotain")).count() == 1);
        assertTrue(virhe, lines().stream().filter(l -> l.contains(s2) && l.contains(s2 + " on muuta")).count() == 1);
    }

    @Test(timeout = 2500)
    public void tietoaEiListataElleiPyydeta() throws SQLException {
        String syote = "x\n";

        Scanner s = new Scanner(syote);
        String s1 = UUID.randomUUID().toString().substring(0, 4);
        String s2 = UUID.randomUUID().toString().substring(0, 4);

        tietokanta.lisaa(new Todo(s1, s1 + " on jotain", Boolean.FALSE));
        tietokanta.lisaa(new Todo(s2, s2 + " on muuta", Boolean.FALSE));
        Kayttoliittyma kl = new Kayttoliittyma(s, tietokanta);

        try {
            kl.kaynnista();
        } catch (SQLException ex) {
            fail("Virhe sovelluksen suorittamisessa: " + ex.getMessage());
        }

        String virhe = "Tietokannassa olevien tietojen ei tule esiintyä tulostuksessa ellei listausta pyydetä erikseen.\n"
                + "Syöte oli:\n" + syote + "\n"
                + "Kokeile listata tehtävät koodilla:\n"
                + "Scanner s = new Scanner(System.in);\n"
                + "TodoDao td = new TodoDao(\"jdbc:h2:./tietokantatiedosto\");\n"
                + "td.lisaa(new Todo(\"" + s1 + "\", \"" + s1 + " on jotain\", Boolean.FALSE));\n"
                + "td.lisaa(new Todo(\"" + s2 + "\", \"" + s2 + " on muuta\", Boolean.FALSE));\n"
                + "Kayttoliittyma kl = new Kayttoliittyma(s, td);\n"
                + "kl.kaynnista();";

        assertTrue(virhe, lines().stream().filter(l -> l.contains(s1) && l.contains(s1 + " on jotain")).count() == 0);
        assertTrue(virhe, lines().stream().filter(l -> l.contains(s2) && l.contains(s2 + " on muuta")).count() == 0);
    }

    @Test(timeout = 2500)
    public void lisaysLisaaTietokantaan() throws SQLException {
        String nimi = UUID.randomUUID().toString().substring(0, 4);
        String kuvaus = UUID.randomUUID().toString().substring(0, 4);
        String syote = "2\n" + nimi + "\n" + kuvaus + "\nx\n";

        Scanner s = new Scanner(syote);
        Kayttoliittyma kl = new Kayttoliittyma(s, tietokanta);

        try {
            kl.kaynnista();
        } catch (SQLException ex) {
            fail("Virhe sovelluksen suorittamisessa: " + ex.getMessage());
        }

        String virhe = "Käyttöliittymässä lisättyjen tehtävien tulee päätyä tietokantaan.\n"
                + "Syöte oli:\n" + syote + "\n";

        assertFalse(virhe + "Nyt tietokanta oli tyhjä.", tietokanta.listaa().isEmpty());
        assertFalse(virhe + "Nyt tietokantaan oli päätynyt liian monta tehtävää.", tietokanta.listaa().size() > 1);

        Todo t = tietokanta.listaa().get(0);
        assertEquals(virhe + "Nyt nimeksi oli päätynyt " + t.getNimi(), nimi, t.getNimi());
        assertEquals(virhe + "Nyt kuvaukseksi oli päätynyt " + t.getKuvaus(), kuvaus, t.getKuvaus());
        assertFalse(virhe + "Odotettiin että tehtävä ei olisi valmis (valmis = false).\nNyt tehtävän valmis-muuttujan arvo oli " + t.getValmis(), t.getValmis());
    }

    @Test(timeout = 2500)
    public void asetaValmiiksi() throws SQLException {
        tietokanta.lisaa(new Todo("koe", "kertaa kokeeseen", false));
        assertFalse("Kun tehtävä tallennetaan tietokantaan, sen pitäisi olla asetettu tekemättömäksi (valmis = false). ", tietokanta.listaa().get(0).getValmis());

        Todo teht = tietokanta.listaa().get(0);
        int id = teht.getId();

        String syote = "3\n" + id + "\nx\n";
        Scanner s = new Scanner(syote);
        Kayttoliittyma kl = new Kayttoliittyma(s, tietokanta);

        try {
            kl.kaynnista();
        } catch (SQLException ex) {
            fail("Virhe sovelluksen suorittamisessa: " + ex.getMessage());
        }

        String virhe = "Käyttöliittymässä tehtyjen muutosten pitää näkyä tietokannassa.\n"
                + "Kun tietokannassa on tehtävä: " + teht + "\n"
                + "Ja syöte on:\n" + syote + "\n";

        assertTrue(virhe + "Ei tietokannassa olevien tehtävien määrän tule muuttua.", tietokanta.listaa().size() == 1);
        assertTrue(virhe + "Tulee valmis-muuttujan arvoksi päivittyä true.", tietokanta.listaa().get(0).getValmis());

    }

    @Test(timeout = 2500)
    public void asetaValmiiksiUseampi() throws SQLException {
        tietokanta.lisaa(new Todo("koe 1", "kertaa kokeeseen", false));
        tietokanta.lisaa(new Todo("koe 2", "kertaa kokeeseen", false));
        tietokanta.lisaa(new Todo("koe 3", "kertaa kokeeseen", false));
        tietokanta.lisaa(new Todo("koe 4", "kertaa kokeeseen", false));
        assertTrue("Kun tehtävä tallennetaan tietokantaan, sen pitäisi olla asetettu tekemättömäksi (valmis = false). ", tietokanta.listaa().stream().filter(t -> t.getValmis()).count() == 0);

        List<Integer> idt = tietokanta.listaa().stream().map(t -> t.getId()).distinct().unordered().collect(Collectors.toList());
        assertEquals("Kun tietokantaan on tallennettu neljä tehtävää, pitäisi siellä olla neljä eri id:tä.\nTätä virhettä ei pitäisi tapahtua ellet ole muokannut tiedostoja, joita ei pitänyt muokata.", 4, idt.size());

        String syote = "3\n" + idt.get(0) + "\n3\n" + idt.get(1) + "\nx\n";
        Scanner s = new Scanner(syote);
        Kayttoliittyma kl = new Kayttoliittyma(s, tietokanta);

        try {
            kl.kaynnista();
        } catch (SQLException ex) {
            fail("Virhe sovelluksen suorittamisessa: " + ex.getMessage());
        }

        for (int id : idt) {
            assertTrue("Tietokannassa olevien tehtävien id-arvojen ei tule muuttua päivityksen yhteydessä.", tietokanta.listaa().stream().filter(t -> t.getId() == id).count() == 1);
        }

        assertTrue("Kun tehtävä asetetaan tehdyksi käyttöliittymästä, sen pitäisi muuttua tehdyksi myös tietokannassa.", tietokanta.listaa().stream().filter(t -> t.getId() == idt.get(0)).map(t -> t.getValmis()).findFirst().get());
        assertTrue("Kun tehtävä asetetaan tehdyksi käyttöliittymästä, sen pitäisi muuttua tehdyksi myös tietokannassa.", tietokanta.listaa().stream().filter(t -> t.getId() == idt.get(1)).map(t -> t.getValmis()).findFirst().get());
        assertFalse("Tietyn tehtävän asettaminen tehdyksi ei tule muuttaa muita tietokannassa olevia tehtäviä.", tietokanta.listaa().stream().filter(t -> t.getId() == idt.get(2)).map(t -> t.getValmis()).findFirst().get());
        assertFalse("Tietyn tehtävän asettaminen tehdyksi ei tule muuttaa muita tietokannassa olevia tehtäviä.", tietokanta.listaa().stream().filter(t -> t.getId() == idt.get(3)).map(t -> t.getValmis()).findFirst().get());

    }

    @Test(timeout = 2500)
    public void poistaYksi() throws SQLException {
        tietokanta.lisaa(new Todo("koe", "kertaa kokeeseen", false));

        Todo teht = tietokanta.listaa().get(0);
        int id = teht.getId();

        String syote = "4\n" + id + "\nx\n";
        Scanner s = new Scanner(syote);
        Kayttoliittyma kl = new Kayttoliittyma(s, tietokanta);

        try {
            kl.kaynnista();
        } catch (SQLException ex) {
            fail("Virhe sovelluksen suorittamisessa: " + ex.getMessage());
        }

        String virhe = "Käyttöliittymässä tehdyn poiston tulee poistaa tehtävä myös tietokannasta.\n"
                + "Kun tietokannassa on tehtävä: " + teht + "\n"
                + "Ja syöte on:\n" + syote + "\n";

        assertTrue(virhe + "Pitäisi tietokannassa olla ohjelman suorituksen jälkeen nolla riviä.", tietokanta.listaa().isEmpty());

    }

    @Test(timeout = 2500)
    public void poistaUseampi() throws SQLException {
        tietokanta.lisaa(new Todo("koe 1", "kertaa kokeeseen", false));
        tietokanta.lisaa(new Todo("koe 2", "kertaa kokeeseen", false));
        tietokanta.lisaa(new Todo("koe 3", "kertaa kokeeseen", false));
        tietokanta.lisaa(new Todo("koe 4", "kertaa kokeeseen", false));

        List<Integer> idt = tietokanta.listaa().stream().map(t -> t.getId()).distinct().unordered().collect(Collectors.toList());
        assertEquals("Kun tietokantaan on tallennettu neljä tehtävää, pitäisi siellä olla neljä eri id:tä.\nTätä virhettä ei pitäisi tapahtua ellet ole muokannut tiedostoja, joita ei pitänyt muokata.", 4, idt.size());

        String syote = "4\n" + idt.get(0) + "\n4\n" + idt.get(1) + "\nx\n";
        Scanner s = new Scanner(syote);
        Kayttoliittyma kl = new Kayttoliittyma(s, tietokanta);

        try {
            kl.kaynnista();
        } catch (SQLException ex) {
            fail("Virhe sovelluksen suorittamisessa: " + ex.getMessage());
        }

        assertTrue("Kun tehtävä poistetaan käyttöliittymän kautta, sitä ei pitäisi enää löytyä tietokannasta.", tietokanta.listaa().stream().filter(t -> t.getId() == idt.get(0)).count() == 0);
        assertTrue("Kun tehtävä poistetaan käyttöliittymän kautta, sitä ei pitäisi enää löytyä tietokannasta.", tietokanta.listaa().stream().filter(t -> t.getId() == idt.get(1)).count() == 0);
        assertTrue("Tietyn tehtävän poistamisen ei pitäisi johtaa muiden tehtävien poistamiseen.", tietokanta.listaa().stream().filter(t -> t.getId() == idt.get(2)).count() == 1);
        assertTrue("Tietyn tehtävän poistamisen ei pitäisi johtaa muiden tehtävien poistamiseen.", tietokanta.listaa().stream().filter(t -> t.getId() == idt.get(3)).count() == 1);

    }

    private List<String> lines() {
        return Arrays.asList(io.getSysOut().split("\n"));
    }
}
