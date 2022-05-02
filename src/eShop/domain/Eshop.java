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

    public Artikel fuegeArtikelEin(int nr, String titel, int nummer) throws ArtikelExistiertBereitsException {
        Artikel a = new Artikel(nr, titel, nummer);
        artikelVW.einfuegen(a);
        return a;
    }

}
