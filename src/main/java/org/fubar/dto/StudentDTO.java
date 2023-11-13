package org.fubar.dto;

public record StudentDTO(String family, String name, int age, int groupId, double averageGrade) {

    @Override
    public String toString() {
        return "Фамилия: " + family + ", Имя: " + name + ", Возраст: " + age + ", Класс: " + groupId + ", Средняя оценка: " + averageGrade;
    }
}