package eshop.valueobjects;

import java.io.Serial;

public class Massengutartikel extends Artikel{

    @Serial
    private static final long serialVersionUID = 54654354;
    private int packungsgroesse;

    public Massengutartikel(int nummer, String bezeichnung, int bestand, double preis, int packungsgroesse) {
        super(nummer, bezeichnung, bestand, preis);
        this.packungsgroesse = packungsgroesse;
    }

    public Massengutartikel(String bezeichnung, int bestand, double preis, int packungsgroesse) {
        super(bezeichnung, bestand, preis);
        this.packungsgroesse = packungsgroesse;
    }

    @Override
    public String toString() {
        return "Massengutartikel{" +
                "nummer=" + nummer +
                ", bezeichnung='" + bezeichnung + '\'' +
                ", bestand=" + bestand +
                ", preis=" + preis +
                ", packungsgroesse=" + packungsgroesse +
                '}';
    }

    public int getPackungsgrosse() {
        return packungsgroesse;
    }

    public void setPackungsgrosse(int packungsgrosse) {
        this.packungsgroesse = packungsgrosse;
    }
}
