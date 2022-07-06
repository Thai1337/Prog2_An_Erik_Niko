package eshop.ui.gui.model;

import eshop.valueobjects.*;

import javax.swing.table.AbstractTableModel;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Vector;

public class ProtokollTableModel extends AbstractTableModel {
    private List<Protokoll> protokollList;

    private boolean istMitarbeiterAngemeldet;
    private String[] spaltenNamen = { "ProtokollNummer","Datum", "NutzerTyp", "NutzerNummer", "NutzerName" ,"EreignisTyp" }; //col

    public ProtokollTableModel(List<Protokoll> aktuelleProtokollListe) {
        protokollList = new Vector<>(aktuelleProtokollListe);
    }

    public void setProtokoll(List<Protokoll> aktuelleProtokollListe) {
        protokollList.clear();
        protokollList.addAll(aktuelleProtokollListe);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return protokollList.size();
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

        Protokoll gewaehltesProtokoll = protokollList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex;
            case 1:
                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                return myFormatObj.format(gewaehltesProtokoll.getDatum());
            case 2:
                if(gewaehltesProtokoll instanceof MitarbeiterProtokoll)
                    return "Mitarbeiter";
                else
                    return "Kunde";
            case 3:
                if(gewaehltesProtokoll instanceof MitarbeiterProtokoll)
                    return ((MitarbeiterProtokoll) gewaehltesProtokoll).getMitarbeiter().getNummer();
                else
                    return ((KundenProtokoll) gewaehltesProtokoll).getKunde().getNummer();
            case 4:
                if(gewaehltesProtokoll instanceof MitarbeiterProtokoll)
                    return ((MitarbeiterProtokoll) gewaehltesProtokoll).getMitarbeiter().getName();
                else
                    return ((KundenProtokoll) gewaehltesProtokoll).getKunde().getName();
            case 5:
                return gewaehltesProtokoll.EreignisTyp();
            default:
                return null;
        }
    }

//    @Override
//    public boolean isCellEditable(int rowIndex, int columnIndex) {
//        if(!istMitarbeiterAngemeldet){
//            return false;
//        }

//        if(protokollList.get(rowIndex) instanceof Massengutartikel) {
//            return columnIndex == 1 || columnIndex == 2 || columnIndex == 3 || columnIndex == 4;
//        }
        //return columnIndex == 1 || columnIndex == 2 || columnIndex == 3;

//    }

    public void setIstMitarbeiterAngemeldet(boolean istMitarbeiterAngemeldet){
        this.istMitarbeiterAngemeldet = istMitarbeiterAngemeldet;
    }

    public List<Protokoll> getProtokoll() {
        return protokollList;
    }
}
