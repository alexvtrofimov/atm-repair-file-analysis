package com.example.atm.controller;

import com.example.atm.controller.dto.ReasonCountDto;
import com.example.atm.controller.dto.ReasonDurationDto;
import com.example.atm.controller.dto.ReasonRepeatDto;
import com.example.atm.controller.dto.ReasonRepeatInterfaceDto;
import com.example.atm.entity.AtmRepairReason;
import com.example.atm.service.AtmRepairReasonService;
import com.example.atm.util.excel.FileHeaderRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/statistic")
public class StatisticController {

    @Autowired
    private AtmRepairReasonService atmRepairReasonService;

    @GetMapping
    public String statisticPage(Model model) {
        List<AtmRepairReason> all = atmRepairReasonService.getAll();
        model.addAttribute("records", all);
        model.addAttribute("headers", FileHeaderRow.values());
        return "statistic";
    }

    @GetMapping("/all")
    public String all(Model model) {
        List<AtmRepairReason> all = atmRepairReasonService.getAll();
        model.addAttribute("records", all);
        model.addAttribute("headers", FileHeaderRow.values());
        return "statistic";
    }

    @GetMapping("/top3reasons")
    public String top3Reasons(Model model) {
        List<ReasonCountDto> top3Reason = atmRepairReasonService.getTop3Reason();
        model.addAttribute("top3Reason", top3Reason);
        return "statistic";
    }

    @GetMapping("/top3duration")
    public String top3Duration(Model model) {
        List<ReasonDurationDto> top3Reason = atmRepairReasonService.getTop3Duration();
        model.addAttribute("top3Duration", top3Reason);
        return "statistic";
    }

    @GetMapping("/repeatRepairs")
    public String repeatRepairs15days(Model model) {
        List<ReasonRepeatDto> repeatRepairs = atmRepairReasonService.getRepeatRepairs15days();
        model.addAttribute("repeatRepairs", repeatRepairs);
        return "statistic";
    }
}
