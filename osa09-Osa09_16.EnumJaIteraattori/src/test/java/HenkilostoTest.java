
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.*;
import static org.junit.Assert.*;

public class HenkilostoTest<_Koulutus, _Henkilo, _Tyontekijat> {

    Reflex.ClassRef<_Koulutus> _KoulutusRef;
    Reflex.ClassRef<_Henkilo> _HenkiloRef;
    Reflex.ClassRef<_Tyontekijat> _TyontekRef;

    @Before
    public void atStart() {
        try {
            _KoulutusRef = Reflex.reflect("Koulutus");
            _HenkiloRef = Reflex.reflect("Henkilo");
            _TyontekRef = Reflex.reflect("Tyontekijat");
        } catch (Throwable e) {
        }
    }

    @Test
    @Points("09-16.1")
    public void onEnumKoulutus() {
        String luokanNimi = "Koulutus";
        try {
            _KoulutusRef = Reflex.reflect(luokanNimi);
        } catch (Throwable t) {
            fail("tee enum Koulutus");
        }
        assertTrue("tee enum Koulutus", _KoulutusRef.isPublic());
        Class c = _KoulutusRef.cls();
        assertTrue("tee enum Koulutus, nyt taisit tehdä normaalin luokan", c.isEnum());
    }

    @Test
    @Points("09-16.1")
    public void enumillaOikeatTunnukset() {
        String luokanNimi = "Koulutus";
        Class c = ReflectionUtils.findClass(luokanNimi);
        Object[] vakiot = c.getEnumConstants();
        assertEquals("enumin Koulutus tulisi määritellä oikea määrä tunnuksia", 4, vakiot.length);

        String[] t = {"FilYO", "LuK", "FM", "FT"};

        for (String tunnus : t) {
            assertTrue("Enumin Koulutus tulisi määritellä tunnus " + tunnus, sis(tunnus, vakiot));
        }
    }

    @Test
    @Points("09-16.2")
    public void onLuokkaHenkilo() {
        String luokanNimi = "Henkilo";
        _HenkiloRef = Reflex.reflect(luokanNimi);
        assertTrue("tee luokka Henkilo", _HenkiloRef.isPublic());
    }

    public _Henkilo luoHenkilo(String nimi, _Koulutus k) throws Throwable {
        return _HenkiloRef.constructor().taking(String.class, _KoulutusRef.cls()).invoke(nimi, k);
    }

    @Test
    @Points("09-16.2")
    public void HenkilollaOikeaKonstruktori() throws Throwable {
        assertTrue("Tee luokalle Henkilo konstruktori public Henkilo(String nimi, Koulutus koulutus)",
                _HenkiloRef.constructor().taking(String.class, _KoulutusRef.cls()).isPublic());

        String luokanNimi = "Henkilo";
        Class c = ReflectionUtils.findClass(luokanNimi);

        _Koulutus k = koulutus("FT");

        assertEquals("Luokalla Henkilo tulee olla vain yksi konstruktori, nyt konstruktoreja on muu määrä", 1, c.getConstructors().length);

        _HenkiloRef.constructor().taking(String.class, _KoulutusRef.cls()).withNiceError("\nVirheeseen johtanut koodi new Henkilo(\"Arto\", Koulutus.FT); ").invoke("arto", k);
    }

    @Test
    @Points("09-16.2")
    public void HenkilollaOikeatOliomuuttujat() {
        String luokanNimi = "Henkilo";

        Class c = ReflectionUtils.findClass(luokanNimi);

        boolean k = false;
        boolean n = false;
        int fc = 0;
        for (Field f : c.getDeclaredFields()) {
            String nimi = f.toString();
            if (nimi.contains("String")) {
                n = true;
                fc++;
            } else if (nimi.contains("Koulutus")) {
                k = true;
                fc++;
            } else {
                fail(k + " poista " + f.getName());
            }
            assertTrue("Luokan Henkilo oliomuuttujien tulee olla private, muuta " + f.getName(), nimi.contains("private"));
        }
        assertTrue("Luokalla Henkilo oltava String-tyyppinen oliomuuttuja", n);
        assertTrue("Luokalla Henkilo oltava Koulutus-tyyppinen oliomuuttuja", k);
        assertTrue("Luokalla Henkilo tarvitaan vaan 2 oliomuuttujaa", fc == 2);
    }

