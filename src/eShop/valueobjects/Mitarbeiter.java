package eShop.valueobjects;

public class Mitarbeiter extends Nutzer{

    public Mitarbeiter(String name, int nummer) {
        this.name = name;
        this.nummer = nummer;
    }

    // Methoden zum Setzen und Lesen der Mitarbeiter-Eigenschaften,

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNummer() {
        return nummer;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }
}
