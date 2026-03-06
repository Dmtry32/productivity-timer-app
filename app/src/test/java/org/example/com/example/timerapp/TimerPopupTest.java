package com.example.timerapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TimerPopupTest {
    @Test
    void testTimerInitialization() {
        TimerPopup timer = new TimerPopup(1000);
        assertNotNull(timer);
        // Add more tests as needed; for E2E, simulate popup with Robot (advanced)
    }
}