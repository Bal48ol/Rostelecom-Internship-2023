package org.fubar.service;

import org.fubar.dto.GradesDTO;

public interface GradeService {
    GradesDTO getGradesById(Integer studentId);
    GradesDTO updateGrade(Integer studentId, String subject, Integer newGrade);
}
