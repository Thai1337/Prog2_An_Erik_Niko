package eshop.valueobjects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Protokoll {
    private Mitarbeiter mitarbeiter;
    private Artikel artikel;
    private String datum;

    private Kunde kunde;
    private boolean einfuegenLoeschen;

    public Protokoll(Mitarbeiter mitarbeiter, Artikel artikel, boolean einfuegenLoeschen){
        this.mitarbeiter = mitarbeiter;
        this.artikel = artikel;
        this.einfuegenLoeschen = einfuegenLoeschen;


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

    public Protokoll(Kunde kunde){
        LocalDateTime myObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.datum = myObj.format(myFormatObj);
        this.kunde = kunde;
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
