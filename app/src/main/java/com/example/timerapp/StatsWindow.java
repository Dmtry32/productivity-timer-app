package com.example.timerapp;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.axis.NumberAxis;
import java.awt.BasicStroke;

import javax.swing.*;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class StatsWindow extends JFrame {
    public StatsWindow(SessionDataManager dataManager) {
        super("Monthly Work Statistics");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        Map<YearMonth, Long> totals = dataManager.getMonthlyTotals();
        for (Map.Entry<YearMonth, Long> entry : totals.entrySet()) {
            String monthStr = entry.getKey().format(formatter);
            double hours = entry.getValue() / 3600.0;
            dataset.addValue(hours, "Hours Worked", monthStr);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Monthly Focused Time (Hours)",
                "Month",
                "Hours",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        // ... after JFreeChart chart = ChartFactory.createLineChart(...)

// Force Y-axis to start at 0, but allow auto-adjustment with better scaling for small values
        org.jfree.chart.axis.NumberAxis rangeAxis = (org.jfree.chart.axis.NumberAxis) chart.getXYPlot().getRangeAxis();
        rangeAxis.setAutoRangeIncludesZero(true);           // Keep 0 as minimum
        rangeAxis.setAutoRangeMinimumSize(0.1);             // Minimum visible range (e.g. at least 0.1 hours)
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); // Nicer ticks if values grow

// Optional: Add some top padding for better look when data is small
        rangeAxis.setUpperMargin(0.2);                      // 20% extra space at top

// Optional: Make line thicker and add markers
        chart.getXYPlot().getRenderer().setSeriesStroke(0, new BasicStroke(2.0f)); // thicker line
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 320));
        setContentPane(chartPanel);

        setVisible(true);
    }
}