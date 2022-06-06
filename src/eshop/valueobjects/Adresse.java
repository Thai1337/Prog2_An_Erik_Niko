package eshop.valueobjects;

import java.io.Serial;
import java.io.Serializable;

/**
 * Klasse zur Repräsentation einzelner Adressen.
 *
 * @author seliger
 * @author nguyen
 * @author heuschmann
 */
public class Adresse implements Serializable {
    @Serial
    private static final long serialVersionUID = 4856484;
    private String strasse;
    private int hausnummer;
    private int plz;
    private String ort;

    public Adresse(String strasse, int hausnummer, int plz, String ort) {
        this.strasse = strasse;
        this.hausnummer = hausnummer;
        this.plz = plz;
        this.ort = ort;
    }

    @Override
    public String toString() {
        return "Adresse{" +
                "strasse='" + strasse + '\'' +
                ", hausnummer=" + hausnummer +
                ", plz=" + plz +
                ", ort='" + ort + '\'' +
                '}';
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public int getHomeNumber() {
        return hausnummer;
    }

    public void setHomeNumber(int homeNumber) {
        this.hausnummer = homeNumber;
    }

    public int getPlz() {
        return plz;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }
}
