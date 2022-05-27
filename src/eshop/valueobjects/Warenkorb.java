package eshop.valueobjects;

import eshop.domain.exceptions.ArtikelNichtVorhandenException;
import eshop.domain.exceptions.ArtikelbestandUnterNullException;

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
    //private double gesamtpreis;


    private Map<Artikel, Integer> warenkorbListe;

    public Warenkorb(){
        warenkorbListe = new Hashtable<>();
    }

    /**
     * Methode zum hinzufuegen von Artikeln in eine Hash-table mit ihrer Menge
     * @param artikel fuegt dem Warenkorb einen bestimmten Artikel ein
     * //@param anzahlArtikel Menge des jeweiligen Artikels im Warenkorb
     */
    /*public void addArtikelZuWarenkorbListe(Artikel artikel, int anzahlArtikel){
        warenkorbListe.put(artikel, anzahlArtikel);
    }*/

    public void warenkorbLeeren() {
        warenkorbListe.clear();
    }

    private void artikelExistiert(Artikel artikel) throws ArtikelNichtVorhandenException {
        if(!warenkorbListe.containsKey(artikel)) { // das wird benötigt, weil wenn man einen im "Lager" vorhandenen artikel entfernen will, dieser aber nicht im Warenkorb existiert gibt es eine NullPointerException
            throw new ArtikelNichtVorhandenException();
        }
    }

    public void removeArtikelVonWarenkorbListe(Artikel artikel) throws ArtikelNichtVorhandenException {
        //artikelExistiert(artikel);

        warenkorbListe.remove(artikel);
    }

    public void andereBestandVonWarenkorb(Artikel artikel, int neuerBestand) throws ArtikelNichtVorhandenException, ArtikelbestandUnterNullException {
        artikelExistiert(artikel); // das wird benötigt, weil wenn man einen im "Lager" vorhandenen artikel entfernen will, dieser aber nicht im Warenkorb existiert gibt es eine NullPointerException

        int alterBestand = warenkorbListe.get(artikel);
        if(neuerBestand == 0 || alterBestand - neuerBestand == 0){
            removeArtikelVonWarenkorbListe(artikel);
        }

        if((neuerBestand + alterBestand) > artikel.getBestand()) {
            throw new ArtikelbestandUnterNullException(artikel, " Die Menge dieses Artikels in Ihrem Warenkorb ist höher als der Bestand im Shop!");
        }
        warenkorbListe.put(artikel, neuerBestand + alterBestand);
    }

    public void addArtikelZuWarenkorbListe(Artikel artikel, int bestand) throws ArtikelbestandUnterNullException, ArtikelNichtVorhandenException {

        //artikelExistiert(artikel); // das wird benötigt, weil wenn man einen im "Lager" vorhandenen artikel entfernen will, dieser aber nicht im Warenkorb existiert gibt es eine NullPointerException

        if(bestand < 0) { // eingabe darf nicht kleiner als 0 sein | mit 0 wird der ganze artikel gelöscht
            throw new ArtikelbestandUnterNullException(artikel, " Bitte geben Sie ein Entfernmenge groesser als 0 ein!");
        }

        warenkorbListe.put(artikel, bestand);
    }

    public void getArtikelAusWarenkorb(){

    }

    // leeren

    // bestand ändern


    public Map<Artikel, Integer> getWarenkorbListe() {
        return warenkorbListe;
    }

    public double getGesamtpreis() {
        double gesamtpreis = 0;

        for(Map.Entry<Artikel, Integer> entry: warenkorbListe.entrySet()) {
            gesamtpreis += entry.getKey().getPreis() * entry.getValue();
        }

        return gesamtpreis;
    }


}



