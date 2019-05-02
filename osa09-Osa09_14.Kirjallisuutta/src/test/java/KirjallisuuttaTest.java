

import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.Rule;

public class KirjallisuuttaTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    @Points("09-14.1")
    public void tulostetaanKirjojenLukumaaraJaKirjat1() {
        String in = "aapinen\n3\nkukkonen\n4\npaapinen\n5\nkiukkunen\n1\nukkonen\n2\n\n";
        final List<String> input = Arrays.asList(in.toLowerCase().split("\n"));

        io.setSysIn(in);

        Paaohjelma.main(new String[]{});

        List<String> rivit = rivit();

        Optional<String> kirjat = rivit.stream().filter(r -> r.contains("5 kirjaa")).findFirst();
        assertTrue("Kun ohjelmaan syötetään viisi kirjaa, odotetaan, että tulostuksessa on merkkijono \"5 kirjaa\". Nyt ei ollut. Syöte oli:\n" + input, kirjat.isPresent());

        rivit = rivit.subList(rivit.indexOf(kirjat.get()), rivit.size());

        Set<String> nimetLoytyy = new HashSet<>();
        nimetLoytyy.add("aapinen");
        nimetLoytyy.add("kukkonen");
        nimetLoytyy.add("paapinen");
        nimetLoytyy.add("kiukkunen");
        nimetLoytyy.add("ukkonen");

        rivit.stream().forEach(rivi -> {
            for (String nimi : new ArrayList<>(nimetLoytyy)) {
                if (rivi.contains(nimi)) {
                    nimetLoytyy.remove(nimi);
                    return;
                }
            }
        });

        assertTrue("Kaikkien syötettyjen kirjojen pitäisi löytyä. Nyt ei löytynyt: " + nimetLoytyy + "\nSyöte oli:\n" + input, nimetLoytyy.isEmpty());
    }

    @Test
    @Points("09-14.2")
    public void jarjestetaanVuodenPerusteella() {
        String in = "Aapinen1\n30\nKukkonen1\n40\nAapinen2\n50\nkukkonen2\n10\nKukkonen3\n20\nAapinen3\n60\n\n";
        final List<String> input = Arrays.asList(in.toLowerCase().split("\n"));

        io.setSysIn(in);

        Paaohjelma.main(new String[]{});

        List<String> rivit = rivit();

        Optional<String> kirjat = rivit.stream().filter(r -> r.contains("6 kirjaa")).findFirst();
        assertTrue("Kun ohjelmaan syötetään kuusi kirjaa, odotetaan, että tulostuksessa on merkkijono \"6 kirjaa\". Nyt ei ollut. Syöte oli:\n" + input, kirjat.isPresent());

        rivit = rivit.subList(rivit.indexOf(kirjat.get()), rivit.size());

        Map<String, Integer> isompiaVuosia = new TreeMap<>();
        isompiaVuosia.put("10", 5);
        isompiaVuosia.put("20", 4);
        isompiaVuosia.put("30", 3);
        isompiaVuosia.put("40", 2);
        isompiaVuosia.put("50", 1);
        isompiaVuosia.put("60", 0);

        for (String rivi : rivit) {

            for (String vuosi : new ArrayList<>(isompiaVuosia.keySet())) {

                if (rivi.contains(vuosi)) {
                    int isompiaVuosiaPitaisiOllaJaljella = isompiaVuosia.remove(vuosi);

                    if (isompiaVuosia.size() > isompiaVuosiaPitaisiOllaJaljella) {
                        fail("Näyttäisi siltä että kirjat eivät ole järjestyksessä vuoden mukaan. Syöte oli:\n" + input + "\ntuloste oli:\n" + rivit);
                    }
                }
            }

        }

        assertTrue("Jokaisen syötetyn kirjan tulee esiintyä tulostuksessa. Nyt seuraavien ikävuosien kirjat eivät näkyneet tulostuksessa:\n" + isompiaVuosia.keySet(), isompiaVuosia.isEmpty());
    }

    @Test
    @Points("09-14.3")
    public void vuodenJaNimenMukaanJarjestyksessa1() {
        String in = "aapinen\n1990\nsorsanen\n1991\nkukkonen\n1989\n\n";
        final List<String> input = Arrays.asList(in.toLowerCase().split("\n"));

        io.setSysIn(in);

        Paaohjelma.main(new String[]{});

        List<String> rivit = rivit();

        Optional<String> kirjat = rivit.stream().filter(r -> r.contains("3 kirjaa")).findFirst();
        assertTrue("Kun ohjelmaan syötetään kolme kirjaa, odotetaan, että tulostuksessa on merkkijono \"3 kirjaa\". Nyt ei ollut. Syöte oli:\n" + input, kirjat.isPresent());

        rivit = rivit.subList(rivit.indexOf(kirjat.get()), rivit.size());

        Set<String> nimetLoytyy = new HashSet<>();
        nimetLoytyy.add("aapinen");
        nimetLoytyy.add("sorsanen");
        nimetLoytyy.add("kukkonen");

        for (String rivi : rivit) {
            if (rivi.contains("aapinen")) {
                nimetLoytyy.remove("aapinen");
                assertTrue("Kun kohdattiin aapinen, kohtaamattomat nimet olivat: " + nimetLoytyy, nimetLoytyy.size() == 1 && nimetLoytyy.contains("sorsanen"));
            }

            for (String nimi : new HashSet<>(nimetLoytyy)) {
                if (rivi.contains(nimi)) {
                    nimetLoytyy.remove(nimi);
                }
            }

        }

        assertTrue("Kaikkien nimien pitäisi löytyä, nyt ei löytynyt: " + nimetLoytyy, nimetLoytyy.isEmpty());
    }
    
    @Test
    @Points("09-14.3")
    public void vuodenJaNimenMukaanJarjestyksessa2() {
        String in = "bbbb\n1\naaaa\n1\ncccc\n1\ndddd\n0\n\n";
        final List<String> input = Arrays.asList(in.toLowerCase().split("\n"));

        io.setSysIn(in);

        Paaohjelma.main(new String[]{});

        List<String> rivit = rivit();

        Optional<String> kirjat = rivit.stream().filter(r -> r.contains("4 kirjaa")).findFirst();
        assertTrue("Kun ohjelmaan syötetään neljä kirjaa, odotetaan, että tulostuksessa on merkkijono \"4 kirjaa\". Nyt ei ollut. Syöte oli:\n" + input, kirjat.isPresent());

        rivit = rivit.subList(rivit.indexOf(kirjat.get()), rivit.size());
        
        List<String> odotettuJarjestys = new ArrayList<>();
        odotettuJarjestys.add("dddd");
        odotettuJarjestys.add("aaaa");
        odotettuJarjestys.add("bbbb");
        odotettuJarjestys.add("cccc");

        Set<String> nahdyt = new HashSet<>();

        for (String rivi : rivit) {
            
            String nahty = null;
            
            for (String odotettu : new ArrayList<>(odotettuJarjestys)) {
                if(rivi.contains(odotettu)) {
                    nahty = odotettu;
                    break;
                }
            }
            
            if(nahty == null) {
                continue;
            }
            
            if(odotettuJarjestys.indexOf(nahty) != 0) {
                fail("Kun syöte on:\n" + in + "\nOdotettu jarjestys on\ndddd, aaaa, bbbb, cccc.\nNyt " + nahty + " tuli liian aikaisin.");
            }
            
            odotettuJarjestys.remove(nahty);
        }

        assertTrue("Kaikkien nimien pitäisi löytyä, nyt ei löytynyt: " + odotettuJarjestys, odotettuJarjestys.isEmpty());
    }

    private List<String> rivit() {
        return Arrays.asList(io.getSysOut().toLowerCase().trim().split("\n"));
    }
}
