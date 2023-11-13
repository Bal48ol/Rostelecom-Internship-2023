package org.fubar;

import org.fubar.controller.Controller;
import org.fubar.database.jpa.entities.Grade;
import org.fubar.database.jpa.entities.Student;
import org.fubar.database.jpa.repositories.GradeRepository;
import org.fubar.database.jpa.repositories.StudentRepository;
import org.fubar.dto.GradesDTO;
import org.fubar.dto.StudentDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class ControllerTests {

    @Autowired
    private Controller controller;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private GradeRepository gradeRepository;

    @Test
    void testGetStudentGradesById() {
        int studentId = 1;
        Student student = new Student();
        student.setId(studentId);
        student.setFamily("Иванов");
        student.setName("Иван");
        student.setAge(20);
        student.setGroupId(1);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        ResponseEntity<StudentDTO> response = controller.getStudentGradesById(studentId);

        // Проверка результата
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        StudentDTO studentDTO = response.getBody();
        assertEquals(studentId, studentDTO.id());
        assertEquals(student.getFamily(), studentDTO.family());
        assertEquals(student.getName(), studentDTO.name());
        assertEquals(student.getAge(), studentDTO.age());
        assertEquals(student.getGroupId(), studentDTO.groupId());
        assertNull(studentDTO.averageGrade());

        // Проверка вызова методов мок-репозитория
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    void testGetGroupIdAverageGrades() {
        int groupId = 1;
        Student student1 = new Student();
        student1.setId(1);
        student1.setFamily("Иванов");
        student1.setName("Иван");
        student1.setAge(20);
        student1.setGroupId(groupId);

        Student student2 = new Student();
        student2.setId(2);
        student2.setFamily("Петров");
        student2.setName("Петр");
        student2.setAge(21);
        student2.setGroupId(groupId);

        List<Student> students = Arrays.asList(student1, student2);

        Grade grade1 = new Grade();
        grade1.setStudent(student1);
        grade1.setPhysics(4);
        grade1.setMathematics(5);
        grade1.setRus(4);
        grade1.setLiterature(5);
        grade1.setGeometry(4);
        grade1.setInformatics(5);

        Grade grade2 = new Grade();
        grade2.setStudent(student2);
        grade2.setPhysics(2);
        grade2.setMathematics(5);
        grade2.setRus(4);
        grade2.setLiterature(4);
        grade2.setGeometry(5);
        grade2.setInformatics(3);

        List<Grade> grades = Arrays.asList(grade1, grade2);

        when(studentRepository.findByGroupId(groupId)).thenReturn(students);
        when(gradeRepository.findAllByStudentIdIn(anyList())).thenReturn(grades);
        ResponseEntity<List<StudentDTO>> response = controller.getGroupIdAverageGrades(groupId);

        // Проверка результата
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        List<StudentDTO> studentDTOs = response.getBody();
        assertEquals(2, studentDTOs.size());

        StudentDTO studentDTO1 = studentDTOs.get(0);
        assertEquals(student1.getId(), studentDTO1.id());
        assertEquals(student1.getFamily(), studentDTO1.family());
        assertEquals(student1.getName(), studentDTO1.name());
        assertEquals(student1.getAge(), studentDTO1.age());
        assertEquals(student1.getGroupId(), studentDTO1.groupId());
        assertEquals(4.5, studentDTO1.averageGrade(), 0.01);

        StudentDTO studentDTO2 = studentDTOs.get(1);
        assertEquals(student2.getId(), studentDTO2.id());
        assertEquals(student2.getFamily(), studentDTO2.family());
        assertEquals(student2.getName(), studentDTO2.name());
        assertEquals(student2.getAge(), studentDTO2.age());
        assertEquals(student2.getGroupId(), studentDTO2.groupId());
        assertEquals(3.83, studentDTO2.averageGrade(), 0.01);

        // Проверка вызова методов мок-репозиториев
        verify(studentRepository, times(1)).findByGroupId(groupId);
        verify(gradeRepository, times(1)).findAllByStudentIdIn(anyList());
    }

    @Test
    void testAddStudent() {
        String lastName = "Иванов";
        String firstName = "Иван";
        int age = 20;
        int groupId = 1;

        Student student = new Student();
        student.setId(1);
        student.setFamily(lastName);
        student.setName(firstName);
        student.setAge(age);
        student.setGroupId(groupId);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        ResponseEntity<StudentDTO> response = controller.addStudent(lastName, firstName, age, groupId);

        // Проверка результата
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        StudentDTO studentDTO = response.getBody();
        assertEquals(student.getId(), studentDTO.id());
        assertEquals(student.getFamily(), studentDTO.family());
        assertEquals(student.getName(), studentDTO.name());
        assertEquals(student.getAge(), studentDTO.age());
        assertEquals(student.getGroupId(), studentDTO.groupId());
        assertNull(studentDTO.averageGrade());

        // Проверка вызова методов мок-репозитория
        verify(studentRepository, times(1)).save(any(Student.class));
        verify(gradeRepository, times(1)).save(any(Grade.class));
    }

    @Test
    void testDeleteStudent() {
        int studentId = 1;
        Student student = new Student();
        student.setId(studentId);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        ResponseEntity<String> response = controller.deleteStudent(studentId);

        // Проверка результата
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Студент " + studentId + " удалён", response.getBody());

        // Проверка вызова методов мок-репозитория
        verify(gradeRepository, times(1)).deleteByStudentId(studentId);
        verify(studentRepository, times(1)).deleteById(studentId);
    }

    @Test
    void testUpdateGrade() {
        int studentId = 1;
        String subject = "physics";
        int newGrade = 85;

        Student student = new Student();
        student.setId(studentId);

        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setPhysics(80);
        grade.setMathematics(90);
        grade.setRus(70);
        grade.setLiterature(85);
        grade.setGeometry(75);
        grade.setInformatics(95);

        when(gradeRepository.findByStudentId(studentId)).thenReturn(Optional.of(grade));
        ResponseEntity<GradesDTO> response = controller.updateGrade(studentId, subject, newGrade);

        // Проверка результата
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        GradesDTO gradesDTO = response.getBody();
        assertEquals(newGrade, gradesDTO.physics());
        assertEquals(grade.getMathematics(), gradesDTO.mathematics());
        assertEquals(grade.getRus(), gradesDTO.rus());
        assertEquals(grade.getLiterature(), gradesDTO.literature());
        assertEquals(grade.getGeometry(), gradesDTO.geometry());
        assertEquals(grade.getInformatics(), gradesDTO.informatics());

        // Проверка вызова методов мок-репозитория
        verify(gradeRepository, times(1)).findByStudentId(studentId);
        verify(gradeRepository, times(1)).save(grade);
    }
}
