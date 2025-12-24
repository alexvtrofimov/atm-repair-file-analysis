package com.example.atm.controller.dto;

public class ReasonRepeatDto {
    private String caseId;
    private String atmId;
    private String reason;
    private Long count;

    public ReasonRepeatDto(String caseId, String atmId, String reason, Long count) {
        this.caseId = caseId;
        this.atmId = atmId;
        this.reason = reason;
        this.count = count;
    }

    public String getCaseId() {
        return caseId;
    }

    public String getAtmId() {
        return atmId;
    }

    public String getReason() {
        return reason;
    }

    public Long getCount() {
        return count;
    }
}
