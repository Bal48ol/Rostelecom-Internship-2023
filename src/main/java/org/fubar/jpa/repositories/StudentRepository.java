package org.fubar.jpa.repositories;

import org.fubar.jpa.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByGroupId(Integer groupId);
}