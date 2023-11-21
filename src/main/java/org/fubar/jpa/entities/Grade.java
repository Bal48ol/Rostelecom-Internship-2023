package org.fubar.jpa.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "grades")
@Data
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "physics")
    private Integer physics;

    @Column(name = "mathematics")
    private Integer mathematics;

    @Column(name = "rus")
    private Integer rus;

    @Column(name = "literature")
    private Integer literature;

    @Column(name = "geometry")
    private Integer geometry;

    @Column(name = "informatics")
    private Integer informatics;
}

