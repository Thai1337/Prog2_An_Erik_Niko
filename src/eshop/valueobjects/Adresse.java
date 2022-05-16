package eshop.valueobjects;

public class Adresse {
    private String strasse;
    private int hausnummer;
    private int plz;

    public Adresse(String strasse, int hausnummer, int plz){
        this.strasse = strasse;
        this.hausnummer = hausnummer;
        this.plz = plz;
    }

    @Override
    public String toString() {
        return "Adresse{" +
                "strasse='" + strasse + '\'' +
                ", homeNumber=" + hausnummer +
                ", plz=" + plz +
                '}';
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public int getHomeNumber() {
        return hausnummer;
    }

    public void setHomeNumber(int homeNumber) {
        this.hausnummer = homeNumber;
    }

    public int getPlz() {
        return plz;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }
}
