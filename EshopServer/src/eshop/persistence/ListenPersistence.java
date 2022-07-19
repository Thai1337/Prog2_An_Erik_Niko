package eshop.persistence;

import java.io.*;
import java.util.List;
import java.util.Vector;

public class ListenPersistence<T>{

    private final String dateiname;
    public ListenPersistence(String dateiname){
        this.dateiname = dateiname;
    }

    public void speichernListe(List<T> list) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("EshopServer/src/eshop/persistence/daten/" + dateiname + ".txt"));
        for (T objekt : list){
            oos.writeObject(objekt);
        }
        oos.close();
    }

    public List<T> ladenListe() throws IOException {
        //ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./src/eshop/persistence/daten/" + dateiname + ".txt"));
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("EshopServer/src/eshop/persistence/daten/" + dateiname + ".txt"));
        //EshopServer\src\eshop\persistence

        System.out.println(ois);
        List<T> list = new Vector<>();
        boolean cont = true;
        while (cont) {
            try {
                list.add((T) ois.readObject());
            }catch (Exception e){
                cont = false;
            }
        }
        ois.close();
        System.out.println(list);
        return list;
    }

}
