/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.entities;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.napalmvin.neuro_log_vui.entities.enums.ExamTypeEnum;

@Entity
@Table(name = "GENERAL_EXAM")
public class GeneralExam {

    @Transient public static final String ID = "id";
    @Transient public static final String PATIENT = "patient";
    @Transient public static final String DOCTOR = "doctor";
    @Transient public static final String TAKEN = "taken";
    @Transient public static final String AERIAL_EXAMS = "aerialExams";

    @Transient public static final String[] FIELD_LIST={ID,PATIENT,DOCTOR,TAKEN,AERIAL_EXAMS};
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Temporal(javax.persistence.TemporalType.DATE)
    @NotNull
    private Date taken;

    @NotNull
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "GENERAL_EXAM_ID")
    private List<AerialExam> aerialExams = new LinkedList<>();


    public GeneralExam() {
    }

    public List<AerialExam> getAerialExams() {
        return Collections.unmodifiableList(aerialExams);
    }

    public void removeEmptyAerialExams() {
        for (int i = 0; i < aerialExams.size(); i++) {
            AerialExam aerExam = aerialExams.get(i);
            if (aerExam.getComments() == null || aerExam.getComments().isEmpty()) {
                aerialExams.remove(i);
                i--;
            }
        }
    }

    public void addAerialExam(AerialExam aerialExam) {
        aerialExams.add(aerialExam);

    }

    public void setAerialExams(List<AerialExam> aerialExams) {
        this.aerialExams = aerialExams;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setTaken(Date taken) {
        this.taken = taken;
    }

    public Long getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public Date getTaken() {
        return taken;
    }

    public AerialExam getAerialExam(ExamTypeEnum type) {
        for (AerialExam aerEx : aerialExams) {
            if (aerEx.getExamType().equals(type)) {
                return aerEx;
            }

        }
        return null;
    }

}
