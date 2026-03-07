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
import java.util.Map;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SessionDataManager {
    private static final String DATA_FILE = "daily_sessions.json";
    private Map<LocalDate, Long> dailyTotals = new TreeMap<>(); // date → total seconds that day

//    public SessionDataManager() {
//        loadData();
//    }
//
//    public void addSession(LocalDateTime dateTime, long seconds) {
//        LocalDate date = dateTime.toLocalDate();
//        dailyTotals.merge(date, seconds, Long::sum);
//        saveData();
//    }

    public Map<LocalDate, Long> getDailyTotals() {
        return new TreeMap<>(dailyTotals);
    }

    private void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    LocalDate date = LocalDate.parse(parts[0], DateTimeFormatter.ISO_LOCAL_DATE);
                    long secs = Long.parseLong(parts[1]);
                    dailyTotals.put(date, secs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addSession(LocalDateTime dateTime, long seconds) {
        LocalDate date = dateTime.toLocalDate();
        dailyTotals.merge(date, seconds, Long::sum);
        System.out.println("Added to map: " + date + " += " + seconds + "s → total now " + dailyTotals.get(date));
        saveData();
    }
    public SessionDataManager() {
        loadData();
        System.out.println("Loaded " + dailyTotals.size() + " days from file");
        if (!dailyTotals.isEmpty()) {
            System.out.println("Example: " + dailyTotals.entrySet().iterator().next());
        }
    }
    private void saveData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Map.Entry<LocalDate, Long> entry : dailyTotals.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}