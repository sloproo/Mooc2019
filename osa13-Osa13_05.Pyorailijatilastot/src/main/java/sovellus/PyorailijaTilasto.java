package sovellus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class PyorailijaTilasto {

    private List<String> rivit;

    public PyorailijaTilasto(String tiedosto) {
        try {
            rivit = Files.lines(Paths.get(tiedosto)).collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException ex) {
            throw new RuntimeException("Tiedoston " + tiedosto + " lukeminen epäonnistui. Virhe: " + ex.getMessage());
        }
    }

    public List<String> paikat() {
        List<String> paikat = Arrays.stream(rivit.get(0).split(";")).collect(Collectors.toList());
        return paikat.subList(1, paikat.size());
    }

    public Map<String, Integer> pyorailijoitaKuukausittain(String paikka) {
        List<String> paikat = paikat();
        if (paikat.indexOf(paikka) < 0) {
            return new HashMap<>();
        }

        Map<String, List<Integer>> mittaArvotKuukausittain = new TreeMap<>();

        int indeksi = paikat.indexOf(paikka) + 1;
        rivit.stream().map(merkkijono -> merkkijono.split(";"))
                .filter(taulukko -> taulukko.length > 10)
                .forEach(taulukko -> {
                    String[] pvmTaulukko = taulukko[0].split(" ");
                    if (pvmTaulukko.length < 3) {
                        return;
                    }

                    String kuukausi = pvmTaulukko[3] + " / " + merkkijonoKuukaudenNumeroksi(pvmTaulukko[2]);

                    mittaArvotKuukausittain.putIfAbsent(kuukausi, new ArrayList<>());

                    int maara = 0;
                    if (!taulukko[indeksi].isEmpty()) {
                        maara = Integer.parseInt(taulukko[indeksi]);
                    }

                    mittaArvotKuukausittain.get(kuukausi).add(maara);
                });

        Map<String, Integer> pyorailijoidenLukumaarat = new TreeMap<>();
        mittaArvotKuukausittain.keySet().stream().forEach(arvo -> {
            pyorailijoidenLukumaarat.put(arvo, mittaArvotKuukausittain.get(arvo).stream().mapToInt(a -> a).sum());
        });

        return pyorailijoidenLukumaarat;
    }

    private String merkkijonoKuukaudenNumeroksi(String kuukausi) {
        List<String> kuukaudet = Arrays.asList("tammi", "helmi", "maalis", "huhti", "touko", "kesä", "heinä", "elo", "syys", "loka", "marras", "joulu");
        int numero = kuukaudet.indexOf(kuukausi) + 1;

        if (numero < 10) {
            return "0" + numero;
        }

        return "" + numero;
    }
}
