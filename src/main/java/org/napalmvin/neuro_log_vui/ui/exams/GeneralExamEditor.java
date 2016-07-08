package org.napalmvin.neuro_log_vui.ui.exams;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.MethodProperty;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.List;
import java.util.ResourceBundle;
import org.napalmvin.neuro_log_vui.data.GeneralExamRepository;
import org.napalmvin.neuro_log_vui.entities.AerialExam;
import org.napalmvin.neuro_log_vui.entities.GeneralExam;
import org.napalmvin.neuro_log_vui.entities.enums.ExamTypeEnum;
import org.napalmvin.neuro_log_vui.ui.AppFieldFactory;
import org.napalmvin.neuro_log_vui.ui.ChangeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple example to introduce building forms. As your real application is
 * probably much more complicated than this example, you could re-use this
 * fieldGroup in multiple places. This example component is only used in
 * VaadinUI.
 * <p>
 * In a real world application you'll most likely using a common super class for
 * all your forms - less code, better UX. See e.g. AbstractForm in Virin
 * (https://vaadin.com/addon/viritin).
 */
@SpringComponent
@UIScope
public class GeneralExamEditor extends Panel {

    private final ResourceBundle msg;
    private final GeneralExamRepository repo;
    private AppFieldFactory appFieldFactory;

    private GeneralExam genEx;

    FormLayout form;
    FieldGroup fieldGroup;
    Button save;
    Button cancel;
    Button delete;
    HorizontalLayout actions;
    VerticalLayout vl;

    final Logger LOG = LoggerFactory.getLogger(GeneralExamEditor.class);

    @Autowired
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public GeneralExamEditor(GeneralExamRepository doctorRepo, 
            ResourceBundle msg,
            AppFieldFactory appFieldFactory) {
        this.msg = msg;
        this.repo = doctorRepo;
        this.appFieldFactory=appFieldFactory;
        createUI();
        initUI();
    }

    private void createUI() {
        form = new FormLayout();
        this.delete = new Button(msg.getString("delete"), FontAwesome.TRASH_O);
        this.cancel = new Button(msg.getString("cancel"), FontAwesome.RECYCLE);
        this.save = new Button(msg.getString("save"), FontAwesome.SAVE);
        this.actions = new HorizontalLayout(save, cancel, delete);

        this.vl = new VerticalLayout(form, actions);

    }

    private void initUI() {

        save.setId("save");
        cancel.setId("cancel");
        delete.setId("delete");

        vl.setSpacing(true);
        vl.setMargin(true);
        actions.setMargin(true);
        setContent(vl);

        // Configure and style components
        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> {
            repo.save(genEx);
        });
        delete.addClickListener(e -> {
            repo.delete(genEx);
        });
        cancel.addClickListener(e -> {
            edit(genEx);
        });

        setVisible(false);
    }

    public final void edit(GeneralExam genEx) {
        final boolean persisted = genEx.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            this.genEx = repo.findOne(genEx.getId());

        } else {
            this.genEx = genEx;

        }

        // Bind customer properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
//        bindFieldsUnbuffered = BeanFieldGroup.bindFieldsUnbuffered(genEx, this);
        BeanItem beanItem = new BeanItem(this.genEx);
        List<AerialExam> aerialExams = genEx.getAerialExams();
        for (ExamTypeEnum key : ExamTypeEnum.values()) {
            AerialExam aerEx = genEx.getAerialExam(key);
            if (aerEx == null) {
                aerEx = new AerialExam();
                aerEx.setExamType(key);
                aerEx.setComments("");
                genEx.getAerialExams().add(aerEx);
            }
            beanItem.addItemProperty(key.name(),
                    new MethodProperty<AerialExam>(aerEx,AerialExam.FieldsList.comments.name()));
        }
        fieldGroup=new FieldGroup();
        fieldGroup.setFieldFactory(appFieldFactory);
        beanItem.removeItemProperty(GeneralExam.FieldsList.aerialExams.name());
         beanItem.removeItemProperty(GeneralExam.FieldsList.id.name());
        fieldGroup.setItemDataSource(beanItem);
        for (Object itemPropertyId : beanItem.getItemPropertyIds()) {
            Field f = fieldGroup.buildAndBind(msg.getString(itemPropertyId.toString()),
                    itemPropertyId);
            if(itemPropertyId.equals(GeneralExam.FieldsList.doctor.name()) || 
                    itemPropertyId.equals(GeneralExam.FieldsList.patient.name())){
                f.setRequired(true);
            }

            form.addComponent(f);
        }

//        setSizeFull();
        this.setVisible(true);

        // A hack to ensure the whole fieldGroup is visible
        save.focus();
        // Select all text in firstName field automatically
    }

    
    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        save.addClickListener(e -> h.onChange());
        delete.addClickListener(e -> h.onChange());
        cancel.addClickListener(e -> h.onChange());
    }

}
