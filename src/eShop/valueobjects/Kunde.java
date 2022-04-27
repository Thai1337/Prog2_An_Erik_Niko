package eShop.valueobjects;


/**
 * Klasse zur Repräsentation einzelner Kunden.
 *
 * @author seliger test
 * @author nguyen test
 * @author heuschmann
 */
public class Kunde extends Nutzer {
    private String addresse;

    public Kunde(String name, int nummer, String addresse) {
        this.name = name;
        this.nummer = nummer;
        this.addresse = addresse;
    }

    /**
     * Standard-Methode von Object überschrieben.
     * Methode wird immer automatisch aufgerufen, wenn ein Buch-Objekt als String
     * benutzt wird (z.B. in println(buch);)
     */
    public String toString() {
        return "Kunde{" +
                "addresse='" + addresse + '\'' +
                ", name='" + name + '\'' +
                ", nummer=" + nummer +
                '}';
    }

    // Methoden zum Setzen und Lesen der Kunden-Eigenschaften,

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNummer() {
        return nummer;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

}
