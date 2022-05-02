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
    private ArrayList artikelBestand = new ArrayList();

    /**
     * Konstruktor welcher Artikel erstellt und der ArrayList des Bestandes hinzufügt
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
    public ArrayList getArtikelBestand() {
        return new ArrayList(artikelBestand);
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
    public ArrayList getArtikelBestand(int sortierung) {
        ArrayList sortierteListe;

        switch (sortierung) {
            case 1:
                sortierteListe = (ArrayList) artikelBestand.stream().sorted(Comparator.comparing(Artikel::getBezeichnung, String.CASE_INSENSITIVE_ORDER)).collect(Collectors.toList());
                break;
            case 2:
                sortierteListe = (ArrayList) artikelBestand.stream().sorted(Comparator.comparing(Artikel::getNummer)).collect(Collectors.toList());
                break;
            case 3:
                sortierteListe = (ArrayList) artikelBestand.stream().sorted(Comparator.comparing(Artikel::getBezeichnung, String.CASE_INSENSITIVE_ORDER).reversed()).collect(Collectors.toList());
                break;
            case 4:
                sortierteListe = (ArrayList) artikelBestand.stream().sorted(Comparator.comparing(Artikel::getNummer).reversed()).collect(Collectors.toList());
                break;
            default:
                sortierteListe = artikelBestand;
                break;
        }
        return new ArrayList(sortierteListe);
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

        // das übernimmt der ArrayList:
        artikelBestand.add(einArtikel);

    }

    /**
     * Methode, die ein Artikel an das Ende des artikelBestandes einfügt.
     *
     * @param einArtikel der einzufügende Artikel
     * @throws ArtikelbestandUnterNullException wenn der eingegebene Artikelbestand unter 0 ist
     */
    public void aendereArtikelbestand(Artikel einArtikel) throws ArtikelbestandUnterNullException {
        // das übernimmt der ArrayList:
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
        // das übernimmt der ArrayList:
        artikelBestand.remove(einArtikel);
    }

    /**
     * Methode, die anhand einer Bezeichnung nach Artikeln sucht. Es wird eine Liste von Artikeln
     * zurückgegeben, die alle Artikel mit exakt übereinstimmender Bezeichnung enthält.
     *
     * @param bezeichung Bezeichnung des gesuchten Artikels
     * @return Liste der Artikel mit gesuchter Bezeichnung (evtl. leer)
     */
    public ArrayList sucheArtikel(String bezeichung) {
        ArrayList ergebnis = new ArrayList();

        // Durchlaufen des ArrayLists mittels Iterator
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

