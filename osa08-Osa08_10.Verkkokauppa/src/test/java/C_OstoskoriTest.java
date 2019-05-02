
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;
import org.junit.*;
import static org.junit.Assert.*;

public class C_OstoskoriTest {

    String klassName = "Ostoskori";
    Reflex.ClassRef<Object> klass;
    Class c;

    @Before
    public void setup() {
        klass = Reflex.reflect(klassName);
        try {
            c = ReflectionUtils.findClass(klassName);
        } catch (Throwable e) {
        }
    }

    @Test
    @Points("08-10.5")
    public void onLuokkaOstoskori() {
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Points("08-10.5")
    @Test
    public void onMapTaiList() {
        Field[] kentat = ReflectionUtils.findClass(klassName).getDeclaredFields();

        String k = "Talleta " + klassName + ":n ostokset joko oliomuuttujaan Map<String, Ostos> ostokset;\n"
                + "tai oliomuuttujaan List<Ostos> ostokset; ";

        int map = 0;
        for (Field field : kentat) {
            assertFalse(k
                    + "eli muuta " + kentta(field.toString()) + " oikean tyyppiseksi", field.toString().contains("HashMap"));
            assertFalse(k
                    + "eli muuta " + kentta(field.toString()) + " oikean tyyppiseksi", field.toString().contains("ArrayList"));

            assertFalse(k + " muita oliomuuttujia et tarvitse, poista " + kentta(field.toString()), !field.toString().contains("Map") && !field.toString().contains("List"));

            if (field.toString().contains("Map") || field.toString().contains("List")) {
                map++;
            }
        }
        assertTrue(k, map > 0 && map < 3);

    }

    @Test
    @Points("08-10.5")
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus(klassName, 1, "ostos-oliot tallettavan oliomuuttujan");
    }

