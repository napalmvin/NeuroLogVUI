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
import org.napalmvin.neuro_log_vui.entities.Doctor;
import org.napalmvin.neuro_log_vui.entities.Patient;
import org.napalmvin.neuro_log_vui.ui.doctor.PersonComponent;
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
    private DoctorRepository repo;

    @Override
    public <T extends Field> T createField(Class<?> type, Class<T> fieldType) {
        if (Doctor.class.isAssignableFrom(type)) {
            return createDoctorField(type, fieldType);
        } else if (Enum.class.isAssignableFrom(Patient.class)) {
            return super.createField(type, fieldType);
//            return createPatientField(type, fieldType);
        } else {
            return super.createField(type, fieldType);
        }
    }

    private <T extends Object & Field> T createDoctorField(Class<?> type, Class<T> fieldType) {
        return (T) new PersonComponent(msg,repo);
    }

    private <T extends Object & Field> T createPatientField(Class<?> type, Class<T> fieldType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

}
