package eshop.ui.cui;

import eshop.domain.Eshop;
import eshop.domain.exceptions.ArtikelbestandUnterNullException;

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
    public KundenMenue(){
        eingabeAusgabe = new EA();

        shop = new Eshop();

    }
    /* (non-Javadoc)
     *
     * Interne (private) Methode zur Ausgabe des Kunden-Menüs.
     */
    public void gibKundenMenueAus() {
        System.out.println("\n(a) = Artikel ausgeben");
        System.out.println("(b) = Artikel suchen");
        System.out.println("(c) = Warenkorb ausgeben");
        System.out.println("(d) = Artikel zum Warenkorb hinzufügen");
        System.out.println("(e) = Artikel aus dem Warenkorb entfernen");
        System.out.println("(q) = Ausloggen");
        System.out.print("Eingabe --> ");
    }
    /**
     * Methode zur Ausführung der Hauptschleife:
     * - Kunden-Menü ausgeben
     * - Eingabe des Benutzers einlesen
     * - Eingabe verarbeiten und Ergebnis ausgeben
     * (EVA-Prinzip: Eingabe-Verarbeitung-Ausgabe)
     */
    public void run(){
        String input = "";
        do{
            gibKundenMenueAus();
            try{
                input = eingabeAusgabe.einlesen();
                verarbeiteKundenEingabe(input);
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
    private void verarbeiteKundenEingabe(String line) throws IOException, ArtikelbestandUnterNullException {
        String nummer, bezeichnung, bestand;
        int nr, bst;
        List liste;

        switch (line) {
            case "a":
                System.out.println("");
                System.out.println("Kunden Menue");
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

                break;
            case"d":

                break;
            case"e":

                break;
        }
    }



}