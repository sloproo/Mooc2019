
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import org.junit.*;
import static org.junit.Assert.*;

public class JarjestaminenTest {

    String klassName = "Paaohjelma";
    Reflex.ClassRef<Object> klass;

    @Before
    public void justForKicks() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    @Points("07-04.1")
    public void onLuokka() {
        klass = Reflex.reflect(klassName);
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    @Points("07-04.1")
    public void onMetodiPienin() throws Throwable {
        String metodi = "pienin";
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        assertTrue("tee luokkaan Paaohjelma metodi public static int pienin(int[] taulukko)",
                klass.staticMethod(metodi).returning(int.class).taking(int[].class).isPublic());

        String v = "Ongelman aiheutti \n"
                + "int[] t = {2, 1, 3, 0};\n"
                + "int p = pienin(t);\n";
        int[] t = {2, 1, 3, 0};
        klass.staticMethod(metodi).returning(int.class).taking(int[].class).withNiceError(v).invoke(t);
    }

    @Points("07-04.1")
    @Test
    public void eiKiellettyjaKomentoja0() {
        noForbiddens();
    }

    @Points("07-04.1")
    @Test
    public void pieninEiMuutaTaulukonSisaltoa() {
        int[] t = {2, 1, 3, 0};
        pienin(t);

        assertTrue("metodi pienin ei saa muuttaa parametrina olevan taulukon sisältöä", t[0] == 2);
        assertTrue("metodi pienin ei saa muuttaa parametrina olevan taulukon sisältöä", t[1] == 1);
        assertTrue("metodi pienin ei saa muuttaa parametrina olevan taulukon sisältöä", t[2] == 3);
        assertTrue("metodi pienin ei saa muuttaa parametrina olevan taulukon sisältöä", t[3] == 0);
    }

    @Points("07-04.1")
    @Test
    public void pieninTesti1() {
        int[] t = {2, 1, 3};
        int odotettu = 1;

        assertEquals("metodi pienin ei toiminut syötteellä " + toS(t) + "", odotettu, pienin(t));
    }

    @Points("07-04.1")
    @Test
    public void pieninTesti2() {
        int[] t = {6, 3, 0, -1, 4};
        int odotettu = -1;

        assertEquals("metodi pienimmanIndeksi ei toiminut syötteellä " + toS(t) + "", odotettu, pienin(t));
    }
    Random arpa = new Random();

    @Points("07-04.1")
    @Test
    public void pieninTesti3() {
        int[] t = {3, 5, 6, 2, 7, 1, 3, 7, 5};
        int odotettu = arpa.nextInt(t.length);
        t[odotettu] = -10 - arpa.nextInt(10);

        assertEquals("metodi pienin ei toiminut syötteellä " + toS(t) + "", t[odotettu], pienin(t));
    }

    /*
     *
     */
    @Points("07-04.2")
    @Test
    public void onMetodiPienimmanIndeksi() throws Throwable {
        String virhe = "tee luokkaan Paaohjelma metodi public static int pienimmanIndeksi(int[] taulukko)";
        String metodi = "pienimmanIndeksi";

        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        assertTrue(virhe,
                klass.staticMethod(metodi).returning(int.class).taking(int[].class).isPublic());

        String v = "Ongelman aiheutti \n"
                + "int[] t = {2, 1, 3, 0};\n"
                + "int p = " + metodi + "(t);\n";
        int[] t = {2, 1, 3, 0};
        klass.staticMethod(metodi).returning(int.class).taking(int[].class).withNiceError(v).invoke(t);
    }

    @Points("07-04.2")
    @Test
    public void eiKiellettyjaKomentoja1() {
        noForbiddens();
    }

    @Points("07-04.2")
    @Test
    public void pienimmanIndeksiEiMuutaTaulukonSisaltoa() {
        int[] t = {2, 1, 3, 0};
        pienimmanIndeksi(t);

        assertTrue("metodi pienimmanIndeksi ei saa muuttaa parametrina olevan taulukon sisältöä", t[0] == 2);
        assertTrue("metodi pienimmanIndeksi ei saa muuttaa parametrina olevan taulukon sisältöä", t[1] == 1);
        assertTrue("metodi pienimmanIndeksi ei saa muuttaa parametrina olevan taulukon sisältöä", t[2] == 3);
        assertTrue("metodi pienimmanIndeksi ei saa muuttaa parametrina olevan taulukon sisältöä", t[3] == 0);
    }

    @Points("07-04.2")
    @Test
    public void pienimmanIndeksiTesti1() {
        int[] t = {2, 1, 3};
        int odotettu = 1;

        assertEquals("metodi pienimmanIndeksi ei toiminut syötteellä " + toS(t) + "", odotettu, pienimmanIndeksi(t));
    }

    @Points("07-04.2")
    @Test
    public void pienimmanIndeksiTesti2() {
        int[] t = {6, 3, 0, -1, 4};
        int odotettu = 3;

        assertEquals("metodi pienimmanIndeksi ei toiminut syötteellä " + toS(t) + "", odotettu, pienimmanIndeksi(t));
    }

    @Points("07-04.2")
    @Test
    public void pienimmanIndeksiTesti3() {
        int[] t = {3, -5, 6, 1, 7, 1, 3, 7, 5};
        int odotettu = arpa.nextInt(t.length);
        t[odotettu] = -10 - arpa.nextInt(10);

        assertEquals("metodi pienimmanIndeksi ei toiminut syötteellä " + toS(t) + "", odotettu, pienimmanIndeksi(t));
    }

    /*
     *
     */
    @Points("07-04.3")
    @Test
    public void eiKiellettyjaKomentoja2() {
        noForbiddens();
    }

    @Points("07-04.3")
    @Test
    public void onMetodiPienimmanIndeksiAlkaen() throws Throwable {
        String virhe = "tee luokkaan Paaohjelma metodi public static int pienimmanIndeksiAlkaen(int[] taulukko, int aloitusIndeksi)";
        String metodi = "pienimmanIndeksiAlkaen";

        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        assertTrue(virhe,
                klass.staticMethod(metodi).returning(int.class).taking(int[].class, int.class).isPublic());

        String v = "Ongelman aiheutti \n"
                + "int[] t = {2, 1, 3, 0};\n"
                + "int p = " + metodi + "(t, 1);\n";
        int[] t = {2, 1, 3, 0};
        klass.staticMethod(metodi).returning(int.class).taking(int[].class, int.class).withNiceError(v).invoke(t, 1);
    }

    @Points("07-04.3")
    @Test
    public void pienimmanIndeksiAlkaenEiMuutaTaulukonSisaltoa() {
        int[] t = {2, 1, 3, 0};
        pienimmanIndeksiAlkaen(t, 0);

        assertTrue("metodi pienimmanIndeksi ei saa muuttaa parametrina olevan taulukon sisältöä", t[0] == 2);
        assertTrue("metodi pienimmanIndeksi ei saa muuttaa parametrina olevan taulukon sisältöä", t[1] == 1);
        assertTrue("metodi pienimmanIndeksi ei saa muuttaa parametrina olevan taulukon sisältöä", t[2] == 3);
        assertTrue("metodi pienimmanIndeksi ei saa muuttaa parametrina olevan taulukon sisältöä", t[3] == 0);

        pienimmanIndeksiAlkaen(t, 2);

        assertTrue("metodi pienimmanIndeksi ei saa muuttaa parametrina olevan taulukon sisältöä", t[0] == 2);
        assertTrue("metodi pienimmanIndeksi ei saa muuttaa parametrina olevan taulukon sisältöä", t[1] == 1);
        assertTrue("metodi pienimmanIndeksi ei saa muuttaa parametrina olevan taulukon sisältöä", t[2] == 3);
        assertTrue("metodi pienimmanIndeksi ei saa muuttaa parametrina olevan taulukon sisältöä", t[3] == 0);
    }

    @Points("07-04.3")
    @Test
    public void pienimmanIndeksiAlkaenTesti1() {
        int[] t = {-1, 3, 1, 2};
        int a = 0;
        int odotettu = 0;

        assertEquals("metodi pienimmanIndeksiAlkaen ei toiminut syötteellä " + toS(t) + " aloitusindeksi " + a, odotettu, pienimmanIndeksiAlkaen(t, a));

        a = 1;
        odotettu = 2;
        assertEquals("metodi pienimmanIndeksiAlkaen ei toiminut syötteellä " + toS(t) + " aloitusindeksi " + a, odotettu, pienimmanIndeksiAlkaen(t, a));

        a = 2;
        odotettu = 2;
        assertEquals("metodi pienimmanIndeksiAlkaen ei toiminut syötteellä " + toS(t) + " aloitusindeksi " + a, odotettu, pienimmanIndeksiAlkaen(t, a));

        a = 3;
        odotettu = 3;
        assertEquals("metodi pienimmanIndeksiAlkaen ei toiminut syötteellä " + toS(t) + " aloitusindeksi " + a, odotettu, pienimmanIndeksiAlkaen(t, a));
    }

    @Points("07-04.3")
    @Test
    public void pienimmanIndeksiAlkaenTesti3() {
        //          0  1  2  3  4  5  6  7  8
        int[] t = {-1, 3, 1, 7, 4, 5, 2, 4, 3};
        int a = 0;
        int odotettu = 0;

        assertEquals("metodi pienimmanIndeksiAlkaen ei toiminut syötteellä " + toS(t) + " aloitusindeksi " + a, odotettu, pienimmanIndeksiAlkaen(t, a));

        a = 1;
        odotettu = 2;
        assertEquals("metodi pienimmanIndeksiAlkaen ei toiminut syötteellä " + toS(t) + " aloitusindeksi " + a, odotettu, pienimmanIndeksiAlkaen(t, a));

        a = 2;
        odotettu = 2;
        assertEquals("metodi pienimmanIndeksiAlkaen ei toiminut syötteellä " + toS(t) + " aloitusindeksi " + a, odotettu, pienimmanIndeksiAlkaen(t, a));

        a = 3;
        odotettu = 6;
        assertEquals("metodi pienimmanIndeksiAlkaen ei toiminut syötteellä " + toS(t) + " aloitusindeksi " + a, odotettu, pienimmanIndeksiAlkaen(t, a));

        a = 4;
        odotettu = 6;
        assertEquals("metodi pienimmanIndeksiAlkaen ei toiminut syötteellä " + toS(t) + " aloitusindeksi " + a, odotettu, pienimmanIndeksiAlkaen(t, a));

        a = 5;
        odotettu = 6;
        assertEquals("metodi pienimmanIndeksiAlkaen ei toiminut syötteellä " + toS(t) + " aloitusindeksi " + a, odotettu, pienimmanIndeksiAlkaen(t, a));

        a = 6;
        odotettu = 6;
        assertEquals("metodi pienimmanIndeksiAlkaen ei toiminut syötteellä " + toS(t) + " aloitusindeksi " + a, odotettu, pienimmanIndeksiAlkaen(t, a));

        a = 7;
        odotettu = 8;
        assertEquals("metodi pienimmanIndeksiAlkaen ei toiminut syötteellä " + toS(t) + " aloitusindeksi " + a, odotettu, pienimmanIndeksiAlkaen(t, a));

    }

    /*
     *
     */
    @Points("07-04.4")
    @Test
    public void onMetodiVaihda() throws Throwable {
        String virhe = "tee luokkaan Paaohjelma metodi public static void vaihda(int[] taulukko, int indeksi1, int indeksi2)";
        String metodi = "vaihda";
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        assertTrue(virhe,
                klass.staticMethod(metodi).returningVoid().taking(int[].class, int.class, int.class).isPublic());

        String v = "Ongelman aiheutti \n"
                + "int[] t = {2, 1, 3, 0};\n"
                + metodi + "(t, 1, 3);\n";
        int[] t = {2, 1, 3, 0};
        klass.staticMethod(metodi).returningVoid().taking(int[].class, int.class, int.class).withNiceError(v).invoke(t, 1, 3);
    }

    @Points("07-04.4")
    @Test
    public void eiKiellettyjaKomentoja3() {
        noForbiddens();
    }

    @Points("07-04.4")
    @Test
    public void vaihdaTesti1() {
        //             0  1  2  3
        int[] t = {4, 7, 8, 6};
        int[] alkup = {4, 7, 8, 6};
        int[] od = {6, 7, 8, 4};
        int i1 = 0;
        int i2 = 3;

        vaihda(t, i1, i2);
        assertTrue("metodi vaihda ei toimi oikein kun parametrina on " + toS(alkup) + " indeksi1=" + i1 + " indeksi2=" + i2 + " "
                + "\nlopputulos oli " + toS(t) + " oikea tulos on " + toS(od), Arrays.equals(od, t));
    }

    @Points("07-04.4")
    @Test
    public void vaihdaTesti2() {
        //             0  1  2  3
        int[] t = {4, 7, 8, 6};
        int[] alkup = {4, 7, 8, 6};
        int[] od = {4, 8, 7, 6};
        int i1 = 1;
        int i2 = 2;

        vaihda(t, i1, i2);
        assertTrue("metodi vaihda ei toimi oikein kun parametrina on " + toS(alkup) + " indeksi1=" + i1 + " indeksi2=" + i2 + " "
                + "\nlopputulos oli " + toS(t) + " oikea tulos on " + toS(od), Arrays.equals(od, t));
    }

    @Points("07-04.4")
    @Test
    public void vaihdaTesti3() {
        //             0  1  2  3  4  5  6
        int[] t = {4, 7, 8, 6, 9, 2, 3};
        int[] alkup = {4, 7, 8, 6, 9, 2, 3};
        int[] od = {4, 2, 8, 6, 9, 7, 3};
        int i1 = 1;
        int i2 = 5;

        vaihda(t, i1, i2);
        assertTrue("metodi vaihda ei toimi oikein kun parametrina on " + toS(alkup) + " indeksi1=" + i1 + " indeksi2=" + i2 + " "
                + "\nlopputulos oli " + toS(t) + " oikea tulos on " + toS(od), Arrays.equals(od, t));
    }

    @Points("07-04.4")
    @Test
    public void vaihdaTesti4() {
        //             0  1  2  3  4  5  6
        int[] t = {4, 7, 8, 6, 9, 2, 3};
        int[] alkup = {4, 7, 8, 6, 9, 2, 3};
        int[] od = {4, 7, 9, 6, 8, 2, 3};
        int i1 = 2;
        int i2 = 4;

        vaihda(t, i1, i2);
        assertTrue("metodi vaihda ei toimi oikein kun parametrina on " + toS(alkup) + " indeksi1=" + i1 + " indeksi2=" + i2 + " "
                + "\nlopputulos oli " + toS(t) + " oikea tulos on " + toS(od), Arrays.equals(od, t));
    }

    @Points("07-04.4")
    @Test
    public void vaihdaTesti5() {
        //             0  1  2  3  4  5  6
        int[] t = {4, 7, 8, 6, 9, 2, 3};
        int[] alkup = {4, 7, 8, 6, 9, 2, 3};
        int[] od = {3, 7, 8, 6, 9, 2, 4};
        int i1 = 0;
        int i2 = 6;

        vaihda(t, i1, i2);
        assertTrue("metodi vaihda ei toimi oikein kun parametrina on " + toS(alkup) + " indeksi1=" + i1 + " indeksi2=" + i2 + " "
                + "\nlopputulos oli " + toS(t) + " oikea tulos on " + toS(od), Arrays.equals(od, t));
    }

    /*
     *
     */
    @Points("07-04.5")
    @Test
    public void onMetodiJarjesta() throws Throwable {
        String virhe = "tee luokkaan Paaohjelma metodi public static void jarjesta(int[] taulukko)";
        String metodi = "jarjesta";
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        assertTrue(virhe,
                klass.staticMethod(metodi).returningVoid().taking(int[].class).isPublic());

        String v = "Ongelman aiheutti \n"
                + "int[] t = {2, 1, 3, 0};\n"
                + metodi + "(t);\n";
        int[] t = {2, 1, 3, 0};
        klass.staticMethod(metodi).returningVoid().taking(int[].class).withNiceError(v).invoke(t);
    }

    @Points("07-04.5")
    @Test
    public void eiKiellettyjaKomentoja4() {
        noForbiddens();
    }

    @Points("07-04.5")
    @Test
    public void jarjestaTesti1() {
        int[] t = {4, 7, 1};
        int[] alkup = {4, 7, 1};
        int[] od = {4, 7, 1};

        Arrays.sort(od);

        jarjesta(t);
        assertTrue("metodi jarjesta ei toimi oikein kun parametrina on " + toS(alkup) + " "
                + "lopputulos oli " + toS(t) + " oikea tulos on " + toS(od), Arrays.equals(od, t));

    }

    @Points("07-04.5")
    @Test
    public void jarjestaTesti2() {
        int[] t = {4, 7, 8, 6, 9, 2, 3};
        int[] alkup = {4, 7, 8, 6, 9, 2, 3};
        int[] od = {4, 7, 8, 6, 9, 2, 3};

        Arrays.sort(od);

        jarjesta(t);
        assertTrue("metodi jarjesta ei toimi oikein kun parametrina on " + toS(alkup) + " "
                + "lopputulos oli " + toS(t) + " oikea tulos on " + toS(od), Arrays.equals(od, t));
    }

    @Points("07-04.5")
    @Test
    public void jarjestaTesti3() {
        int n = arpa.nextInt(5) + 5;

        int[] t = new int[n];
        int[] alkup = new int[n];
        int[] od = new int[n];

        for (int i = 0; i < n; i++) {
            int arvottu = 20 - arpa.nextInt(30);
            t[i] = arvottu;
            od[i] = arvottu;
            alkup[i] = arvottu;
        }

        Arrays.sort(od);

        jarjesta(t);
        assertTrue("metodi jarjesta ei toimi oikein kun parametrina on " + toS(alkup) + " "
                + "lopputulos oli " + toS(t) + " oikea tulos on " + toS(od), Arrays.equals(od, t));
    }

    @Points("07-04.5")
    @Test
    public void jarjestaTesti4() {
        for (int k = 0; k < 10; k++) {
            int n = arpa.nextInt(20) + 20;

            int[] t = new int[n];
            int[] alkup = new int[n];
            int[] od = new int[n];

            for (int i = 0; i < n; i++) {
                int arvottu = 20 - arpa.nextInt(30);
                t[i] = arvottu;
                od[i] = arvottu;
                alkup[i] = arvottu;
            }

            Arrays.sort(od);

            jarjesta(t);
            assertTrue("metodi jarjesta ei toimi oikein kun parametrina on " + toS(alkup) + " "
                    + "lopputulos oli " + toS(t) + " oikea tulos on " + toS(od), Arrays.equals(od, t));
        }

    }

    /*
     *
     */
    private void jarjesta(int[] t) {
        String metodi = "jarjesta";

        try {
            Method m = ReflectionUtils.requireMethod(Paaohjelma.class, metodi, int[].class);
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, t);

        } catch (ArrayIndexOutOfBoundsException e) {
            fail("metodissa " + metodi + " viitataan taulukon ulkopuolelle kun syötteenä on " + toS(t));
        } catch (Throwable e) {
            fail("metodissa " + metodi + " tapahtui jotain odottamatonta, lisätietoja:\n" + e);
        }
    }

