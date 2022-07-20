package eshop.ui.gui.menu;

import eshop.ui.gui.iframe.LoginIFrame;
import eshop.ui.gui.panel.ArtikelLoeschenPanel;
import eshop.valueobjects.Mitarbeiter;
import eshop.valueobjects.Nutzer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArtikelMenu extends JMenu implements ActionListener, LoginIFrame.LoginListener {
    private JMenuItem artikelEinfuegenItem;
    private JMenuItem artikelLoeschenItem;

   private boolean sichtbarEinfuegen;
    private boolean sichtbarLoeschen;

    public interface ArtikelEinfuegenItemClickListener {
        public void onArtikelEinfuegenMenuItemClick(boolean sichtbar);
    }

    public interface ArtikelLoeschenItemClickListener {
        public void onArtikelLoeschenMenuItemClick(boolean sichtbar);
    }

    private ArtikelEinfuegenItemClickListener artikelEinfuegenItemClickListener;
    private ArtikelLoeschenItemClickListener artikelLoeschenItemClickListener;
    public ArtikelMenu(String title, ArtikelEinfuegenItemClickListener artikelEinfuegenItemClickListener, ArtikelLoeschenItemClickListener artikelLoeschenItemClickListener) {
        super(title);
        this.artikelEinfuegenItemClickListener = artikelEinfuegenItemClickListener;
        this.artikelLoeschenItemClickListener = artikelLoeschenItemClickListener;

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
                if(!sichtbarEinfuegen){
                    sichtbarEinfuegen = true;
                }else{
                    sichtbarEinfuegen = false;
                }
                System.out.println("Einfuegen wurde geklickt");
                artikelEinfuegenItemClickListener.onArtikelEinfuegenMenuItemClick(sichtbarEinfuegen);
                break;
            case "Löschen":
                 if(!sichtbarLoeschen){
                     sichtbarLoeschen = true;
             }else{
                     sichtbarLoeschen = false;
                 }
                artikelLoeschenItemClickListener.onArtikelLoeschenMenuItemClick(sichtbarLoeschen);
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
