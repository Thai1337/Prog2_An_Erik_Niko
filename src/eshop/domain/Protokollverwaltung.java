package eshop.domain;

import eshop.valueobjects.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        if(!protokollListe.isEmpty()){
            for(int i = 0; i < protokollListe.size(); i++){
                LocalDateTime jetzt = LocalDateTime.now();
                if(protokollListe.get(i) != null && ChronoUnit.DAYS.between(protokollListe.get(i).getDatum(), jetzt) > 30) {
                    protokollListe.remove(protokollListe.get(i));
                }
            }
        }
        return protokollListe;
    }
}