    private void vaihda(int[] t, int i1, int i2) {
        String metodi = "vaihda";

        try {
            Method m = ReflectionUtils.requireMethod(Paaohjelma.class, metodi, int[].class, int.class, int.class);

            ReflectionUtils.invokeMethod(int.class, m, null, t, i1, i2);
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("metodissa " + metodi + " viitataan taulukon ulkopuolelle kun syötteenä on " + toS(t));
        } catch (Throwable e) {
            fail("metodissa " + metodi + " tapahtui jotain odottamatonta, lisätietoja:\n" + e);
        }
    }

    private int pienimmanIndeksiAlkaen(int[] t, int a) {
        String metodi = "pienimmanIndeksiAlkaen";

        try {
            String[] args = new String[0];
            Method m = ReflectionUtils.requireMethod(Paaohjelma.class, metodi, int[].class, int.class);

            return ReflectionUtils.invokeMethod(int.class, m, null, t, a);
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("metodissa " + metodi + " viitataan taulukon ulkopuolelle kun syötteenä on " + toS(t));
        } catch (Throwable e) {
            fail("metodissa " + metodi + " tapahtui jotain odottamatonta, lisätietoja:\n" + e);
        }
        return -1;
    }

    private int pienin(int[] t) {
        String metodi = "pienin";

        try {
            String[] args = new String[0];
            Method m = ReflectionUtils.requireMethod(Paaohjelma.class, metodi, int[].class);

            return ReflectionUtils.invokeMethod(int.class, m, null, t);
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("metodissa " + metodi + " viitataan taulukon ulkopuolelle kun syötteenä on " + toS(t));
        } catch (Throwable e) {
            fail("metodissa " + metodi + " tapahtui jotain odottamatonta, lisätietoja:\n" + e);
        }
        return -1;
    }

