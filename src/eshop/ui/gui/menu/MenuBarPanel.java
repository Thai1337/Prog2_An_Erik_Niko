package eshop.ui.gui.menu;

import eshop.ui.gui.panel.LoginPanel;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.Nutzer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuBarPanel extends JMenuBar {

    public interface LoginButtonClickListener {
        public void onLoginButtonClick();
    }

    public interface RegistrierenButtonClickListener {
        public void onRegistrierenButtonClick();
    }

    private LoginButtonClickListener loginButtonClickListener;
    private RegistrierenButtonClickListener registrierenButtonClickListener;
    private JMenu kontoMenu;
    private JMenuItem loginItem;
    private JMenuItem registrierenItem;
    private JMenuItem logoutItem;
    public MenuBarPanel(LoginButtonClickListener loginButtonClickListener, RegistrierenButtonClickListener registrierenButtonClickListener) {
        this.loginButtonClickListener = loginButtonClickListener;
        setupMenu();
    }

    private void setupMenu() {
        kontoMenu = new KontoMenu("Konto");
        add(kontoMenu);
    }

    class KontoMenu extends JMenu implements ActionListener{
       public KontoMenu(String title) {
            super(title);
            // nur oberfläche action logik ist in der oberklasse
           loginItem = new JMenuItem("Anmelden");
           loginItem.addActionListener(this);
           add(loginItem);

           addSeparator();

           registrierenItem = new JMenuItem("Registrieren");
           registrierenItem.addActionListener(this);
           add(registrierenItem);

           //addSeparator();

           logoutItem = new JMenuItem("Abmelden");
           logoutItem.setVisible(false);
           logoutItem.addActionListener(this);
           add(logoutItem);
       }

        @Override
        public void actionPerformed(ActionEvent e) {
           String actionCommand = e.getActionCommand();
           switch (actionCommand){
               case "Anmelden":
                   System.out.println("Anmelden wurde geklickt");
                   loginButtonClickListener.onLoginButtonClick();
                   break;
               case "Registrieren":
                   System.out.println("Registrieren wurde geklickt");
                   registrierenButtonClickListener.onRegistrierenButtonClick();
                   break;
               case "Abmelden":
                   loginItem.setVisible(true); // vllt die 3 wo anders plazieren
                   registrierenItem.setVisible(true);
                   logoutItem.setVisible(false);
                   break;
           }

        }
    }

}
