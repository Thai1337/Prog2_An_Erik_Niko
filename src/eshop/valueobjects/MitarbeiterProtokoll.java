package eshop.valueobjects;

import java.io.Serial;
import java.time.format.DateTimeFormatter;

public class MitarbeiterProtokoll extends Protokoll {
    @Serial
    private static final long serialVersionUID = 78239257;
    private Artikel artikel;
    private Mitarbeiter mitarbeiter;
    public MitarbeiterProtokoll(Mitarbeiter mitarbeiter , Artikel artikel, EreignisTyp aktion) {
        super(aktion);
        this.mitarbeiter = mitarbeiter;
        this.artikel = artikel;
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
                    protokoll += " | Bezeichnungseanderung zu: " + artikel.getBezeichnung();
                }
                if (artikel.getPreis() != -1.01) { //Preis
                    protokoll += " | Preisaenderung zu: " + artikel.getPreis();
                }
                if (artikel.getBestand() != -1) { //Bestand
                    protokoll += " | Bestandsaenderung zu: " + artikel.getBestand();
                }
                break;
        }
        return protokoll;
    }
}
