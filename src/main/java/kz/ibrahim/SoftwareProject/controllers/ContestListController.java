package kz.ibrahim.SoftwareProject.controllers;


import kz.ibrahim.SoftwareProject.repositories.ContestRepository;
import kz.ibrahim.SoftwareProject.services.ContestService;
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

    private final ContestService contestService;
    @Autowired
    public ContestListController(ContestService contestService) {
        this.contestService = contestService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("upcomingContests", contestService.findAllUpcoming());
        model.addAttribute("recentContests", contestService.findAllRecent());

        return "contest/index";
    }

}
