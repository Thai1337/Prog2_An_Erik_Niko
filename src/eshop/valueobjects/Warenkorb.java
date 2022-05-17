package eshop.valueobjects;

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
public class Warenkorb {
    private double gesamtpreis;


    private Map<Artikel, Integer> warenkorbListe;

    public Warenkorb(){
        warenkorbListe = new Hashtable<>();
    }

    /**
     * Methode zum hinzufuegen von Artikeln in eine Hash-table mit ihrer Menge
     * @param artikel fuegt dem Warenkorb einen bestimmten Artikel ein
     * @param anzahlArtikel Menge des jeweiligen Artikels im Warenkorb
     */
    public void setWarenkorbListe(Artikel artikel, int anzahlArtikel){
        warenkorbListe.put(artikel, anzahlArtikel);
    }



    public Map<Artikel, Integer> getWarenkorbListe() {
        return warenkorbListe;
    }

    public double getGesamtpreis() {
        return gesamtpreis;
    }

    public void setGesamtpreis(double gesamtpreis) {
        this.gesamtpreis = gesamtpreis;
    }


}



