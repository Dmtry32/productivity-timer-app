package com.example.timerapp;

public class App {
    public static void main(String[] args) {
        TimerPopup timer = new TimerPopup(25 * 60 * 1000); // 25 minutes in ms
        timer.start();
        System.out.println("Timer app running... Press Ctrl+C to stop.");
    }
}