package org.fubar;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        DatabaseHelper dbhelp = new DatabaseHelper();
        dbhelp.connect();
        CommandBuilder commandBuilder = new CommandBuilder(dbhelp);

        System.out.println("\nВведите команду для получения информации:");
        System.out.println("\tc1 - Средние оценки по предметам в старших классах (10 и 11)");
        System.out.println("\tc2 - Список всех отличников старше 14 лет");
        System.out.println("\tc3 - Средняя оценка ученика по указанной фамилии");
        System.out.println("\tq - Выход\n");

        Command command1 = commandBuilder.buildCommand("command1");
        Command command2 = commandBuilder.buildCommand("command2");
        Command command3 = commandBuilder.buildCommand("command3");

        try (Scanner scanner = new Scanner(System.in)){
            while (scanner.hasNext()){
                switch (scanner.nextLine()){
                    case "c1" -> command1.execute();
                    case "c2" -> command2.execute();
                    case "c3" -> command3.execute();
                    case "q" -> {
                        System.out.println("Завершение программы...");
                        System.exit(0);
                    }
                    default -> System.out.println("Неизвестная команда");
                }
            }
        }
        finally {
            dbhelp.disconnect();
        }
    }
}
