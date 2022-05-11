package eshop.domain;

import eshop.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.domain.exceptions.ArtikelbestandUnterNullException;
import eshop.valueobjects.Artikel;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Klasse zur Verwaltung der Artikel.
 *
 * @author seliger
 * @author nguyen
 * @author heuschmann
 */
public class Artikelverwaltung {
    private List<Artikel> artikelBestand = new Vector();

    /**
     * Konstruktor welcher Artikel erstellt und der Vector des Bestandes hinzufügt
     */
    public Artikelverwaltung() {
        Artikel a1 = new Artikel(10, "Holz", 100);
        Artikel a2 = new Artikel(1, "Metall", 50);
        Artikel a3 = new Artikel(11, "Ball", 50);
        Artikel a4 = new Artikel(12, "Cola", 50);
        Artikel a5 = new Artikel(13, "Ananas", 50);
        Artikel a6 = new Artikel(666, "Buch", 1);
        artikelBestand.add(a1);
        artikelBestand.add(a2);
        artikelBestand.add(a3);
        artikelBestand.add(a4);
        artikelBestand.add(a5);
        artikelBestand.add(a6);

    }

    /**
     * Methode, die eine KOPIE des Artikelbestands zurückgibt.
     * (Eine Kopie ist eine gute Idee, wenn ich dem Empfänger
     * der Daten nicht ermöglichen möchte, die Original-Daten
     * zu manipulieren.)
     *
     * @return Liste aller Artikel im Artikelbestand (Kopie)
     */
    public List<Artikel> getArtikelBestand() {
        return new Vector(artikelBestand);
    }

    /**
     * Methode, die eine KOPIE des Artikelbestands zurückgibt.
     * Die Methode besitz eine andere Signatur,
     * als die Normale getArtikelBestand mit einem Parameter um die Art der Sortierung zu ermitteln.
     * (Eine Kopie ist eine gute Idee, wenn ich dem Empfänger
     * der Daten nicht ermöglichen möchte, die Original-Daten
     * zu manipulieren.)
     *
     * @param sortierung index welcher den Typ der Sortierung ermittelt
     * @return Liste aller Artikel im Artikelbestand sortiert oder nicht als (Kopie)
     */
    public List<Artikel> getArtikelBestand(int sortierung) {
        List<Artikel> sortierteListe;

        switch (sortierung) {
            case 1:
                sortierteListe = artikelBestand.stream().sorted(Comparator.comparing(Artikel::getBezeichnung, String.CASE_INSENSITIVE_ORDER)).collect(Collectors.toList());
                break;
            case 2:
                sortierteListe = artikelBestand.stream().sorted(Comparator.comparing(Artikel::getNummer)).collect(Collectors.toList());
                break;
            case 3:
                sortierteListe = artikelBestand.stream().sorted(Comparator.comparing(Artikel::getBezeichnung, String.CASE_INSENSITIVE_ORDER).reversed()).collect(Collectors.toList());
                break;
            case 4:
                sortierteListe = artikelBestand.stream().sorted(Comparator.comparing(Artikel::getNummer).reversed()).collect(Collectors.toList());
                break;
            default:
                sortierteListe = new Vector<Artikel>(artikelBestand);
                break;
        }
        return sortierteListe;
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
        /*
        Iterator it = artikelBestand.iterator();
        while (it.hasNext()) {
            Artikel artikel = (Artikel) it.next();
            if (artikel.getBezeichnung().equals(einArtikel.getBezeichnung())) {

                artikel.setBestand(einArtikel.getBestand());
            }
        }*/

        for (Artikel artikel : artikelBestand) {
            if (artikel.getBezeichnung().equals(einArtikel.getBezeichnung())) {
                artikel.setBestand(einArtikel.getBestand());
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
    public List<Artikel> sucheArtikel(String bezeichung) {
        List<Artikel> ergebnis = new Vector();

        // Durchlaufen des Vectors mittels Iterator/for-each Schleife
        /*
        Iterator it = artikelBestand.iterator();
        while (it.hasNext()) {
            Artikel artikel = (Artikel) it.next();
            if (artikel.getBezeichnung().equals(bezeichung)) {
                ergebnis.add(artikel);
            }
        }
        */
        // TODO Iterator rauswerfen wenn Projekt fertig danke
        for (Artikel artikel: artikelBestand) {
            if (artikel.getBezeichnung().equals(bezeichung)) {
                ergebnis.add(artikel);
            }
        }

        return ergebnis;
    }
}

