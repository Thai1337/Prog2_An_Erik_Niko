package eshop.domain;

import eshop.domain.exceptions.ArtikelNichtVorhandenException;
import eshop.domain.exceptions.ArtikelbestandUnterNullException;
import eshop.domain.exceptions.MassengutartikelBestandsException;
import eshop.domain.exceptions.WarenkorbLeerException;
import eshop.persistence.ListenPersistence;
import eshop.valueobjects.*;

import java.io.IOException;
import java.util.*;

public class Warenkorbverwaltung {

    private Warenkorb warenkorb;

    private ListenPersistence<Artikel> persistence;

    public Warenkorbverwaltung() {
        persistence = new ListenPersistence<>("artikel");
    }


    /**
     * Fügt Artikel dem Warenkorb hinzu oder erhöht die Anzahl der Artikel im Warenkorb,
     * wenn der Artikel bereits vorhanden ist.
     * Gesamtpreis wird ebenfalls hier berechnet
     *
     * @param artikel                der Artikel, welcher zum Warenkorb hinzugefügt wird
     * @param warenkorbArtikelAnzahl Anzahl an hinzugefügten Artikel
     * @param kunde                  Kunde, dem der Warenkorb zugewiesen wurde
     * @throws ArtikelbestandUnterNullException wenn der hinzugefügte Artikelbestand kleiner 0 sein sollte
     */
    public Warenkorb artikelZuWarenkorbHinzufuegen(Artikel artikel, int warenkorbArtikelAnzahl, Kunde kunde) throws ArtikelbestandUnterNullException, MassengutartikelBestandsException {
        warenkorb = kunde.getWarkorb();

        if (warenkorbArtikelAnzahl <= 0) // eingabe darf nicht kleiner gleich 0 sein
            throw new ArtikelbestandUnterNullException(artikel, " Bitte geben Sie ein Bestellmenge groesser als 0 ein!");

        if (warenkorbArtikelAnzahl > artikel.getBestand())
            throw new ArtikelbestandUnterNullException(artikel, " Die Menge dieses Artikels in Ihrem Warenkorb ist höher als der Bestand im Shop!");

        if(artikel instanceof Massengutartikel && warenkorbArtikelAnzahl % ((Massengutartikel) artikel).getPackungsgrosse() != 0 )
            throw new MassengutartikelBestandsException((Massengutartikel)artikel, warenkorbArtikelAnzahl);

        if ((warenkorbArtikelAnzahl + warenkorb.getArtikelAnzahlImWarenkorb(artikel)) <= artikel.getBestand())  //warenkorb.getArtikelAnzahlImWarenkorb(artikel) = alteWarenkorbArtikelAnzahl
            warenkorb.addArtikelZuWarenkorbListe(artikel, warenkorbArtikelAnzahl);
        else
            throw new ArtikelbestandUnterNullException(artikel, " Die Menge dieses Artikels in Ihrem Warenkorb ist höher als der Bestand im Shop!");
        return warenkorb;
    }

    /**
     * Methode, die einen Artikel aus dem Warenkorb entfernt, wenn anzahlZuEntfernenderArtikel 0 ist
     * oder die differenz der warenkorbArtikelAnzahl - anzahlZuEntfernenderArtikel 0 ergibt.
     * Gesamtpreis wird ebenfalls hier berechnet
     *
     * @param artikel                     bestimmter Artikel
     * @param anzahlZuEntfernenderArtikel Anzahl vom Artikel
     * @param kunde                       Kunde, dem der Warenkorb zugewiesen wurde
     * @throws ArtikelbestandUnterNullException wenn der zu entfernende Artikelbestand kleiner 0 sein sollte
     */
    public Warenkorb artikelAusWarenkorbEntfernen(Artikel artikel, int anzahlZuEntfernenderArtikel, Kunde kunde) throws ArtikelbestandUnterNullException, ArtikelNichtVorhandenException, MassengutartikelBestandsException {
        warenkorb = kunde.getWarkorb();

        if (anzahlZuEntfernenderArtikel < 0) // eingabe darf nicht kleiner als 0 sein | mit 0 wird der ganze artikel gelöscht
            throw new ArtikelbestandUnterNullException(artikel, " Bitte geben Sie ein Entfernmenge groesser als 0 ein!");

        if (!warenkorb.warenkorbEnthaeltArtikel(artikel)) // das wird benötigt, weil wenn man einen im "Lager" vorhandenen artikel entfernen will, dieser aber nicht im Warenkorb existiert gibt es eine NullPointerException
            throw new ArtikelNichtVorhandenException();

        if(artikel instanceof Massengutartikel && anzahlZuEntfernenderArtikel % ((Massengutartikel) artikel).getPackungsgrosse() != 0 )
            throw new MassengutartikelBestandsException((Massengutartikel)artikel, anzahlZuEntfernenderArtikel);

        if (warenkorb.getArtikelAnzahlImWarenkorb(artikel) - anzahlZuEntfernenderArtikel < 0) // Menge im Warenkorb darf nicht negativ sein z.B. 90 im Warenkorb - 92 welche entfernt werden sollen
            throw new ArtikelbestandUnterNullException(artikel, " Der Bestand ihres Warenkorbs darf nicht unter 0 fallen!");

        warenkorb.removeArtikelVonWarenkorbListe(artikel, anzahlZuEntfernenderArtikel);

        return warenkorb;
    }

