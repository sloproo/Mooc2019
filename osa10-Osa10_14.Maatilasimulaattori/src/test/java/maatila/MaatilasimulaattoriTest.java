package maatila;


import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import fi.helsinki.cs.tmc.edutestutils.Reflex.ClassRef;
import fi.helsinki.cs.tmc.edutestutils.Reflex.MethodRef0;
import fi.helsinki.cs.tmc.edutestutils.Reflex.MethodRef1;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;

public class MaatilasimulaattoriTest<S, L, R, N, M> {

    private ClassRef<S> maitosailioClassRef;
    private MethodRef0<S, Double> maitosailioGetTilavuusMethodRef;
    private MethodRef0<S, Double> maitosailioGetSaldoMethodRef;
    private MethodRef0<S, Double> maitosailioPaljonkoTilaaJaljellaMethodRef;
    private MethodRef1<S, Void, Double> maitosailioLisaaSailioonMethodRef;
    private MethodRef1<S, Double, Double> maitosailioOtaSailiostaMethodRef;
    private ClassRef<L> lehmaClassRef;
    private MethodRef0<L, String> lehmaGetNimiMethodRef;
    private MethodRef0<L, Double> lehmaGetTilavuusMethodRef;
    private MethodRef0<L, Double> lehmaGetMaaraMethodRef;
    private MethodRef0<L, Double> lehmaLypsaMethodRef;
    private MethodRef0<L, Void> lehmaEleleTuntiMethodRef;
    private ClassRef<R> lypsyrobottiClassRef;
    private MethodRef1<R, Void, Lypsava> lypsyrobottiLypsaMethodRef;
    private MethodRef1<R, Void, S> lypsyrobottiSetMaitosailioMethodRef;
    private MethodRef0<R, S> lypsyrobottiGetMaitosailioMethodRef;
    private ClassRef<N> navettaClassRef;
    private MethodRef0<N, S> navettaGetMaitosailioMethodRef;
    private MethodRef1<N, Void, R> navettaAsennaLypsyrobottiMethodRef;
    private MethodRef1<N, Void, L> navettaHoidaLehmaMethodRef;
    private MethodRef1<N, Void, List> navettaHoidaLehmatMethodRef;
    private ClassRef<M> maatilaClassRef;
    private MethodRef0<M, String> maatilaGetOmistajaMethodRef;
    private MethodRef1<M, Void, R> maatilaAsennaNavettaanLypsyrobottiMethodRef;
    private MethodRef1<M, Void, L> maatilaLisaaLehmaMethodRef;
    private MethodRef0<M, Void> maatilaHoidaLehmatMethodRef;
    private MethodRef0<M, Void> maatilaEleleTuntiMethodRef;

    public static void testaaOnkoKokonaisLuku(Class<?> luokka,
            String metodinNimi, double luku, String paluuarvo) {
        if (!Double.valueOf(luku).equals(Math.ceil(luku))) {
            Assert.fail("Luokan " + luokka.getName() + " metodin "
                    + metodinNimi + " paluuarvossa luku " + luku
                    + " pitää pyöristää Math-luokan ceil()-metodilla. "
                    + "Metodin paluuarvo oli: " + paluuarvo);
        }
    }

    @Test
    @Points("10-14.1")
    public void testaaMaitosailio() throws Throwable {
        reflektoiMaitosailio();

        S sailio = luoMaitosailio(maitosailioClassRef, null);

        String v = ""
                + "Maitosailio m = new Maitosailio();";

        testaaTilavuus(sailio, 2000.0, v);
        testaaSaldo(sailio, 0.0, v);
        testaaPaljonkoTilaaJaljella(sailio, v);

        v = ""
                + "Maitosailio m = new Maitosailio();\n"
                + "m.lisaaSailioon(20.234);";

        testaaLisaaSailioon(sailio, 20.234, v);

        v = ""
                + "Maitosailio m = new Maitosailio();\n"
                + "m.lisaaSailioon(20.234);\n"
                + "m.lisaaSailioon(2032.0);";

        testaaLisaaSailioon(sailio, 2032.0, v);

        v = ""
                + "Maitosailio m = new Maitosailio();\n"
                + "m.lisaaSailioon(20.234);\n"
                + "m.lisaaSailioon(2032.0);\n"
                + "m.lisaaSailioon(5000.0);";

        testaaLisaaSailioon(sailio, 5000.0, v);

        sailio = luoMaitosailio(maitosailioClassRef, 5782.4);

        v = ""
                + "Maitosailio m = new Maitosailio(5782.4);";

        testaaTilavuus(sailio, 5782.4, v);
        testaaSaldo(sailio, 0.0, v);
        testaaPaljonkoTilaaJaljella(sailio, v);

        v = ""
                + "Maitosailio m = new Maitosailio(5782.4);\n"
                + "m.lisaaSailioon(3232.13);";

        testaaLisaaSailioon(sailio, 3232.13, v);

        v = ""
                + "Maitosailio m = new Maitosailio(5782.4);\n"
                + "m.lisaaSailioon(3232.13);\n"
                + "m.lisaaSailioon(50000.99);\n";

        testaaLisaaSailioon(sailio, 50000.99, v);

        v = ""
                + "Maitosailio m = new Maitosailio(5782.4);\n"
                + "m.lisaaSailioon(3232.13);\n"
                + "m.lisaaSailioon(50000.99);\n"
                + "m.lisaaSailioon(5000.0);";

        testaaLisaaSailioon(sailio, 5000.0, v);

        v = ""
                + "Maitosailio m = new Maitosailio(5782.4);\n"
                + "m.lisaaSailioon(3232.13);\n"
                + "m.lisaaSailioon(50000.99);\n"
                + "m.lisaaSailioon(5000.0);\n"
                + "m.otaSailiosta(1.5);";

        testaaOtaSailiosta(sailio, 1.5, v);

        v = ""
                + "Maitosailio m = new Maitosailio(5782.4);\n"
                + "m.lisaaSailioon(3232.13);\n"
                + "m.lisaaSailioon(50000.99);\n"
                + "m.lisaaSailioon(5000.0);\n"
                + "m.otaSailiosta(1.5);\n"
                + "m.otaSailiosta(1432.1232);";

        testaaOtaSailiosta(sailio, 1432.1232, v);

        v = ""
                + "Maitosailio m = new Maitosailio(5782.4);\n"
                + "m.lisaaSailioon(3232.13);\n"
                + "m.lisaaSailioon(50000.99);\n"
                + "m.lisaaSailioon(5000.0);\n"
                + "m.otaSailiosta(1.5);\n"
                + "m.otaSailiosta(1432.1232);\n"
                + "m.otaSailiosta(50000.0)";

        testaaOtaSailiosta(sailio, 50000.0, v);

        v = ""
                + "Maitosailio m = new Maitosailio(5782.4);\n"
                + "m.lisaaSailioon(3232.13);\n"
                + "m.lisaaSailioon(50000.99);\n"
                + "m.lisaaSailioon(5000.0);\n"
                + "m.otaSailiosta(1.5);\n"
                + "m.otaSailiosta(1432.1232);\n"
                + "m.otaSailiosta(50000.0)\n"
                + "m.otaSailiosta(50000.0)";

        testaaOtaSailiosta(sailio, 50000.0, v);
    }

