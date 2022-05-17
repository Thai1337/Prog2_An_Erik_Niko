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
        System.out.println("\n(1) = Artikel ausgeben");
        System.out.println("(2) = Artikel suchen");
        System.out.println("(3) = Artikel einfuegen");
        System.out.println("(4) = Artikelbestand aendern");
        System.out.println("(5) = Artikel loeschen");
        System.out.println("(6) = Mitarbeiter hinzufuegen");
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
            }catch (IOException | ArtikelbestandUnterNullException | EingabeNichtLeerException e){
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
    private void verarbeiteMitarbeiterEingabe(int line) throws IOException, ArtikelbestandUnterNullException, EingabeNichtLeerException {
        String nummer, bezeichnung, bestand, name, passwort;
        int nr, bst;
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
                System.out.print("Artikelnummer --> ");
                nr = eingabeAusgabe.einlesenInteger();
                System.out.print("Artikelbezeichnung  --> ");
                bezeichnung = eingabeAusgabe.einlesenString();
                System.out.print("Artikelbestand  --> ");
                bst = eingabeAusgabe.einlesenInteger();

                try {
                    shop.fuegeArtikelEin(nr, bezeichnung, bst);
                    System.out.println("Einfuegen ok");
                } catch (ArtikelExistiertBereitsException e) {
                    // Hier Fehlerbehandlung...
                    System.out.println(e.getMessage());
                    //e.printStackTrace();
                }
                break;
            case 4:

                System.out.println("");
                System.out.print("Artikelnummer --> ");
                //nummer = eingabeAusgabe.einlesenString();
                nr = eingabeAusgabe.einlesenInteger();
                System.out.print("Artikelbezeichnung  --> ");
                bezeichnung = eingabeAusgabe.einlesenString();
                System.out.print("Artikelbestand aendern zu --> ");
                bst = eingabeAusgabe.einlesenInteger();

                try {
                    shop.aendereArtikelbestand(bezeichnung, nr, bst);
                }catch (ArtikelbestandUnterNullException e){
                    System.out.println(e.getMessage());
                }


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
        }
    }
}
