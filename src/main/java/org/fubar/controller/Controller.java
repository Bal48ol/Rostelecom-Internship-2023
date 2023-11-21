package org.fubar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fubar.jpa.entities.Grade;
import org.fubar.jpa.entities.Student;
import org.fubar.jpa.repositories.GradeRepository;
import org.fubar.jpa.repositories.StudentRepository;
import org.fubar.dto.GradesDTO;
import org.fubar.dto.StudentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Tag(name = "Student's grades")
@RequiredArgsConstructor
@RestController
public class Controller {
    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;

    @Transactional
    @GetMapping("/get/student/{id}")
    @Operation(summary = "Получение данных студента по id")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Integer id) {
        return studentRepository.findById(id)
                .flatMap(student -> gradeRepository.findById(id)
                        .map(grade -> {
                            double averageGrade = calculateAverageGrade(grade);
                            StudentDTO studentDTO = new StudentDTO(student.getId(), student.getFamily(), student.getName(),
                                    student.getAge(), student.getGroupId(), averageGrade);

                            log.info("Получение данных студента по id: Успешно! " + studentDTO);
                            return ResponseEntity.ok(studentDTO);
                        }))
                .orElseGet(() -> {
                    log.info("Неверный id студента");
                    return ResponseEntity.notFound().build();
                });
    }

    @Transactional
    @GetMapping("/get/grades/{id}")
    @Operation(summary = "Получение оценок студента по id")
    public ResponseEntity<GradesDTO> getGradesById(@PathVariable Integer id) {
        return gradeRepository.findByStudentId(id)
                .map(grade -> {
                    GradesDTO gradesDTO = new GradesDTO(grade.getPhysics(), grade.getMathematics(), grade.getRus(),
                            grade.getLiterature(), grade.getGeometry(), grade.getInformatics());

                    log.info("Получение данных студента по id: Успешно! " + gradesDTO);
                    return ResponseEntity.ok(gradesDTO);
                })
                .orElseGet(() -> {
                    log.info("Неверный id студента");
                    return ResponseEntity.notFound().build();
                });
    }

    @Transactional
    @GetMapping("/get/average_grades/{groupId}")
    @Operation(summary = "Получение средних оценок каждого ученика в указанном классе")
    public ResponseEntity<List<StudentDTO>> getGroupIdAverageGrades(@PathVariable Integer groupId) {
        List<Student> students = studentRepository.findByGroupId(groupId);
        List<Integer> studentIds = students.stream().map(Student::getId).collect(Collectors.toList());

        List<Grade> grades = gradeRepository.findAllByStudentIdIn(studentIds);

        Map<Integer, Double> averageGradesMap = grades.stream()
                .collect(Collectors.groupingBy(grade -> grade.getStudent().getId(), Collectors.averagingDouble(this::calculateAverageGrade)));

        List<StudentDTO> studentAverageGrades = students.stream()
                .filter(student -> averageGradesMap.containsKey(student.getId()))
                .map(student -> {
                    double averageGrade = averageGradesMap.get(student.getId());
                    return new StudentDTO(student.getId(), student.getFamily(), student.getName(),
                            student.getAge(), student.getGroupId(), averageGrade);
                })
                .collect(Collectors.toList());

        return studentAverageGrades.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(studentAverageGrades);
    }

    @Transactional
    @PostMapping("/add/student")
    @Operation(summary = "Добавление студента (оценки по умолчанию 0)")
    public ResponseEntity<StudentDTO> addStudent(@RequestParam String lastName,
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

        double averageGrade = calculateAverageGrade(grade);
        StudentDTO studentDTO = new StudentDTO(student.getId(), student.getFamily(), student.getName(),
                student.getAge(), student.getGroupId(), averageGrade);

        log.info("Добавление студента (оценки по умолчанию 0): Успешно! " + studentDTO);
        return ResponseEntity.ok(studentDTO);
    }

    @Transactional
    @DeleteMapping("/delete/student/{id}")
    @Operation(summary = "Удаление студента по id")
    public ResponseEntity<String> deleteStudent(@PathVariable Integer id) {
        Optional<Student> studentOptional = studentRepository.findById(id);

        return studentOptional.map(student -> {
            gradeRepository.deleteByStudentId(id);
            studentRepository.deleteById(id);
            log.info("Удаление студента по id: Успешно! Студент " + id + " удалён");
            return ResponseEntity.ok("Студент " + id + " удалён");
        }).orElseGet(() -> {
            log.info("Неудачное удаление студента");
            return ResponseEntity.notFound().build();
        });
    }

    @Transactional
    @PutMapping("/update/grade/{studentId}/{subject}")
    @Operation(summary = "Редактирование оценки студента по определенному предмету")
    public ResponseEntity<GradesDTO> updateGrade(@PathVariable Integer studentId, @PathVariable String subject, @RequestParam Integer newGrade) {
        return gradeRepository.findByStudentId(studentId)
                .map(grade -> updateGradeAndCreateDTO(grade, subject, newGrade))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private ResponseEntity<GradesDTO> updateGradeAndCreateDTO(Grade grade, String subject, Integer newGrade) {
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

    public double calculateAverageGrade(Grade grade) {
        int totalGrades = 6;
        int sumGrades = grade.getGeometry() + grade.getInformatics() + grade.getLiterature() +
                grade.getMathematics() + grade.getPhysics() + grade.getRus();
        return (double) sumGrades / totalGrades;
    }
}
