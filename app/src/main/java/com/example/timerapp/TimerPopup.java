package com.example.timerapp;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class TimerPopup {
    private long interval;

    public TimerPopup(long interval) {
        this.interval = interval;
    }

    public void start() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                showPopup();
            }
        }, interval, interval);
    }

    private void showPopup() {
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        JOptionPane.showMessageDialog(null, "Time's up! Current clock: " + currentTime, "Productivity Timer", JOptionPane.INFORMATION_MESSAGE);
    }
}