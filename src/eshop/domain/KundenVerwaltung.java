package eshop.domain;

import eshop.valueobjects.Kunde;
import eshop.valueobjects.Mitarbeiter;

import java.util.List;
import java.util.Vector;

public class KundenVerwaltung {

    List<Kunde> kundenList = new Vector<>();
    public KundenVerwaltung(){
        Kunde k1 = new Kunde("Bea");
    }


    /**
     * Methode womit Mitarbeiter andere Mitarbeiter erstellen können und diese in einen Vektor speichern
     * @param einMitarbeiter Mitarbeiter
     * @return gibt die Mitarbeiternummer des erstellten Mitarbeiters Zurück
     */
    public int erstelleKunde(Kunde einKunde){
        //Methode zum Erstellen von Kunden
        kundenListe.add(einKunde);
        return einKunde.getNummer();
    }

    /**
     * Methode zum Anmelden von Mitarbeiter, welche Mitarbeiternummern und passwörter ueberpueft, ob sie in dem
     * Vektor entahlten sind
     * @param nummer übernimmt den Wert, welcher eingegben wurde
     * @param passwort übernimmt den Wert, welcher eingegben wurde
     * @return Ein Boolischenwert, welcher True ist, wenn der Mitarbeiter im System ist oder False, wenn dieser nicht im System ist
     */
    public boolean mitarbeiterAnmelden(int nummer, String passwort){
        //Vergleicht Eingabe mit den Werten aus der Mitarbeiterliste
        for(Kunde k : kundenListe){
            if(k.getNummer() == nummer && k.getPasswort().equals(passwort)){
                return true;
            }
        }
        return false;
    }



}
