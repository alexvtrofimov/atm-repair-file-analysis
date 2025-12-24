package com.example.atm.entity;

import com.example.atm.util.DateTimeFormatterUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class AtmRepairReason {

    @Id
    private String caseId;

    @Column
    private String atmId;

    @Column
    private String reason;

    @Column
    //@DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime timeBegin;

    @Column
    private LocalDateTime timeEnd;

    @Column
    private String serialNumber;

    @Column
    private String bankNm;

    @Column
    private String channel;

    public AtmRepairReason(
            String caseId,
            String atmId,
            String reason,
            LocalDateTime timeBegin,
            LocalDateTime timeEnd,
            String serialNumber,
            String bankNm,
            String channel
    ) {
        this.caseId = caseId;
        this.atmId = atmId;
        this.reason = reason;
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
        this.serialNumber = serialNumber;
        this.bankNm = bankNm;
        this.channel = channel;
    }

    public AtmRepairReason() {
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getAtmId() {
        return atmId;
    }

    public void setAtmId(String atmId) {
        this.atmId = atmId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFormatTimeBegin() {
        return DateTimeFormatterUtil.getDateStringFormat(timeBegin);
    }

    public LocalDateTime getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(LocalDateTime timeBegin) {
        this.timeBegin = timeBegin;
    }

    public String getFormatTimeEnd() {
        return DateTimeFormatterUtil.getDateStringFormat(timeEnd);
    }

    public LocalDateTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalDateTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getBankNm() {
        return bankNm;
    }

    public void setBankNm(String bankNm) {
        this.bankNm = bankNm;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
