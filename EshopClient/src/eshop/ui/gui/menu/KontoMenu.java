package eshop.ui.gui.menu;

import eshop.ui.gui.iframe.LoginIFrame;
import eshop.valueobjects.Nutzer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KontoMenu extends JMenu implements ActionListener, LoginIFrame.LoginListener {
    public interface LoginMenuItemClickListener {
        public void onLoginMenuItemClick();
    }

    public interface RegistrierenMenuItemClickListener {
        public void onRegistrierenMenuItemClick();
    }

    public interface LogoutMenuItemClickListener {
        public void onLogoutMenuItemClick();
    }
    private String title;
    private JMenuItem loginItem;
    private JMenuItem registrierenItem;
    private JMenuItem logoutItem;
    private LoginMenuItemClickListener loginMenuItemClickListener;
    private RegistrierenMenuItemClickListener registrierenMenuItemClickListener;
    private LogoutMenuItemClickListener logoutMenuItemClickListener;

    //private LogoutMenuItemClickListener logoutArtikelMenuItemClickListener;

    public KontoMenu(String title, LoginMenuItemClickListener loginMenuItemClickListener, RegistrierenMenuItemClickListener registrierenMenuItemClickListener,
                     LogoutMenuItemClickListener logoutMenuItemClickListener) {
        super(title);
        this.title = title;
        this.loginMenuItemClickListener = loginMenuItemClickListener;
        this.registrierenMenuItemClickListener = registrierenMenuItemClickListener;
        this.logoutMenuItemClickListener = logoutMenuItemClickListener;

        loginItem = new JMenuItem("Anmelden");
        loginItem.addActionListener(this);
        add(loginItem);

        addSeparator();

        registrierenItem = new JMenuItem("Registrieren");
        registrierenItem.addActionListener(this);
        add(registrierenItem);

        logoutItem = new JMenuItem("Abmelden");
        logoutItem.setVisible(false);
        logoutItem.addActionListener(this);
        add(logoutItem);
    }

    public void separatorVisible(boolean visible) {
        getMenuComponent(1).setVisible(visible);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
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
                separatorVisible(true);
                setText(title);
                break;
        }
    }

    @Override
    public void onLogin(Nutzer nutzer) {
        System.out.println("BE GONE!!!! login und registrieren");
        loginItem.setVisible(false);
        registrierenItem.setVisible(false);
        logoutItem.setVisible(true);
        separatorVisible(false);
        setText(nutzer.getName());
    }
}
