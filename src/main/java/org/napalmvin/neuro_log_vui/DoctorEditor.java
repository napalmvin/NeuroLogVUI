package org.napalmvin.neuro_log_vui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import org.napalmvin.neuro_log_vui.data.RaceEnum;
import org.napalmvin.neuro_log_vui.data.GenderEnum;
import org.napalmvin.neuro_log_vui.entities.Doctor;

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
public class DoctorEditor extends Panel {

    private final DoctorRepository repository;

    /**
     * The currently edited customer
     */
    private Doctor doctor;


    /* Fields to edit properties in Customer entity */
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    DateField birthDate = new DateField("Birth date", new Date());
    TextField qualification = new TextField("Qualificaton");
    ComboBox gender = new ComboBox("Sex", Arrays.asList(GenderEnum.values()));
    ComboBox race = new ComboBox("Race", Arrays.asList(RaceEnum.values()));
    TextField photoUrl = new TextField("File name");
    Embedded image = new Embedded("Image");

    ImageUploadeReceiver receiver = new ImageUploadeReceiver(photoUrl, image);

    Upload upload = new Upload("Doctor photo", receiver);
    /* Action buttons */
    Button save = new Button("Save", FontAwesome.SAVE);
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", FontAwesome.TRASH_O);

    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);
    VerticalLayout vl = new VerticalLayout(firstName, lastName, birthDate, gender, race, qualification, photoUrl, image, upload, actions);

    @Autowired
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public DoctorEditor(DoctorRepository repository) {
        this.repository = repository;
        photoUrl.setReadOnly(true);
        vl.setSpacing(true);
        vl.setMargin(true);
        actions.setMargin(true);
        setContent(vl);

        // Configure and style components
        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> repository.save(doctor));
        delete.addClickListener(e -> repository.delete(doctor));
        cancel.addClickListener(e -> editDoctor(doctor));
        setVisible(false);
    }

    public interface ChangeHandler {

        void onChange();
    }

    public final void editDoctor(Doctor dr) {
        final boolean persisted = dr.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            doctor = repository.findOne(dr.getId());
            image.setSource(new ExternalResource(dr.getPhotoUrl()));

        } else {
            doctor = dr;
            image.setSource(null);
        }
        cancel.setVisible(persisted);

        // Bind customer properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        BeanFieldGroup.bindFieldsUnbuffered(doctor, this);
//        setSizeFull();
        setVisible(true);

        // A hack to ensure the whole form is visible
        save.focus();
        // Select all text in firstName field automatically
        firstName.selectAll();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        save.addClickListener(e -> h.onChange());
        delete.addClickListener(e -> h.onChange());
    }

}
