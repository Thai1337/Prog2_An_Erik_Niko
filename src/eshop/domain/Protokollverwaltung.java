package eshop.domain;

import eshop.valueobjects.*;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Protokollverwaltung {

    private List<String> protokollListe;

    public Protokollverwaltung(){
        protokollListe = new Vector<String>();
    }

    public void einfuegenLoeschenLog(Protokoll protokoll){
        String protokollString;
        String mitarbeiter = "\n"+protokoll.getDatum() + " | " + "M | Nummer: " +protokoll.getMitarbeiter().getNummer() +" | Name: " + protokoll.getMitarbeiter().getName();

        if(protokoll.getEinfuegenLoeschen()){ //Einfügen
            mitarbeiter += "\n\t | Typ: Artikelanlegung | Artikelnummer:" +protokoll.getArtikel().getNummer()+ " | Bezeichnung: "+protokoll.getArtikel().getBezeichnung()+ " | Bestand: "+ protokoll.getArtikel().getBestand();
        }else{
            mitarbeiter += "\n\t | Typ: Artikelloeschung | Artikelnummer:" +protokoll.getArtikel().getNummer()+ " | Bezeichnung: "+protokoll.getArtikel().getBezeichnung()+ " | Bestand: "+ protokoll.getArtikel().getBestand();
        }
        protokollString = mitarbeiter;
        protokollListe.add(protokollString);
    }

    public void bearbeitenLog(Protokoll protokoll){
        if(protokoll.getArtikel().getBezeichnung().isEmpty() && protokoll.getArtikel().getPreis() == -1.01 && protokoll.getArtikel().getBestand() == -1){ // es soll kein log erstellt werden, wenn alle 3 änderungsfelder leer sind
            return;
        }

        String protokollString;
        String mitarbeiter = "\n"+protokoll.getDatum() + " | " + "M | Nummer: " +protokoll.getMitarbeiter().getNummer() +" | Name: " + protokoll.getMitarbeiter().getName();
        if(!protokoll.getArtikel().getBezeichnung().isEmpty()){ //Bezeichnung
            mitarbeiter += "\n\t | Typ: Bearbeitung | Bezeichnungseanderung zu: " + protokoll.getArtikel().getBezeichnung();
        }
        if(protokoll.getArtikel().getPreis() != -1.01){ //Preis
            mitarbeiter += "\n\t | Typ: Bearbeitung | Preisaenderung zu: " + protokoll.getArtikel().getPreis();
        }
        if(protokoll.getArtikel().getBestand() != -1){ //Bestand
            mitarbeiter += "\n\t | Typ: Bearbeitung | Bestandsaenderung zu: " + protokoll.getArtikel().getBestand();
        }

        protokollString = mitarbeiter;
        protokollListe.add(protokollString);
    }

    public void kaufLog(Protokoll protokoll){
        Kunde kundeObjekt =  protokoll.getKunde();
        if(kundeObjekt.getWarkorb().getWarenkorbListe().isEmpty()){ // damit kein log erstellt wird, wenn der kunde ohne Artikel im warenkorb eine rechnung erstellt
            return;
        }

        String kunde = "\n" + protokoll.getDatum() + " | " + "K | Nummer: " +protokoll.getKunde().getNummer() +" | Name: " + protokoll.getKunde().getName();
        String protokollString;
        for(Map.Entry<Artikel, Integer> entry:kundeObjekt.getWarkorb().getWarenkorbListe().entrySet()){
            kunde += "\n\t | Typ: Kauf | Artikelnummer: " + entry.getKey().getNummer() + " | Bezeichnung: "+ entry.getKey().getBezeichnung() +" | Bestandsaenderung: -"+ entry.getValue();
        }

        protokollString = kunde;
        protokollListe.add(protokollString);
    }

    public List<String> getProtokollListe() {
        return protokollListe;
    }
}
