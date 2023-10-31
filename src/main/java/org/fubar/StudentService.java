package org.fubar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class StudentService {
    private final DataGroup classroomDataGroups;
    private final DataGroup personAgeDataGroups;
    private final DataGroup personNameDataGroup;

    public StudentService(DataLoader dataLoader) throws IOException {
        classroomDataGroups = new DataGroup(person -> String.valueOf(person.group()));
        personAgeDataGroups = new DataGroup(person -> String.valueOf(person.age()));
        personNameDataGroup = new DataGroup(person -> String.valueOf(person.lastName().charAt(0)));

        List<Person> persons = dataLoader.loadData();

        for (Person person : persons) {
            classroomDataGroups.addPerson(person);
            personAgeDataGroups.addPerson(person);
            personNameDataGroup.addPerson(person);
        }
    }

    public void calculateAverageMarks() {
        MyList<Person> tenClassStudents = classroomDataGroups.getPersons("10");
        MyList<Person> elevenClassStudents = classroomDataGroups.getPersons("11");
        System.out.println("\nСредняя оценка в 10 классе: " + averageMark(tenClassStudents));
        System.out.println("Средняя оценка в 11 классе: " + averageMark(elevenClassStudents));
    }

    public void findHonorStudents() {
        List<Person> honorStudents = new ArrayList<>();
        for (int age = 15; age <= 100; age++) {
            MyList<Person> studentsOver14 = personAgeDataGroups.getPersons(String.valueOf(age));
            for (Person student : studentsOver14) {
                if (student.physics() == 5 && student.mathematics() == 5 &&
                        student.rus() == 5 && student.literature() == 5 &&
                        student.geometry() == 5 && student.informatics() == 5) {
                    honorStudents.add(student);
                }
            }
        }
        System.out.println("\nОтличники старше 14 лет:");
        for (Person person : honorStudents) {
            System.out.println("\t" + person.lastName() + " " + person.firstName());
        }
    }

    public void searchStudentByLastName() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("\nВведите фамилию ученика для получения информации: ");
            String lastName = scanner.nextLine();
            MyList<Person> studentsWithLastName = personNameDataGroup.getPersons(String.valueOf(lastName.charAt(0)));
            List<Person> matchingStudents = new ArrayList<>();
            for (Person student : studentsWithLastName) {
                if (student.lastName().equalsIgnoreCase(lastName)) {
                    matchingStudents.add(student);
                }
            }
            System.out.println("Результаты поиска:");
            for (Person student : matchingStudents) {
                System.out.println(student.lastName() + " " + student.firstName() +
                        ", возраст: " + student.age() + ", класс: " + student.group());
            }
        }
    }

    private double averageMark(MyList<Person> classStudents) {
        int totalMarks = 0;
        int totalStudents = 0;
        for (Person person : classStudents) {
            totalMarks += person.physics() + person.mathematics() + person.rus() +
                    person.literature() + person.geometry() + person.informatics();
            totalStudents++;
        }
        return (double) totalMarks / (6 * totalStudents);
    }
}
