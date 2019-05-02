

import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Modifier;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Rule;

public class ElaimiaTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    @Points("08-12.1")
    public void abstraktiLuokkaElain() {
        assertTrue("Luokan Elain pitäisi olla abstrakti. Nyt ei ollut.", Modifier.isAbstract(Reflex.reflect("Elain").cls().getModifiers()));
        assertTrue("Luokalla Elain pitäisi olla konstruktori, joka saa parametrina merkkijonon. Nyt ei ollut.", Reflex.reflect("Elain").constructor().taking(String.class).exists());
        assertTrue("Luokalla Elain pitäisi olla metodi getNimi. Nyt ei ollut.", Reflex.reflect("Elain").method("getNimi").returning(String.class).takingNoParams().exists());
        assertTrue("Luokalla Elain pitäisi olla metodi syo. Nyt ei ollut.", Reflex.reflect("Elain").method("syo").returningVoid().takingNoParams().exists());
        assertTrue("Luokalla Elain pitäisi olla metodi nuku. Nyt ei ollut.", Reflex.reflect("Elain").method("nuku").returningVoid().takingNoParams().exists());
    }

    @Test
    @Points("08-12.2")
    public void koiraLuokka() throws Throwable {
        assertFalse("Luokan Koira ei pitäisi olla abstrakti. Nyt oli.", Modifier.isAbstract(Reflex.reflect("Koira").cls().getModifiers()));
        assertTrue("Luokalla Koira pitäisi olla konstruktori, joka saa parametrina merkkijonon. Nyt ei ollut.", Reflex.reflect("Koira").constructor().taking(String.class).exists());
        assertTrue("Luokalla Koira pitäisi olla parametriton konstruktori. Nyt ei ollut.", Reflex.reflect("Koira").constructor().takingNoParams().exists());
        assertTrue("Luokalla Koira pitäisi olla metodi getNimi. Nyt ei ollut.", Reflex.reflect("Koira").method("getNimi").returning(String.class).takingNoParams().exists());
        assertTrue("Luokalla Koira pitäisi olla metodi syo. Nyt ei ollut.", Reflex.reflect("Koira").method("syo").returningVoid().takingNoParams().exists());
        assertTrue("Luokalla Koira pitäisi olla metodi nuku. Nyt ei ollut.", Reflex.reflect("Koira").method("nuku").returningVoid().takingNoParams().exists());
        assertTrue("Luokalla Koira pitäisi olla metodi hauku. Nyt ei ollut.", Reflex.reflect("Koira").method("hauku").returningVoid().takingNoParams().exists());

        Object koira = Reflex.reflect("Koira").constructor().takingNoParams().invoke();
        Reflex.reflect("Koira").method("hauku").returningVoid().takingNoParams().invokeOn(koira);
        assertTrue("Koiran haukkumista ei tulostettu. Tulostus oli:\n" + io.getSysOut(), io.getSysOut().toLowerCase().contains("koira") && io.getSysOut().toLowerCase().contains("haukkuu"));

        Object nalle = Reflex.reflect("Koira").constructor().taking(String.class).invoke("Nalle");
        Reflex.reflect("Koira").method("syo").returningVoid().takingNoParams().invokeOn(nalle);
        assertTrue("Nallen syömistä ei tulostettu. Tulostus oli:\n" + io.getSysOut(), io.getSysOut().toLowerCase().contains("nalle") && io.getSysOut().toLowerCase().contains("syo"));
    }

    @Test
    @Points("08-12.2")
    public void koiraLuokkaMetoditPeritty() {
        assertTrue("Luokan Koira metodin getNimi pitäisi olla peritty. Nyt ei ollut.", Reflex.reflect("Koira").method("getNimi").returning(String.class).takingNoParams().getMethod().getDeclaringClass().toString().contains("Elain"));
        assertTrue("Luokan Koira metodin syo pitäisi olla peritty. Nyt ei ollut.", Reflex.reflect("Koira").method("syo").returningVoid().takingNoParams().getMethod().getDeclaringClass().toString().contains("Elain"));
        assertTrue("Luokan Koira metodin nuku pitäisi olla peritty. Nyt ei ollut.", Reflex.reflect("Koira").method("nuku").returningVoid().takingNoParams().getMethod().getDeclaringClass().toString().contains("Elain"));
    }

    @Test
    @Points("08-12.3")
    public void kissaLuokka() {
        assertFalse("Luokan Kissa ei pitäisi olla abstrakti. Nyt oli.", Modifier.isAbstract(Reflex.reflect("Kissa").cls().getModifiers()));
        assertTrue("Luokalla Kissa pitäisi olla konstruktori, joka saa parametrina merkkijonon. Nyt ei ollut.", Reflex.reflect("Kissa").constructor().taking(String.class).exists());
        assertTrue("Luokalla Kissa pitäisi olla parametriton konstruktori. Nyt ei ollut.", Reflex.reflect("Kissa").constructor().takingNoParams().exists());
        assertTrue("Luokalla Kissa pitäisi olla metodi getNimi. Nyt ei ollut.", Reflex.reflect("Kissa").method("getNimi").returning(String.class).takingNoParams().exists());
        assertTrue("Luokalla Kissa pitäisi olla metodi syo. Nyt ei ollut.", Reflex.reflect("Kissa").method("syo").returningVoid().takingNoParams().exists());
        assertTrue("Luokalla Kissa pitäisi olla metodi nuku. Nyt ei ollut.", Reflex.reflect("Kissa").method("nuku").returningVoid().takingNoParams().exists());
        assertTrue("Luokalla Kissa pitäisi olla metodi hauku. Nyt ei ollut.", Reflex.reflect("Kissa").method("mourua").returningVoid().takingNoParams().exists());
    }

    @Test
    @Points("08-12.3")
    public void kissaLuokkaMetoditPeritty() {
        assertTrue("Luokan Kissa metodin getNimi pitäisi olla peritty. Nyt ei ollut.", Reflex.reflect("Kissa").method("getNimi").returning(String.class).takingNoParams().getMethod().getDeclaringClass().toString().contains("Elain"));
        assertTrue("Luokan Kissa metodin syo pitäisi olla peritty. Nyt ei ollut.", Reflex.reflect("Kissa").method("syo").returningVoid().takingNoParams().getMethod().getDeclaringClass().toString().contains("Elain"));
        assertTrue("Luokan Kissa metodin nuku pitäisi olla peritty. Nyt ei ollut.", Reflex.reflect("Kissa").method("nuku").returningVoid().takingNoParams().getMethod().getDeclaringClass().toString().contains("Elain"));
    }

    @Test
    @Points("08-12.4")
    public void onRajapintaAanteleva() {
        assertTrue("Rajapinnan Aanteleva pitäisi olla rajapinta. Nyt ei ollut.", Modifier.isInterface(Reflex.reflect("Aanteleva").cls().getModifiers()));
        assertTrue("Rajapinnan Aanteleva pitäisi määritellä metodi aantele. Nyt ei ollut.", Reflex.reflect("Aanteleva").method("aantele").returningVoid().takingNoParams().exists());
    }

    @Test
    @Points("08-12.4")
    public void koiraToteuttaaRajapinnanAanteleva() throws Throwable {
        assertTrue("Koiran pitäisi toteuttaa rajapinta Aanteleva. Nyt ei ollut.", Reflex.reflect("Aanteleva").cls().isAssignableFrom(Reflex.reflect("Koira").cls()));

        Object torpedo = Reflex.reflect("Koira").constructor().taking(String.class).invoke("Torpedo");
        Reflex.reflect("Aanteleva").method("aantele").returningVoid().takingNoParams().invokeOn(torpedo);
        Reflex.reflect("Koira").method("syo").returningVoid().takingNoParams().invokeOn(torpedo);

        assertTrue("Koiran haukkumista ei tulostettu kun kutsuttiin aantelevan kautta. Tulostus oli:\n" + io.getSysOut(), io.getSysOut().toLowerCase().contains("torpedo") && io.getSysOut().toLowerCase().contains("haukkuu"));
    }
    
    @Test
    @Points("08-12.4")
    public void kissaToteuttaaRajapinnanAanteleva() throws Throwable {
        assertTrue("Kissan pitäisi toteuttaa rajapinta Aanteleva. Nyt ei ollut.", Reflex.reflect("Aanteleva").cls().isAssignableFrom(Reflex.reflect("Kissa").cls()));

        Object karvinen = Reflex.reflect("Kissa").constructor().taking(String.class).invoke("Karvinen");
        Reflex.reflect("Aanteleva").method("aantele").returningVoid().takingNoParams().invokeOn(karvinen);
        Reflex.reflect("Kissa").method("syo").returningVoid().takingNoParams().invokeOn(karvinen);

        assertTrue("Kissan mouruamista ei tulostettu kun kutsuttiin aantelevan kautta. Tulostus oli:\n" + io.getSysOut(), io.getSysOut().toLowerCase().contains("karvinen") && io.getSysOut().toLowerCase().contains("mouruaa"));
    }

}
