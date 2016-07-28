package org.napalmvin.neuro_log_vui.ui.exams;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.event.ItemClickEvent;
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
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import org.napalmvin.neuro_log_vui.data.GeneralExamRepository;
import org.napalmvin.neuro_log_vui.entities.AerialExam;
import org.napalmvin.neuro_log_vui.entities.GeneralExam;
import org.napalmvin.neuro_log_vui.entities.enums.ExamTypeEnum;
import org.napalmvin.neuro_log_vui.ui.StringPathToImgConverter;
import org.springframework.util.StringUtils;

@Theme("mytheme")
@SpringView(name = "generalExams")
public class GeneralExamsView extends VerticalLayout implements View {

    private ResourceBundle msg;
    private final GeneralExamRepository repo;

    private GeneralExamEditor editor;

    private final Window popupWindow;

    private final Table table;
    private final TextField filter;
    private final Button addNewBtn;

    private final StringPathToImgConverter converter = new StringPathToImgConverter();

    private final static String[] NESTED_PROPS = {"doctor.firstName", "doctor.lastName", "patient.firstName", "patient.lastName", "patient.photoName"};
    private final static String IMAGE_COLUMN = "image";
    private final static String NEW_GENRAL_EXAM = "new_general_exam";

    @Autowired
    public GeneralExamsView(GeneralExamRepository repo, ResourceBundle msg, GeneralExamEditor editor) {
        this.setImmediate(true);
        this.msg = msg;
        this.editor = editor;
        this.repo = repo;
//        this.editor = editor;
        this.table = new Table();
        this.filter = new TextField();
        this.addNewBtn = new Button(msg.getString(NEW_GENRAL_EXAM), FontAwesome.PLUS);
        this.addNewBtn.setId(NEW_GENRAL_EXAM);
        this.popupWindow = new Window();

        initMainUI();
        initEditorWindow();
        addListeners();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        listEntities(null);
    }

    private void initMainUI() {
        table.setImmediate(true);
        table.setWidth(100, Unit.PERCENTAGE);
        table.setHeight(100, Unit.PERCENTAGE);
//       

        filter.setInputPrompt(msg.getString("filter_by_last_name"));

        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        actions.setSpacing(true);

        addComponents(actions, table);
        setHeight(100, Unit.PERCENTAGE);
        setSizeFull();

        setExpandRatio(table, 5);
        setMargin(true);
        setSpacing(true);
    }

    private void addListeners() {
        filter.addTextChangeListener(e -> listEntities(e.getText()));

        table.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent e) {
                if (e.getItem() == null) {
                    editor.setVisible(false);
                    UI.getCurrent().removeWindow(popupWindow);
                } else if (!popupWindow.isAttached()) {
                    editor.edit((GeneralExam) e.getItemId());
                    UI.getCurrent().addWindow(popupWindow);
                }
            }
        });

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> {
            GeneralExam genEx = new GeneralExam();
            genEx.setTaken(GregorianCalendar.getInstance().getTime());
            editor.edit(genEx);
            UI.getCurrent().addWindow(popupWindow);

        });

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            UI.getCurrent().removeWindow(popupWindow);
            listEntities(filter.getValue());
        });
    }

    private void initEditorWindow() {
        popupWindow.setContent(editor);
        popupWindow.setModal(true);
        popupWindow.setImmediate(true);
        popupWindow.setHeight(85, Unit.PERCENTAGE);
    }

    // tag::listCustomers[]
    private void listEntities(String text) {
        BeanItemContainer biContainer = null;
        if (StringUtils.isEmpty(text)) {
            biContainer = new BeanItemContainer(GeneralExam.class, repo.findAll());
        } else {
            //TODO Implement 
            biContainer = new BeanItemContainer(GeneralExam.class, repo.findByPatientLastNameStartsWithIgnoreCase(text));
        }

        for (String property : NESTED_PROPS) {
            biContainer.addNestedContainerProperty(property);
        }
        GeneratedPropertyContainer gCont = new GeneratedPropertyContainer(biContainer);

        table.setContainerDataSource(gCont);

        //To avoid collections and service classes being depicted
        //Check wheathe generated columns are present
        if (table.getColumnHeader(IMAGE_COLUMN).equals(IMAGE_COLUMN)) {
            addGeneratedColumns();
            //Set localized header(column names)
            for (String key : table.getColumnHeaders()) {
                table.setColumnHeader(key, msg.getString(key));
            }
        }
        setVisibleColums();

    }

    private void setVisibleColums() {
        LinkedList<Object> filteredItemIds = new LinkedList<>();
        filteredItemIds.add(GeneralExam.ID);
        filteredItemIds.add(GeneralExam.TAKEN);

        //Avoidin  photo name got into table
        for (int i = 0; i < NESTED_PROPS.length - 1; i++) {
            filteredItemIds.add(NESTED_PROPS[i]);
            
        }

        filteredItemIds.add(IMAGE_COLUMN);
        for (ExamTypeEnum enumType : ExamTypeEnum.values()) {
            filteredItemIds.add(enumType.name());
        }
        table.setVisibleColumns(filteredItemIds.toArray());
    }

    private void addGeneratedColumns() {
        table.addGeneratedColumn(IMAGE_COLUMN, (Table source, Object itemId, Object columnId) -> {
            String imagePath = (String) source.getItem(itemId)
                    .getItemProperty(NESTED_PROPS[4]).getValue();
            return new Embedded("", converter.convertToPresentation(imagePath, Resource.class, msg.getLocale()));
        });
        table.setColumnWidth(IMAGE_COLUMN, 210);

        for (ExamTypeEnum enumType : ExamTypeEnum.values()) {
            String key = enumType.name();

            table.addGeneratedColumn(key, (Table source, Object itemId, Object columnId) -> {
                Property prop = source.getItem(itemId).getItemProperty(GeneralExam.AERIAL_EXAMS);
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
