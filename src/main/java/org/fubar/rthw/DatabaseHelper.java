package org.fubar.rthw;

import jakarta.servlet.http.HttpServletRequest;
import org.fubar.rthw.dto.AverageGradesDTO;
import org.fubar.rthw.dto.StudentDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public void addStudent(String lastName, String firstName, int age, int groupId, HttpServletRequest request) throws SQLException {
        String result;
        try {
            beginTransaction();

            String selectMaxIdQuery = "SELECT MAX(id) FROM students";
            int nextId;
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectMaxIdQuery)) {
                if (resultSet.next()) {
                    nextId = resultSet.getInt(1) + 1;
                } else {
                    nextId = 1;
                }
            }

            String insertStudentQuery = "INSERT INTO students (id, family, name, age, group_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertStudentQuery)) {
                insertStatement.setInt(1, nextId);
                insertStatement.setString(2, lastName);
                insertStatement.setString(3, firstName);
                insertStatement.setInt(4, age);
                insertStatement.setInt(5, groupId);
                insertStatement.executeUpdate();
            }

            String insertGradesQuery = "INSERT INTO grades (id, student_id, physics, mathematics, rus, literature, geometry, informatics) VALUES (?, ?, 0, 0, 0, 0, 0, 0)";
            try (PreparedStatement insertGradesStatement = connection.prepareStatement(insertGradesQuery)) {
                insertGradesStatement.setInt(1, nextId);
                insertGradesStatement.setInt(2, nextId);
                insertGradesStatement.executeUpdate();
            }

            commitTransaction();
            result = "Студент с ID " + nextId + " успешно добавлен.";
        } catch (SQLException e) {
            rollbackTransaction();
            result = "Ошибка при добавлении студента: " + e.getMessage();
            System.out.println(e.getMessage());
        }

        request.setAttribute("result", result);
    }

    public void deleteStudent(int studentId, HttpServletRequest request) throws SQLException {
        String result;
        try {
            beginTransaction();

            // Удаление строк из таблицы grades
            String deleteGradesQuery = "DELETE FROM grades WHERE student_id = ?";
            try (PreparedStatement deleteGradesStatement = connection.prepareStatement(deleteGradesQuery)) {
                deleteGradesStatement.setInt(1, studentId);
                deleteGradesStatement.executeUpdate();
            }

            // Удаление записи о студенте из таблицы students
            String deleteStudentQuery = "DELETE FROM students WHERE id = ?";
            try (PreparedStatement deleteStudentStatement = connection.prepareStatement(deleteStudentQuery)) {
                deleteStudentStatement.setInt(1, studentId);
                int rowsDeleted = deleteStudentStatement.executeUpdate();
                if (rowsDeleted == 0) {
                    result = "Студент с ID " + studentId + " не найден.";
                } else {
                    result = "Студент с ID " + studentId + " успешно удален.";
                }
            }

            commitTransaction();
        } catch (SQLException e) {
            rollbackTransaction();
            result = "Ошибка при удалении студента: " + e.getMessage();
            System.out.println(e.getMessage());
        }

        request.setAttribute("result", result);
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

    public List<StudentDTO> getHonorStudentsAfterAge(int age) throws SQLException {
        List<StudentDTO> honorStudents = null;
        try {
            beginTransaction();

            String query = "SELECT students.family, students.name, students.age, students.group_id, " +
                    "(grades.physics + grades.mathematics + grades.rus + grades.literature + grades.geometry + grades.informatics) / 6.0 " +
                    "FROM students INNER JOIN grades ON students.id = grades.student_id " +
                    "WHERE students.age > " + age + " " +
                    "GROUP BY students.family, students.name, students.age, students.group_id, grades.physics, grades.mathematics, grades.rus, grades.literature, grades.geometry, grades.informatics " +
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

            String query = "SELECT students.family, students.name, students.age, students.group_id, " +
                    "(grades.physics + grades.mathematics + grades.rus + grades.literature + grades.geometry + grades.informatics) / 6.0 " +
                    "FROM students INNER JOIN grades ON students.id = grades.student_id " +
                    "WHERE students.family = ? " +
                    "GROUP BY students.family, students.name, students.age, students.group_id, grades.physics, grades.mathematics, grades.rus, grades.literature, grades.geometry, grades.informatics";

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

            String query = "SELECT students.family, students.name, students.age, students.group_id, " +
                    "(grades.physics + grades.mathematics + grades.rus + grades.literature + grades.geometry + grades.informatics) / 6.0 " +
                    "FROM students INNER JOIN grades ON students.id = grades.student_id " +
                    "WHERE students.group_id = ? " +
                    "GROUP BY students.family, students.name, students.age, students.group_id, grades.physics, grades.mathematics, grades.rus, grades.literature, grades.geometry, grades.informatics";

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

    public void updateStudentGrade(String lastName, String firstName, int age, int groupId, String lesson, int newGrade, HttpServletRequest request) throws SQLException {
        try {
            beginTransaction();

            // Поиск учеников с полным совпадением по имени, фамилии, возрасту и классу
            String checkStudentQuery = "SELECT id FROM students " +
                    "WHERE family = ? AND name = ? AND age = ? AND group_id = ?";
            List<Integer> matchingStudentIds = new ArrayList<>();

            try (PreparedStatement checkStatement = connection.prepareStatement(checkStudentQuery)) {
                checkStatement.setString(1, lastName);
                checkStatement.setString(2, firstName);
                checkStatement.setInt(3, age);
                checkStatement.setInt(4, groupId);

                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int studentId = resultSet.getInt("id");
                        matchingStudentIds.add(studentId);
                    }
                }
            }

            if (matchingStudentIds.size() == 1) {
                // Если есть только один ученик с данными именем, фамилией, возрастом и классом,
                // то заменяем оценку по указанному предмету
                int studentId = matchingStudentIds.get(0);

                String updateGradeQuery = "UPDATE grades SET " + lesson + " = ? WHERE student_id = ?";
                try (PreparedStatement updateStatement = connection.prepareStatement(updateGradeQuery)) {
                    updateStatement.setInt(1, newGrade);
                    updateStatement.setInt(2, studentId);
                    int rowsUpdated = updateStatement.executeUpdate();
                    String result;
                    if (rowsUpdated == 0) {
                        result = "Оценка " + newGrade + " по предмету " + lesson + " не обновлена у " + lastName + ", " + firstName + ", " + age + ", " + groupId;
                    }
                    else {
                        result = "Оценка " + newGrade + " по предмету " + lesson + " успешно обновлена у " + lastName + ", " + firstName + ", " + age + ", " + groupId;
                    }
                    request.setAttribute("result", result);
                }
            } else {
                // Если есть полное совпадение по имени, фамилии, возрасту и классу,
                // то возвращаем список id полностью совпадающих учеников
                if (!matchingStudentIds.isEmpty()) {
                    StringBuilder result = new StringBuilder("ID учеников с идентичными данными (" + lastName + ", " + firstName + ", " + age + ", " + groupId + "): ");
                    for (int studentId : matchingStudentIds) {
                        result.append("\n").append("ID: ").append(studentId);
                    }
                    result.append("\n").append("Используйте ссылку /update/grade/id");
                    request.setAttribute("result", result.toString());
                } else {
                    throw new SQLException("Ученик не найден");
                }
            }

            commitTransaction();
        }
        catch (SQLException e) {
            rollbackTransaction();
            System.out.println(e.getMessage());
        }
    }

    public void updateStudentGradeById(int studentId, String lesson, int newGrade, HttpServletRequest request) throws SQLException {
        try {
            beginTransaction();

            String updateGradeQuery = "UPDATE grades SET " + lesson + " = ? WHERE student_id = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateGradeQuery)) {
                updateStatement.setInt(1, newGrade);
                updateStatement.setInt(2, studentId);
                int rowsUpdated = updateStatement.executeUpdate();
                String result;
                if (rowsUpdated == 0) {
                    result = "Оценка " + newGrade + " по предмету " + lesson + " не обновлена для ученика с ID " + studentId;
                }
                else {
                    result = "Оценка " + newGrade + " по предмету " + lesson + " успешно обновлена для ученика с ID " + studentId;
                }
                request.setAttribute("result", result);
            }

            commitTransaction();
        }
        catch (SQLException e) {
            rollbackTransaction();
            System.out.println(e.getMessage());
        }
    }

    private void resNext(List<StudentDTO> honorStudents, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String family = resultSet.getString(1);
            String name = resultSet.getString(2);
            int age = resultSet.getInt(3);
            int groupId = resultSet.getInt(4);
            double averageGrade = resultSet.getDouble(5);

            StudentDTO student = new StudentDTO(family, name, age, groupId, averageGrade);
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

