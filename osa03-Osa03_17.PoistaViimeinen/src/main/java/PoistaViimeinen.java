
import java.util.ArrayList;

public class PoistaViimeinen {

    public static void main(String[] args) {
        // kokeile toteuttamasi metodin toimintaa täällä
    }
    
    public static void poistaViimeinen(ArrayList<String> mjonot) {
        if (mjonot.size() != 0) {
            mjonot.remove(mjonot.size()-1);
        }
        
    }
            

}
