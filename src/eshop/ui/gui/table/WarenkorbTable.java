package eshop.ui.gui.table;

import eshop.domain.Eshop;
import eshop.ui.gui.model.ArtikelTableModel;
import eshop.ui.gui.model.WarenkorbTableModel;
import eshop.ui.gui.panel.WarenkorbPanel;
import eshop.valueobjects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class WarenkorbTable extends JTable implements WarenkorbPanel.ArtikelZuWarenkorbListener, MouseListener {
    private Eshop shop;
    private Kunde kunde;
    private WarenkorbTableModel warenkorbModel;
    private JTextField artikelnummerTextField;
    public WarenkorbTable(Eshop shop) {
        super();
        this.shop = shop;

        setVisible(false);
        addMouseListener(this);
    }

    public void updateWarenkorb(Warenkorb warenkorb){

        warenkorbModel = (WarenkorbTableModel) getModel();
        warenkorbModel.setWarenkorb(warenkorb);
    }

    public void setKunde(Nutzer kunde) {
        this.kunde = (Kunde) kunde;
        this.warenkorbModel = new WarenkorbTableModel(this.kunde.getWarkorb());
        setModel(warenkorbModel);
        updateWarenkorb(this.kunde.getWarkorb());
        setVisible(true);
    }

    public void setArtikelnummerTextField(JTextField artikelnummerTextField) {
        this.artikelnummerTextField = artikelnummerTextField;
    }

    @Override
    public void onArtikelZuWarenkorb() {
        updateWarenkorb(shop.getWarenkorb(kunde));
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
}
