
public class Main {
    
    public static void main(String[] args) {
        // Testaa ohjelmasi toimintaa täällä!
        Lyhenteet lyhenteet = new Lyhenteet();
lyhenteet.lisaaLyhenne("esim.", "esimerkiksi");
lyhenteet.lisaaLyhenne("jne.", "ja niin edelleen");
lyhenteet.lisaaLyhenne("yms.", "ynnä muuta sellaista");

String teksti = "esim. jne. yms. lol.";

for (String osa: teksti.split(" ")) {
    if(lyhenteet.onkoLyhennetta(osa)) {
        osa = lyhenteet.haeLyhenne(osa);
    }

    System.out.print(osa);
    System.out.print(" ");
}

System.out.println();
    }
        
        
}
