package org.napalmvin.neuro_log_vui.ui.doctor;

import org.napalmvin.neuro_log_vui.data.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.Arrays;
import java.util.Date;
import static org.napalmvin.neuro_log_vui.Constants.Type.IMAGE;
import org.napalmvin.neuro_log_vui.data.RaceEnum;
import org.napalmvin.neuro_log_vui.data.GenderEnum;
import org.napalmvin.neuro_log_vui.data.ImageRepository;
import org.napalmvin.neuro_log_vui.entities.Doctor;
import org.napalmvin.neuro_log_vui.ui.UploadReceiver;

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

    private final DoctorRepository doctorRepo;

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
    TextField photoName = new TextField("File name");
    Embedded image = new Embedded("Image");

    UploadReceiver receiver ;

    Upload upload = new Upload();
    
    /* Action buttons */
    Button save = new Button("Save", FontAwesome.SAVE);
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", FontAwesome.TRASH_O);

    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);
    VerticalLayout vl = new VerticalLayout(firstName, lastName, birthDate, gender, race, qualification, photoName, image, upload, actions);
    private final ImageRepository imageRepo;

    @Autowired
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public DoctorEditor(DoctorRepository doctorRepo,ImageRepository imageRepo) {
        this.doctorRepo = doctorRepo;
         this.imageRepo = imageRepo;
        initUI();
        addValidators();
    }

    private void initUI() {
        photoName.setVisible(false);
        vl.setSpacing(true);
        vl.setMargin(true);
        actions.setMargin(true);
        setContent(vl);
        receiver=new UploadReceiver(photoName, image, imageRepo, IMAGE);
        upload.setReceiver(receiver);
        upload.addSucceededListener(receiver);
        upload.addFailedListener(receiver);
        
        // Configure and style components
        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> doctorRepo.save(doctor));
        delete.addClickListener(e -> doctorRepo.delete(doctor));
        cancel.addClickListener(e -> editDoctor(doctor));
        image.setWidth(200, Unit.PIXELS);
        setVisible(false);
    }

    private void addValidators() {
        firstName.addValidator(new StringLengthValidator("Must have length  from 1 to 10", 1, 10, false));
        lastName.addValidator(new StringLengthValidator("Must have length  from 1 to 10", 1, 10, false));
        photoName.addValidator(new StringLengthValidator("File should be selected", 1, 125, false));
    }

    public interface ChangeHandler {
        
        void onChange();
    }

    public final void editDoctor(Doctor dr) {
        final boolean persisted = dr.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            doctor = doctorRepo.findOne(dr.getId());
            image.setSource(new ExternalResource(IMAGE.getParentFolder()+dr.getPhotoName()));
            image.setVisible(true);

        } else {
            doctor = dr;
            image.setSource(null);
            image.setVisible(false);

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