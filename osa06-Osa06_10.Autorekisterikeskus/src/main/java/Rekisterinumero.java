
import java.util.Objects;

public class Rekisterinumero {
    // älä muuta luokan jo valmiina olevia osia

    // oliomuuttujille on lisätty määre final, jolloin niiden arvoa ei asetuksen
    // jälkeen voi enää muuttaa
    private final String rekNro;
    private final String maa;

    public Rekisterinumero(String maa, String rekNro) {
        this.rekNro = rekNro;
        this.maa = maa;
    }

    @Override
    public String toString() {
        return maa + " " + rekNro;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.rekNro);
        hash = 37 * hash + Objects.hashCode(this.maa);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Rekisterinumero other = (Rekisterinumero) obj;
        if (!Objects.equals(this.rekNro, other.rekNro)) {
            return false;
        }
        if (!Objects.equals(this.maa, other.maa)) {
            return false;
        }
        return true;
    }
    
    

}
