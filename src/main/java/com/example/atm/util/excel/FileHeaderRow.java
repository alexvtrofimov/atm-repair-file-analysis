package com.example.atm.util.excel;

public enum FileHeaderRow {
    CASE_ID("CASE ID"),
    ATM_ID("ATM ID"),
    REASON_NAME("Название управляющей причины"),
    BEGIN("Начало"),
    END("Окончание"),
    SERIAL("Серийный номер"),
    BANK_NM("BANK_NM"),
    CHANNEL("Канал связи");

    private final String value;

    FileHeaderRow(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
