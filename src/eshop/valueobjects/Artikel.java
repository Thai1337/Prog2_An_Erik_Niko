package eshop.valueobjects;


/**
 * Klasse zur Repräsentation einzelner Artikel.
 *
 * @author seliger
 * @author nguyen
 * @author heuschmann
 */
public class Artikel {

    private int nummer;
    private String bezeichnung;
    private int bestand;
    private double preis;

    private static int counter;

    public Artikel(int nummer, String bezeichnung, int bestand, double preis){
        this.nummer = nummer;
        this.bezeichnung = bezeichnung;
        this.bestand = bestand;
        this.preis = preis;
    }

    public Artikel(String bezeichnung, int bestand, double preis){
        this.nummer = counter++;
        this.bezeichnung = bezeichnung;
        this.bestand = bestand;
        this.preis = preis;
    }

    @Override
    public String toString() {
        return "Artikel{" +
                "nummer=" + nummer +
                ", bezeichnung='" + bezeichnung + '\'' +
                ", bestand=" + bestand +
                ", preis=" + preis +
                '}';
    }

    /**
     * Standard-Methode von Object überschrieben.
     * Methode wird immer automatisch aufgerufen, wenn ein Artikel-Objekt als String
     * benutzt wird (z.B. in println(artikel);)
     *
     * @see java.lang.Object#toString()
     */





    // Methoden zum Setzen und Lesen der Artikel-Eigenschaften,

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public int getNummer() {
        return nummer;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public int getBestand() {
        return bestand;
    }

    public void setBestand(int bestand) {
        this.bestand = bestand;
    }

    /**
     * Standard-Methode von Object überschrieben.
     * Methode dient Vergleich von zwei Artikel-Objekten anhand ihrer Werte,
     * d.h. Bezeichnung und Nummer.
     *
     * @see java.lang.Object#toString()
     */
    public boolean equals(Object andererArtikel) {
        if (andererArtikel instanceof Artikel)
            return ((this.nummer == ((Artikel) andererArtikel).nummer)
                    || (this.bezeichnung.equals(((Artikel) andererArtikel).bezeichnung)));
        else
            return false;
    }


}
