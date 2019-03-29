package sovellus;

public interface Sensori {

    boolean onPaalla();  // palauttaa true jos sensori on päällä

    void paalle();       // käynnistä sensorin

    void poisPaalta();   // sulkee sensorin

    int mittaa();        // palauttaa sensorin lukeman jos sensori on päällä
                         // jos sensori ei päällä heittää poikkeuksen IllegalStateException
}
