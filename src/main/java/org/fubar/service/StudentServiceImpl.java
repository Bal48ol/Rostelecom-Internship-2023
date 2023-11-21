package org.fubar.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fubar.dto.StudentDTO;
import org.fubar.jpa.entities.Grade;
import org.fubar.jpa.entities.Student;
import org.fubar.jpa.repositories.StudentRepository;
import org.fubar.jpa.repositories.GradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;

    @Override
    public StudentDTO getStudentById(Integer id) {
        return studentRepository.findById(id)
                .flatMap(student -> gradeRepository.findById(id)
                        .map(grade -> {
                            double averageGrade = calculateAverageGrade(grade);
                            return new StudentDTO(student.getId(), student.getFamily(), student.getName(),
                                    student.getAge(), student.getGroupId(), averageGrade);
                        }))
                .orElse(null);
    }

    @Override
    public List<StudentDTO> getGroupIdAverageGrades(Integer groupId) {
        List<Student> students = studentRepository.findByGroupId(groupId);
        List<Integer> studentIds = students.stream().map(Student::getId).collect(Collectors.toList());

        List<Grade> grades = gradeRepository.findAllByStudentIdIn(studentIds);

        Map<Integer, Double> averageGradesMap = grades.stream()
                .collect(Collectors.groupingBy(grade -> grade.getStudent().getId(), Collectors.averagingDouble(this::calculateAverageGrade)));

        return students.stream()
                .filter(student -> averageGradesMap.containsKey(student.getId()))
                .map(student -> {
                    double averageGrade = averageGradesMap.get(student.getId());
                    return new StudentDTO(student.getId(), student.getFamily(), student.getName(),
                            student.getAge(), student.getGroupId(), averageGrade);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public StudentDTO addStudent(String lastName, String firstName, int age, int groupId) {
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
        return new StudentDTO(student.getId(), student.getFamily(), student.getName(),
                student.getAge(), student.getGroupId(), averageGrade);
    }

    @Transactional
    @Override
    public String deleteStudent(Integer id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        return studentOptional.map(student -> {
            gradeRepository.deleteByStudentId(id);
            studentRepository.deleteById(id);
            return "Студент " + id + " удалён";
        }).orElse("Неудачное удаление студента");
    }

    private double calculateAverageGrade(Grade grade) {
        int totalGrades = 6;
        int sumGrades = grade.getGeometry() + grade.getInformatics() + grade.getLiterature() +
                grade.getMathematics() + grade.getPhysics() + grade.getRus();
        return (double) sumGrades / totalGrades;
    }
}
