package eshop.domain;

import eshop.domain.exceptions.ArtikelNichtVorhandenException;
import eshop.domain.exceptions.ArtikelbestandUnterNullException;
import eshop.domain.exceptions.WarenkorbLeerException;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.Kunde;
import eshop.valueobjects.Warenkorb;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.DataFormatException;

public class Warenkorbverwaltung {

    private Warenkorb warenkorb;

    public Warenkorbverwaltung(){

    }


    /**
     * Fügt Artikel dem Warenkorb hinzu oder erhöht die Anzahl der Artikel im Warenkorb,
     * wenn der Artikel bereits vorhanden ist.
     * Gesamtpreis wird ebenfalls hier berechnet
     *
     * @param artikel der Artikel, welcher zum Warenkorb hinzugefügt wird
     * @param warenkorbArtikelAnzahl Anzahl an hinzugefügten Artikel
     * @param kunde Kunde, dem der Warenkorb zugewiesen wurde
     * @throws ArtikelbestandUnterNullException wenn der hinzugefügte Artikelbestand kleiner 0 sein sollte
     */
    public void artikelZuWarenkorbHinzufuegen(Artikel artikel, int warenkorbArtikelAnzahl, Kunde kunde) throws ArtikelbestandUnterNullException, ArtikelNichtVorhandenException {
        warenkorb = kunde.getWarkorb();
        //warenkorb.addArtikelZuWarenkorbListe(artikel, warenkorbArtikelAnzahl); //artikel wird zum Warenkorb hinzugefügt
        warenkorb.addArtikelZuWarenkorbListe(artikel, warenkorbArtikelAnzahl); // erhöht bestand im Warenkorb um die neu angegebene Menge, wenn Artikel bereits im Warenkorb
    }

    /**
     * Methode, die einen Artikel aus dem Warenkorb entfernt, wenn anzahlZuEntfernenderArtikel 0 ist
     * oder die differenz der warenkorbArtikelAnzahl - anzahlZuEntfernenderArtikel 0 ergibt.
     * Gesamtpreis wird ebenfalls hier berechnet
     *
     * @param artikel bestimmter Artikel
     * @param anzahlZuEntfernenderArtikel Anzahl vom Artikel
     * @param kunde Kunde, dem der Warenkorb zugewiesen wurde
     * @throws ArtikelbestandUnterNullException wenn der zu entfernende Artikelbestand kleiner 0 sein sollte
     */
    public void artikelAusWarenkorbEntfernen(Artikel artikel, int anzahlZuEntfernenderArtikel, Kunde kunde) throws ArtikelbestandUnterNullException, ArtikelNichtVorhandenException {
        warenkorb = kunde.getWarkorb();

        warenkorb.andereBestandVonWarenkorb(artikel,  -anzahlZuEntfernenderArtikel);

    }

    /**
     * Methode welche die Map übergibt.
     *
     * @param kunde Kunde, dem der Warenkorb zugewiesen wurde
     * @return Map mit den aktuellen Artikeln im Warenkorb
     */
    public Warenkorb getWarenkorb(Kunde kunde){
        warenkorb = kunde.getWarkorb();
        return warenkorb;
    }

    /**
     *Methode zum Löschen des gesamten Warenkorbs und zum reset des Gesamtpreises
     * @param kunde Kunde, dem der Warenkorb zugewiesen wurde
     */
    public void warenkorbLoeschen(Kunde kunde){
        warenkorb = kunde.getWarkorb();
        warenkorb.warenkorbLeeren();
    }

    /**
     * Methode zum Abschließen des Einkaufs
     * @param kunde Kunde, dem der Warenkorb zugewiesen wurde
     * @return Aufrufen der erstelle Rechnung methode
     * @throws ArtikelbestandUnterNullException wenn eines der gekauften Artikel nicht mehr zu kaufen ist, da die Menge im Warenkorb größer als die im Lager ist
     * @throws WarenkorbLeerException wenn sich keine Artikel im Warenkorb befinden
     */
    public String einkaufAbschliessen(Kunde kunde, List<Artikel> artikelBestand) throws ArtikelbestandUnterNullException, WarenkorbLeerException, ArtikelNichtVorhandenException {
        warenkorb = kunde.getWarkorb();

        if(warenkorb.getWarenkorbListe().isEmpty()){
            throw new WarenkorbLeerException();
        }

        for (Map.Entry<Artikel, Integer> entry: warenkorb.getWarenkorbListe().entrySet()) {
            if((entry.getKey().getBestand() - entry.getValue()) < 0){ //überprüft ob der Artikelbestand beim abschliessen des Kaufes immer noch über den Lagerbestand liegt(vllt war ein anderer Kunde schneller)
                 // entfernt den nicht vorhandenen artikel aus dem warenkorb
                warenkorb.getWarenkorbListe().remove(entry.getKey());
                throw new ArtikelbestandUnterNullException(entry.getKey(), " DU WARST ZU LANGSAM BEIM EINKAUF. NICE TRY! ¯\\_(ツ)_/¯");
            }
            if(!artikelBestand.contains(entry.getKey())){
                warenkorb.getWarenkorbListe().remove(entry.getKey());
                throw new ArtikelNichtVorhandenException(entry.getKey(), " Überprüfen sie ihren Warenkorb, da ein Artikel aus ihrem Warenkorb nicht mehr in unserem Shop vorhanden ist ");
            }
            entry.getKey().setBestand(entry.getKey().getBestand() - entry.getValue()); // wenn einkauf erfolgreich: entferne Bestellung aus Bestand
        }
        return erstelleRechnung(kunde);
    }

    /**
     * Methode welche einen String erstellt mit den Inhalten des Einkaufes und den Warenkorb leert
     * @param kunde Kunde, dem der Warenkorb zugewiesen wurde
     * @return der zusammengesetzte Rechnungstring wird zurückgegeben
     */
    private String erstelleRechnung(Kunde kunde){
        String rechnungKunde, rechnungArtikel, rechnung, rechnungGesamtpreis;
        DecimalFormat df = new DecimalFormat("0.00");

        LocalDateTime myObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedDate = myObj.format(myFormatObj);

        rechnungKunde = "\nRechnung an:\t\t\t\t\t\t\t\t\t\t"+ "Datum: "  + formattedDate + " Uhr" +"\n\n\t" + kunde.getName() +"\n\t" + kunde.getAdresse().getStrasse() + " " + kunde.getAdresse().getHomeNumber() +"\n\t" + kunde. getAdresse().getPlz()+ " " + kunde.getAdresse().getOrt() + "\n";

        rechnungArtikel = "\nBestellung:\n";

        for (Map.Entry<Artikel, Integer> entry: kunde.getWarkorb().getWarenkorbListe().entrySet()){
            rechnungArtikel += "\n\tArtikelnummer: " + entry.getKey().getNummer()+ " | Name: " + entry.getKey().getBezeichnung() + " | Stueckpreis: " + df.format(entry.getKey().getPreis()) + "EUR | Menge: " + entry.getValue() + " | Preis: " + df.format(entry.getValue()*entry.getKey().getPreis()) + "EUR";
        }

        rechnungGesamtpreis = "\n\n\tGesamtpreis: " + df.format(kunde.getWarkorb().getGesamtpreis()) + "EUR";

        rechnung = rechnungKunde + rechnungArtikel + rechnungGesamtpreis;

        warenkorbLoeschen(kunde);

        return rechnung;
    }

}
