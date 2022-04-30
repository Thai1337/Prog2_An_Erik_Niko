package eShop.ui.cui;

import eShop.domain.Eshop;
import eShop.valueobjects.Artikel;
import eShop.valueobjects.Kunde;
import java.util.Scanner;
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

    private void gibMenueAus() {

        System.out.println("\nBefehle:R = Registrieren");
        System.out.println("Befehle:A = Anmelden");
        System.out.println("Befehle:B = Artikel Ausgeben");
        System.out.println("Befehle:q = Programm Beenden");
    }
    public String einlesen() throws IOException {
        //einlesen der Nutzereingaben
        return in.readLine();
    }
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
    public void verarbeiteEingabe(String line) {
        String nummer;
        Vector liste;

        int nr;
        switch (line) {
            case "B":
                System.out.println("");
                liste = shop.gibAlleArtikel();
                gibArtikellisteAus(liste);
                break;
        }



    }

    private void gibArtikellisteAus(Vector liste) {
        if (liste.isEmpty()) {
            System.out.println("Liste ist leer.");
        } else {
            // Durchlaufen des Vectors mittels for each-Schleife
            // (alternativ: Iterator)
            for (Object Artikel: liste) {
                System.out.println(Artikel);
            }
        }
    }


    public static void main(String[] args) {

        /*EshopClientCUI shop = new EshopClientCUI();
        shop.gibMenueAus();
        shop.einlesen();*/

        Artikel A1 = new Artikel(1,"Holz",100);

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
