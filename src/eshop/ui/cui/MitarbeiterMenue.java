package eshop.ui.cui;

import eshop.domain.Eshop;
import eshop.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.domain.exceptions.ArtikelbestandUnterNullException;
import eshop.domain.exceptions.EingabeNichtLeerException;

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
    public MitarbeiterMenue(Eshop shop){
        eingabeAusgabe = new EA();
        this.shop = shop;
    }
    /* (non-Javadoc)
     *
     * Interne (private) Methode zur Ausgabe des Mitarbeiter-Menüs.
     */
    private void gibMitarbeiterMenueAus() {
        System.out.println("\n(1) = Artikel: ausgeben");
        System.out.println("(2) = Artikel: suchen");
        System.out.println("(3) = Artikel: einfuegen");
        System.out.println("(4) = Artikel: bearbeiten");
        System.out.println("(5) = Artikel: loeschen");
        System.out.println("(6) = Mitarbeiter: hinzufuegen");
        System.out.println("(7) = Änderungsprotokoll: anzeigen");
        System.out.println("(0) = Ausloggen");
        System.out.print("\nEingabe --> ");
    }
    /**
     * Methode zur Ausführung der Hauptschleife:
     * - Mitarbeiter-Menü ausgeben
     * - Eingabe des Benutzers einlesen
     * - Eingabe verarbeiten und Ergebnis ausgeben
     * (EVA-Prinzip: Eingabe-Verarbeitung-Ausgabe)
     */
    public void run(){
        int input = -1;
        do{
            gibMitarbeiterMenueAus();
            try{
                input = eingabeAusgabe.einlesenInteger();
                verarbeiteMitarbeiterEingabe(input);
            }catch (IOException | ArtikelbestandUnterNullException | EingabeNichtLeerException |
                    ArtikelExistiertBereitsException e){
                // TODO Auto-generated catch block
                System.out.println("\n" + e.getMessage());
                //e.printStackTrace();
            }

        }while(input != (0));
    }
    /* (non-Javadoc)
     *
     * Interne (private) Methode zur Verarbeitung von Eingaben
     * und Ausgabe von Ergebnissen.
     */
    private void verarbeiteMitarbeiterEingabe(int line) throws IOException, ArtikelbestandUnterNullException, EingabeNichtLeerException, ArtikelExistiertBereitsException {
        String nummer, bezeichnung, bestand, name, passwort;
        int nr, neueNr, bst;
        double preis;
        List liste;
        //TODO eigenes Menü für Artikel erstellen
        //TODO Passwort ändern
        //TODO Mitarbeiter daten einsehen
        //TODO Mitarbeiter verwalten Kunden
        switch (line) {
            case 1:
                System.out.println("");
                System.out.println("Mitarbeiter Menue");
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
                System.out.println("");
                System.out.print("Artikelbezeichnung  --> ");
                bezeichnung = eingabeAusgabe.einlesenString();
                System.out.print("Artikelbestand  --> ");
                bst = eingabeAusgabe.einlesenInteger();
                System.out.print("Artikelpreis  --> ");
                preis = eingabeAusgabe.einlesenDouble();
                shop.fuegeArtikelEin(bezeichnung, bst, preis);
                System.out.println("Einfuegen ok");


                break;
            case 4:

                System.out.println("");
                System.out.print("Artikelnummer --> ");
                nr = eingabeAusgabe.einlesenInteger();
                System.out.print("Neue Bezeichnung (bei keiner Eingabe bleibt die Bezeichnung gleich)  --> ");
                bezeichnung = eingabeAusgabe.einlesenString();
                System.out.print("Neuer Artikelbestand (bei keiner Eingabe bleibt der Artikelbestand gleich) --> ");
                bst = eingabeAusgabe.einlesenInteger();
                System.out.print("Neuer Artikelpreis (bei keiner Eingabe bleibt der Artikelpreis gleich) --> ");
                preis = eingabeAusgabe.einlesenDouble();

                shop.aendereArtikel(bezeichnung, nr, bst, preis);

                break;
            case 5:

                System.out.println("");
                System.out.print("Artikelnummer --> ");
                nr = eingabeAusgabe.einlesenInteger();
                System.out.print("Artikelbezeichnung  --> ");
                bezeichnung = eingabeAusgabe.einlesenString();

                shop.loescheArtikel(bezeichnung, nr);

                break;
            case 6:
                //TODO Password kann geändert werden, da es zuerst von einem anderen Mitarbeiter definiert wird
                System.out.print("\nGeben sie den Namen des Mitarbeiters ein --> ");
                name = eingabeAusgabe.einlesenString();
                System.out.print("Geben sie das temporaere Passwort des Mitarbeiters an --> ");
                passwort = eingabeAusgabe.einlesenString();

                System.out.print("\nDie Mitarbeiternummer von ihrem erstellten Mitarbeiter lautet --> " + shop.erstelleMitarbeiter(name, passwort) + "\n");
                break;
            case 7:

                break;
        }
    }
}
