/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.entities;

import java.util.Date;
import org.napalmvin.neuro_log_vui.entities.enums.RaceEnum;
import org.napalmvin.neuro_log_vui.entities.enums.GenderEnum;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.metawidget.inspector.annotation.UiHidden;
import org.metawidget.inspector.annotation.UiRequired;

@Entity
@Table(name = "PATIENTS")
public class Patient {

    public enum FieldsList {
        id,
        firstName,
        middleName,
        lastName,
        birthDate,
        gender,
        race,
        photoName;

        public static String[] getStringArray() {
            FieldsList[] values = FieldsList.values();
            String[] returnVal = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                returnVal[i] = values[i].toString();
            }
            return returnVal;
        }

    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty
    @Length(min = 1, max = 25)
    private String firstName;

    @Length(min = 0, max = 25)
    private String middleName;

    @NotEmpty
    @Length(min = 1, max = 25)
    private String lastName;

    @Temporal(javax.persistence.TemporalType.DATE)
    @NotNull
    private Date birthDate;

    @Enumerated(EnumType.STRING)
    @NotNull
//    @javax.validation.constraints.Size(min = 1, max = 25)
    private GenderEnum gender;

    @Enumerated(EnumType.STRING)
    @NotNull
//    @javax.validation.constraints.Size(min = 1, max = 25)
    private RaceEnum race;

//    @OneToOne(targetEntity = Image.class)
    @NotEmpty
    private String photoName;

    public Patient() {
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMiddleName() {
        return middleName;
    }

    @UiHidden
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public RaceEnum getRace() {
        return race;
    }

    public String getPhotoName() {
        return photoName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.firstName);
        hash = 83 * hash + Objects.hashCode(this.lastName);
        hash = 83 * hash + Objects.hashCode(this.birthDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Patient other = (Patient) obj;
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.birthDate, other.birthDate)) {
            return false;
        }
        return true;
    }

    @UiRequired
    public void setFirstName(String first_name) {
        this.firstName = first_name;
    }

    @UiRequired
    public void setLastName(String last_name) {
        this.lastName = last_name;
    }

    @UiRequired
    public void setBirthDate(Date birth_date) {
        this.birthDate = birth_date;
    }

    @UiRequired
    public void setGender(GenderEnum sex) {
        this.gender = sex;
    }

    @UiRequired
    public void setRace(RaceEnum race) {
        this.race = race;
    }

    @UiRequired
    public void setPhotoName(String photo) {
        this.photoName = photo;
    }
}
