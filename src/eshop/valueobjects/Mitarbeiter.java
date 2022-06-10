package eshop.valueobjects;

import java.io.Serial;
import java.io.Serializable;

/**
 * Klasse zur Repräsentation einzelner Mitarbeiter.
 *
 * @author seliger
 * @author nguyen
 * @author heuschmann
 */
//TODO Wenn Persistenz dann Mitarbeiternummer generierung ändern
//TODO Mitarbeiternummern sind scuffed af
public class Mitarbeiter extends Nutzer {
    //Counter der Mitarbeiternummern generiert
    //private static int counter;
    @Serial
    private static final long serialVersionUID = 345634;
    public String toString() {
        return "Mitarbeiter{" +
                "name='" + name + '\'' +
                ", nummer=" + nummer +
                '}';
    }

    public Mitarbeiter(String name, String passwort) {
        super(name, passwort);
    }

    public Mitarbeiter(int nummer, String name, String passwort) {
        super(name, nummer, passwort);
    }

    public Mitarbeiter(Mitarbeiter mitarbeiter) {
        super(mitarbeiter.getName(), mitarbeiter.getNummer(), mitarbeiter.getPasswort());
    }


}
