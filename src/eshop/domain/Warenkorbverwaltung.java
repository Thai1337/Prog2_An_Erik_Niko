package eshop.domain;

import eshop.domain.exceptions.ArtikelbestandUnterNullException;
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
//todo Messages ob erfolgreich hinzugefügt wurde

    /**
     * Übergibt den Warenkorb
     * @param artikel der Artikel, welcher zum Warenkorb hinzugefügt wird
     * @param anzahlArtikel Anzahl vom hinzugefügten Artikel
     * @param kunde Kunde, dem der Warenkorb zugewiesen wurde
     * @throws ArtikelbestandUnterNullException
     */
    public void artikelZuWarenkorbHinzufuegen(Artikel artikel, int anzahlArtikel, Kunde kunde) throws ArtikelbestandUnterNullException {
        warenkorb = kunde.getWarkorb();
// TODO keine Negativen werte
//  fehlermeldung bei eingabe einer nicht vorhandenen nummer

        //anzahlArtikel > wasimwarenkorbsteht

        if(warenkorb.getWarenkorbListe().containsKey(artikel)){
            // TODO
             int alteWarenkorbMenge = warenkorb.getWarenkorbListe().get(artikel); // gibt eine Nullpointerexception wenn außerhalb des ifs, weil versucht wird auf einen leeren artikel zuzugreifen, falls diese nichts existierts(deswegen containsKey)

            if((anzahlArtikel + alteWarenkorbMenge) > artikel.getBestand()) { // Wenn die Menge im Warenkorb größer ist als der Bestand des Artikels im Lager throw exception
                throw new ArtikelbestandUnterNullException(artikel, " Die Menge dieses Arikels in Ihrem Warenkorb ist höher als der Bestand im Shop");
            }

             warenkorb.setWarenkorbListe(artikel, anzahlArtikel + alteWarenkorbMenge);
        }else{

            if(anzahlArtikel > artikel.getBestand()){
                throw new ArtikelbestandUnterNullException(artikel, " Die Menge dieses Artikels in Ihrem Warenkorb ist höher als der Bestand im Shop");
            }

            warenkorb.setWarenkorbListe(artikel, anzahlArtikel);
        }
        //kunde.setMeinWarenkorb(warenkorb);
    }

    /**
     * Methode, die einen Artikel aus dem Warenkorb entfernt
     * @param artikel bestimmter Artikel
     * @param anzahlArtikel Anzahl vom Artikel
     * @param kunde Kunde, dem der Warenkorb zugewiesen wurde
     * @throws ArtikelbestandUnterNullException
     */
    public void artikelAusWarenkorbEntfernen(Artikel artikel, int anzahlArtikel, Kunde kunde) throws ArtikelbestandUnterNullException {
        warenkorb = kunde.getWarkorb();

        if(warenkorb.getWarenkorbListe().containsKey(artikel)){ // das wird benötigt, weil wenn man einen im "Lager" vorhandenen artikel entfernen will, dieser aber nicht im Warenkorb existiert gibt es eine NullPointerException
            int alteWarenkorbMenge = warenkorb.getWarenkorbListe().get(artikel);

            // TODO vielleicht noch ändern, dass wenn alterWert - anzahlArtikel <= 0 der Artikel entfernt wird. Tutor fragen ob das mehr Sinn macht, würde die unter exception überflüssig machen
            if(alteWarenkorbMenge - anzahlArtikel < 0){
                throw new ArtikelbestandUnterNullException(artikel, " Der Bestand ihres Warenkorbs darf nicht unter 0 fallen!");
            }

            if(anzahlArtikel == 0 || (alteWarenkorbMenge - anzahlArtikel) == 0){ // wenn die eingegebene Menge im warenkorb 0 ist oder das ergebnis z.B. 92-92 0 wird, wird der Artikel entfernt
                warenkorb.getWarenkorbListe().remove(artikel);
            }else{ // sonst wird die Menge des Artikels im Warenkorb geändert
                warenkorb.setWarenkorbListe(artikel,  alteWarenkorbMenge - anzahlArtikel);
            }
        }
    }

    /**
     * Methode welche die Map übergibt und den Gesammtpreis berechnet
     * @param kunde Kunde, dem der Warenkorb zugewiesen wurde
     * @return Map mit den aktuellen Artikeln im Warenkorb
     */
    public Warenkorb getWarenkorb(Kunde kunde){
        warenkorb = kunde.getWarkorb();

        double gesamtpreis = 0;

        for (Map.Entry<Artikel, Integer> entry: warenkorb.getWarenkorbListe().entrySet()) {
            gesamtpreis += entry.getValue() * entry.getKey().getPreis(); //Berechnung für den Gesamtpreis, welcher für die Rechnung nötig ist
        }
        warenkorb.setGesamtpreis(gesamtpreis);
        return warenkorb;
    }

    /**
     *Methode zum Löschen des gesamten Warenkorbs
     * @param kunde Kunde, dem der Warenkorb zugewiesen wurde
     */
    public void warenkorbLoeschen(Kunde kunde){
        warenkorb = kunde.getWarkorb();
        warenkorb.getWarenkorbListe().clear();
    }

    /**
     * Methode zum Abschließen des Einkaufs
     * @param kunde Kunde, dem der Warenkorb zugewiesen wurde
     * @return Aufrufen der erstelle Rechnung methode
     * @throws ArtikelbestandUnterNullException
     */
    public String einkaufAbschliessen(Kunde kunde) throws ArtikelbestandUnterNullException {
        // TODO rechnung muss noch erstellt werden
        warenkorb = kunde.getWarkorb();

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

        getWarenkorb(kunde); // zum berechnen des gesamtpreises

        rechnungGesamtpreis = "\n\n\tGesamtpreis: " + df.format(kunde.getWarkorb().getGesamtpreis()) + "EUR";

        rechnung = rechnungKunde + rechnungArtikel + rechnungGesamtpreis;

        warenkorbLoeschen(kunde);

        return rechnung;
    }

}
