package org.napalmvin.neuro_log_vui.ui.common;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.Collection;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import static org.napalmvin.neuro_log_vui.TextConstants.CANCEL;
import static org.napalmvin.neuro_log_vui.TextConstants.SAVE;
import static org.napalmvin.neuro_log_vui.TextConstants.DELETE;
import org.napalmvin.neuro_log_vui.ui.ChangeHandler;
import org.napalmvin.neuro_log_vui.ui.UploadReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.napalmvin.neuro_log_vui.data.LastNameSearchableRepository;
import org.napalmvin.neuro_log_vui.entities.interfaces.HasId;

public abstract class AbstractEntityEditor<T extends HasId> extends Panel implements UploadReceiver.AfterUploadSuceededListener {

    @Autowired
    protected ResourceBundle msg;
    @Autowired
    private LastNameSearchableRepository<T> entityRepository;

    private BeanFieldGroup fieldGroup;
    private T entityToEdit;

    Button save;
    Button cancel;
    Button delete;
    HorizontalLayout actions;
    VerticalLayout vl;

    final static Logger log = LoggerFactory.getLogger(AbstractEntityEditor.class);

    public final void edit(T entity) {
        fillEntityFromRepositoryIfExistsOrTakeMethodParameter(entity);
        childrenEditHandler(entity);
        createAndfillFiledGroupWithPropertiesOf(entity);
        makeEditorVisibleAndFocusOnFirstField();
    }

    @PostConstruct
    private void createAndBindUI() {
        createUI();
        initOwnUI();
    }

    private void createUI() {
        createOwnComponents();
        createComponentsInChildren();
        layoutComponents();
    }

    private void createOwnComponents() {
        this.delete = new Button(msg.getString(DELETE), FontAwesome.TRASH_O);
        this.cancel = new Button(msg.getString(CANCEL), FontAwesome.RECYCLE);
        this.save = new Button(msg.getString(SAVE), FontAwesome.SAVE);
        this.actions = new HorizontalLayout(save, cancel, delete);
        this.vl = new VerticalLayout();
    }

    private void layoutComponents() {
        LinkedList<Component> components = new LinkedList();
        components.addAll(getComponents());
        components.add(actions);
        vl.addComponents(components.toArray(new Component[components.size()]));
    }

   



    private void initOwnUI() {
        setOwnComponentsIDsHtmlAttribute();
        setComponentsIDsHtmlAttribute();
        vl.setSpacing(true);
        vl.setMargin(true);
        actions.setMargin(true);
        this.setContent(vl);
        initChildrenUI();
       

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
            entityRepository.save(entityToEdit);
        });
        delete.addClickListener(e -> {
            entityRepository.delete(entityToEdit);
        });
        cancel.addClickListener(e -> {
            edit(entityToEdit);
        });
        
        this.setVisible(false);
    }

    private void setOwnComponentsIDsHtmlAttribute() {
        
        save.setId(SAVE);
        cancel.setId(CANCEL);
        delete.setId(DELETE);

    }

   

    private void makeEditorVisibleAndFocusOnFirstField() {
        setVisible(true);
        // A hack to ensure the whole form is visible
        save.focus();
    }

   

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        save.addClickListener(e -> h.onChange());
        delete.addClickListener(e -> h.onChange());
        cancel.addClickListener(e -> h.onChange());
    }

    private void fillEntityFromRepositoryIfExistsOrTakeMethodParameter(T pt) {
        final boolean persisted = pt.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            entityToEdit = entityRepository.findOne(pt.getId());
        } else {
            entityToEdit = pt;

        }
    }

    private void createAndfillFiledGroupWithPropertiesOf(T pt) {
        // Bind customer properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        fieldGroup = BeanFieldGroup.bindFieldsUnbuffered(entityToEdit, this);
    }

    protected abstract void createComponentsInChildren();

    protected abstract void setComponentsIDsHtmlAttribute(); //To change body of generated methods, choose Tools | Templates.

    protected abstract  Collection<? extends Component> getComponents() ;

    protected abstract  void initChildrenUI();

    protected  abstract  void childrenEditHandler(T entity);

}
