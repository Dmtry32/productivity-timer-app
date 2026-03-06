package com.example.timerapp;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

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

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 320));
        setContentPane(chartPanel);

        setVisible(true);
    }
}