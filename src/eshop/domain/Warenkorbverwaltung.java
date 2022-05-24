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
import java.util.Base64;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.zip.DataFormatException;

public class Warenkorbverwaltung {

    private Warenkorb warenkorb;

    public Warenkorbverwaltung(){

    }


    /**
     * Fügt Artikel dem Warenkorb hinzu
     * @param artikel der Artikel, welcher zum Warenkorb hinzugefügt wird
     * @param warenkorbArtikelAnzahl Anzahl an hinzugefügten Artikel
     * @param kunde Kunde, dem der Warenkorb zugewiesen wurde
     * @throws ArtikelbestandUnterNullException wenn der hinzugefügte Artikelbestand kleiner 0 sein sollte
     */
    public void artikelZuWarenkorbHinzufuegen(Artikel artikel, int warenkorbArtikelAnzahl, Kunde kunde) throws ArtikelbestandUnterNullException {
        warenkorb = kunde.getWarkorb();

        if(warenkorbArtikelAnzahl <= 0){
            throw new ArtikelbestandUnterNullException(artikel, " Bitte geben Sie ein Bestellmenge groesser als 0 ein!");
        }

        if(warenkorb.getWarenkorbListe().containsKey(artikel)){ // wenn der artikel vorhanden ist ....

            int alteWarenkorbArtikelAnzahl = warenkorb.getWarenkorbListe().get(artikel);    // gibt eine NullPointerException, wenn außerhalb des ifs, weil versucht wird auf einen leeren artikel zuzugreifen, falls diese nichts existiert(deswegen containsKey)
            if((warenkorbArtikelAnzahl + alteWarenkorbArtikelAnzahl) > artikel.getBestand()) {   // Wenn die Menge im Warenkorb größer ist als der Bestand des Artikels im Lager throw exception
                throw new ArtikelbestandUnterNullException(artikel, " Die Menge dieses Arikels in Ihrem Warenkorb ist höher als der Bestand im Shop");
            }
            warenkorb.addWarenkorbListe(artikel, warenkorbArtikelAnzahl + alteWarenkorbArtikelAnzahl); // .... erhöhe bestand im Warenkorb um die neue Menge
        }else{

            if(warenkorbArtikelAnzahl > artikel.getBestand()){
                throw new ArtikelbestandUnterNullException(artikel, " Die Menge dieses Artikels in Ihrem Warenkorb ist höher als der Bestand im Shop");
            }
            warenkorb.addWarenkorbListe(artikel, warenkorbArtikelAnzahl); //artikel wird zum Warenkorb hinzugefügt
        }
        warenkorb.gesamtpreisErhoehen(artikel.getPreis() * warenkorbArtikelAnzahl);
    }

    /**
     * Methode, die einen Artikel aus dem Warenkorb entfernt
     * @param artikel bestimmter Artikel
     * @param anzahlZuEntfernenderArtikel Anzahl vom Artikel
     * @param kunde Kunde, dem der Warenkorb zugewiesen wurde
     * @throws ArtikelbestandUnterNullException wenn der zu entfernende Artikelbestand kleiner 0 sein sollte
     */
    public void artikelAusWarenkorbEntfernen(Artikel artikel, int anzahlZuEntfernenderArtikel, Kunde kunde) throws ArtikelbestandUnterNullException, ArtikelNichtVorhandenException {
        warenkorb = kunde.getWarkorb();
        if(anzahlZuEntfernenderArtikel < 0){ // eingabe darf nicht kleiner als 0 sein
            throw new ArtikelbestandUnterNullException(artikel, " Bitte geben Sie ein Entfernmenge groesser als 0 ein!");
        }

        if(!warenkorb.getWarenkorbListe().containsKey(artikel)){ // das wird benötigt, weil wenn man einen im "Lager" vorhandenen artikel entfernen will, dieser aber nicht im Warenkorb existiert gibt es eine NullPointerException
            throw new ArtikelNichtVorhandenException();
        }

        int warenkorbArtikelAnzahl = warenkorb.getWarenkorbListe().get(artikel);
        if(warenkorbArtikelAnzahl - anzahlZuEntfernenderArtikel < 0){ // Menge im Warenkorb darf nicht negativ sein z.B. 90 im Warenkorb - 92 welche entfernt werden sollen
            throw new ArtikelbestandUnterNullException(artikel, " Der Bestand ihres Warenkorbs darf nicht unter 0 fallen!");
        }

        if(anzahlZuEntfernenderArtikel == 0 || (warenkorbArtikelAnzahl - anzahlZuEntfernenderArtikel) == 0){ // wenn die eingegebene Menge im warenkorb 0 ist oder das ergebnis z.B. 92-92 0 wird, wird der Artikel entfernt
            warenkorb.getWarenkorbListe().remove(artikel);
            warenkorb.gesamtpreisVerringern(artikel.getPreis() * warenkorbArtikelAnzahl);
        }else{ // sonst wird die Menge des Artikels im Warenkorb geändert
            warenkorb.addWarenkorbListe(artikel,  warenkorbArtikelAnzahl - anzahlZuEntfernenderArtikel);
            warenkorb.gesamtpreisVerringern(artikel.getPreis() * anzahlZuEntfernenderArtikel);
        }

    }

