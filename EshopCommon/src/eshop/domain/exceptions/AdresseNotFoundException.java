package eshop.net.rmi.common.exceptions;

public class AdresseNotFoundException extends Exception {

    public AdresseNotFoundException(String name) {
        super("Fehler: Adresse von " + name + " nicht gefunden.");
    }
}
