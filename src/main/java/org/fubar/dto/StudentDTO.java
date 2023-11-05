package org.fubar.dto;

public record StudentDTO(String family, String name, int groupId, double averageGrade) {

    @Override
    public String toString() {
        return "\tФамилия: " + family + ", Имя: " + name + ", Класс: " + groupId + ", Средняя оценка: " + averageGrade;
    }
}