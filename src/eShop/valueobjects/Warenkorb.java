package eShop.valueobjects;

/**
 * Klasse zur Repräsentation einzelner Warenkörbe
 *
 * @author seliger
 * @author nguyen
 * @author heuschmann
 */
public class Warenkorb {
    private double gesamtpreis;

    public Warenkorb(double gesamtpreis){
        this.gesamtpreis = gesamtpreis;
    }
    /**
     * Standard-Methode von Object überschrieben.
     * Methode wird immer automatisch aufgerufen, wenn ein Warenkorb-Objekt als String
     * benutzt wird (z.B. in println(warenkorb);)
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "Warenkorb{" +
                "gesamtpreis=" + gesamtpreis +
                '}';
    }

    public double getGesamtpreis() {
        return gesamtpreis;
    }

    public void setGesamtpreis(double gesamtpreis) {
        this.gesamtpreis = gesamtpreis;
    }
}
