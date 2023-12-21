package kz.ibrahim.SoftwareProject.controllers;


import jakarta.validation.Valid;
import kz.ibrahim.SoftwareProject.models.Contest;
import kz.ibrahim.SoftwareProject.services.ContestService;
import kz.ibrahim.SoftwareProject.util.AdminValidator;
import kz.ibrahim.SoftwareProject.util.ContestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/contest")
public class ContestListController {

    private final ContestService contestService;

    private final ContestValidator contestValidator;

    private final AdminValidator adminValidator;

    @Autowired
    public ContestListController(ContestService contestService, ContestValidator contestValidator, AdminValidator adminValidator) {
        this.contestService = contestService;
        this.contestValidator = contestValidator;
        this.adminValidator = adminValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("upcomingContests", contestService.findAllUpcoming());
        model.addAttribute("recentContests", contestService.findAllRecent());

        return "contest/index";
    }

    @GetMapping("/new")
    public String newContest(@ModelAttribute("contest") Contest contest) {
        return "contest/new";
    }

    // @ModelAttribute("admin") @Valid Contest admin, BindingResult adminBindingResult
    @PostMapping()
    public String createContest(@ModelAttribute("contest") @Valid Contest contest, BindingResult contestBindingResult) throws IOException {
        contestValidator.validate(contest, contestBindingResult);
//        adminValidator.validate(admin, adminBindingResult);
        if (contestBindingResult.hasErrors()) {
            return "contest/new";
        }
        System.out.println(contest);
        contestService.save(contest);
        System.out.println(contest.getUrl());
        return "redirect:/contest";
    }

}