    @Test
    @Points("10-14.2")
    public void testaaLehmaa() throws Throwable {
        reflektoiLehma();

        L lehma = luoLehma(lehmaClassRef, null);

        String v = "Lehma lehma = new Lehma();";

        testaaLehmanElelemista(lehma, v);
        testaaLehmanLypsamista(lehma, v);

        testaaLehmanElelemista(lehma, v);
        testaaLehmanElelemista(lehma, v);

        testaaLehmanLypsamista(lehma, v);
        testaaLehmanLypsamista(lehma, v);

        for (int i = 0; i < 300; i++) {
            testaaLehmanElelemista(lehma, v);
        }

        testaaLehmanLypsamista(lehma, v);
    }

    @Test
    @Points("10-14.3")
    public void testaaLypsyrobottia() throws Throwable {
        reflektoiMaitosailio();
        reflektoiLehma();
        reflektoiLypsyrobotti();

        R robo = luoLypsyrobotti(lypsyrobottiClassRef);
        L lehma = luoLehma(lehmaClassRef, null);

        testaaLehmanElelemista(lehma);

        S sailio = luoMaitosailio(maitosailioClassRef, 100.0);

        String v = "Lypsyrobotti r = new Lypsyrobotti();\n"
                + "Maitosailio m = new Maitosailio(100.0);\n"
                + "r.setMaitosailio(m);";

        lypsyrobottiSetMaitosailioMethodRef.withNiceError(v).invokeOn(robo, sailio);

        testaaLehmanElelemista(lehma);
        testaaLehmanElelemista(lehma);

        testaaLypsyrobotinLypsamista(robo, lehma);
    }

    @Test
    @Points("10-14.3")
    public void testaaLypsyrobottiaIlmanSailiota() throws Throwable {
        reflektoiMaitosailio();
        reflektoiLehma();
        reflektoiLypsyrobotti();

        R robo = luoLypsyrobotti(lypsyrobottiClassRef);
        L lehma = luoLehma(lehmaClassRef, null);

        testaaLypsyrobotinLypsamistaIlmanSailota(robo, lehma);
    }

    /*
     *
     */
    @Test
    @Points("10-14.4")
    public void testaaNavettaa() throws Throwable {
        reflektoiMaitosailio();
        reflektoiLehma();
        reflektoiLypsyrobotti();
        reflektoiNavetta();

        L lehma1 = luoLehma(lehmaClassRef, "Lehmä1");
        L lehma2 = luoLehma(lehmaClassRef, "Lehmä2");
        L lehma3 = luoLehma(lehmaClassRef, "Lehmä3");
        L lehma4 = luoLehma(lehmaClassRef, "Lehmä4");

        testaaLehmanElelemista(lehma1);
        testaaLehmanElelemista(lehma1);
        testaaLehmanElelemista(lehma1);
        testaaLehmanElelemista(lehma1);
        testaaLehmanElelemista(lehma1);
        testaaLehmanElelemista(lehma2);
        testaaLehmanElelemista(lehma2);
        testaaLehmanElelemista(lehma2);
        testaaLehmanElelemista(lehma2);
        testaaLehmanElelemista(lehma3);
        testaaLehmanElelemista(lehma3);

        List<L> lehmat = new ArrayList<L>();
        lehmat.add(lehma1);
        lehmat.add(lehma2);
        lehmat.add(lehma3);
        lehmat.add(lehma4);

        S sailio = luoMaitosailio(maitosailioClassRef, 8374.0);

        N navetta = luoNavetta(navettaClassRef, sailio);

        R robo = luoLypsyrobotti(lypsyrobottiClassRef);

        try {
            navettaAsennaLypsyrobottiMethodRef.invokeOn(navetta, robo);
        } catch (Throwable t) {
            Assert.fail("Luokan " + navetta.getClass().getName() + " metodi "
                    + navettaAsennaLypsyrobottiMethodRef.getMethod().getName()
                    + " heitti poikkeuksen: " + t.toString());
        }

        testaaNavetanLehmienHoitamista(navetta, lehmat);
    }

    @Test
    @Points("10-14.5")
    public void testaaMaatilaa() throws Throwable {
        reflektoiMaitosailio();
        reflektoiLehma();
        reflektoiLypsyrobotti();
        reflektoiNavetta();
        reflektoiMaatila();

        L lehma1 = luoLehma(lehmaClassRef, "Lehmä1");
        L lehma2 = luoLehma(lehmaClassRef, null);
        L lehma3 = luoLehma(lehmaClassRef, "Lehmä3");
        L lehma4 = luoLehma(lehmaClassRef, null);
        L lehma5 = luoLehma(lehmaClassRef, "Lehmä5");

        List<L> lehmat = new ArrayList<L>();
        lehmat.add(lehma1);
        lehmat.add(lehma2);
        lehmat.add(lehma3);
        lehmat.add(lehma4);
        lehmat.add(lehma5);

        S sailio = luoMaitosailio(maitosailioClassRef, 121384.0);
        N navetta = luoNavetta(navettaClassRef, sailio);
        M maatila = luoMaatila(maatilaClassRef, "Öyvind", navetta);

        String v = "\nMaitosailio sailio = new Maitosailio();\n"
                + "Maatila tila = new Maatila(\"pekka\", "
                + "new Navetta( sailio ));\n";

        for (L lehma : lehmat) {
            v += "tila.lisaaLehma( new Lehma());\n";
            maatilaLisaaLehmaMethodRef.withNiceError(v).invokeOn(maatila, lehma);
        }

        v += "tila.asennaNavettaanLypsyrobotti( new Lypsyrobotti() );\n";

        R robo = luoLypsyrobotti(lypsyrobottiClassRef);

        maatilaAsennaNavettaanLypsyrobottiMethodRef.withNiceError(v).invokeOn(maatila, robo);

        testaaMaatilanLehmienHoitamista(maatila, sailio, lehmat, 1);

        testaaMaatilanLehmienHoitamista(maatila, sailio, lehmat, 5);

        testaaMaatilanLehmienHoitamista(maatila, sailio, lehmat, 300);
    }

    private void reflektoiMaitosailio() {
        String luokanNimi = "maatila.Maitosailio";

        maitosailioClassRef = (ClassRef<S>) Reflex.reflect(luokanNimi);

        saniteettitarkastus("maatila.Maitosailio", 2, "kuin tilavuuden ja maitomäärän muistavat oliomuuuttujat");

        maitosailioGetTilavuusMethodRef = maitosailioClassRef.method("getTilavuus").
                returning(double.class).takingNoParams();
        Assert.assertTrue("Luokalla " + s(luokanNimi) + " ei ole metodia public " + maitosailioGetTilavuusMethodRef.signature(),
                maitosailioGetTilavuusMethodRef.isPublic());

        maitosailioGetSaldoMethodRef = maitosailioClassRef.method("getSaldo").
                returning(double.class).takingNoParams();
        Assert.assertTrue("Luokalla " + s(luokanNimi) + " ei ole metodia public " + maitosailioGetSaldoMethodRef.signature(),
                maitosailioGetSaldoMethodRef.isPublic());

        maitosailioPaljonkoTilaaJaljellaMethodRef = maitosailioClassRef.method("paljonkoTilaaJaljella").
                returning(double.class).takingNoParams();
        Assert.assertTrue("Luokalla " + s(luokanNimi) + " ei ole metodia public " + maitosailioPaljonkoTilaaJaljellaMethodRef.signature(),
                maitosailioPaljonkoTilaaJaljellaMethodRef.isPublic());

        maitosailioLisaaSailioonMethodRef = maitosailioClassRef.method("lisaaSailioon").
                returningVoid().taking(double.class);
        Assert.assertTrue("Luokalla " + s(luokanNimi) + " ei ole metodia public " + maitosailioLisaaSailioonMethodRef.signature(),
                maitosailioLisaaSailioonMethodRef.exists());

        maitosailioOtaSailiostaMethodRef = maitosailioClassRef.method("otaSailiosta").
                returning(double.class).taking(double.class);
        Assert.assertTrue("Luokalla " + s(luokanNimi) + " ei ole metodia public " + maitosailioOtaSailiostaMethodRef.signature(),
                maitosailioOtaSailiostaMethodRef.exists());
    }

