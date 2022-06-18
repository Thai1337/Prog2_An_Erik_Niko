package eshop.ui.gui.model;

import eshop.valueobjects.Artikel;
import eshop.valueobjects.Massengutartikel;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Vector;

public class ArtikelTableModel extends AbstractTableModel {
    private List<Artikel> artikel;

    private String[] spaltenNamen = { "Nummer", "Bezeichnung", "Bestand", "Preis" ,"Packungsgrosse" };

    public ArtikelTableModel(List<Artikel> aktuelleartikel) {
        artikel = new Vector<>(aktuelleartikel);
    }

    public void setArtikel(List<Artikel> aktuelleartikel) {
        artikel.clear();
        artikel.addAll(aktuelleartikel);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return artikel.size();
    }

    @Override
    public int getColumnCount() {
        return spaltenNamen.length;
    }

    @Override
    public String getColumnName(int col) {
        return spaltenNamen[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Artikel gewaehlterArtikel = artikel.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return gewaehlterArtikel.getNummer();
            case 1:
                return gewaehlterArtikel.getBezeichnung();
            case 2:
                return gewaehlterArtikel.getBestand();
            case 3:
                return gewaehlterArtikel.getPreis();
            case 4:
                if(gewaehlterArtikel instanceof Massengutartikel)
                    return ((Massengutartikel) gewaehlterArtikel).getPackungsgrosse();
                else
                    return 1;
            default:
                return null;
        }
    }
}
