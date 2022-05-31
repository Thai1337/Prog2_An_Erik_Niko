package eshop.domain.exceptions;

public class WarenkorbLeerException extends Exception {

    public WarenkorbLeerException() {
        super("\n Sie koennen keine Rechnung erstellen, solange ihr Warenkorb leer ist!");
    }

}
