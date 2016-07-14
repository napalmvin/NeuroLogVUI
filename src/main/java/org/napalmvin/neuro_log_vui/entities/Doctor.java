/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.validator.constraints.Length;
import org.metawidget.inspector.annotation.UiRequired;

@Entity
@Table(name = "DOCTORS")
public class Doctor extends Person {

    @Transient public final static String QUALIFICATION = "qualification";
    @Transient public final static String[] FIELD_LIST = {
        ID,
        FIRST_NAME,
        MIDLLE_NAME,
        LAST_NAME,
        BIRTH_DATE,
        GENDER,
        RACE,
        QUALIFICATION,
        PHOTO_NAME
    };

    @Length(min = 1, max = 254)
    private String qualification;

    public Doctor() {
    }

    public String getQualification() {
        return qualification;
    }

    @UiRequired
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}
