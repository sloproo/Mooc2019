
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-26")
public class NumeroUnoTest {

    @Test(timeout = 1000)
    public void test1() throws Throwable {
        int luku = Reflex.reflect(NumeroUno.class).staticMethod("numeroUno").returning(int.class).takingNoParams().invoke();
        assertEquals("Metodin numeroUno tulee palauttaa arvo 1.", 1, luku);
    }

}
