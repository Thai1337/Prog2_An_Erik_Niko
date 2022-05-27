package eshop.valueobjects;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Rechnung {
    private Kunde kunde;
    private Warenkorb warenkorb;

    public Rechnung(Kunde kunde) {
        this.kunde = kunde;
        this.warenkorb = kunde.getWarkorb();
    }

    /**
     * Methode welche einen String erstellt mit den Inhalten des Einkaufes und den Warenkorb leert
     *
     * @return der zusammengesetzte Rechnungsstring wird zurückgegeben
     */
    @Override
    public String toString() {
        String rechnungKunde, rechnungArtikel, rechnung, rechnungGesamtpreis;
        DecimalFormat df = new DecimalFormat("0.00");

        LocalDateTime myObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedDate = myObj.format(myFormatObj);

        rechnungKunde = "\nRechnung an:\t\t\t\t\t\t\t\t\t\t" + "Datum: " + formattedDate + " Uhr" + "\n\n\t" + kunde.getName() + "\n\t" + kunde.getAdresse().getStrasse() + " " + kunde.getAdresse().getHomeNumber() + "\n\t" + kunde.getAdresse().getPlz() + " " + kunde.getAdresse().getOrt() + "\n";

        rechnungArtikel = "\nBestellung:\n";

        for (Map.Entry<Artikel, Integer> entry : warenkorb.getWarenkorbListe().entrySet()) {
            rechnungArtikel += "\n\tArtikelnummer: " + entry.getKey().getNummer() + " | Name: " + entry.getKey().getBezeichnung() + " | Stueckpreis: " + df.format(entry.getKey().getPreis()) + "EUR | Menge: " + entry.getValue() + " | Preis: " + df.format(entry.getValue() * entry.getKey().getPreis()) + "EUR";
        }

        rechnungGesamtpreis = "\n\n\tGesamtpreis: " + df.format(warenkorb.getGesamtpreis()) + "EUR";

        rechnung = rechnungKunde + rechnungArtikel + rechnungGesamtpreis;

        warenkorb.warenkorbLeeren();

        return rechnung;
    }


    public Kunde getKunde() {
        return kunde;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public Warenkorb getWarenkorb() {
        return warenkorb;
    }

    public void setWarenkorb(Warenkorb warenkorb) {
        this.warenkorb = warenkorb;
    }
}
