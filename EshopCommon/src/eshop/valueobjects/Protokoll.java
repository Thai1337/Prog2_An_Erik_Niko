package eshop.valueobjects;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Klasse zur Repräsentation einzelner Protokolle.
 *
 * @author seliger
 * @author nguyen
 * @author heuschmann
 */
public abstract class Protokoll implements Serializable {

    //enum EreignisTyp { NEU, KAUF, EINLAGERUNG, AUSLAGERUNG, LÖSCHUNG };
    public enum EreignisTyp {EINFUEGEN, EINKAUFEN, AENDERUNG, LOESCHUNG}
    protected LocalDateTime datum;
    protected EreignisTyp aktion;

    //private List<Artikel> artikelListe;

    public Protokoll(EreignisTyp aktion) {
        this.aktion = aktion;
        this.datum = LocalDateTime.now();
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    public EreignisTyp EreignisTyp() {
        return aktion;
    }
}
