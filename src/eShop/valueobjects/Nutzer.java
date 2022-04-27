package eShop.valueobjects;


/**
 * Abstrakte Klasse welche als Vorlage für Kunde und Mitarbeiter dient.
 * Gibt Methodenköpfe vor für Getter und Setter.
 *
 * @author seliger ass
 * @author nguyen
 * @author heuschmann
 * @version 1
 */
public abstract class Nutzer {
    protected String name;
    protected int nummer;


    // Methoden zum Setzen und Lesen der Nutzer-Eigenschaften,

    public abstract String getName();

    public abstract void setName(String name);

    public abstract int getNummer();

    public abstract void setNummer(int nummer);

}
