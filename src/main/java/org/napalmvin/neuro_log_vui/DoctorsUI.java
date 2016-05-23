package org.napalmvin.neuro_log_vui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ImageRenderer;
import java.util.Date;
import java.util.Locale;
import org.napalmvin.neuro_log_vui.data.RaceEnum;
import org.napalmvin.neuro_log_vui.data.GenderEnum;
import org.napalmvin.neuro_log_vui.entities.Doctor;

@SpringUI
@Theme("mytheme")
public class DoctorsUI extends UI {

    private final DoctorRepository repo;
    private final DoctorEditor editor;

    private final Window popupWindow;

    private final Grid grid;
    private final TextField filter;
    private final Button addNewBtn;

    private final ImageRenderer imgRndrr = new ImageRenderer();
    private final PathToImgConverter converter = new PathToImgConverter();

    public DoctorsUI() {
        this.repo = null;
        this.editor = null;
        this.grid = null;
        this.filter = null;
        this.addNewBtn = null;
        this.popupWindow = null;
    }

    @Autowired
    public DoctorsUI(DoctorRepository repo, DoctorEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid();
        this.filter = new TextField();
        this.addNewBtn = new Button("New doctor", FontAwesome.PLUS);
        this.popupWindow = new Window();
    }

    @Override
    protected void init(VaadinRequest request) {
        initMainUI();
        initEditorWindow();
        bindListeners();

        // Initialize listing
        listCustomers(null);
    }

    // tag::listCustomers[]
    private void listCustomers(String text) {
        if (StringUtils.isEmpty(text)) {
            grid.setContainerDataSource(
                    new BeanItemContainer(Doctor.class, repo.findAll()));
        } else {
            grid.setContainerDataSource(new BeanItemContainer(Doctor.class,
                    repo.findByLastNameStartsWithIgnoreCase(text)));
        }
    }
    // end::listCustomers[]

    private void bindListeners() {
        filter.addTextChangeListener(e -> listCustomers(e.getText()));

        grid.addSelectionListener(new SelectionEvent.SelectionListener() {
            @Override
            public void select(SelectionEvent e) {
                if (e.getSelected().isEmpty()) {
//                editor.setVisible(false);
                    removeWindow(popupWindow);
                } else {
                    editor.editDoctor((Doctor) grid.getSelectedRow());
                    addWindow(popupWindow);
                }
            }
        });

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> {
            editor.editDoctor(new Doctor("", "", new Date(),
                    GenderEnum.MALE, RaceEnum.Caucasian, null, "Good docctor,MD."));
            addWindow(popupWindow);

        });

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            removeWindow(popupWindow);
            listCustomers(filter.getValue());
        });
    }

    @SuppressWarnings("all")
    private void initMainUI() {
        grid.setWidth(100, Unit.PERCENTAGE);
        grid.setHeight(100, Unit.PERCENTAGE);
        grid.setColumns(Doctor.FieldsList.getStringArray());
        Grid.Column photo = grid.getColumn(Doctor.FieldsList.photoUrl.name());
        photo.setRenderer(imgRndrr, converter);
        
        filter.setInputPrompt("Filter by last name");

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

}
