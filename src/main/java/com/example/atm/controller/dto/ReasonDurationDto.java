package com.example.atm.controller.dto;

import java.time.Duration;

public record ReasonDurationDto(String caseId, String reason, Duration duration) {
    public String getDuration() {
        return String.format("%02dд %02dч %02dм",
                duration.toDays(),
                duration.toHours() % 24,
                duration.toMinutes() % 60);
    }
}
