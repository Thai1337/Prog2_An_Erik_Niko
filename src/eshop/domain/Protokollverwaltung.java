package eshop.domain;

import eshop.persistence.ListenPersistence;
import eshop.valueobjects.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Protokollverwaltung {

    private List<Protokoll> protokollListe;

    private ListenPersistence<Protokoll> persistence;

    /**
     * Konstruktor, welcher einen String Verktor mit dem Namen Protokollliste ertsellt
     */
    public Protokollverwaltung() {
        protokollListe = new Vector<Protokoll>();
        persistence = new ListenPersistence<>("protokoll");
    }

    /**
     * Liest die Protokolle aus der protokoll.txt Datei ein und lädt sie in einen Vektor
     * @throws IOException
     */
    public void liesProtokoll() throws IOException {
        protokollListe = persistence.ladenListe();
    }

    /**
     * Schreibt die Protokolle aus dem Vektor in die protokoll.txt Datei
     * @throws IOException
     */
    public void schreibProtokoll() throws IOException {
        persistence.speichernListe(protokollListe);
    }

    /**
     * Methode, welche eine Log erstellt, sofern ein Mitarbeiter ein Artikel einfügt oder löscht.
     *
     * @param protokoll die Artikeländerungen werden im Protokoll angezeigt und aufgerufen
     */
    public void logZuProtokollListe(Protokoll protokoll) throws IOException {
        protokollListe.add(protokoll);
        schreibProtokoll();
    }

    /**
     * Löscht Protokolle, welche eine gegebene Zeitdifferenz überschreiten (30 Tage)
     * @throws IOException
     */
    public void protokollLoeschungNachZeiten() throws IOException {
        // TODO überprüfen ob alle if Bedingungen nötig sind
        if(!protokollListe.isEmpty()){
            for(int i = 0; i < protokollListe.size(); i++){
                LocalDateTime jetzt = LocalDateTime.now();
                if(protokollListe.get(i) != null && ChronoUnit.DAYS.between(protokollListe.get(i).getDatum(), jetzt) > 30) {
                    protokollListe.remove(protokollListe.get(i));
                    i = i - 1; // wird benötigt damit der counter beim Entfernen eines Protokolls keine stelle in der Liste überspringt z.B. i=0, size=5 => remove => i=1, size=4;
                }
            }
        }
        schreibProtokoll();
    }

    /**
     * gibt die Protokollliste zurück
     * @return protokollListe, liste von Protokollen
     * @throws IOException
     */
    public List<Protokoll> getProtokollListe() throws IOException {
        protokollLoeschungNachZeiten();
        return protokollListe;
    }
}
