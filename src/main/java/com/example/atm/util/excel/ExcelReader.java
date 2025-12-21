package com.example.atm.util.excel;

import com.example.atm.entity.AtmRepairReason;
import com.example.atm.exception.EmptySheetException;
import com.example.atm.exception.ExcelFirstRowException;
import com.example.atm.exception.ReadFileException;
import com.example.atm.util.DateTimeFormatterUtil;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class ExcelReader {
    private static final Logger log = LoggerFactory.getLogger(ExcelReader.class);
    private DataFormatter dataFormatter = new DataFormatter();
    private MultipartFile file;

    public ExcelReader(MultipartFile file) {
        this.file = file;
    }

    public List<AtmRepairReason> readSheet(int sheetIndex) throws ReadFileException, ExcelFirstRowException, EmptySheetException {
        try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            Iterator<Row> rowIterator = sheet.iterator();

            if (!rowIterator.hasNext()) {
                throw new EmptySheetException("Empty sheet");
            }

            Optional<String> errorFirstRowOptional = checkFirstRow(rowIterator.next());
            if (errorFirstRowOptional.isPresent()) {
                throw new ExcelFirstRowException(errorFirstRowOptional.get());
            }

            List<AtmRepairReason> atmRepairReasons = new ArrayList<>();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                AtmRepairReason repairReason = getAtmRepairReasonFromRow(row);
                atmRepairReasons.add(repairReason);
            }

            return atmRepairReasons;

        } catch (IOException e) {
            log.error(e.toString());
            throw new ReadFileException("Ошибка чтения файла");
        } catch (DateTimeParseException e) {
            throw new ReadFileException("Ошибка чтения файла: " + e.getMessage());
        }
    }

    private Optional<String> checkFirstRow(Row row) {
        FileHeaderRow[] headerValues = FileHeaderRow.values();
        for (int i = 0; i < headerValues.length; i++) {
            Cell cell = row.getCell(i);
            if (!cell.getStringCellValue().equals(headerValues[i].toString())) {
                return Optional.of(cell.getStringCellValue() + " is unknown field name");
            }
        }
        return Optional.empty();
    }

    private AtmRepairReason getAtmRepairReasonFromRow(Row row) {
        AtmRepairReason repairReason = new AtmRepairReason();
        for (int cellIndex = 0; cellIndex < FileHeaderRow.values().length; cellIndex++) {
            Cell cell = row.getCell(cellIndex);
            String strValue = dataFormatter.formatCellValue(cell);
            switch (FileHeaderRow.values()[cellIndex]) {
                case FileHeaderRow.CASE_ID -> repairReason.setCaseId(strValue);
                case FileHeaderRow.ATM_ID -> repairReason.setAtmId(strValue);
                case FileHeaderRow.REASON_NAME -> repairReason.setReason(strValue);
                case FileHeaderRow.BEGIN -> repairReason.setTimeBegin(LocalDateTime.parse(strValue, DateTimeFormatterUtil.dateExcelFormatter));
                case FileHeaderRow.END -> repairReason.setTimeEnd(LocalDateTime.parse(strValue, DateTimeFormatterUtil.dateExcelFormatter));
                case FileHeaderRow.SERIAL -> repairReason.setSerialNumber(strValue);
                case FileHeaderRow.BANK_NM -> repairReason.setBankNm(strValue);
                case FileHeaderRow.CHANNEL -> repairReason.setChannel(strValue);
            }
        }
        return repairReason;
    }

}
