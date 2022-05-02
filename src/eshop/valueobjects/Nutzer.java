package eshop.valueobjects;


/**
 * Abstrakte Klasse welche als Vorlage für Kunde und Mitarbeiter dient.
 * Gibt Methodenköpfe vor für Getter und Setter.
 *
 * @author seliger
 * @author nguyen
 * @author heuschmann
 * @version 1
 */
public abstract class Nutzer { //abstrakt damit kein Objekt der Klasse Nutzer erstellt werden kann, man aber trotzdem die Implementierung in dieser Klasse durchführen kann
    protected String name;
    protected int nummer;
    protected String passwort;

    /**
     * Abstrakte vorgab einer
     * Standard-Methode von Object überschrieben.
     * Methode wird immer automatisch aufgerufen, wenn ein Nutzer-Objekt als String
     * benutzt wird (z.B. in println(Nutzer);)
     */
    public abstract String toString();

    // Methoden zum Setzen und Lesen der Nutzer-Eigenschaften,

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
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