package org.fubar.rthw;

import org.fubar.rthw.dto.AverageGradesDTO;
import org.fubar.rthw.dto.StudentDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Класс DatabaseManager организован таким образом, что операции базы данных,
// связанные с определенной бизнес-логикой, выполняются внутри одной транзакции.
// Это позволяет обеспечить целостность данных и согласованность результатов операций.

// Методы getAverageGradesForSeniorClasses, getHonorStudentsAfter14, getAverageGradeByLastName
// используют Data Transfer Objects (DTO) для передачи данных между слоями приложения.

public class DatabaseHelper {
    private Connection connection;

    public void connect() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/school";
        String username = "postgres";
        String password = "root";
        connection = DriverManager.getConnection(url, username, password);
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public AverageGradesDTO getAverageGradesForSeniorClasses() throws SQLException {
        try {
            beginTransaction();

            String query = "SELECT AVG(physics), AVG(mathematics), AVG(rus), AVG(literature), AVG(geometry), AVG(informatics) " +
                    "FROM grades INNER JOIN students ON grades.student_id = students.id " +
                    "WHERE students.group_id IN (10, 11)";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                if (resultSet.next()) {
                    double averagePhysics = resultSet.getDouble(1);
                    double averageMathematics = resultSet.getDouble(2);
                    double averageRus = resultSet.getDouble(3);
                    double averageLiterature = resultSet.getDouble(4);
                    double averageGeometry = resultSet.getDouble(5);
                    double averageInformatics = resultSet.getDouble(6);
                    return new AverageGradesDTO(averagePhysics, averageMathematics, averageRus,
                            averageLiterature, averageGeometry, averageInformatics);
                }
            }

            commitTransaction();
        }
        catch (SQLException e){
            rollbackTransaction();
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<StudentDTO> getHonorStudentsAfter(int age) throws SQLException {
        List<StudentDTO> honorStudents = null;
        try {
            beginTransaction();

            String query = "SELECT students.family, students.name, students.group_id, " +
                    "(grades.physics + grades.mathematics + grades.rus + grades.literature + grades.geometry + grades.informatics) / 6.0 " +
                    "FROM students INNER JOIN grades ON students.id = grades.student_id " +
                    "WHERE students.age > " + age + " " +
                    "GROUP BY students.family, students.name, students.group_id, grades.physics, grades.mathematics, grades.rus, grades.literature, grades.geometry, grades.informatics " +
                    "HAVING (grades.physics + grades.mathematics + grades.rus + grades.literature + grades.geometry + grades.informatics) / 6.0 = 5.0";

            honorStudents = new ArrayList<>();
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                resNext(honorStudents, resultSet);
            }

            commitTransaction();
        } catch (SQLException e) {
            rollbackTransaction();
            System.out.println(e.getMessage());
        }
        return honorStudents;
    }

    public List<StudentDTO> getAverageGradeByLastName(String lastName) throws SQLException {
        List<StudentDTO> studentInfoList = null;
        try {
            beginTransaction();

            String query = "SELECT students.family, students.name, students.group_id, " +
                    "(grades.physics + grades.mathematics + grades.rus + grades.literature + grades.geometry + grades.informatics) / 6.0 " +
                    "FROM students INNER JOIN grades ON students.id = grades.student_id " +
                    "WHERE students.family = ? " +
                    "GROUP BY students.family, students.name, students.group_id, grades.physics, grades.mathematics, grades.rus, grades.literature, grades.geometry, grades.informatics";

            studentInfoList = new ArrayList<>();
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, lastName);

                try (ResultSet resultSet = statement.executeQuery()) {
                    resNext(studentInfoList, resultSet);
                }
            }

            commitTransaction();
        } catch (SQLException e) {
            rollbackTransaction();
            System.out.println(e.getMessage());
        }
        return studentInfoList;
    }

    public List<StudentDTO> getAverageGradeByGroupId(int id) throws SQLException {
        List<StudentDTO> studentInfoList = null;
        try {
            beginTransaction();

            String query = "SELECT students.family, students.name, students.group_id, " +
                    "(grades.physics + grades.mathematics + grades.rus + grades.literature + grades.geometry + grades.informatics) / 6.0 " +
                    "FROM students INNER JOIN grades ON students.id = grades.student_id " +
                    "WHERE students.group_id = ? " +
                    "GROUP BY students.family, students.name, students.group_id, grades.physics, grades.mathematics, grades.rus, grades.literature, grades.geometry, grades.informatics";

            studentInfoList = new ArrayList<>();
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement. setInt(1, id);

                try (ResultSet resultSet = statement.executeQuery()) {
                    resNext(studentInfoList, resultSet);
                }
            }

            commitTransaction();
        } catch (SQLException e) {
            rollbackTransaction();
            System.out.println(e.getMessage());
        }
        return studentInfoList;
    }

    public void updateStudentGrade(String lastName, String firstName, int groupId, String lesson, int newGrade) throws SQLException {
        try {
            beginTransaction();
            // ученик существует?
            String checkStudentQuery = "SELECT students.id FROM students WHERE students.family = ? AND students.name = ? AND students.group_id = ?";
            int studentId;
            try (PreparedStatement checkStatement = connection.prepareStatement(checkStudentQuery)) {
                checkStatement.setString(1, lastName);
                checkStatement.setString(2, firstName);
                checkStatement.setInt(3, groupId);

                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    if (resultSet.next()) {
                        studentId = resultSet.getInt(1);
                    }
                    else {
                        throw new SQLException("Ученик не найден");
                    }
                }
            }
            // обновление оценки ученика в определенном предмете
            String updateGradeQuery = "UPDATE grades SET " + lesson + " = ? WHERE student_id = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateGradeQuery)) {
                updateStatement.setInt(1, newGrade);
                updateStatement.setInt(2, studentId);
                int rowsUpdated = updateStatement.executeUpdate();
                if (rowsUpdated == 0) {
                    return;
                }
            }

            commitTransaction();
        }
        catch (SQLException e) {
            rollbackTransaction();
            System.out.println(e.getMessage() + " + опять ошибка");
        }
    }

    private void resNext(List<StudentDTO> honorStudents, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String family = resultSet.getString(1);
            String name = resultSet.getString(2);
            int groupId = resultSet.getInt(3);
            double averageGrade = resultSet.getDouble(4);

            StudentDTO student = new StudentDTO(family, name, groupId, averageGrade);
            honorStudents.add(student);
        }
    }

    private void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    private void commitTransaction() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    private void rollbackTransaction() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
    }
}

