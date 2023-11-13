package org.fubar.controller;

import org.fubar.database.jpa.entities.Grade;
import org.fubar.database.jpa.entities.Student;
import org.fubar.database.jpa.repositories.GradeRepository;
import org.fubar.database.jpa.repositories.StudentRepository;
import org.fubar.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class Controller {
    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;

    @Autowired
    public Controller(StudentRepository studentRepository, GradeRepository gradeRepository) {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
    }

    // Получение данных студента по id
    @Transactional
    @GetMapping("/get/student/grades/{id}")
    public ResponseEntity<Grade> getStudentGradesById(@PathVariable Integer id) {
        Optional<Grade> gradeOptional = gradeRepository.findByStudentId(id);
        if (gradeOptional.isPresent()) {
            Grade info = gradeOptional.get();
            return ResponseEntity.ok(info);
        }
        return ResponseEntity.notFound().build();
    }

    // Получение средних оценок каждого ученика в указанном классе
    @Transactional
    @GetMapping("/get/average_grades/{groupId}")
    public ResponseEntity<List<StudentDTO>> getGroupIdAverageGrades(@PathVariable Integer groupId) {
        List<Student> students = studentRepository.findByGroupId(groupId);
        List<Integer> studentIds = students.stream().map(Student::getId).collect(Collectors.toList());

        List<Grade> grades = gradeRepository.findAllByStudentIdIn(studentIds);

        Map<Integer, Double> averageGradesMap = grades.stream()
                .collect(Collectors.groupingBy(grade -> grade.getStudent().getId(), Collectors.averagingDouble(this::calculateAverageGrade)));

        List<StudentDTO> studentAverageGrades = students.stream()
                .filter(student -> averageGradesMap.containsKey(student.getId()))
                .map(student -> new StudentDTO(
                        student.getFamily(),
                        student.getName(),
                        student.getAge(),
                        student.getGroupId(),
                        averageGradesMap.get(student.getId())
                ))
                .collect(Collectors.toList());

        if (!studentAverageGrades.isEmpty()) {
            return ResponseEntity.ok(studentAverageGrades);
        }
        return ResponseEntity.notFound().build();
    }

    // Добавление студента (оценки по умолчанию 0)
    @Transactional
    @PostMapping("/add/student")
    public ResponseEntity<Student> addStudent(@RequestParam String lastName,
                                              @RequestParam String firstName,
                                              @RequestParam int age,
                                              @RequestParam int groupId) {
        Student student = new Student();
        student.setFamily(lastName);
        student.setName(firstName);
        student.setAge(age);
        student.setGroupId(groupId);

        Student savedStudent = studentRepository.save(student);

        Grade grade = new Grade();
        grade.setStudent(savedStudent);
        grade.setPhysics(0);
        grade.setMathematics(0);
        grade.setRus(0);
        grade.setLiterature(0);
        grade.setGeometry(0);
        grade.setInformatics(0);
        gradeRepository.save(grade);

        return ResponseEntity.ok(savedStudent);
    }

    // Удаление студента по id
    @Transactional
    @DeleteMapping("/delete/student/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Integer id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            gradeRepository.deleteByStudentId(id);
            studentRepository.deleteById(id);
            return ResponseEntity.ok("Студент " + id + " удалён");
        }
        return ResponseEntity.notFound().build();
    }


    // Редактирование оценки студента по определенному предмету
    @PutMapping("/update/grade/{studentId}/{subject}")
    public ResponseEntity<String> updateGrade(@PathVariable Integer studentId, @PathVariable String subject, @RequestParam Integer newGrade) {
        Optional<Grade> gradeOptional = gradeRepository.findByStudentId(studentId);
        if (gradeOptional.isPresent()) {
            Grade grade = gradeOptional.get();
            switch (subject) {
                case "physics":
                    grade.setPhysics(newGrade);
                    break;
                case "mathematics":
                    grade.setMathematics(newGrade);
                    break;
                case "rus":
                    grade.setRus(newGrade);
                    break;
                case "literature":
                    grade.setLiterature(newGrade);
                    break;
                case "geometry":
                    grade.setGeometry(newGrade);
                    break;
                case "informatics":
                    grade.setInformatics(newGrade);
                    break;
                default:
                    return ResponseEntity.badRequest().body("Неизвестный предмет");
            }
            gradeRepository.save(grade);
            return ResponseEntity.ok("Оценка по предмету " + subject + " успешно изменена");
        }
        return ResponseEntity.notFound().build();
    }

    private double calculateAverageGrade(Grade grade) {
        int totalGrades = 6;
        int sumGrades = grade.getGeometry() + grade.getInformatics() + grade.getLiterature() +
                grade.getMathematics() + grade.getPhysics() + grade.getRus();
        return (double) sumGrades / totalGrades;
    }
}
