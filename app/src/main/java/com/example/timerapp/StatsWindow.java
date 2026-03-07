package com.example.timerapp;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;

import javax.swing.*;
import java.awt.BasicStroke;
import java.awt.Dimension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class StatsWindow extends JFrame {

    private final SessionDataManager dataManager;

    public StatsWindow(SessionDataManager dataManager) {
        super("Daily Focused Time Statistics");
        this.dataManager = dataManager;

        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        rebuildChart();  // Build chart on first open

        setVisible(true);
    }

    public void rebuildChart() {
        getContentPane().removeAll();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");  // ← try full date

        Map<LocalDate, Long> totals = dataManager.getDailyTotals();
        System.out.println("Stats rebuild: found " + totals.size() + " daily entries");

        for (Map.Entry<LocalDate, Long> entry : totals.entrySet()) {
            String dayLabel = entry.getKey().format(dateFmt);
            double hours = entry.getValue() / 3600.0;
            dataset.addValue(hours, "Hours Worked", dayLabel);
            System.out.println("  → " + dayLabel + " : " + hours + " hours (" + entry.getValue() + " seconds)");
        }

        // Debug dataset
        System.out.println("Dataset row count: " + dataset.getRowCount());
        System.out.println("Dataset column count: " + dataset.getColumnCount());

        JFreeChart chart = ChartFactory.createLineChart(  // or createBarChart for test
                "Daily Focused Time (Hours)",
                "Date",
                "Hours",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

        // Force small visible range for testing
        rangeAxis.setLowerBound(0.0);
        rangeAxis.setUpperBound(Math.max(1.0, rangeAxis.getUpperBound())); // at least up to 1h
        rangeAxis.setAutoRange(false);

        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setUpperMargin(0.3);

        plot.getRenderer().setSeriesStroke(0, new BasicStroke(2.0f));
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(560, 320));
        setContentPane(chartPanel);

        revalidate();
        repaint();
    }