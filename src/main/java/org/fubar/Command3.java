package org.fubar;

import java.io.IOException;

class Command3 implements Command {
    private final StudentService studentService;

    public Command3(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void execute() throws IOException {
        studentService.searchStudentByLastName();
    }
}