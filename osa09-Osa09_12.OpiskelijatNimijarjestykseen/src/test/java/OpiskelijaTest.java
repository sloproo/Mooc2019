
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

@Points("09-12")
public class OpiskelijaTest {

    Class opiskelijaClass;
    String klassName = "Opiskelija";
    Reflex.ClassRef<Object> klass;

    @Before
    public void setUp() {
        klass = Reflex.reflect(klassName);

        try {
            opiskelijaClass = ReflectionUtils.findClass(klassName);
        } catch (Throwable t) {
            fail("Onhan tehtävässä luokka \"Opiskelija\"?");
        }
    }

    @Test
    public void onLuokka() {
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    public void onImplementComparableOpiskelija() {
        String nimi = "Opiskelija";
        Class kl;
        try {
            kl = opiskelijaClass;
            Class is[] = kl.getInterfaces();
            Class oikein[] = {java.lang.Comparable.class};
            for (int i = 0; i < is.length; i++) {
            }
            assertTrue("Varmista että " + nimi + " toteuttaa (vain!) rajapinnan Comparable",
                    Arrays.equals(is, oikein));

        } catch (Throwable t) {
            fail("Toteuttaahan luokka " + nimi + " rajapinnan \"Comparable<Opiskelija>\"");
        }
    }

    @Test
    public void onCompareToMetodi() throws Throwable {
        String metodi = "compareTo";

        Opiskelija pekka = new Opiskelija("Pekka");
        Opiskelija arto = new Opiskelija("Arto");

        assertTrue("tee luokalle " + klassName + " metodi public int " + metodi + "(Opiskelija verrattava)",
                klass.method(pekka, metodi)
                        .returning(int.class).taking(Opiskelija.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi\n"
                + "Opiskelija pekka = new Opiskelija(\"Pekka\");\n"
                + "Opiskelija arto = new Opiskelija(\"Arto\");\n"
                + "pekka.compareTo(arto);";

        klass.method(pekka, metodi)
                .returning(int.class).taking(Opiskelija.class).withNiceError(v).invoke(arto);

        try {
            ReflectionUtils.requireMethod(opiskelijaClass, int.class, "compareTo", Opiskelija.class);
        } catch (Throwable t) {
            fail("Olethan luonut metodin \"public int compareTo(Opiskelija toinen)\"");
        }
    }

    private Object teeOpiskelija(String nimi) {
        return new Opiskelija(nimi);
    }

    private Method opiskelijaCompareToMethod() {
        Method m = ReflectionUtils.requireMethod(opiskelijaClass, "compareTo", Opiskelija.class);
        return m;
    }

    @Test
    public void testaaCompareTo() {
        try {
            Object h1 = teeOpiskelija("Ville");
            Object h2 = teeOpiskelija("Aapo");
            Method m = opiskelijaCompareToMethod();
            int tulos = ReflectionUtils.invokeMethod(int.class, m, h1, h2);

        } catch (Throwable ex) {

            fail("Olethan toteuttanut luokalla \"Opiskelija\" metodin \"public int compareTo(Opiskelija toinen)\".\n"
                    + "Toteuttaahan Opiskelija-luokka myös rajapinnan Comparable<Opiskelija>?");
        }
    }

    @Test
    public void toteuttaaComparablen() {
        try {
            assertTrue("Opiskelija-luokkasi ei toteuta rajapintaa Comparable!", opiskelijaClass.getInterfaces()[0].equals(Comparable.class));
        } catch (Throwable t) {
            fail("Opiskelija-luokkasi ei toteuta rajapintaa Comparable!");
        }
    }

    public int testaaKahta(String ekaNimi, String tokaNimi) {
        try {
            Object h1 = teeOpiskelija(ekaNimi);
            Object h2 = teeOpiskelija(tokaNimi);
            Method m = opiskelijaCompareToMethod();

            int tulos = ReflectionUtils.invokeMethod(int.class, m, h1, h2);
            return tulos;
        } catch (Throwable ex) {

            fail("Olethan toteuttanut luokalla \"Opiskelija\" metodin \"public int compareTo(Opiskelija toinen)\".\n"
                    + "Toteuttaahan Opiskelija-luokka myös rajapinnan Comparable<Opiskelija>?");
            return -111;
        }
    }

    //Tarkistaa listan opiskelijoita kerrallaan
    public void onkoJarjestyksessa(List<String> lista) {
        Collections.sort(lista);
        for (int i = 0; i < lista.size() - 1; i++) {
            if (testaaKahta(lista.get(i), (lista.get(i + 1))) > 0) {
                fail("Ongelma koodilla: \n"
                        + "Opiskelija eka = new Opiskelija(\"" + lista.get(i) + "\");\n"
                        + "Opiskelija toka = new Opiskelija(\"" + lista.get(i + 1) + "\");\n"
                        + "System.out.println(eka.compareTo(toka));\n"
                        + "tulostui: " + testaaKahta(lista.get(i), lista.get(i + 1)));
            }
        }
    }

    public void testaa(String eka, String toka) {
        int vastaus = testaaKahta(eka, toka);
        String t = eka.compareTo(toka) > 0 ? "positiivinen" : "negatiivinen";
        boolean tulos = eka.compareTo(toka) > 0 ? vastaus > 0 : vastaus < 0;

        assertTrue("tuloksen olisi pitänyt olla " + t + " luku\n"
                + "Opiskelija eka = new Opiskelija(\"" + eka + "\");\n"
                + "Opiskelija toka = new Opiskelija(\"" + toka + "\");\n"
                + "eka.compareTo(toka)\n"
                + "tulos oli: " + vastaus, tulos);
    }

    @Test
    public void eiJarjestyksessa() {
        String[][] sanat = {
            {"Pekka", "Aku"},
            {"Aku", "Aapo"},
            {"Gödel", "Dijkstra"},
            {"Jukka", "Jaana"},
            {"Arto", "Edsger"},
            {"Kalle", "Kaarle"}
        };

        for (String[] strings : sanat) {
            testaa(strings[0], strings[1]);
        }

    }

    @Test
    public void testaaSamatNimet() {
        String eka = "Aku";
        String toka = "Aku";
        testaaSama(eka, toka);

        eka = "Aapo";
        toka = "Aapo";
        testaaSama(eka, toka);

        eka = "Pelle";
        toka = "Pelle";
        testaaSama(eka, toka);
    }

    private void testaaSama(String eka, String toka) {
        int vastaus = testaaKahta(eka, toka);
        assertEquals("Opiskelija eka = new Opiskelija(\"" + eka + "\");\n"
                + "Opiskelija toka = new Opiskelija(\"" + toka + "\");\n"
                + "eka.compareTo(toka);", (int) vastaus, 0);
    }
}
