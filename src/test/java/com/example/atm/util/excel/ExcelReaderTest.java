package com.example.atm.util.excel;

import com.example.atm.entity.AtmRepairReason;
import com.example.atm.exception.EmptySheetException;
import com.example.atm.exception.ExcelFirstRowException;
import com.example.atm.exception.ReadFileException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class ExcelReaderTest {
    private static final Logger log = LoggerFactory.getLogger(ExcelReaderTest.class);
    private String testExcelFilename = "test_file.xlsx";
    private ExcelReader excelReader;

    @BeforeEach
    public void setExcelReader() {
        URL resourceUrl = getClass().getClassLoader().getResource(testExcelFilename);
        byte[] content = null;
        try {
            Path resourcePath = Paths.get(resourceUrl.toURI());
            content = Files.readAllBytes(resourcePath);
        } catch (Exception e) {
            log.error("Get test file error: " + e.getMessage());
        }
        MultipartFile mockFile = new MockMultipartFile(testExcelFilename, content);
        excelReader = new ExcelReader(mockFile);
    }

    @Test
    void testReadSheet() throws ExcelFirstRowException, EmptySheetException, ReadFileException {
        List<AtmRepairReason> atmRepairReasons = excelReader.readSheet(0);
        assertEquals(500, atmRepairReasons.size());
    }
}