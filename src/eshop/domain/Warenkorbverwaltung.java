package eshop.domain;

import eshop.domain.exceptions.ArtikelNichtVorhandenException;
import eshop.domain.exceptions.ArtikelbestandUnterNullException;
import eshop.domain.exceptions.MassengutartikelBestandsException;
import eshop.domain.exceptions.WarenkorbLeerException;
import eshop.persistence.ListenPersistence;
import eshop.valueobjects.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.DataFormatException;

public class Warenkorbverwaltung {

    private Warenkorb warenkorb;

    private ListenPersistence<Artikel> persistence = new ListenPersistence<>("artikel");

    public Warenkorbverwaltung() {

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
    public void artikelZuWarenkorbHinzufuegen(Artikel artikel, int warenkorbArtikelAnzahl, Kunde kunde) throws ArtikelbestandUnterNullException, MassengutartikelBestandsException {
        warenkorb = kunde.getWarkorb();

        if (warenkorbArtikelAnzahl <= 0) { // eingabe darf nicht kleiner gleich 0 sein
            throw new ArtikelbestandUnterNullException(artikel, " Bitte geben Sie ein Bestellmenge groesser als 0 ein!");
        }
        if (warenkorbArtikelAnzahl > artikel.getBestand()) {
            throw new ArtikelbestandUnterNullException(artikel, " Die Menge dieses Artikels in Ihrem Warenkorb ist höher als der Bestand im Shop!");
        }

        if(artikel instanceof Massengutartikel && warenkorbArtikelAnzahl % ((Massengutartikel) artikel).getPackungsgrosse() != 0 ){
            throw new MassengutartikelBestandsException();
        }

        if (!warenkorb.getWarenkorbListe().containsKey(artikel)) {
            warenkorb.addArtikelZuWarenkorbListe(artikel, warenkorbArtikelAnzahl); //artikel wird zum Warenkorb hinzugefügt
        } else {
            int alteWarenkorbArtikelAnzahl = warenkorb.getArtikelAnzahlImWarenkorb(artikel);    // gibt eine NullPointerException, wenn außerhalb des if, weil versucht wird auf einen leeren artikel zuzugreifen, falls diese nichts existiert(deswegen containsKey)
            if ((warenkorbArtikelAnzahl + alteWarenkorbArtikelAnzahl) > artikel.getBestand()) {
                throw new ArtikelbestandUnterNullException(artikel, " Die Menge dieses Artikels in Ihrem Warenkorb ist höher als der Bestand im Shop!");
            }
            warenkorb.addArtikelZuWarenkorbListe(artikel, warenkorbArtikelAnzahl + alteWarenkorbArtikelAnzahl); // erhöht bestand im Warenkorb um die neu angegebene Menge, wenn Artikel bereits im Warenkorb
        }
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
    public void artikelAusWarenkorbEntfernen(Artikel artikel, int anzahlZuEntfernenderArtikel, Kunde kunde) throws ArtikelbestandUnterNullException, ArtikelNichtVorhandenException, MassengutartikelBestandsException {
        warenkorb = kunde.getWarkorb();
        if (anzahlZuEntfernenderArtikel < 0) { // eingabe darf nicht kleiner als 0 sein | mit 0 wird der ganze artikel gelöscht
            throw new ArtikelbestandUnterNullException(artikel, " Bitte geben Sie ein Entfernmenge groesser als 0 ein!");
        }

        if (!warenkorb.getWarenkorbListe().containsKey(artikel)) { // das wird benötigt, weil wenn man einen im "Lager" vorhandenen artikel entfernen will, dieser aber nicht im Warenkorb existiert gibt es eine NullPointerException
            throw new ArtikelNichtVorhandenException();
        }

        if(artikel instanceof Massengutartikel && anzahlZuEntfernenderArtikel % ((Massengutartikel) artikel).getPackungsgrosse() != 0 ){
            throw new MassengutartikelBestandsException();
        }

        int warenkorbArtikelAnzahl = warenkorb.getArtikelAnzahlImWarenkorb(artikel);
        if (warenkorbArtikelAnzahl - anzahlZuEntfernenderArtikel < 0) { // Menge im Warenkorb darf nicht negativ sein z.B. 90 im Warenkorb - 92 welche entfernt werden sollen
            throw new ArtikelbestandUnterNullException(artikel, " Der Bestand ihres Warenkorbs darf nicht unter 0 fallen!");
        }

        if (anzahlZuEntfernenderArtikel == 0 || (warenkorbArtikelAnzahl - anzahlZuEntfernenderArtikel) == 0) { // wenn die eingegebene Menge im warenkorb 0 ist oder das ergebnis z.B. 92-92 0 wird, wird der Artikel entfernt
            warenkorb.removeArtikelVonWarenkorbListe(artikel);
        } else { // sonst wird die Menge des Artikels im Warenkorb geändert
            warenkorb.addArtikelZuWarenkorbListe(artikel, warenkorbArtikelAnzahl - anzahlZuEntfernenderArtikel);
        }

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
    public void warenkorbLoeschen(Kunde kunde) {
        warenkorb = kunde.getWarkorb();
        //warenkorb.keeren();
        warenkorb.warenkorbLeeren();
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

        if (warenkorb.getWarenkorbListe().isEmpty()) {
            throw new WarenkorbLeerException();
        }

        for (Map.Entry<Artikel, Integer> entry : warenkorb.getWarenkorbListe().entrySet()) {
            if ((entry.getKey().getBestand() - entry.getValue()) < 0) { //überprüft ob der Artikelbestand beim abschliessen des Kaufes immer noch über den Lagerbestand liegt(vllt war ein anderer Kunde schneller)
                // entfernt den nicht vorhandenen artikel aus dem warenkorb
                warenkorb.removeArtikelVonWarenkorbListe(entry.getKey());
                throw new ArtikelbestandUnterNullException(entry.getKey(), " DU WARST ZU LANGSAM BEIM EINKAUF. NICE TRY! ¯\\_(ツ)_/¯");
            }
            if (!artikelBestand.contains(entry.getKey())) {
                warenkorb.removeArtikelVonWarenkorbListe(entry.getKey());
                throw new ArtikelNichtVorhandenException(entry.getKey(), " Überprüfen sie ihren Warenkorb, da ein Artikel aus ihrem Warenkorb nicht mehr in unserem Shop vorhanden ist ");
            }
            entry.getKey().setBestand(entry.getKey().getBestand() - entry.getValue()); // wenn einkauf erfolgreich: entferne Bestellung aus Bestand
            persistence.speichernListe(artikelBestand);
        }
        return new Rechnung(kunde);
    }

}
