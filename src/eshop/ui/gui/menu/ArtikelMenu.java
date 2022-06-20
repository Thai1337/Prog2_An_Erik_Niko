package eshop.ui.gui.menu;

import eshop.ui.gui.iframe.LoginIFrame;
import eshop.valueobjects.Mitarbeiter;
import eshop.valueobjects.Nutzer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArtikelMenu extends JMenu implements ActionListener, LoginIFrame.LoginListener {
    private JMenuItem artikelEinfuegenItem;
    private JMenuItem artikelLoeschenItem;
    private boolean sichtbar;

    public interface ArtikelEinfuegenItemClickListener {
        public void onArtikelEinfuegenMenuItemClick(boolean sichtbar);
    }

    private ArtikelEinfuegenItemClickListener artikelEinfuegenItemClickListener;
    public ArtikelMenu(String title, ArtikelEinfuegenItemClickListener artikelEinfuegenItemClickListener) {
        super(title);
        this.artikelEinfuegenItemClickListener = artikelEinfuegenItemClickListener;
        artikelEinfuegenItem = new JMenuItem("Einfügen");
        artikelEinfuegenItem.addActionListener(this);
        add(artikelEinfuegenItem);

        addSeparator();

        artikelLoeschenItem = new JMenuItem("Löschen");
        artikelLoeschenItem.addActionListener(this);
        add(artikelLoeschenItem);

        setVisible(false);

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
            case "Einfügen":
                if(!sichtbar){
                    sichtbar = true;
                }else{
                    sichtbar = false;
                }
                System.out.println("Einfuegen wurde geklickt");
                artikelEinfuegenItemClickListener.onArtikelEinfuegenMenuItemClick(sichtbar);
                break;
            case "Löschen":
                System.out.println("Loeschen wurde geklickt");
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
