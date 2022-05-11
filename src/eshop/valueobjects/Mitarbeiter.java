package eshop.valueobjects;

/**
 * Klasse zur Repräsentation einzelner Mitarbeiter.
 *
 * @author seliger
 * @author nguyen
 * @author heuschmann
 */
//TODO Wenn Persistenz dann Mitarbeiternummer generierung ändern
    //TODO Mitarbeiternummern sind scuffed af
public class Mitarbeiter extends Nutzer{
   //Counter der Mitarbeiternummern generiert
    //private static int counter;

    public String toString() {
        return "Mitarbeiter{" +
                "name='" + name + '\'' +
                ", nummer=" + nummer +
                '}';
    }

    public Mitarbeiter(String name, String passwort) {
        this.name = name;
        this.nummer = counter++;
        this.passwort = passwort;
    }


}
