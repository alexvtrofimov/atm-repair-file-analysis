package com.example.atm.service;

import com.example.atm.controller.dto.ReasonCountDto;
import com.example.atm.controller.dto.ReasonDurationDto;
import com.example.atm.controller.dto.ReasonRepeatInterfaceDto;
import com.example.atm.entity.AtmRepairReason;
import com.example.atm.repository.AtmRepairReasonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtmRepairReasonService {
    private static final Logger log = LoggerFactory.getLogger(AtmRepairReasonService.class);

    @Autowired
    private AtmRepairReasonRepository atmRepairReasonRepository;

    public int add(List<AtmRepairReason> list) {
        return atmRepairReasonRepository.saveAll(list).size();
    }

    @CachePut("countDbRecords")
    public long count() {
        return atmRepairReasonRepository.count();
    }

    public void deleteAll() {
        atmRepairReasonRepository.deleteAllInBatch();
    }

    public List<AtmRepairReason> getAll() {
        return atmRepairReasonRepository.findAll();
    }

    public List<ReasonCountDto> getTop3Reason() {
        return atmRepairReasonRepository.getTop3Reason();
    }

    public List<ReasonDurationDto> getTop3Duration() {
        return atmRepairReasonRepository.getTop3Duration();
    }

    public List<ReasonRepeatInterfaceDto> getRepeatRepairs(int days) {
        return atmRepairReasonRepository.getRepeatRepairs(days);
    }
}
