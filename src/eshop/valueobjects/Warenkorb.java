package eshop.valueobjects;

/**
 * Klasse zur Repräsentation einzelner Warenkörbe
 *
 * @author seliger
 * @author nguyen
 * @author heuschmann
 */
public class Warenkorb {
    private double gesamtpreis;
    private int nutzernummer;

    public Warenkorb(int nutzernummer){
        this.nutzernummer = nutzernummer;
    }

    public double getGesamtpreis() {
        return gesamtpreis;
    }

    public void setGesamtpreis(double gesamtpreis) {
        this.gesamtpreis = gesamtpreis;
    }

    public int getNutzernummer() {
        return nutzernummer;
    }

    public void setNutzernummer(int nutzernummer) {
        this.nutzernummer = nutzernummer;
    }
}
