package org.fubar;

import java.sql.SQLException;

public class Command1 implements Command {
    private final DatabaseHelper dbhelp;

    public Command1(DatabaseHelper dbhelp) {
        this.dbhelp = dbhelp;
    }

    @Override
    public void execute() {
        try {
            AverageGradesDTO averageGrades = dbhelp.getAverageGradesForSeniorClasses();
            System.out.println("Средние оценки по предметам в старших классах (10 и 11):");
            System.out.println("\tФизика: " + averageGrades.averagePhysics());
            System.out.println("\tМатематика: " + averageGrades.averageMathematics());
            System.out.println("\tРусский язык: " + averageGrades.averageRus());
            System.out.println("\tЛитература: " + averageGrades.averageLiterature());
            System.out.println("\tГеометрия: " + averageGrades.averageGeometry());
            System.out.println("\tИнформатика: " + averageGrades.averageInformatics());
        } catch (SQLException e) {
            System.out.println("\tОшибка при выполнении запроса: " + e.getMessage());
        }
    }
}

