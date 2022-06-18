package eshop.ui.gui.panel;

import eshop.ui.gui.model.ArtikelTableModel;
import eshop.valueobjects.Artikel;

import javax.swing.*;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.TableRowSorter;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

public class ArtikelTablePanel extends JTable {

    private  ArtikelTableModel tableModel;
    public ArtikelTablePanel(List<Artikel> artikel) {
        super();

        this.tableModel = new ArtikelTableModel(artikel);

        setModel(tableModel);

        updateArtikel(artikel);
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
}
