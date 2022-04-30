package eShop.valueobjects;


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

    public Artikel(int nummer, String bezeichnung, int bestand){
        this.nummer = nummer;
        this.bezeichnung = bezeichnung;
        this.bestand = bestand;
    }
    /**
     * Standard-Methode von Object überschrieben.
     * Methode wird immer automatisch aufgerufen, wenn ein Artikel-Objekt als String
     * benutzt wird (z.B. in println(artikel);)
     *
     * @see java.lang.Object#toString()
     */

    public String toString() {
        return "Artikel{" +
                "nummer=" + nummer +
                ", bezeichnung='" + bezeichnung + '\'' +
                ", bestand=" + bestand +
                '}';
    }
    // Methoden zum Setzen und Lesen der Artikel-Eigenschaften,

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

}
