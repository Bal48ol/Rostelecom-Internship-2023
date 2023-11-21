package org.fubar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fubar.dto.GradesDTO;
import org.fubar.dto.StudentDTO;
import org.fubar.service.GradeService;
import org.fubar.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@Tag(name = "Student's grades")
@RequiredArgsConstructor
@RestController
public class Controller {
    private final StudentService studentService;
    private final GradeService gradeService;

    @GetMapping("/get/student/{id}")
    @Operation(summary = "Получение данных студента по id")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Integer id) {
        StudentDTO studentDTO = studentService.getStudentById(id);
        return (studentDTO != null)
                ? ResponseEntity.ok(studentDTO)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/get/grades/{id}")
    @Operation(summary = "Получение оценок студента по id")
    public ResponseEntity<GradesDTO> getGradesById(@PathVariable Integer id) {
        GradesDTO gradesDTO = gradeService.getGradesById(id);
        return (gradesDTO != null)
                ? ResponseEntity.ok(gradesDTO)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/get/average_grades/{groupId}")
    @Operation(summary = "Получение средних оценок каждого ученика в указанном классе")
    public ResponseEntity<List<StudentDTO>> getGroupIdAverageGrades(@PathVariable Integer groupId) {
        List<StudentDTO> studentDTOs = studentService.getGroupIdAverageGrades(groupId);
        return (!studentDTOs.isEmpty())
                ? ResponseEntity.ok(studentDTOs)
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/add/student")
    @Operation(summary = "Добавление студента (оценки по умолчанию 0)")
    public ResponseEntity<StudentDTO> addStudent(@RequestParam String lastName,
                                                 @RequestParam String firstName,
                                                 @RequestParam int age,
                                                 @RequestParam int groupId) {
        StudentDTO studentDTO = studentService.addStudent(lastName, firstName, age, groupId);
        return ResponseEntity.ok(studentDTO);
    }

    @DeleteMapping("/delete/student/{id}")
    @Operation(summary = "Удаление студента по id")
    public ResponseEntity<String> deleteStudent(@PathVariable Integer id) {
        String result = studentService.deleteStudent(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/grade/{studentId}/{subject}")
    @Operation(summary = "Редактирование оценки студента по определенному предмету")
    public ResponseEntity<GradesDTO> updateGrade(@PathVariable Integer studentId,
                                                 @PathVariable String subject,
                                                 @RequestParam Integer newGrade) {
        GradesDTO gradesDTO = gradeService.updateGrade(studentId, subject, newGrade);
        return (gradesDTO != null)
                ? ResponseEntity.ok(gradesDTO)
                : ResponseEntity.notFound().build();
    }
}

