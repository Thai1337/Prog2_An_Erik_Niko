package eshop.ui.cui;

import eshop.domain.Eshop;
import eshop.domain.exceptions.ArtikelbestandUnterNullException;
import eshop.valueobjects.Warenkorb;

import java.io.IOException;
import java.util.List;
/**
 * Klasse für das Anmelden der Kunden in einem CUI, welche zum Verarbeiten der Eingaben und Ausgaben genutzt wird.
 *
 *
 */
public class KundenMenue {

    private EA eingabeAusgabe;
    private Eshop shop;
    public KundenMenue(Eshop shop){
        eingabeAusgabe = new EA();

        this.shop = shop;
    }
    /* (non-Javadoc)
     *
     * Interne (private) Methode zur Ausgabe des Kunden-Menüs.
     */
    public void gibKundenMenueAus() {
        System.out.println("\n(1) = Artikel ausgeben");
        System.out.println("(2) = Artikel suchen");
        System.out.println("(3) = Warenkorb ausgeben");
        System.out.println("(4) = Artikel zum Warenkorb hinzufuegen");
        System.out.println("(5) = Artikel aus dem Warenkorb entfernen");
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
                e.printStackTrace();
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
        int nr, bst;
        List liste;
        //TODO eigenes Menü für Artikel erstellen
        //TODO Passwort ändern
        //TODO Kunden daten einsehen
        switch (line) {
            case 1:
                System.out.println("");
                System.out.println("Kunden Menue");
                System.out.println("(0): Unsortiert");
                System.out.println("(1): Aufsteigend nach Bezeichnung");
                System.out.println("(2): Aufsteigend nach Nummer");
                System.out.println("(3): Absteigend nach Bezeichnung");
                System.out.println("(4): Absteigend nach Nummer\n");
                System.out.print("Listen Sortierung --> ");


                nr = eingabeAusgabe.einlesenInteger();
                //nr = Integer.parseInt(nummer);
                System.out.println("");
                liste = shop.gibAlleArtikel(nr);
                eingabeAusgabe.gibListeAus(liste);

                //System.out.println("Fehler bei der Eingabe: Bitte nur Zahlen sind gueltig");
                //e.printStackTrace();

                break;
            case 2:
                System.out.print("Artikelbezeichnung  --> ");
                bezeichnung = eingabeAusgabe.einlesenString();

                liste = shop.sucheNachbezeichnung(bezeichnung);
                eingabeAusgabe.gibListeAus(liste);
                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
        }
    }



}