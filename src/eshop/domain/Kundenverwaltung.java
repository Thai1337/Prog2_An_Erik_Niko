package eshop.domain;

import eshop.domain.exceptions.AnmeldungFehlgeschlagenException;
import eshop.domain.exceptions.EingabeNichtLeerException;
import eshop.persistence.ListenPersistence;
import eshop.persistence.Persistence;
import eshop.valueobjects.Adresse;
import eshop.valueobjects.Kunde;
import eshop.valueobjects.Mitarbeiter;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class Kundenverwaltung {
    /**
     * Konstruktor welcher Kunden erstellt
     */
    private List<Kunde> kundenListe = new Vector<>();
    private ListenPersistence<Kunde> persistence;

    public Kundenverwaltung() {

        /*Adresse a1 = new Adresse("Ballermann", 14, 28816, "Berne");
        Adresse a2 = new Adresse("An den Ruschen", 21, 28817, "Bremen");
        Adresse a3 = new Adresse("Moselstraße", 28, 28818, "Bremen");

        Kunde k1 = new Kunde(3,"Bea", a1, "passwortB");
        Kunde k2 = new Kunde(4,"Luggas", a2, "passwortL");
        Kunde k3 = new Kunde(5,"Lisa", a3, "passwortL");

        kundenListe.add(k1);
        kundenListe.add(k2);
        kundenListe.add(k3);*/
        persistence = new ListenPersistence<Kunde>("kunde");
    }

    public void liesKunden() throws IOException {
        kundenListe = persistence.ladenListe();
    }

    public void schreibKunden() throws IOException {
        persistence.speichernListe(kundenListe);
    }

    public int getNummerVomLetztenKunden() throws IOException {
        liesKunden();
        return kundenListe.get(kundenListe.size() - 1).getNummer();
    }


    /**
     * Methode, womit Kunden sich selbst registrieren können und diesen Kunden in einen Vektor speichern
     *
     * @param einKunde Kunde
     * @return gibt die Kundennummer des erstellten Kunden Zurück
     * @throws EingabeNichtLeerException wenn eines der Eingabefelder leer ist oder ein negativer Wert eingegeben wird
     */
    public int erstelleKunde(Kunde einKunde) throws EingabeNichtLeerException, IOException {
        //Methode zum Erstellen von Kunden
        if (einKunde.getName().isEmpty() || einKunde.getPasswort().isEmpty() || einKunde.getAdresse().getStrasse().isEmpty()
                || einKunde.getAdresse().getHomeNumber() <= -1 || einKunde.getAdresse().getPlz() <= -1) {
            throw new EingabeNichtLeerException();
        }
        kundenListe.add(einKunde);
        schreibKunden();
        return einKunde.getNummer();

    }

    /**
     * Methode zum Anmelden von Kunden, welche Kundennummern und Passwörter überprüft, ob sie in dem
     * Vektor enthalten sind
     *
     * @param nummer   übernimmt den Wert, welcher eingegeben wurde
     * @param passwort übernimmt den Wert, welcher eingegeben wurde
     * @return Ein Boolischenwert, welcher True ist, wenn der Kunde im System ist oder False, wenn dieser nicht im System ist
     * @throws AnmeldungFehlgeschlagenException wenn die Anmeldedaten nicht mit den Angegeben daten überstimmen
     */
    public Kunde kundeAnmelden(int nummer, String passwort) throws AnmeldungFehlgeschlagenException {
        //Vergleicht Eingabe mit den Werten aus der Mitarbeiterliste
        for (Kunde k : kundenListe) {
            if (k.getNummer() == nummer && k.getPasswort().equals(passwort)) {
                return k;
            }
        }
        throw new AnmeldungFehlgeschlagenException(" Ungueltige Anmeldedaten!");
    }


}
