package org.fubar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class FileDataLoader implements DataLoader {
    private final Path filePath;

    public FileDataLoader(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Person> loadData() throws IOException {
        List<Person> persons = new ArrayList<>();

        try (FileReader input = new FileReader(filePath.toString());
             BufferedReader reader = new BufferedReader(input);
             Scanner scanner = new Scanner(reader)) {

            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(";");
                String lastName = fields[0];
                String firstName = fields[1];
                int age = Integer.parseInt(fields[2]);
                int group = Integer.parseInt(fields[3]);
                int physics = Integer.parseInt(fields[4]);
                int mathematics = Integer.parseInt(fields[5]);
                int rus = Integer.parseInt(fields[6]);
                int literature = Integer.parseInt(fields[7]);
                int geometry = Integer.parseInt(fields[8]);
                int informatics = Integer.parseInt(fields[9]);

                Person person = new Person(firstName, lastName, age, group, physics, mathematics, rus, literature, geometry, informatics);
                persons.add(person);
            }
        }
        return persons;
    }
}
