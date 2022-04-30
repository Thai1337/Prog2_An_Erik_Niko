package eShop.domain;

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
}
