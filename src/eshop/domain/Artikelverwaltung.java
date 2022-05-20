package eshop.domain;

import eshop.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.domain.exceptions.ArtikelbestandUnterNullException;
import eshop.domain.exceptions.EingabeNichtLeerException;
import eshop.valueobjects.Artikel;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Klasse zur Verwaltung der Artikel.
 *
 * @author seliger
 * @author nguyen
 * @author heuschmann
 */
public class Artikelverwaltung {
    private List<Artikel> artikelBestand = new Vector();

    /**
     * Konstruktor welcher Artikel erstellt und der Vector des Bestandes hinzufügt
     */
    public Artikelverwaltung() {
        Artikel a1 = new Artikel("Holz", 100, 99.99);
        Artikel a12 = new Artikel("Holzbrett", 6, 10.55);
        Artikel a2 = new Artikel("Metall", 50, 99.0);
        Artikel a3 = new Artikel( "Ball", 50, 99.97);
        Artikel a4 = new Artikel("Cola", 50, 99.98);
        Artikel a5 = new Artikel("Ananas", 50, 99.49);
        Artikel a6 = new Artikel( "Buch", 1, 99.19);
        artikelBestand.add(a1);
        artikelBestand.add(a2);
        artikelBestand.add(a3);
        artikelBestand.add(a4);
        artikelBestand.add(a5);
        artikelBestand.add(a6);
        artikelBestand.add(a12);

    }

    /**
     * Methode, die eine KOPIE des Artikelbestands zurückgibt.
     * (Eine Kopie ist eine gute Idee, wenn ich dem Empfänger
     * der Daten nicht ermöglichen möchte, die Original-Daten
     * zu manipulieren.)
     *
     * @return Liste aller Artikel im Artikelbestand (Kopie)
     */
    public List<Artikel> getArtikelBestand() {
        return new Vector(artikelBestand);
    }

    /**
     * Methode, die eine KOPIE des Artikelbestands zurückgibt.
     * Die Methode besitz eine andere Signatur,
     * als die Normale getArtikelBestand mit einem Parameter um die Art der Sortierung zu ermitteln.
     * (Eine Kopie ist eine gute Idee, wenn ich dem Empfänger
     * der Daten nicht ermöglichen möchte, die Original-Daten
     * zu manipulieren.)
     *
     * @param sortierung index welcher den Typ der Sortierung ermittelt
     * @return Liste aller Artikel im Artikelbestand sortiert oder nicht als (Kopie)
     */
    public List<Artikel> getArtikelBestand(int sortierung) {
        List<Artikel> kopie = new Vector<Artikel>(artikelBestand);

        /*Comparator<Artikel> com = new Comparator<Artikel>(){
            public int compare(Artikel a1, Artikel a2){
                return a1.getNummer()- a2.getNummer();
            }
        };*/

        switch (sortierung) {
            case 1:
                Collections.sort(kopie,
                        (a1, a2) -> a1.getBezeichnung().compareToIgnoreCase(a2.getBezeichnung()));
                break;
            case 2:
                Collections.sort(kopie,
                        (a1, a2) -> a1.getNummer()-a2.getNummer());
                break;
            case 3:
                Collections.sort(kopie,
                        (a1, a2) -> a2.getBezeichnung().compareToIgnoreCase(a1.getBezeichnung()));
                break;
            case 4:
                Collections.sort(kopie,
                        (a1, a2) -> a2.getNummer()-a1.getNummer());
                break;
            default:
                kopie = new Vector<Artikel>(artikelBestand);
                break;
        }
        return kopie;



    }



    /**
     * Methode, die ein Artikel an das Ende des artikelBestandes einfügt.
     *
     * @param einArtikel der einzufügende Artikel
     * @throws ArtikelExistiertBereitsException wenn ein Artikel bereits existiert
     */
    public void einfuegen(Artikel einArtikel) throws ArtikelExistiertBereitsException, EingabeNichtLeerException {
        if (artikelBestand.contains(einArtikel)) { //.contains() benutzt equals Methode von Artikel, welche in der Artikelklasse überschrieben wurde.
            throw new ArtikelExistiertBereitsException(einArtikel, "im Lager");
        }
        if(einArtikel.getNummer() <= -1 || einArtikel.getBestand() <= -1 || einArtikel.getBezeichnung().isEmpty()){
            // TODO optional: werte an die exception übergeben und dort logik einbauen um zu überprüfen was falsch ist
            throw new EingabeNichtLeerException();
        }

        // das übernimmt der Vector:
        artikelBestand.add(einArtikel);

    }

    /**
     * Methode, die ein Artikel an das Ende des artikelBestandes einfügt.
     *
     * @param einArtikel der einzufügende Artikel
     * @throws ArtikelbestandUnterNullException wenn der eingegebene Artikelbestand unter -1 ist
     */
    public void aendereArtikelbestand(Artikel einArtikel) throws ArtikelbestandUnterNullException {
        // das übernimmt der Vector:
        // TODO exception damit keine negativen Preise eingegeben werden können und die java-doc ändern!!!! dafuq?
        if (einArtikel.getBestand() < -1) {
            throw new ArtikelbestandUnterNullException(einArtikel, " AMIGO");
        }

        for (Artikel artikel : artikelBestand) {
            if (artikel.getNummer() == einArtikel.getNummer()) {
                if(!einArtikel.getBezeichnung().isEmpty()){
                    artikel.setBezeichnung(einArtikel.getBezeichnung());
                }
                if(einArtikel.getPreis() != -1.01){
                    artikel.setPreis(einArtikel.getPreis());
                }
                if(einArtikel.getBestand() != -1){
                    artikel.setBestand(einArtikel.getBestand());
                }
            }

        }
        
    }
    /**
     * Methode zum Löschen eines Artikels aus dem Bestand.
     *
     * @param einArtikel der löschende Artikel
     */
    public void loeschen(Artikel einArtikel) {
        // das übernimmt der Vector:
        artikelBestand.remove(einArtikel);
    }

    /**
     * Methode, die anhand einer Bezeichnung nach Artikeln sucht. Es wird eine Liste von Artikeln
     * zurückgegeben, die alle Artikel mit exakt übereinstimmender Bezeichnung enthält.
     *
     * @param bezeichung Bezeichnung des gesuchten Artikels
     * @return Liste der Artikel mit gesuchter Bezeichnung (evtl. leer)
     */
    public List<Artikel> sucheArtikel(String bezeichung) {
        List<Artikel> ergebnis = new Vector();

        for (Artikel artikel: artikelBestand) {
            if (artikel.getBezeichnung().toLowerCase().contains(bezeichung.toLowerCase())) {
                ergebnis.add(artikel);
            }
        }

        return ergebnis;
    }
}

