package org.fubar;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        DataLoader dataLoader = new FileDataLoader(Paths.get("C:\\Users\\dayof\\IdeaProjects\\hw1\\src\\main\\resources\\students.csv"));
        StudentService studentService = new StudentService(dataLoader);
        CommandBuilder commandBuilder = new CommandBuilder(studentService);

        System.out.println("\nВведите команду для получения информации:");
        System.out.println("\tc1 - Средняя оценка");
        System.out.println("\tc2 - Поиск всех отличников старше 14");
        System.out.println("\tc3 - Поиск ученика по фамили");

        Command command1 = commandBuilder.buildCommand("command1");
        Command command2 = commandBuilder.buildCommand("command2");
        Command command3 = commandBuilder.buildCommand("command3");

        try (Scanner scanner = new Scanner(System.in)){
            while (scanner.hasNext()){
                switch (scanner.nextLine()){
                    case "c1" -> command1.execute();
                    case "c2" -> command2.execute();
                    case "c3" -> command3.execute();
                    default -> System.out.println("Invalid command");
                }
            }
        }
    }
}
