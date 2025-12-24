package com.example.atm.controller.dto;

public class ReasonRepeatDto implements ReasonRepeatInterfaceDto {
    private String caseId;
    private String reason;
    private Long count;

    public ReasonRepeatDto(String caseId, String reason, Long count) {
        this.caseId = caseId;
        this.reason = reason;
        this.count = count;
    }

    @Override
    public String getCaseId() {
        return caseId;
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public Long getCount() {
        return count;
    }
}
