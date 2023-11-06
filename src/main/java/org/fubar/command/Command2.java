package org.fubar.command;

import org.fubar.DatabaseHelper;
import org.fubar.dto.StudentDTO;

import java.sql.SQLException;
import java.util.List;

public class Command2 implements Command {
    private final DatabaseHelper dbhelp;

    public Command2(DatabaseHelper dbhelp) {
        this.dbhelp = dbhelp;
    }

    @Override
    public void execute() {
        try {
            List<StudentDTO> excellentStudents = dbhelp.getHonorStudentsAfter14();
            System.out.println("\nСписок отличников старше 14 лет:");
            for (StudentDTO o : excellentStudents){
                System.out.println(o);
            }
        }
        catch (SQLException e) {
            System.out.println("\tОшибка при выполнении запроса: " + e.getMessage());
        }
    }
}

