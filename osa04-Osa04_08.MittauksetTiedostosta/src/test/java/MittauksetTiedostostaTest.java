
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;

@Points("04-08")
public class MittauksetTiedostostaTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testaaMittaukset1() throws Throwable {
        mittauksetTest("mittaukset-1.txt", 5, 20, 3);
    }

    @Test
    public void testaaMittaukset2() throws Throwable {
        mittauksetTest("mittaukset-1.txt", 1, 1, 0);
    }

    @Test
    public void testaaMittaukset3() throws Throwable {
        mittauksetTest("mittaukset-1.txt", -999, 999, 4);
    }

    public void mittauksetTest(String tiedosto, int alaraja, int ylaraja, int lukuja) throws Throwable {
        String in = tiedosto + "\n" + alaraja + "\n" + ylaraja + "\n";
        io.setSysIn(in);

        MittauksetTiedostosta.main(new String[]{});

        String out = io.getSysOut();

        assertTrue("Kun syöte on:\n" + in + "Odotettiin, että tulostuksessa olisi \"Lukuja: " + lukuja + "\".\nNyt tulostus oli:\n" + out, out.contains("Lukuja: " + lukuja));
    }
}
