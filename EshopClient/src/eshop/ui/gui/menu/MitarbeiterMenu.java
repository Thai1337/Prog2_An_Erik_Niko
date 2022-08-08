package eshop.ui.gui.menu;

import eshop.ui.gui.iframe.LoginIFrame;
import eshop.valueobjects.Mitarbeiter;
import eshop.valueobjects.Nutzer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MitarbeiterMenu extends JMenu implements ActionListener, LoginIFrame.LoginListener {
    private JMenuItem mitarbeiterEinfuegenItem;
    private boolean sichtbarHinzufuegen;

    public interface MitarbeiterHinzufuegenItemClickListener {
        public void onMitarbeiterHinzufuegenMenuItemClick(boolean sichtbar);
    }
    private MitarbeiterHinzufuegenItemClickListener mitarbeiterHinzufuegenItemClickListener;
    public MitarbeiterMenu(String title, MitarbeiterHinzufuegenItemClickListener mitarbeiterHinzufuegenItemClickListener) {
        super(title);
        this.mitarbeiterHinzufuegenItemClickListener = mitarbeiterHinzufuegenItemClickListener;
        mitarbeiterEinfuegenItem = new JMenuItem("Einstellen");
        mitarbeiterEinfuegenItem.addActionListener(this);
        add(mitarbeiterEinfuegenItem);

        setVisible(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
            case "Einstellen":
                if(!sichtbarHinzufuegen){
                    sichtbarHinzufuegen = true;
                }else{
                    sichtbarHinzufuegen = false;
                }
                mitarbeiterHinzufuegenItemClickListener.onMitarbeiterHinzufuegenMenuItemClick(sichtbarHinzufuegen);
                break;
        }
    }

    @Override
    public void onLogin(Nutzer nutzer) {
        if(nutzer instanceof Mitarbeiter){
            setVisible(true);
        }
    }
}
