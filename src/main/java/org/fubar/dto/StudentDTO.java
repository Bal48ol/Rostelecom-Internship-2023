package org.fubar.dto;

public record StudentDTO(int id, String family, String name, int age, int groupId, Double averageGrade) {

    @Override
    public String toString() {
        return "ID: " + id + ", Фамилия: " + family + ", Имя: " + name + ", Возраст: " + age + ", Класс: " + groupId + ", Средняя оценка: " + averageGrade;
    }
}