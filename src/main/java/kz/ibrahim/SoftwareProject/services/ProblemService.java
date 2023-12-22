package kz.ibrahim.SoftwareProject.services;

import kz.ibrahim.SoftwareProject.external.CodeForcesAdapter;
import kz.ibrahim.SoftwareProject.models.Contest;
import kz.ibrahim.SoftwareProject.models.Problem;
import kz.ibrahim.SoftwareProject.repositories.ProblemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProblemService {

    private final CodeForcesAdapter codeForcesService;

    private final ProblemRepository problemRepository;

    public ProblemService(CodeForcesAdapter codeForcesService, ProblemRepository problemRepository) {
        this.codeForcesService = codeForcesService;
        this.problemRepository = problemRepository;
    }

    public List<Problem> findAll() {
        return problemRepository.findAll();
    }

    @Transactional
    public void save(Problem problem) throws IOException {
        String[] temp = problem.getUrl().split("/");
        problem.setName(codeForcesService.getProblemName(problem.getUrl()));
        problemRepository.save(problem);
    }
}
