package com.example.atm.service;

import com.example.atm.controller.dto.ReasonCountDto;
import com.example.atm.controller.dto.ReasonDurationDto;
import com.example.atm.controller.dto.ReasonRepeatDto;
import com.example.atm.controller.dto.ReasonRepeatInterfaceDto;
import com.example.atm.entity.AtmRepairReason;
import com.example.atm.repository.AtmRepairReasonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    //   SQL solve
//    public List<ReasonCountDto> getTop3Reason() {
//        return atmRepairReasonRepository.getTop3Reason();
//    }

    // Java solve
    public List<ReasonCountDto> getTop3Reason() {
        List<ReasonCountDto> sortedAllReasonCount = atmRepairReasonRepository
                .findAll()
                .stream()
                .collect(Collectors.groupingBy(AtmRepairReason::getReason))
                .entrySet()
                .stream()
                .map(entry -> new ReasonCountDto(entry.getKey(), entry.getValue().size()))
                .sorted((el1, el2) -> Long.compare(el2.count(), el1.count()))
                .toList();
        return sortedAllReasonCount.subList(0, 3);
    }

    //   SQL solve
//    public List<ReasonDurationDto> getTop3Duration() {
//        return atmRepairReasonRepository.getTop3Duration();
//    }

    public List<ReasonDurationDto> getTop3Duration() {
        List<ReasonDurationDto> sortedAllReasonByDuration = atmRepairReasonRepository
                .findAll()
                .stream()
                .sorted((el1, el2) -> {
                    long durationEl1 = Duration.between(el1.getTimeBegin(), el1.getTimeEnd()).getSeconds();
                    long durationEl2 = Duration.between(el2.getTimeBegin(), el2.getTimeEnd()).getSeconds();
                    return Long.compare(durationEl2, durationEl1);
                })
                .map(reason -> new ReasonDurationDto(
                        reason.getCaseId(),
                        reason.getReason(),
                        Duration.between(reason.getTimeBegin(), reason.getTimeEnd())
                ))
                .toList();
        return sortedAllReasonByDuration.subList(0, 3);
    }

    // SQL solve
//    public List<ReasonRepeatInterfaceDto> getRepeatRepairs15days() {
//        return atmRepairReasonRepository.getRepeatRepairs15days();
//    }

    //Java solve
    public List<ReasonRepeatInterfaceDto> getRepeatRepairs15days() {
        List<AtmRepairReason> all = atmRepairReasonRepository.findAll();
        List<ReasonRepeatInterfaceDto> result = new ArrayList<>();
        for (AtmRepairReason reason : all) {
            long countRepeatReasons = all
                    .stream()
                    .filter(el -> {
                        boolean equalsReason = el.getReason().equals(reason.getReason());
                        LocalDateTime start = reason.getTimeBegin();
                        LocalDateTime end = reason.getTimeEnd().plusDays(15);
                        boolean isTimeBeginIn15daysPeriod =
                                (el.getTimeBegin().isEqual(start) || el.getTimeBegin().isAfter(start)) &&
                                (el.getTimeBegin().isEqual(end) || el.getTimeBegin().isBefore(end));
                        return !reason.getCaseId().equals(el.getCaseId()) && equalsReason && isTimeBeginIn15daysPeriod;
                    })
                    .count();
            if (countRepeatReasons > 0) {
                result.add(new ReasonRepeatDto(reason.getCaseId(), reason.getReason(), countRepeatReasons));
            }
        }
        return result
                .stream()
                .sorted((el1, el2) -> Long.compare(el2.getCount(), el1.getCount()))
                .toList();
    }
}