    /**
     * Methode welche die Map übergibt und den Gesammtpreis berechnet
     * @param kunde Kunde, dem der Warenkorb zugewiesen wurde
     * @return Map mit den aktuellen Artikeln im Warenkorb
     */
    public Warenkorb getWarenkorb(Kunde kunde){
        warenkorb = kunde.getWarkorb();
        return warenkorb;
    }

    /**
     *Methode zum Löschen des gesamten Warenkorbs
     * @param kunde Kunde, dem der Warenkorb zugewiesen wurde
     */
    public void warenkorbLoeschen(Kunde kunde){
        warenkorb = kunde.getWarkorb();
        warenkorb.setGesamtpreis(0);
        warenkorb.getWarenkorbListe().clear();
    }

    /**
     * Methode zum Abschließen des Einkaufs
     * @param kunde Kunde, dem der Warenkorb zugewiesen wurde
     * @return Aufrufen der erstelle Rechnung methode
     * @throws ArtikelbestandUnterNullException wenn eines der gekauften Artikel nicht mehr zu kaufen ist, da die Menge im Warenkorb größer als die im Lager ist
     * @throws WarenkorbLeerException wenn sich keine Artikel im Warenkorb befinden
     */
    public String einkaufAbschliessen(Kunde kunde) throws ArtikelbestandUnterNullException, WarenkorbLeerException {
        // TODO rechnung muss noch erstellt werden
        warenkorb = kunde.getWarkorb();

        if(warenkorb.getWarenkorbListe().isEmpty()){
            throw new WarenkorbLeerException();
        }

        for (Map.Entry<Artikel, Integer> entry: warenkorb.getWarenkorbListe().entrySet()) {
            if((entry.getKey().getBestand() - entry.getValue()) < 0){ //überprüft ob der Artikelbestand beim abschliessen des Kaufes immer noch über den Lagerbestand liegt(vllt war ein anderer Kunde schneller)
                warenkorb.getWarenkorbListe().remove(entry.getKey()); // entfernt den nicht vorhandenen artikel aus dem warenkorb
                throw new ArtikelbestandUnterNullException(entry.getKey(), " DU WARST ZU LANGSAM BEIM EINKAUF. NICE TRY! ¯\\_(ツ)_/¯");
            }
            entry.getKey().setBestand(entry.getKey().getBestand() - entry.getValue());

            

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

        //getWarenkorb(kunde); // zum berechnen des gesamtpreises

        rechnungGesamtpreis = "\n\n\tGesamtpreis: " + df.format(kunde.getWarkorb().getGesamtpreis()) + "EUR";

        rechnung = rechnungKunde + rechnungArtikel + rechnungGesamtpreis;

        warenkorbLoeschen(kunde);

        return rechnung;
    }

}
