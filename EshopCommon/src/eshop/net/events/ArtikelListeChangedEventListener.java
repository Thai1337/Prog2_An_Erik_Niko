package eshop.net.events;

import eshop.valueobjects.Artikel;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ArtikelListeChangedEventListener extends Remote {
    public void onArtikelListeChangedEvent(List<Artikel> artikelList) throws RemoteException;
}
