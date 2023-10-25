package org.fubar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DataGroup {
    private final Map<String, List<Person>> dataGroups;
    private final GroupCriterion criterion;

    public DataGroup(GroupCriterion criterion) {
        dataGroups = new HashMap<>();
        this.criterion = criterion;
    }

    public void addPerson(Person person) {
        String key = criterion.getKey(person);
        List<Person> groupData = dataGroups.getOrDefault(key, new ArrayList<>());
        groupData.add(person);
        dataGroups.put(key, groupData);
    }

    public List<Person> getPersons(String key) {
        return dataGroups.getOrDefault(key, new ArrayList<>());
    }
}