
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import org.junit.*;

@Points("02-27")
public class MerkkijonoTest {

    @Test(timeout = 1000)
    public void test1() throws Throwable {
        String palautus = Reflex.reflect(Merkkijono.class).staticMethod("merkkijono").returning(String.class).takingNoParams().invoke();
    }
}
