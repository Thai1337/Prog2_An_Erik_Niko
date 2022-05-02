package eshop.valueobjects;

/**
 * Klasse zur Repräsentation einzelner Mitarbeiter.
 *
 * @author seliger
 * @author nguyen
 * @author heuschmann
 */
public class Mitarbeiter extends Nutzer{

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
