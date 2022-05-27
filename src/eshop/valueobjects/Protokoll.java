package eshop.valueobjects;

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
public class Protokoll {

    //enum EreignisTyp { NEU, KAUF, EINLAGERUNG, AUSLAGERUNG, LÖSCHUNG };
    public enum EreignisTyp {EINFUEGEN, EINKAUFEN, AENDERUNG, LOESCHUNG}

    ;

    private Warenkorb warenkorb;

    private Artikel artikel;
    private String datum;
    private Nutzer nutzer;
    private EreignisTyp aktion;

    //private List<Artikel> artikelListe;

    public Protokoll(Nutzer nutzer, Artikel artikel, EreignisTyp aktion) {
        this.nutzer = nutzer;
        this.artikel = artikel;
        //aktion = EreignisTyp.KAUF;
        this.aktion = aktion;

        LocalDateTime myObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.datum = myObj.format(myFormatObj);
    }

    public Protokoll(Nutzer nutzer, EreignisTyp aktion) {
        LocalDateTime myObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        this.aktion = aktion;
        this.datum = myObj.format(myFormatObj);
        this.nutzer = nutzer;

        //this.warenkorb = new Warenkorb(warenkorb);

        Kunde k = (Kunde) nutzer;
        this.warenkorb = new Warenkorb(k.getWarkorb());
    }

    @Override
    public String toString() {
        // TODO exceptions überprüfen
        // TODO vllt das Protokoll in Mitarbeiter und Nutzer aufteilen
        String protokoll = "\n" + this.datum + " | " + (nutzer instanceof Kunde ? "K" : "M") + " | Nummer: " + nutzer.getNummer() + " | Name: " + nutzer.getName() + "\n\t | Typ: " + aktion;
        //mitarbeiterProtokoll += " | Artikelnummer: " +protokoll.getArtikel().getNummer()+ " | Bezeichnung: "+protokoll.getArtikel().getBezeichnung()+ " | Bestand: "+ protokoll.getArtikel().getBestand();//
        switch (aktion) {
            case EINFUEGEN, LOESCHUNG:
                protokoll += " | Artikelnummer: " + artikel.getNummer() + " | Bezeichnung: " + artikel.getBezeichnung() + " | Bestand: " + artikel.getBestand();
                break;
            case AENDERUNG:
                if (!artikel.getBezeichnung().isEmpty()) { //Bezeichnung
                    protokoll += " | Bezeichnungseanderung zu: " + artikel.getBezeichnung();
                }
                if (artikel.getPreis() != -1.01) { //Preis
                    protokoll += " | Preisaenderung zu: " + artikel.getPreis();
                }
                if (artikel.getBestand() != -1) { //Bestand
                    protokoll += " | Bestandsaenderung zu: " + artikel.getBestand();
                }
                break;
            case EINKAUFEN:
                for (Map.Entry<Artikel, Integer> entry : warenkorb.getWarenkorbListe().entrySet()) {
                    protokoll += "\n\t | Artikelnummer: " + entry.getKey().getNummer() + " | Bezeichnung: " + entry.getKey().getBezeichnung() + " | Bestandsaenderung: -" + entry.getValue();
                }
                break;

        }


        return protokoll;
    }

    public Nutzer getNutzer() {
        return nutzer;
    }

    public void setNutzer(Nutzer nutzer) {
        this.nutzer = nutzer;
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
