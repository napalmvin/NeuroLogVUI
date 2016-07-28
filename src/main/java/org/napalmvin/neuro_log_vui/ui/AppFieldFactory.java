/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.ui;

import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Field;
import java.util.ResourceBundle;
import org.napalmvin.neuro_log_vui.data.DoctorRepository;
import org.napalmvin.neuro_log_vui.data.PatientRepository;
import org.napalmvin.neuro_log_vui.entities.Doctor;
import org.napalmvin.neuro_log_vui.entities.Patient;
import org.napalmvin.neuro_log_vui.ui.person.PersonSelectionComponent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author LOL
 */
@SpringComponent
public class AppFieldFactory extends DefaultFieldGroupFieldFactory {

    @Autowired
    private ResourceBundle msg;

    @Autowired
    private DoctorRepository repositoryDoctors;

    @Autowired
    private PatientRepository repositoryPatients;

    @Override
    public <T extends Field> T createField(Class<?> type, Class<T> fieldType) {
        if (Doctor.class.isAssignableFrom(type)) {
            return createDoctorField(type, fieldType);
        } else if (Patient.class.isAssignableFrom(type)) {
            return createPatientField(type, fieldType);
        } else {
            return super.createField(type, fieldType);
        }
    }

    private <T extends Object & Field> T createDoctorField(Class<?> type, Class<T> fieldType) {
        PersonSelectionComponent<Doctor> ret = new PersonSelectionComponent<>(msg, repositoryDoctors, Doctor.class);
//        ret.setIConverter(new PersonToStringConverter<Doctor>(Doctor.class));
        return (T) ret;
    }

    private <T extends Object & Field> T createPatientField(Class<?> type, Class<T> fieldType) {
        PersonSelectionComponent<Patient> ret = new PersonSelectionComponent<>(msg, repositoryPatients, Patient.class);
//        ret.setConverter(new PersonToStringConverter<Patient>(Patient.class));
        return (T) ret;
    }

}
