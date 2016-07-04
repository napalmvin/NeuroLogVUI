package org.napalmvin.neuro_log_vui.ui.exams;

import org.napalmvin.neuro_log_vui.ui.doctor.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Table;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ImageRenderer;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import org.napalmvin.neuro_log_vui.data.GeneralExamRepository;
import org.napalmvin.neuro_log_vui.entities.AerialExam;
import org.napalmvin.neuro_log_vui.entities.Doctor;
import org.napalmvin.neuro_log_vui.entities.GeneralExam;
import org.napalmvin.neuro_log_vui.entities.enums.ExamTypeEnum;
import org.napalmvin.neuro_log_vui.ui.StringPathToImgConverter;

@Theme("mytheme")
@SpringView(name = "generalExams")
public class GeneralExamsView extends VerticalLayout implements View {

    private ResourceBundle msg;
    private final GeneralExamRepository repo;

    private DoctorEditor editor;

    private final Window popupWindow;

    private final Table table;
    private final TextField filter;
    private final Button addNewBtn;

    private final ImageRenderer imgRndrr = new ImageRenderer();
    private final StringPathToImgConverter converter = new StringPathToImgConverter();

    private final String[] NESTED_PROPS = {"doctor.firstName", "doctor.lastName", "patient.firstName", "patient.lastName", "patient.photoName"};

    @Autowired
    public GeneralExamsView(GeneralExamRepository repo, ResourceBundle msg, DoctorEditor editor) {
        this.setImmediate(true);
        this.msg = msg;
        this.editor = editor;
        this.repo = repo;
//        this.editor = editor;
        this.table = new Table();
        this.filter = new TextField();
        this.addNewBtn = new Button(msg.getString("new_general_exam"), FontAwesome.PLUS);

        addNewBtn.setId("new_general_exam");
        this.popupWindow = new Window();

        initMainUI();
        initEditorWindow();
        bindListeners();

        // Initialize listing
    }

    // tag::listCustomers[]
    private void listEntities(String text) {
        BeanItemContainer biContainer = new BeanItemContainer(GeneralExam.class, repo.findAll());
        for (String property : NESTED_PROPS) {
            biContainer.addNestedContainerProperty(property);
        }

        biContainer.removeContainerProperty("doctor");
        biContainer.removeContainerProperty("patient");

        GeneratedPropertyContainer gCont = new GeneratedPropertyContainer(biContainer);

        table.setContainerDataSource(gCont);
        Object[] itemIds = biContainer.getItem(biContainer.firstItemId()).getItemPropertyIds().toArray();
//        table.setConverter("patient.photoName", converter);
        table.setVisibleColumns(Arrays.copyOfRange(itemIds, 1, itemIds.length - 1));

        addGeneratedColumns();

        //Set localized header(column names)
        for (String key : table.getColumnHeaders()) {
            table.setColumnHeader(key, msg.getString(key));
        }

    }
    // end::listCustomers[]

    private void bindListeners() {
        filter.addTextChangeListener(e -> listEntities(e.getText()));

//        table.addSelectionListener(new SelectionEvent.SelectionListener() {
//            @Override
//            public void select(SelectionEvent e) {
//                if (e.getSelected().isEmpty()) {
////                editor.setVisible(false);
//                    UI.getCurrent().removeWindow(popupWindow);
//                } else {
//                    editor.editDoctor((Doctor) table.getSelectedRow());
//                    UI.getCurrent().addWindow(popupWindow);
//                }
//            }
//        });
        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> {
            Doctor dr = new Doctor();
            editor.editDoctor(dr);
            UI.getCurrent().addWindow(popupWindow);

        });

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            UI.getCurrent().removeWindow(popupWindow);
            listEntities(filter.getValue());
        });
    }

    @SuppressWarnings("all")
    private void initMainUI() {
        table.setImmediate(true);
        table.setWidth(100, Unit.PERCENTAGE);
        table.setHeight(100, Unit.PERCENTAGE);
//       

        filter.setInputPrompt(msg.getString("filter_by_last_name"));

        addNewBtn.setId("new_doctor");

        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        actions.setSpacing(true);

        addComponents(actions, table);
        setHeight(100, Unit.PERCENTAGE);
        setSizeFull();

        setExpandRatio(table, 5);
        setMargin(true);
        setSpacing(true);
    }

    private void initEditorWindow() {
        popupWindow.setContent(editor);
        popupWindow.setModal(true);
        popupWindow.setImmediate(true);
        popupWindow.setHeight(85, Unit.PERCENTAGE);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        listEntities(null);
    }

    private void addGeneratedColumns() {
        table.addGeneratedColumn("image", (Table source, Object itemId, Object columnId) -> {
            String imagePath = (String) source.getItem(itemId)
                    .getItemProperty(NESTED_PROPS[4]).getValue();
            return new Embedded("", converter.convertToPresentation(imagePath, Resource.class, msg.getLocale()));
        });

        for (ExamTypeEnum enumType : ExamTypeEnum.values()) {
            String key = enumType.toString();
            table.addGeneratedColumn(key, (Table source, Object itemId, Object columnId) -> {
                Property prop = source.getItem(itemId).getItemProperty(GeneralExam.FieldsList.aerialExams.toString()
                );
                List<AerialExam> aerExams = (List<AerialExam>) prop.getValue();

                for (AerialExam aerExam : aerExams) {
                    if (aerExam.getExamType().equals(enumType)) {
                        String value = aerExam.getComments();
                        if (value != null) {
                            return value;
                        }
                    }
                }
                return "";

            });
        }

    }
}
