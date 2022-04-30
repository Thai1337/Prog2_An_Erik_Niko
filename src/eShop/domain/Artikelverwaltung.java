package eShop.domain;

import eShop.valueobjects.Artikel;

import java.util.Vector;

/**
 * Klasse zur Verwaltung der Artikel.
 *
 * @author seliger
 * @author nguyen
 * @author heuschmann
 */
public class Artikelverwaltung {
    private Vector ArtikelBestand = new Vector();

    /**
     * Konstruktor welcher Artikel erstellt und dem Vektor des Bestandes hinzufügt
     */
    public Artikelverwaltung(){
        Artikel a1 = new Artikel(10, "Holz", 100);
        Artikel a2 = new Artikel(1, "Metall", 50);
        ArtikelBestand.add(a1);
        ArtikelBestand.add(a2);


    }
    /**
     * Methode, die eine KOPIE des Artikelbestands zurückgibt.
     * (Eine Kopie ist eine gute Idee, wenn ich dem Empfänger
     * der Daten nicht ermöglichen möchte, die Original-Daten
     * zu manipulieren.)
     *
     * @return Liste aller Artikel im Artikelbestand (Kopie)
     */
    public Vector getArtikelBestand() {
        return new Vector(ArtikelBestand);

    }

}

