package org.fubar.command;

import org.fubar.DatabaseHelper;

import java.sql.SQLException;
import java.util.Scanner;

// ТЕСТ
public class Command4 implements Command{

    private final DatabaseHelper dbhelp;

    public Command4(DatabaseHelper dbhelp) {
        this.dbhelp = dbhelp;
    }

    @Override
    public void execute() throws SQLException {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("\nВведите фамилию ученика: ");
            String lastName = scanner.nextLine();

            System.out.println("\nВведите имя ученика: ");
            String firstName = scanner.nextLine();

            System.out.println("\nВведите класс ученика: ");
            int groupId = Integer.parseInt(scanner.nextLine());

            System.out.println("\nВведите урок, по которому нужно изменить оценку: ");
            String lesson = scanner.nextLine();

            System.out.println("\nВведите новую оценку: ");
            int newGrade = Integer.parseInt(scanner.nextLine());

            dbhelp.updateStudentGrade(lastName, firstName, groupId, lesson, newGrade);
        }
    }
}
