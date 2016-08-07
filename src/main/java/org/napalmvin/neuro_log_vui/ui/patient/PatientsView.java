package org.napalmvin.neuro_log_vui.ui.patient;


import com.vaadin.annotations.Theme;
import com.vaadin.spring.annotation.SpringView;
import static org.napalmvin.neuro_log_vui.TextConstants.NEW_PATIENT;
import org.napalmvin.neuro_log_vui.entities.Patient;
import org.napalmvin.neuro_log_vui.ui.common.AbstractTemplateView;

@Theme("mytheme")
@SpringView(name = "patients")
public class PatientsView extends AbstractTemplateView<Patient> {

    @Override
    public Patient getNewEmptyInstance() {
        return new Patient();
    }

    @Override
    protected String getAddNewButtonCaption() {
        return NEW_PATIENT;
    }


}
