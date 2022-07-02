package eshop.ui.gui.table;

import eshop.domain.Eshop;
import eshop.ui.gui.model.ArtikelTableModel;
import eshop.ui.gui.model.WarenkorbTableModel;
import eshop.ui.gui.panel.WarenkorbPanel;
import eshop.valueobjects.*;

import javax.swing.*;
import java.util.List;

public class WarenkorbTable extends JTable implements WarenkorbPanel.ArtikelZuWarenkorbListener {
    private Eshop shop;
    private Kunde kunde;
    private WarenkorbTableModel warenkorbModel;
    public WarenkorbTable(Eshop shop) {
        super();
        this.shop = shop;

        setVisible(false);
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

    @Override
    public void onArtikelZuWarenkorb() {
        updateWarenkorb(shop.getWarenkorb(kunde));
    }
}
