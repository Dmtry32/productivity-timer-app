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
import java.awt.Dimension;
import java.awt.BasicStroke;
import javax.swing.*;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.time.LocalDate;
import java.time.LocalDateTime;
public class StatsWindow extends JFrame {
    public StatsWindow(SessionDataManager dataManager) {
        super("Monthly Work Statistics");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("MM-dd");  // e.g. 03-07

        Map<LocalDate, Long> totals = dataManager.getDailyTotals();
        for (Map.Entry<LocalDate, Long> entry : totals.entrySet()) {
            String dayLabel = entry.getKey().format(dateFmt);
            double hours = entry.getValue() / 3600.0;
            dataset.addValue(hours, "Hours Worked", dayLabel);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Daily Focused Time (Hours)",
                "Date",
                "Hours",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        // ... after JFreeChart chart = ChartFactory.createLineChart(...)
// Fix plot type
        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

// Better scaling for small values
        rangeAxis.setAutoRangeIncludesZero(true);
        rangeAxis.setAutoRangeMinimumSize(0.05);  // show even 3 minutes (0.05 h)
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setUpperMargin(0.3);

        plot.getRenderer().setSeriesStroke(0, new BasicStroke(2.0f));

// Rotate labels if many days
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(560, 320));
        setContentPane(chartPanel);

        setVisible(true);
    }
}