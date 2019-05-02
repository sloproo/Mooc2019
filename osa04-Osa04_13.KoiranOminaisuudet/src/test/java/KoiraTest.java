
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Assert;

@Points("04-13")
public class KoiraTest {

    @Test
    public void koiraLuokkaOlemassa() {
        try {
            Reflex.reflect("Koira").requirePublic();
        } catch (Throwable t) {
            Assert.fail("Olethan luonut luokan Koira? Tarkista, että nimi on kirjoitettu täsmälleen oikein.");
        }
    }

    @Test
    public void kolmeOliomuuttujaa() {
        koiraLuokkaOlemassa();
        int olioMuuttujia = Reflex.reflect("Koira").cls().getDeclaredFields().length;
        Assert.assertTrue("Teithän luokalle Koira kolme oliomuuttujaa? Nyt oliomuuttujia oli " + olioMuuttujia, olioMuuttujia == 3);
    }

    @Test
    public void oliomuuttujaStringNimiJokaPrivate() {
        oliomuuttujanTarkistus("nimi", String.class, "private String nimi");
    }

    @Test
    public void oliomuuttujaStringRotuJokaPrivate() {
        oliomuuttujanTarkistus("rotu", String.class, "private String rotu");
    }

    @Test
    public void oliomuuttujaIntIkaJokaPrivate() {
        oliomuuttujanTarkistus("ika", int.class, "private int ika");
    }

    private void oliomuuttujanTarkistus(String muuttujanNimi, Class tyyppi, String merkkijonona) {
        kolmeOliomuuttujaa();

        Field nimi = null;

        try {
            nimi = Reflex.reflect("Koira").cls().getDeclaredField(muuttujanNimi);
        } catch (NoSuchFieldException e) {
            Assert.fail("Onhan luokalla Koira oliomuuttuja \"" + merkkijonona + ";\"?");
        }

        Assert.assertTrue("Onhan luokalla Koira oliomuuttuja \"" + merkkijonona + ";\"?", nimi.getType().equals(tyyppi));
        Assert.assertFalse("Onhan luokalla Koira oliomuuttuja \"" + merkkijonona + ";\"?", nimi.isAccessible());
    }
}
