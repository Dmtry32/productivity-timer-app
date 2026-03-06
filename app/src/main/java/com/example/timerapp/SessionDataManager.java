package com.example.timerapp;

import java.io.*;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SessionDataManager {
    private static final String DATA_FILE = "sessions.json";
    private Map<YearMonth, Long> monthlyTotals = new TreeMap<>(); // total seconds per month

    public SessionDataManager() {
        loadData();
    }

    public void addSession(LocalDateTime dateTime, long seconds) {
        YearMonth month = YearMonth.from(dateTime);
        monthlyTotals.merge(month, seconds, Long::sum);
        saveData();
    }

    public Map<YearMonth, Long> getMonthlyTotals() {
        return new TreeMap<>(monthlyTotals);
    }

    private void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    YearMonth ym = YearMonth.parse(parts[0], DateTimeFormatter.ofPattern("yyyy-MM"));
                    long secs = Long.parseLong(parts[1]);
                    monthlyTotals.put(ym, secs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Map.Entry<YearMonth, Long> entry : monthlyTotals.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}