
import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.*;
import org.junit.Test;

@Points("05-07")
public class NpeVirheOhjelmaTest {

    @Test
    public void tapahtuuNullPointerException() {
        try {
            NullPointerExceptionOhjelma.main(new String[]{});
        } catch (NullPointerException e) {
            return;
        }
        
        fail("Ohjelman suorituksessa tulee tapahtua NullPointerException-virhe. Nyt ei tapahtunut.");
    }
}
