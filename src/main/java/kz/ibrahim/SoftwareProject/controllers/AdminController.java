package kz.ibrahim.SoftwareProject.controllers;


import jakarta.validation.Valid;
import kz.ibrahim.SoftwareProject.models.Contest;
import kz.ibrahim.SoftwareProject.models.Post;
import kz.ibrahim.SoftwareProject.models.Problem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping()
    public String admin(@ModelAttribute("post") Post post) {
        return "admin/new-post";
    }

    @GetMapping("/new-post")
    public String newPost(@ModelAttribute("post") Post post) {
        return "admin/new-post";
    }

    @GetMapping("/new-problem")
    public String newProblem(@ModelAttribute("problem") Problem problem) {
        return "admin/new-problem";
    }

    @GetMapping("/new-contest")
    public String newContest(@ModelAttribute("contest") Contest contest) {
        return "admin/new-contest";
    }

    @PostMapping("/new-post")
    public String createPost(@ModelAttribute("post") @Valid Post post, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/new-post";
        }
        return "redirect:/new-post";
    }

    @PostMapping("/new-problem")
    public String createProblem(@ModelAttribute("problem") @Valid Problem problem, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/new-problem";
        }

        return "redirect:/new-problem";
    }

    @PostMapping("/new-contest")
    public String createContest(@ModelAttribute("contest") @Valid Contest contest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/new-contest";
        }
        return "redirect:/new-contest";
    }

}
