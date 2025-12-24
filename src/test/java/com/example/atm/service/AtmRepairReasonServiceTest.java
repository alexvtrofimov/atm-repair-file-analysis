package com.example.atm.service;

import com.example.atm.IntegrationTest;
import com.example.atm.controller.dto.ReasonCountDto;
import com.example.atm.controller.dto.ReasonDurationDto;
import com.example.atm.controller.dto.ReasonRepeatDto;
import com.example.atm.controller.dto.ReasonRepeatInterfaceDto;
import com.example.atm.entity.AtmRepairReason;
import com.example.atm.exception.EmptySheetException;
import com.example.atm.exception.ExcelFirstRowException;
import com.example.atm.exception.ReadFileException;
import com.example.atm.util.excel.ExcelReader;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AtmRepairReasonServiceTest extends IntegrationTest {

    private final int EXPECTED_TOTAL_COUNT_ROW = 500;
    private final int EXPECTED_TOP_1_REASON_COUNT = 211;
    private final int EXPECTED_TOP_2_REASON_COUNT = 86;
    private final int EXPECTED_TOP_3_REASON_COUNT = 60;
    private final int EXPECTED_REPEAT_REASON_ATM_TOP = 3;
    private final int EXPECTED_REPEAT_REASON_ATM_LAST = 1;

    @Autowired
    private AtmRepairReasonService service;

    @Value("${testFileName}")
    private String testFileName;

    @Test
    @Order(2)
    void testAdd() throws ExcelFirstRowException, EmptySheetException, ReadFileException {
        URL resourceUrl = getClass().getClassLoader().getResource(testFileName);
        byte[] content = null;
        try {
            Path resourcePath = Paths.get(resourceUrl.toURI());
            content = Files.readAllBytes(resourcePath);
        } catch (Exception e) {

        }
        MultipartFile mockFile = new MockMultipartFile(testFileName, content);
        ExcelReader excelReader = new ExcelReader(mockFile);
        List<AtmRepairReason> atmRepairReasons = excelReader.readSheet(0);
        service.add(atmRepairReasons);
        assertEquals(EXPECTED_TOTAL_COUNT_ROW, service.count());
    }

    @Test
    @Order(1)
    void testDeleteAll() {
        service.deleteAll();
        assertEquals(0, service.count());
    }

    @Test
    @Order(3)
    void testGetAll() {
        assertEquals(EXPECTED_TOTAL_COUNT_ROW, service.getAll().size());
    }

    @Test
    @Order(4)
    void testGetTop3Reason() {
        List<ReasonCountDto> top3Reasons = service.getTop3Reason();
        assertEquals(EXPECTED_TOP_1_REASON_COUNT, top3Reasons.get(0).count());
        assertEquals(EXPECTED_TOP_2_REASON_COUNT, top3Reasons.get(1).count());
        assertEquals(EXPECTED_TOP_3_REASON_COUNT, top3Reasons.get(2).count());
    }

    @Test
    @Order(5)
    void testGetTop3Duration() {
        List<ReasonDurationDto> top3Duration = service.getTop3Duration();
        assertEquals("218810091", top3Duration.get(0).caseId());
        assertEquals("218818827", top3Duration.get(1).caseId());
        assertEquals("218795847", top3Duration.get(2).caseId());
    }

//    @Disabled("Not work H2 sql query with INTERVAL")
    @Test
    @Order(6)
    void testGetRepeatRepairs() {
        List<ReasonRepeatDto> repeatRepairs = service.getRepeatRepairs15days();
        assertEquals(EXPECTED_REPEAT_REASON_ATM_TOP, repeatRepairs.get(0).getCount());
        assertEquals(EXPECTED_REPEAT_REASON_ATM_LAST, repeatRepairs.getLast().getCount());
    }

}