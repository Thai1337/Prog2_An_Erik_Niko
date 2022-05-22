package eshop.domain.exceptions;

public class EingabeNichtLeerException extends Exception {

    public EingabeNichtLeerException(){
        super("Ihre eingabe ist Leer oder falsch!    TIPP: Sie muessen alle Inputs ausfüllen und Nummern müssen >= 0 sein!");
    }

}
