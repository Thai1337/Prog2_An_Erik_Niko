package eshop.ui.gui.chart;

import eshop.domain.exceptions.ArtikelNichtVorhandenException;
import eshop.net.rmi.common.EshopSerializable;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.KundenProtokoll;
import eshop.valueobjects.MitarbeiterProtokoll;
import eshop.valueobjects.Protokoll;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

/*
 * Externe Bibliothek JFreeCharts wurde hier genutzt
 * Quelle: https://www.jfree.org/jfreechart/https://www.jfree.org/jfreechart/
 */
public class BestandshistorieChart extends JFrame {

    private EshopSerializable shop;
    private int artikelnummer;

    /**
     *
     * @param applicationTitle
     * @param chartTitle
     * @param bestand
     * @param shop
     * @param artikelnummer
     */
    public BestandshistorieChart(String applicationTitle, String chartTitle, List<Protokoll> bestand, EshopSerializable shop, int artikelnummer) {
        super(applicationTitle);
        this.shop = shop;
        this.artikelnummer = artikelnummer;
        JFreeChart xylineChart = ChartFactory.createXYLineChart(chartTitle , "Tage" , "Bestand" , createDataset(bestand) , PlotOrientation.VERTICAL , true , true , false);

        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        final XYPlot plot = xylineChart.getXYPlot( );

        final NumberAxis range = (NumberAxis) plot.getDomainAxis();
        range.setRange(new Range(0, 30));
        range.setTickUnit(new NumberTickUnit(1));

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setSeriesPaint( 0 , Color.RED );
        renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
        plot.setRenderer( renderer );
        setContentPane( chartPanel );

        setSize(new Dimension(700, 500));
        setVisible(true);
    }

    /**
     *
     * @param bestandsWerte
     * @return
     */
    private XYDataset createDataset(List<Protokoll> bestandsWerte) {
        //boolean einzigartig = true;

        //Collections.reverse(bestandsWerte);

        LocalDateTime heute = LocalDateTime.now();
        //heute.getDayOfMonth();

        Artikel artikel;
        try {
            artikel = shop.gibArtikelNachNummer(artikelnummer);
        } catch (ArtikelNichtVorhandenException | RemoteException e) {
            throw new RuntimeException(e);
        }

        final XYSeries bestand = new XYSeries(artikel.getBezeichnung()); // Eine ganz Linie (Graph)


        List<Protokoll> letzterBestandVomTagProtokoll =  new ArrayList<Protokoll>(); // Liste der Protokolle, welche das letzte Protokoll von jedem Tag speichert

        for (int i = 0; i < bestandsWerte.size(); i++) {
            int j = i + 1;
            if(j < bestandsWerte.size() && bestandsWerte.get(i).getDatum().getDayOfYear() != bestandsWerte.get(j).getDatum().getDayOfYear()) { //vergleicht die Tage des Protokolls(i) mit dem nächsten Protokoll(j = i + 1) in der Liste, sobald der Tag nicht gleich ist, wird der Befehl ausgeführt mit dem letzten Protokoll des Tages
                letzterBestandVomTagProtokoll.add(bestandsWerte.get(i));// fügt das letzte Protokoll des Tages zur Liste von Protokollen hinzu
            }
        }

        //System.out.println(letzterBestandVomTagProtokoll);

        if(bestandsWerte.size() != 0) { // wenn die Liste nicht Leer ist ...
            letzterBestandVomTagProtokoll.add(bestandsWerte.get(bestandsWerte.size()-1)); // ... wird der letzte Wert vom Array zur Liste hinzugefügt.(Sonst würde der letzte Wert im Graph fehlen, weil i mit i+1 verglichen wird und es der letzte Wert ist)
        }



        List<Integer> letzterBestandVomTagInteger = new ArrayList<Integer>(); // speichert die Integer-Werte des Bestands der Protokolle aus der letzterBestandVomTagProtokoll Liste

        int differenzTage = 1;

        /*
            for-schleife welche die Differenz der Tage zwischen 2 Protokollen vergleicht
         */
        for (int i = 0; i < letzterBestandVomTagProtokoll.size(); i++) {
            int j = i + 1;
            if(j < letzterBestandVomTagProtokoll.size()) { // Abfrage, um den index nicht zu überschreiten
                differenzTage = letzterBestandVomTagProtokoll.get(j).getDatum().getDayOfYear() - letzterBestandVomTagProtokoll.get(i).getDatum().getDayOfYear(); // differenz der letzten Protokolle von verschiedenen Tagen
            }
            if(j == letzterBestandVomTagProtokoll.size()){//damit der letzte Wert angezeigt wird
                differenzTage = heute.getDayOfYear() - letzterBestandVomTagProtokoll.get(i).getDatum().getDayOfYear(); // Berechnet die Differenz vom letzten Protokoll in der Liste mit dem aktuellen Tag im selben Monat
                if(differenzTage == 0) {
                    differenzTage = 1; // muss auf eins sein, weil die darauffolgende for-schleife mindestens einmal durchlaufen werden muss, um einen Wert hinzuzufügen
                }
            }

            //System.out.println(differenzTage);

            /*
                 for-schleife, welche den gleichen Bestand so oft in die Liste speichert wie groß die Differenz ist
             */
            for (int k = 0; k < differenzTage; k++) {
                if(letzterBestandVomTagProtokoll.get(i) instanceof MitarbeiterProtokoll ) {
                    letzterBestandVomTagInteger.add(((MitarbeiterProtokoll) letzterBestandVomTagProtokoll.get(i)).getArtikel().getBestand());// wenn das Protokoll ein Mitarbeiterprotokoll ist, wird der Bestandswert in die Liste gespeichert
                }
                if(letzterBestandVomTagProtokoll.get(i) instanceof KundenProtokoll ) { // wenn das Protokoll ein Kundenprotokoll ist, wird die Warenkorb Map durchsucht nach dem gesuchten Artikel


                    for (Map.Entry<Artikel, Integer> entry: ((KundenProtokoll) letzterBestandVomTagProtokoll.get(i)).getWarenkorb().getWarenkorbListe().entrySet()) {

                        if(entry.getKey().equals(artikel)){
                            int artikelbestand = entry.getKey().getBestand();

                            letzterBestandVomTagInteger.add(artikelbestand);
                        }
                    }
                }
            }
        }
       //System.out.println(letzterBestandVomTagInteger);

        /*
            for-schleife, welche alle Werte dem Graphen hinzufügt.
         */
        for (int i = 0; i < letzterBestandVomTagInteger.size(); i++) {
            bestand.add(i, letzterBestandVomTagInteger.get(i));
        }
        final XYSeriesCollection dataset = new XYSeriesCollection( );
        dataset.addSeries( bestand );
        return dataset;
    }

}