    private <T> T luoMaitosailio(ClassRef<T> luokka, Double tilavuus) throws Throwable {
        if (tilavuus == null) {
            assertTrue("Luokalla " + s(luokka.getReferencedClass().getName())
                    + " ei ole julkista parametritonta konstruktoria.",
                    luokka.constructor().takingNoParams().isPublic());

            String v = "\nvirheen aiheuttanut koodi\n"
                    + "new Maitosailio()";

            return luokka.constructor().takingNoParams().withNiceError(v).invoke();
        }

        assertTrue("Luokalla " + s(luokka.getReferencedClass().getName())
                + " ei ole konstruktoria public Maitosailio(double tilavuus)",
                luokka.constructor().taking(double.class).isPublic());

        String v = "\nvirheen aiheuttanut koodi\n"
                + "new Maitosailio(" + tilavuus + ")";

        return luokka.constructor().taking(double.class).withNiceError(v).invoke(tilavuus);

    }

    private void testaaTilavuus(S sailio, Double oletettu, String v) throws Throwable {
        Double paluuarvo = maitosailioGetTilavuusMethodRef.withNiceError(v).invokeOn(sailio);
        Assert.assertTrue(v + "\nm.getTilavuus();\n odotettu tulos " + oletettu
                + "\nPalautettu arvo oli: " + paluuarvo,
                paluuarvo >= oletettu - 0.1 && paluuarvo <= oletettu + 0.1);
    }

    private void testaaSaldo(S sailio, Double oletettu, String v) throws Throwable {
        Double paluuarvo = maitosailioGetSaldoMethodRef.withNiceError(v).invokeOn(sailio);
        Assert.assertTrue(v + "\nm.getSaldo();\n odotettu tulos " + oletettu
                + "\nPalautettu arvo oli: " + paluuarvo,
                paluuarvo >= oletettu - 0.1 && paluuarvo <= oletettu + 0.1);
    }

    private String toString(Object o, String v) throws Throwable {

        Reflex.ClassRef<Object> classRef = Reflex.reflect(o.getClass().getName());
        return classRef.method(o, "toString").returning(String.class).takingNoParams().withNiceError(v).invoke();
    }

    private void testaaPaljonkoTilaaJaljella(S sailio, String v) throws Throwable {
        Double tilavuus, saldo, paluuarvo;

        paluuarvo = maitosailioPaljonkoTilaaJaljellaMethodRef.withNiceError(v).invokeOn(sailio);

        try {
            tilavuus = maitosailioGetTilavuusMethodRef.invokeOn(sailio);
            saldo = maitosailioGetSaldoMethodRef.invokeOn(sailio);

        } catch (Throwable t) {
            Assert.fail("Luokan " + s(sailio.getClass().getName()) + " metodi "
                    + maitosailioPaljonkoTilaaJaljellaMethodRef.getMethod().getName()
                    + " heitti poikkeuksen: " + t.toString());
            return;
        }

        Double oletettu = (tilavuus - saldo);

        Assert.assertTrue(v + "\nm.paljonkoTilaaJaljella();\n odotettu tulos " + oletettu
                + "\nPalautettu arvo oli: " + paluuarvo,
                paluuarvo >= oletettu - 0.1 && paluuarvo <= oletettu + 0.1);

        String tila = toString(sailio, v + "\nm.toString();");

        assertFalse("Tee luokalle " + s(sailio.getClass().getName()) + " tehtävämäärittelyn mukainen toString()", tila.contains("@"));

        String[] tilanOsat = tila.split("/");

        Assert.assertTrue("Luokan " + s(sailio.getClass().getName())
                + " metodin toString() pitäisi palauttaa säiliön tila "
                + "muodossa: saldo/tilavuus, \n"
                + "mutta palautettu merkkijono oli: " + tila,
                tilanOsat.length == 2);

        double merkkijonoSaldo;
        double merkkijonoTilavuus;

        try {
            merkkijonoSaldo = Double.parseDouble(tilanOsat[0].trim());
            merkkijonoTilavuus = Double.parseDouble(tilanOsat[1].trim());

        } catch (Exception e) {
            Assert.fail("Luokan " + s(sailio.getClass().getName())
                    + " metodin toString() pitäisi palauttaa säiliön tila "
                    + "muodossa: saldo/tilavuus, \nmutta palautetussa merkkijonossa "
                    + "palautettu merkkijono ei sisältänyt numeroita: " + tila);
            return;
        }

        testaaOnkoKokonaisLuku(sailio.getClass(), "toString()",
                merkkijonoSaldo, tila);
        testaaOnkoKokonaisLuku(sailio.getClass(), "toString()",
                merkkijonoTilavuus, tila);

        Assert.assertEquals("Luokan " + s(sailio.getClass().getName())
                + " metodin toString() pitäisi palauttaa säiliön tila "
                + "muodossa: saldo/tilavuus, mutta palautettu saldo oli väärä: "
                + merkkijonoSaldo + " -- sen olisi pitänyt olla: " + Math.ceil(saldo),
                Math.ceil(saldo), merkkijonoSaldo, 0.1);
        Assert.assertEquals("Luokan " + s(sailio.getClass().getName())
                + " metodin toString() pitäisi palauttaa säiliön tila "
                + "muodossa: saldo/tilavuus, mutta palautettu tilavuus oli väärä: "
                + merkkijonoTilavuus + " -- sen olisi pitänyt olla: " + Math.ceil(tilavuus),
                Math.ceil(tilavuus), merkkijonoTilavuus, 0.1);
    }

    private void testaaLisaaSailioon(S sailio, Double maara, String v) throws Throwable {
        Double saldoEnnen, tilavuus, saldoJalkeen, odotettuSaldo;

        tilavuus = maitosailioGetTilavuusMethodRef.invokeOn(sailio);
        saldoEnnen = maitosailioGetSaldoMethodRef.invokeOn(sailio);
        maitosailioLisaaSailioonMethodRef.withNiceError(v).invokeOn(sailio, maara);
        saldoJalkeen = maitosailioGetSaldoMethodRef.invokeOn(sailio);


        if ((saldoEnnen + maara) >= tilavuus) {
            odotettuSaldo = tilavuus;
        } else {
            odotettuSaldo = saldoEnnen + maara;
        }

        Assert.assertTrue(v + "\nm.getSaldo();\n odotettu tulos " + odotettuSaldo
                + "\nPalautettu arvo oli: " + saldoJalkeen,
                saldoJalkeen >= odotettuSaldo - 0.1 && saldoJalkeen <= odotettuSaldo + 0.1);

        testaaPaljonkoTilaaJaljella(sailio, v);
    }

