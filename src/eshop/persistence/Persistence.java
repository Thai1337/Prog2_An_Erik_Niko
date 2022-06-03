package eshop.persistence;

import java.io.*;

public class Persistence<T> {
    private final String dateiname;
    public Persistence(String dateiname){
        this.dateiname = dateiname;
    }
    public void speichern(T objekt) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./src/eshop/persistence/daten/" + dateiname + ".txt"));
        oos.writeObject(objekt);
        oos.close();
    }

    public T laden() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./src/eshop/persistence/daten/" + dateiname + ".txt"));
        T object = (T) ois.readObject();
        ois.close();
        return object;
    }

}
