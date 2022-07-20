package eshop.ui.gui.chart;

import eshop.domain.exceptions.ArtikelNichtVorhandenException;
import eshop.net.rmi.common.eShopSerializable;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

public class BestandshistorieChart extends JFrame {

    private eShopSerializable shop;
    private int artikelnummer;

    public BestandshistorieChart( String applicationTitle, String chartTitle, List<Protokoll> bestand, eShopSerializable shop, int artikelnummer) {
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

        final XYSeries bestand = new XYSeries(artikel.getBezeichnung());


        List<Protokoll> letzterBestandVomTagProtokoll =  new ArrayList<Protokoll>();

        for (int i = 0; i < bestandsWerte.size(); i++) {
            int j = i + 1;
            if(j < bestandsWerte.size() && bestandsWerte.get(i).getDatum().getDayOfMonth() != bestandsWerte.get(j).getDatum().getDayOfMonth()) {
                letzterBestandVomTagProtokoll.add(bestandsWerte.get(i));
            }
        }

        System.out.println(letzterBestandVomTagProtokoll);

        if(bestandsWerte.size() != 0) {
            letzterBestandVomTagProtokoll.add(bestandsWerte.get(bestandsWerte.size()-1));
        }



        List<Integer> letzterBestandVomTagInteger = new ArrayList<Integer>();

        int differenzTage = 1;

        for (int i = 0; i < letzterBestandVomTagProtokoll.size(); i++) {
            int j = i + 1;
            if(j < letzterBestandVomTagProtokoll.size()) {
                differenzTage = letzterBestandVomTagProtokoll.get(j).getDatum().getDayOfMonth() - letzterBestandVomTagProtokoll.get(i).getDatum().getDayOfMonth();
            }
            if(j == letzterBestandVomTagProtokoll.size()){
                differenzTage = heute.getDayOfMonth() - letzterBestandVomTagProtokoll.get(i).getDatum().getDayOfMonth();
                if(differenzTage == 0) {
                    differenzTage = 1;
                }
            }

            System.out.println(differenzTage);

            for (int k = 0; k < differenzTage; k++) {
                if(letzterBestandVomTagProtokoll.get(i) instanceof MitarbeiterProtokoll ) {
                    letzterBestandVomTagInteger.add(((MitarbeiterProtokoll) letzterBestandVomTagProtokoll.get(i)).getArtikel().getBestand());
                }
                if(letzterBestandVomTagProtokoll.get(i) instanceof KundenProtokoll ) {


                    for (Map.Entry<Artikel, Integer> entry: ((KundenProtokoll) letzterBestandVomTagProtokoll.get(i)).getWarenkorb().getWarenkorbListe().entrySet()) {

                        if(entry.getKey().equals(artikel)){
                            int artikelbestand = entry.getKey().getBestand();

                            letzterBestandVomTagInteger.add(artikelbestand);
                        }
                    }
                }
            }
        }
       System.out.println(letzterBestandVomTagInteger);
        for (int i = 0; i < letzterBestandVomTagInteger.size(); i++) {
            bestand.add(i, letzterBestandVomTagInteger.get(i));
        }
        final XYSeriesCollection dataset = new XYSeriesCollection( );
        dataset.addSeries( bestand );
        return dataset;
    }

}