    private void testaaOtaSailiosta(S sailio, Double maara, String v) throws Throwable {
        Double saldoEnnen, tilavuus, otettu, saldoJalkeen, odotettuSaldo;

        tilavuus = maitosailioGetTilavuusMethodRef.invokeOn(sailio);
        saldoEnnen = maitosailioGetSaldoMethodRef.invokeOn(sailio);
        otettu = maitosailioOtaSailiostaMethodRef.withNiceError(v).invokeOn(sailio, maara);
        saldoJalkeen = maitosailioGetSaldoMethodRef.invokeOn(sailio);

        if ((saldoEnnen - maara) < 0) {
            odotettuSaldo = 0.0;
        } else {
            odotettuSaldo = saldoEnnen - maara;
        }

        Assert.assertTrue(v + "\nm.getSaldo();\n odotettu tulos " + odotettuSaldo
                + "\nPalautettu arvo oli: " + saldoJalkeen,
                saldoJalkeen >= odotettuSaldo - 0.1 && saldoJalkeen <= odotettuSaldo + 0.1);

        testaaPaljonkoTilaaJaljella(sailio, v);
    }

    private void reflektoiLehma() {
        String luokanNimi = "maatila.Lehma";

        saniteettitarkastus(luokanNimi, 5, "kuin nimen, utareiden tilavuuden ja utareiden maitomäärän muistavat oliomuuttujat");

        lehmaClassRef = (ClassRef<L>) Reflex.reflect(luokanNimi);

        if (!Eleleva.class
                .isAssignableFrom(lehmaClassRef.getReferencedClass())) {
            Assert.fail(
                    "Luokan " + s(luokanNimi) + " täytyy "
                    + "toteuttaa rajapinta " + s(Eleleva.class.getName()));
        }

        if (!Lypsava.class
                .isAssignableFrom(lehmaClassRef.getReferencedClass())) {
            Assert.fail(
                    "Luokan " + s(luokanNimi) + " täytyy "
                    + "toteuttaa rajapinta " + s(Lypsava.class.getName()));
        }

        lehmaGetTilavuusMethodRef = lehmaClassRef.method("getTilavuus").
                returning(double.class).takingNoParams();

        Assert.assertTrue(
                "Luokalla " + s(luokanNimi) + " ei ole metodia public " + lehmaGetTilavuusMethodRef.signature(),
                lehmaGetTilavuusMethodRef.isPublic());

        lehmaGetMaaraMethodRef = lehmaClassRef.method("getMaara").
                returning(double.class).takingNoParams();

        Assert.assertTrue(
                "Luokalla " + s(luokanNimi) + " ei ole metodia public " + lehmaGetMaaraMethodRef.signature(),
                lehmaGetMaaraMethodRef.isPublic());

        lehmaGetNimiMethodRef = lehmaClassRef.method("getNimi").
                returning(String.class).takingNoParams();

        Assert.assertTrue(
                "Luokalla " + s(luokanNimi) + " ei ole metodia public " + lehmaGetNimiMethodRef.signature(),
                lehmaGetNimiMethodRef.isPublic());

        lehmaLypsaMethodRef = lehmaClassRef.method("lypsa").
                returning(double.class).takingNoParams();

        Assert.assertTrue(
                "Luokalla " + s(luokanNimi) + " ei ole metodia public " + lehmaLypsaMethodRef.signature(),
                lehmaLypsaMethodRef.isPublic());

        lehmaEleleTuntiMethodRef = lehmaClassRef.method("eleleTunti").
                returningVoid().takingNoParams();

        Assert.assertTrue(
                "Luokalla " + s(luokanNimi) + " ei ole metodia public " + lehmaEleleTuntiMethodRef.signature(),
                lehmaEleleTuntiMethodRef.isPublic());
    }

    private L luoLehma(ClassRef<L> luokka, String nimi) throws Throwable {
        L instanssi;
        String v = "";

        assertTrue("Luo luokalle Lehma konstruktori public Lehma(String nimi)", luokka.constructor().taking(String.class).isPublic());
        if (nimi == null) {
            assertTrue("Luokalla Lehma pitää olla julkinen parametriton konstruktori", luokka.constructor().takingNoParams().isPublic());
            instanssi = luokka.constructor().takingNoParams().invoke();
            v = "Lehma l = new Lehma();";
        } else {
            instanssi = luokka.constructor().taking(String.class).withNiceError().invoke(nimi);
            v = "Lehma l = new Lehma(\"" + nimi + "\");";
        }

        String palautettuNimi = lehmaGetNimiMethodRef.withNiceError(v + "\nl.getNimi();").invokeOn(instanssi);

        Double maara = lehmaGetMaaraMethodRef.withNiceError(v + "\nl.getMaara();").invokeOn(instanssi);

        Double tilavuus = lehmaGetTilavuusMethodRef.withNiceError(v + "\nl.getTilavuus();").invokeOn(instanssi);

        if (nimi == null) {
            Assert.assertTrue(v + "\nl.getNimi();\n palautti: " + palautettuNimi,
                    (palautettuNimi != null) && palautettuNimi.trim().length() > 0);
        } else {
            Assert.assertTrue(v + "\nl.getNimi();\n palautti: " + palautettuNimi,
                    nimi.equals(palautettuNimi));
        }

        Assert.assertEquals(v + "\nl.getMaara();\n",
                0.0, maara, 0.1);

        Assert.assertTrue("Utareen tilavuuden pitäisi olla välillä 15-40 litraa\n"
                + "" + v + "\nl.getTilavuus();\npalautti: " + tilavuus, tilavuus >= 15 && tilavuus <= 40);

        String tila = toString(instanssi, v + "l.toString();");//instanssi.toString();

        assertFalse("Tee luokalle Lehma tehtävämäärittelyn mukainen toString()", tila.contains("@"));

        String[] tilanOsat = tila.split("[ /]");

        Assert.assertTrue("Luokan " + instanssi.getClass().getName()
                + " metodin toString() pitäisi palauttaa lehmän utareiden tila "
                + "muodossa: nimi määrä/tilavuus, \n"
                + "mutta palautettu merkkijono oli: " + tila,
                tilanOsat.length == 3);

        String merkkijonoNimi = tilanOsat[0].trim();

        Assert.assertTrue("Luokan " + instanssi.getClass().getName()
                + " metodin toString() pitäisi palauttaa lehmän tiedot "
                + "muodossa: nimi saldo/tilavuus, mutta palautetussa merkkijonossa "
                + "lehmän nimi ei ollut annettu nimi " + palautettuNimi + ", vaan: " + merkkijonoNimi,
                palautettuNimi.equals(merkkijonoNimi));

        return instanssi;
    }

