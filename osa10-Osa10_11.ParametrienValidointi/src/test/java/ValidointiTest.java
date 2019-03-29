
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

public class ValidointiTest {

    Class henkiloLuokka;
    Constructor henkiloConstructor;
    Class laskinLuokka;
    Constructor laskinConstructor;
    Method kertomaMethod;
    Method binomikerroinMethod;

    @Before
    public void setup() {
        try {
            henkiloLuokka = ReflectionUtils.findClass("validointi.Henkilo");
            henkiloConstructor = ReflectionUtils.requireConstructor(henkiloLuokka, String.class, int.class);
            laskinLuokka = ReflectionUtils.findClass("validointi.Laskin");
            laskinConstructor = ReflectionUtils.requireConstructor(laskinLuokka);
            kertomaMethod = ReflectionUtils.requireMethod(laskinLuokka, "kertoma", int.class);
            binomikerroinMethod = ReflectionUtils.requireMethod(laskinLuokka, "binomikerroin", int.class, int.class);
        } catch (Throwable t) {
        }
    }

    @Test
    @Points("10-11.1")
    public void henkiloLuokka() {
        if (henkiloLuokka == null) {
            fail("Olethan luonut luokan Henkilo pakkaukseen validointi, ja onhan sillä määre public?");
        }

        if (henkiloConstructor == null) {
            fail("Onhan luokalla Henkilo konstruktori public Henkilo(String nimi, int ika)?");
        }

        for (int i = 0; i <= 120; i++) {
            try {
                luoHenkilo("mikael", i);
            } catch (IllegalArgumentException e) {
                fail("Henkilön jonka ikä on " + i + " ja nimi on \"mikael\" luominen epäonnistui. Ikien välillä 0 ja 120 pitäisi olla ok.");
            }
        }

        try {
            luoHenkilo("mikael", -5);
            fail("Henkilön luominen negatiivisella iällä onnistui. Henkilo-luokan konstruktorin pitäisi heittää poikkeus IllegalArgumentException jos ikä ei ole välillä 0-120");
        } catch (IllegalArgumentException e) {
        }

        try {
            luoHenkilo("mikael", -1);
            fail("Henkilön luominen negatiivisella iällä onnistui. Henkilo-luokan konstruktorin pitäisi heittää poikkeus IllegalArgumentException jos ikä ei ole välillä 0-120");
        } catch (IllegalArgumentException e) {
        }

        try {
            luoHenkilo("mikael", 121);
            fail("121-vuotiaan henkilön luominen onnistui. Henkilo-luokan konstruktorin pitäisi heittää poikkeus IllegalArgumentException jos ikä ei ole välillä 0-120.");
        } catch (IllegalArgumentException e) {
        }

        try {
            luoHenkilo("mikael", 130);
            fail("130-vuotiaan henkilön luominen onnistui. Henkilo-luokan konstruktorin pitäisi heittää poikkeus IllegalArgumentException jos ikä ei ole välillä 0-120.");
        } catch (IllegalArgumentException e) {
        }

        try {
            luoHenkilo("", 30);
            fail("Henkilön luonti tyhjällä nimellä onnistui. Henkilo-luokan konstruktorin pitäisi heittää poikkeus IllegalArgumentException jos nimi on null tai tyhjä tai yli 40 merkkiä pitkä.");
        } catch (IllegalArgumentException e) {
        }

        try {
            luoHenkilo(null, 30);
            fail("Henkilön luonti null-nimellä onnistui. Henkilo-luokan konstruktorin pitäisi heittää poikkeus IllegalArgumentException jos nimi on null tai tyhjä tai yli 40 merkkiä pitkä.");
        } catch (IllegalArgumentException e) {
        } catch (NullPointerException e) {
            fail("Tarkistathan ettei Henkilo-luokan konstruktori heitä NullPointerException-virhettä jos nimi on null?");
        }

        try {
            luoHenkilo("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 30);
            fail("Henkilön luonti yli 40 merkkiä pitkällä nimellä onnistui. Henkilo-luokan konstruktorin pitäisi heittää poikkeus IllegalArgumentException jos nimi on null tai tyhjä tai yli 40 merkkiä pitkä.");
        } catch (IllegalArgumentException e) {
        }

        try {
            luoHenkilo("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 30);
        } catch (IllegalArgumentException e) {
            fail("Henkilön luonti 40 merkkiä pitkällä nimellä epäonnistui. Henkilo-luokan konstruktorin pitäisi heittää poikkeus IllegalArgumentException jos nimi on null tai tyhjä tai yli 40 merkkiä pitkä.");
        }

        try {
            luoHenkilo("a", 30);
        } catch (IllegalArgumentException e) {
            fail("Henkilön luonti yhden merkin nimellä epäonnistui. Henkilo-luokan konstruktorin pitäisi heittää poikkeus IllegalArgumentException jos nimi on null tai tyhjä tai yli 40 merkkiä pitkä.");
        }
    }

    @Test
    @Points("10-11.2")
    public void laskinLuokka() {
        if (laskinLuokka == null) {
            fail("Olethan luonut luokan Laskin pakkaukseen validointi, ja onhan sillä määre public?");
        }

        if (laskinConstructor == null) {
            fail("Onhan luokalla Laskin konstruktori public Laskin()?");
        }

        if (kertomaMethod == null) {
            fail("Onhan luokalla Laskin metodi public int kertoma(int luvusta)?");
        }

        Object laskin = luoLaskin();

        try {
            kutsuKertoma(laskin, -1);
            fail("Laskin-olion metodin kertoma-kutsuminen onnistui negatiivisella luvulla. Metodin pitäisi toimia vain ei-negatiivisilla luvuilla.");
        } catch (IllegalArgumentException e) {
        }

        try {
            kutsuKertoma(laskin, -42);
            fail("Laskin-olion metodin kertoma-kutsuminen onnistui negatiivisella luvulla. Metodin pitäisi toimia vain ei-negatiivisilla luvuilla.");
        } catch (IllegalArgumentException e) {
        }

        for (int i = 0; i < 5; i++) {
            try {
                kutsuKertoma(laskin, i);
            } catch (IllegalArgumentException e) {
                fail("Laskin-olion metodin kertoma-kutsuminen epäonnistui luvulla " + i + ". Luku " + i + " on ei-negatiivinen, joten laskimen kertoma-metodin pitäisi toimia oikein.");
            }
        }

        if (binomikerroinMethod == null) {
            fail("Onhan luokalla Laskin metodi public int binomikerroin(int joukonKoko, int osaJoukonKoko)?");
        }


        try {
            kutsuBinomikerroin(laskin, -1, 3);
            fail("Laskin-olion metodin binomikerroin-kutsuminen onnistui negatiivisella joukon koolla. Metodin pitäisi toimia vain ei-negatiivisilla luvuilla.");
        } catch (IllegalArgumentException e) {
        }

        try {
            kutsuBinomikerroin(laskin, 3, -1);
            fail("Laskin-olion metodin binomikerroin-kutsuminen onnistui negatiivisella joukon koolla. Metodin pitäisi toimia vain ei-negatiivisilla luvuilla.");
        } catch (IllegalArgumentException e) {
        }

        try {
            kutsuBinomikerroin(laskin, 3, 4);
            fail("Laskin-olion metodin binomikerroin-kutsuminen onnistui siten, että osajoukon koko oli suurempi kuin joukon koko. Metodin pitäisi toimia vain kun joukon koko on suurempi tai yhtäsuuri kuin osajoukon koko.");
        } catch (IllegalArgumentException e) {
        }


    }

    private Object luoLaskin() {
        try {
            return ReflectionUtils.invokeConstructor(laskinConstructor);
        } catch (Throwable t) {
            fail("Laskin-luokan konstruktorin kutsuminen aiheutti poikkeuksen: " + t.getMessage() + ".");
        }

        return null;
    }

    private int kutsuKertoma(Object laskin, int luku) {
        try {
            return ReflectionUtils.invokeMethod(int.class, kertomaMethod, laskin, luku);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Throwable t) {
            fail("Laskin-olion kertoma-metodin kutsuminen aiheutti poikkeuksen: " + t.getMessage() + ".");
        }

        return -1;
    }

    private int kutsuBinomikerroin(Object laskin, int joukonKoko, int osajoukonKoko) {
        try {
            return ReflectionUtils.invokeMethod(int.class, binomikerroinMethod, laskin, joukonKoko, osajoukonKoko);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Throwable t) {
            fail("Laskin-olion binomikerroin-metodin kutsuminen aiheutti poikkeuksen: " + t.getMessage() + ".");
        }

        return -1;
    }

    private Object luoHenkilo(String nimi, int ika) {
        try {
            return ReflectionUtils.invokeConstructor(henkiloConstructor, nimi, ika);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (NullPointerException e) {
            throw e;
        } catch (Throwable t) {
            fail("Henkilo-luokan konstruktorin kutsuminen nimellä: " + nimi + " ja iällä: " + ika + " aiheutti poikkeuksen: " + t.getMessage() + ". Validointivirheiden tulee luoda poikkeus IllegalArgumentException.");
        }

        return null;
    }
}
