package eshop.domain;

import eshop.valueobjects.Kunde;
import eshop.valueobjects.Mitarbeiter;

import java.util.List;
import java.util.Vector;

public class Kundenverwaltung {

    List<Kunde> kundenListe = new Vector<>();
    public Kundenverwaltung(){
        Kunde k1 = new Kunde("Bea", "Bley Strasse", "passwortB");
        Kunde k2 = new Kunde("Luggas", "Luggas Allee", "passwortL");
        Kunde k3 = new Kunde("Lisa", "Lisa Strasse", "passwortL");

        kundenListe.add(k1);
        kundenListe.add(k2);
        kundenListe.add(k3);
    }


    /**
     * Methode, womit Kunden sich selbst registrieren können und diesen Kunden in einen Vektor speichern
     * @param einKunde Kunde
     * @return gibt die Kundennummer des erstellten Kunden Zurück
     */
    public int erstelleKunde(Kunde einKunde){
        //Methode zum Erstellen von Kunden
        kundenListe.add(einKunde);
        return einKunde.getNummer();
    }

    /**
     * Methode zum Anmelden von Kunden, welche Kundennummern und Passwörter überprüft, ob sie in dem
     * Vektor enthalten sind
     * @param nummer übernimmt den Wert, welcher eingegeben wurde
     * @param passwort übernimmt den Wert, welcher eingegeben wurde
     * @return Ein Boolischenwert, welcher True ist, wenn der Kunde im System ist oder False, wenn dieser nicht im System ist
     */
    public boolean kundeAnmelden(int nummer, String passwort){
        //Vergleicht Eingabe mit den Werten aus der Mitarbeiterliste
        for(Kunde k : kundenListe){
            if(k.getNummer() == nummer && k.getPasswort().equals(passwort)){
                return true;
            }
        }
        return false;
    }



}
