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

        try (Scanner scanner = new Scanner(System.in)){
            while (scanner.hasNext()){
                switch (scanner.nextLine()){
                    case "c1":
                        Command command1 = commandBuilder.buildCommand("command1");
                        command1.execute();
                        break;
                    case "c2":
                        Command command2 = commandBuilder.buildCommand("command2");
                        command2.execute();
                        break;
                    case "c3":
                        Command command3 = commandBuilder.buildCommand("command3");
                        command3.execute();
                        break;
                    default:
                        System.out.println("Invalid command");
                }
            }
        }
    }
}
