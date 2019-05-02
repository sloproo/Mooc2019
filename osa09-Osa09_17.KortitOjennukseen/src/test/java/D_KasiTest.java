

import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class D_KasiTest<_Kasi> {

    String klassName = "Kasi";
    Reflex.ClassRef<Object> klass;

    @Before
    public void setUp() {
        klass = Reflex.reflect(klassName);
    }

    public Object luo() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        return ctor.invoke();
    }

    private void lisaa(Object olio, Kortti k) throws Throwable {
        klass.method(olio, "lisaa")
                .returningVoid()
                .taking(Kortti.class)
                .invoke(k);
    }

    @Test
    @Points("09-17.6")
    public void metodiJarjestaMaittain() throws Throwable {
        String metodi = "jarjestaMaittain";

        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "()",
                klass.method(olio, metodi)
                        .returningVoid().takingNoParams().isPublic());

        String v = "Kasi kasi = new Kasi();\n"
                + "kasi.tulosta();";

        klass.method(olio, metodi)
                .returningVoid().takingNoParams().withNiceError("virheen aiheutti koodi \n" + v).
                invoke();
    }

    @Test
    @Points("09-17.6")
    public void maittainJarjestettyTulostusToimii() throws Throwable {
        MockInOut io = new MockInOut("");

        Object olio = luo();

        Kortti k0 = new Kortti(14, Maa.PATA);
        Kortti k = new Kortti(12, Maa.HERTTA);
        Kortti k3 = new Kortti(14, Maa.RUUTU);
        Kortti k2 = new Kortti(2, Maa.PATA);
        Kortti k4 = new Kortti(7, Maa.RUUTU);

        lisaa(olio, k0);
        lisaa(olio, k);
        lisaa(olio, k3);
        lisaa(olio, k2);
        lisaa(olio, k4);

        String v = "Kasi kasi = new Kasi();\n"
                + "kasi.lisaa( new Kortti(14,Maa.PATA) ); \n"
                + "kasi.lisaa( new Kortti(12,Maa.HERTTA) ); \n"
                + "kasi.lisaa( new Kortti(14,Maa.RUUTU) ); \n"
                + "kasi.lisaa( new Kortti(2,Maa.PATA) ); \n"
                + "kasi.lisaa( new Kortti(7,Maa.RUUTU) ); \n"
                + "kasi.jarjestaMaittain();\n"
                + "kasi.tulosta()\n";

        klass.method(olio, "jarjestaMaittain")
                .returningVoid().takingNoParams().withNiceError("virheen aiheutti koodi \n" + v).
                invoke();

        klass.method(olio, "tulosta")
                .returningVoid().takingNoParams().withNiceError("virheen aiheutti koodi \n" + v).
                invoke();

        String out = io.getOutput();

        assertTrue("Koodilla \n" + v + "pitäisi tulostaa 5 riviä, nyt tulostus oli\n" + out, out.split("\n").length > 4);

        int j1 = out.indexOf(k4.toString());
        int j2 = out.indexOf(k3.toString());
        int j3 = out.indexOf(k.toString());
        int j4 = out.indexOf(k2.toString());
        int j5 = out.indexOf(k0.toString());

        assertTrue("Jokainen kortti ei tulostunut koodilla \ntulostus oli\n" + out, j1 > -1 && j2 > -1 && j3 > -1 && j4 > -1 && j5 > -1);

        assertTrue("Koodilla \n" + v + "ensin pitäisi tulostua " + k4 + "\ntulostus oli\n" + out, j1 < j2 && j1 < j3 && j1 < j4 && j1 < j5);
        assertTrue("Koodilla \n" + v + "toisena pitäisi tulostua " + k3 + "\ntulostus oli\n" + out, j2 < j3 && j2 < j4 && j2 < j4);
        assertTrue("Koodilla \n" + v + "kolmantena pitäisi tulostua " + k + "\ntulostus oli\n" + out, j3 < j4 && j3 < j5);
        assertTrue("Koodilla \n" + v + "neljäntenä pitäisi tulostua " + k2 + "\ntulostus oli\n" + out, j4 < j5);

//        String left = out;
//        assertTrue("Koodilla \n" + v + "ensin pitäisi tulostua " + k4 + "\ntulostus oli\n" + out, left.contains(k4.toString()));
//        left = left.substring(left.indexOf(k4.toString()));
//        assertTrue("Koodilla \n" + v + "toisena pitäisi tulostua " + k3 + "\ntulostus oli\n" + out, left.contains(k3.toString()));
//        left = left.substring(left.indexOf(k3.toString()));
//        assertTrue("Koodilla \n" + v + "kolmantena pitäisi tulostua " + k + "\ntulostus oli\n" + out, left.contains(k.toString()));
//        left = left.substring(left.indexOf(k.toString()));
//        assertTrue("Koodilla \n" + v + "neljäntenä pitäisi tulostua " + k2 + "\ntulostus oli\n" + out, left.contains(k2.toString()));
//        left = left.substring(left.indexOf(k2.toString()));
//        assertTrue("Koodilla \n" + v + "viimeisenä pitäisi tulostua " + k0 + "\ntulostus oli\n" + out, left.contains(k0.toString()));
    }
}