    @Test
    @Points("09-16.2")
    public void HenkilonToString() {
        Object henkilo = hlo("Arto", "FT");
        assertFalse("Määrittele luokalle Henkilo esimerkin mukaan toimiva toString", henkilo.toString().contains("@"));

        assertEquals("h = new Henkilo(\"Arto\", Koulutus.FT); \nSystem.out.print(h);\n", "Arto, FT", henkilo.toString());
    }

    @Test
    @Points("09-16.2")
    public void HenkilonToString2() {
        String[][] tt = {{"Pekka", "FilYO"}, {"Mikke", "LuK"}, {"Thomas", "FM"}, {"Esko", "FT"}};

        for (String[] nimiKoulu : tt) {
            Object henkilo = hlo(nimiKoulu[0], nimiKoulu[1]);

            assertEquals("h = new Henkilo(\"" + nimiKoulu[0] + "\", Koulutus." + nimiKoulu[1] + "); \nSystem.out.print(h);\n", nimiKoulu[0] + ", " + nimiKoulu[1], henkilo.toString());
        }

    }

    @Test
    @Points("09-16.2")
    public void koulutusGetteri() {
        String metodi = "getKoulutus";
        String virhe = "tee luokalle Henkilo metodi public Koulutus getKoulutus()";

        assertTrue(virhe, _HenkiloRef.method(metodi).returning(_KoulutusRef.cls()).takingNoParams().isPublic());
    }

    @Test
    @Points("09-16.2")
    public void koulutusGetteriEiKaada() throws Throwable {
        _Henkilo hlo = luoHenkilo("Pekka", koulutus("FT"));
        _Koulutus vast = _HenkiloRef.method("getKoulutus").returning(_KoulutusRef.cls()).
                takingNoParams().withNiceError("\nvirheeseen johtanut koodi\nHenkilo h = new Henkilo(\"Arto\", Koulutus.FT); \nh.getKoulutus();\n").invokeOn(hlo);

        assertEquals("Henkilo h = new Henkilo(\"Arto\", Koulutus.FT); \nh.getKoulutus();\n", koulutus("FT"), vast);
    }

    @Test
    @Points("09-16.2")
    public void koulutusGetteriToimii() throws Exception {
        String[][] tt = {{"Pekka", "FilYO"}, {"Mikke", "LuK"}, {"Thomas", "FM"}, {"Esko", "FT"}};

        for (String[] nimiKoulu : tt) {
            Object henkilo = hlo(nimiKoulu[0], nimiKoulu[1]);

            assertEquals("h = new Henkilo(\"" + nimiKoulu[0] + "\", Koulutus." + nimiKoulu[1] + "); \nh.getKoulutus();\n", nimiKoulu[1], getKoulutus(henkilo).toString());
        }

    }

    @Test
    @Points("09-16.3")
    public void onLuokkaTyontekijat() {
        String luokanNimi = "Tyontekijat";
        _TyontekRef = Reflex.reflect(luokanNimi);
        assertTrue("tee pakkauseen henkilosto luokka Tyontekijat", _TyontekRef.isPublic());
    }

    @Test
    @Points("09-16.3")
    public void TyontekijoillaOikeaKonstruktori() throws Throwable {
        assertTrue("Tee luokalle Tyontekijat konstruktori public Tyontekijat()",
                _TyontekRef.constructor().takingNoParams().isPublic());

        _TyontekRef.constructor().takingNoParams().withNiceError("\nVirheeseen johtanut koodi new Tyontekijat(); ").invoke();
    }

