
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

@Points("09-11")
public class IhminenTest {

    String klassName = "Ihminen";
    Class c;
    Reflex.ClassRef<Object> klass;
    private Class ihminenClass;
    private final String vastaus = "compareTo-metodin tulee palauttaa positiivinen luku, mikäli this.palkka on pienempi kuin verrattavan palkka.\n"
            + "Kun palkat ovat yhtäsuuret, tulee palauttaa luku 0.\n"
            + "Kun verrattavalla on pienempi palkka, tulee compareTo-metodin palauttaa negatiivinen luku.\n";

    @Before
    public void setUp() {
        klass = Reflex.reflect(klassName);

        try {
            ihminenClass = ReflectionUtils.findClass(klassName);
        } catch (Throwable t) {
            fail("Onhan tehtävässä luokka Ihminen?");
        }
    }

    @Test
    public void onLuokkaIhminen() {
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    public void onImplementComparableIhminen() {
        String nimi = "Ihminen";
        Class kohdeLuokka;
        try {
            kohdeLuokka = ihminenClass;
            Class kohdeTaulukko[] = kohdeLuokka.getInterfaces();
            Class oikein[] = {java.lang.Comparable.class};
            for (int i = 0; i < kohdeTaulukko.length; i++) {
            }
            assertTrue("Varmista että luokka " + nimi + " toteuttaa (vain!) rajapinnan Comparable",
                    Arrays.equals(kohdeTaulukko, oikein));

        } catch (Throwable t) {
            fail("Toteuttaahan luokka " + nimi + " rajapinnan \"Comparable<Ihminen>\"");
        }
    }

    @Test
    public void onCompareToMetodi() throws Throwable {
        String metodi = "compareTo";

        Ihminen pekka = new Ihminen("Pekka", 1600);
        Ihminen arto = new Ihminen("Arto", 3500);

        assertTrue("tee luokalle " + klassName + " metodi public int " + metodi + "(Ihminen verrattava)",
                klass.method(pekka, metodi)
                .returning(int.class).taking(Ihminen.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi\n"
                + "Ihminen pekka = new Ihminen(\"Pekka\",1600);\n"
                + "Ihminen arto = new Ihminen(\"Arto\",3500);\n"
                + "pekka.compareTo(arto);";

        klass.method(pekka, metodi)
                .returning(int.class).taking(Ihminen.class).withNiceError(v).invoke(arto);
    }

    private Object teeIhminen(String nimi, int palkka) {
        try {
            Constructor ihminenConst = ReflectionUtils.requireConstructor(ihminenClass, String.class, int.class);
            Object ihminenObject = ReflectionUtils.invokeConstructor(ihminenConst, nimi, palkka);
            return ihminenObject;
        } catch (Throwable t) {
            fail("Onhan tehtävässä luokka \"Ihminen\"");
            return null;
        }
    }

    private Method ihmisCompareToMethod() {
        Method m = ReflectionUtils.requireMethod(ihminenClass, "compareTo", Ihminen.class);
        return m;
    }

    public int testaaKahta(String ekaNimi, int ekaPalkka, String tokaNimi, int tokaPalkka) {
        try {
            Object henkilo1 = teeIhminen(ekaNimi, ekaPalkka);
            Object henkilo2 = teeIhminen(tokaNimi, tokaPalkka);
            Method m = ihmisCompareToMethod();
//            int tulos = ReflectionUtils.invokeMethod(int.class, m, henkilo1, henkilo2);
            return ReflectionUtils.invokeMethod(int.class, m, henkilo1, henkilo2);
        } catch (Throwable ex) {

            fail("Olethan toteuttanut luokalla \"Ihminen\" metodin \"public int compareTo(Ihminen toinen)\".\n"
                    + "Toteuttaahan Ihminen-luokka myös rajapinnan Comparable?");
            return -111;
        }
    }

    @Test
    public void verrattavanPalkkaIsompi() {
        String ekaNimi = "Aku";
        String tokaNimi = "Roope";
        int ekaPalkka = 0;
        int tokaPalkka = Integer.MAX_VALUE;
        int tulos = testaaKahta(ekaNimi, ekaPalkka, tokaNimi, tokaPalkka);

        String lisaVihje = ""
                + "Ihminen eka = new Ihminen(" + ekaNimi + ", " + ekaPalkka + ");\n"
                + "Ihminen eka = new Ihminen(" + tokaNimi + ", " + tokaPalkka + ");\n"
                + "System.out.println(eka.compareTo(toka));\n"
                + "tulostui: "+tulos;

        if (tulos == -111) {
            fail("Olethan toteuttanut luokalla \"Ihminen\" metodin \"public int compareTo(Ihminen toinen)\".\n"
                    + "Toteuttaahan Ihminen-luokka myös rajapinnan Comparable?");
        } else {
            assertTrue(vastaus + "\n" + lisaVihje, tulos > 0);
        }
    }

    @Test
    public void verrattavanPalkkaPienempi() {
        String ekaNimi = "Roope";
        String tokaNimi = "Aku";
        int ekaPalkka = Integer.MAX_VALUE;
        int tokaPalkka = 0;

        int tulos = testaaKahta(ekaNimi, ekaPalkka, tokaNimi, tokaPalkka);

        String lisaVihje = ""
                + "Ihminen eka = new Ihminen(" + ekaNimi + ", " + ekaPalkka + ");\n"
                + "Ihminen eka = new Ihminen(" + tokaNimi + ", " + tokaPalkka + ");\n"
                + "System.out.println(eka.compareTo(toka));\n"
                + "tulostui: "+tulos;

        assertTrue(vastaus + "\n" + lisaVihje, tulos < 0);
    }

    @Test
    public void palkatSamat() {
        String ekaNimi = "Hessu";
        String tokaNimi = "Taavi";
        int ekaPalkka = 3000;
        int tokaPalkka = 3000;

        int tulos = testaaKahta(ekaNimi, ekaPalkka, tokaNimi, tokaPalkka);

        String lisaVihje = ""
                + "Ihminen eka = new Ihminen(" + ekaNimi + ", " + ekaPalkka + ");\n"
                + "Ihminen eka = new Ihminen(" + tokaNimi + ", " + tokaPalkka + ");\n"
                + "System.out.println(eka.compareTo(toka));\n"
                + "tulostui: "+tulos;

        assertTrue(vastaus + "\n" + lisaVihje, tulos == 0);
    }

    @Test
    public void lisaTesteja() {
        String ekaNimi = "Hessu";
        String tokaNimi = "Taavi";
        int ekaPalkka = 3000;
        int tokaPalkka = 30000;
        int vastausInt = testaaKahta(ekaNimi, ekaPalkka, tokaNimi, tokaPalkka);

        String lisaVihje = ""
                + "Ihminen eka = new Ihminen(" + ekaNimi + ", " + ekaPalkka + ");\n"
                + "Ihminen eka = new Ihminen(" + tokaNimi + ", " + tokaPalkka + ");\n"
                + "System.out.println(eka.compareTo(toka));\n"
                + "tulostui: "+vastausInt;

        assertTrue("compareToMetodisi vastasi väärin. Kun this.palkka on: " + ekaPalkka
                + ", ja verrattava.palkka on " + tokaPalkka + " antaa compareTo metodisi vastaukseski" + vastausInt + "\n"
                + lisaVihje, vastausInt > 0);


        ekaPalkka = 0;
        tokaPalkka = 0;
        vastausInt = testaaKahta(ekaNimi, ekaPalkka, tokaNimi, tokaPalkka);
        assertTrue("compareToMetodisi vastasi väärin. Kun this.palkka on: " + ekaPalkka
                + ", ja verrattava.palkka on " + tokaPalkka + " antaa compareTo metodisi vastaukseski" + vastausInt + "\n" + lisaVihje, vastausInt == 0);

        ekaPalkka = 300;
        tokaPalkka = 10;
        vastausInt = testaaKahta(ekaNimi, ekaPalkka, tokaNimi, tokaPalkka);
        assertTrue("compareToMetodisi vastasi väärin. Kun this.palkka on: " + ekaPalkka
                + ", ja verrattava.palkka on " + tokaPalkka + " antaa compareTo metodisi vastaukseski" + vastausInt + "\n" + lisaVihje, vastausInt < 0);

    }
}
