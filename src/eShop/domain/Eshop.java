package eShop.domain;

import eShop.domain.exceptions.ArtikelExistiertBereitsException;
import eShop.domain.exceptions.ArtikelbestandUnterNullException;
import eShop.valueobjects.Artikel;

import java.util.Vector;

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
     * Methode welche einen Vektor aller Artikel im Bestand des E-Shops der Artikelverwlatung erhält und zurückgibt
     *
     * @return Vektor aller Artikel
     */
  public Vector gibAlleArtikel(){
      // einfach delegieren an meineBuecher
      return artikelVW.getArtikelBestand();
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
}