    @Test
    @Points("08-10.5")
    public void konstruktori() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "()", ctor.isPublic());
        String v = "virheen aiheutti koodi new Varasto();";
        ctor.withNiceError(v).invoke();
    }

    public Object luo() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        return ctor.invoke();
    }

    @Test
    @Points("08-10.5")
    public void onMetodiHinta() throws Throwable {
        String metodi = "hinta";

        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public int " + metodi + "()",
                klass.method(olio, metodi)
                .returning(int.class).takingNoParams().isPublic());

        String v = "Kori = new Ostoskori(); kori.hinta()";

        klass.method(olio, metodi)
                .returning(int.class).takingNoParams().withNiceError("virheen aiheutti koodi \n" + v).invoke();
    }

    @Test
    @Points("08-10.5")
    public void tyhjanKorinHintaNolla() throws Throwable {
        String k = "Kori = new Ostoskori(); kori.hinta()";
        Object kori = newOstoskori();
        int hinta = hinta(kori);
        assertEquals(k, 0, hinta);
    }

    @Test
    @Points("08-10.5")
    public void onMetodiLisaa() throws Throwable {
        String metodi = "lisaa";

        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "(String tuote, int hinta)",
                klass.method(olio, metodi)
                .returningVoid().taking(String.class, int.class).isPublic());

        String v = "Kori = new Ostoskori(); kori.lisaa(\"maito\",3)";

        klass.method(olio, metodi)
                .returningVoid().taking(String.class, int.class).withNiceError("virheen aiheutti koodi \n" + v).invoke("maito", 3);
    }

    @Test
    @Points("08-10.5")
    public void tuotteenLisaysKasvattaaKorinHintaa() throws Throwable {
        String k = "Kori = new Ostoskori(); kori.lisaa(\"maito\",3); kori.hinta()";

        Object kori = newOstoskori();
        lisaa(kori, "maito", 3);
        int hinta = hinta(kori);
        assertEquals(k, 3, hinta);
    }

    @Test
    @Points("08-10.5")
    public void kahdenEriTuotteenLisaysKasvattaaKorinHintaa() throws Throwable {
        String k = "Kori = new Ostoskori(); kori.lisaa(\"maito\",3); kori.lisaa(\"voi\",5); kori.hinta()";

        Object kori = newOstoskori();
        lisaa(kori, "maito", 3);
        lisaa(kori, "voi", 5);
        int hinta = hinta(kori);
        assertEquals(k, 8, hinta);
    }

    @Test
    @Points("08-10.5")
    public void kolmenEriTuotteenLisaysKasvattaaKorinHintaa() throws Throwable {
        String k = "Kori = new Ostoskori(); kori.lisaa(\"maito\",3); kori.lisaa(\"voi\",5);kori.lisaa(\"leipa\",4); kori.hinta()";

        Object kori = newOstoskori();
        lisaa(kori, "maito", 3);
        lisaa(kori, "voi", 5);
        lisaa(kori, "leipa", 4);
        int hinta = hinta(kori);
        assertEquals(k, 12, hinta);
    }

    /*
     *
     */
    @Test
    @Points("08-10.6")
    public void onMetodiTulosta() throws Throwable {
        String metodi = "tulosta";

        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "()",
                klass.method(olio, metodi)
                .returningVoid().takingNoParams().isPublic());

        String v = "Kori = new Ostoskori(); kori.tulosta()";

        klass.method(olio, metodi)
                .returningVoid().takingNoParams().withNiceError("virheen aiheutti koodi \n" + v).invoke();

    }

    @Test
    @Points("08-10.6")
    public void tulostusToimii() throws Throwable {
        MockInOut io = new MockInOut("");

        String k = "Kori = new Ostoskori(); \n"
                + "kori.lisaa(\"maito\",3); \n"
                + "kori.lisaa(\"voi\",5);\n"
                + "kori.lisaa(\"leipa\",4); \n"
                + "kori.tulosta()\n";

        Object kori = newOstoskori();
        lisaa(kori, "maito", 3);
        lisaa(kori, "voi", 5);
        lisaa(kori, "leipa", 4);
        tulosta(kori);

        String[] t = io.getOutput().split("\n");
        assertEquals("varmista että Ostoskorin metodi tulosta toimii esimerkin mukaan, \n"
                + "" + k + " tulostettavien rivien määrä", 3, t.length);
        String etsittava = "maito: 1";
        assertTrue("varmista että Ostoskorin metodi tulosta toimii esimerkin mukaan,  \n"
                + k + " rivin " + etsittava + " pitäisi tulostua. Tuloste oli:\n"+io.getOutput(), sisaltaa(t, etsittava));
        etsittava = "voi: 1";
        assertTrue("varmista että Ostoskorin metodi tulosta toimii esimerkin mukaan,  \n"
                + k + " rivin " + etsittava + " pitäisi tulostua. Tuloste oli:\n"+io.getOutput(), sisaltaa(t, etsittava));
        etsittava = "leipa: 1";
        assertTrue("varmista että Ostoskorin metodi tulosta toimii esimerkin mukaan,  \n"
                + k + " rivin " + etsittava + " pitäisi tulostua. Tuloste oli:\n"+io.getOutput(), sisaltaa(t, etsittava));
    }

    @Test
    @Points("08-10.7")
    public void kahdenSamanTuotteenLisaysKasvattaaKorinHintaa() throws Throwable {
        String k = "Kori = new Ostoskori(); \n"
                + "kori.lisaa(\"maito\",3); \n"
                + "kori.lisaa(\"maito\",3); \n"
                + "kori.hinta()";

        Object kori = newOstoskori();
        lisaa(kori, "maito", 3);
        lisaa(kori, "maito", 3);
        int hinta = hinta(kori);
        assertEquals(k, 6, hinta);
    }

    @Test
    @Points("08-10.7")
    public void kahdenSamanTuotteenLisaysEiTeeKahtaOstosta() throws Throwable {
        MockInOut io = new MockInOut("");

        String k = "Kori = new Ostoskori(); \n"
                + "kori.lisaa(\"maito\",3); \n"
                + "kori.lisaa(\"maito\",3); \n"
                + "kori.tulosta()";

        Object kori = newOstoskori();
        lisaa(kori, "maito", 3);
        lisaa(kori, "maito", 3);
        tulosta(kori);

        String[] t = io.getOutput().split("\n");
        assertEquals("varmista että Ostoskorin metodi tulosta toimii esimerkin mukaan, "
                + "kun koriin lisätään 2 samaa tuotetta \n"
                + k + " tulostettavien rivien määrä", 1, t.length);
        assertTrue("varmista että Ostoskorin metodi tulosta toimii esimerkin mukaan, "
                + "kun koriin lisätään 2 samaa tuotetta \n"
                + k + "\n"
                + "ainoan tulostuvan rivin pitäisi olla maito: 2, tulostui \n" + t[0] + "\n", t[0].contains("maito: 2"));
    }

    @Test
    @Points("08-10.7")
    public void montaSamaaJaEri() throws Throwable {
        MockInOut io = new MockInOut("");
        String k = "Kori = new Ostoskori(); \n"
                + "kori.lisaa(\"maito\",3); \n"
                + "kori.lisaa(\"makkara\",7); \n"
                + "kori.lisaa(\"maito\",3); \n"
                + "kori.lisaa(\"maito\",3); \n"
                + "kori.lisaa(\"makkara\",7); \n"
                + "kori.lisaa(\"kerma\", 2);\n"
                + "kori.hinta()";

        Object kori = newOstoskori();
        lisaa(kori, "maito", 3);
        lisaa(kori, "makkara", 7);
        lisaa(kori, "maito", 3);
        lisaa(kori, "maito", 3);
        lisaa(kori, "makkara", 7);
        lisaa(kori, "kerma", 2);
        int hinta = hinta(kori);
        assertEquals(k, 25, hinta);

        tulosta(kori);

        String[] t = io.getOutput().split("\n");

        assertEquals("varmista että Ostoskorin metodi tulosta toimii esimerkin mukaan, "
                + "kun koriin lisätään monta samaa tuotetta \n"
                + k + " tulostettavien rivien määrä", 3, t.length);
        String etsittava = "maito: 3";
        assertTrue("varmista että Ostoskorin metodi tulosta toimii esimerkin mukaan,  \n"
                + k + " rivin " + etsittava + " pitäisi tulostua, ", sisaltaa(t, etsittava));
        etsittava = "makkara: 2";
        assertTrue("varmista että Ostoskorin metodi tulosta toimii esimerkin mukaan,  \n"
                + k + " rivin " + etsittava + " pitäisi tulostua, ", sisaltaa(t, etsittava));
        etsittava = "kerma: 1";
        assertTrue("varmista että Ostoskorin metodi tulosta toimii esimerkin mukaan,  \n"
                + k + " rivin " + etsittava + " pitäisi tulostua, ", sisaltaa(t, etsittava));
    }

    /*
     *
     */
    @Test
    @Points("08-10.8")
    public void onLuokkaKauppa() {
        try {
            ReflectionUtils.findClass("Kauppa");
        } catch (Throwable e) {
            fail("tee ohjelmaasi luokka Kauppa ja kopioi sinne tehtävänannossa oleva koodipohja");
        }
    }

    @Test
    @Points("08-10.8")
    public void asiointiToimii() throws Throwable {
        int saldo = 0;
        String rivit[] = null;
        Object v = null;

        try {
            MockInOut io = new MockInOut("");
            Scanner sk = new Scanner("kahvi\nleipa\nvesi\n\n");
            v = newVarasto();

            lisaaVarastoon(v, "kahvi", 5, 10);
            lisaaVarastoon(v, "maito", 3, 20);
            lisaaVarastoon(v, "kerma", 2, 55);
            lisaaVarastoon(v, "leipa", 7, 8);
            Object kauppa = newKauppa(v, sk);
            asioi(kauppa, "pekka");
            saldo = saldo(v, "kahvi");
            rivit = io.getOutput().split("\n");


        } catch (Throwable t) {
            fail("luotiin kauppa samoin kuin esimerkissä ja asiakkaan syöte on kahvi<enter>leipa<enter>vesi<enter><enter>\n tapahtui poikkeus " + t + "\n"
                    + "kopioitko tehtävänannossa olevan koodin luokkaan Kauppa?");
        }
        assertEquals("kauppa on luotu samoin kuin esimerkissä ja asiakkaan syöte on kahvi<enter>leipa<enter>vesi<enter><enter>\n tulisi kahvin varastosaldon pienentyä yhdellä", 9, saldo);
        saldo = saldo(v, "leipa");
        assertEquals("kauppa on luotu samoin kuin esimerkissä ja asiakkaan syöte on kahvi<enter>leipa<enter>vesi<enter><enter>\n tulisi leivän varastosaldon pienentyä yhdellä", 7, saldo);
        assertTrue("kauppa on luotu samoin kuin esimerkissäja asiakkaan syöte on kahvi<enter>leipa<enter>vesi<enter><enter>\n tulisi ostoskorin hinnan olla 12, nyt hinta oli oli " + rivit[rivit.length - 1], rivit[rivit.length - 1].contains("12"));
    }

    private void asioi(Object olio, String nimi) throws Throwable {
        try {
            Class clzz = ReflectionUtils.findClass("Kauppa");
            Method metodi = ReflectionUtils.requireMethod(clzz, "asioi", String.class);
            ReflectionUtils.invokeMethod(void.class, metodi, olio, nimi);
        } catch (Throwable t) {
            throw t;
        }
    }

    private int saldo(Object olio, String tuote) throws Throwable {
        try {
            Class clzz = ReflectionUtils.findClass("Varasto");
            Method metodi = ReflectionUtils.requireMethod(clzz, "saldo", String.class);
            return ReflectionUtils.invokeMethod(int.class, metodi, olio, tuote);
        } catch (Throwable t) {
            throw t;
        }
    }

    private void lisaaVarastoon(Object olio, String tuote, int hinta, int saldo) throws Throwable {
        try {
            Class clzz = ReflectionUtils.findClass("Varasto");
            Method metodi = ReflectionUtils.requireMethod(clzz, "lisaaTuote", String.class, int.class, int.class);
            List<String> l = null;

            ReflectionUtils.invokeMethod(void.class, metodi, olio, tuote, hinta, saldo);
        } catch (Throwable t) {
            throw t;
        }
    }

    private Object newVarasto() throws Throwable {
        String luokanNimi = "Varasto";
        try {
            Class clzz = ReflectionUtils.findClass(luokanNimi);
            return ReflectionUtils.invokeConstructor(clzz.getConstructor());
        } catch (Throwable t) {
            fail("tee luokalle " + luokanNimi + " parametriton julkinen konstruktori");
        }
        return null;
    }

    private Object newKauppa(Object varasto, Scanner lukija) throws Throwable {
        String luokanNimi = "Kauppa";
        try {
            Class clzz = ReflectionUtils.findClass(luokanNimi);
            return clzz.getConstructors()[0].newInstance(varasto, lukija);
            //return ReflectionUtils.invokeConstructor(clzz.getConstructor(), varasto, lukija);
        } catch (Throwable t) {
            fail("tee luokalle " + luokanNimi + " parametriton julkinen konstruktori");
        }
        return null;
    }

    private void lisaa(Object olio, String tuote, int hinta) throws Throwable {
        try {
            Method metodi = ReflectionUtils.requireMethod(c, "lisaa", String.class, int.class);
            ReflectionUtils.invokeMethod(void.class, metodi, olio, tuote, hinta);
        } catch (Throwable t) {
            throw t;
        }
    }

    private int hinta(Object olio) throws Throwable {
        try {
            Method metodi = ReflectionUtils.requireMethod(c, "hinta");

            return ReflectionUtils.invokeMethod(int.class, metodi, olio);
        } catch (Throwable t) {
            throw t;
        }
    }

    private void tulosta(Object olio) throws Throwable {
        try {
            Method metodi = ReflectionUtils.requireMethod(c, "tulosta");

            ReflectionUtils.invokeMethod(void.class, metodi, olio);
        } catch (Throwable t) {
            throw t;
        }
    }

    private Object newOstoskori() throws Throwable {
        try {
            c = ReflectionUtils.findClass(klassName);
            return ReflectionUtils.invokeConstructor(c.getConstructor());
        } catch (Throwable t) {
            fail("tee luokalle " + klassName + " parametriton julkinen konstruktori");
        }
        return null;
    }

    private String kentta(String toString) {
        return toString.replace(klassName + ".", "");
    }

    private boolean sisaltaa(String[] t, String mj) {
        for (String rivi : t) {
            if (rivi.contains(mj)) {
                return true;
            }
        }
        return false;
    }

    private void saniteettitarkastus(String klassName, int n, String m) throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("et tarvitse \"stattisia muuttujia\", poista luokalta " + klassName + " muuttuja " + kentta(field.toString(), klassName), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("luokan kaikkien oliomuuttujien näkyvyyden tulee olla private, muuta luokalta " + klassName + " löytyi: " + kentta(field.toString(), klassName), field.toString().contains("private"));
        }

        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("et tarvitse " + klassName + "-luokalle kuin " + m + ", poista ylimääräiset", var <= n);
        }
    }

    private String kentta(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }
}
