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

    public void liesProtokoll() throws IOException {
        protokollListe = persistence.ladenListe();
    }

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

    public void protokollLoeschungNachZeiten() throws IOException {
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

    public List<Protokoll> getProtokollListe() throws IOException {
        protokollLoeschungNachZeiten();
        return protokollListe;
    }
}
