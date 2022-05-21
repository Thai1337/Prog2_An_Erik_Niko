package eshop.domain;

import eshop.domain.exceptions.AnmeldungFehlgeschlagenException;
import eshop.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.domain.exceptions.ArtikelbestandUnterNullException;
import eshop.domain.exceptions.EingabeNichtLeerException;
import eshop.valueobjects.*;

import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Klasse zur Verwaltung eines E-Shops.
 * Bietet Methoden zum Zurückgeben aller Artikel im Bestand.
 *
 * @author seliger
 * @author nguyen
 * @author heuschmann
 *
 */
public class Eshop {

    private Artikelverwaltung artikelVW;
    private Mitarbeiterverwaltung mitarbeiterVW;
    private Kundenverwaltung kundenVW;

    private Warenkorbverwaltung warenkoerbeVW;
    /**
     * Konstruktor, der eine Artikelverwaltung, eine Mitarbeiterverwaltung und eine Kundenverwaltung erstellt.
     * (Initialisierung des E-Shops).
     */
    public Eshop() {
        artikelVW = new Artikelverwaltung();
        mitarbeiterVW = new Mitarbeiterverwaltung();
        kundenVW = new Kundenverwaltung();
        warenkoerbeVW = new Warenkorbverwaltung();
    }

    /**
     * Methode, welche eine Liste aller Artikel im Bestand des E-Shops der Artikelverwaltung erhält und zurückgibt.
     *
     * @return Vector aller Artikel
     */
  public List<Artikel> gibAlleArtikel(){
      // einfach delegieren an meineBuecher
      return artikelVW.getArtikelBestand();
  }

    /**
     * Methode, welche eine sortierte Liste aller Artikel im Bestand des E-Shops der Artikelverwaltung erhält und zurückgibt.
     * @param sortierung index welcher den Typ der Sortierung ermittelt
     * @return Vector aller Artikel
     */
    public List<Artikel> gibAlleArtikel(int sortierung){
        // einfach delegieren an meineBuecher
        return artikelVW.getArtikelBestand(sortierung);
    }

    /**
     * Methode zum Einfügen eines neuen Artikels in den Bestand.
     * Wenn der Artikel bereits im Bestand ist, wird der Bestand nicht geändert.
     * @param bezeichnung Bezeichnung des Artikels
     * @param bestand Bestand des Artikels
     * @return Artikel-Objekt, das im Erfolgsfall eingefügt wurde
     * @throws ArtikelExistiertBereitsException wenn der Artikel bereits existiert
     */
    public Artikel fuegeArtikelEin(String bezeichnung, int bestand, double preis) throws ArtikelExistiertBereitsException, EingabeNichtLeerException {
        Artikel a = new Artikel(bezeichnung, bestand, preis);
        artikelVW.einfuegen(a);
        return a;
    }
    /**
     * Methode zum ändern des Artikelbestandes.
     * @param nr Nummer des Artikels
     * @param bezeichnung Bezeichnung des Artikels
     * @param bestand Bestand des Artikels
     */
    // Todo Ändern in Bearbeite Artikel
    public void aendereArtikel(String bezeichnung, int nr, int bestand, double preis) throws ArtikelbestandUnterNullException {

        Artikel a = new Artikel(nr, bezeichnung, bestand, preis);
        artikelVW.aendereArtikelbestand(a);

    }

    /**
     * Methode zum Löschen eines Artikels aus dem Bestand.
     * Es wird nur das erste Vorkommen des Artikels gelöscht.
     *
     * @param bezeichner Bezeichnung des Artikels
     * @param nummer Nummer des Artikels
     */
    public void loescheArtikel(String bezeichner, int nummer) {
        Artikel a = new Artikel(nummer, bezeichner, 0, 0);
        artikelVW.loeschen(a);
    }

    /**
     * Methode zum Suchen von Artikeln anhand der Bezeichnung. Es wird eine Liste von Artikeln
     * zurückgegeben, die alle Artikel mit exakt übereinstimmender Bezeichnung enthält.
     *
     * @param bezeichnung Bezeichnung des gesuchten Artikels
     * @return Liste der gefundenen Artikel (evtl. leer)
     */
    public List<Artikel> sucheNachbezeichnung(String bezeichnung) {
        // Methode zum suchen von Buechern nach der Bezeichnung
        return artikelVW.sucheArtikel(bezeichnung);
    }

    /**
     * Methode zum Erstellen eines Mitarbeiters anhand eines Namens und Passworts. Es wird ein neuer Mitarbeiter mit der
     * Mitarbeiternummer zurückgegeben.
     *
     * @param name Name des Mitarbeiters, welcher eingestellt werden soll
     * @param passwort Passwort des Mitarbeiters, welcher eingestellt werden soll
     * @return Gibt die Mitarbeiternummer des neuen Mitarbeiters zurück
     */
    public int erstelleMitarbeiter(String name, String passwort) throws EingabeNichtLeerException {
        Mitarbeiter m = new Mitarbeiter(name ,passwort);
        return mitarbeiterVW.erstelleMitarbeiter(m);
    }

