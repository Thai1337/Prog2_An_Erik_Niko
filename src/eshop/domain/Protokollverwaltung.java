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

    public void bearbeitenLog(Protokoll protokoll){
        String protokollString;
        String mitarbeiter = "\n"+protokoll.getDatum() + " | " + "M | Nummer: " +protokoll.getMitarbeiter().getNummer() +" | Name: " + protokoll.getMitarbeiter().getName();
            if(protokoll.getAenderung().isEmpty()){ //Einfügen
                mitarbeiter += "\n\t | Typ: Artikelanlegung | Artikelnummer:" +protokoll.getArtikel().getNummer()+ " | Bezeichnung: "+protokoll.getArtikel().getBezeichnung()+ " | Bestandsaenderung zu: "+ protokoll.getArtikel().getBestand();
            }
            if(protokoll.getAenderung().contains("a")){ //Bezeichnung
                mitarbeiter += "\n\t | Typ: Bearbeitung | Bezeichnungseanderung zu: " + protokoll.getArtikel().getBezeichnung();
            }
            if(protokoll.getAenderung().contains("b")){ //Preis
                mitarbeiter += "\n\t | Typ: Bearbeitung | Preisaenderung zu: " + protokoll.getArtikel().getPreis();
            }
            if(protokoll.getAenderung().contains("c")){ //Bestand
                mitarbeiter += "\n\t | Typ: Bearbeitung | Bestandsaenderung zu: " + protokoll.getArtikel().getBestand();
            }
            protokollString = mitarbeiter;
            protokollListe.add(protokollString);
    }
    public void kaufLog(Protokoll protokoll){
        String kunde = "\n" + protokoll.getDatum() + " | " + "K | Nummer: " +protokoll.getKunde().getNummer() +" | Name: " + protokoll.getKunde().getName();
        String protokollString;

        Kunde kundeObjekt =  protokoll.getKunde();

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
