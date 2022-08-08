package eshop.domain;

import eshop.domain.exceptions.EingabeNichtLeerException;
import eshop.persistence.ListenPersistence;
import eshop.valueobjects.Mitarbeiter;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class Mitarbeiterverwaltung {

    private List<Mitarbeiter> mitarbeiterListe = new Vector();

    private ListenPersistence<Mitarbeiter> persistence;

    /**
     * Konstruktor welcher Mitarbeiter erstellt und der Vector der mitarbeiterListe hinzufügt
     */
    public Mitarbeiterverwaltung() {
        // TODO wenn persistenz da löschen
        /*Mitarbeiter m1 = new Mitarbeiter("Niko", "passwortN");
        Mitarbeiter m2 = new Mitarbeiter("An", "passwortA");
        Mitarbeiter m3 = new Mitarbeiter("Erik", "passwortE");

        mitarbeiterListe.add(m1);
        mitarbeiterListe.add(m2);
        mitarbeiterListe.add(m3);*/
        persistence = new ListenPersistence<Mitarbeiter>("mitarbeiter");
    }

    /**
     * Liest die Mitarbeiter aus der mitarbeiter.txt Datei ein und lädt sie in einen Vektor
     * @throws IOException
     */
    public void liesMitarbeiter() throws IOException {
        mitarbeiterListe = persistence.ladenListe();
    }

    /**
     * Schreibt den Mitarbeiter aus dem Vektor in die mitarbeiter.txt Datei
     * @throws IOException
     */
    public void schreibMitarbeiter() throws IOException {
        persistence.speichernListe(mitarbeiterListe);
    }

    /**
     * Gibt die höchste Mitarbeiternummer zurück, welche gespeichert wurde bzw. vom letzten Mitarbeiter in der Liste
     * @return Mitarbeiternummer des letzten Mitarbeiters
     * @throws IOException
     */
    public int getNummerVomLetztenMitarbeiter() throws IOException {
        liesMitarbeiter();
        return mitarbeiterListe.get(mitarbeiterListe.size() - 1).getNummer();
    }

    /**
     * Methode womit Mitarbeiter andere Mitarbeiter erstellen können und diese in einen Vektor speichern
     *
     * @param einMitarbeiter Mitarbeiter
     * @return gibt die Mitarbeiternummer des erstellten Mitarbeiters Zurück
     * @throws EingabeNichtLeerException wenn bei der erstellung eines Mitarbeiters kein Name oder Passwort angegeben wird
     */
    public int erstelleMitarbeiter(Mitarbeiter einMitarbeiter) throws EingabeNichtLeerException, IOException {
        //Methode zum Erstellen von Mitarbeitern
        if (einMitarbeiter.getName().isEmpty() || einMitarbeiter.getPasswort().isEmpty()) {
            throw new EingabeNichtLeerException();
        }
        mitarbeiterListe.add(einMitarbeiter);
        schreibMitarbeiter();
        return einMitarbeiter.getNummer();
    }

    /**
     * Methode zum Anmelden von Mitarbeiter, welche Mitarbeiternummern und passwörter ueberpueft, ob sie in dem
     * Vektor entahlten sind
     *
     * @param nummer   übernimmt den Wert, welcher eingegben wurde
     * @param passwort übernimmt den Wert, welcher eingegben wurde
     * @return Ein Boolischenwert, welcher True ist, wenn der Mitarbeiter im System ist oder False, wenn dieser nicht im System ist
     */
    public Mitarbeiter mitarbeiterAnmelden(int nummer, String passwort) {
        //Vergleicht Eingabe mit den Werten aus der Mitarbeiterliste
        for (Mitarbeiter mitarbeiter : mitarbeiterListe) {
            if (mitarbeiter.getNummer() == nummer && mitarbeiter.getPasswort().equals(passwort)) {
                return mitarbeiter;
            }
        }
        //throw new AnmeldungFehlgeschlagenException(" Ungueltige Anmeldedaten!"); // musste geändert werden wegen der neuen anmeldung
        return null;
    }
}
