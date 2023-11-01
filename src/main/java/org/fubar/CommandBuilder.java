package org.fubar;

class CommandBuilder {
    private final DatabaseHelper dbhelp;

    public CommandBuilder(DatabaseHelper dbhelp) {
        this.dbhelp = dbhelp;
    }

    public Command buildCommand(String commandName) {
        return switch (commandName) {
            case "command1" -> new Command1(dbhelp);
            case "command2" -> new Command2(dbhelp);
            case "command3" -> new Command3(dbhelp);
            default -> throw new IllegalArgumentException("Неизвестная команда");
        };
    }
}


