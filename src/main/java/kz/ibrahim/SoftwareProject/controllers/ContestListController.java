package kz.ibrahim.SoftwareProject.controllers;


import kz.ibrahim.SoftwareProject.repositories.ContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/contest")
public class ContestListController {

    @Autowired
    private ContestRepository contestRepository;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("upcomingContests", contestRepository.findAll().stream()
                .filter(contest -> contest.getContestDate().isAfter(LocalDateTime.now(ZoneOffset.ofHours(6)))) // Replace with appropriate condition for LocalDate if needed
                .collect(Collectors.toList()));

        model.addAttribute("recentContests", contestRepository.findAll().stream()
                .filter(contest -> contest.getContestDate().isBefore(LocalDateTime.now(ZoneOffset.ofHours(6)))) // Replace with appropriate condition for LocalDate if needed
                .collect(Collectors.toList()));
//        model.addAttribute("upcomingContests", contestRepository.findAll());
        System.out.println(LocalDateTime.now(ZoneOffset.ofHours(6)));
        return "contest/index";
    }

}
