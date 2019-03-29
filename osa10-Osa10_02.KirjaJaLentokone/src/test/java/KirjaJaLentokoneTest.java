
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

@Points("10-02")
public class KirjaJaLentokoneTest {

    @Test
    public void onLuokat() {
        onYksityinenAttribuutti("Kirja", "nimi", String.class);
        onYksityinenAttribuutti("Kirja", "kirjoittaja", String.class);
        onYksityinenAttribuutti("Kirja", "sivuja", int.class);

        onYksityinenAttribuutti("Lentokone", "tunnus", String.class);
        onYksityinenAttribuutti("Lentokone", "malli", String.class);
        onYksityinenAttribuutti("Lentokone", "kayttoonottovuosi", int.class);
    }

    public void onYksityinenAttribuutti(String luokka, String nimi, Class tyyppi) {
        assertTrue("Luokkaa " + luokka + " ei löydy. Onhan se määritelty muodossa public class " + luokka + " { ...?", Reflex.reflect(luokka).isPublic());
        Class luokkaClass = Reflex.reflect(luokka).getReferencedClass();
        String virhe = "Luokassa " + luokka + " pitäisi olla yksityinen (private) muuttuja " + nimi + ", joka on tyyppiä " + tyyppi + ".";

        Field field = null;

        try {
            field = luokkaClass.getDeclaredField(nimi);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(virhe);
        }

        assertTrue(virhe, field.getType().equals(tyyppi));

        assertTrue(virhe, Modifier.isPrivate(field.getModifiers()));
    }

    public void rajattuAttribuuttienMaara(String luokka, int maxAttribuutteja) {
        assertTrue("Luokkaa " + luokka + " ei löydy. Onhan se määritelty muodossa public class " + luokka + " { ...?", Reflex.reflect(luokka).isPublic());

        int attribuutteja = Reflex.reflect(luokka).getReferencedClass().getDeclaredFields().length;
        assertTrue("Luokalla " + luokka + " saa olla korkeintaan " + maxAttribuutteja + " attribuuttia. Nyt niitä oli " + attribuutteja + ".", attribuutteja <= maxAttribuutteja);
    }
}
