package eshop.ui.gui.table;

import eshop.net.rmi.common.EshopSerializable;
import eshop.ui.gui.model.WarenkorbTableModel;
import eshop.ui.gui.panel.WarenkorbPanel;
import eshop.valueobjects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

public class WarenkorbTable extends JTable implements WarenkorbPanel.ArtikelZuWarenkorbListener, MouseListener {
    private EshopSerializable shop;
    private Kunde kunde;
    private WarenkorbTableModel warenkorbModel;
    private JTextField artikelnummerTextField;
    public WarenkorbTable(EshopSerializable shop) {
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
    public void onArtikelZuWarenkorb() throws RemoteException {
        updateWarenkorb(shop.getWarenkorb(kunde));
    }

    /*
        MouseClicked Listener wird nur als Trigger verwendet um die Tabellenmethoden wie getValueAt() aufzurufen.
     */
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
