
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Lukutaitovertailu {
    
    public static void main(String[] args) {
        List<Maa> maat = new ArrayList<>();
        try {
        Files.lines(Paths.get("lukutaito.csv"))
                .map(rivi -> rivi.split(","))
                .map(palat -> new Maa(palat[3].trim(), 
                        Integer.valueOf(palat[4].trim()), Double.valueOf(palat[5].trim()),
                        palat[2].substring(0, palat[02].length() - 4).trim()))
                .forEach(maa -> maat.add((maa)));
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
        }
        
        maat.stream().sorted().forEach(maa -> System.out.println(maa));
                

    }
}
