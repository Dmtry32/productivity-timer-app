package com.example.timerapp;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.axis.NumberAxis;
import java.awt.BasicStroke;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;

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
// Get the correct plot type (CategoryPlot, not XYPlot)
        org.jfree.chart.plot.CategoryPlot plot = chart.getCategoryPlot();

// Get axes
        org.jfree.chart.axis.CategoryAxis domainAxis = plot.getDomainAxis();
        org.jfree.chart.axis.NumberAxis rangeAxis = (org.jfree.chart.axis.NumberAxis) plot.getRangeAxis();

// Improve Y-axis for small values
        rangeAxis.setAutoRangeIncludesZero(true);
        rangeAxis.setAutoRangeMinimumSize(0.1);             // at least show up to 0.1 hours
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setUpperMargin(0.2);                      // extra space at top

// Thicker line (use the renderer for CategoryPlot)
        plot.getRenderer().setSeriesStroke(0, new BasicStroke(2.0f));  // thicker red line

// Optional: Rotate X-axis labels if months overlap
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);  // 45-degree angle for readability

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 320));
        setContentPane(chartPanel);

        setVisible(true);
    }
}