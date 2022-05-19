package eshop.domain;

import eshop.domain.exceptions.ArtikelbestandUnterNullException;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.Kunde;
import eshop.valueobjects.Warenkorb;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
public class Warenkorbverwaltung {

    private Warenkorb warenkorb;

    public Warenkorbverwaltung(){
    }
//todo Messages ob erfolgreich hinzugefügt wurde
    public void artikelZuWarenkorbHinzufuegen(Artikel artikel, int anzahlArtikel, Kunde kunde) throws ArtikelbestandUnterNullException {
        warenkorb = kunde.getWarkorb();
// TODO keine Negativen werte
//  fehlermeldung bei eingabe einer nicht vorhandenen nummer

        //anzahlArtikel > wasimwarenkorbsteht

        if(warenkorb.getWarenkorbListe().containsKey(artikel)){
            // TODO
             int alteWarenkorbMenge = warenkorb.getWarenkorbListe().get(artikel); // gibt eine Nullpointerexception wenn außerhalb des ifs, weil versucht wird auf einen leeren artikel zuzugreifen, falls diese nichts existierts(deswegen containsKey)

            if((anzahlArtikel + alteWarenkorbMenge) > artikel.getBestand()){ // Wenn die Menge im Warenkorb größer ist als der Bestand des Artikels im Lager throw exception
                throw new ArtikelbestandUnterNullException(artikel, " Die Menge dieses Arikels in Ihrem Warenkorb ist höher als der Bestand im Shop");
            }

             warenkorb.setWarenkorbListe(artikel, anzahlArtikel + alteWarenkorbMenge);
        }else{

            if(anzahlArtikel > artikel.getBestand()){
                throw new ArtikelbestandUnterNullException(artikel, " Die Menge dieses Artikels in Ihrem Warenkorb ist höher als der Bestand im Shop");
            }

            warenkorb.setWarenkorbListe(artikel, anzahlArtikel);
        }
        //kunde.setMeinWarenkorb(warenkorb);
    }

    public void artikelAusWarenkorbEntfernen(Artikel artikel, int anzahlArtikel, Kunde kunde) throws ArtikelbestandUnterNullException {
        warenkorb = kunde.getWarkorb();

        if(warenkorb.getWarenkorbListe().containsKey(artikel)){ // das wird benötigt, weil wenn man einen im "Lager" vorhandenen artikel entfernen will, dieser aber nicht im Warenkorb existiert gibt es eine NullPointerException
            int alterWert = warenkorb.getWarenkorbListe().get(artikel);

            // TODO vielleicht noch ändern, dass wenn alterWert - anzahlArtikel <= 0 der Artikel entfernt wird. Tutor fragen ob das mehr Sinn macht, würde die unter exception überflüssig machen
            if(alterWert - anzahlArtikel < 0){
                throw new ArtikelbestandUnterNullException(artikel, " Der Bestand ihres Warenkorbs darf nicht unter 0 fallen!");
            }

            if(anzahlArtikel == 0 || (alterWert - anzahlArtikel) == 0){
                warenkorb.getWarenkorbListe().remove(artikel);
            }else{
                warenkorb.setWarenkorbListe(artikel,  alterWert - anzahlArtikel);
            }
        }

    }

    public Map<Artikel, Integer> getWarenkorb(Kunde kunde){
        warenkorb = kunde.getWarkorb();
        return warenkorb.getWarenkorbListe();
    }
    public void warenkorbLoeschen(Kunde kunde){
        warenkorb = kunde.getWarkorb();
        warenkorb.getWarenkorbListe().clear();
    }
    public void einkaufAbschliessen(Kunde kunde) throws ArtikelbestandUnterNullException {
        // TODO rechnung muss noch erstellt werden
        warenkorb = kunde.getWarkorb();

        for (Map.Entry<Artikel, Integer> entry: warenkorb.getWarenkorbListe().entrySet()) {
            if((entry.getKey().getBestand() - entry.getValue()) < 0){
                throw new ArtikelbestandUnterNullException(entry.getKey(), ". DU WARST ZU LANGSAM BEIM EINKAUF. NICE TRY! ¯\\_(ツ)_/¯");
            }
            entry.getKey().setBestand(entry.getKey().getBestand() - entry.getValue());
        }

        warenkorbLoeschen(kunde);
    }


}
