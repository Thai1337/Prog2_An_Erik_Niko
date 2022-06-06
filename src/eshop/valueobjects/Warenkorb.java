package eshop.valueobjects;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Klasse zur Repräsentation einzelner Warenkörbe
 *
 * @author seliger
 * @author nguyen
 * @author heuschmann
 */
public class Warenkorb implements Serializable {
    //private double gesamtpreis;
    @Serial
    private static final long serialVersionUID = 98674546;

    private Map<Artikel, Integer> warenkorbListe;

    public Warenkorb() {
        warenkorbListe = new Hashtable<>();
    }

    public Warenkorb(Warenkorb warenkorb) {
        this.warenkorbListe = new Hashtable<>(warenkorb.getWarenkorbListe());
    }

    /**
     * Methode zum hinzufuegen von Artikeln in eine Hash-table mit ihrer Menge
     *
     * @param artikel       fuegt dem Warenkorb einen bestimmten Artikel ein
     * @param anzahlArtikel Menge des jeweiligen Artikels im Warenkorb
     */
    public void addArtikelZuWarenkorbListe(Artikel artikel, int anzahlArtikel) {
        warenkorbListe.put(artikel, anzahlArtikel);
    }

    public void removeArtikelVonWarenkorbListe(Artikel artikel) {
        warenkorbListe.remove(artikel);
    }

    public void warenkorbLeeren() {
        warenkorbListe.clear();
    }

    public int getArtikelAnzahlImWarenkorb(Artikel artikel) {
        return warenkorbListe.get(artikel);
    }

    // leeren

    // bestand ändern


    public Map<Artikel, Integer> getWarenkorbListe() {
        return warenkorbListe;
    }

    public double getGesamtpreis() {
        double gesamtpreis = 0;
        for (Map.Entry<Artikel, Integer> entry : warenkorbListe.entrySet()) {
            gesamtpreis += entry.getKey().getPreis() * entry.getValue();
        }
        return gesamtpreis;
    }
    // getter: gesamtpreis hier errechnen
    // setter: weg
    /*public void setGesamtpreis(double gesamtpreis){
        this.gesamtpreis = gesamtpreis;
    }

    public void gesamtpreisErhoehen(double gesamtpreis) {
        this.gesamtpreis += gesamtpreis;
    }

    public void gesamtpreisVerringern(double gesamtpreis) {
        this.gesamtpreis -= gesamtpreis;
    }*/


}



