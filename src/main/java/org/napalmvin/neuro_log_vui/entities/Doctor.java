/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Length;
import org.metawidget.inspector.annotation.UiRequired;

@Entity
@Table(name = "DOCTORS")
public class Doctor extends Person{

    public enum FieldsList {
        id,
        firstName,
        middleName,
        lastName,
        birthDate,
        gender,
        race,
        photoName,
        qualification;

        public static String[] valuesAsStrings() {
            int size=values().length;
             String[] resultArr=new String[size];
             for(int i=0;i<size;i++){
                 resultArr[i]=values()[i].name();
             }
            return resultArr;
        }
    }

   

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
