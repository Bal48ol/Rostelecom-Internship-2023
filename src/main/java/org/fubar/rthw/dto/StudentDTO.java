package org.fubar.rthw.dto;

public record StudentDTO(String family, String name, int groupId, double averageGrade) {

    @Override
    public String toString() {
        return "Фамилия: " + family + ", Имя: " + name + ", Класс: " + groupId + ", Средняя оценка: " + averageGrade;
    }
}