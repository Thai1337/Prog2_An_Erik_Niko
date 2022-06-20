package eshop.ui.gui.menu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBarPanel extends JMenuBar {

    public interface LoginMenuItemClickListener {
        public void onLoginMenuItemClick();
    }

    public interface RegistrierenMenuItemClickListener {
        public void onRegistrierenMenuItemClick();
    }

    public interface LogoutMenuItemClickListener {
        public void onLogoutMenuItemClick();
    }

    private LoginMenuItemClickListener loginMenuItemClickListener;
    private RegistrierenMenuItemClickListener registrierenMenuItemClickListener;
    private LogoutMenuItemClickListener logoutMenuItemClickListener;
    private JMenu kontoMenu;
    public MenuBarPanel(LoginMenuItemClickListener loginMenuItemClickListener, RegistrierenMenuItemClickListener registrierenMenuItemClickListener, LogoutMenuItemClickListener logoutMenuItemClickListener) {
        this.loginMenuItemClickListener = loginMenuItemClickListener;
        this.registrierenMenuItemClickListener = registrierenMenuItemClickListener;
        this.logoutMenuItemClickListener = logoutMenuItemClickListener;
        setupMenu();
    }

    private void setupMenu() {
        kontoMenu = new KontoMenu("Konto");
        add(kontoMenu);
    }

    class KontoMenu extends JMenu implements ActionListener{
        private JMenuItem loginItem;
        private JMenuItem registrierenItem;
        private JMenuItem logoutItem;
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
                   loginMenuItemClickListener.onLoginMenuItemClick();
                   break;
               case "Registrieren":
                   System.out.println("Registrieren wurde geklickt");
                   registrierenMenuItemClickListener.onRegistrierenMenuItemClick();
                   break;
               case "Abmelden":
                   loginItem.setVisible(true); // vllt die 3 wo anders plazieren
                   registrierenItem.setVisible(true);
                   logoutItem.setVisible(false);
                   logoutMenuItemClickListener.onLogoutMenuItemClick();
                   break;
           }

        }
    }

}
