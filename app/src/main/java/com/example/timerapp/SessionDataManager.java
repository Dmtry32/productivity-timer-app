package com.example.timerapp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SessionDataManager {
    private static final String DATA_FILE = "daily_sessions.json";
    private Map<LocalDate, Long> dailyTotals = new TreeMap<>(); // date → total seconds that day

    public SessionDataManager() {
        loadData();
    }

    public void addSession(LocalDateTime dateTime, long seconds) {
        LocalDate date = dateTime.toLocalDate();
        dailyTotals.merge(date, seconds, Long::sum);
        saveData();
    }

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