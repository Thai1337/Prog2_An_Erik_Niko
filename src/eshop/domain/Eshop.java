package eshop.domain;

import eshop.domain.exceptions.*;
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
    private Protokollverwaltung protokollVW;
    /**
     * Konstruktor, der eine Artikelverwaltung, eine Mitarbeiterverwaltung und eine Kundenverwaltung erstellt.
     * (Initialisierung des E-Shops).
     */
    public Eshop() {
        artikelVW = new Artikelverwaltung();
        mitarbeiterVW = new Mitarbeiterverwaltung();
        kundenVW = new Kundenverwaltung();
        warenkoerbeVW = new Warenkorbverwaltung();
        protokollVW = new Protokollverwaltung();
    }

    /**
     * Methode, welche eine Liste aller Artikel im Bestand des E-Shops der Artikelverwaltung erhält und zurückgibt.
     *
     * @return Vector aller Artikel
     */
  public List<Artikel> gibAlleArtikel(){
      return artikelVW.getArtikelBestand();
  }

    /**
     * Methode, welche eine sortierte Liste aller Artikel im Bestand des E-Shops der Artikelverwaltung erhält und zurückgibt.
     * @param sortierung index welcher den Typ der Sortierung ermittelt
     * @return Vector aller Artikel
     */
    public List<Artikel> gibAlleArtikel(int sortierung){
        return artikelVW.getArtikelBestand(sortierung);
    }

    /**
     * Methode zum Einfügen eines neuen Artikels in den Bestand.
     * Wenn der Artikel bereits im Bestand ist, wird der Bestand nicht geändert.
     * @param bezeichnung Bezeichnung des Artikels
     * @param bestand Bestand des Artikels
     * @return Artikel-Objekt, das im Erfolgsfall eingefügt wurde
     * @throws ArtikelExistiertBereitsException wenn der Artikel bereits existiert
     * @throws EingabeNichtLeerException Wenn die Eingabe leer oder falsch ist
     * @throws ArtikelbestandUnterNullException wenn der angegebene bestand beim Einfügen unter null ist
     */
    public Artikel fuegeArtikelEin(String bezeichnung, int bestand, double preis, Mitarbeiter mitarbeiter) throws ArtikelExistiertBereitsException, EingabeNichtLeerException, ArtikelbestandUnterNullException {
        Artikel neuerArtikel = new Artikel(bezeichnung, bestand, preis);
        artikelVW.einfuegen(neuerArtikel);

        protokollVW.einfuegenLoeschenLog(new Protokoll(mitarbeiter, neuerArtikel, true));
        return neuerArtikel;
    }
    /**
     * Methode zum ändern des Artikelbestandes.
     * @param nr Nummer des Artikels
     * @param bezeichnung Bezeichnung des Artikels
     * @param bestand Bestand des Artikels
     * @throws EingabeNichtLeerException Wenn die Eingabe leer oder falsch ist
     * @throws ArtikelbestandUnterNullException Wenn der Artikelbestand unter null fällt oder fallen würde
     * @throws ArtikelNichtVorhandenException Wenn der Artikel nicht in unserem Lager ist
     */
    // Todo Ändern in Bearbeite Artikel
    public void aendereArtikel(String bezeichnung, int nr, int bestand, double preis, Mitarbeiter mitarbeiter) throws EingabeNichtLeerException, ArtikelbestandUnterNullException, ArtikelNichtVorhandenException {
        Artikel artikel = new Artikel(nr, bezeichnung, bestand, preis);
        artikelVW.aendereArtikel(artikel);

        protokollVW.bearbeitenLog(new Protokoll(mitarbeiter, artikel));

    }

    /**
     * Methode zum Löschen eines Artikels aus dem Bestand.
     * Es wird nur das erste Vorkommen des Artikels gelöscht.
     *
     * @param artikelnummer Nummer des Artikels
     * @throws ArtikelNichtVorhandenException wenn die eingegebenden Daten zu keinem Artikel übereinstimmen
     */
    public void loescheArtikel(int artikelnummer, Mitarbeiter mitarbeiter) throws ArtikelNichtVorhandenException {
        Artikel zuEntfernenderArtikel; // new Artikel(artikelnummer, "", 0, 0);

        zuEntfernenderArtikel = artikelVW.gibArtikelNachNummer(artikelnummer);
        artikelVW.loeschen(zuEntfernenderArtikel);

        protokollVW.einfuegenLoeschenLog(new Protokoll(mitarbeiter, zuEntfernenderArtikel, false));
    }

    /**
     * Methode zum Suchen von Artikeln anhand der Bezeichnung. Es wird eine Liste von Artikeln
     * zurückgegeben, die alle Artikel mit exakt übereinstimmender Bezeichnung enthält.
     *
     * @param bezeichnung Bezeichnung des gesuchten Artikels
     * @return Liste der gefundenen Artikel (evtl. leer)
     */
    public List<Artikel> sucheNachBezeichnung(String bezeichnung) {
        return artikelVW.sucheArtikel(bezeichnung);
    }

    /**
     * Methode zum Erstellen eines Mitarbeiters anhand eines Namens und Passworts. Es wird ein neuer Mitarbeiter mit der
     * Mitarbeiternummer zurückgegeben.
     *
     * @param name Name des Mitarbeiters, welcher eingestellt werden soll
     * @param passwort Passwort des Mitarbeiters, welcher eingestellt werden soll
     * @return Gibt die Mitarbeiternummer des neuen Mitarbeiters zurück
     * @throws EingabeNichtLeerException wenn die Eingabe leer oder falsch ist
     */
    public int erstelleMitarbeiter(String name, String passwort) throws EingabeNichtLeerException {
        Mitarbeiter mitarbeiter = new Mitarbeiter(name ,passwort);
        return mitarbeiterVW.erstelleMitarbeiter(mitarbeiter);
    }

    /**
     * Methode zum Anmelden von Mitarbeitern anhand ihrer Mitarbeiternummer und ihres Passworts.
     * Es wird ein Boolischenwert (true) zurückgegeben, wenn die Eingaben im System exakt übereinstimmenden.
     *
     * @param mitarbeiternummer Nummer der im System gesuchten Mitarbeiternummer
     * @param passwort Passwort des im Systems gesuchten Mitarbeiter
     * @return Ein Mitarbeiter
     */
    public Mitarbeiter mitarbeiterAnmelden(int mitarbeiternummer, String passwort) throws AnmeldungFehlgeschlagenException {
        return mitarbeiterVW.mitarbeiterAnmelden(mitarbeiternummer, passwort);
    }
    /**
     * Methode zum Anmelden von Kunden anhand ihrer Kundennummer und des Passworts.
     * Es wird ein Boolischenwert (true) zurückgegeben, wenn die Eingaben im System exakt übereinstimmen.
     *
     * @param kundennummer Nummer der im System gesuchten Kundennummer
     * @param passwort Passwort des im Systems gesuchten Kunden
     * @return Ein Kunden
     */
    public Kunde kundenAnmelden(int kundennummer, String passwort) throws AnmeldungFehlgeschlagenException {
        return kundenVW.kundeAnmelden(kundennummer, passwort);
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
     * @throws EingabeNichtLeerException wenn die Eingabe leer oder falsch ist
     */
    public int registriereKunden(String name, String passwort, String strasse, int hausnummer, int plz, String ort) throws EingabeNichtLeerException {
        Adresse adresse = new Adresse(strasse, hausnummer, plz, ort);
        Kunde neuerKunde = new Kunde(name, adresse, passwort);
        return kundenVW.erstelleKunde(neuerKunde);
    }
    /**
     * Methode zum Hinzufügen von Artikeln in eine Warenkorb map
     *
     * @param artikelnummer die Artikelnummer zum Warenkorb hinzugefügten Artikel
     * @param anzahlArtikel die Anzahl der Artikel, welche zum Warenkorb hinzugefügt wurden
     * @param kunde das Kunden Objekt
     * @throws ArtikelbestandUnterNullException wenn der Artikelbestand, den man einfügen will, unter null fällt oder fallen würde
     * @throws ArtikelNichtVorhandenException wenn der Artikel nicht im Warenkorb ist
     */
    public void artikelZuWarenkorb(int artikelnummer, int anzahlArtikel, Kunde kunde) throws ArtikelbestandUnterNullException, ArtikelNichtVorhandenException {
        Artikel artikel = artikelVW.gibArtikelNachNummer(artikelnummer);
        warenkoerbeVW.artikelZuWarenkorbHinzufuegen(artikel, anzahlArtikel, kunde);
    }

    /**
     * Methode, die den Warenkorb mit den Artikeln und Gesamtpreis vom Kunden zurückgibt
     * @param kunde das Kundenobjekt
     * @return Warenkorb mit Artikelinfo und aktueller Gesamtpreis (Aufrufen der Warenkorb löschen Methode)
     */
    public Warenkorb getWarenkorb(Kunde kunde){
        return warenkoerbeVW.getWarenkorb(kunde);
    }

    /**
     * Löscht alle Artikel, die sich im Warenkorb befinden mithilfe der clear methode
     * @param kunde das Kundenobjekt
     */
    public void warenkorbLoeschen(Kunde kunde){
        warenkoerbeVW.warenkorbLoeschen(kunde);
    }

    /**
     *Entfernt einen bestimmten Artikel aus dem Warenkorb
     * @param artikelnummer die Artikelnummer des zu entfernenden Artikels
     * @param anzahlZuEntfernenderArtikel die Anzahl des zu entfernenden Artikels
     * @param kunde das Kundenobjekt
     * @throws ArtikelbestandUnterNullException wenn der bestand im Warenkorb, der zu entfernen ist, unter null ist
     * @throws ArtikelNichtVorhandenException wenn der Artikel nicht im Warenkorb ist
     */
    public void artikelAusWarenkorbEntfernen(int artikelnummer, int anzahlZuEntfernenderArtikel, Kunde kunde) throws ArtikelbestandUnterNullException, ArtikelNichtVorhandenException {
        Artikel zuEntfernenderArtikel = artikelVW.gibArtikelNachNummer(artikelnummer);
        warenkoerbeVW.artikelAusWarenkorbEntfernen(zuEntfernenderArtikel, anzahlZuEntfernenderArtikel, kunde);
    }

    /**
     * Methode, welche die Rechnung mit den Artikeln erstellt. Außerdem wird ein Log für den EinKauf in der Protokollverwaltung angefertigt.
     * Bei der Methode wird zusätzlich noch abgefragt, ob noch zum jeweiligen Artikel genug im Lager ist. In dem Fall wird der Kunde
     * darauf hingewiesen, dass er zu langsam war
     * @param kunde das Kundenobjekt
     * @return Aufrufen der einkauf abschließen methode
     * @throws ArtikelbestandUnterNullException wird geworfen, wenn der Bestand im Lager kleiner ist als der Bestand im Warenkorb den man kaufen will
     * @throws WarenkorbLeerException wenn keine Artikel im Warenkorb sind
     */
    public String einkaufAbschliessen(Kunde kunde) throws ArtikelbestandUnterNullException, WarenkorbLeerException, ArtikelNichtVorhandenException {
        Protokoll protokoll = new Protokoll(kunde, artikelVW.getArtikelBestand());
        protokollVW.kaufLog(protokoll);

        return warenkoerbeVW.einkaufAbschliessen(kunde, artikelVW.getArtikelBestand());

    }

    /**
     * Gibt die Liste aller Protokolle an die KundenMenu CUI weiter in einem String Vektor
     * @return Protkolllisten Vektor
     */
    public List<String> getProtokollListe(){
        return protokollVW.getProtokollListe();
    }


}