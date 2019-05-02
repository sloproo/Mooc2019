
public class Raha {

    private final int euroa;
    private final int senttia;

    public Raha(int euroa, int senttia) {

        if (senttia > 99) {
            euroa = euroa + senttia / 100;
            senttia = senttia % 100;
        }

        this.euroa = euroa;
        this.senttia = senttia;
    }

    public int eurot() {
        return this.euroa;
    }

    public int sentit() {
        return this.senttia;
    }

    public String toString() {
        String nolla = "";
        if (this.senttia < 10) {
            nolla = "0";
        }

        return this.euroa + "." + nolla + this.senttia + "e";
    }
    
    public Raha plus(Raha lisattava) {
        Raha uusi = new Raha(this.euroa + lisattava.euroa, this.senttia + 
                lisattava.senttia);
        return uusi;
    }
    
    public Raha miinus(Raha vahentaja) {
        if (this.vahemman(vahentaja)) {
            Raha nolla = new Raha(0, 0);
            return nolla;
        } else {
            int vahenevatEurot = this.euroa;
            int vahenevatSentit = this.senttia;
            if (vahentaja.senttia > this.senttia) {
                vahenevatEurot = vahenevatEurot - 1;
                vahenevatSentit = vahenevatSentit + 100;
            }
            Raha uusi = new Raha(vahenevatEurot - vahentaja.euroa, 
                    vahenevatSentit - vahentaja.senttia);
            return uusi;

        }
        
    }
    
    public boolean vahemman(Raha verrattava) {
        if (this.euroa < verrattava.euroa) {
            return true;
        }
        if (this.euroa == verrattava.euroa && this.senttia < verrattava.senttia) {
            return true;
        }
        return false;
    }

}
