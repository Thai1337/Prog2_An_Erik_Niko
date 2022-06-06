package eshop.valueobjects;

import java.io.Serial;
import java.util.Hashtable;
import java.util.Map;

/**
 * Klasse zur Repräsentation einzelner Kunden.
 *
 * @author seliger
 * @author nguyen
 * @author heuschmann
 */
public class Kunde extends Nutzer {
    @Serial
    private static final long serialVersionUID = 7635563;
    private Adresse adresse;

    private Warenkorb meinWarenkorb;

    public Kunde(String name, Adresse adresse, String passwort) {
        super(name, passwort);
        this.adresse = adresse;
        this.meinWarenkorb = new Warenkorb();
    }

    public Kunde(int nummer, String name, Adresse adresse, String passwort) {
        super(name, nummer, passwort);
        this.adresse = adresse;
        this.meinWarenkorb = new Warenkorb();
    }
    /*
    public void artikelZuWarenkorb(Artikel artikel, int anzahlArtikel){
        Map<Artikel, Integer> map = new Hashtable<>();
        map.put(artikel, anzahlArtikel);
        meinWarenkorb.setWarenkorbListe(map);
    }*/

    public Warenkorb getWarkorb() {
        return meinWarenkorb;
    }

    public void setMeinWarenkorb(Warenkorb meinWarenkorb) {
        this.meinWarenkorb = meinWarenkorb;
    }

    /**
     * Standard-Methode von Object überschrieben.
     * Methode wird immer automatisch aufgerufen, wenn ein Kunde-Objekt als String
     * benutzt wird (z.B. in println(kunde);)
     */
    public String toString() {
        return "Kunde{" +
                "adresse='" + adresse + '\'' +
                ", name='" + name + '\'' +
                ", nummer=" + nummer +
                '}';
    }

    // Ergänzende-Methoden zum Setzen und Lesen der Kunden-Eigenschaften,

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

}
