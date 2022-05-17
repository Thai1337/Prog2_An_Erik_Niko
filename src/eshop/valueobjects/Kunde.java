package eshop.valueobjects;

/**
 * Klasse zur Repräsentation einzelner Kunden.
 *
 * @author seliger
 * @author nguyen
 * @author heuschmann
 */
public class Kunde extends Nutzer {
    private Adresse adresse;
    // TODO Adresse aufteilen z.B. Klasse adresse
    public Kunde(String name, Adresse adresse, String passwort) {
        super(name, passwort);
        this.adresse = adresse;
    }

    public Kunde(String name, int nummer,Adresse adresse, String passwort) {
        super(name, nummer, passwort);
        this.adresse = adresse;
    }

    /**
     * Standard-Methode von Object überschrieben.
     * Methode wird immer automatisch aufgerufen, wenn ein Kunde-Objekt als String
     * benutzt wird (z.B. in println(kunde);)
     */
    public String toString() {
        return "Kunde{" +
                "adresse='" + adresse + '\'' +
                ", name='" + name + '\'' +
                ", nummer=" + nummer +
                '}';
    }

    // Ergänzende-Methoden zum Setzen und Lesen der Kunden-Eigenschaften,

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

}
