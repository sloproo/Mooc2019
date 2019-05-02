
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

@Points("06-04")
public class LyhenteetTest {

    @Test
    public void onLuokkaLyhenteet() {
        assertTrue("Luokka Lyhenteet puuttuu. Onhan se muotoa \"public class Lyhenteet {\"...", Reflex.reflect("Lyhenteet").isPublic());
    }

    @Test
    public void onMetodiLisaaLyhenne() {
        Reflex.reflect("Lyhenteet").method("lisaaLyhenne").returningVoid().taking(String.class, String.class).requirePublic();
    }

    @Test
    public void onMetodiOnkoLyhennetta() {
        Reflex.reflect("Lyhenteet").method("onkoLyhennetta").returning(boolean.class).taking(String.class).requirePublic();
    }

    @Test
    public void onMetodiHaeLyhenne() {
        Reflex.reflect("Lyhenteet").method("haeLyhenne").returning(String.class).taking(String.class).requirePublic();
    }

    @Test
    public void onParametritonKonstruktori() {
        Reflex.reflect("Lyhenteet").ctor().takingNoParams().requirePublic();
    }

    @Test
    public void lyhenteenLisaysHakuJaPoisto() throws Throwable {
        Object lyhenteet = Reflex.reflect("Lyhenteet").ctor().takingNoParams().invoke();
        Reflex.reflect("Lyhenteet").method("lisaaLyhenne").returningVoid().taking(String.class, String.class).invokeOn(lyhenteet, "ok", "olipa kerran");
        Reflex.reflect("Lyhenteet").method("lisaaLyhenne").returningVoid().taking(String.class, String.class).invokeOn(lyhenteet, "jne", "ja niin edelleen");

        String koodi = "Lyhenteet lyhenteet = new Lyhenteet();\n"
                + "lyhenteet.lisaaLyhenne(\"ok\", \"olipa kerran\");\n"
                + "lyhenteet.lisaaLyhenne(\"jne\", \"ja niin edelleen\");\n"
                + "System.out.println(lyhenteet.onkoLyhennetta(\"lol\"));\n"
                + "System.out.println(lyhenteet.onkoLyhennetta(\"jne\"));\n"
                + "System.out.println(lyhenteet.v(\"lol\"));\n"
                + "System.out.println(lyhenteet.haeLyhenne(\"jne\"));";

        assertFalse("Lyhenteen olemassaolon tarkastaminen tuotti jotain ei odotettua. Kokeile koodia:\n" + koodi, Reflex.reflect("Lyhenteet").method("onkoLyhennetta").returning(boolean.class).taking(String.class).invokeOn(lyhenteet, "lol"));
        assertTrue("Lyhenteen olemassaolon tarkastaminen tuotti jotain ei odotettua. Kokeile koodia:\n" + koodi, Reflex.reflect("Lyhenteet").method("onkoLyhennetta").returning(boolean.class).taking(String.class).invokeOn(lyhenteet, "jne"));

        assertNull("Lyhenteen hakeminen tuotti jotain ei odotettua. Kokeile koodia:\n" + koodi, Reflex.reflect("Lyhenteet").method("haeLyhenne").returning(String.class).taking(String.class).invokeOn(lyhenteet, "lol"));
        assertEquals("Lyhenteen hakeminen tuotti jotain ei odotettua. Kokeile koodia:\n" + koodi, "ja niin edelleen", Reflex.reflect("Lyhenteet").method("haeLyhenne").returning(String.class).taking(String.class).invokeOn(lyhenteet, "jne"));
    }

}
