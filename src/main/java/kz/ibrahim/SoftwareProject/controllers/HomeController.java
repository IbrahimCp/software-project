package kz.ibrahim.SoftwareProject.controllers;


import kz.ibrahim.SoftwareProject.models.Handle;
import kz.ibrahim.SoftwareProject.models.Student;
import kz.ibrahim.SoftwareProject.services.ContestService;
import kz.ibrahim.SoftwareProject.services.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home")
public class HomeController {


    private final StudentService studentService;

    private final ContestService contestService;
    public HomeController(StudentService studentService, ContestService contestService) {
        this.studentService = studentService;
        this.contestService = contestService;
    }

    @GetMapping()
    public String home(Model model, @ModelAttribute("handle") Handle handle) {
        model.addAttribute("students", studentService.findAll(10));
        model.addAttribute("upcomingContests", contestService.findAllUpcoming());
        return "/home/home";
    }

    @PostMapping()
    public String show(@ModelAttribute("handle") Handle handle) {
        return "redirect:/profile/" + handle.getHandle();
    }


}
