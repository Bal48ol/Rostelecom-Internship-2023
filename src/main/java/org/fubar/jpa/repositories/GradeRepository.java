package org.fubar.jpa.repositories;

import org.fubar.jpa.entities.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
    Optional<Grade> findByStudentId(Integer studentId);
    List<Grade> findAllByStudentIdIn(List<Integer> studentIds);
    void deleteByStudentId(Integer studentId);
}