package com.example.atm.controller;

import com.example.atm.entity.AtmRepairReason;
import com.example.atm.service.AtmRepairReasonService;
import com.example.atm.util.excel.ExcelReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private AtmRepairReasonService atmRepairReasonService;

    @GetMapping
    public String homePage(Model model) {
        long currentDbRows = atmRepairReasonService.count();
        model.addAttribute("isUpload", currentDbRows > 0);
        model.addAttribute("countRows", currentDbRows);
        return "home";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {
        long currentDbRows = atmRepairReasonService.count();
        model.addAttribute("isUpload", currentDbRows > 0);
        model.addAttribute("filename", file.getOriginalFilename());
        ExcelReader excelReader = new ExcelReader(file);
        try {
            List<AtmRepairReason> atmRepairReasonList = excelReader.readSheet(0);
            int countSaved = atmRepairReasonService.add(atmRepairReasonList);
            model.addAttribute("countRows", countSaved);
            model.addAttribute("isUpload", true);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteAll(Model model) {
        atmRepairReasonService.deleteAll();
        model.addAttribute("isUpload", false);
        return "redirect:/";
    }
}
