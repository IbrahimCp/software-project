package kz.ibrahim.SoftwareProject.repositories;

import kz.ibrahim.SoftwareProject.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
}
