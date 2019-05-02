
public class Asunto {

    private int huoneita;
    private int nelioita;
    private int neliohinta;

    public Asunto(int huoneita, int nelioita, int neliohinta) {
        this.huoneita = huoneita;
        this.nelioita = nelioita;
        this.neliohinta = neliohinta;
    }
    
    public boolean suurempi(Asunto verrattava) {
        if (this.nelioita > verrattava.nelioita) {
            return true;
        } else return false; 
    }
    
    public int hinta() {
        return this.neliohinta * this.nelioita;
    }
    
    public int hintaero(Asunto verrattava) {
        int hintaero = (this.hinta() - verrattava.hinta());
        if (hintaero < 0) {
            hintaero = hintaero * (-1);
        }
        return hintaero;
        
    }
    
    public boolean kalliimpi(Asunto verrattava) {
        if (this.hinta() > verrattava.hinta()) {
            return true;
        } else return false;
    }

}
