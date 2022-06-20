package eshop.domain;

import eshop.domain.exceptions.*;
import eshop.persistence.ListenPersistence;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.Massengutartikel;

import java.io.IOException;
import java.util.*;

/**
 * Klasse zur Verwaltung der Artikel.
 *
 * @author seliger
 * @author nguyen
 * @author heuschmann
 */
public class Artikelverwaltung {
    private List<Artikel> artikelBestand = new Vector<>();

    private ListenPersistence<Artikel> persistence;

    /**
     * Konstruktor welcher Artikel erstellt und der Vector des Bestandes hinzufügt
     */
    public Artikelverwaltung() {
        /*Artikel a1 = new Artikel("Holz", 100, 2.01);
        Artikel a12 = new Artikel("Holzbrett", 6, 4.01);
        Artikel a2 = new Artikel("Metall", 50, 6.01);
        Artikel a3 = new Artikel("Ball", 50, 8.01);
        Artikel a4 = new Artikel("Cola", 50, 10.01);
        Artikel a5 = new Artikel("Ananas", 50, 11.01);
        Artikel a6 = new Artikel("Buch", 1, 12.01);
        Massengutartikel am1 = new Massengutartikel("Bier", 60, 5, 6);
        artikelBestand.add(a1);
        artikelBestand.add(a12);
        artikelBestand.add(a2);
        artikelBestand.add(a3);
        artikelBestand.add(a4);
        artikelBestand.add(a5);
        artikelBestand.add(a6);
        artikelBestand.add(am1);*/
        persistence = new ListenPersistence<>("artikel");
    }

    /**
     * Liest die Artikel aus der artikel.txt Datei ein und lädt sie in einen Vektor
     * @throws IOException
     */
    public void liesArtikel() throws IOException {
        artikelBestand = persistence.ladenListe();
    }

    /**
     * Schreibt die Artikel aus dem Vektor in die artikel.txt Datei
     * @throws IOException
     */
    public void schreibArtikel() throws IOException {
        persistence.speichernListe(artikelBestand);
    }

    /**
     * Gibt die höchste Artikelnummer zurück, welche gespeichert wurde bzw. vom letzten Artikel in der Liste
     * @return Artikelnummer des letzten Artikels
     * @throws IOException
     */
    public int getNummerVomLetztenArtikel() throws IOException {
        liesArtikel();
        return artikelBestand.get(artikelBestand.size() - 1).getNummer();
    }

    /**
     * Gibt den Artikel anhand der Artikelnummer zurück
     * @param artikelNummer Nummer des Artikels
     * @return der Artikel der gegebenen Artikelnummer
     * @throws ArtikelNichtVorhandenException Wenn die Nummer zu keinem Artikel passt.
     */
    public Artikel gibArtikelNachNummer(int artikelNummer) throws ArtikelNichtVorhandenException {
        for (Artikel gesuchterArtikel : artikelBestand) {
            if (gesuchterArtikel.getNummer() == artikelNummer) {
                return gesuchterArtikel;
            }
        }
        throw new ArtikelNichtVorhandenException();
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
        List<Artikel> kopie = new Vector<Artikel>(artikelBestand);

        Collections.sort(kopie, new Comparator<Artikel>(){
            public int compare(Artikel a1, Artikel a2){
                switch (sortierung){
                    case 1:
                        return a1.getBezeichnung().compareToIgnoreCase(a2.getBezeichnung());
                    case 2:
                        return a1.getNummer() - a2.getNummer();
                    case 3:
                        return a2.getBezeichnung().compareToIgnoreCase(a1.getBezeichnung());
                    case 4:
                        return a2.getNummer() - a1.getNummer();
                    default:
                        return 0;
                }
            }
        });

        return kopie;
    }


    /**
     * Methode, die ein Artikel an das Ende des artikelBestandes einfügt.
     *
     * @param einArtikel der einzufügende Artikel
     * @throws ArtikelExistiertBereitsException wenn ein Artikel bereits existiert
     * @throws EingabeNichtLeerException        wenn eines der eingegebenen Daten leer ist
     * @throws ArtikelbestandUnterNullException wenn der artikelbestand unter -1 ist
     */
    public void einfuegen(Artikel einArtikel) throws ArtikelExistiertBereitsException, EingabeNichtLeerException, ArtikelbestandUnterNullException, MassengutartikelBestandsException, IOException {
        if (artikelBestand.contains(einArtikel)) //.contains() benutzt equals Methode von Artikel, welche in der Artikelklasse überschrieben wurde.
            throw new ArtikelExistiertBereitsException(einArtikel, " im Lager!");

        if (einArtikel.getBestand() < -1)
            throw new ArtikelbestandUnterNullException(einArtikel, " AMIGO");

        if (einArtikel.getNummer() <= -1 || einArtikel.getPreis() < 0 || einArtikel.getBestand() == -1 || einArtikel.getBezeichnung().isEmpty())
            throw new EingabeNichtLeerException(); // TODO optional: werte an die exception übergeben und dort logik einbauen um zu überprüfen was falsch ist

        if(einArtikel instanceof Massengutartikel && (((Massengutartikel) einArtikel).getPackungsgrosse() == 0 || ((Massengutartikel) einArtikel).getPackungsgrosse() <= -2))
            throw new MassengutartikelBestandsException(((Massengutartikel) einArtikel));

        if(einArtikel instanceof Massengutartikel && einArtikel.getBestand() % ((Massengutartikel) einArtikel).getPackungsgrosse() != 0)
            throw new MassengutartikelBestandsException((Massengutartikel)einArtikel);


        // das übernimmt der Vector:
        artikelBestand.add(einArtikel);
        schreibArtikel();
    }

