package kz.ibrahim.SoftwareProject.controllers;


import jakarta.validation.Valid;
import kz.ibrahim.SoftwareProject.models.Contest;
import kz.ibrahim.SoftwareProject.services.ContestService;
import kz.ibrahim.SoftwareProject.services.StudentService;
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
    private final StudentService studentService;

    private final ContestValidator contestValidator;

    @Autowired
    public ContestListController(ContestService contestService, StudentService studentService, ContestValidator contestValidator) {
        this.contestService = contestService;
        this.studentService = studentService;
        this.contestValidator = contestValidator;
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

    @PostMapping()
    public String createContest(@ModelAttribute("contest") @Valid Contest contest, BindingResult contestBindingResult) throws IOException {
        contestValidator.validate(contest, contestBindingResult);
        if (contestBindingResult.hasErrors()) {
            return "contest/new";
        }
        contestService.save(contest);

        return "redirect:/contest";
    }

    @PostMapping("/update")
    public String update() throws IOException {
        studentService.updateRating();
        return "redirect:";
    }

}
