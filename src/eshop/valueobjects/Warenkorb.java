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
public class Warenkorb implements Serializable{
    //private double gesamtpreis;
    @Serial
    private static final long serialVersionUID = 1231246;
    private Map<Artikel, Integer> warenkorbListe;

    public Warenkorb() {
        warenkorbListe = new Hashtable<>();
    }

    public Warenkorb(Warenkorb warenkorb) {
        // TODO verweise in der Hashtable sind keine kopien, ist quasi fertig nur noch testen
        Map<Artikel, Integer> kopie = new Hashtable<>();
        for (Map.Entry<Artikel, Integer> entry : warenkorb.getWarenkorbListe().entrySet())
            kopie.put(new Artikel(entry.getKey()), entry.getValue());

        this.warenkorbListe = kopie;
    }

    /**
     * Methode zum hinzufuegen von Artikeln in eine Hash-table mit ihrer Menge
     *
     * @param artikel       fuegt dem Warenkorb einen bestimmten Artikel ein
     * @param anzahlArtikel Menge des jeweiligen Artikels im Warenkorb
     */
    public void addArtikelZuWarenkorbListe(Artikel artikel, int anzahlArtikel) {
        if(!warenkorbListe.containsKey(artikel)){
            warenkorbListe.put(artikel, anzahlArtikel);
        }else{
            int alteWarenkorbArtikelAnzahl = getArtikelAnzahlImWarenkorb(artikel);
            warenkorbListe.put(artikel, anzahlArtikel + alteWarenkorbArtikelAnzahl);
        }
    }

    public void removeArtikelVonWarenkorbListe(Artikel artikel) {
        warenkorbListe.remove(artikel);
    }
    public void removeArtikelVonWarenkorbListe(Artikel artikel, int anzahlZuEntfernenderArtikel) {
        if(anzahlZuEntfernenderArtikel == 0 || (getArtikelAnzahlImWarenkorb(artikel) - anzahlZuEntfernenderArtikel) == 0) { // wenn die eingegebene Menge im warenkorb 0 ist oder das ergebnis z.B. 92-92 0 wird, wird der Artikel entfernt
            removeArtikelVonWarenkorbListe(artikel);
        }else{
            int alteWarenkorbArtikelAnzahl = getArtikelAnzahlImWarenkorb(artikel);
            warenkorbListe.put(artikel, alteWarenkorbArtikelAnzahl - anzahlZuEntfernenderArtikel);
        }
    }

    public void warenkorbLeeren() {
        warenkorbListe.clear();
    }

    public boolean warenkorbEnthaeltArtikel(Artikel artikel) {
        return warenkorbListe.containsKey(artikel);
    }

    public int getArtikelAnzahlImWarenkorb(Artikel artikel) {
        if(!warenkorbListe.containsKey(artikel)){ // wird benötigt falls der artikel im Bestand existiert, aber nicht im warenkorb ist(Nullpointerexception) weil der artikel im warenkorb nicht gefunden werden kann
            return 0;
        }
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



