package org.fubar;

class DataGroup {
    private final MyMap<String, MyList<Person>> dataGroups;
    private final GroupCriterion criterion;

    public DataGroup(GroupCriterion criterion) {
        dataGroups = new MyMap<>();
        this.criterion = criterion;
    }

    public void addPerson(Person person) {
        String key = criterion.getKey(person);
        MyList<Person> groupData = dataGroups.getOrDefault(key, new MyList<>());
        groupData.add(person);
        dataGroups.put(key, groupData);
    }

    public MyList<Person> getPersons(String key) {
        return dataGroups.getOrDefault(key, new MyList<>());
    }
}