    /**
     * Methode zum Anmelden von Mitarbeitern anhand ihrer Mitarbeiternummer und ihres Passworts.
     * Es wird ein Boolischenwert (true) zurückgegeben, wenn die Eingaben im System exakt übereinstimmenden.
     *
     * @param nummer Nummer der im System gesuchten Mitarbeiternummer
     * @param passwort Passwort des im Systems gesuchten Mitarbeiter
     * @return Ein Boolischenwert, welcher True ist, wenn der Mitarbeiter im System ist oder False, wenn dieser nicht im System ist
     */
    public Mitarbeiter mitarbeiterAnmelden(int nummer, String passwort) throws AnmeldungFehlgeschlagenException {
        return mitarbeiterVW.mitarbeiterAnmelden(nummer, passwort);
    }
    /**
     * Methode zum Anmelden von Kunden anhand ihrer Kundennummer und des Passworts.
     * Es wird ein Boolischenwert (true) zurückgegeben, wenn die Eingaben im System exakt übereinstimmen.
     *
     * @param nummer Nummer der im System gesuchten Kundennummer
     * @param passwort Passwort des im Systems gesuchten Kunden
     * @return Ein Boolischenwert, welcher True ist, wenn der Kunden im System ist oder False, wenn dieser nicht im System ist
     */
    public Kunde kundenAnmelden(int nummer, String passwort) throws AnmeldungFehlgeschlagenException {
        return kundenVW.kundeAnmelden(nummer, passwort);
    }
    /**
     * Methode zum Registrieren von Kunden anhand eines Namens, Passworts und einer Adresse. Es wird ein neuer Kunde mit der
     * Kundennummer zurückgegeben.
     *
     * @param name Name des Kunden, welcher Regestriert werden soll
     * @param passwort Passwort gewählt vom Kunden
     * @param strasse Straße gewählt vom Kunden
     * @param hausnummer Hausnummer gewählt vom Kunden
     * @param  plz postleitzahl gewählt vom Kunden
     * @return Gibt die Kundennummer des neuen Kunden zurück
     */
    public int registriereKunden(String name, String passwort, String strasse, int hausnummer, int plz, String ort) throws EingabeNichtLeerException {
        Adresse a = new Adresse(strasse, hausnummer, plz, ort);
        Kunde k = new Kunde(name, a, passwort);
        return kundenVW.erstelleKunde(k);
    }
    /**
     * Methode zum Hinzufügen von Artikeln in eine Warenkorb map
     *
     * @param artikelnummer die Artikelnummer zum Warenkorb hinzugefügten Artikel
     * @param anzahlArtikel die Anzahl der Artikel, welche zum Warenkorb hinzugefügt wurden
     * @param kunde das Kunden Objekt
     *
     */
    public void artikelZuWarenkorb(int artikelnummer, int anzahlArtikel, Kunde kunde) throws ArtikelbestandUnterNullException {
        for (Artikel artikel: artikelVW.getArtikelBestand()) {
            if(artikel.getNummer() == artikelnummer){
                warenkoerbeVW.artikelZuWarenkorbHinzufuegen(artikel, anzahlArtikel, kunde);
            }
        }
    }

    /**
     *
     * @param kunde
     * @return
     */
    public Warenkorb getWarenkorb(Kunde kunde){
        return warenkoerbeVW.getWarenkorb(kunde);
    }

    /**
     *
     * @param kunde
     */
    public void warenkorbLoeschen(Kunde kunde){
        warenkoerbeVW.warenkorbLoeschen(kunde);
    }

    /**
     *
     * @param artikelnummer
     * @param anzahlArtikel
     * @param kunde
     * @throws ArtikelbestandUnterNullException
     */
    public void artikelAusWarenkorbEntfernen(int artikelnummer, int anzahlArtikel, Kunde kunde) throws ArtikelbestandUnterNullException {
            for (Artikel a: artikelVW.getArtikelBestand()) {
                if(a.getNummer() == artikelnummer){
                    warenkoerbeVW.artikelAusWarenkorbEntfernen(a, anzahlArtikel, kunde);
                }
            }
    }

    /**
     *
     * @param kunde
     * @return
     * @throws ArtikelbestandUnterNullException
     */
    public String einkaufAbschliessen(Kunde kunde) throws ArtikelbestandUnterNullException {
        return warenkoerbeVW.einkaufAbschliessen(kunde);
    }
}