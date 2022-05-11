package eshop.valueobjects;

/**
 * Klasse zur Repräsentation einzelner Kunden.
 *
 * @author seliger
 * @author nguyen
 * @author heuschmann
 */
public class Kunde extends Nutzer {
    private String adresse;
    // TODO Adresse aufteilen z.B. Klasse adresse
    public Kunde(String name, String adresse, String passwort) {
        this.name = name;
        this.nummer = counter++;
        this.adresse = adresse;
        this.passwort = passwort;
    }

    public Kunde(String name, int nummer,String adresse, String passwort) {
        this.name = name;
        this.nummer = nummer;
        this.adresse = adresse;
        this.passwort = passwort;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

}
