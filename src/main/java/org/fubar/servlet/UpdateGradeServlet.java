package org.fubar.servlet;

import org.fubar.DatabaseHelper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/update/grade/")
public class UpdateGradeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        DatabaseHelper dbHelp = new DatabaseHelper();

        String lastName = request.getParameter("lastName");
        System.out.println(lastName);

        String firstName = request.getParameter("firstName");
        System.out.println(firstName);

        int groupId = Integer.parseInt(request.getParameter("groupId"));
        System.out.println(groupId);

        String lesson = request.getParameter("lesson");
        System.out.println(lesson);

        int newGrade = Integer.parseInt(request.getParameter("newGrade"));
        System.out.println(newGrade);

        try {
            dbHelp.connect();
            dbHelp.updateStudentGrade(lastName, firstName, groupId, lesson, newGrade);
            response.getWriter().println("Оценка успешно обновлена: " + lastName + " " + firstName + " " + groupId + " " + lesson + " " + newGrade);
        }
        catch (SQLException e) {
            response.getWriter().println("Ошибка при обновлении оценки: " + e.getMessage());
        }
        finally {
            try {
                dbHelp.disconnect();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
