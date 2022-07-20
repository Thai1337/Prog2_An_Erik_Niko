package eshop.net.rmi.common;

import eshop.domain.exceptions.*;
import eshop.valueobjects.*;
//import eShop.net.rmi.common.exceptions.AdresseNotFoundException;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface EshopSerializable extends Remote{

    public List<Artikel> gibAlleArtikel()  throws RemoteException;

    /**
     * Methode, welche eine sortierte Liste aller Artikel im Bestand des E-Shops der Artikelverwaltung erhält und zurückgibt.
     *
     * @param sortierung index welcher den Typ der Sortierung ermittelt
     * @return Vector aller Artikel
     */
    public List<Artikel> gibAlleArtikel(int sortierung) throws RemoteException;

    /**
     * Methode zum Einfügen eines neuen Artikels in den Bestand.
     * Wenn der Artikel bereits im Bestand ist, wird der Bestand nicht geändert.
     *
     * @param bezeichnung Bezeichnung des Artikels
     * @param bestand     Bestand des Artikels
     * @return Artikel-Objekt, das im Erfolgsfall eingefügt wurde
     * @throws RemoteException, ArtikelExistiertBereitsException wenn der Artikel bereits existiert
     * @throws RemoteException, EingabeNichtLeerException        Wenn die Eingabe leer oder falsch ist
     * @throws RemoteException, ArtikelbestandUnterNullException wenn der angegebene bestand beim Einfügen unter null ist
     */
    public Artikel fuegeArtikelEin(String bezeichnung, int bestand, double preis, Mitarbeiter mitarbeiter, int packungsgroesse) throws RemoteException, ArtikelExistiertBereitsException, EingabeNichtLeerException, ArtikelbestandUnterNullException, MassengutartikelBestandsException, IOException, ClassNotFoundException;

    /**
     * Methode zum Ändern des Artikelbestandes.
     *
     * @param nr          Nummer des Artikels
     * @param bezeichnung Bezeichnung des Artikels
     * @param bestand     Bestand des Artikels
     * @throws RemoteException, EingabeNichtLeerException        Wenn die Eingabe leer oder falsch ist
     * @throws RemoteException, ArtikelbestandUnterNullException Wenn der Artikelbestand unter null fällt oder fallen würde
     * @throws RemoteException, ArtikelNichtVorhandenException   Wenn der Artikel nicht in unserem Lager ist
     */
    // Todo Ändern in Bearbeite Artikel
    public void aendereArtikel(String bezeichnung, int nr, int bestand, double preis, Mitarbeiter mitarbeiter, int packungsgroesse) throws RemoteException, EingabeNichtLeerException, ArtikelbestandUnterNullException, ArtikelNichtVorhandenException, MassengutartikelBestandsException, IOException;

    /**
     * Methode zum Löschen eines Artikels aus dem Bestand.
     * Es wird nur das erste Vorkommen des Artikels gelöscht.
     *
     * @param artikelnummer Nummer des Artikels
     * @throws RemoteException, ArtikelNichtVorhandenException wenn die eingegebenden Daten zu keinem Artikel übereinstimmen
     */
    public void loescheArtikel(int artikelnummer, Mitarbeiter mitarbeiter) throws RemoteException, ArtikelNichtVorhandenException, IOException;

    /**
     * Methode zum Suchen von Artikeln anhand der Bezeichnung. Es wird eine Liste von Artikeln
     * zurückgegeben, die alle Artikel mit exakt übereinstimmender Bezeichnung enthält.
     *
     * @param bezeichnung Bezeichnung des gesuchten Artikels
     * @return Liste der gefundenen Artikel (evtl. leer)
     */
    public List<Artikel> sucheNachBezeichnung(String bezeichnung) throws RemoteException;
    /**
     * Methode zum Erstellen eines Mitarbeiters anhand eines Namens und Passworts. Es wird ein neuer Mitarbeiter mit der
     * Mitarbeiternummer zurückgegeben.
     *
     * @param name     Name des Mitarbeiters, welcher eingestellt werden soll
     * @param passwort Passwort des Mitarbeiters, welcher eingestellt werden soll
     * @return Gibt die Mitarbeiternummer des neuen Mitarbeiters zurück
     * @throws RemoteException, EingabeNichtLeerException wenn die Eingabe leer oder falsch ist
     */
    public int erstelleMitarbeiter(String name, String passwort) throws RemoteException, EingabeNichtLeerException, IOException;

    /**
     * Methode zum Anmelden von Mitarbeitern anhand ihrer Mitarbeiternummer und ihres Passworts.
     * Es wird ein Boolischenwert (true) zurückgegeben, wenn die Eingaben im System exakt übereinstimmenden.
     *
     * @param mitarbeiternummer Nummer der im System gesuchten Mitarbeiternummer
     * @param passwort          Passwort des im Systems gesuchten Mitarbeiter
     * @return Ein Mitarbeiter
     */
    public Mitarbeiter mitarbeiterAnmelden(int mitarbeiternummer, String passwort) throws RemoteException, AnmeldungFehlgeschlagenException;

    /**
     * Methode zum Anmelden von Kunden anhand ihrer Kundennummer und des Passworts.
     * Es wird ein Boolischenwert (true) zurückgegeben, wenn die Eingaben im System exakt übereinstimmen.
     *
     * @param kundennummer Nummer der im System gesuchten Kundennummer
     * @param passwort     Passwort des im Systems gesuchten Kunden
     * @return Ein Kunden
     */
    public Kunde kundenAnmelden(int kundennummer, String passwort) throws RemoteException, AnmeldungFehlgeschlagenException;

    /**
     * Methode zum Registrieren von Kunden anhand eines Namens, Passworts und einer Adresse. Es wird ein neuer Kunde mit der
     * Kundennummer zurückgegeben.
     *
     * @param name       Name des Kunden, welcher Regestriert werden soll
     * @param passwort   Passwort gewählt vom Kunden
     * @param strasse    Straße gewählt vom Kunden
     * @param hausnummer Hausnummer gewählt vom Kunden
     * @param plz        postleitzahl gewählt vom Kunden
     * @return Gibt die Kundennummer des neuen Kunden zurück
     * @throws RemoteException, EingabeNichtLeerException wenn die Eingabe leer oder falsch ist
     */
    public int registriereKunden(String name, String passwort, String strasse, int hausnummer, int plz, String ort) throws RemoteException, EingabeNichtLeerException, IOException;

    /**
     * Methode zum Hinzufügen von Artikeln in eine Warenkorb map
     *
     * @param artikelnummer die Artikelnummer zum Warenkorb hinzugefügten Artikel
     * @param anzahlArtikel die Anzahl der Artikel, welche zum Warenkorb hinzugefügt wurden
     * @param kunde         das Kunden Objekt
     * @throws RemoteException, ArtikelbestandUnterNullException wenn der Artikelbestand, den man einfügen will, unter null fällt oder fallen würde
     * @throws RemoteException, ArtikelNichtVorhandenException   wenn der Artikel nicht im Warenkorb ist
     */
    public Warenkorb artikelZuWarenkorb(int artikelnummer, int anzahlArtikel, Kunde kunde) throws RemoteException, ArtikelbestandUnterNullException, ArtikelNichtVorhandenException, MassengutartikelBestandsException;

    /**
     * Methode, die den Warenkorb mit den Artikeln und Gesamtpreis vom Kunden zurückgibt
     *
     * @param kunde das Kundenobjekt
     * @return Warenkorb mit Artikelinfo und aktueller Gesamtpreis (Aufrufen der Warenkorb löschen Methode)
     */
    public Warenkorb getWarenkorb(Kunde kunde) throws RemoteException;

    /**
     * Löscht alle Artikel, die sich im Warenkorb befinden mithilfe der clear methode
     *
     * @param kunde das Kundenobjekt
     * @return
     */
    public Warenkorb warenkorbLoeschen(Kunde kunde) throws RemoteException;

    /**
     * Entfernt einen bestimmten Artikel aus dem Warenkorb
     *
     * @param artikelnummer               die Artikelnummer des zu entfernenden Artikels
     * @param anzahlZuEntfernenderArtikel die Anzahl des zu entfernenden Artikels
     * @param kunde                       das Kundenobjekt
     * @throws RemoteException, ArtikelbestandUnterNullException wenn der bestand im Warenkorb, der zu entfernen ist, unter null ist
     * @throws RemoteException, ArtikelNichtVorhandenException   wenn der Artikel nicht im Warenkorb ist
     */
    public Warenkorb artikelAusWarenkorbEntfernen(int artikelnummer, int anzahlZuEntfernenderArtikel, Kunde kunde) throws RemoteException, ArtikelbestandUnterNullException, ArtikelNichtVorhandenException, MassengutartikelBestandsException;

    /**
     * Methode, welche die Rechnung mit den Artikeln erstellt. Außerdem wird ein Log für den EinKauf in der Protokollverwaltung angefertigt.
     * Bei der Methode wird zusätzlich noch abgefragt, ob noch zum jeweiligen Artikel genug im Lager ist. In dem Fall wird der Kunde
     * darauf hingewiesen, dass er zu langsam war
     *
     * @param kunde das Kundenobjekt
     * @return Aufrufen der einkauf abschließen methode
     * @throws RemoteException, ArtikelbestandUnterNullException wird geworfen, wenn der Bestand im Lager kleiner ist als der Bestand im Warenkorb den man kaufen will
     * @throws RemoteException, WarenkorbLeerException           wenn keine Artikel im Warenkorb sind
     */
    public Rechnung einkaufAbschliessen(Kunde kunde) throws RemoteException, ArtikelbestandUnterNullException, WarenkorbLeerException, ArtikelNichtVorhandenException, IOException;

    /**
     * Gibt die Liste aller Protokolle an die KundenMenu CUI weiter in einem String Vektor
     *
     * @return Protkolllisten Vektor
     */
    public List<Protokoll> getProtokollListe() throws RemoteException, IOException;

    /**
     * Gibt den Artikel anhand der Artikelnummer zurück
     * @param nummer Nummer des Artikels
     * @return der Artikel der gegebenen Artikelnummer
     * @throws RemoteException, ArtikelNichtVorhandenException Wenn die Nummer zu keinem Artikel passt.
     */
    public Artikel gibArtikelNachNummer(int nummer) throws RemoteException, ArtikelNichtVorhandenException;

    public List<Protokoll> getProtokollNachArtikel(int artikelnummer) throws RemoteException, ArtikelNichtVorhandenException;
}
