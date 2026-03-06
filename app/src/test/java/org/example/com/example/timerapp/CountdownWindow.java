package com.example.timerapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;

public class CountdownWindow extends JFrame {
    private JLabel countdownLabel;
    private Timer timer;
    private long endTimeMillis;
    private boolean isRunning = true;

    public CountdownWindow(long durationMillis) {
        super("Productivity Timer");

        // Set end time based on current system time + duration (syncs with laptop clock)
        endTimeMillis = System.currentTimeMillis() + durationMillis;

        // UI setup: Small window, always on top, no resize
        setSize(200, 100);
        setResizable(false);
        setAlwaysOnTop(true); // Makes the window persistent and doesn't disappear behind others
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Closes app when window closed
        setLocationRelativeTo(null); // Center on screen

        // Label for countdown (e.g., "25:00" updating to "00:00")
        countdownLabel = new JLabel("Initializing...", SwingConstants.CENTER);
        countdownLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(countdownLabel, BorderLayout.CENTER);

        // Swing Timer: Updates every 1 second (1000 ms)
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCountdown();
            }
        });
        timer.start();

        setVisible(true); // Show the persistent window
    }

    private void updateCountdown() {
        if (!isRunning) return;

        long remainingMillis = endTimeMillis - System.currentTimeMillis();
        if (remainingMillis <= 0) {
            timer.stop();
            isRunning = false;
            countdownLabel.setText("00:00");
            showTimeUpAlert();
            // Optional: Reset for next cycle
            // endTimeMillis = System.currentTimeMillis() + (25 * 60 * 1000);
            // timer.start();
            // isRunning = true;
        } else {
            long minutes = (remainingMillis / 1000) / 60;
            long seconds = (remainingMillis / 1000) % 60;
            countdownLabel.setText(String.format("%02d:%02d", minutes, seconds));
        }
    }

    private void showTimeUpAlert() {
        // Beep sound
        Toolkit.getDefaultToolkit().beep();

        // Modal popup with current clock time (stays until OK clicked)
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        JOptionPane.showMessageDialog(this, "Time's up! Current clock: " + currentTime, "Productivity Timer", JOptionPane.INFORMATION_MESSAGE);
    }
}