
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

@Points("10-10.1 10-10.2")
public class LentokenttaTest {

    String klassName = "lentokentta.Main";
    Reflex.ClassRef<Object> klass;

    @Before
    public void setUp() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void luokkaJulkinen() {
        assertTrue("Pakkauksessa lentokentta olevan luokan Main pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    public void tulostaaMenutJaPoistuu() throws Throwable {
        String syote = "x\nx\n";
        MockInOut io = new MockInOut(syote);
        suorita(f(syote));

        String[] menuRivit = {
            "Lentokentän hallinta",
            "[1] Lisää lentokone",
            "[2] Lisää lento",
            "[x] Poistu hallintamoodista",
            "Lentopalvelu",
            "[1] Tulosta lentokoneet",
            "[2] Tulosta lennot",
            "[3] Tulosta lentokoneen tiedot",
            "[x] Lopeta"
        };

        String output = io.getOutput();
        String op = output;
        for (String menuRivi : menuRivit) {
            int ind = op.indexOf(menuRivi);
            assertRight(menuRivi, syote, output, ind > -1);
            op = op.substring(ind + 1);
        }
    }

    @Test
    public void tulostusLentokoneenLisayksessa() throws Throwable {
        String syote = "1\nAY-123\n108\nx\nx\n";
        MockInOut io = new MockInOut(syote);
        suorita(f(syote));

        String[] menuRivit = {
            "Lentokentän hallinta",
            "[1] Lisää lentokone",
            "[2] Lisää lento",
            "[x] Poistu hallintamoodista",
            "Anna lentokoneen tunnus:",
            "Anna lentokoneen kapasiteetti:",
            "[1] Lisää lentokone",
            "[2] Lisää lento",
            "[x] Poistu hallintamoodista",
            "Lentopalvelu",
            "[1] Tulosta lentokoneet",
            "[2] Tulosta lennot",
            "[3] Tulosta lentokoneen tiedot",
            "[x] Lopeta"
        };

        String output = io.getOutput();
        String op = output;
        for (String menuRivi : menuRivit) {
            int ind = op.indexOf(menuRivi);
            assertRight(menuRivi, syote, output, ind > -1);
            op = op.substring(ind + 1);
        }
    }

    @Test
    public void tulostusLennonLisayksessa() throws Throwable {
        String syote = "1\nAY-123\n108\n"
                + "2\nAY-123\nHEL\nTXL\n"
                + "\nx\nx\n";
        MockInOut io = new MockInOut(syote);
        suorita(f(syote));

        String[] menuRivit = {
            "Lentokentän hallinta",
            "[1] Lisää lentokone",
            "[2] Lisää lento",
            "[x] Poistu hallintamoodista",
            "Anna lentokoneen tunnus:",
            "Anna lentokoneen kapasiteetti:",
            "[1] Lisää lentokone",
            "[2] Lisää lento",
            "[x] Poistu hallintamoodista",
            "Anna lentokoneen tunnus:",
            "Anna lähtöpaikan tunnus:",
            "Anna kohdepaikan tunnus:",
            "[1] Lisää lentokone",
            "[2] Lisää lento",
            "[x] Poistu hallintamoodista",
            "Lentopalvelu",
            "[1] Tulosta lentokoneet",
            "[2] Tulosta lennot",
            "[3] Tulosta lentokoneen tiedot",
            "[x] Lopeta"
        };

        String output = io.getOutput();
        String op = output;
        for (String menuRivi : menuRivit) {
            int ind = op.indexOf(menuRivi);
            assertRight(menuRivi, syote, output, ind > -1);
            op = op.substring(ind + 1);
        }

    }

    @Test
    public void lentokoneidenTulostus1() throws Throwable {
        String syote = "1\nAY-123\n108\n"
                + "x\n"
                + "1\n"
                + "x\n";
        MockInOut io = new MockInOut(syote);
        suorita(f(syote));

        String[] menuRivit = {
            "Lentopalvelu",
            "[1] Tulosta lentokoneet",
            "[2] Tulosta lennot",
            "[3] Tulosta lentokoneen tiedot",
            "[x] Lopeta",
            "AY-123 (108 henkilöä)"
        };

        String output = io.getOutput();
        String op = output;
        for (String menuRivi : menuRivit) {
            int ind = op.indexOf(menuRivi);
            assertRight(menuRivi, syote, output, ind > -1);
            op = op.substring(ind + 1);
        }
    }

    @Test
    public void lentokoneidenTulostus2() throws Throwable {
        String syote = "1\nAY-123\n108\n"
                + "1\nDE-213\n75\n"
                + "x\n"
                + "1\n"
                + "x\n";
        MockInOut io = new MockInOut(syote);
        suorita(f(syote));

        String[] menuRivit = {
            "Lentopalvelu",
            "[1] Tulosta lentokoneet",
            "[2] Tulosta lennot",
            "[3] Tulosta lentokoneen tiedot",
            "[x] Lopeta",};

        String output = io.getOutput();
        String op = output;
        for (String menuRivi : menuRivit) {
            int ind = op.indexOf(menuRivi);
            assertRight(menuRivi, syote, output, ind > -1);
            op = op.substring(ind + 1);
        }

        String rivi = "AY-123 (108 henkilöä)";
        assertRight(rivi, syote, output, output.contains(rivi));

        rivi = "DE-213 (75 henkilöä)";
        assertRight(rivi, syote, output, output.contains(rivi));
    }

    @Test
    public void lentojenTulostus1() throws Throwable {
        String syote = "1\nAY-123\n108\n"
                + "2\nAY-123\nHEL\nTXL\n"
                + "x\n"
                + "2\n"
                + "x\n";
        MockInOut io = new MockInOut(syote);
        suorita(f(syote));

        String[] menuRivit = {
            "Lentopalvelu",
            "[1] Tulosta lentokoneet",
            "[2] Tulosta lennot",
            "[3] Tulosta lentokoneen tiedot",
            "[x] Lopeta",
            "AY-123 (108 henkilöä) (HEL-TXL)"
        };

        String output = io.getOutput();
        String op = output;
        for (String menuRivi : menuRivit) {
            int ind = op.indexOf(menuRivi);
            assertRight(menuRivi, syote, output, ind > -1);
            op = op.substring(ind + 1);
        }

    }

    @Test
    public void lentojenTulostus2() throws Throwable {
        String syote = "1\nAY-123\n108\n"
                + "2\nAY-123\nHEL\nTXL\n"
                + "2\nAY-123\nJFK\nHEL\n"
                + "x\n"
                + "2\n"
                + "x\n";
        MockInOut io = new MockInOut(syote);
        suorita(f(syote));

        String[] menuRivit = {
            "Lentopalvelu",
            "[1] Tulosta lentokoneet",
            "[2] Tulosta lennot",
            "[3] Tulosta lentokoneen tiedot",
            "[x] Lopeta",
        };

        String output = io.getOutput();
        String op = output;
        for (String menuRivi : menuRivit) {
            int ind = op.indexOf(menuRivi);
            assertRight(menuRivi, syote, output, ind > -1);
            op = op.substring(ind + 1);
        }

        String rivi = "AY-123 (108 henkilöä) (HEL-TXL)";
        assertRight(rivi, syote, output, output.contains(rivi));

        rivi = "AY-123 (108 henkilöä) (JFK-HEL)";
        assertRight(rivi, syote, output, output.contains(rivi));
    }

    @Test
    public void lentojenTulostus3() throws Throwable {
        String syote = "1\nAY-123\n108\n"
                + "1\nDE-213\n75\n"
                + "2\nAY-123\nHEL\nTXL\n"
                + "2\nAY-123\nJFK\nHEL\n"
                + "2\nDE-213\nTXL\nBAL\n"
                + "x\n"
                + "2\n"
                + "x\n";
        MockInOut io = new MockInOut(syote);
        suorita(f(syote));

        String[] menuRivit = {
            "Lentopalvelu",
            "[1] Tulosta lentokoneet",
            "[2] Tulosta lennot",
            "[3] Tulosta lentokoneen tiedot",
            "[x] Lopeta",
        };

        String output = io.getOutput();
        String op = output;
        for (String menuRivi : menuRivit) {
            int ind = op.indexOf(menuRivi);
            assertRight(menuRivi, syote, output, ind > -1);
            op = op.substring(ind + 1);
        }

        String rivi = "AY-123 (108 henkilöä) (HEL-TXL)";
        assertRight(rivi, syote, output, output.contains(rivi));

        rivi = "AY-123 (108 henkilöä) (JFK-HEL)";
        assertRight(rivi, syote, output, output.contains(rivi));

        rivi = "DE-213 (75 henkilöä) (TXL-BAL)";
        assertRight(rivi, syote, output, output.contains(rivi));
    }

 @Test
    public void lentokoneenTiedot1() throws Throwable {
        String syote = "1\nAY-123\n108\n"
                + "x\n"
                + "3\n"
                + "AY-123\n"
                + "x\n";
        MockInOut io = new MockInOut(syote);
        suorita(f(syote));

        String[] menuRivit = {
            "Lentopalvelu",
            "[1] Tulosta lentokoneet",
            "[2] Tulosta lennot",
            "[3] Tulosta lentokoneen tiedot",
            "[x] Lopeta",
            "Mikä kone:",
            "AY-123 (108 henkilöä)"
        };

        String output = io.getOutput();
        String op = output;
        for (String menuRivi : menuRivit) {
            int ind = op.indexOf(menuRivi);
            assertRight(menuRivi, syote, output, ind > -1);
            op = op.substring(ind + 1);
        }
    }

 @Test
    public void lentokoneenTiedot2() throws Throwable {
        String syote = "1\nAY-123\n108\n"
                + "1\nDE-213\n75\n"
                + "x\n"
                + "3\n"
                + "AY-123\n"
                + "x\n";
        MockInOut io = new MockInOut(syote);
        suorita(f(syote));

        String[] menuRivit = {
            "Lentopalvelu",
            "[1] Tulosta lentokoneet",
            "[2] Tulosta lennot",
            "[3] Tulosta lentokoneen tiedot",
            "[x] Lopeta",
            "Mikä kone:",
            "AY-123 (108 henkilöä)"
        };

        String output = io.getOutput();
        String op = output;
        for (String menuRivi : menuRivit) {
            int ind = op.indexOf(menuRivi);
            assertRight(menuRivi, syote, output, ind > -1);
            op = op.substring(ind + 1);
        }

        String rivi = "DE-213";
        assertFalse("Varmista, että ohjelmasi tulostusasu on täsmälleen sama kuin esimerkissä\n"
                + f(syote) + "\nohjelman ei olisi pitänyt tulostaa riviä jolla teksti \"" + rivi + "\"!\n"
                + "ohjelmasi tulosti:\n\n" + output, output.contains("DE-213"));
    }
    @Test
    public void monimutkainenSyote() throws Throwable {
        String syote = "1\nAY-123\n108\n"
                + "1\nDE-213\n75\n"
                + "1\nRU-999\n430\n"
                + "2\nAY-123\nHEL\nTXL\n"
                + "2\nAY-123\nJFK\nHEL\n"
                + "2\nDE-213\nTXL\nBAL\n"
                + "x\n"
                + "2\n"
                + "1\n"
                + "3\n"
                + "AY-123\n"
                + "x\n";
        MockInOut io = new MockInOut(syote);
        suorita(f(syote));

        String[] menuRivit = {
            "Lentopalvelu",
            "[1] Tulosta lentokoneet",
            "[2] Tulosta lennot",
            "[3] Tulosta lentokoneen tiedot",
            "[x] Lopeta",};

        String output = io.getOutput();
        String op = output;
        for (String menuRivi : menuRivit) {
            int ind = op.indexOf(menuRivi);
            assertRight(menuRivi, syote, output, ind > -1);
            op = op.substring(ind + 1);
        }

        String rivi = "AY-123 (108 henkilöä) (HEL-TXL)";
        assertRight(rivi, syote, output, output.contains(rivi));

        rivi = "AY-123 (108 henkilöä) (JFK-HEL)";
        assertRight(rivi, syote, output, output.contains(rivi));

        rivi = "DE-213 (75 henkilöä) (TXL-BAL)";
        assertRight(rivi, syote, output, output.contains(rivi));

        int ind = op.indexOf("DE-213 (75 henkilöä) (TXL-BAL)");
        op = op.substring(ind + 1);

        rivi = "AY-123 (108 henkilöä)";
        assertRight(rivi, syote, output, op.contains(rivi));

        rivi = "DE-213 (75 henkilöä)";
        assertRight(rivi, syote, output, op.contains(rivi));

        rivi = "RU-999 (430 henkilöä)";
        assertRight(rivi, syote, output, op.contains(rivi));

        ind = op.indexOf("RU-999 (430 henkilöä)");
        op = op.substring(ind + 1);

        rivi = "AY-123 (108 henkilöä)";
        assertRight(rivi, syote, output, op.contains(rivi));
    }

    private void assertRight(String menuRivi, String syote, String output, boolean ehto) {
        assertTrue("Varmista, että ohjelmasi tulostusasu on täsmälleen sama kuin esimerkissä\n"
                + f(syote) + "\nohjelman olisi pitänyt tulostaa rivi \"" + menuRivi + "\" oikeassa kodassa\n"
                + "ohjelmasi tulosti:\n\n" + output, ehto);
    }

    private String f(String syote) {
        return "\nkäyttäjän syöte oli:\n" + syote;
    }

    private void suorita(String error) throws Throwable {
        String[] args = new String[0];
        klass.staticMethod("main").
                returningVoid().
                taking(String[].class).withNiceError(error).
                invoke(args);
    }
}
