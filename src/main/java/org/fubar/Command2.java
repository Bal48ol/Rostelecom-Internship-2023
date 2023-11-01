package org.fubar;

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
            List<HonorStudentDTO> excellentStudents = dbhelp.getHonorStudentsAfter14();
            System.out.println("\nСписок отличников старше 14 лет:");
            for (HonorStudentDTO o : excellentStudents){
                System.out.println(o);
            }
        }
        catch (SQLException e) {
            System.out.println("\tОшибка при выполнении запроса: " + e.getMessage());
        }
    }
}

