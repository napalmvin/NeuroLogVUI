package org.napalmvin.neuro_log_vui.ui.exams;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.MethodProperty;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ResourceBundle;
import org.napalmvin.neuro_log_vui.data.AerialExamRepository;
import org.napalmvin.neuro_log_vui.data.GeneralExamRepository;
import org.napalmvin.neuro_log_vui.entities.AerialExam;
import org.napalmvin.neuro_log_vui.entities.GeneralExam;
import org.napalmvin.neuro_log_vui.entities.enums.ExamTypeEnum;
import org.napalmvin.neuro_log_vui.ui.AppFieldFactory;
import org.napalmvin.neuro_log_vui.ui.ChangeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

@SpringComponent
@UIScope
public class GeneralExamEditor extends Panel {

    private final ResourceBundle msg;
    private final GeneralExamRepository repoGenEx;
    private final AerialExamRepository repoAerEx;
    private AppFieldFactory appFieldFactory;

    private GeneralExam generalExam;

    FormLayout formLayout;
    FieldGroup fieldGroup;
    Button save;
    Button cancel;
    Button delete;
    HorizontalLayout actions;
    VerticalLayout vl;

    final Logger LOG = LoggerFactory.getLogger(GeneralExamEditor.class);

    @Autowired
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public GeneralExamEditor(GeneralExamRepository repoGenEx,
            AerialExamRepository repoAerEx,
            ResourceBundle msg,
            AppFieldFactory appFieldFactory) {
        this.msg = msg;
        this.repoGenEx = repoGenEx;
        this.repoAerEx = repoAerEx;
        this.appFieldFactory = appFieldFactory;
        createUI();
        initUI();
    }

    private void createUI() {
        formLayout = new FormLayout();
        this.delete = new Button(msg.getString("delete"), FontAwesome.TRASH_O);
        this.cancel = new Button(msg.getString("cancel"), FontAwesome.RECYCLE);
        this.save = new Button(msg.getString("save"), FontAwesome.SAVE);
        this.actions = new HorizontalLayout(save, cancel, delete);

        this.vl = new VerticalLayout(formLayout, actions);

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
            try {
//                fieldGroup.commit();
                for (Field f : fieldGroup.getFields()) {
                    if (f.getValue() != null) {
                        f.commit();
                    }
                }
                //Todo refactoring redundant unchanged entities commit
                repoAerEx.save(generalExam.getAerialExams());
                repoAerEx.flush();
                generalExam.removeEmptyAerialExams();
                repoGenEx.save(generalExam);
            } catch (Exception ex) {
                LOG.error("", ex);
            }

        });
        delete.addClickListener(e -> {
            repoGenEx.delete(generalExam);
        });
        cancel.addClickListener(e -> {
            edit(generalExam);
        });

        setVisible(false);
    }

    public final void edit(GeneralExam genExam) {
        this.generalExam = getExistantOrCreateExam(genExam);

        BeanItem beanItem = getBeanItemAndAddAerialProperties(generalExam);

        formLayout.removeAllComponents();
        fieldGroup = new FieldGroup();

        fieldGroup.setFieldFactory(appFieldFactory);
        fieldGroup.setItemDataSource(beanItem);
        createFieldGroupComponentsFromBeanItemSelectivly(fieldGroup, beanItem);

        setupFields(fieldGroup);

        formLayout.addComponents(fieldGroup.getFields().toArray(new Component[0]));

        this.setVisible(true);

        // A hack to ensure the whole fieldGroup is visible
        save.focus();
    }

    private GeneralExam getExistantOrCreateExam(GeneralExam genExam) {
        final boolean persisted = genExam.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            return repoGenEx.findOne(genExam.getId());

        } else {
            return genExam;

        }
    }
    
     

    private BeanItem getBeanItemAndAddAerialProperties(GeneralExam generalExam) {
        BeanItem beanItem = new BeanItem(generalExam);
        for (ExamTypeEnum key : ExamTypeEnum.values()) {
            AerialExam aerEx = generalExam.getAerialExam(key);
            if (aerEx == null) {
                aerEx = new AerialExam();
                aerEx.setExamType(key);
                aerEx.setComments("");
                generalExam.addAerialExam(aerEx);
            }
            beanItem.addItemProperty(key.name(),
                    new MethodProperty<AerialExam>(aerEx, AerialExam.FieldsList.comments.name()));
        }
        return beanItem;
    }

    private void createFieldGroupComponentsFromBeanItemSelectivly(FieldGroup fieldGroup, BeanItem beanItem) {
        for (Object itemPropertyId : beanItem.getItemPropertyIds()) {
            try {
                if (GeneralExam.AERIAL_EXAMS.equals(itemPropertyId)
                        || GeneralExam.ID.equals(itemPropertyId)) {
                    continue;
                }
                fieldGroup.buildAndBind(msg.getString(itemPropertyId.toString()),
                        itemPropertyId);
            } catch (Exception ex) {
                LOG.error(Marker.ANY_NON_NULL_MARKER, "Error while binding :" + itemPropertyId, ex);
            }
        }
    }

    private void setupFields(FieldGroup fieldGroup) {
        fieldGroup.getField(GeneralExam.DOCTOR).setRequired(true);
        fieldGroup.getField(GeneralExam.PATIENT).setRequired(true);
        
        fieldGroup.getField(GeneralExam.TAKEN).setReadOnly(true);
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        save.addClickListener(e -> h.onChange());
        delete.addClickListener(e -> h.onChange());
        cancel.addClickListener(e -> h.onChange());
    }

   
}
