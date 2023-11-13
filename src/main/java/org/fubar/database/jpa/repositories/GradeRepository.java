package org.fubar.database.jpa.repositories;

import org.fubar.database.jpa.entities.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {
    Optional<Grade> findByStudentId(Integer studentId);
    List<Grade> findAllByStudentIdIn(List<Integer> studentIds);
    void deleteByStudentId(Integer studentId);
}