/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.entities;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Lob;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.metawidget.inspector.annotation.UiHidden;
import org.metawidget.inspector.annotation.UiRequired;
import org.napalmvin.neuro_log_vui.data.RaceEnum;
import org.napalmvin.neuro_log_vui.data.GenderEnum;

@Entity
@Table(name = "DOCTORS")
public class Doctor {

    public enum FieldsList {
        id,
        firstName,
        lastName,
        birthDate,
        gender,
        race,
        photoUrl,
        qualification;

        public static String[] getStringArray() {
            FieldsList[] values = FieldsList.values();
            String[] returnVal = new String[values.length];
            for (int i=0;i<values.length;i++) {
                returnVal[i]=values[i].toString();
            }
            return returnVal;
        }
        
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birthDate;
    private GenderEnum gender;
    private RaceEnum race;
    
    private String photoUrl;
    private String qualification;

    protected Doctor() {
    }

    public Doctor(String first_name, String last_name, Date birth_date, GenderEnum sex, RaceEnum race, String photo, String qualification) {
        this.firstName = first_name;
        this.lastName = last_name;
        this.birthDate = birth_date;
        this.gender = sex;
        this.race = race;
        this.photoUrl = photo;
        this.qualification = qualification;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getQualification() {
        return qualification;
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
        final Doctor other = (Doctor) obj;
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
    public void setPhotoUrl(String photo) {
        this.photoUrl = photo;
    }

    @UiRequired
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

}
