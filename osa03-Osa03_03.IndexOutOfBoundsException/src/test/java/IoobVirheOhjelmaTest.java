
import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.*;
import org.junit.Test;

@Points("03-03")
public class IoobVirheOhjelmaTest {

    @Test
    public void tapahtuuNullPointerException() {
        try {
            IoobVirheOhjelma.main(new String[]{});
            fail("Ohjelman suorituksessa tulee tapahtua IndexOutOfBoundsException-virhe. Nyt ei tapahtunut.");
        } catch (IndexOutOfBoundsException e) {
        }
    }
}
