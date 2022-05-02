package eShop.domain;

import eShop.domain.exceptions.ArtikelExistiertBereitsException;
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
     * @param nr Titel des Buchs
     * @param bezeichnung Titel des Buchs
     * @param bestand Nummer des Buchs
     * @returns Artikel-Objekt, das im Erfolgsfall eingefügt wurde
     * @throws ArtikelExistiertBereitsException wenn das Buch bereits existiert
     */
    public Artikel fuegeArtikelEin(int nr, String bezeichnung, int bestand) throws ArtikelExistiertBereitsException {
        Artikel a = new Artikel(nr, bezeichnung, bestand);
        artikelVW.einfuegen(a);
        return a;
    }

}
