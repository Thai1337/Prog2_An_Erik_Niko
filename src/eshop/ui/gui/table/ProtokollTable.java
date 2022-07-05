package eshop.ui.gui.table;

import eshop.domain.Eshop;
import eshop.domain.exceptions.ArtikelNichtVorhandenException;
import eshop.domain.exceptions.ArtikelbestandUnterNullException;
import eshop.domain.exceptions.EingabeNichtLeerException;
import eshop.domain.exceptions.MassengutartikelBestandsException;
import eshop.ui.gui.StringConverter;
import eshop.ui.gui.model.ArtikelTableModel;
import eshop.ui.gui.model.ProtokollTableModel;
import eshop.valueobjects.*;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class ProtokollTable extends JTable implements MouseListener {

    private Eshop shop;
    private List<Protokoll> protokollList;
    private Mitarbeiter mitarbeiter;
    //private Kunde kunde;
    private ProtokollTableModel tableModel;
    public ProtokollTable(Eshop shop) {
        super();
        this.shop = shop;
        try {
            this.tableModel = new ProtokollTableModel(shop.getProtokollListe());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        protokollList = tableModel.getProtokoll(); // keine ahnung warum aber wenn ich das nicht hier hab gib es bei allen neue eingefügten artikeln in zeile 60 einen index out of bounds error wenn man den bestand ändert
        setModel(tableModel);

        try {
            updateProtokoll(shop.getProtokollListe());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addMouseListener(this);
        setVisible(true);
    }

    public void updateProtokoll(List<Protokoll> protokoll){
        sortiereArtikel();

        tableModel = (ProtokollTableModel) getModel();

        tableModel.setProtokoll(protokoll);
    }

    private void sortiereArtikel(){
        TableRowSorter<ProtokollTableModel> sorter = new TableRowSorter<ProtokollTableModel>(tableModel);

        // TODO mit einem sorter.addRowSorterListener(); die sortierung im eshop machen vllt

        this.setRowSorter(sorter);

        sorter.setSortable(2, false);
        sorter.setSortable(3, false);
        sorter.setSortable(4, false);
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
    public void mouseClicked(MouseEvent e) {

        try {
            List<Protokoll> protokollList = shop.getProtokollListe();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Protokoll protokoll = protokollList.get(getSelectedRow());
        JOptionPane.showMessageDialog(this, protokoll);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

//    @Override
//    public void onEinkaufAbschliessen() {
//        updateProtokoll(shop.gibAlleArtikel());
//    }

//    @Override
//    public void mouseClicked(MouseEvent e) {
//        //System.out.println(getValueAt(getSelectedRow(), getSelectedColumn()));
//        mouseClickListener.onMouseClick(kunde, artikel.get(getSelectedRow()));
//    }
}