    private int pienimmanIndeksi(int[] t) {
        String metodi = "pienimmanIndeksi";

        try {
            String[] args = new String[0];
            Method m = ReflectionUtils.requireMethod(Paaohjelma.class, metodi, int[].class);

            return ReflectionUtils.invokeMethod(int.class, m, null, t);
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("metodissa " + metodi + " viitataan taulukon ulkopuolelle kun syötteenä on " + toS(t));
        } catch (Throwable e) {
            fail("metodissa " + metodi + " tapahtui jotain odottamatonta, lisätietoja:\n" + e);
        }
        return -1;
    }

    private String toS(int[] t) {
        return Arrays.toString(t).replace("[", "").replace("]", "");
    }

    private void noForbiddens() {
        try {
            Scanner lukija = new Scanner(new File("src/main/java/Paaohjelma.java"));
            while (lukija.hasNext()) {
                String rivi = lukija.nextLine();
                if (rivi.contains("Arrays.sort(")) {
                    fail("Koska nyt harjoitellaan järjestämisalgoritmin kirjoittamista, "
                            + "et saa käyttää ohjelmassasi komentoa Arrays.sort()");
                }

                if (rivi.contains("ArrayList<")) {
                    fail("Koska nyt harjoitellaan järjestämisalgoritmin kirjoittamista, "
                            + "et saa käyttää ohjelmassasi ArrayList:ejä!");
                }
            }
        } catch (Exception e) {
            fail(e.toString());
        }
    }
}
