package org.napalmvin.neuro_log_vui.ui.doctor;

import org.napalmvin.neuro_log_vui.data.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
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
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;
import org.napalmvin.neuro_log_vui.PathConstants;
import static org.napalmvin.neuro_log_vui.PathConstants.Type.IMAGE;
import org.napalmvin.neuro_log_vui.entities.enums.RaceEnum;
import org.napalmvin.neuro_log_vui.entities.enums.GenderEnum;
import org.napalmvin.neuro_log_vui.data.ImageRepository;
import org.napalmvin.neuro_log_vui.entities.Doctor;
import org.napalmvin.neuro_log_vui.ui.ChangeHandler;
import org.napalmvin.neuro_log_vui.ui.UploadReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class DoctorEditor extends Panel implements UploadReceiver.AfterUploadSuceededListener {
    private ResourceBundle msg;
    private final DoctorRepository doctorRepo;
    private final ImageRepository imageRepo;
    private BeanFieldGroup<Doctor> bindFieldsUnbuffered;

    private Doctor doctor;

    TextField firstName;
    TextField lastName;
    DateField birthDate;
    TextField qualification;
    ComboBox gender;
    ComboBox race;
    TextField photoName;
    Embedded image;

    UploadReceiver receiver;
    Upload upload;
    ProgressBar progressBar;

    Button save;
    Button cancel;
    Button delete;
    HorizontalLayout actions;
    VerticalLayout vl;

    final static Logger log = LoggerFactory.getLogger(DoctorEditor.class);

    @Autowired
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public DoctorEditor(DoctorRepository doctorRepo, ImageRepository imageRepo, ResourceBundle msg) {        
        this.msg = msg;
        this.doctorRepo = doctorRepo;
        this.imageRepo = imageRepo;
        createUI();
        initUI();
        addValidators();
    }

    private void createUI() {
       
        
        this.delete = new Button(msg.getString("delete"), FontAwesome.TRASH_O);
        this.cancel = new Button(msg.getString("cancel"), FontAwesome.RECYCLE);
        this.save = new Button(msg.getString("save"), FontAwesome.SAVE);
        this.actions = new HorizontalLayout(save, cancel, delete);
        
        this.progressBar = new ProgressBar(0f);
        this.upload = new Upload();
        upload.setCaption(msg.getString("upload"));
        this.image = new Embedded(msg.getString("image"));
        this.photoName = new TextField(msg.getString("photoName"));
        this.gender = new ComboBox(msg.getString("gender"), Arrays.asList(GenderEnum.values()));
        this.race = new ComboBox(msg.getString("race"), Arrays.asList(RaceEnum.values()));
        this.qualification = new TextField(msg.getString("qualification"));
        this.birthDate = new DateField(msg.getString("birthDate"), new Date());
        this.lastName = new TextField(msg.getString("lastName"));
        this.firstName = new TextField(msg.getString("firstName"));
        
        this.vl = new VerticalLayout(firstName, lastName, birthDate, gender, race, qualification, image, upload, progressBar, photoName, actions);
        
    }

    private void initUI() {
        firstName.setId("firstName");
        lastName.setId("lastName");
        birthDate.setId("birthDate");
        qualification.setId("qualification");
        gender.setId("gender");
        race.setId("race");
        image.setId("image");

        upload.setId("upload");
        save.setId("save");
        cancel.setId("cancel");
        delete.setId("delete");

        vl.setSpacing(true);
        vl.setMargin(true);
        actions.setMargin(true);
        setContent(vl);
        receiver = new UploadReceiver(imageRepo, IMAGE);
        upload.setReceiver(receiver);
        upload.addSucceededListener(receiver);
        upload.addFailedListener(receiver);
        upload.addProgressListener(new Upload.ProgressListener() {
            @Override
            public void updateProgress(long readBytes, long contentLength) {
                if (!progressBar.isVisible()) {
                    progressBar.setVisible(true);
                }
                progressBar.setValue(((float) readBytes) / contentLength);
            }
        });

        receiver.addAfterUploadSuceededListener(this);

        // Configure and style components
        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> {
            try {
                bindFieldsUnbuffered.commit();
            } catch (FieldGroup.CommitException ex) {
                log.error("", ex);
            }
            doctorRepo.save(doctor);
        });
        delete.addClickListener(e -> {
            doctorRepo.delete(doctor);
        });
        cancel.addClickListener(e -> {
            editDoctor(doctor);
        });
        image.setWidth(200, Unit.PIXELS);
        progressBar.setVisible(false);
        setVisible(false);
    }

    private void addValidators() {
//        StringLengthValidator lengthValidator = new StringLengthValidator(msg.getString("length_limitation_1_25"),
//                1, 25, false);
//        firstName.addValidator(lengthValidator);
//        lastName.addValidator(lengthValidator);
//        photoName.addValidator(new StringLengthValidator(msg.getString("file_selection"), 1, 125, false));
//        RegexpValidator raceValidator = new RegexpValidator(
//                RaceEnum.Afropoid+"|"+RaceEnum.Mongoloid+"|"+RaceEnum.Caucasian,"Elements must correspong ");
//        race.addValidator(raceValidator);
//        
//        birthDate.addValidator(
//                new DateRangeValidator("Select right date", new Date(1900, 1, 1), new Date(), Resolution.MONTH));
//        
//        RegexpValidator genderValidator = new RegexpValidator(
//                GenderEnum.MALE+"|"+GenderEnum.FEMALE,"Elements must correspong enum");
//        gender.addValidator(genderValidator);

    }

    public final void editDoctor(Doctor dr) {
        final boolean persisted = dr.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            doctor = doctorRepo.findOne(dr.getId());
            image.setSource(new ExternalResource(IMAGE.getParentFolder() + dr.getPhotoName()));
            image.setVisible(true);

        } else {
            doctor = dr;
            image.setSource(null);
            image.setVisible(false);

        }

        // Bind customer properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        bindFieldsUnbuffered = BeanFieldGroup.bindFieldsUnbuffered(doctor, this);

//        setSizeFull();
        setVisible(true);

        // A hack to ensure the whole form is visible
        save.focus();
        // Select all text in firstName field automatically
        firstName.selectAll();
    }

    @Override
    public void afterUploadSuceeded(Upload.SucceededEvent event, String fileName, PathConstants.Type type) {
        photoName.setValue(fileName);

        progressBar.setVisible(false);

        image.setSource(new ExternalResource(type.getParentFolder() + fileName));
        image.setVisible(true);
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        save.addClickListener(e ->{
            h.onChange();
                });
        delete.addClickListener(e -> h.onChange());
        cancel.addClickListener(e -> h.onChange());
    }

}
