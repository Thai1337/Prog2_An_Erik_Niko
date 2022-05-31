package eshop.domain.exceptions;

public class AnmeldungFehlgeschlagenException extends Exception {

    public AnmeldungFehlgeschlagenException(String zusatzMsg) {
        super(" Anmeldung Fehlgeschlagen: " + zusatzMsg);
    }

}
