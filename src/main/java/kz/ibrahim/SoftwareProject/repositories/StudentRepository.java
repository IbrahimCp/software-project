package kz.ibrahim.SoftwareProject.repositories;

import kz.ibrahim.SoftwareProject.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByHandle(String handle);
}
