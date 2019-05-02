

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

@Points("09-17.1")
public class A_KorttiTest extends Apu {

    Class korttiClass;
    String klassName = "Kortti";
    String fullName = klassName;
    Reflex.ClassRef<Object> klass;

    @Before
    public void setUp() {
        klass = Reflex.reflect(fullName);

        try {
            korttiClass = ReflectionUtils.findClass(fullName);
        } catch (Throwable t) {
            fail("Onhan tehtävässä luokka \"" + klassName + "\"?");
        }
        
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    public void onImplementComparableKortti() {
        Class kl;
        try {
            kl = korttiClass;
            Class is[] = kl.getInterfaces();
            Class oikein[] = {java.lang.Comparable.class};
            assertTrue("Varmista että " + klassName + " toteuttaa (vain!) rajapinnan Comparable",
                    Arrays.equals(is, oikein));

        } catch (Throwable t) {
            fail("Toteuttaahan luokka " + klassName + " rajapinnan \"Comparable<Kortti>\"");
        }
    }

    @Test
    public void onCompareToMetodi() throws Throwable {
        String metodi = "compareTo";

        Kortti eka = new Kortti(14, Maa.HERTTA);
        Kortti toka = new Kortti(10, Maa.RISTI);

        assertTrue("tee luokalle " + klassName + " metodi public int " + metodi + "(Kortti verrattava)",
                klass.method(eka, metodi)
                        .returning(int.class).taking(Kortti.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi\n"
                + "Kortti eka = new Kortti(14, Maa.HERTTA);\n"
                + "Kortti toka = new Kortti(10, Maa.RISTI);\n"
                + "eka.compareTo(toka);";

        klass.method(eka, metodi)
                .returning(int.class).taking(Kortti.class).withNiceError(v).invoke(toka);

        try {
            ReflectionUtils.requireMethod(korttiClass, int.class, "compareTo", Kortti.class);
        } catch (Throwable t) {
            fail("Olethan luonut metodin \"public int compareTo(Kortti toinen)\"");
        }
    }

    private Method compareToMethod() {
        Method m = ReflectionUtils.requireMethod(korttiClass, "compareTo", Kortti.class);
        return m;
    }

    @Test
    public void testaaCompareTo() {
        try {
            Object h1 = new Kortti(2, Maa.HERTTA);
            Object h2 = new Kortti(2, Maa.HERTTA);
            Method m = compareToMethod();
            int tulos = ReflectionUtils.invokeMethod(int.class, m, h1, h2);

        } catch (Throwable ex) {

            fail("Olethan toteuttanut luokalla \"Kortti\" metodin \"public int compareTo(Kortti toinen)\".\n"
                    + "Toteuttaahan Kortti-luokka myös rajapinnan Comparable<Kortti>?");
        }
    }

    @Test
    public void toteuttaaComparablen() {
        try {
            assertTrue("Kortti-luokkasi ei toteuta rajapintaa Comparable!", korttiClass.getInterfaces()[0].equals(Comparable.class));
        } catch (Throwable t) {
            fail("Kortti-luokkasi ei toteuta rajapintaa Comparable!");
        }
    }

    @Test
    public void testaa() {
        testaa(2, 2);
        testaa(5, 2);
        testaa(14, 3);

        int[][] luvut = {
            {4, 2, 5, 2},
            {3, 2, 4, 3},
            {6, 2, 8, 3},
            {10, 2, 10, 3},
            {11, 1, 11, 2}
        };

        for (int[] is : luvut) {
            testaa(is[0], is[1], is[2], is[3], false);
            testaa(is[2], is[3], is[0], is[1], true);
        }
    }

    public int testaaKahta(Kortti h1, Kortti h2) {
        try {
            Method m = compareToMethod();

            int tulos = ReflectionUtils.invokeMethod(int.class, m, h1, h2);
            return tulos;
        } catch (Throwable ex) {

            fail("Olethan toteuttanut luokalla \"Kortti\" metodin \"public int compareTo(Kortti toinen)\".\n"
                    + "Toteuttaahan Kortti-luokka myös rajapinnan Comparable<Kortti>?");
            return -111;
        }
    }

    public void testaa(int a1, int m1, int a2, int m2, boolean pos) {
        int vastaus = testaaKahta(new Kortti(a1, m(m1)), new Kortti(a2, m(m2)));
        String t = pos ? "positiivinen" : "negatiivinen";
        boolean tulos = pos ? vastaus > 0 : vastaus < 0;

        assertTrue("tuloksen olisi pitänyt olla " + t + " luku\n"
                + "Kortti eka = new Kortti(" + a1 + "," + m(m1) + ");\n"
                + "Kortti toka = new Kortti(" + a2 + "," + m(m2) + ");\n"
                + "eka.compareTo(toka)\n"
                + "tulos oli: " + vastaus, tulos);
    }

    public void testaa(int a1, int m1) {
        int vastaus = testaaKahta(new Kortti(a1, m(m1)), new Kortti(a1, m(m1)));

        assertEquals(
                "Kortti eka = new Kortti(" + a1 + "," + m(m1) + ");\n"
                + "Kortti toka = new Kortti(" + a1 + "," + m(m1) + ");\n"
                + "eka.compareTo(toka)", 0, vastaus);
    }

}