    private _Tyontekijat luoTyontakijat() throws Throwable {
        return _TyontekRef.constructor().takingNoParams().invoke();
    }

    @Test
    @Points("09-16.3")
    public void onMetodiLisaaHenkilo() throws Throwable {
        String metodi = "lisaa";
        String virhe = "tee luokalle Tyontekijat metodi public void lisaa(Henkilo lisattava)";
        _Henkilo henkilo = luoHenkilo("Arto", koulutus("FT"));

        _Tyontekijat tt = luoTyontakijat();

        assertTrue(virhe, _TyontekRef.method(tt, metodi).returningVoid().taking(_HenkiloRef.cls()).isPublic());
        String v = "Virheen aiheutti koodi\n"
                + "Tyontekijat tt = new Tyontekijat();\n"
                + "tt.lisaa( new Henkilo(\"Arto\", koulutus.FT));";
        _TyontekRef.method(tt, metodi).returningVoid().taking(_HenkiloRef.cls()).withNiceError(v).invoke(henkilo);
    }

    private void lisaa(_Tyontekijat tt, _Henkilo henkilo, String v) throws Throwable {
        _TyontekRef.method(tt, "lisaa").returningVoid().taking(_HenkiloRef.cls()).withNiceError(v).invoke(henkilo);
    }

    private void lisaa(_Tyontekijat tt, List h, String v) throws Throwable {
        _TyontekRef.method(tt, "lisaa").returningVoid().taking(List.class).withNiceError(v).invoke(h);
    }

