package kz.ibrahim.SoftwareProject.services;


import kz.ibrahim.SoftwareProject.external.CodeForcesAdapter;
import kz.ibrahim.SoftwareProject.models.Contest;
import kz.ibrahim.SoftwareProject.repositories.ContestRepository;
import kz.ibrahim.SoftwareProject.util.ContestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ContestService {

    private final CodeForcesAdapter codeForcesService;
    private final ContestRepository contestRepository;


    @Autowired
    public ContestService(CodeForcesAdapter codeForcesService, ContestRepository contestRepository) {
        this.codeForcesService = codeForcesService;
        this.contestRepository = contestRepository;
    }

    public List<Contest> findAll() {
        return contestRepository.findAll();
    }

    public List<Contest> findAllUpcoming() {
        return contestRepository.findAll().stream()
                .filter(contest -> contest.getContestDate().isAfter(LocalDateTime.now(ZoneOffset.ofHours(6))))
                .collect(Collectors.toList());
    }

    public List<Contest> findAllRecent() {
        return contestRepository.findAll().stream()
                .filter(contest -> contest.getContestDate().isBefore(LocalDateTime.now(ZoneOffset.ofHours(6))))
                .collect(Collectors.toList());
    }

    public Contest findOne(String id) {
        Optional<Contest> foundContest = contestRepository.findById(id);

        return foundContest.orElse(null);
    }

    @Transactional
    public void save(Contest contest) throws IOException {
        String[] temp = contest.getUrl().split("/");
        contest.setContestDate(codeForcesService.getContestStartTime(contest.getUrl()));
        contest.setName(codeForcesService.getContestName(contest.getUrl()));
        System.out.println(contest);
        contestRepository.save(contest);
    }

    @Transactional
    public void update(String url, Contest updatedContest) {
        updatedContest.setUrl(url);
        contestRepository.save(updatedContest);
    }

}
