package org.napalmvin.neuro_log_vui.ui.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ImageRenderer;
import java.util.ResourceBundle;
import org.napalmvin.neuro_log_vui.data.PatientRepository;
import org.napalmvin.neuro_log_vui.entities.Patient;
import org.napalmvin.neuro_log_vui.ui.StringPathToImgConverter;

@Theme("mytheme")
@SpringView(name = "patients")
public class PatientsView extends VerticalLayout implements View {

    private ResourceBundle msg;

    private final PatientRepository repo;
    
    private  PatientEditor editor;

    private final Window popupWindow;

    private final Grid grid;
    private final TextField filter;
    private final Button addNewBtn;

    private final ImageRenderer imgRndrr = new ImageRenderer();
    private final StringPathToImgConverter converter = new StringPathToImgConverter();

    @Autowired
    public PatientsView(PatientRepository repo,ResourceBundle msg,PatientEditor editor) {
        this.setImmediate(true);
        this.msg=msg;
        this.editor=editor;
        this.repo = repo;
//        this.editor = editor;
        this.grid = new Grid();
        this.filter = new TextField();
        this.addNewBtn = new Button(msg.getString("new_patient"), FontAwesome.PLUS);

        addNewBtn.setId("new_patient");
        this.popupWindow = new Window();

        initMainUI();
        initEditorWindow();
        bindListeners();

        // Initialize listing
    }

    // tag::listCustomers[]
    private void listCustomers(String text) {
        if (StringUtils.isEmpty(text)) {
            grid.setContainerDataSource(
                    new BeanItemContainer(Patient.class, repo.findAll()));
        } else {
            grid.setContainerDataSource(new BeanItemContainer(Patient.class,
                    repo.findByLastNameStartsWithIgnoreCase(text)));
        }
        //Set localized header(column names)
        for (String key : Patient.FieldsList.valuesAsStrings()) {
            grid.getDefaultHeaderRow().getCell(key).setText(msg.getString(key));
        }
        grid.setColumns(Patient.FieldsList.valuesAsStrings());
        Grid.Column photo = grid.getColumn(Patient.FieldsList.photoName.name());
        photo.setRenderer(imgRndrr, converter);
    }
    // end::listCustomers[]

    private void bindListeners() {
        filter.addTextChangeListener(e -> listCustomers(e.getText()));

        grid.addSelectionListener(new SelectionEvent.SelectionListener() {
            @Override
            public void select(SelectionEvent e) {
                if (e.getSelected().isEmpty()) {
//                editor.setVisible(false);
                    UI.getCurrent().removeWindow(popupWindow);
                } else {
                    editor.edit((Patient) grid.getSelectedRow());
                    UI.getCurrent().addWindow(popupWindow);
                }
            }
        });

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> {
            Patient dr=new Patient();
            editor.edit(dr);
            UI.getCurrent().addWindow(popupWindow);

        });

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            UI.getCurrent().removeWindow(popupWindow);
            listCustomers(filter.getValue());
        });
    }

    @SuppressWarnings("all")
    private void initMainUI() {
        grid.setImmediate(true);
        grid.setWidth(100, Unit.PERCENTAGE);
        grid.setHeight(100, Unit.PERCENTAGE);
        

        filter.setInputPrompt(msg.getString("filter_by_last_name"));

        addNewBtn.setId("new_doctor");

        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        actions.setSpacing(true);

        addComponents(actions, grid);
        setHeight(100, Unit.PERCENTAGE);
        setSizeFull();

        setExpandRatio(grid, 5);
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
        listCustomers(null);
    }

}
