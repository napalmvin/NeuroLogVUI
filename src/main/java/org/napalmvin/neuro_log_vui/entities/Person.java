/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.entities;

import org.napalmvin.neuro_log_vui.entities.interfaces.FieldListEntity;
import java.util.Date;
import org.napalmvin.neuro_log_vui.entities.enums.RaceEnum;
import org.napalmvin.neuro_log_vui.entities.enums.GenderEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public abstract class Person implements FieldListEntity{

    @Transient public static final String ID = "id";
    @Transient public static final String FIRST_NAME = "firstName";
    @Transient public static final String MIDLLE_NAME = "middleName";
    @Transient public static final String LAST_NAME = "lastName";
    @Transient public static final String BIRTH_DATE = "birthDate";
    @Transient public static final String GENDER = "gender";
    @Transient public static final String RACE = "race";
    @Transient public static final String PHOTO_NAME = "photoName";
    
    @Transient private  final String[] FIELD_LIST = {
        ID,
        FIRST_NAME,
        MIDLLE_NAME,
        LAST_NAME,
        BIRTH_DATE,
        GENDER,
        RACE,
        PHOTO_NAME};

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long id;

    @NotEmpty
    @Length(min = 1, max = 25)
    protected String firstName;

    @Length(min = 0, max = 25)
    protected String middleName;

    @NotEmpty
    @Length(min = 1, max = 25)
    protected String lastName;

    @Temporal(javax.persistence.TemporalType.DATE)
    @NotNull
    protected Date birthDate;

    @Enumerated(EnumType.STRING)
    @NotNull
//    @javax.validation.constraints.Size(min = 1, max = 25)
    protected GenderEnum gender;

    @Enumerated(EnumType.STRING)
    @NotNull
//    @javax.validation.constraints.Size(min = 1, max = 25)
    protected RaceEnum race;

//    @OneToOne(targetEntity = Image.class)
    @NotEmpty
    protected String photoName;

    public Person() {
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public RaceEnum getRace() {
        return race;
    }

    public void setRace(RaceEnum race) {
        this.race = race;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    @Override
    public String[] getFieldList() {
        return FIELD_LIST;
    }

    
    
    
}
