package org.fubar.service;

import lombok.RequiredArgsConstructor;
import org.fubar.dto.GradesDTO;
import org.fubar.jpa.entities.Grade;
import org.fubar.jpa.repositories.GradeRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;

    @Override
    public GradesDTO getGradesById(Integer studentId) {
        return gradeRepository.findByStudentId(studentId)
                .map(grade -> new GradesDTO(grade.getPhysics(), grade.getMathematics(), grade.getRus(),
                        grade.getLiterature(), grade.getGeometry(), grade.getInformatics()))
                .orElse(null);
    }

    @Override
    public GradesDTO updateGrade(Integer studentId, String subject, Integer newGrade) {
        return gradeRepository.findByStudentId(studentId)
                .map(grade -> updateGradeAndCreateDTO(grade, subject, newGrade))
                .orElse(null);
    }

    private GradesDTO updateGradeAndCreateDTO(Grade grade, String subject, Integer newGrade) {
        switch (subject) {
            case "physics":
                grade.setPhysics(newGrade);
                break;
            case "mathematics":
                grade.setMathematics(newGrade);
                break;
            case "rus":
                grade.setRus(newGrade);
                break;
            case "literature":
                grade.setLiterature(newGrade);
                break;
            case "geometry":
                grade.setGeometry(newGrade);
                break;
            case "informatics":
                grade.setInformatics(newGrade);
                break;
            default:
                return null;
        }

        gradeRepository.save(grade);
        return new GradesDTO(grade.getPhysics(), grade.getMathematics(), grade.getRus(),
                grade.getLiterature(), grade.getGeometry(), grade.getInformatics());
    }
}
