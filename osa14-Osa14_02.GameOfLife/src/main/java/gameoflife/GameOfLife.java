package gameoflife;

import java.util.Random;

public class GameOfLife {

    private int[][] taulukko;

    public GameOfLife(int leveys, int korkeus) {
        this.taulukko = new int[leveys][korkeus];
    }

    public void alustaSatunnaisesti() {
        Random satunnaistaja = new Random();

        int x = 0;
        while (x < taulukko.length) {

            int y = 0;
            while (y < taulukko[x].length) {
                if (satunnaistaja.nextDouble() < 0.4) {
                    taulukko[x][y] = 1;
                }

                y++;
            }
            x++;
        }
    }

    public void kehity() {
        // säännöt kehittymiselle:

        // 1. jokainen elossa oleva alkio, jolla on alle 2 elossa olevaa naapuria kuolee
        // 2. jokainen elossa oleva alkio, jolla on 2 tai 3 elossa olevaa naapuria pysyy hengissä
        // 3. jokainen elossa oleva alkio, jolla on 4 tai enemmän naapureita kuolee
        // 4. jokainen kuollut alkio, jolla on tasan 3 naapuria muuttuu eläväksi
        // taulukossa arvo 1 kuvaa elävää alkiota, arvo 0 kuollutta alkiota
        int[][] kopio = new int[this.taulukko.length][this.taulukko[0].length];
        for (int x = 0; x < taulukko.length; x++) {
            for (int y = 0; y < taulukko[x].length; y++) {
                if (onkoElossa(this.taulukko, x, y)) {
                    if (elossaOleviaNaapureita(this.taulukko, x, y) < 2) {
                        kopio[x][y] = 0;
                        continue;
                    }
                    
                    if (elossaOleviaNaapureita(this.taulukko, x, y) == 2 ||
                            elossaOleviaNaapureita(this.taulukko, x, y) == 3) {
                        kopio[x][y] = 1;
                        continue;
                    }
                    
                    if (elossaOleviaNaapureita(this.taulukko, x, y) >= 4) {
                        kopio[x][y] = 0;
                        continue;
                    }
                }
                
                if (!(onkoElossa(this.taulukko, x, y)) && 
                    (elossaOleviaNaapureita(this.taulukko, x, y) == 3) ) {
                    kopio[x][y] = 1;
                }
            }
        }
        this.taulukko = kopio;
    }

    public int[][] getTaulukko() {
        return taulukko;
    }

    public void setTaulukko(int[][] taulukko) {
        this.taulukko = taulukko;
    }

    public int elossaOleviaNaapureita(int[][] taulukko, int x, int y) {
        int elossaOlevia = 0;
        for (int haettavaX = x - 1; haettavaX <= x + 1; haettavaX++) {
            for (int haettavaY = y - 1; haettavaY <= y + 1; haettavaY++) {
                if (onkoSisalla(taulukko, haettavaX, haettavaY)) {
                    if (taulukko[haettavaX][haettavaY] == 1) elossaOlevia++;
                }
            }
        }
        if (taulukko[x][y] == 1) elossaOlevia--;
        return elossaOlevia;
    }
    
    public boolean onkoElossa(int[][] taulukko, int x, int y) {
        return (taulukko[x][y] == 1);
    }
    
    private boolean onkoSisalla(int[][] taulukko, int x, int y) {
        return (x >= 0 && x < taulukko.length) && (y >= 0 && y < taulukko[x].length);
    }
}
