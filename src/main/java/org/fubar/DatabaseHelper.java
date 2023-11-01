package org.fubar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

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

    public List<HonorStudentDTO> getHonorStudentsAfter14() throws SQLException {
        List<HonorStudentDTO> honorStudents = null;
        try {
            beginTransaction();

            String query = "SELECT students.family, students.name, students.group_id, " +
                    "(grades.physics + grades.mathematics + grades.rus + grades.literature + grades.geometry + grades.informatics) / 6.0 " +
                    "FROM students INNER JOIN grades ON students.id = grades.student_id " +
                    "WHERE students.age > 14 " +
                    "GROUP BY students.family, students.name, students.group_id, grades.physics, grades.mathematics, grades.rus, grades.literature, grades.geometry, grades.informatics " +
                    "HAVING (grades.physics + grades.mathematics + grades.rus + grades.literature + grades.geometry + grades.informatics) / 6.0 = 5.0";

            honorStudents = new ArrayList<>();
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String family = resultSet.getString(1);
                    String name = resultSet.getString(2);
                    int groupId = resultSet.getInt(3);
                    double averageGrade = resultSet.getDouble(4);

                    HonorStudentDTO student = new HonorStudentDTO(family, name, groupId, averageGrade);
                    honorStudents.add(student);
                }
            }

            commitTransaction();
        } catch (SQLException e) {
            rollbackTransaction();
            System.out.println(e.getMessage());
        }
        return honorStudents;
    }


    public List<AverageGradeByLastNameDTO> getAverageGradeByLastName(String lastName) throws SQLException {
        List<AverageGradeByLastNameDTO> studentInfoList = null;
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
                    while (resultSet.next()) {
                        String family = resultSet.getString(1);
                        String name = resultSet.getString(2);
                        int groupId = resultSet.getInt(3);
                        double averageGrade = resultSet.getDouble(4);

                        AverageGradeByLastNameDTO studentInfo = new AverageGradeByLastNameDTO(family, name, groupId, averageGrade);
                        studentInfoList.add(studentInfo);
                    }
                }
            }

            commitTransaction();
        } catch (SQLException e) {
            rollbackTransaction();
            System.out.println(e.getMessage());
        }
        return studentInfoList;
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

