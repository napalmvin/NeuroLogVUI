package org.napalmvin.neuro_log_vui.ui.person;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
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
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import org.napalmvin.neuro_log_vui.PathConstants;
import static org.napalmvin.neuro_log_vui.PathConstants.Type.IMAGE;
import org.napalmvin.neuro_log_vui.entities.enums.RaceEnum;
import org.napalmvin.neuro_log_vui.entities.enums.GenderEnum;
import org.napalmvin.neuro_log_vui.data.ImageRepository;
import org.napalmvin.neuro_log_vui.data.PersonRepository;
import org.napalmvin.neuro_log_vui.entities.Person;
import org.napalmvin.neuro_log_vui.ui.ChangeHandler;
import org.napalmvin.neuro_log_vui.ui.UploadReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PersonEditor<T extends Person> extends Panel implements UploadReceiver.AfterUploadSuceededListener {

    @Autowired
    protected ResourceBundle msg;
    @Autowired
    private PersonRepository<T> personRepo;
    @Autowired
    private ImageRepository imageRepo;

    private BeanFieldGroup fieldGroup;

    private T person;

    TextField firstName;
    TextField lastName;
    DateField birthDate;
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

    final static Logger log = LoggerFactory.getLogger(PersonEditor.class);

    public final void edit(T pt) {

        fillPesonFromRepositoryIfExistsOrFromParameter(pt);
        showImageForExistantPersonOrHideItIfPersonIsNotPersisted(pt);

        createAndfillFiledGroupWithPropertiesOf(pt);

        makeEditorVisibleAndFocusOnFirstField();
    }

    @PostConstruct
    private void createAndBindUI() {
        initSubComponents();
        createUI();
        initUI();
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
        this.birthDate = new DateField(msg.getString("birthDate"), new Date());
        this.lastName = new TextField(msg.getString("lastName"));
        this.firstName = new TextField(msg.getString("firstName"));

        LinkedList<Component> components = new LinkedList();
        components.add(firstName);
        components.add(lastName);
        components.add(birthDate);
        components.add(gender);
        components.add(race);
        components.addAll(attachSubComponents());
        components.add(image);
        components.add(upload);
        components.add(progressBar);
        components.add(photoName);
        components.add(actions);
        this.vl = new VerticalLayout();
        vl.addComponents(components.toArray(new Component[components.size()]));

    }

    public abstract List<Component> attachSubComponents();

    public abstract void initSubComponents();

    private void initUI() {
        setComponentsIDsHtmlAttribute();

        vl.setSpacing(true);
        vl.setMargin(true);
        actions.setMargin(true);
        this.setContent(vl);
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
                fieldGroup.commit();
            } catch (FieldGroup.CommitException ex) {
                log.error("", ex);
            }
            personRepo.save(person);
        });
        delete.addClickListener(e -> {
            personRepo.delete(person);
        });
        cancel.addClickListener(e -> {
            edit(person);
        });
        image.setWidth(200, Unit.PIXELS);
        progressBar.setVisible(false);
        this.setVisible(false);
    }

    private void setComponentsIDsHtmlAttribute() {
        firstName.setId("firstName");
        lastName.setId("lastName");
        birthDate.setId("birthDate");
        gender.setId("gender");
        race.setId("race");
        image.setId("image");

        upload.setId("upload");
        save.setId("save");
        cancel.setId("cancel");
        delete.setId("delete");
    }

    private void showImageForExistantPersonOrHideItIfPersonIsNotPersisted(T pt) {
        final boolean isPersisted = pt.getId() != null;
        if (isPersisted) {
            showPersonPhoto(pt);
        } else {
            hidePersonPhoto();
        }
    }

    private void hidePersonPhoto() {
        image.setSource(null);
        image.setVisible(false);
    }

    private void showPersonPhoto(T pt) {
        image.setSource(new ExternalResource(IMAGE.getParentFolder() + pt.getPhotoName()));
        image.setVisible(true);
    }

    private void makeEditorVisibleAndFocusOnFirstField() {
        setVisible(true);
        // A hack to ensure the whole form is visible
        save.focus();
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
        save.addClickListener(e -> h.onChange());
        delete.addClickListener(e -> h.onChange());
        cancel.addClickListener(e -> h.onChange());
    }

    private void fillPesonFromRepositoryIfExistsOrFromParameter(T pt) {
        final boolean persisted = pt.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            person = personRepo.findOne(pt.getId());
        } else {
            person = pt;

        }
    }

    private void createAndfillFiledGroupWithPropertiesOf(T pt) {
        // Bind customer properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        fieldGroup = BeanFieldGroup.bindFieldsUnbuffered(person, this);
    }

}
