package eshop.ui.gui.model;

import eshop.valueobjects.Artikel;
import eshop.valueobjects.Massengutartikel;
import eshop.valueobjects.Warenkorb;

import javax.swing.table.AbstractTableModel;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class WarenkorbTableModel extends AbstractTableModel {

    private String[] spaltenNamen = { "Nummer", "Bezeichnung", "Stueckpreis", "Menge" , "Preis" }; //col
    private Warenkorb warenkorb;

    public WarenkorbTableModel(Warenkorb warenkorb) {
        this.warenkorb = warenkorb;
    }

    public void setWarenkorb(Warenkorb aktuellerWarenkorb) {
        //warenkorb.warenkorbLeeren();
        warenkorb.getWarenkorbListe().putAll(aktuellerWarenkorb.getWarenkorbListe());
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return warenkorb.getWarenkorbListe().size();
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

        Object[] entries=warenkorb.getWarenkorbListe().entrySet().toArray();
        Map.Entry<Artikel, Integer> entry=(Map.Entry<Artikel, Integer>)entries[rowIndex];
        System.out.println(entry);
        switch (columnIndex) {
            case 0:
                return entry.getKey().getNummer();
            case 1:
                return entry.getKey().getBezeichnung();
            case 2:
                return entry.getKey().getPreis();
            case 3:
                return entry.getValue();
            case 4:
                return entry.getValue() * entry.getKey().getPreis();
            default:
                return null;
        }
    }
}
