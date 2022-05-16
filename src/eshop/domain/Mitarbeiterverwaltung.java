package eshop.domain;

import eshop.domain.exceptions.EingabeNichtLeerException;
import eshop.valueobjects.Mitarbeiter;

import java.util.List;
import java.util.Vector;

public class Mitarbeiterverwaltung {

    private List<Mitarbeiter> mitarbeiterListe = new Vector();
    /**
     * Konstruktor welcher Mitarbeiter erstellt und der Vector der mitarbeiterListe hinzufügt
     */
    public Mitarbeiterverwaltung(){
        // TODO wenn persistenz da löschen
        Mitarbeiter m1 = new Mitarbeiter("Niko", "passwortN");
        Mitarbeiter m2 = new Mitarbeiter("An", "passwortA");
        Mitarbeiter m3 = new Mitarbeiter("Erik", "passwortE");

        mitarbeiterListe.add(m1);
        mitarbeiterListe.add(m2);
        mitarbeiterListe.add(m3);
    }
    /**
     * Methode womit Mitarbeiter andere Mitarbeiter erstellen können und diese in einen Vektor speichern
     * @param einMitarbeiter Mitarbeiter
     * @return gibt die Mitarbeiternummer des erstellten Mitarbeiters Zurück
     */
   public int erstelleMitarbeiter(Mitarbeiter einMitarbeiter) throws EingabeNichtLeerException {
       //Methode zum Erstellen von Mitarbeitern
           if(einMitarbeiter.getName().isEmpty() || einMitarbeiter.getPasswort().isEmpty()){
               throw new EingabeNichtLeerException();
           }
           mitarbeiterListe.add(einMitarbeiter);
           return einMitarbeiter.getNummer();
   }
    /**
     * Methode zum Anmelden von Mitarbeiter, welche Mitarbeiternummern und passwörter ueberpueft, ob sie in dem
     * Vektor entahlten sind
     * @param nummer übernimmt den Wert, welcher eingegben wurde
     * @param passwort übernimmt den Wert, welcher eingegben wurde
     * @return Ein Boolischenwert, welcher True ist, wenn der Mitarbeiter im System ist oder False, wenn dieser nicht im System ist
     */
   public Mitarbeiter mitarbeiterAnmelden(int nummer, String passwort){
        //Vergleicht Eingabe mit den Werten aus der Mitarbeiterliste
        for(Mitarbeiter m : mitarbeiterListe){
            if(m.getNummer() == nummer && m.getPasswort().equals(passwort)){
                return m;
            }
        }
           return null;
       }
   }