    private void testaaLehmanElelemista(L lehma, String v) throws Throwable {
        Double maaraEnnen, tilavuus, maaraJalkeen;

        tilavuus = lehmaGetTilavuusMethodRef.invokeOn(lehma);
        maaraEnnen = lehmaGetMaaraMethodRef.invokeOn(lehma);
        lehmaEleleTuntiMethodRef.withNiceError(v + "\nlehma.eleleTunti();").invokeOn(lehma);
        maaraJalkeen = lehmaGetMaaraMethodRef.invokeOn(lehma);


        Assert.assertTrue("Luokan " + s(lehma.getClass().getName()) + " metodia "
                + lehmaEleleTuntiMethodRef.getMethod().getName()
                + " kutsuttiin ja lehmän tilavuus oli "
                + tilavuus + ", \n"
                + "mutta maidon määrä ylitti tilavuuden: " + maaraJalkeen,
                maaraJalkeen <= tilavuus);

        Assert.assertTrue("Luokan " + s(lehma.getClass().getName()) + " metodia "
                + lehmaEleleTuntiMethodRef.getMethod().getName()
                + " kutsuttiin ja säiliön tilavuus oli "
                + tilavuus + ", \nmutta maidon määrä " + maaraJalkeen
                + " ei kasvanut odotetuissa rajoissa, 0.7-2.0 litraa tunnissa, \n"
                + "kasvu oli "
                + (maaraJalkeen - maaraEnnen),
                maaraJalkeen >= tilavuus - 0.1
                || (maaraJalkeen >= maaraEnnen + 0.7
                && maaraJalkeen <= maaraEnnen + 2.0));

        String tila = lehma.toString();

        String[] tilanOsat = tila.split("[ /]");

        Assert.assertTrue("Luokan " + lehma.getClass().getName()
                + " metodin toString() pitäisi palauttaa säiliön tila "
                + "muodossa: nimi määrä/tilavuus, mutta palautettu merkkijono oli: " + tila,
                tilanOsat.length == 3);

        double merkkijonoMaara;
        double merkkijonoTilavuus;

        try {
            merkkijonoMaara = Double.parseDouble(tilanOsat[1].trim());
            merkkijonoTilavuus = Double.parseDouble(tilanOsat[2].trim());

        } catch (Exception e) {
            Assert.fail("Luokan " + lehma.getClass().getName()
                    + " metodin toString() pitäisi palauttaa lehmän tiedot "
                    + "muodossa: nimi saldo/tilavuus, mutta "
                    + "palautettu merkkijono ei sisältänyt numeroita: " + tila);
            return;
        }

        testaaOnkoKokonaisLuku(lehma.getClass(), "toString()",
                merkkijonoMaara, tila);
        testaaOnkoKokonaisLuku(lehma.getClass(), "toString()",
                merkkijonoTilavuus, tila);

        Assert.assertEquals("Luokan " + lehma.getClass().getName()
                + " metodin toString() pitäisi palauttaa lehmän tiedot "
                + "muodossa: saldo/tilavuus, mutta palautettu maidon määrä oli väärä: "
                + merkkijonoMaara + " -- sen olisi pitänyt olla: " + Math.ceil(maaraJalkeen),
                Math.ceil(maaraJalkeen), merkkijonoMaara, 0.1);
        Assert.assertEquals("Luokan " + lehma.getClass().getName()
                + " metodin toString() pitäisi palauttaa lehmän tiedot "
                + "muodossa: saldo/tilavuus, mutta palautettu tilavuus oli väärä: "
                + merkkijonoTilavuus + " -- sen olisi pitänyt olla: " + Math.ceil(tilavuus),
                Math.ceil(tilavuus), merkkijonoTilavuus, 0.1);
    }

    private void testaaLehmanElelemista(L lehma) {
        Double maaraEnnen, tilavuus, maaraJalkeen;
        try {
            tilavuus = lehmaGetTilavuusMethodRef.invokeOn(lehma);
            maaraEnnen = lehmaGetMaaraMethodRef.invokeOn(lehma);
            lehmaEleleTuntiMethodRef.invokeOn(lehma);
            maaraJalkeen = lehmaGetMaaraMethodRef.invokeOn(lehma);
        } catch (Throwable t) {
            Assert.fail("Luokan " + lehma.getClass().getName() + " metodi "
                    + lehmaEleleTuntiMethodRef.getMethod().getName() + " heitti poikkeuksen: " + t.toString());
            return;
        }

        Assert.assertTrue("Luokan " + lehma.getClass().getName() + " metodia "
                + lehmaEleleTuntiMethodRef.getMethod().getName()
                + " kutsuttiin ja lehmän tilavuus oli "
                + tilavuus + ", mutta maidon määrä ylitti tilavuuden: " + maaraJalkeen,
                maaraJalkeen <= tilavuus);

        Assert.assertTrue("Luokan " + lehma.getClass().getName() + " metodia "
                + lehmaEleleTuntiMethodRef.getMethod().getName()
                + " kutsuttiin ja säiliön tilavuus oli "
                + tilavuus + ", mutta maidon määrä " + maaraJalkeen
                + " ei kasvanut odotetuissa rajoissa, 0.7-2.0 litraa tunnissa, kasvu oli "
                + (maaraJalkeen - maaraEnnen),
                maaraJalkeen >= tilavuus - 0.1
                || (maaraJalkeen >= maaraEnnen + 0.7
                && maaraJalkeen <= maaraEnnen + 2.0));

        String tila = lehma.toString();

        String[] tilanOsat = tila.split("[ /]");

        Assert.assertTrue("Luokan " + lehma.getClass().getName()
                + " metodin toString() pitäisi palauttaa säiliön tila "
                + "muodossa: nimi määrä/tilavuus, mutta palautettu merkkijono oli: " + tila,
                tilanOsat.length == 3);

        double merkkijonoMaara;
        double merkkijonoTilavuus;

        try {
            merkkijonoMaara = Double.parseDouble(tilanOsat[1].trim());
            merkkijonoTilavuus = Double.parseDouble(tilanOsat[2].trim());

        } catch (Exception e) {
            Assert.fail("Luokan " + lehma.getClass().getName()
                    + " metodin toString() pitäisi palauttaa lehmän tiedot "
                    + "muodossa: nimi saldo/tilavuus, mutta "
                    + "palautettu merkkijono ei sisältänyt numeroita: " + tila);
            return;
        }

        testaaOnkoKokonaisLuku(lehma.getClass(), "toString()",
                merkkijonoMaara, tila);
        testaaOnkoKokonaisLuku(lehma.getClass(), "toString()",
                merkkijonoTilavuus, tila);

        Assert.assertEquals("Luokan " + lehma.getClass().getName()
                + " metodin toString() pitäisi palauttaa lehmän tiedot "
                + "muodossa: saldo/tilavuus, mutta palautettu maidon määrä oli väärä: "
                + merkkijonoMaara + " -- sen olisi pitänyt olla: " + Math.ceil(maaraJalkeen),
                Math.ceil(maaraJalkeen), merkkijonoMaara, 0.1);
        Assert.assertEquals("Luokan " + lehma.getClass().getName()
                + " metodin toString() pitäisi palauttaa lehmän tiedot "
                + "muodossa: saldo/tilavuus, mutta palautettu tilavuus oli väärä: "
                + merkkijonoTilavuus + " -- sen olisi pitänyt olla: " + Math.ceil(tilavuus),
                Math.ceil(tilavuus), merkkijonoTilavuus, 0.1);
    }

    private void testaaLehmanLypsamista(L lehma, String v) throws Throwable {
        Double maaraEnnen, lypsettyMaara, maaraJalkeen;

        maaraEnnen = lehmaGetMaaraMethodRef.invokeOn(lehma);
        lypsettyMaara = lehmaLypsaMethodRef.withNiceError(v + "\nlehma.lypsa()").invokeOn(lehma);
        maaraJalkeen = lehmaGetMaaraMethodRef.invokeOn(lehma);


        Assert.assertTrue("Luokan " + s(lehma.getClass().getName()) + " metodia "
                + lehmaLypsaMethodRef.getMethod().getName()
                + " kutsuttiin, mutta maitoa jäi jäljelle: " + maaraJalkeen,
                maaraJalkeen == 0);

        Assert.assertTrue("Luokan " + s(lehma.getClass().getName()) + " metodia "
                + lehmaLypsaMethodRef.getMethod().getName()
                + " kutsuttiin, mutta lypsetyn maidon määrä " + maaraJalkeen
                + " ei ollut sama kuin maidon määrä lehmässä: " + maaraEnnen,
                (lypsettyMaara >= maaraEnnen - 0.1
                && lypsettyMaara <= maaraEnnen + 0.1));
    }

