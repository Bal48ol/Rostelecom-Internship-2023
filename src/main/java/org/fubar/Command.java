package org.fubar;

import java.io.IOException;
import java.sql.SQLException;

interface Command {
    void execute() throws IOException, SQLException;
}