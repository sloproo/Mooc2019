
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import static org.junit.Assert.*;
import org.junit.Test;

@Points("05-05")
public class MontaKonstruktoriaTest {

    @Test
    public void nimellinenKonstruktori() throws Throwable {
        Reflex.reflect(Esine.class).ctor().taking(String.class).requirePublic();

        Esine e = Reflex.reflect(Esine.class).ctor().taking(String.class).invoke("nakki");
        String virhe = "ei ollut oikein. Kokeile:\n"
                + "Esine e = new Esine(\"nakki\");\n"
                + "System.out.println(e.getNimi());\n"
                + "System.out.println(e.getSijainti());\n"
                + "System.out.println(e.getPaino());";

        assertEquals("Nimi " + virhe, "nakki", e.getNimi());
        assertEquals("Sijainti " + virhe, "pientavarahylly", e.getSijainti());
        assertEquals("Paino " + virhe, 1, e.getPaino());
    }

    @Test
    public void nimellinenJaSijainnillinenKonstruktori() throws Throwable {
        Reflex.reflect(Esine.class).ctor().taking(String.class, String.class).requirePublic();

        Esine e = Reflex.reflect(Esine.class).ctor().taking(String.class, String.class).invoke("nauris", "kellari");
        String virhe = "ei ollut oikein. Kokeile:\n"
                + "Esine e = new Esine(\"nauris\", \"kellari\");\n"
                + "System.out.println(e.getNimi());\n"
                + "System.out.println(e.getSijainti());\n"
                + "System.out.println(e.getPaino());";

        assertEquals("Nimi " + virhe, "nauris", e.getNimi());
        assertEquals("Sijainti " + virhe, "kellari", e.getSijainti());
        assertEquals("Paino " + virhe, 1, e.getPaino());
    }

    @Test
    public void nimellinenJaPainollinenKonstruktori() throws Throwable {
        Reflex.reflect(Esine.class).ctor().taking(String.class, int.class).requirePublic();

        Esine e = Reflex.reflect(Esine.class).ctor().taking(String.class, int.class).invoke("laastikasa", 42);
        String virhe = "ei ollut oikein. Kokeile:\n"
                + "Esine e = new Esine(\"laastikasa\", 42);\n"
                + "System.out.println(e.getNimi());\n"
                + "System.out.println(e.getSijainti());\n"
                + "System.out.println(e.getPaino());";

        assertEquals("Nimi " + virhe, "laastikasa", e.getNimi());
        assertEquals("Sijainti " + virhe, "varasto", e.getSijainti());
        assertEquals("Paino " + virhe, 42, e.getPaino());
    }

}
