package eshop.ui.cui;

import eshop.domain.Eshop;
import eshop.domain.exceptions.ArtikelbestandUnterNullException;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.Kunde;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * Klasse für das Anmelden der Kunden in einem CUI, welche zum Verarbeiten der Eingaben und Ausgaben genutzt wird.
 *
 *
 */
public class KundenMenue {

    private EA eingabeAusgabe;
    private Eshop shop;


    private Kunde kunde;

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public KundenMenue(Eshop shop){
        eingabeAusgabe = new EA();

        this.shop = shop;
    }
    /* (non-Javadoc)
     *
     * Interne (private) Methode zur Ausgabe des Kunden-Menüs.
     */
    // TODO 5-7 sind noch nicht Fertig
    // TODO Michelles Anmeldungsidee umsetzen
    // TODO Optional: Eine gemeinsame Anmeldung für Kunden und Mitarbeiter
    public void gibKundenMenueAus() {
        System.out.println("\n(1) = Artikel: ausgeben");
        System.out.println("(2) = Artikel: suchen");
        System.out.println("(3) = Warenkorb: ausgeben");
        System.out.println("(4) = Warenkorb: Artikel hinzufuegen");
        System.out.println("(5) = Warenkorb: Artikel entfernen ");
        System.out.println("(6) = Warenkorb: leeren");
        System.out.println("(7) = Einkauf abschliessen");
        System.out.println("(0) = Ausloggen");
        System.out.print("\nEingabe --> ");
    }
    /**
     * Methode zur Ausführung der Hauptschleife:
     * - Kunden-Menü ausgeben
     * - Eingabe des Benutzers einlesen
     * - Eingabe verarbeiten und Ergebnis ausgeben
     * (EVA-Prinzip: Eingabe-Verarbeitung-Ausgabe)
     */
    public void run(){
        int input = -1;

        do{
            gibKundenMenueAus();
            try{
                input = eingabeAusgabe.einlesenInteger();
                verarbeiteKundenEingabe(input);
            }catch (IOException | ArtikelbestandUnterNullException e){
                // TODO Auto-generated catch block
                System.out.println(e.getMessage());
            }

        }while(input != 0);
    }
    /* (non-Javadoc)
     *
     * Interne (private) Methode zur Verarbeitung von Eingaben
     * und Ausgabe von Ergebnissen.
     */
    private void verarbeiteKundenEingabe(int line) throws IOException, ArtikelbestandUnterNullException {
        String nummer, bezeichnung, bestand;
        int nr, bst, artikelnummer, anzahlArtikel;
        List liste;
        //TODO eigenes Menü für Artikel erstellen
        //TODO Passwort ändern
        //TODO Kunden daten einsehen
        switch (line) {
            case 1:
                System.out.println("");
                System.out.println("Art der Sortierung");
                System.out.println("(0): Unsortiert");
                System.out.println("(1): Aufsteigend nach Bezeichnung");
                System.out.println("(2): Aufsteigend nach Nummer");
                System.out.println("(3): Absteigend nach Bezeichnung");
                System.out.println("(4): Absteigend nach Nummer\n");
                System.out.print("Listen Sortierung --> ");

                nr = eingabeAusgabe.einlesenInteger();
                System.out.println("");
                liste = shop.gibAlleArtikel(nr);
                eingabeAusgabe.gibListeAus(liste);

                break;
            case 2:
                System.out.print("Artikelbezeichnung  --> ");
                bezeichnung = eingabeAusgabe.einlesenString();

                liste = shop.sucheNachbezeichnung(bezeichnung);
                eingabeAusgabe.gibListeAus(liste);
                break;
            case 3:
                DecimalFormat df = new DecimalFormat("0.00");

                Map<Artikel, Integer> test = shop.getWarenkorb(kunde).getWarenkorbListe();

                System.out.println("\nWarenkorb:");
                for (Map.Entry<Artikel, Integer> entry : test.entrySet()) {
                    System.out.println("Artikelnummer: " + entry.getKey().getNummer()+ " | Name: " + entry.getKey().getBezeichnung() + " | Stueckpreis: " + df.format(entry.getKey().getPreis()) + "EUR | Menge: " + entry.getValue() + " | Preis: " + df.format(entry.getValue()*entry.getKey().getPreis()) + "EUR");
                }

                System.out.println("Gesamtpreis: " + df.format(shop.getWarenkorb(kunde).getGesamtpreis()) + "EUR");

                break;
            case 4:
                // TODO negative zahlen
                System.out.print("Geben Sie die Artikelnummer ein, von dem Artikel den Sie hinzufuegen möchten --> ");
                artikelnummer = eingabeAusgabe.einlesenInteger();
                System.out.print("Geben Sie die gewuenschte Bestellmenge an --> ");
                anzahlArtikel = eingabeAusgabe.einlesenInteger();

                shop.artikelZuWarenkorb(artikelnummer, anzahlArtikel, kunde);
                break;
            case 5:
                System.out.print("Geben Sie die Artikelnummer ein, von dem Artikel den Sie entfernen moechten --> ");
                artikelnummer = eingabeAusgabe.einlesenInteger();
                System.out.print("Geben Sie die gewuenschte Menge an, welche Sie entfernen moechten / geben Sie 0 ein und der Artikel wird entfernt --> ");
                anzahlArtikel = eingabeAusgabe.einlesenInteger();


                shop.artikelAusWarenkorbEntfernen(artikelnummer, anzahlArtikel, kunde);

                break;
            case 6:

                shop.warenkorbLoeschen(kunde);

                break;
            case 7:
                System.out.print("Moechten Sie den Kauf abschliessen?\nGeben Sie J oder j ein --> ");
                if(eingabeAusgabe.einlesenString().equalsIgnoreCase("j")){

                    shop.einkaufAbschliessen(kunde);

                }
                break;
        }
    }



}