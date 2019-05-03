package hiekkaranta;

import java.util.Random;
import java.util.ArrayList;


public class Simulaatio {
    private Tyyppi[][] taulukko;
    private int leveys;
    private int korkeus;

    public Simulaatio(int leveys, int korkeus) {
        this.taulukko = new Tyyppi[leveys][korkeus];
        this.leveys = leveys;
        this.korkeus = korkeus;
        this.alusta();
    }

    public void alusta() {
        for (int x = 0; x < this.leveys; x++) {
            for (int y = 0; y < this.korkeus; y++) {
                this.lisaa(x, y, Tyyppi.TYHJA);
            }
        }
    }

    
    public void lisaa(int x, int y, Tyyppi tyyppi) {
        this.taulukko[x][y] = tyyppi;
    }
    
    public void lisaaTaulukkoon(int x, int y, Tyyppi tyyppi, Tyyppi[][] taulukko) {
        taulukko[x][y] = tyyppi;
    }

    public Tyyppi sisalto(int x, int y) {
        if (onkoSisalla(x, y)) return this.taulukko[x][y];
        return Tyyppi.METALLI;
    }
    
    public Tyyppi sisaltoTaulukosta(int x, int y, Tyyppi[][] taulukko) {
        if (onkoSisalla(x, y)) return taulukko[x][y];
        return Tyyppi.METALLI;
    }

    public void paivita() {
        Tyyppi[][] uusiTaulukko = this.taulukko;

        // sitten tapahtuu

        for (int x = 0; x < this.leveys; x++) {
            for (int y = this.korkeus - 1; y >= 0; y--) {
                if (sisalto(x, y) == Tyyppi.HIEKKA) {
                    pudotaHiekkaa(x, y, this.taulukko, uusiTaulukko);
                } else if (sisalto(x, y) == Tyyppi.VESI) {
                    pudotaVetta(x, y, this.taulukko, uusiTaulukko);
                }
            }
        }
        
        this.taulukko = uusiTaulukko;
    }
    
    
    private boolean onkoSisalla(int x, int y) {
        return (x >= 0 && x < this.leveys && y >= 0 && y < this.korkeus);
    }

    

    private void pudotaHiekkaa (int x, int y, 
            Tyyppi[][] vanhaTaulukko, Tyyppi[][] uusiTaulukko) {
        
        ArrayList<Koordinaatti> vapaat = this.allaTyhjia(x, y, uusiTaulukko);
        ArrayList<Koordinaatti> vesiAlla = this.allaVetta(x, y, uusiTaulukko);
        if (!vapaat.isEmpty()) {
            Koordinaatti pudotusKoordinaatti = vapaat.get(new Random().nextInt(vapaat.size()));
            lisaaTaulukkoon(pudotusKoordinaatti.getX(), pudotusKoordinaatti.getY(), 
                Tyyppi.HIEKKA, uusiTaulukko);
            lisaaTaulukkoon(x, y, Tyyppi.TYHJA, uusiTaulukko);
        } else if(!vesiAlla.isEmpty()) {
            Koordinaatti pudotusKoordinaatti = vesiAlla.get(new Random().nextInt(vesiAlla.size()));
            lisaaTaulukkoon(pudotusKoordinaatti.getX(), pudotusKoordinaatti.getY(), 
                Tyyppi.HIEKKA, uusiTaulukko);
            lisaaTaulukkoon(x, y, Tyyppi.VESI, uusiTaulukko);
        }
    }
    
    private void pudotaVetta (int x, int y, 
            Tyyppi[][] vanhaTaulukko, Tyyppi[][] uusiTaulukko) {
        ArrayList<Koordinaatti> vapaat = this.allaTyhjia(x, y, uusiTaulukko);
        if (!vapaat.isEmpty()) {
            Koordinaatti pudotusKoordinaatti = vapaat.get(new Random().nextInt(vapaat.size()));
            lisaaTaulukkoon(pudotusKoordinaatti.getX(), pudotusKoordinaatti.getY(), 
                Tyyppi.VESI, uusiTaulukko);
            lisaaTaulukkoon(x, y, Tyyppi.TYHJA, uusiTaulukko);
        } else {
            vapaat = this.sivullaTyhjia(x, y, uusiTaulukko);
            if (!vapaat.isEmpty()) {
                Koordinaatti sivulleKoordinaatti = vapaat.get(new Random().nextInt(vapaat.size()));
                lisaaTaulukkoon(sivulleKoordinaatti.getX(), sivulleKoordinaatti.getY(), 
                    Tyyppi.VESI, uusiTaulukko);
                lisaaTaulukkoon(x, y, Tyyppi.TYHJA, uusiTaulukko);
            }
        }

    }
    
    private ArrayList<Koordinaatti> allaTyhjia(int x, int y, 
            Tyyppi[][] taulukko) {
        ArrayList<Koordinaatti> palautettava = new ArrayList<>();
        for (int xHaettava = x - 1; xHaettava <= x + 1; xHaettava++) {
            if (this.onkoSisalla(xHaettava, y+1) && 
                    this.sisaltoTaulukosta(xHaettava, y+1, taulukko) == Tyyppi.TYHJA) {
                palautettava.add(new Koordinaatti(xHaettava, y+1));
            }
        }
        return palautettava;
    }
    
        private ArrayList<Koordinaatti> allaVetta(int x, int y, 
            Tyyppi[][] taulukko) {
        ArrayList<Koordinaatti> palautettava = new ArrayList<>();
        for (int xHaettava = x - 1; xHaettava <= x + 1; xHaettava++) {
            if (this.onkoSisalla(xHaettava, y+1) && 
                    this.sisaltoTaulukosta(xHaettava, y+1, taulukko) == Tyyppi.VESI) {
                palautettava.add(new Koordinaatti(xHaettava, y+1));
            }
        }
        return palautettava;
    }
    
    private ArrayList<Koordinaatti> sivullaTyhjia(int x, int y, 
            Tyyppi[][] taulukko) {
        ArrayList<Koordinaatti> palautettava = new ArrayList<>();
        for (int xHaettava = x - 1; xHaettava <= x + 1; xHaettava += 2) {
            if (this.onkoSisalla(xHaettava, y) && 
                    this.sisaltoTaulukosta(xHaettava, y, taulukko) == Tyyppi.TYHJA) {
                palautettava.add(new Koordinaatti(xHaettava, y));
            }
        }
        return palautettava;
    }
        
}