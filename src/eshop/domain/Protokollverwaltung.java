package eshop.domain;

import eshop.valueobjects.*;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Protokollverwaltung {

    private List<Protokoll> protokollListe;

    /**
     * Konstruktor, welcher einen String Verktor mit dem Namen Protokollliste ertsellt
     */
    public Protokollverwaltung() {
        protokollListe = new Vector<Protokoll>();
    }

    /**
     * Methode, welche eine Log erstellt, sofern ein Mitarbeiter ein Artikel einfügt oder löscht.
     *
     * @param protokoll die Artikeländerungen werden im Protokoll angezeigt und aufgerufen
     */
    public void logZuProtokollListe(Protokoll protokoll) {
        protokollListe.add(protokoll);
    }

    public List<Protokoll> getProtokollListe() {
        return protokollListe;
    }
}
