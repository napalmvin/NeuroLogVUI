package org.napalmvin.neuro_log_vui.ui.patient;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import java.util.Collections;
import java.util.List;
import org.napalmvin.neuro_log_vui.entities.Patient;
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
public class PatientEditor extends PersonEditor<Patient> {

    @Override
    public List<Component> attachSubComponents() {
       //Do nothing as PersonEditor has all that needed
       return Collections.emptyList();
    };

    @Override
    public void createAndInitSubComponents() {
        //Do nothing as PersonEditor has all that needed
    }
    
    
   

}
