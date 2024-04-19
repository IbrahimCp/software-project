package kz.ibrahim.SoftwareProject.services;

import kz.ibrahim.SoftwareProject.external.CodeForcesAdapter;
import kz.ibrahim.SoftwareProject.models.Contest;
import kz.ibrahim.SoftwareProject.models.Student;
import kz.ibrahim.SoftwareProject.repositories.ScoreRepository;
import kz.ibrahim.SoftwareProject.repositories.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.min;

@Service
@Transactional(readOnly = true)
public class StudentService {
    private final StudentRepository studentRepository;
    private final ContestService contestService;
    private final CodeForcesAdapter codeForcesAdapter;
    private final ScoreRepository scoreRepository;

    public StudentService(StudentRepository studentRepository, ContestService contestService, CodeForcesAdapter codeForcesAdapter, ScoreRepository scoreRepository) {
        this.studentRepository = studentRepository;
        this.contestService = contestService;
        this.codeForcesAdapter = codeForcesAdapter;
        this.scoreRepository = scoreRepository;
    }

    @Transactional
    public void updateRating() throws IOException {
        List<Contest> filteredContests =  contestService.findAll().stream()
                .filter(contest -> !contest.getUpdated())
                .toList();

        System.out.println("here");
        for (var currentContest : filteredContests) {
            List<String> handles = studentRepository.findAll().stream()
                    .map(Student::getHandle)
                    .toList();
            List<String> students = codeForcesAdapter.getUserRanks(currentContest.getUrl(), handles);
            for (int i = 0; i < min(30, students.size()); i++) {
                Student currentStudent = studentRepository.findByHandle(students.get(i));
                currentStudent.setRating(currentStudent.getRating() + scoreRepository.findById(i + 1).get().getPoint());
                studentRepository.save(currentStudent);
            }
        }
        System.out.println("here A");

        for (var currentContest : filteredContests) {
            currentContest.setUpdated(true);
            contestService.update(currentContest.getUrl(), currentContest);
        }

        System.out.println("here B");
    }

    public List<Student> findAll(int limit) {
        List<Student> all = studentRepository.findAll();
        all.sort(Comparator.comparing(Student::getRating).reversed());

        return all.stream().limit(min(limit, all.size())).collect(Collectors.toList());
    }
}
