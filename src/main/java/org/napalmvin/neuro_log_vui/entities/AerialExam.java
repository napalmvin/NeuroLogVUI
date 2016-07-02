/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.napalmvin.neuro_log_vui.entities.enums.ExamTypeEnum;

@Entity
@Table(name = "AERIAL_EXAM")
public class AerialExam {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ExamTypeEnum examType;
    
    @Column(nullable = true,length = 255)
    private String comments;
    
    public AerialExam() {
    }

    public void setExamType(ExamTypeEnum examType) {
        this.examType = examType;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public ExamTypeEnum getExamType() {
        return examType;
    }

    public String getComments() {
        return comments;
    }
    


}
