package eshop.valueobjects;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static eshop.valueobjects.Protokoll.EreignisTyp.LOESCHUNG;

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
