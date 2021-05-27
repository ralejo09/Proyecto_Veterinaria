/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.veterinaria.vista;

/**
 *
 * @author ALEJO CARMONA
 */
import com.veterinaria.controlador.ControladorVeterinaria;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JInternalFrame;

public class PieChartEx extends JInternalFrame {

    public PieChartEx() {

        initUI();
    }

    private void initUI() {

        DefaultPieDataset dataset = createDataset();

        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Gracica Servicio");
//        setLocationRelativeTo(null);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }

    private DefaultPieDataset createDataset() {

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Baño", 25);
        dataset.setValue("Consulta Medica", 27);
        dataset.setValue("Estetica", 24);
        dataset.setValue("Desparasitacion", 24);

        return dataset;
    }

    private JFreeChart createChart(DefaultPieDataset dataset) {

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Web servers market share",
                dataset,
                false, true, false);

        return pieChart;
    }

//    public static void main(String[] args) {
//
//        EventQueue.invokeLater(() -> {
//
//            PieChartEx ex = new PieChartEx();
//            ex.setVisible(true);
//        });
//    }
}
