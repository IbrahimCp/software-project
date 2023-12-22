package kz.ibrahim.SoftwareProject.repositories;


import kz.ibrahim.SoftwareProject.models.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, String> {
}
