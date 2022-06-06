package eshop.valueobjects;

import java.io.Serial;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class KundenProtokoll extends Protokoll{
    @Serial
    private static final long serialVersionUID = 5432299;
    private Warenkorb warenkorb;

    private Kunde kunde;

    public KundenProtokoll(Kunde kunde, EreignisTyp aktion) {
        super(aktion);
        this.kunde = kunde;
        //this.warenkorb = new Warenkorb(warenkorb);
        this.warenkorb = new Warenkorb(kunde.getWarkorb());
    }

    @Override
    public String toString() {
        // TODO exceptions überprüfen
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String protokoll = "\n" + this.datum.format(myFormatObj) + " | " + "K" + " | Nummer: " + kunde.getNummer() + " | Name: " + kunde.getName() + "\n\t | Typ: " + aktion;
        //mitarbeiterProtokoll += " | Artikelnummer: " +protokoll.getArtikel().getNummer()+ " | Bezeichnung: "+protokoll.getArtikel().getBezeichnung()+ " | Bestand: "+ protokoll.getArtikel().getBestand();//
            if(aktion == Protokoll.EreignisTyp.EINKAUFEN){
                for (Map.Entry<Artikel, Integer> entry : warenkorb.getWarenkorbListe().entrySet()) {
                    protokoll += "\n\t | Artikelnummer: " + entry.getKey().getNummer() + " | Bezeichnung: " + entry.getKey().getBezeichnung() + " | Bestandsaenderung: -" + entry.getValue();
                }
            }
        return protokoll;
    }
}
