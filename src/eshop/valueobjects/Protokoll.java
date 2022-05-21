package eshop.valueobjects;

import java.util.Map;

public class Protokoll {
    private Map<Artikel, Integer> logMap;
    private Nutzer nutzer;
    private String datum;

    public Protokoll(Map<Artikel, Integer> logMap, Nutzer nutzer, String datum){
        this.logMap = logMap;
        this.nutzer = nutzer;
        this.datum = datum;
    }

    public Map<Artikel, Integer> getLogMap() {
        return logMap;
    }

    public void setLogMap(Map<Artikel, Integer> logMap) {
        this.logMap = logMap;
    }

    public Nutzer getNutzer() {
        return nutzer;
    }

    public void setNutzer(Nutzer nutzer) {
        this.nutzer = nutzer;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
}
