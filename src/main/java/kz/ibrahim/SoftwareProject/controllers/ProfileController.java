package kz.ibrahim.SoftwareProject.controllers;


import kz.ibrahim.SoftwareProject.external.CodeForcesAdapter;
import kz.ibrahim.SoftwareProject.models.Problem;
import kz.ibrahim.SoftwareProject.repositories.StudentRepository;
import kz.ibrahim.SoftwareProject.services.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CodeForcesAdapter codeForcesAdapter;

    @Autowired
    private ProblemService problemService;
    @GetMapping("/{handle}")
    public String profile(Model model, @PathVariable String handle) throws IOException {
        model.addAttribute("handle", handle);
        model.addAttribute("student", studentRepository.findByHandle(handle));
        model.addAttribute("solvedProblems", codeForcesAdapter.getSolvedProblems(handle, problemService.findAll().stream().map(Problem::getUrl).toList()).size());
        model.addAttribute("totalProblems", problemService.findAll().size());
        return "profile/profile";
    }


}
