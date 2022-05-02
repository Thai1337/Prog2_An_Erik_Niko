package eShop.valueobjects;

/**
 * Klasse zur Repräsentation einzelner Mitarbeiter.
 *
 * @author seliger
 * @author nguyen
 * @author heuschmann
 */
public class Mitarbeiter extends Nutzer{

    /**
     * Standard-Methode von Object überschrieben.
     * Methode wird immer automatisch aufgerufen, wenn ein Kunde-Objekt als String
     * benutzt wird (z.B. in println(kunde);)
     */
    public String toString() {
        return "Mitarbeiter{" +
                "name='" + name + '\'' +
                ", nummer=" + nummer +
                '}';
    }

    public Mitarbeiter(String name, int nummer) {
        this.name = name;
        this.nummer = nummer;
    }


}
