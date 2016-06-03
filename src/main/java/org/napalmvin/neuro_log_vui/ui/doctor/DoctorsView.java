package org.napalmvin.neuro_log_vui.ui.doctor;

import org.napalmvin.neuro_log_vui.data.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ImageRenderer;
import java.util.Date;
import org.napalmvin.neuro_log_vui.data.RaceEnum;
import org.napalmvin.neuro_log_vui.data.GenderEnum;
import org.napalmvin.neuro_log_vui.entities.Doctor;
import org.napalmvin.neuro_log_vui.ui.PathToImgConverter;

@SpringView(name = DoctorsView.VIEW_NAME)
public class DoctorsView extends Panel implements View {

    public static final String VIEW_NAME = "doctors";

    private final DoctorRepository repo;
    private final DoctorEditor editor;

    private final Window popupWindow;

    private final Grid grid;
    private final TextField filter;
    private final Button addNewBtn;

    private final ImageRenderer imgRndrr = new ImageRenderer();
    private final PathToImgConverter converter = new PathToImgConverter();

    public DoctorsView() {
        this.repo = null;
        this.editor = null;
        this.grid = null;
        this.filter = null;
        this.addNewBtn = null;
        this.popupWindow = null;
    }

    @Autowired
    public DoctorsView(DoctorRepository repo, DoctorEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid();
        this.filter = new TextField();
        this.addNewBtn = new Button("New doctor", FontAwesome.PLUS);
        this.popupWindow = new Window();
    }

    private void listCustomers(String text) {
        if (StringUtils.isEmpty(text)) {
            grid.setContainerDataSource(
                    new BeanItemContainer(Doctor.class, repo.findAll()));
        } else {
            grid.setContainerDataSource(new BeanItemContainer(Doctor.class,
                    repo.findByLastNameStartsWithIgnoreCase(text)));
        }
    }

    private void bindListeners() {
        filter.addTextChangeListener(e -> listCustomers(e.getText()));

        grid.addSelectionListener(new SelectionEvent.SelectionListener() {
            @Override
            public void select(SelectionEvent e) {
                if (e.getSelected().isEmpty()) {
//                editor.setVisible(false);
                    getUI().removeWindow(popupWindow);
                } else {
                    editor.editDoctor((Doctor) grid.getSelectedRow());
                    getUI().addWindow(popupWindow);
                }
            }
        });

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> {
            editor.editDoctor(new Doctor("", "", new Date(),
                    GenderEnum.MALE, RaceEnum.Caucasian, null, "Good docctor,MD."));
            getUI().addWindow(popupWindow);

        });

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            getUI().removeWindow(popupWindow);
            listCustomers(filter.getValue());
        });
    }

    @SuppressWarnings("all")
    private void initUI() {
        grid.setWidth(100, Unit.PERCENTAGE);
        grid.setHeight(100, Unit.PERCENTAGE);
        grid.setColumns(Doctor.FieldsList.getStringArray());
        Grid.Column photo = grid.getColumn(Doctor.FieldsList.photoName.name());
        photo.setRenderer(imgRndrr, converter);

        filter.setInputPrompt("Filter by last name");

        addNewBtn.setId("new_doctor");

        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        VerticalLayout mainLayout = new VerticalLayout(actions, grid);
        mainLayout.setHeight(100, Unit.PERCENTAGE);
        mainLayout.setSizeFull();
        setContent(mainLayout);

        actions.setSpacing(true);
        mainLayout.setExpandRatio(grid, 5);
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
    }

    private void initEditorWindow() {
        popupWindow.setContent(editor);
        popupWindow.setModal(true);
        popupWindow.setImmediate(true);
        popupWindow.setHeight(85, Unit.PERCENTAGE);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        initUI();
        initEditorWindow();
        bindListeners();

        // Initialize listing
        listCustomers(null);
    }

}
