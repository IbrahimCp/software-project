package kz.ibrahim.SoftwareProject.repositories;

import kz.ibrahim.SoftwareProject.models.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Integer> {
}
