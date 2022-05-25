package eshop.valueobjects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Klasse zur Repräsentation einzelner Protokolle.
 *
 * @author seliger
 * @author nguyen
 * @author heuschmann
 */
public class Protokoll {

    enum EreignisTyp { NEU, KAUF, EINLAGERUNG, AUSLAGERUNG, LÖSCHUNG };

    private Mitarbeiter mitarbeiter;
    private Artikel artikel;
    private String datum;

    private Kunde kunde;
        private Nutzer nutzer;

    private boolean einfuegenLoeschen;
        private EreignisTyp aktion;

    private List<Artikel> artikelListe;

    public Protokoll(Mitarbeiter mitarbeiter, Artikel artikel, boolean einfuegenLoeschen){
        this.mitarbeiter = mitarbeiter;
        this.artikel = artikel;
        this.einfuegenLoeschen = einfuegenLoeschen;

        aktion = EreignisTyp.KAUF;

        LocalDateTime myObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.datum = myObj.format(myFormatObj);
    }

    public Protokoll(Mitarbeiter mitarbeiter, Artikel artikel){
        this.mitarbeiter = mitarbeiter;
        this.artikel = artikel;

        LocalDateTime myObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.datum = myObj.format(myFormatObj);
    }


    public Kunde getKunde() {
        return kunde;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public Mitarbeiter getMitarbeiter() {
        return mitarbeiter;
    }

    public void setMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }

    public Protokoll(Kunde kunde, List<Artikel> artikelListe){
        LocalDateTime myObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.datum = myObj.format(myFormatObj);
        this.kunde = kunde;
        this.artikelListe = artikelListe;
    }

    public List<Artikel> getArtikelListe() {
        return artikelListe;
    }

    public void setArtikelListe(List<Artikel> artikelListe) {
        this.artikelListe = artikelListe;
    }

    public boolean getEinfuegenLoeschen() {
        return einfuegenLoeschen;
    }

    public Artikel getArtikel() {
        return artikel;
    }

    public void setArtikel(Artikel artikel) {
        this.artikel = artikel;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
}
