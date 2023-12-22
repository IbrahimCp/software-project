package kz.ibrahim.SoftwareProject.services;

import kz.ibrahim.SoftwareProject.external.CodeForcesAdapter;
import kz.ibrahim.SoftwareProject.models.Contest;
import kz.ibrahim.SoftwareProject.models.Problem;
import kz.ibrahim.SoftwareProject.repositories.ProblemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    public List<Problem> findAll(String handle) throws IOException {

        List<Problem> problems = problemRepository.findAll();

        Map<String, LocalDateTime> solved = codeForcesService.
                getSolvedProblems(handle, problems.stream().
                map(Problem::getUrl).toList());


        for (var cur : problems) {
            if (solved.containsKey(cur.getUrl())) {
                cur.setSolved(true);
            }
        }
        return problems;
    }

    @Transactional
    public void save(Problem problem) throws IOException {
        String[] temp = problem.getUrl().split("/");
        problem.setName(codeForcesService.getProblemName(problem.getUrl()));
        problemRepository.save(problem);
    }
}
