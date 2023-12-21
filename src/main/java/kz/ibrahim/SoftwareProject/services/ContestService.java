package kz.ibrahim.SoftwareProject.services;


import kz.ibrahim.SoftwareProject.models.Contest;
import kz.ibrahim.SoftwareProject.repositories.ContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ContestService {

    private final ContestRepository contestRepository;

    @Autowired
    public ContestService(ContestRepository contestRepository) {
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

    public Contest findOne(Integer id) {
        Optional<Contest> foundContest = contestRepository.findById(id);

        return foundContest.orElse(null);
    }

    @Transactional
    public void save(Contest contest) {
        contestRepository.save(contest);
    }

    @Transactional
    public void update(int id, Contest updatedContest) {
        updatedContest.setId(id);
        contestRepository.save(updatedContest);
    }

}
