package org.fubar;

import java.io.IOException;

class Command2 implements Command {
    private final StudentService studentService;

    public Command2(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void execute() throws IOException {
        studentService.findHonorStudents();
    }
}
