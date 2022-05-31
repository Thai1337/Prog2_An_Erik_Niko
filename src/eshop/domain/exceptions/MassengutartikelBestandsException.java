package eshop.domain.exceptions;

public class MassengutartikelBestandsException extends Exception {
    public MassengutartikelBestandsException(){
        super("\nDer von ihnen angegebe Bestand ist kein Vielfaches der Packungsgrosse!");
    }
}