    /**
     * Methode welche die Map übergibt.
     *
     * @param kunde Kunde, dem der Warenkorb zugewiesen wurde
     * @return Map mit den aktuellen Artikeln im Warenkorb
     */
    public Warenkorb getWarenkorb(Kunde kunde) {
        warenkorb = kunde.getWarkorb();
        return warenkorb;
    }

    /**
     * Methode zum Löschen des gesamten Warenkorbs und zum reset des Gesamtpreises
     *
     * @param kunde Kunde, dem der Warenkorb zugewiesen wurde
     */
    public Warenkorb warenkorbLoeschen(Kunde kunde) {
        warenkorb = kunde.getWarkorb();
        warenkorb.warenkorbLeeren();
        return warenkorb;
    }

    /**
     * Methode zum Abschließen des Einkaufs
     *
     * @param kunde Kunde, dem der Warenkorb zugewiesen wurde
     * @return Aufrufen der erstelle Rechnung methode
     * @throws ArtikelbestandUnterNullException wenn eines der gekauften Artikel nicht mehr zu kaufen ist, da die Menge im Warenkorb größer als die im Lager ist
     * @throws WarenkorbLeerException           wenn sich keine Artikel im Warenkorb befinden
     */
    public Rechnung einkaufAbschliessen(Kunde kunde, List<Artikel> artikelBestand) throws ArtikelbestandUnterNullException, WarenkorbLeerException, ArtikelNichtVorhandenException, IOException {
        warenkorb = kunde.getWarkorb();

        if (warenkorb.getWarenkorbListe().isEmpty())
            throw new WarenkorbLeerException();

        for (Map.Entry<Artikel, Integer> entry : warenkorb.getWarenkorbListe().entrySet()) {
            // TODO kucken ob die if bedinung noch funkt
            if ((entry.getKey().getBestand() - entry.getValue()) < 0) { //überprüft ob der Artikelbestand beim abschliessen des Kaufes immer noch über den Lagerbestand liegt(vllt war ein anderer Kunde schneller)
                warenkorb.removeArtikelVonWarenkorbListe(entry.getKey());
                throw new ArtikelbestandUnterNullException(entry.getKey(), " DU WARST ZU LANGSAM BEIM EINKAUF. NICE TRY! ¯\\_(ツ)_/¯");
            }
            if (!artikelBestand.contains(entry.getKey())) { // entfernt den nicht vorhandenen artikel aus dem warenkorb
                warenkorb.removeArtikelVonWarenkorbListe(entry.getKey());
                throw new ArtikelNichtVorhandenException(entry.getKey(), " Überprüfen Sie ihren Warenkorb, da ein Artikel aus ihrem Warenkorb nicht mehr in unserem Shop vorhanden ist ");
            }

            for (Artikel artikel: artikelBestand) {
                if(artikel.equals(entry.getKey())) {
                    artikel.setBestand(artikel.getBestand() - entry.getValue()); // wenn einkauf erfolgreich: entferne Bestellung aus Bestand hier 4 real
                }
            }
            entry.getKey().setBestand(entry.getKey().getBestand() - entry.getValue()); // wenn einkauf erfolgreich: entferne Bestellung aus Bestand also für die rechnung lul
        }
        persistence.speichernListe(artikelBestand);
        Rechnung rechnung = new Rechnung(kunde); // es wird eine kopie des Kunden erstellt, deswegen über dem warenkorbLeeren()
        warenkorb.warenkorbLeeren();
        return rechnung;
    }

}
