package eshop.domain.exceptions;

public class EingabeNichtLeerException extends Exception {

    public EingabeNichtLeerException(){
        super("Ihre eingabe ist Leer! ");
    }

}
