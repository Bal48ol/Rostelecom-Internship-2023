package org.fubar;

@FunctionalInterface
interface GroupCriterion {
    String getKey(Person person);
}