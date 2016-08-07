package org.napalmvin.neuro_log_vui.ui.person;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.napalmvin.neuro_log_vui.PathConstants;
import static org.napalmvin.neuro_log_vui.PathConstants.Type.IMAGE;
import org.napalmvin.neuro_log_vui.entities.enums.RaceEnum;
import org.napalmvin.neuro_log_vui.entities.enums.GenderEnum;
import org.napalmvin.neuro_log_vui.data.ImageRepository;
import org.napalmvin.neuro_log_vui.entities.Person;
import org.napalmvin.neuro_log_vui.ui.UploadReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.napalmvin.neuro_log_vui.ui.common.AbstractEntityEditor;

public abstract class PersonEditor<T extends Person> extends AbstractEntityEditor<T> {

    TextField firstName;
    TextField middleName;
    TextField lastName;
    DateField birthDate;
    ComboBox gender;
    ComboBox race;
    TextField photoName;
    Embedded image;

    UploadReceiver receiver;
    Upload upload;
    ProgressBar progressBar;

    @Autowired
    private ImageRepository imageRepo;

    final static Logger log = LoggerFactory.getLogger(PersonEditor.class);

    protected void createComponentsInChildren() {
        //TODO use constants instead
        this.progressBar = new ProgressBar(0f);
        this.upload = new Upload();
        upload.setCaption(msg.getString("upload"));
        this.image = new Embedded(msg.getString("image"));
        receiver = new UploadReceiver(imageRepo, IMAGE);

        this.photoName = new TextField(msg.getString("photoName"));
        this.gender = new ComboBox(msg.getString("gender"), Arrays.asList(GenderEnum.values()));
        this.race = new ComboBox(msg.getString("race"), Arrays.asList(RaceEnum.values()));
        this.birthDate = new DateField(msg.getString("birthDate"), new Date());
        this.lastName = new TextField(msg.getString("lastName"));
        this.firstName = new TextField(msg.getString("firstName"));
        this.middleName = new TextField(msg.getString("middleName"));

        createAndInitSubComponents();

    }

    @Override
    protected void setComponentsIDsHtmlAttribute() {
        //TODO Constants
        firstName.setId("firstName");
        lastName.setId("lastName");
        birthDate.setId("birthDate");
        gender.setId("gender");
        race.setId("race");
        photoName.setId("photoName");
        image.setId("image");
        upload.setId("upload");
        middleName.setId("middleName");
    }

    @Override
    protected Collection<? extends Component> getComponents() {
        List<Component> components = new LinkedList<>();
        components.add(firstName);
        components.add(middleName);
        components.add(lastName);
        components.add(birthDate);
        components.add(gender);
        components.add(race);
        components.addAll(attachSubComponents());
        components.add(image);
        components.add(upload);
        components.add(progressBar);
        components.add(photoName);
        return components;
    }

    @Override
    protected void initChildrenUI() {
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

        image.setWidth(200, Unit.PIXELS);
        progressBar.setVisible(false);

    }

    @Override
    protected void childrenEditHandler(T entity) {
        showImageForExistantPersonOrHideItIfPersonIsNotPersisted(entity);
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

    @Override
    public void afterUploadSuceeded(Upload.SucceededEvent event, String fileName, PathConstants.Type type) {
        photoName.setValue(fileName);

        progressBar.setVisible(false);

        image.setSource(new ExternalResource(type.getParentFolder() + fileName));
        image.setVisible(true);
    }

    public abstract List<Component> attachSubComponents();

    public abstract void createAndInitSubComponents();

}
