package eshop.domain;

import eshop.domain.exceptions.EingabeNichtLeerException;
import eshop.valueobjects.Adresse;
import eshop.valueobjects.Kunde;
import eshop.valueobjects.Mitarbeiter;

import java.util.List;
import java.util.Vector;

public class Kundenverwaltung {

    List<Kunde> kundenListe = new Vector<>();
    public Kundenverwaltung(){

        Adresse a1 = new Adresse("Ballermann", 14, 28816);
        Adresse a2 = new Adresse("An den Ruschen", 21, 28817);
        Adresse a3 = new Adresse("Moselstraße", 28, 28818);

        Kunde k1 = new Kunde("Bea", a1, "passwortB");
        Kunde k2 = new Kunde("Luggas", a2, "passwortL");
        Kunde k3 = new Kunde("Lisa", a3, "passwortL");

        kundenListe.add(k1);
        kundenListe.add(k2);
        kundenListe.add(k3);
    }


    /**
     * Methode, womit Kunden sich selbst registrieren können und diesen Kunden in einen Vektor speichern
     * @param einKunde Kunde
     * @return gibt die Kundennummer des erstellten Kunden Zurück
     */
    public int erstelleKunde(Kunde einKunde) throws EingabeNichtLeerException {
        //Methode zum Erstellen von Kunden
        if(einKunde.getName().isEmpty() || einKunde.getPasswort().isEmpty() || einKunde.getAdresse().getStrasse().isEmpty()
                ||  einKunde.getAdresse().getHomeNumber() <= -1 || einKunde.getAdresse().getPlz() <= -1){
            throw new EingabeNichtLeerException();
        }
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
    public Kunde kundeAnmelden(int nummer, String passwort){
        //Vergleicht Eingabe mit den Werten aus der Mitarbeiterliste
        for(Kunde k : kundenListe){
            if(k.getNummer() == nummer && k.getPasswort().equals(passwort)){
                return k;
            }
            // TODO throw exception wenn kein Kunde vorhanden
        }
        return null;
    }



}
