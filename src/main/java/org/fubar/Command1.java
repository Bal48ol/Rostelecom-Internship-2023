package org.fubar;

import java.io.IOException;

class Command1 implements Command {
    private final StudentService studentService;

    public Command1(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void execute() throws IOException {
        studentService.calculateAverageMarks();
    }
}

