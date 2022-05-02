package eShop.domain;

import eShop.domain.exceptions.ArtikelExistiertBereitsException;
import eShop.domain.exceptions.ArtikelbestandUnterNullException;
import eShop.valueobjects.Artikel;

import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

/**
 * Klasse zur Verwaltung der Artikel.
 *
 * @author seliger
 * @author nguyen
 * @author heuschmann
 */
public class Artikelverwaltung {
    private Vector artikelBestand = new Vector();

    /**
     * Konstruktor welcher Artikel erstellt und dem Vektor des Bestandes hinzufügt
     */
    public Artikelverwaltung() {
        Artikel a1 = new Artikel(10, "Holz", 100);
        Artikel a2 = new Artikel(1, "Metall", 50);
        Artikel a3 = new Artikel(11, "Benis", 50);
        Artikel a4 = new Artikel(12, "COCK", 50);
        Artikel a5 = new Artikel(13, "Anus", 50);
        artikelBestand.add(a1);
        artikelBestand.add(a2);
        artikelBestand.add(a3);
        artikelBestand.add(a4);
        artikelBestand.add(a5);


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
        return new Vector(artikelBestand);
    }

    /**
     * Methode, die ein Artikel an das Ende des artikelBestandes einfügt.
     *
     * @param einArtikel der einzufügende Artikel
     * @throws ArtikelExistiertBereitsException wenn ein Artikel bereits existiert
     */
    public void einfuegen(Artikel einArtikel) throws ArtikelExistiertBereitsException {
        if (artikelBestand.contains(einArtikel)) {
            throw new ArtikelExistiertBereitsException(einArtikel, " - in 'einfuegen()'");
        }

        // das übernimmt der Vector:
        artikelBestand.add(einArtikel);

    }

    /**
     * Methode, die ein Artikel an das Ende des artikelBestandes einfügt.
     *
     * @param einArtikel der einzufügende Artikel
     * @throws ArtikelbestandUnterNullException wenn der eingegebene Artikelbestand unter 0 ist
     */
    public void aendereArtikelbestand(Artikel einArtikel) throws ArtikelbestandUnterNullException {
        // das übernimmt der Vector:
        if (einArtikel.getBestand() < 0) {
            throw new ArtikelbestandUnterNullException(einArtikel, " AMIGO");

        }/*else if(einArtikel.getBestand == 0 && ){

        }
        */
        Iterator it = artikelBestand.iterator();
        while (it.hasNext()) {
            Artikel artikel = (Artikel) it.next();
            if (artikel.getBezeichnung().equals(einArtikel.getBezeichnung())) {

                artikel.setBestand(einArtikel.getBestand());

                //TODO löschen methode ausführen wenn bestand 0 ist mit Abfrage und AMIGO entfernen
            }
        }
    }
    /**
     * Methode zum Löschen eines Artikels aus dem Bestand.
     *
     * @param einArtikel der löschende Artikel
     */
    public void loeschen(Artikel einArtikel) {
        // das übernimmt der Vector:
        artikelBestand.remove(einArtikel);
    }

    /**
     * Methode, die anhand einer Bezeichnung nach Artikeln sucht. Es wird eine Liste von Artikeln
     * zurückgegeben, die alle Artikel mit exakt übereinstimmender Bezeichnung enthält.
     *
     * @param bezeichung Bezeichnung des gesuchten Artikels
     * @return Liste der Artikel mit gesuchter Bezeichnung (evtl. leer)
     */
    public Vector sucheArtikel(String bezeichung) {
        Vector ergebnis = new Vector();

        // Durchlaufen des Vectors mittels Iterator
        // (alternativ: for each-Schleife)
        Iterator it = artikelBestand.iterator();
        while (it.hasNext()) {
            Artikel artikel = (Artikel) it.next();
            if (artikel.getBezeichnung().equals(bezeichung)) {
                ergebnis.add(artikel);
            }
        }

        return ergebnis;
    }
}

