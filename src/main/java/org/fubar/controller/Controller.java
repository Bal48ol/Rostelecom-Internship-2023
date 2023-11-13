package org.fubar.controller;

import lombok.extern.slf4j.Slf4j;
import org.fubar.database.jpa.entities.Grade;
import org.fubar.database.jpa.entities.Student;
import org.fubar.database.jpa.repositories.GradeRepository;
import org.fubar.database.jpa.repositories.StudentRepository;
import org.fubar.dto.GradesDTO;
import org.fubar.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
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
    public ResponseEntity<StudentDTO> getStudentGradesById(@PathVariable Integer id) {
        Optional<Student> gradeOptional = studentRepository.findById(id);
        if (gradeOptional.isPresent()) {
            Student student = gradeOptional.get();
            StudentDTO studentDTO = new StudentDTO(student.getId(), student.getFamily(), student.getName(),
                    student.getAge(), student.getGroupId(), null);
            log.info("Получение данных студента по id: Успешно! " + studentDTO);
            return ResponseEntity.ok(studentDTO);
        }
        log.info("Неверный id студента");
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
                        student.getId(),
                        student.getFamily(),
                        student.getName(),
                        student.getAge(),
                        student.getGroupId(),
                        averageGradesMap.get(student.getId())
                ))
                .collect(Collectors.toList());

        if (!studentAverageGrades.isEmpty()) {
            log.info("Получение средних оценок каждого ученика в указанном классе: Успешно! Чекай постман");
            return ResponseEntity.ok(studentAverageGrades);
        }
        log.info("Такого класса нет");
        return ResponseEntity.notFound().build();
    }

    // Добавление студента (оценки по умолчанию 0)
    @Transactional
    @PostMapping("/add/student")
    public ResponseEntity<StudentDTO> addStudent(@RequestParam String lastName,
                                              @RequestParam String firstName,
                                              @RequestParam int age,
                                              @RequestParam int groupId) {
        Student student = new Student();
        if (studentRepository.count() == 0) {
            student.setId(1);
        }
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

        StudentDTO studentDTO = new StudentDTO(student.getId(), student.getFamily(), student.getName(), student.getAge(), student.getGroupId(), null);

        log.info("Добавление студента (оценки по умолчанию 0): Успешно! " + studentDTO);
        return ResponseEntity.ok(studentDTO);
    }

    // Удаление студента по id
    @Transactional
    @DeleteMapping("/delete/student/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Integer id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            gradeRepository.deleteByStudentId(id);
            studentRepository.deleteById(id);
            log.info("Удаление студента по id: Успешно! Студент " + id + " удалён");
            return ResponseEntity.ok("Студент " + id + " удалён");
        }
        log.info("Неудачное удаление студента");
        return ResponseEntity.notFound().build();
    }

    // Редактирование оценки студента по определенному предмету
    @PutMapping("/update/grade/{studentId}/{subject}")
    public ResponseEntity<GradesDTO> updateGrade(@PathVariable Integer studentId, @PathVariable String subject, @RequestParam Integer newGrade) {
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
                    return ResponseEntity.notFound().build();
            }
            gradeRepository.save(grade);
            GradesDTO gradesDTO = new GradesDTO(grade.getPhysics(), grade.getMathematics(), grade.getRus(),
                    grade.getLiterature(), grade.getGeometry(), grade.getInformatics());

            log.info("Редактирование оценки студента по определенному предмету: Успешно! Оценка по предмету " + subject + " изменена");
            return ResponseEntity.ok(gradesDTO);
        }
        log.info("Неудачное редактирование оценки");
        return ResponseEntity.notFound().build();
    }

    private double calculateAverageGrade(Grade grade) {
        int totalGrades = 6;
        int sumGrades = grade.getGeometry() + grade.getInformatics() + grade.getLiterature() +
                grade.getMathematics() + grade.getPhysics() + grade.getRus();
        return (double) sumGrades / totalGrades;
    }
}
