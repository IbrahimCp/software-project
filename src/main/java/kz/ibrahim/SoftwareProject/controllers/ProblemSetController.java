package kz.ibrahim.SoftwareProject.controllers;


import jakarta.validation.Valid;
import kz.ibrahim.SoftwareProject.models.Contest;
import kz.ibrahim.SoftwareProject.models.Problem;
import kz.ibrahim.SoftwareProject.services.ProblemService;
import kz.ibrahim.SoftwareProject.util.ProblemValidator;
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
@RequestMapping("/problem-set")
public class ProblemSetController {

    private final ProblemService problemService;

    private final ProblemValidator problemValidator;

    @Autowired
    public ProblemSetController(ProblemService problemService, ProblemValidator problemValidator) {
        this.problemService = problemService;
        this.problemValidator = problemValidator;
    }

    @GetMapping()
    public String index(Model model) {
            model.addAttribute("problems", problemService.findAll());
            return "problem-set/index";
    }

    @GetMapping("/new")
    public String newContest(@ModelAttribute("problem") Problem problem) {
        return "problem-set/new";
    }

    @PostMapping()
    public String createContest(@ModelAttribute("problem") @Valid Problem problem, BindingResult problemBindingResult) throws IOException {
        problemValidator.validate(problem, problemBindingResult);
        if (problemBindingResult.hasErrors()) {
            return "problem-set/new";
        }

        problemService.save(problem);

        return "redirect:/problem-set";
    }

}
