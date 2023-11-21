package org.fubar.service;

import org.fubar.dto.StudentDTO;

import java.util.List;

public interface StudentService {
    StudentDTO getStudentById(Integer id);
    List<StudentDTO> getGroupIdAverageGrades(Integer groupId);
    StudentDTO addStudent(String lastName, String firstName, int age, int groupId);
    String deleteStudent(Integer id);
}