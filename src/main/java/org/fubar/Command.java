package org.fubar;

import java.io.IOException;

interface Command {
    void execute() throws IOException;
}