    private void reflektoiLypsyrobotti() {
        String luokanNimi = "maatila.Lypsyrobotti";

        lypsyrobottiClassRef = (ClassRef<R>) Reflex.reflect(luokanNimi);

        saniteettitarkastus(luokanNimi, 1, "kuin maitosäiliön muistavan oliomuuttujan");

        lypsyrobottiLypsaMethodRef = lypsyrobottiClassRef.method("lypsa").
                returningVoid().taking(Lypsava.class);
        Assert.assertTrue("Luokalla " + s(luokanNimi) + " ei ole metodia public " + lypsyrobottiLypsaMethodRef.signature(),
                lypsyrobottiLypsaMethodRef.isPublic());

        lypsyrobottiGetMaitosailioMethodRef = lypsyrobottiClassRef.method("getMaitosailio").
                returning(maitosailioClassRef.getReferencedClass()).takingNoParams();
        Assert.assertTrue("Luokalla " + s(luokanNimi) + " ei ole metodia public " + lypsyrobottiGetMaitosailioMethodRef.signature(),
                lypsyrobottiGetMaitosailioMethodRef.isPublic());

        lypsyrobottiSetMaitosailioMethodRef = lypsyrobottiClassRef.method("setMaitosailio").
                returningVoid().taking(maitosailioClassRef.getReferencedClass());
        Assert.assertTrue("Luokalla " + s(luokanNimi) + " ei ole metodia public " + lypsyrobottiSetMaitosailioMethodRef.signature(),
                lypsyrobottiSetMaitosailioMethodRef.isPublic());
    }

    private R luoLypsyrobotti(ClassRef<R> luokka) throws Throwable {
        assertTrue("Luokalla Lypsyrobotti pitää olla parametriton konstruktori", luokka.constructor().takingNoParams().isPublic());

        R instanssi = luokka.constructor().takingNoParams().invoke();

        String v = "Lypsyrobotti r = new Lypsyrobotti();\n"
                + "r.getMailisailio();";

        S sailio = lypsyrobottiGetMaitosailioMethodRef.withNiceError(v).invokeOn(instanssi);


        Assert.assertTrue("Luokan " + instanssi.getClass().getName()
                + " metodi " + lypsyrobottiGetMaitosailioMethodRef.getMethod().getName()
                + " täytyy palauttaa null-viite, kun säiliötä ei ole asetettu: ",
                sailio == null);

        return instanssi;
    }

    private void testaaLypsyrobotinLypsamistaIlmanSailota(R robo, L lehma) throws Throwable {
        String v = "Lypsyrobotti r = new Lyspyrobotti();\n"
                + "Lehma lehma = new Lehma();\n"
                + "r.lypsa(lehma);";

        try {
            lypsyrobottiLypsaMethodRef.withNiceError(v).invokeOn(robo, (Lypsava) lehma);

            Assert.fail("Luokan " + s(robo.getClass().getName())
                    + " metodi " + lypsyrobottiLypsaMethodRef.getMethod().getName()
                    + " ei heittänyt poikkeusta. Tarkista, että metodi heittää IllegalStateException-poikkeuksen, "
                    + "jos maitosäiliötä ei ole asetettu.");

        } catch (Throwable t) {

            if (!t.toString().contains("IllegalState")) {
                Assert.fail("Luokan " + s(robo.getClass().getName())
                        + " metodi " + lypsyrobottiLypsaMethodRef.getMethod().getName()
                        + " ei heittänyt poikkeusta. Tarkista, että metodi heittää IllegalStateException-poikkeuksen, "
                        + "jos maitosäiliötä ei ole asetettu.\n"
                        + "Eli esimerkiksi jos suoritetaan koodi\n"
                        + "Lehma lehma = new Lehma(\"Arto\");\n"
                        + "Lypsyrobotti r = new Lypsyrobotti();\n"
                        + "r.lypsa(lehma);");
            }
        }
    }

    private void testaaLypsyrobotinLypsamista(R robo, L lehma) throws Throwable {
        String v = "Lypsyrobotti r = new Lypsyrobotti();\n"
                + "Maitosailio m = new Maitosailio(100.0);\n"
                + "r.setMaitosailio(m);";

        S sailio = lypsyrobottiGetMaitosailioMethodRef.withNiceError(v).invokeOn(robo);

        assertFalse(v + "\nr.getMaitosailio(); \npalautti null", sailio == null);

        Double sailionTilavuus = null, saldoEnnen = null,
                jaljellaEnnen = null, lehmassaEnnen = null;

        sailionTilavuus = maitosailioGetTilavuusMethodRef.withNiceError().invokeOn(sailio);
        saldoEnnen = maitosailioGetSaldoMethodRef.withNiceError().invokeOn(sailio);
        jaljellaEnnen = maitosailioPaljonkoTilaaJaljellaMethodRef.withNiceError().invokeOn(sailio);
        lehmassaEnnen = lehmaGetMaaraMethodRef.withNiceError().invokeOn(lehma);

        lypsyrobottiLypsaMethodRef.withNiceError(v).invokeOn(robo, (Lypsava) lehma);

        Double lehmassaJalkeen = lehmaGetMaaraMethodRef.withNiceError().invokeOn(lehma);

        Double saldoJalkeen = maitosailioGetSaldoMethodRef.withNiceError().invokeOn(sailio);

        v = "Lypsyrobotti r = new Lypsyrobotti();\n"
                + "r.setMaitosailio( new Maitosailio(100.0) );\n";
        v += "Lehma lehma = new Lehma();\n"
                + "lehma.eleleTunti();\n"
                + "r.lypsa(lehma)\n"
                + "lehma.getSaldo();\n";

        Assert.assertEquals("Lypsyrobotin lypsämisen jälkeen lehmässä ei pitäisi olla enää maitoa! Testaa koodi\n"
                + v,
                0.0, lehmassaJalkeen, 0.01);

        v = "Lypsyrobotti r = new Lypsyrobotti();\n"
                + "Maitosailio sailio = new Maitosailio(100.0);\n"
                + "r.setMaitosailio( sailio );\n";
        v += "Lehma lehma = new Lehma();\n"
                + "lehma.eleleTunti();\n"
                + "r.lypsa(lehma)\n"
                + "sailio.getSaldo();\n";

        Assert.assertTrue("Kun lehmässä ennen lypsämistä oli " + lehmassaEnnen + " ja säiliössä " + saldoEnnen + " "
                + ", lypsyrobotin lypsämisen jälkeen kaiken maidon pitäisi olla maitosäiliössä\n"
                + "Nyt säiliössä on vaan " + saldoJalkeen
                + "\nTarkasta koodi:\n" + v,
                saldoJalkeen >= sailionTilavuus - 0.1
                || (saldoJalkeen >= saldoEnnen + lehmassaEnnen - 0.1
                && saldoJalkeen <= saldoEnnen + lehmassaEnnen + 0.1));

    }

    private void reflektoiNavetta() {
        String luokanNimi = "maatila.Navetta";

        navettaClassRef = (ClassRef<N>) Reflex.reflect(luokanNimi);

        saniteettitarkastus(luokanNimi, 2, "kuin maitosäiliön ja lypsyrobotin muistavat oliomuuttujat");

        navettaGetMaitosailioMethodRef = navettaClassRef.method("getMaitosailio").
                returning(maitosailioClassRef.getReferencedClass()).takingNoParams();
        Assert.assertTrue("Luokalla " + s(luokanNimi) + " ei ole metodia public " + navettaGetMaitosailioMethodRef.signature(),
                navettaGetMaitosailioMethodRef.isPublic());

        navettaHoidaLehmaMethodRef = navettaClassRef.method("hoida").
                returningVoid().taking(lehmaClassRef.getReferencedClass());
        Assert.assertTrue("Luokalla " + s(luokanNimi) + " ei ole metodia public " + navettaHoidaLehmaMethodRef.signature(),
                navettaHoidaLehmaMethodRef.isPublic());

        navettaHoidaLehmatMethodRef = navettaClassRef.method("hoida").
                returningVoid().taking(List.class);
        Assert.assertTrue("Luokalla " + s(luokanNimi) + " ei ole metodia public void hoida(List<Lehma> lehmat)",
                navettaHoidaLehmatMethodRef.isPublic());

        navettaAsennaLypsyrobottiMethodRef = navettaClassRef.method("asennaLypsyrobotti").
                returningVoid().taking(lypsyrobottiClassRef.getReferencedClass());
        Assert.assertTrue("Luokalla " + s(luokanNimi) + " ei ole metodia public " + navettaAsennaLypsyrobottiMethodRef.signature(),
                navettaAsennaLypsyrobottiMethodRef.isPublic());
    }

