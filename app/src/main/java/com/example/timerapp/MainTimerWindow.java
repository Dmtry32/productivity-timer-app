package com.example.timerapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainTimerWindow extends JFrame {
    private JLabel timeLabel;
    private JButton playPauseButton;
    private JButton statsButton;
    private Timer updateTimer;           // for UI refresh every second
    private long startTime = 0;          // millis when session started
    private long pausedTime = 0;         // accumulated paused time
    private long sessionStartMillis = 0; // for logging completed sessions
    private boolean isRunning = false;
    private SessionDataManager dataManager;
    private JButton finishButton;
    public MainTimerWindow() {
        super("Productivity Timer");
        dataManager = new SessionDataManager(); // handles save/load

        setSize(280, 180);
        setResizable(false);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Big time display (count-up)
        timeLabel = new JLabel("00:00:00", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 48));
        add(timeLabel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        playPauseButton = new JButton("Play");
        playPauseButton.setFont(new Font("Arial", Font.PLAIN, 18));
        playPauseButton.addActionListener(this::togglePlayPause);
        buttonPanel.add(playPauseButton);

        statsButton = new JButton("Stats");
        statsButton.setFont(new Font("Arial", Font.PLAIN, 18));
        statsButton.addActionListener(e -> showStatsWindow());
        buttonPanel.add(statsButton);

        finishButton = new JButton("Finish ✓");
        finishButton.setFont(new Font("Arial", Font.PLAIN, 18));
        finishButton.addActionListener(e -> finishSession());
        buttonPanel.add(finishButton);  // add to the same panel as Play and Stats

        add(buttonPanel, BorderLayout.SOUTH);

        // Timer to update display every second
        updateTimer = new Timer(1000, e -> updateDisplay());
        updateTimer.start(); // always running for live update when active

        setVisible(true);
    }

    private void togglePlayPause(ActionEvent e) {
        if (!isRunning) {
            // Start or resume
            if (startTime == 0) {
                // First start of session
                startTime = System.currentTimeMillis();
                sessionStartMillis = startTime;
            } else {
                // Resume after pause
                startTime = System.currentTimeMillis() - pausedTime;
            }
            playPauseButton.setText("Pause \u23F8");
            isRunning = true;
        } else {
            // Pause
            pausedTime = System.currentTimeMillis() - startTime;
            playPauseButton.setText("Play");
            isRunning = false;

            // Save completed session chunk
            long durationSec = pausedTime / 1000;
            if (durationSec > 30) { // ignore very short <30s
                dataManager.addSession(LocalDateTime.now(), durationSec);
            }
        }
    }

    private void updateDisplay() {
        long elapsedMillis;
        if (isRunning) {
            elapsedMillis = System.currentTimeMillis() - startTime;
        } else {
            elapsedMillis = pausedTime;
        }

        long hours = elapsedMillis / 3600000;
        long minutes = (elapsedMillis / 60000) % 60;
        long seconds = (elapsedMillis / 1000) % 60;

        timeLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));

        // Optional: change color when running
        timeLabel.setForeground(isRunning ? Color.GREEN : Color.GRAY);
    }

    private void showStatsWindow() {
        new StatsWindow(dataManager);
    }
    private void finishSession() {
        if (isRunning || pausedTime > 0) {
            long durationSec;
            if (isRunning) {
                durationSec = (System.currentTimeMillis() - startTime) / 1000;
            } else {
                durationSec = pausedTime / 1000;
            }

            if (durationSec > 30) {  // ignore tiny sessions
                dataManager.addSession(LocalDateTime.now(), durationSec);
            }

            // Reset everything
            startTime = 0;
            pausedTime = 0;
            sessionStartMillis = 0;
            isRunning = false;
            playPauseButton.setText("Play");
            timeLabel.setText("00:00:00");
            timeLabel.setForeground(Color.GRAY);
        }
    }
    @Override
    public void dispose() {
        // Save any ongoing session on close
        if (isRunning) {
            long durationSec = (System.currentTimeMillis() - startTime) / 1000;
            if (durationSec > 30) {
                dataManager.addSession(LocalDateTime.now(), durationSec);
            }
        }
        super.dispose();
    }
}