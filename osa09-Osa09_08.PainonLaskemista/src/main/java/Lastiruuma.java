

import java.util.ArrayList;

public class Lastiruuma {

    private ArrayList<Matkalaukku> matkalaukut;
    private int maksimipaino;

    public Lastiruuma(int maksimipaino) {
        this.maksimipaino = maksimipaino;
        this.matkalaukut = new ArrayList<>();
    }

    public void lisaaMatkalaukku(Matkalaukku matkalaukku) {
        if (this.yhteispaino() + matkalaukku.yhteispaino() > maksimipaino) {
            return;
        }

        this.matkalaukut.add(matkalaukku);
    }

    public int yhteispaino() {
        return this.matkalaukut.stream()
                .mapToInt(s -> s.yhteispaino())
                .sum();
                
        /* int summa = 0;
        int indeksi = 0;
        while (indeksi < this.matkalaukut.size()) {
        summa += this.matkalaukut.get(indeksi).yhteispaino();
        indeksi++;
        }
        return summa;
        */
    }

    public void tulostaTavarat() {
        this.matkalaukut.stream()
                .forEach(matkalaukku -> matkalaukku.tulostaTavarat());
        
        /*
        int indeksi = 0;
        while (indeksi < this.matkalaukut.size()) {
        this.matkalaukut.get(indeksi).tulostaTavarat();
        indeksi++; */
        
    }

    @Override
    public String toString() {
        if (this.matkalaukut.isEmpty()) {
            return "ei matkalaukkuja (0 kg)";
        }

        if (this.matkalaukut.size() == 1) {
            return "1 matkalaukku (" + this.yhteispaino() + " kg)";
        }

        return this.matkalaukut.size() + " matkalaukkua (" + this.yhteispaino() + " kg)";
    }
}