    private void tulosta(_Tyontekijat tt, String v) throws Throwable {
        _TyontekRef.method(tt, "tulosta").returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    private void tulosta(_Tyontekijat tt, _Koulutus k, String v) throws Throwable {
        _TyontekRef.method(tt, "tulosta").returningVoid().taking(_KoulutusRef.cls()).withNiceError(v).invoke(k);
    }

    @Test
    @Points("09-16.3")
    public void onMetodiLisaaList() throws Throwable {
        String metodi = "lisaa";
        String virhe = "tee luokalle Tyontekijat metodi public void lisaa(List<Henkilo> lisattavat)";

        _Henkilo henkilo = luoHenkilo("Arto", koulutus("FT"));
        _Henkilo henkilo2 = luoHenkilo("Pekka", koulutus("FM"));

        _Tyontekijat tt = luoTyontakijat();

        assertTrue(virhe, _TyontekRef.method(tt, metodi).returningVoid().taking(List.class).isPublic());
        String v = "Virheen aiheutti koodi\n"
                + "List hlot = new ArrayList<Henkilo>();\n"
                + "hlot.add(new Henkilo(\"Arto\", koulutus.FT));\n"
                + "hlot.add(new Henkilo(\"Pekka\", koulutus.FM));\n"
                + "Tyontekijat tt = new Tyontekijat();\n"
                + "tt.lisaa(hlot);";

        List h = new ArrayList();
        h.add(henkilo);
        h.add(henkilo2);
        _TyontekRef.method(tt, metodi).returningVoid().taking(List.class).withNiceError(v).invoke(h);
    }

    /*
     *
     */
    @Test
    @Points("09-16.3")
    public void onMetodiTulosta() throws Throwable {
        String metodi = "tulosta";
        String virhe = "tee luokalle Tyontekijat metodi public void tulosta()";

        _Tyontekijat tt = luoTyontakijat();
        assertTrue(virhe, _TyontekRef.method(tt, metodi).returningVoid().takingNoParams().isPublic());
        String v = "Virheen aiheutti koodi\nTyontekijat t = new Tyontekijat();\nt.tulosta();\n";

        _TyontekRef.method(tt, metodi).returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("09-16.3")
    public void tulostusToimii1() throws Throwable {
        String v = "Tarkasta koodi \n"
                + "t = new Tyontekijat(); \n"
                + "h = new Henkilo(\"Arto\", Koulutus.FT); \n"
                + "t.lisaa(h); \n"
                + "t.tulosta(), \n"
                + "tulostuksen pitäisi sisältää rivi \"Arto, FT\"\n";

        MockInOut io = new MockInOut("");
        _Tyontekijat tt = luoTyontakijat();
        _Henkilo henkilo = luoHenkilo("Arto", koulutus("FT"));
        lisaa(tt, henkilo, v);
        tulosta(tt, v);
        String out = io.getOutput();
        assertTrue(v + "tulostui\n" + out, out.contains(henkilo.toString()));
    }

    @Test
    @Points("09-16.3")
    public void tulostusToimii2() throws Throwable {
        String v = "Tarkasta koodi t = new Tyontekijat(); \n"
                + "h = new Henkilo(\"Arto\", Koulutus.FT); \n"
                + "t.lisaa(h); \n"
                + "h2 = new Henkilo(\"Pekka\", Koulutus.FilYO); \n"
                + "t.lisaa(h2); \n"
                + "t.tulosta();\n"
                + "tulostuksen pitäisi sisältää rivi \"Arto, FT\"\n";
        String v2 = "Tarkasta koodi \n"
                + "t = new Tyontekijat();\n"
                + "h = new Henkilo(\"Pekka\",Koulutus.FT); \n"
                + "t.lisaa(h); \n"
                + "t.tulosta()\n"
                + "tulostuksen pitäisi sisältää rivi \"Pekka, FilYO\"\n";

        MockInOut io = new MockInOut("");
        _Tyontekijat tt = luoTyontakijat();
        _Henkilo henkilo = luoHenkilo("Arto", koulutus("FT"));
        _Henkilo henkilo2 = luoHenkilo("Pekka", koulutus("FilYO"));
        lisaa(tt, henkilo, v);
        lisaa(tt, henkilo2, v);
        tulosta(tt, v);
        String out = io.getOutput();
        assertTrue(v + "tulostui\n" + out, out.contains(henkilo.toString()));
        assertTrue(v2 + "tulostui\n" + out, out.contains(henkilo2.toString()));
    }

    @Test
    @Points("09-16.3")
    public void tulostusToimii3() throws Throwable {
        String v = "Tarkasta koodi \nt = new Tyontekijat(); \n"
                + "ArrayList<Henkilo> lista = new ...; \n"
                + "lista.add((\"Arto\", Koulutus.FT); \n"
                + "lista.add(\"Pekka\", Koulutus.FilYO); \n"
                + "t.lisaa(lista); \n"
                + "t.tulosta(); \n"
                + "tulostuksen pitäisi sisältää rivi \"Arto, FT\"\n"
                + "";
        String v2 = "Tarkasta koodi \nt = new Tyontekijat(); \n"
                + "ArrayList<Henkilo> lista = new ...; \n"
                + "lista.add((\"Arto\", Koulutus.FT); \n"
                + "lista.add(\"Pekka\", Koulutus.FilYO); \n"
                + "t.lisaa(lista); \n"
                + "t.tulosta();\n "
                + "tulostuksen pitäisi sisältää rivi \"Pekka, FilYO\"\n";

        MockInOut io = new MockInOut("");
        _Tyontekijat tt = luoTyontakijat();
        _Henkilo henkilo = luoHenkilo("Arto", koulutus("FT"));
        _Henkilo henkilo2 = luoHenkilo("Pekka", koulutus("FilYO"));
        ArrayList l = new ArrayList();
        l.add(henkilo);
        l.add(henkilo2);
        lisaa(tt, l, v);
        tulosta(tt, v);
        String out = io.getOutput();
        assertTrue(v + "tulostui\n" + out, out.contains(henkilo.toString()));
        assertTrue(v2 + "tulostui\n" + out, out.contains(henkilo2.toString()));

    }

    @Test
    @Points("09-16.3")
    public void eiylimaaraisiaOliomuuttujia() {
        Object tt = tyontekijat();
        String v = "Et tarvitse luokalle Tyontekijat kuin yhden oliomuuttujan"
                + ", eli listan joka tallettaa Henkilo-oliota. Poista ylimääräiset";
        assertEquals(v, 1, tt.getClass().getDeclaredFields().length);

    }

    @Test
    @Points("09-16.3")
    public void tulostuskayttaaIteraattoria() {
        iteraattoriKaytossa();
    }

    /*
     *
     */
    @Test
    @Points("09-16.3")
    public void onMetodiTulostaKoulutus() throws Throwable {
        String metodi = "tulosta";
        String virhe = "tee luokalle Tyontekijat metodi public void tulosta(Koulutus koulutus)";

        _Tyontekijat tt = luoTyontakijat();
        assertTrue(virhe, _TyontekRef.method(tt, metodi).returningVoid().taking(_KoulutusRef.cls()).isPublic());
        String v = "Virheen aiheutti koodi\nTyontekijat t = new Tyontekijat();\nt.tulosta(Koulutus.FilYo);\n";

        _TyontekRef.method(tt, metodi).returningVoid().taking(_KoulutusRef.cls()).withNiceError(v).invoke(koulutus("FilYO"));
    }

    @Test
    @Points("09-16.3")
    public void filtteroityTulostusToimii1() throws Throwable {
        String v = "Tarkasta koodi \n"
                + "t = new Tyontekijat(); \n"
                + "h = new Henkilo(\"Arto\", Koulutus.FT); \n"
                + "t.lisaa(h); t.tulosta(Koulutus.FT); \n"
                + "tulostuksen pitäisi sisältää rivi \"Arto, FT\"\n";

        MockInOut io = new MockInOut("");
        _Tyontekijat tt = luoTyontakijat();
        _Henkilo henkilo = luoHenkilo("Arto", koulutus("FT"));
        lisaa(tt, henkilo, v);
        tulosta(tt, koulutus("FT"), v);
        String out = io.getOutput();
        assertTrue(v + "tulostui\n" + out, out.contains(henkilo.toString()));
    }

    @Test
    @Points("09-16.3")
    public void filtteroityTulostusToimii1b() throws Throwable {
        String v = "Tarkasta koodi \n"
                + "t = new Tyontekijat(); \n"
                + "h = new Henkilo(\"Arto\", Koulutus.LuK); \n"
                + "t.lisaa(h); \n"
                + "t.tulosta(Koulutus.FT)\n "
                + "ei pitäisi tulostaa mitään\n";

        MockInOut io = new MockInOut("");
        _Tyontekijat tt = luoTyontakijat();
        _Henkilo henkilo = luoHenkilo("Arto", koulutus("FT"));
        lisaa(tt, henkilo, v);
        tulosta(tt, koulutus("LuK"), v);
        String out = io.getOutput();
        assertFalse(v + "tulostui\n" + out, out.contains(henkilo.toString()));
    }

    @Test
    @Points("09-16.3")
    public void filtteroityTulostuskayttaaIteraattoria() {
        iteraattoriKaytossa2();
    }

    /*
     *
     */
    @Test
    @Points("09-16.4")
    public void onMetodiIrtisano() throws Throwable {
        String metodi = "irtisano";
        String virhe = "tee luokalle Tyontekijat metodi public void irtisano(Koulutus koulutus)";

        _Tyontekijat tt = luoTyontakijat();
        assertTrue(virhe, _TyontekRef.method(tt, metodi).returningVoid().taking(_KoulutusRef.cls()).isPublic());
        String v = "Virheen aiheutti koodi\nTyontekijat t = new Tyontekijat();\n"
                + "t.irtisano(Koulutus.FT);\n";

        _TyontekRef.method(tt, metodi).returningVoid().taking(_KoulutusRef.cls()).withNiceError(v).invoke(koulutus("FT"));
    }

    private void irtisano(_Tyontekijat tt, _Koulutus k, String v) throws Throwable {
        _TyontekRef.method(tt, "irtisano").returningVoid().taking(_KoulutusRef.cls()).withNiceError(v).invoke(k);

    }

    @Test
    @Points("09-16.4")
    public void irtisanominenOnnistuu() throws Throwable {
        String v = "Tarkasta koodi \n"
                + "t = new Tyontekijat(); \n"
                + "h = new Henkilo(\"Arto\", Koulutus.FT); \n"
                + "t.lisaa(h); \n"
                + "t.irtisano(Koulutus.FT); \n"
                + "t.tulosta()\n"
                + " ei pitäisi tulostaa mitään\n";

        MockInOut io = new MockInOut("");
        _Tyontekijat tt = luoTyontakijat();
        _Henkilo henkilo = luoHenkilo("Arto", koulutus("FT"));
        lisaa(tt, henkilo, v);

        irtisano(tt, koulutus("FT"), v);

        tulosta(tt, v);
        String out = io.getOutput();
        assertFalse(v + "tulostui\n" + out, out.contains(henkilo.toString()));
    }

    @Test
    @Points("09-16.4")
    public void irtisanominenKayttaaIteraattoria() {
        iteraattoriKaytossa3();
    }

    @Test
    @Points("09-16.4")
    public void irtisanominenOnnistuu2() throws Throwable {
        String v = "Tarkasta koodi \nt = new Tyontekijat(); \n"
                + "h = new Henkilo(\"Arto\", Koulutus.FT); t.lisaa(h); \n"
                + "h = new Henkilo(\"Pekka\", Koulutus.LuK); t.lisaa(h); \n"
                + "h = new Henkilo(\"Matti\", Koulutus.FT); t.lisaa(h); \n"
                + "t.irtisano(Koulutus.FT);\n t.tulosta();\n. Vain Pekan pitäisi tulostua\n";

        MockInOut io = new MockInOut("");
        _Tyontekijat tt = luoTyontakijat();
        _Henkilo henkilo1 = luoHenkilo("Arto", koulutus("FT"));
        lisaa(tt, henkilo1, v);
        _Henkilo henkilo2 = luoHenkilo("Pekka", koulutus("LuK"));
        lisaa(tt, henkilo2, v);
        _Henkilo henkilo3 = luoHenkilo("Matti", koulutus("FT"));
        lisaa(tt, henkilo3, v);

        irtisano(tt, koulutus("FT"), v);

        tulosta(tt, v);
        String out = io.getOutput();
        assertFalse(v + "tulostui\n" + out, out.contains(henkilo1.toString()));
        assertTrue(v + "tulostui\n" + out, out.contains(henkilo2.toString()));
        assertFalse(v + "tulostui\n" + out, out.contains(henkilo3.toString()));
    }

    @Test
    @Points("09-16.4")
    public void irtisanominenOnnistuu3() throws Throwable {
        String v = "Tarkasta koodi \n"
                + "t = new Tyontekijat(); \n"
                + "h = new Henkilo(\"Arto\", Koulutus.FT); t.lisaa(h); \n"
                + "h = new Henkilo(\"Pekka\", Koulutus.LuK); t.lisaa(h); \n"
                + "h = new Henkilo(\"Matti\", Koulutus.FT); t.lisaa(h); \n"
                + "t.irtisano(Koulutus.FM);\n t.tulosta();\n Kaikkien pitäisi tulostua\n";

        MockInOut io = new MockInOut("");
        _Tyontekijat tt = luoTyontakijat();
        _Henkilo henkilo1 = luoHenkilo("Arto", koulutus("FT"));
        lisaa(tt, henkilo1, v);
        _Henkilo henkilo2 = luoHenkilo("Pekka", koulutus("LuK"));
        lisaa(tt, henkilo2, v);
        _Henkilo henkilo3 = luoHenkilo("Matti", koulutus("FT"));
        lisaa(tt, henkilo3, v);

        irtisano(tt, koulutus("FM"), v);

        tulosta(tt, v);
        String out = io.getOutput();
        assertTrue(v + "tulostui\n" + out, out.contains(henkilo1.toString()));
        assertTrue(v + "tulostui\n" + out, out.contains(henkilo2.toString()));
        assertTrue(v + "tulostui\n" + out, out.contains(henkilo3.toString()));
    }

    @Test
    @Points("09-16.4")
    public void irtisanominenOnnistuu4() throws Throwable {
        String v = "Tarkasta koodi \n"
                + "t = new Tyontekijat(); \n"
                + "h = new Henkilo(\"Arto\", Koulutus.FT); t.lisaa(h); \n"
                + "h = new Henkilo(\"Pekka\", Koulutus.LuK); t.lisaa(h); \n"
                + "h = new Henkilo(\"Matti\", Koulutus.FT); t.lisaa(h); \n"
                + "t.irtisano(Koulutus.LuK);\n "
                + "t.tulosta(); \n"
                + "Arton ja Matin pitäisi tulostua\n";

        MockInOut io = new MockInOut("");
        _Tyontekijat tt = luoTyontakijat();
        _Henkilo henkilo1 = luoHenkilo("Arto", koulutus("FT"));
        lisaa(tt, henkilo1, v);
        _Henkilo henkilo2 = luoHenkilo("Pekka", koulutus("LuK"));
        lisaa(tt, henkilo2, v);
        _Henkilo henkilo3 = luoHenkilo("Matti", koulutus("FT"));
        lisaa(tt, henkilo3, v);

        irtisano(tt, koulutus("LuK"), v);

        tulosta(tt, v);
        String out = io.getOutput();
        assertTrue(v + "tulostui\n" + out, out.contains(henkilo1.toString()));
        assertFalse(v + "tulostui\n" + out, out.contains(henkilo2.toString()));
        assertTrue(v + "tulostui\n" + out, out.contains(henkilo3.toString()));
    }

    /*
     *
     */
    private Object getKoulutus(Object olio) throws Exception {
        String metodi = "getKoulutus";
        String virhe = "tee luokalle Henkilo metodi public Koulutus getKoulutus()";

        Object k = koulutus("FT");
        Class c = ReflectionUtils.findClass("Henkilo");
        Method m = ReflectionUtils.requireMethod(c, k.getClass(), metodi);

        return m.invoke(olio);
    }

    private Object tyontekijat() {
        Class c = ReflectionUtils.findClass("Tyontekijat");

        Constructor ctor = ReflectionUtils.requireConstructor(c);
        try {
            return ctor.newInstance();
        } catch (Throwable e) {
            fail("new Tyontekijat(); johti virheeseen lisätietoja " + e);
        }
        return null;
    }

    private Object hlo(String n, String ktus) {
        Class c = ReflectionUtils.findClass("Henkilo");
        String nimi = n;
        Object k = koulutus(ktus);
        Constructor ctor = c.getConstructors()[0];
        try {
            return ctor.newInstance(n, k);
        } catch (Throwable e) {
            fail("new Henkilo(\"Arto\", Koulutus.FT); johti virheeseen lisätietoja " + e);
        }
        return null;
    }

    private _Koulutus koulutus(String e) {
        String luokanNimi = "Koulutus";
        Class c = ReflectionUtils.findClass(luokanNimi);
        Object[] vakiot = c.getEnumConstants();

        for (Object vakio : vakiot) {
            if (vakio.toString().equals(e)) {
                return (_Koulutus) vakio;
            }
        }

        return null;
    }

    private boolean sis(String tunnus, Object[] vakiot) {
        for (Object vakio : vakiot) {
            if (vakio.toString().equals(tunnus)) {
                return true;
            }
        }
        return false;
    }

    private void iteraattoriKaytossa() {
        try {
            Scanner lukija = new Scanner(Paths.get("src", "main", "java", "Tyontekijat.java").toFile());
            int metodissa = 0;

            int iteraattori = 0;
            while (lukija.hasNext()) {

                String rivi = lukija.nextLine();

                if (rivi.indexOf("//") > -1) {
                    rivi = rivi.substring(0, rivi.indexOf("//"));
                }

                if (rivi.contains("void") && rivi.contains("tulosta")) {
                    metodissa++;

                } else if (metodissa > 0) {
                    if (rivi.contains("{") && !rivi.contains("}")) {
                        metodissa++;
                    }

                    if (rivi.contains("}") && !rivi.contains("{")) {
                        metodissa--;
                    }

                    if (rivi.contains("Iterator") && rivi.contains("<Henkilo>")) {
                        iteraattori++;
                    }
                    if (rivi.contains(".hasNext(")) {
                        iteraattori++;
                    }
                    if (rivi.contains(".next(")) {
                        iteraattori++;
                    }

                }

            }
            assertTrue("Luokan Tyontekijat metodin tulosta() tulee hoitaa listan läpikäynti "
                    + "iteraattorin avulla, katso ohje materiaalista!", iteraattori > 2);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    private void iteraattoriKaytossa2() {
        try {
            Scanner lukija = new Scanner(Paths.get("src", "main", "java", "Tyontekijat.java").toFile());
            int metodissa = 0;

            int iteraattori = 0;
            while (lukija.hasNext()) {

                String rivi = lukija.nextLine();

                if (rivi.indexOf("//") > -1) {
                    rivi = rivi.substring(0, rivi.indexOf("//"));
                }

                if (rivi.contains("void") && rivi.contains("tulosta") && rivi.contains("Koulutus")) {
                    metodissa++;
                } else if (metodissa > 0) {
                    if (rivi.contains("{") && !rivi.contains("}")) {
                        metodissa++;
                    }

                    if (rivi.contains("}") && !rivi.contains("{")) {
                        metodissa--;
                    }

                    if (rivi.contains("Iterator") && rivi.contains("<Henkilo>")) {
                        iteraattori++;
                    }
                    if (rivi.contains(".hasNext(")) {
                        iteraattori++;
                    }
                    if (rivi.contains(".next(")) {
                        iteraattori++;
                    }

                }

            }
            assertTrue("Luokan Tyontekijat metodin tulosta(Koulutus koulutus) tulee hoitaa listan läpikäynti "
                    + "iteraattorin avulla, katso ohje materiaalista!", iteraattori > 2);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    private void iteraattoriKaytossa3() {
        try {
            Scanner lukija = new Scanner(Paths.get("src", "main", "java", "Tyontekijat.java").toFile());
            int metodissa = 0;

            while (lukija.hasNext()) {

                String rivi = lukija.nextLine();

                if (rivi.indexOf("//") > -1) {
                    rivi = rivi.substring(0, rivi.indexOf("//"));
                }

                if (rivi.contains("void") && rivi.contains("irtisano")) {
                    metodissa++;
                } else if (metodissa > 0) {
                    if (rivi.contains("{") && !rivi.contains("}")) {
                        metodissa++;
                    }

                    if (rivi.contains("}") && !rivi.contains("{")) {
                        metodissa--;
                    }

                    if (rivi.contains("get(")) {
                        fail("Luokan Tyontekija metodin irtisano() tulee käydä lista läpi iteraattorin avulla"
                                + ", iteraattorin avulla läpikäynnissä listalta poisto ei aiheuta ongelma. "
                                + "\nOta mallia kurssimateriaalista");
                    }

                }

            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
