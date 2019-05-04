package testausta;

import java.util.Scanner;
import org.junit.Test;
import org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OhjelmaTest {

    // toteuta tänne testit luokkaa Ohjelma-varten
    
    @Test
    public void pullojenSummat() {
        assertTrue("Pullojen summa 2 + 3 ei ollut 5", Ohjelma.suorita(new Scanner("2\n3\n-1\n")).contains("Pulloja: 5"));
        assertTrue("Pullojen summa 3 + 6 + (-3) ei ollut 9", Ohjelma.suorita(new Scanner("3\n6\n-3\n-1\n")).contains("Pulloja: 9"));
        assertTrue("Pullojen summa 0 + 0 + (-8) ei ollut 0", Ohjelma.suorita(new Scanner("0\n0\n-8\n-1\n")).contains("Pulloja: 0"));
    }
    
    @Test
    public void oppilaidenMaara() {
        assertTrue("Oppilaiden määrä pulloilla 2 + 3 ei ollut 2", 
                Ohjelma.suorita(new Scanner("2\n3\n-1\n")).contains("Oppilaita: 2"));
        assertTrue("Oppilaiden määrä pulloilla -2 + -3 ei ollut 0", 
                Ohjelma.suorita(new Scanner("-15\n-3\n-1\n")).contains("Oppilaita: 0"));
        assertTrue("Oppilaiden määrä pulloilla 2 + 3 + 5 + 12 ei ollut 4", 
                Ohjelma.suorita(new Scanner("2\n3\n5\n12\n-1\n")).contains("Oppilaita: 4"));
        assertTrue("Oppilaiden määrä pulloilla 2 + 3 + 5 + 12 + -55 ei ollut 4", 
                Ohjelma.suorita(new Scanner("2\n3\n5\n12\n-55\n-1\n")).contains("Oppilaita: 4"));
        
    }
    
    @Test
    public void keskiarvoTesti() {
        assertTrue("Keskiarvo pulloilla 1 + 3 ei ollut 2.0", 
            Ohjelma.suorita(new Scanner("1\n3\n-1\n")).contains("Keskiarvo: 2.0"));
        assertTrue("Keskiarvo pulloilla 1 + 0 + 0 + 0 ei ollut 0.25", 
            Ohjelma.suorita(new Scanner("1\n0\n0\n0\n-1\n")).contains("Keskiarvo: 0.25"));
        assertTrue("Keskiarvo pulloilla 1 + 3 + -5 ei ollut 2.0", 
            Ohjelma.suorita(new Scanner("1\n3\n-5\n-1\n")).contains("Keskiarvo: 2.0"));
        assertTrue("Tilanteessa, jossa palautettuja pulloja ei ollut, palautetta "
                + "\"Keskiarvoa ei voida laskea\" ei annettu.", 
            Ohjelma.suorita(new Scanner("0\n0\n-9\n-1\n")).contains("Keskiarvoa ei voida laskea"));
    }
    
}
