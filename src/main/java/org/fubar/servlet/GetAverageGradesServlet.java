package org.fubar.servlet;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.fubar.DatabaseHelper;
import org.fubar.dto.StudentDTO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Api(value = "Students Grades")
@WebServlet(urlPatterns = {"/students/average_grades/group_id/*"})
public class GetAverageGradesServlet extends HttpServlet {
    @ApiOperation(value = "Get Average Grades by Group ID")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        response.setCharacterEncoding("WINDOWS-1251");
        DatabaseHelper dbHelp = new DatabaseHelper();
        try {
            dbHelp.connect();
            String pathInfo = request.getPathInfo();

            if (pathInfo != null && pathInfo.length() > 1) {
                int groupId = Integer.parseInt(pathInfo.substring(1));
                List<StudentDTO> studentInfoList = dbHelp.getAverageGradeByGroupId(groupId);
                String jsonResponse = convertToJSON(studentInfoList);
                response.getWriter().write(jsonResponse);
            }
            else {
                response.getWriter().write("Параметр groupId отсутствует");
            }
        }
        catch (NumberFormatException e) {
            response.getWriter().write("Неверный формат groupId");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
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

    private String convertToJSON(List<StudentDTO> studentInfoList) {
        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (StudentDTO student : studentInfoList) {
            String jsonStudent = gson.toJson(student);
            sb.append(jsonStudent);
            sb.append(",\n");
        }
        if (!studentInfoList.isEmpty()) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("]");
        return sb.toString();
    }

}


