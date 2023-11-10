package org.fubar.rthw.main;

import org.fubar.rthw.dto.AverageGradesDTO;
import org.springframework.web.bind.annotation.*;
import org.fubar.rthw.DatabaseHelper;
import org.fubar.rthw.dto.StudentDTO;
import java.sql.SQLException;
import java.util.List;

@RestController
public class RThwController {
    private DatabaseHelper dbHelp;

    @RequestMapping("/")
    public String startPage(){
        return "It's Home Work №5!";
    }

    @GetMapping("/average_grades_for_senior_classes")
    public AverageGradesDTO getAverageGradesForSeniorClasses(){
        try {
            dbHelp = new DatabaseHelper();
            dbHelp.connect();
            AverageGradesDTO averageGrades = dbHelp.getAverageGradesForSeniorClasses();
            dbHelp.disconnect();

            return averageGrades;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/average_grades/group_id/{groupId}")
    public String getAverageGradeByGroupId(@PathVariable int groupId) {
        if (groupId > 0 && groupId < 13){
            try {
                dbHelp = new DatabaseHelper();
                dbHelp.connect();
                List<StudentDTO> studentInfoList = dbHelp.getAverageGradeByGroupId(groupId);
                dbHelp.disconnect();

                StringBuilder sb = new StringBuilder();
                for (StudentDTO student : studentInfoList) {
                    sb.append(student.toString());
                    sb.append(";<br>");
                }
                return sb.toString();
            }
            catch (SQLException e) {
                throw new RuntimeException("Ошибка: " + e.getMessage());
            }
        }
        else return "Неверный класс. Верные классы: от 1 до 12";
    }

    @GetMapping("/average_grades/lastName/{lastName}")
    public String getAverageGradeByLastName(@PathVariable String lastName) {
        try {
            dbHelp = new DatabaseHelper();
            dbHelp.connect();
            List<StudentDTO> studentInfoList = dbHelp.getAverageGradeByLastName(lastName);
            dbHelp.disconnect();

            StringBuilder sb = new StringBuilder();
            for (StudentDTO student : studentInfoList) {
                sb.append(student.toString());
                sb.append(";<br>");
            }
            return sb.toString();
        }
        catch (SQLException e) {
            throw new RuntimeException("Ошибка: " + e.getMessage());
        }
    }

    @GetMapping("/honor_students/age/{age}")
    public String getHonorStudentsAfter(@PathVariable int age){
        try {
            dbHelp = new DatabaseHelper();
            dbHelp.connect();
            List<StudentDTO> honorStudents = dbHelp.getHonorStudentsAfter(age);
            dbHelp.disconnect();

            StringBuilder sb = new StringBuilder();
            for (StudentDTO student : honorStudents){
                sb.append(student.toString());
                sb.append(";<br>");
            }
            return sb.toString();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/update/grade")
    public String updateGrade(@RequestParam String lastName,
                              @RequestParam String firstName,
                              @RequestParam int groupId,
                              @RequestParam String lesson,
                              @RequestParam int newGrade) {
        try {
            dbHelp = new DatabaseHelper();
            dbHelp.connect();
            dbHelp.updateStudentGrade(lastName, firstName, groupId, lesson, newGrade);
            dbHelp.disconnect();
            return "Оценка успешно обновлена";
        }
        catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении оценки: " + e.getMessage());
        }
    }
}
