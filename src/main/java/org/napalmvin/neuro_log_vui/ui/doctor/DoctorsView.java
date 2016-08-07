package org.napalmvin.neuro_log_vui.ui.doctor;


import com.vaadin.annotations.Theme;
import com.vaadin.spring.annotation.SpringView;
import static org.napalmvin.neuro_log_vui.TextConstants.NEW_DOCTOR;
import org.napalmvin.neuro_log_vui.entities.Doctor;
import org.napalmvin.neuro_log_vui.ui.common.AbstractTemplateView;

@Theme("mytheme")
@SpringView(name = "doctors")
public class DoctorsView extends AbstractTemplateView<Doctor> {

    @Override
    public Doctor getNewEmptyInstance() {
        return new Doctor();
    }

    @Override
    protected String getAddNewButtonCaption() {
        return NEW_DOCTOR;
    }


}
