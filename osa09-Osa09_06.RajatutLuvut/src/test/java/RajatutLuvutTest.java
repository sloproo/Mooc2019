

import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("09-06")
public class RajatutLuvutTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void luvutTest() {
        String syote = "7\n"
                + "14\n"
                + "4\n"
                + "5\n"
                + "4\n"
                + "-1\n";
        io.setSysIn(syote);
        RajatutLuvut.main(new String[0]);

        assertTrue("Syöte oli:\n" + syote + ", odotettiin että tulostus olisi:\n4\n5\n4\nNyt tulostus oli:\n" + syote, io.getSysOut().contains("4"));
        assertTrue("Syöte oli:\n" + syote + ", odotettiin että tulostus olisi:\n4\n5\n4\nNyt tulostus oli:\n" + syote, io.getSysOut().contains("5"));
        assertTrue("Syöte oli:\n" + syote + ", odotettiin että tulostus olisi:\n4\n5\n4\nNyt tulostus oli:\n" + syote, !io.getSysOut().contains("14"));
        assertTrue("Syöte oli:\n" + syote + ", odotettiin että tulostus olisi:\n4\n5\n4\nNyt tulostus oli:\n" + syote, !io.getSysOut().contains("7"));
        assertTrue("Syöte oli:\n" + syote + ", odotettiin että tulostus olisi:\n4\n5\n4\nNyt tulostus oli:\n" + syote, !io.getSysOut().contains("-1"));
    }

    @Test
    public void luvutTest2() {
        String syote = "13\n22\n5\n3242\n-1\n";
        io.setSysIn(syote);
        RajatutLuvut.main(new String[0]);

        assertTrue("Syöte oli:\n" + syote + ", odotettiin että tulostus olisi:\n" + 5 + "\nNyt tulostus oli:\n" + syote, io.getSysOut().contains("5"));
        assertTrue("Syöte oli:\n" + syote + ", odotettiin että tulostus olisi:\n" + 5 + "\nNyt tulostus oli:\n" + syote, !io.getSysOut().contains("22"));
        assertTrue("Syöte oli:\n" + syote + ", odotettiin että tulostus olisi:\n" + 5 + "\nNyt tulostus oli:\n" + syote, !io.getSysOut().contains("3242"));
        assertTrue("Syöte oli:\n" + syote + ", odotettiin että tulostus olisi:\n" + 5 + "\nNyt tulostus oli:\n" + syote, !io.getSysOut().contains("13"));
        assertTrue("Syöte oli:\n" + syote + ", odotettiin että tulostus olisi:\n" + 5 + "\nNyt tulostus oli:\n" + syote, !io.getSysOut().contains("-1"));
    }

}
