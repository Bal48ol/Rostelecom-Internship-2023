package org.fubar.command;

import java.io.IOException;
import java.sql.SQLException;

public interface Command {
    void execute() throws IOException, SQLException;
}