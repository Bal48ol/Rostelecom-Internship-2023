package org.fubar;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Command3 implements Command {
    private final DatabaseHelper dbhelp;

    public Command3(DatabaseHelper dbhelp) {
        this.dbhelp = dbhelp;
    }

    @Override
    public void execute() {
        try {
            System.out.println("Введите фамилию ученика:");
            Scanner scanner = new Scanner(System.in);
            String lastName = scanner.nextLine();

            List<AverageGradeByLastNameDTO> studentInfoList = dbhelp.getAverageGradeByLastName(lastName);
            if (studentInfoList.isEmpty()) {
                System.out.println("Ученики с указанной фамилией не найдены.");
            }
            else {
                System.out.println("\nИнформация об учениках с фамилией " + lastName + ":");
                for (AverageGradeByLastNameDTO studentInfo : studentInfoList) {
                    System.out.println(studentInfo);
                }
            }
        } catch (SQLException e) {
            System.out.println("\tОшибка при выполнении запроса: " + e.getMessage());
        }
    }
}



