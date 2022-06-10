package eshop.valueobjects;

import java.io.Serial;
import java.time.format.DateTimeFormatter;

public class MitarbeiterProtokoll extends Protokoll {
    @Serial
    private static final long serialVersionUID = 78239257;
    private final Artikel artikel;
    private final Mitarbeiter mitarbeiter;
    public MitarbeiterProtokoll(Mitarbeiter mitarbeiter , Artikel artikel, EreignisTyp aktion) {
        super(aktion);
        // TODO wenn Mitarbeiter gelöscht werden können müssen kopien der Mitarbeiter werden
        this.mitarbeiter = new Mitarbeiter(mitarbeiter);
        this.artikel = new Artikel(artikel);
    }

    @Override
    public String toString() {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String protokoll = "\n" + this.datum.format(myFormatObj) + " | "+ "M" + " | Nummer: " + mitarbeiter.getNummer() + " | Name: " + mitarbeiter.getName() + "\n\t | Typ: " + aktion;

        switch (aktion) {
            case EINFUEGEN, LOESCHUNG:
                protokoll += " | Artikelnummer: " + artikel.getNummer() + " | Bezeichnung: " + artikel.getBezeichnung() + " | Bestand: " + artikel.getBestand();
                break;
            case AENDERUNG:
                if (!artikel.getBezeichnung().isEmpty()) { //Bezeichnung
                    protokoll += " | Artikelnummer: " + artikel.getNummer() + " | Bezeichnungseanderung zu: " + artikel.getBezeichnung();
                }
                if (artikel.getPreis() != -1.01) { //Preis
                    protokoll += " | Artikelnummer: " + artikel.getNummer() + " | Preisaenderung zu: " + artikel.getPreis();
                }
                if (artikel.getBestand() != -1) { //Bestand
                    protokoll += " | Artikelnummer: " + artikel.getNummer() + " | Bestandsaenderung zu: " + artikel.getBestand();
                }
                break;
        }
        return protokoll;
    }

    public Artikel getArtikel() {
        return artikel;
    }

    public Mitarbeiter getMitarbeiter() {
        return mitarbeiter;
    }
}
