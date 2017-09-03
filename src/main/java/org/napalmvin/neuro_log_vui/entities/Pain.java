/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Pain {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "GENERAL_EXAM_ID")
    private Long generalExamId;

    @Size(max = 32)
    @NotNull
    private String location;
    @Size(max = 64)
    @NotNull
    private String characterized;
    @Size(max = 32)
    @NotNull
    private String radiation;
    @Min(0)
    @Max(10)
    private byte severity_now;
    @Min(0)
    @Max(10)
    private byte severity_at_worst;
    @Size(max = 64)
    @NotNull
    private String duration_and_timing;
    @Size(max = 64)
    @NotNull
    private String provocative_factors;
    @Size(max = 64)
    @NotNull
    private String palliating_factors;
    @Size(max = 64)
    @NotNull
    private String pain_beliefs_or_expectations;
    
	@Size(max = 64)
    @NotNull
    private String cause;//what is causing my pain?
    @Size(max = 64)
    @NotNull
    private String meaning;//work will worsen my pain because...
    @Size(max = 64)
    @NotNull
    private String impact;//-- on my work/social/recreational/quality of life in general.
    @Size(max = 64)
    @NotNull
    private String treatment;//-- what needs to be done now and in the future for my pain.
    @Size(max = 64)
    @NotNull
    private String goals;//-- what i expect to achieve with treatment.
    @Size(max = 64)
    @NotNull
    private String involvement;//-- how involved will i be in my treatment?

}
