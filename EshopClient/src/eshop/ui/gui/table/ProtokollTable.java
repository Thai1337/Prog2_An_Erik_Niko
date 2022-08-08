package eshop.ui.gui.table;

import eshop.net.rmi.common.EshopSerializable;
import eshop.ui.gui.model.ProtokollTableModel;
import eshop.ui.gui.panel.ArtikelEinfuegenPanel;
import eshop.ui.gui.panel.ArtikelLoeschenPanel;
import eshop.valueobjects.*;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ProtokollTable extends JTable implements MouseListener, ArtikelEinfuegenPanel.ArtikelEinfuegenListener, ArtikelTable.ArtikelBearbeitenListener, ArtikelLoeschenPanel.ArtikelLoeschenListener {

    private EshopSerializable shop;
    private List<Protokoll> protokollList;
    private Mitarbeiter mitarbeiter;
    //private Kunde kunde;
    private ProtokollTableModel tableModel;
    public ProtokollTable(EshopSerializable shop) {
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

        sorter.setComparator(1, new Comparator<String>(){
            @Override
            public int compare(String o1, String o2) {
                Date datum1 = null;
                Date datum2 = null;
                try {
                    datum1 = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(o1);
                    datum2 = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(o2);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                return datum1.compareTo(datum2);
            }
        });
        this.setRowSorter(sorter);
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
    /*
        MouseClicked Listener wird nur als Trigger verwendet um die Tabellenmethoden wie getValueAt() aufzurufen.
    */
    @Override
    public void mouseClicked(MouseEvent e) {

        try {
            List<Protokoll> protokollList = shop.getProtokollListe();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        int protokollNummer = (Integer) getValueAt(getSelectedRow(), 0);
        Protokoll protokoll = protokollList.get(protokollNummer);
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

    @Override
    public void onArtikelEinfuegen(List<Artikel> artikelList) {
        try {
            updateProtokoll(shop.getProtokollListe());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onArtikelBearbeiten() {
        try {
            updateProtokoll(shop.getProtokollListe());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onArtikelLoeschen(List<Artikel> artikelList) {
        try {
            updateProtokoll(shop.getProtokollListe());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
