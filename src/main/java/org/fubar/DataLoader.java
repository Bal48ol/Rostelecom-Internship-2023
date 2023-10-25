package org.fubar;

import java.io.IOException;
import java.util.List;

interface DataLoader {
    List<Person> loadData() throws IOException;
}