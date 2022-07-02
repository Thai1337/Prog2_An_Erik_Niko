package eshop.ui.gui.table;

import eshop.domain.Eshop;
import eshop.domain.exceptions.ArtikelNichtVorhandenException;
import eshop.domain.exceptions.ArtikelbestandUnterNullException;
import eshop.domain.exceptions.EingabeNichtLeerException;
import eshop.domain.exceptions.MassengutartikelBestandsException;
import eshop.ui.gui.StringConverter;
import eshop.ui.gui.model.ArtikelTableModel;
import eshop.ui.gui.panel.WarenkorbPanel;
import eshop.valueobjects.*;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;

public class ArtikelTable extends JTable implements WarenkorbPanel.EinkaufAbschliessenListener {
    private Eshop shop;
    private List<Artikel> artikel;
    private Mitarbeiter mitarbeiter;
    //private Kunde kunde;
    private  ArtikelTableModel tableModel;
    public ArtikelTable(Eshop shop) {
        super();
        this.shop = shop;
        this.tableModel = new ArtikelTableModel(shop.gibAlleArtikel());
        artikel = tableModel.getArtikel(); // keine ahnung warum aber wenn ich das nicht hier hab gib es bei allen neue eingefügten artikeln in zeile 60 einen index out of bounds error wenn man den bestand ändert
        setModel(tableModel);

        updateArtikel(shop.gibAlleArtikel());
        //addMouseListener(this);
    }

    public void updateArtikel(List<Artikel> artikel){
        sortiereArtikel();

        tableModel = (ArtikelTableModel) getModel();

        tableModel.setArtikel(artikel);
    }

    private void sortiereArtikel(){
        TableRowSorter<ArtikelTableModel> sorter = new TableRowSorter<ArtikelTableModel>(tableModel);

        // TODO mit einem sorter.addRowSorterListener(); die sortierung im eshop machen vllt

        this.setRowSorter(sorter);

        sorter.setSortable(2, false);
        sorter.setSortable(3, false);
        sorter.setSortable(4, false);
    }


    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Artikel row = artikel.get(rowIndex);
        String bezeichnung = "";
        int bestand = row.getBestand();
        double preis = -1.01;
        int packgroesse = -1;
        if(row instanceof Massengutartikel) {
            packgroesse = ((Massengutartikel)row).getPackungsgrosse();
        }

        if(1 == columnIndex) {
            bezeichnung = String.valueOf(aValue);
        }
        else if(2 == columnIndex) {
            bestand = StringConverter.toInteger(String.valueOf(aValue));
        }
        else if(3 == columnIndex) {
            preis = StringConverter.toDouble(String.valueOf(aValue));
        }
        else if(4 == columnIndex) {
            if(row instanceof Massengutartikel) {
                packgroesse = StringConverter.toInteger(String.valueOf(aValue));
            }
        }

        try {
//            Artikel alterArtikel = shop.gibArtikelNachNummer(row.getNummer());
//            // TODO diesen misst irgendwie nach unten schieben oder fixen
//            if(!alterArtikel.getBezeichnung().equals(bezeichnung) && alterArtikel.getBestand() != bestand && alterArtikel.getPreis() != preis) {
//                // TODO diesen misst irgendwie nach unten schieben oder fixen
//                if(alterArtikel.getBezeichnung().equals(bezeichnung) && alterArtikel.getBestand() == bestand && alterArtikel.getPreis() == preis && alterArtikel instanceof Massengutartikel && ((Massengutartikel)alterArtikel).getPackungsgrosse() == packgroesse) {
//                    return;
//                }
//
//            }

            shop.aendereArtikel(bezeichnung, row.getNummer(), bestand, preis, mitarbeiter, packgroesse);

            updateArtikel(shop.gibAlleArtikel());
        } catch (EingabeNichtLeerException | ArtikelbestandUnterNullException | ArtikelNichtVorhandenException | MassengutartikelBestandsException | IOException e) {
            JOptionPane.showMessageDialog(ArtikelTable.this, e.getMessage());
        }
    }

    public void setMitarbeiter(Nutzer nutzer) {
        this.mitarbeiter = (Mitarbeiter) nutzer;
        setIstMitarbeiterAngemeldet(true);
    }

//    public void setKunde(Nutzer nutzer) {
//        this.kunde = (Kunde) nutzer;
//        //setIstKundeAngemeldet(true);
//    }

    public void setIstMitarbeiterAngemeldet(boolean angemeldet){
        tableModel.setIstMitarbeiterAngemeldet(angemeldet);
    }

    @Override
    public void onEinkaufAbschliessen() {
        updateArtikel(shop.gibAlleArtikel());
    }

//    @Override
//    public void mouseClicked(MouseEvent e) {
//        //System.out.println(getValueAt(getSelectedRow(), getSelectedColumn()));
//        mouseClickListener.onMouseClick(kunde, artikel.get(getSelectedRow()));
//    }

}
