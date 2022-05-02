package eshop.domain;

import eshop.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.domain.exceptions.ArtikelbestandUnterNullException;
import eshop.valueobjects.Artikel;

import java.util.ArrayList;

/**
 * Klasse zur Verwaltung eines E-Shops.
 * Bietet Methoden zum Zurückgeben aller Artikel im Bestand,
 *
 * @author seliger
 * @author nguyen
 * @author heuschmann
 *
 */
public class Eshop {

    private Artikelverwaltung artikelVW;
    /**
     * Konstruktor, der eine Artikelverwaltung erstellt
     * (Initialisierung des E-Shops).
     */
    public Eshop() {
        artikelVW = new Artikelverwaltung();
    }

    /**
     * Methode welche eine Liste aller Artikel im Bestand des E-Shops der Artikelverwaltung erhält und zurückgibt
     *
     * @return ArrayList aller Artikel
     */
  public ArrayList gibAlleArtikel(){
      // einfach delegieren an meineBuecher
      return artikelVW.getArtikelBestand();
  }

    /**
     * Methode welche eine sortierte Liste aller Artikel im Bestand des E-Shops der Artikelverwaltung erhält und zurückgibt
     * @param sortierung index welcher den Typ der Sortierung ermittelt
     * @return ArrayList aller Artikel
     */
    public ArrayList gibAlleArtikel(int sortierung){
        // einfach delegieren an meineBuecher
        return artikelVW.getArtikelBestand(sortierung);
    }

    /**
     * Methode zum Einfügen eines neuen Artikels in den Bestand.
     * Wenn der Artikel bereits im Bestand ist, wird der Bestand nicht geändert.
     * @param nr Nummer des Artikels
     * @param bezeichnung Bezeichnung des Artikels
     * @param bestand Bestand des Artikels
     * @returns Artikel-Objekt, das im Erfolgsfall eingefügt wurde
     * @throws ArtikelExistiertBereitsException wenn der Artikel bereits existiert
     */
    public Artikel fuegeArtikelEin(int nr, String bezeichnung, int bestand) throws ArtikelExistiertBereitsException {
        Artikel a = new Artikel(nr, bezeichnung, bestand);
        artikelVW.einfuegen(a);
        return a;
    }
    /**
     * Methode zum aendern des Artikelbestandes.
     * @param nr Nummer des Artikels
     * @param bezeichnung Bezeichnung des Artikels
     * @param bestand Bestand des Artikels
     */
    public void aendereArtikelbestand(String bezeichnung, int nr, int bestand) throws ArtikelbestandUnterNullException {
        Artikel a = new Artikel(nr, bezeichnung, bestand);
        artikelVW.aendereArtikelbestand(a);

    }

    /**
     * Methode zum Löschen eines Artikels aus dem Bestand.
     * Es wird nur das erste Vorkommen des Artikels gelöscht.
     *
     * @param bezeichner Bezeichnung des Artikels
     * @param nummer Nummer des Artikels
     */
    public void loescheArtikel(String bezeichner, int nummer) {
        Artikel a = new Artikel(nummer, bezeichner, 0);
        artikelVW.loeschen(a);
    }

    /**
     * Methode zum Suchen von Artikeln anhand der Bezeichnung. Es wird eine Liste von Artikeln
     * zurückgegeben, die alle Artikel mit exakt übereinstimmender Bezeichnung enthält.
     *
     * @param bezeichnung Bezeichnung des gesuchten Artikels
     * @return Liste der gefundenen Artikel (evtl. leer)
     */
    public ArrayList sucheNachbezeichnung(String bezeichnung) {
        // einfach delegieren an meineBuecher
        return artikelVW.sucheArtikel(bezeichnung);
    }
}