    private N luoNavetta(ClassRef<N> luokka, S sailio) throws Throwable {

        assertTrue("Tee luokalle Navetta konstruktori public Navetta(Maitosailio sailio)", luokka.constructor().taking(maitosailioClassRef.getReferencedClass()).isPublic());

        N instanssi = luokka.constructor().taking(maitosailioClassRef.getReferencedClass()).invoke(sailio);

        String v = "Maitosailio m = new Maitosailio();\n"
                + "Navetta n = new Navetta(m);\n"
                + "n.getMaitosailio();\n";

        S palautettuSailio = navettaGetMaitosailioMethodRef.withNiceError(v).invokeOn(instanssi);

        Assert.assertTrue("Luokan " + s(instanssi.getClass().getName())
                + " metodi " + navettaGetMaitosailioMethodRef.getMethod().getName()
                + " täytyy palauttaa konstruktorissa asetettu maitosäiliö\ntestaa koodi\n"
                + v,
                sailio.equals(palautettuSailio));

        L lehma = luoLehma(lehmaClassRef, null);

        boolean bad = false;
        try {
            navettaHoidaLehmaMethodRef.invokeOn(instanssi, lehma);
            bad = true;
        } catch (Throwable t) {
        }

        assertFalse("Luokan " + s(instanssi.getClass().getName())
                + " metodin hoida tulisi heittää IllegalStateException, "
                + "jos lypsyrobottia ei ole asennettu. \n"
                + "tarkasta koodi:\n"
                + "Maatila tila = new Maatila( new Maitosailio());\n"
                + "Lehma lehma = new Lehma(\"Pekka\");\n"
                + "tila.hoida(lehma);\n", bad);

        return instanssi;
    }

    private double laskeMaidonMaaraLehmissa(Collection<L> lehmat) {
        double yhteensa = 0;
        for (L lehma : lehmat) {
            try {
                yhteensa += lehmaGetMaaraMethodRef.invokeOn(lehma);
            } catch (Throwable t) {
                Assert.fail("Luokan " + lehma.getClass().getName()
                        + " metodi " + lehmaGetMaaraMethodRef.getMethod().getName()
                        + " heitti poikkeuksen: " + t.toString());
            }
        }
        return yhteensa;
    }

    private double annaMaitosailionSaldo(S sailio) {
        try {
            return maitosailioGetSaldoMethodRef.invokeOn(sailio);
        } catch (Throwable t) {
            Assert.fail("Luokan " + sailio.getClass().getName()
                    + " metodi " + maitosailioGetSaldoMethodRef.getMethod().getName()
                    + " heitti poikkeuksen: " + t.toString());
            return -1;
        }
    }

    private double annaMaitosailionTilavuus(S sailio) {
        try {
            return maitosailioGetTilavuusMethodRef.invokeOn(sailio);
        } catch (Throwable t) {
            Assert.fail("Luokan " + sailio.getClass().getName()
                    + " metodi " + maitosailioGetTilavuusMethodRef.getMethod().getName()
                    + " heitti poikkeuksen: " + t.toString());
            return -1;
        }
    }

    private void testaaNavetanLehmienHoitamista(N navetta, List<L> lehmat) throws Throwable {
        double maaraLehmissaYhteensa = laskeMaidonMaaraLehmissa(lehmat);
        double maaraEnsimmaisessaLehmassa = laskeMaidonMaaraLehmissa(
                Collections.singletonList(lehmat.get(0)));

        String v = "\n"
                + "Lehma lehma1 = new Lehma(\"Lehma 1\");\n"
                + "lehma1.eleleTunti();"
                + "Maitosailio m = new Maitosailio();\n"
                + "Navetta nav = new Navetta(m);\n"
                + "nav.asennaLypsyrobotti( new Lypsyrobotti() );\n"
                + "nav.hoida(lehma1);";

        navettaHoidaLehmaMethodRef.withNiceError(v).invokeOn(navetta, lehmat.get(0));

        S sailio = navettaGetMaitosailioMethodRef.withNiceError(v + "\nnav.getMaitosailio()").invokeOn(navetta);

        double sailionSaldo = annaMaitosailionSaldo(sailio);

        Assert.assertEquals("Suoritettiin koodi" + v + "\nEli navetalla hoidettiin yksi lehmä, jossa oli "
                + "maitoa " + maaraEnsimmaisessaLehmassa + " litraa, mutta säiliöön meni "
                + "maitoa eri määrä: " + sailionSaldo,
                maaraEnsimmaisessaLehmassa, sailionSaldo, 0.1);

        /*
         *
         */

        v = "\n"
                + "Lehma lehma1 = new Lehma(\"Lehma 1\");\n"
                + "lehma1.eleleTunti();"
                + "Maitosailio m = new Maitosailio();\n"
                + "Navetta nav = new Navetta(m);\n"
                + "nav.asennaLypsyrobotti( new Lypsyrobotti() );\n"
                + "nav.hoida(lehma1);\n"
                + "List<Lehma> lehmat = new ArrayList<Lehma>();\n"
                + "lehmat.add( new Lehma() );\n"
                + "lehmat.add( new Lehma() );\n"
                + "// listalla olevat lehmät elelevät\n"
                + "nav.hoida( lehmat );\n";

        navettaHoidaLehmatMethodRef.withNiceError(v).invokeOn(navetta, lehmat);

        sailionSaldo = annaMaitosailionSaldo(sailio);

        Assert.assertEquals("Suoritettiin koodi\n"+v
                + "Eli navetalla hoidettiin useampi lehmä, joissa oli yhteensä "
                + "maitoa " + maaraLehmissaYhteensa + " litraa, mutta säiliöön meni "
                + "maitoa eri määrä: " + sailionSaldo,
                maaraLehmissaYhteensa, sailionSaldo, 0.1);

        String tila = navetta.toString();

        assertFalse("Tee luokalle Navetta tehtävämääritelmän mukainen metodi toString()",
                tila.contains("@"));

        String[] tilanOsat = tila.split("/");

        Assert.assertTrue("Luokan " + navetta.getClass().getName()
                + " metodin toString() pitäisi palauttaa navetan sisältämän säiliön tila "
                + "muodossa: saldo/tilavuus, \n"
                + "mutta palautettu merkkijono oli: " + tila,
                tilanOsat.length == 2);

        double sailionTilavuus = annaMaitosailionTilavuus(sailio);

        double merkkijonoSaldo;
        double merkkijonoTilavuus;

        try {
            String eka = tilanOsat[0].trim();
            int n = 0;
            while( !Character.isDigit(eka.charAt(n))){
                n++;
            }
            eka = eka.substring(n);

            merkkijonoSaldo = Double.parseDouble(eka);
            merkkijonoTilavuus = Double.parseDouble(tilanOsat[1].trim());

        } catch (Exception e) {
            Assert.fail("Luokan " + navetta.getClass().getName()
                    + " metodin toString() pitäisi palauttaa säiliön tila "
                    + "muodossa: saldo/tilavuus, mutta palautetussa merkkijonossa "
                    + "palautettu merkkijono ei sisältänyt numeroita: " + tila);
            return;
        }

        testaaOnkoKokonaisLuku(navetta.getClass(), "toString()",
                merkkijonoSaldo, tila);
        testaaOnkoKokonaisLuku(navetta.getClass(), "toString()",
                merkkijonoTilavuus, tila);

        Assert.assertEquals("Luokan " + navetta.getClass().getName()
                + " metodin toString() pitäisi palauttaa navetan sisältämän säiliön tila "
                + "muodossa: saldo/tilavuus, mutta palautettu saldo oli väärä: "
                + merkkijonoSaldo + " -- sen olisi pitänyt olla: " + Math.ceil(sailionSaldo),
                Math.ceil(sailionSaldo), merkkijonoSaldo, 0.1);
        Assert.assertEquals("Luokan " + navetta.getClass().getName()
                + " metodin toString() pitäisi palauttaa navetan sisältämän säiliön tila "
                + "muodossa: saldo/tilavuus, mutta palautettu tilavuus oli väärä: "
                + merkkijonoTilavuus + " -- sen olisi pitänyt olla: " + Math.ceil(sailionTilavuus),
                Math.ceil(sailionTilavuus), merkkijonoTilavuus, 0.1);
    }

