package eshop.domain;

import eshop.valueobjects.*;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Protokollverwaltung {

    private List<String> protokollListe;

    /**
     * Konstruktor, welcher einen String Verktor mit dem Namen Protokollliste ertsellt
     */
    public Protokollverwaltung(){
        protokollListe = new Vector<String>();
    }

    /**
     * Methode, welche eine Log erstellt, sofern ein Mitarbeiter ein Artikel einfügt oder löscht.
     * @param protokoll die Artikeländerungen werden im Protokoll angezeigt und aufgerufen
     */
    public void einfuegenLoeschenLog(Protokoll protokoll){
        String protokollString;
        String mitarbeiter = "\n"+protokoll.getDatum() + " | " + "M | Nummer: " +protokoll.getMitarbeiter().getNummer() +" | Name: " + protokoll.getMitarbeiter().getName();

        if(protokoll.getEinfuegenLoeschen()){ //Einfügen
            mitarbeiter += "\n\t | Typ: Artikelanlegung";
        }else{
            mitarbeiter += "\n\t | Typ: Artikelloeschung";
        }

        mitarbeiter += " | Artikelnummer: " +protokoll.getArtikel().getNummer()+ " | Bezeichnung: "+protokoll.getArtikel().getBezeichnung()+ " | Bestand: "+ protokoll.getArtikel().getBestand();

        protokollString = mitarbeiter;
        protokollListe.add(protokollString);
    }

    /**
     * Methode zum ermitteln der Art des Protokolls und diesen String dann verkettet
     * @param protokoll die Artikeländerungen werden im Protokoll angezeigt und aufgerufen
     */
    public void bearbeitenLog(Protokoll protokoll){
        if(protokoll.getArtikel().getBezeichnung().isEmpty() && protokoll.getArtikel().getPreis() == -1.01 && protokoll.getArtikel().getBestand() == -1){ // es soll kein log erstellt werden, wenn alle 3 änderungsfelder leer sind
            return;
        }

        String protokollString;
        String mitarbeiter = "\n"+protokoll.getDatum() + " | " + "M | Nummer: " +protokoll.getMitarbeiter().getNummer() +" | Name: " + protokoll.getMitarbeiter().getName() + "\n\t | Typ: Bearbeitung | ";
        if(!protokoll.getArtikel().getBezeichnung().isEmpty()){ //Bezeichnung
            mitarbeiter += "Bezeichnungseanderung zu: " + protokoll.getArtikel().getBezeichnung();
        }
        if(protokoll.getArtikel().getPreis() != -1.01){ //Preis
            mitarbeiter += "Preisaenderung zu: " + protokoll.getArtikel().getPreis();
        }
        if(protokoll.getArtikel().getBestand() != -1){ //Bestand
            mitarbeiter += "Bestandsaenderung zu: " + protokoll.getArtikel().getBestand();
        }

        protokollString = mitarbeiter;
        protokollListe.add(protokollString);
    }

    /**
     * Methode, welche ein Log erstellt, sobald ein Kunde einen Einkauf abgeschlossen hat. Jeder Artikel wird untereinander aufgelistet.
     * Zudem kann man sehen, welcher Kunde mit seinen Kundeninformationen den Einkauf betätigt hat.
     * @param protokoll Kaufänderungen werden im Protokoll eingefügt
     */
    public void kaufLog(Protokoll protokoll){
        Kunde kundeObjekt =  protokoll.getKunde();
        if(kundeObjekt.getWarkorb().getWarenkorbListe().isEmpty()){ // damit kein log erstellt wird, wenn der kunde ohne Artikel im warenkorb eine rechnung erstellt
            return;
        }

        String kunde = "\n" + protokoll.getDatum() + " | " + "K | Nummer: " +protokoll.getKunde().getNummer() +" | Name: " + protokoll.getKunde().getName() + "\n\t | Typ: Kauf | ";
        String protokollString;
        for(Map.Entry<Artikel, Integer> entry:kundeObjekt.getWarkorb().getWarenkorbListe().entrySet()){
            kunde += "Artikelnummer: " + entry.getKey().getNummer() + " | Bezeichnung: "+ entry.getKey().getBezeichnung() +" | Bestandsaenderung: -"+ entry.getValue();
        }

        protokollString = kunde;
        protokollListe.add(protokollString);
    }

    /**
     * Gibt die Liste aller Protokolle an den Eshop weiter in eimen String Vektor
     * @return Protkolllisten Vektor
     */
    public List<String> getProtokollListe() {
        return protokollListe;
    }
}
