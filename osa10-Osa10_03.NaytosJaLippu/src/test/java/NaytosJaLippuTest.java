
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

@Points("10-03")
public class NaytosJaLippuTest {

    @Test
    public void onLuokat() {
        onYksityinenAttribuutti("Naytos", "elokuva", String.class);
        onYksityinenAttribuutti("Naytos", "aika", String.class);
        rajattuAttribuuttienMaara("Naytos", 2);

        onYksityinenAttribuutti("Lippu", "paikka", int.class);
        onYksityinenAttribuutti("Lippu", "koodi", int.class);
        rajattuAttribuuttienMaara("Naytos", 3);

        onViiteToiseenLuokkaan("Lippu", "Naytos");
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

    public void onViiteToiseenLuokkaan(String mista, String mihin) {
        assertTrue("Luokkaa " + mista + " ei löydy. Onhan se määritelty muodossa public class " + mista + " { ...?", Reflex.reflect(mista).isPublic());
        assertTrue("Luokkaa " + mihin + " ei löydy. Onhan se määritelty muodossa public class " + mihin + " { ...?", Reflex.reflect(mihin).isPublic());

        Class mistaClass = Reflex.reflect(mista).getReferencedClass();
        Class mihinClass = Reflex.reflect(mihin).getReferencedClass();

        Field viite = null;
        for (Field declaredField : mistaClass.getDeclaredFields()) {
            if (declaredField.getType().equals(mihinClass)) {
                viite = declaredField;
                break;
            }
        }

        assertNotNull("Luokasta " + mista + " ei löytynyt viitettä luokkaan " + mihin + ".\nViite lisätään lisäämällä kohdeluokan tyyppinen oliomuuttuja.", viite);
    }

}
