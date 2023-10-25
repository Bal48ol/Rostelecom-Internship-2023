package org.fubar;

class CommandBuilder {
    private final StudentService studentService;

    public CommandBuilder(StudentService studentService) {
        this.studentService = studentService;
    }

    public Command buildCommand(String commandName) {
        return switch (commandName) {
            case "command1" -> new Command1(studentService);
            case "command2" -> new Command2(studentService);
            case "command3" -> new Command3(studentService);
            default -> throw new IllegalArgumentException("Invalid command: " + commandName);
        };
    }
}


