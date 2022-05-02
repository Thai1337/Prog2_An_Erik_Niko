package eShop.ui.cui;

import eShop.domain.Eshop;
import eShop.domain.exceptions.ArtikelExistiertBereitsException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 * Klasse für sehr einfache Benutzungsschnittstelle für den
 * E-Shop. Die Benutzungsschnittstelle basiert auf Ein-
 * und Ausgaben auf der Kommandozeile, daher der Name CUI
 * (Command line User Interface).
 *
 *
 */
public class EshopClientCUI {

    private Eshop shop;
    private BufferedReader in;

    public EshopClientCUI() {

        shop = new Eshop();

        in = new BufferedReader(new InputStreamReader(System.in));
    }
    /* (non-Javadoc)
     *
     * Interne (private) Methode zur Ausgabe des Menüs.
     */
    private void gibMenueAus() {

        System.out.println("\nBefehle:R = Registrieren");
        System.out.println("Befehle:A = Anmelden");
        System.out.println("Befehle:B = Artikel Ausgeben");
        System.out.println("Befehle:C = Artikel Einfügen");
        System.out.println("Befehle:q = Programm Beenden");
    }

    /* (non-Javadoc)
     *
     * Interne (private) Methode zum Einlesen von Benutzereingaben.
     */
    public String einlesen() throws IOException {
        //einlesen der Nutzereingaben
        return in.readLine();
    }
    /**
     * Methode zur Ausführung der Hauptschleife:
     * - Menü ausgeben
     * - Eingabe des Benutzers einlesen
     * - Eingabe verarbeiten und Ergebnis ausgeben
     * (EVA-Prinzip: Eingabe-Verarbeitung-Ausgabe)
     */
    public void run(){
        String input = "";
        do{
            gibMenueAus();
            try{
                input = einlesen();
                verarbeiteEingabe(input);
            }catch (IOException e){
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
    private void verarbeiteEingabe(String line) throws IOException {
        String nummer, bezeichnung, bestand;
        int nr, bst;
        Vector liste;

        switch (line) {
            case "B":
                System.out.println("");
                liste = shop.gibAlleArtikel();
                gibArtikellisteAus(liste);
                break;
            case "C":
                System.out.println("");
                System.out.print("Artikelnummer > ");
                nummer = einlesen();
                nr = Integer.parseInt(nummer);
                System.out.print("Artikelbezeichnung  > ");
                bezeichnung = einlesen();
                System.out.print("Artikelbestand  > ");
                bestand = einlesen();
                bst = Integer.parseInt(bestand);

                try {
                    shop.fuegeArtikelEin(nr, bezeichnung, bst);
                    System.out.println("Einfügen ok");
                } catch (ArtikelExistiertBereitsException e) {
                    // Hier Fehlerbehandlung...
                    System.out.println("Fehler beim Einfügen");
                    e.printStackTrace();
                }

                break;
        }



    }

    /* (non-Javadoc)
     *
     * Interne (private) Methode zum Ausgeben des Artikel Vektors.
     *
     */
    private void gibArtikellisteAus(Vector liste) {
        if (liste.isEmpty()) {
            System.out.println("Liste ist leer.");
        } else {
            // Durchlaufen des Vectors mittels for each-Schleife
            // (alternativ: Iterator)
            for (Object artikel: liste) {
                System.out.println(artikel);
            }
        }
    }

    /**
     * Die main-Methode...
     */
    public static void main(String[] args) {

        EshopClientCUI cui;
        try {
            cui = new EshopClientCUI();
            cui.run();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }



}
