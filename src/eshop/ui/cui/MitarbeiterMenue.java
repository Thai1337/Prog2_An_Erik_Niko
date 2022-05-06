package eshop.ui.cui;

import eshop.domain.Eshop;
import eshop.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.domain.exceptions.ArtikelbestandUnterNullException;

import java.io.IOException;
import java.util.List;
/**
 * Klasse für das Anmelden der Mitarbeiter in einem CUI, welche zum Verarbeiten der Eingaben und Ausgaben genutzt wird.
 *
 *
 */
public class MitarbeiterMenue {

    private EA eingabeAusgabe;
    private Eshop shop;
    public MitarbeiterMenue(){
        eingabeAusgabe = new EA();
        shop = new Eshop();
    }
    /* (non-Javadoc)
     *
     * Interne (private) Methode zur Ausgabe des Mitarbeiter-Menüs.
     */
    private void gibMitarbeiterMenueAus() {
        System.out.println("\n(a) = Artikel ausgeben");
        System.out.println("(b) = Artikel suchen");
        System.out.println("(c) = Artikel einfügen");
        System.out.println("(d) = Artikelbestand aendern");
        System.out.println("(e) = Artikel loeschen");
        System.out.println("(q) = Ausloggen");
        System.out.print("Eingabe --> ");
    }
    /**
     * Methode zur Ausführung der Hauptschleife:
     * - Mitarbeiter-Menü ausgeben
     * - Eingabe des Benutzers einlesen
     * - Eingabe verarbeiten und Ergebnis ausgeben
     * (EVA-Prinzip: Eingabe-Verarbeitung-Ausgabe)
     */
    public void run(){
        String input = "";
        do{
            gibMitarbeiterMenueAus();
            try{
                input = eingabeAusgabe.einlesen();
                verarbeiteMitarbeiterEingabe(input);
            }catch (IOException | ArtikelbestandUnterNullException e){
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }while(!input.equals("q"));
    }
    /* (non-Javadoc)
     *
     * Interne (private) Methode zur Verarbeitung von Eingaben
     * und Ausgabe von Ergebnissen.
     */
    private void verarbeiteMitarbeiterEingabe(String line) throws IOException, ArtikelbestandUnterNullException {
        String nummer, bezeichnung, bestand;
        int nr, bst;
        List liste;

        switch (line) {
            case "a":
                System.out.println("");
                System.out.println("Mitarbeiter Menue");
                System.out.println("(0): Unsortiert");
                System.out.println("(1): Absteigend nach Bezeichnung");
                System.out.println("(2): Absteigend nach Nummer");
                System.out.println("(3): Aufsteigend nach Bezeichnung");
                System.out.println("(4): Aufsteigend nach Nummer\n");
                System.out.print("Listen Sortierung --> ");

                try{
                    nummer = eingabeAusgabe.einlesen();
                    nr = Integer.parseInt(nummer);
                    System.out.println("");
                    liste = shop.gibAlleArtikel(nr);
                    eingabeAusgabe.gibListeAus(liste);
                }catch (NumberFormatException e){
                    System.out.println("Fehler bei der Eingabe: Bitte nur Zahlen sind gueltig");
                    //e.printStackTrace();
                }


                break;
            case "b":
                System.out.print("Artikelbezeichnung  --> ");
                bezeichnung = eingabeAusgabe.einlesen();

                liste = shop.sucheNachbezeichnung(bezeichnung);
                eingabeAusgabe.gibListeAus(liste);
                break;
            case "c":
                System.out.println("");
                System.out.print("Artikelnummer --> ");
                nummer = eingabeAusgabe.einlesen();
                nr = Integer.parseInt(nummer);
                System.out.print("Artikelbezeichnung  --> ");
                bezeichnung = eingabeAusgabe.einlesen();
                System.out.print("Artikelbestand  --> ");
                bestand = eingabeAusgabe.einlesen();
                bst = Integer.parseInt(bestand);

                try {
                    shop.fuegeArtikelEin(nr, bezeichnung, bst);
                    System.out.println("Einfügen ok");
                } catch (ArtikelExistiertBereitsException e) {
                    // Hier Fehlerbehandlung...
                    System.out.println("Fehler beim Einfügen");
                    //e.printStackTrace();
                }
                break;
            case"d":
                try {
                    System.out.println("");
                    System.out.print("Artikelnummer --> ");
                    nummer = eingabeAusgabe.einlesen();
                    nr = Integer.parseInt(nummer);
                    System.out.print("Artikelbezeichnung  --> ");
                    bezeichnung = eingabeAusgabe.einlesen();
                    System.out.print("Artikelbestand aender zu --> ");
                    bestand = eingabeAusgabe.einlesen();
                    bst = Integer.parseInt(bestand);

                    shop.aendereArtikelbestand(bezeichnung, nr, bst);
                }catch (IOException | NumberFormatException e){
                    System.out.println("Fehler bei der Eingabe");
                    //e.printStackTrace();
                }
                break;
            case"e":
                try {
                    System.out.println("");
                    System.out.print("Artikelnummer --> ");
                    nummer = eingabeAusgabe.einlesen();
                    nr = Integer.parseInt(nummer);
                    System.out.print("Artikelbezeichnung  --> ");
                    bezeichnung = eingabeAusgabe.einlesen();

                    shop.loescheArtikel(bezeichnung, nr);
                }catch (IOException | NumberFormatException e){
                    System.out.println("Fehler bei der Eingabe");
                    //e.printStackTrace();
                }

                break;
        }
    }
}
