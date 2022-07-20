package eshop.ui.gui.table;

import eshop.domain.exceptions.ArtikelNichtVorhandenException;
import eshop.domain.exceptions.ArtikelbestandUnterNullException;
import eshop.domain.exceptions.EingabeNichtLeerException;
import eshop.domain.exceptions.MassengutartikelBestandsException;
import eshop.net.rmi.common.eShopSerializable;
import eshop.ui.gui.StringConverter;
import eshop.ui.gui.model.ArtikelTableModel;
import eshop.ui.gui.panel.WarenkorbPanel;
import eshop.valueobjects.*;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Comparator;
import java.util.List;

public class ArtikelTable extends JTable implements WarenkorbPanel.EinkaufAbschliessenListener, MouseListener {
    private eShopSerializable shop;
    private List<Artikel> artikel;
    private Mitarbeiter mitarbeiter;
    //private Kunde kunde;
    private  ArtikelTableModel tableModel;



    public interface ArtikelBearbeitenListener {
        public void onArtikelBearbeiten();
    }
    private ArtikelBearbeitenListener artikelBearbeitenListener;
    private JTextField artikelnummerTextField;
    public ArtikelTable(eShopSerializable shop, ArtikelBearbeitenListener artikelBearbeitenListener) throws RemoteException {
        super();
        this.shop = shop;
        this.tableModel = new ArtikelTableModel(shop.gibAlleArtikel());
        this.artikelBearbeitenListener = artikelBearbeitenListener;
        artikel = tableModel.getArtikel(); // keine ahnung warum aber wenn ich das nicht hier hab gib es bei allen neue eingefügten artikeln in zeile 60 einen index out of bounds error wenn man den bestand ändert
        setModel(tableModel);

        updateArtikel(shop.gibAlleArtikel());
        addMouseListener(this);

    }

    public void updateArtikel(List<Artikel> artikel){
        sortiereArtikel();

        tableModel = (ArtikelTableModel) getModel();

        tableModel.setArtikel(artikel);
    }
    /*
        Methode welche für alle Spalten den default Comparator: string1.compareTo(string2) mit eigenen Implementierungen überschreibt
     */
    private void sortiereArtikel(){
        TableRowSorter<ArtikelTableModel> sorter = new TableRowSorter<ArtikelTableModel>(tableModel);
        sorter.setComparator(0, new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });

        sorter.setComparator(1, new Comparator<String>(){
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });

        sorter.setComparator(2, new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });

        sorter.setComparator(3, new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });

        sorter.setComparator(4, new Comparator<String>(){
            @Override
            public int compare(String o1, String o2) {
                String preis1s = o1.replace(",", ".");
                String preis2s = o2.replace(",", ".");
                double preis1 = Double.parseDouble(preis1s.replace("€", ""));
                double preis2 = Double.parseDouble(preis2s.replace("€", ""));
                return Double.compare(preis1, preis2);
            }
        });
        this.setRowSorter(sorter);
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
            if(row instanceof Massengutartikel) {
                packgroesse = StringConverter.toInteger(String.valueOf(aValue));
            }
        }
        else if(4 == columnIndex) {
            String preisS = aValue.toString().replace(",", ".");
            preis = StringConverter.toDouble(preisS.replace("€", ""));
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
            artikelBearbeitenListener.onArtikelBearbeiten();
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
    public void onEinkaufAbschliessen() throws RemoteException {
        updateArtikel(shop.gibAlleArtikel());
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int artikelNummer = (Integer) getValueAt(getSelectedRow(), 0);
        artikelnummerTextField.setText(String.valueOf(artikelNummer));
        artikelnummerTextField.setForeground(Color.BLACK);
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
//    public void mouseClicked(MouseEvent e) {
//        //System.out.println(getValueAt(getSelectedRow(), getSelectedColumn()));
//        mouseClickListener.onMouseClick(kunde, artikel.get(getSelectedRow()));
//    }


    public void setArtikelnummerTextField(JTextField artikelnummerTextField) {
        this.artikelnummerTextField = artikelnummerTextField;
    }
}
