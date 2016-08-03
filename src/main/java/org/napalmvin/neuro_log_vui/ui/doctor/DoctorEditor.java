package org.napalmvin.neuro_log_vui.ui.doctor;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import java.util.LinkedList;
import java.util.List;
import org.napalmvin.neuro_log_vui.entities.Doctor;
import org.napalmvin.neuro_log_vui.entities.Person;
import org.napalmvin.neuro_log_vui.ui.person.PersonEditor;

/**
 * A simple example to introduce building forms. As your real application is
 * probably much more complicated than this example, you could re-use this form
 * in multiple places. This example component is only used in VaadinUI.
 * <p>
 * In a real world application you'll most likely using a common super class for
 * all your forms - less code, better UX. See e.g. AbstractForm in Virin
 * (https://vaadin.com/addon/viritin).
 */
@SpringComponent
@UIScope
public class DoctorEditor extends PersonEditor<Person> {

    TextField qualification;

    @Override
    public List<Component> attachSubComponents() {
        List<Component> ret = new LinkedList<>();
        ret.add(qualification);
        return ret;
    }

    @Override
    public void initSubComponents() {
        qualification = new TextField(msg.getString(Doctor.QUALIFICATION));
    }

}