    private void reflektoiMaatila() {
        String luokanNimi = "maatila.Maatila";

        maatilaClassRef = (ClassRef<M>) Reflex.reflect(luokanNimi);

        saniteettitarkastus(luokanNimi, 3, "kuin omistajan, navetan ja lehmät muistavat oliomuuttujat");

        maatilaGetOmistajaMethodRef = maatilaClassRef.method("getOmistaja").
                returning(String.class).takingNoParams();
        Assert.assertTrue("Luokalla " + s(luokanNimi) + " ei ole metodia public " + maatilaGetOmistajaMethodRef.signature(),
                maatilaGetOmistajaMethodRef.isPublic());

        maatilaEleleTuntiMethodRef = maatilaClassRef.method("eleleTunti").
                returningVoid().takingNoParams();
        Assert.assertTrue("Luokalla " + s(luokanNimi) + " ei ole metodia public " + maatilaEleleTuntiMethodRef.signature(),
                maatilaEleleTuntiMethodRef.isPublic());

        maatilaLisaaLehmaMethodRef = maatilaClassRef.method("lisaaLehma").
                returningVoid().taking(lehmaClassRef.getReferencedClass());
        Assert.assertTrue("Luokalla " + s(luokanNimi) + " ei ole metodia public " + maatilaLisaaLehmaMethodRef.signature(),
                maatilaLisaaLehmaMethodRef.isPublic());

        maatilaHoidaLehmatMethodRef = maatilaClassRef.method("hoidaLehmat").
                returningVoid().takingNoParams();
        Assert.assertTrue("Luokalla " + s(luokanNimi) + " ei ole metodia public " + maatilaHoidaLehmatMethodRef.signature(),
                maatilaHoidaLehmatMethodRef.isPublic());

        maatilaAsennaNavettaanLypsyrobottiMethodRef = maatilaClassRef.method("asennaNavettaanLypsyrobotti").
                returningVoid().taking(lypsyrobottiClassRef.getReferencedClass());
        Assert.assertTrue("Luokalla " + s(luokanNimi) + " ei ole metodia public " + maatilaAsennaNavettaanLypsyrobottiMethodRef.signature(),
                maatilaAsennaNavettaanLypsyrobottiMethodRef.isPublic());
    }

    private M luoMaatila(ClassRef<M> luokka, String omistaja, N navetta) throws Throwable {

        String v = "Maatila tila = new Maatila(\"" + omistaja + "\", new Navetta( new Maitosailio() ));\n";

        assertTrue("Tee luokalle " + s(luokka.getReferencedClass().getName())
                + " konstruktori public Maatila(String omistaja, Navetta navetta)", luokka.constructor().taking(String.class,
                navettaClassRef.getReferencedClass()).isPublic());

        M instanssi = luokka.constructor().taking(String.class,
                navettaClassRef.getReferencedClass()).withNiceError(v).invoke(omistaja, navetta);

        v += "tila.getOmistaja();\n";

        String palautettuOmistaja = maatilaGetOmistajaMethodRef.withNiceError(v).invokeOn(instanssi);

        Assert.assertTrue(v + "palautti " + palautettuOmistaja,
                omistaja.equals(palautettuOmistaja));

        return instanssi;
    }

    private void testaaMaatilanLehmienHoitamista(M maatila, S sailio,
            Collection<L> lehmat, int eleltavatTunnit) throws Throwable {

        String v = "Maitosailio sailio = new Maitosailio();\n"
                + "Maatila tila = new Maatila(\"pekka\", new Navetta( sailio ));\n"
                + "tila.asennaNavettaanLypsyrobotti( new Lypsyrobotti() );\n";
        int x = 1;
        for (L l : lehmat) {
            v += "tila.lisaaLehma( new Lehma(\"Lehmä " + x + "\") )\n";
            x++;
        }

        double sailionSaldoAlussa = annaMaitosailionSaldo(sailio);

        for (int i = 0; i < eleltavatTunnit; i++) {
            v += "tila.eleleTunti()\n";
            maatilaEleleTuntiMethodRef.withNiceError(v).invokeOn(maatila);
        }

        double maaraLehmissaYhteensa = 0;
        for (L lehma : lehmat) {
            double maara = laskeMaidonMaaraLehmissa(
                    Collections.singletonList(lehma));
            if (maara < 0.6) {
                Assert.fail("Koodin " + v + "suorituksen jälkeen"
                        + " kaikissa lehmissä pitäisi olla maitoa vähintään 0.7 litraa, "
                        + "mutta näin ei ole lehmässä: " + lehma);
            }

            maaraLehmissaYhteensa += maara;
        }

        v += "tila.hoidaLehmat();\n";

        maatilaHoidaLehmatMethodRef.withNiceError(v).invokeOn(maatila);

        double sailionSaldo = annaMaitosailionSaldo(sailio);

        v += "sailio.getSaldo()";

        String a = "hoidettiin lehmiä, jossa oli "
                + "maitoa " + maaraLehmissaYhteensa + " litraa, mutta säiliöön meni "
                + "maitoa eri määrä. Tarkista koodi\n";

        Assert.assertEquals(a + "\n" + v + "\n",
                maaraLehmissaYhteensa, sailionSaldo - sailionSaldoAlussa, 0.1);

    }

    /*
     *
     */
    private void saniteettitarkastus(String klassName, int n, String m) throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("et tarvitse \"stattisia muuttujia\", poista luokalta " + s(klassName) + " muuttuja " + kentta(field.toString(), s(klassName)), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("luokan kaikkien oliomuuttujien näkyvyyden tulee olla private, muuta luokalta " + s(klassName) + " löytyi: " + kentta(field.toString(), klassName), field.toString().contains("private"));
        }

        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("Et tarvitse " + s(klassName) + "-luokalle " + m + ", poista ylimääräiset", var <= n);
        }
    }

    private String kentta(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }

    private String s(String klassName) {
        return klassName.substring(klassName.lastIndexOf(".") + 1);
    }
}
