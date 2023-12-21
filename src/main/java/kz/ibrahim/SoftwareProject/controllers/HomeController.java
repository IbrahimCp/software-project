package kz.ibrahim.SoftwareProject.controllers;

import kz.ibrahim.SoftwareProject.external.CodeForcesService;
import kz.ibrahim.SoftwareProject.models.Contest;
import kz.ibrahim.SoftwareProject.repositories.ContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private ContestRepository contestRepository;

    @GetMapping()
    public String index(Model model) throws IOException {


        CodeForcesService service = new CodeForcesService();
        String url = "https://oldmy.sdu.edu.kz/index.php?mod=calendar";
        contestRepository.save(new Contest(4, url,  service.getContestName(url), service.getContestStartTime(url)));

        return "home";
    }
}
