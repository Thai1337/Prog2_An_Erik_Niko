package eShop.valueobjects;


/**
 * Klasse zur Repräsentation einzelner Kunden.
 *
 * @author seliger
 * @author nguyen
 * @author heuschmann
 */
public class Kunde extends Nutzer {
    private String addresse;

    public Kunde(String name, int nummer, String addresse, String passwort) {
        this.name = name;
        this.nummer = nummer;
        this.addresse = addresse;
        this.passwort = passwort;
    }

    /**
     * Standard-Methode von Object überschrieben.
     * Methode wird immer automatisch aufgerufen, wenn ein Kunde-Objekt als String
     * benutzt wird (z.B. in println(kunde);)
     */
    public String toString() {
        return "Kunde{" +
                "addresse='" + addresse + '\'' +
                ", name='" + name + '\'' +
                ", nummer=" + nummer +
                '}';
    }

    // Ergänzende-Methoden zum Setzen und Lesen der Kunden-Eigenschaften,

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

}