    /**
     * Methode, die ein Artikel an das Ende des artikelBestandes einfügt.
     *
     * @param einArtikel der einzufügende Artikel
     * @throws ArtikelbestandUnterNullException wenn der eingegebene Artikelbestand unter -1 ist
     * @throws EingabeNichtLeerException        wenn alle eingegebenen Werte leer sind
     * @throws ArtikelNichtVorhandenException   wenn der Artikel nicht in unserem Lager ist
     */
    public void aendereArtikel(Artikel einArtikel) throws ArtikelbestandUnterNullException, EingabeNichtLeerException, ArtikelNichtVorhandenException, MassengutartikelBestandsException, IOException {
        // das übernimmt der Vector:
        // TODO exception damit keine negativen Preise eingegeben werden können und die java-doc ändern!!!! dafuq?
        if (!artikelBestand.contains(einArtikel))
            throw new ArtikelNichtVorhandenException();

        if (einArtikel.getBestand() < -1)
            throw new ArtikelbestandUnterNullException(einArtikel, " AMIGO");

        if (einArtikel.getNummer() <= -1 && einArtikel.getPreis() < 0 && einArtikel.getBestand() == -1 && einArtikel.getBezeichnung().isEmpty())
            throw new EingabeNichtLeerException(); // TODO optional: werte an die exception übergeben und dort logik einbauen um zu überprüfen was falsch ist

        if(einArtikel instanceof Massengutartikel && einArtikel.getBestand() != -1 && einArtikel.getBestand() % ((Massengutartikel) einArtikel).getPackungsgrosse() != 0)
            throw new MassengutartikelBestandsException((Massengutartikel)einArtikel); // wenn Bestand und Packgröße gleichzeitig geändert werden

        for (Artikel artikel : artikelBestand) {
            if (artikel.getNummer() == einArtikel.getNummer()) {
                if (!einArtikel.getBezeichnung().isEmpty()) {
                    artikel.setBezeichnung(einArtikel.getBezeichnung());
                }
                if (einArtikel.getPreis() != -1.01) {
                    artikel.setPreis(einArtikel.getPreis());
                }
                if(einArtikel instanceof Massengutartikel && einArtikel.getBestand() != -1 && einArtikel.getBestand() % ((Massengutartikel) artikel).getPackungsgrosse() != 0){
                    throw new MassengutartikelBestandsException((Massengutartikel)einArtikel); // wenn nur Bestand geändert wird
                }
                if (einArtikel.getBestand() != -1) {
                    artikel.setBestand(einArtikel.getBestand());
                }
                if(einArtikel instanceof Massengutartikel && artikel.getBestand() % ((Massengutartikel) einArtikel).getPackungsgrosse() != 0){
                    throw new MassengutartikelBestandsException((Massengutartikel)einArtikel); // wenn nur Packgröße geändert wird
                }
                if(einArtikel instanceof Massengutartikel){
                    ((Massengutartikel) artikel).setPackungsgrosse(((Massengutartikel) einArtikel).getPackungsgrosse());
                }
                schreibArtikel();
            }

        }

    }

    /**
     * Methode zum Löschen eines Artikels aus dem Bestand.
     *
     * @param einArtikel der löschende Artikel
     * @throws ArtikelNichtVorhandenException wenn sich der zu löschende Artikel nicht in unserem Lager befindet
     */
    public void loeschen(Artikel einArtikel) throws IOException {
        // das übernimmt der Vector:
        artikelBestand.remove(einArtikel);
        schreibArtikel();
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

        for (Artikel artikel : artikelBestand) {
            if (artikel.getBezeichnung().toLowerCase().contains(bezeichung.toLowerCase())) {
                ergebnis.add(artikel);
            }
        }

        return ergebnis;
    }
}

