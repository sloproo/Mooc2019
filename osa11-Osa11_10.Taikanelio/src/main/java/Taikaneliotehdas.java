
public class Taikaneliotehdas {

    public Taikanelio luoTaikanelio(int koko) {

        Taikanelio nelio = new Taikanelio(koko);
        // toteuta taikaneliön luominen Siamese method -algoritmilla tänne
        int x = nelio.getLeveys() / 2;
        int y = 0;
        int asetettava = 1;
        
        while (nelio.onkoNollia()) {
            
            //Asettelu ja koordinaatin uusi paikka
            nelio.asetaArvo(x, y, asetettava);
            if (x + 1 >= koko) x = x + 1 - koko; else x = x + 1;
            if (y - 1 < 0) y = y - 1 + koko; else y= y - 1;
            if (nelio.annaArvo(x, y) != 0) {
                x -= 1;
                y += 2;
                if (x < 0) x += koko;
                if (y >= koko) y -= koko;
            }
            asetettava++;
        }  
        return nelio;
    }
    

}
