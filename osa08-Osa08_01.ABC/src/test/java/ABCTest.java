
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

public class ABCTest {

    String henkiloLuokanNimi = "Henkilo";
    String opiskelijaLuokanNimi = "Opiskelija";
    String opettajaLuokanNimi = "Opettaja";

    @Test
    @Points("08-01.1")
    public void luokatABC() {
        Reflex.reflect("A").requirePublic();
        Reflex.reflect("B").requirePublic();
        Reflex.reflect("C").requirePublic();
    }

    @Test
    @Points("08-01.1")
    public void metoditABC() {
        Reflex.reflect("A").method("a").returningVoid().takingNoParams().requirePublic();
        Reflex.reflect("B").method("b").returningVoid().takingNoParams().requirePublic();
        Reflex.reflect("C").method("c").returningVoid().takingNoParams().requirePublic();
    }

    @Test
    @Points("08-01.1")
    public void vainMetoditABC() {
        vainYksiMetodi("A");
        vainYksiMetodi("B");
        vainYksiMetodi("C");
    }

    @Test
    @Points("08-01.2")
    public void bPeriiAn() {
        Class c = ReflectionUtils.findClass("B");
        Class sc = c.getSuperclass();
        assertTrue("Luokan B tulee periä luokka A", sc.getName().equals("A"));

    }

    @Test
    @Points("08-01.2")
    public void cPeriiBn() {
        Class c = ReflectionUtils.findClass("C");
        Class sc = c.getSuperclass();
        assertTrue("Luokan C tulee periä luokka B", sc.getName().equals("B"));
    }

    private void vainYksiMetodi(String luokka) {
        int metoditLkm = Reflex.reflect(luokka).cls().getDeclaredMethods().length;
        assertTrue("Luokalla " + luokka + " pitäisi olla vain yksi luokassa määritelty metodi. Nyt niitä oli " + metoditLkm, 1 == metoditLkm);
    }
}
