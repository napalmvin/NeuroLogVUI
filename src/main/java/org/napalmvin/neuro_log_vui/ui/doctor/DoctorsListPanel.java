package org.napalmvin.neuro_log_vui.ui.doctor;

import org.napalmvin.neuro_log_vui.data.DoctorRepository;
import org.springframework.util.StringUtils;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ImageRenderer;
import java.util.ResourceBundle;
import org.napalmvin.neuro_log_vui.entities.Doctor;
import org.napalmvin.neuro_log_vui.ui.SelectionHandler;
import org.napalmvin.neuro_log_vui.ui.StringPathToImgConverter;

public class DoctorsListPanel extends VerticalLayout {

    private ResourceBundle msg;

    private final DoctorRepository repo;

    private final Grid grid;
    private final TextField filter;
    private final Button select;

    private final ImageRenderer imgRndrr = new ImageRenderer();
    private final StringPathToImgConverter converter = new StringPathToImgConverter();

    public DoctorsListPanel(DoctorRepository repo, ResourceBundle msg) {
        this.setImmediate(true);
        this.msg = msg;
        this.repo = repo;
//        this.editor = editor;
        this.grid = new Grid();
        this.filter = new TextField();
        select = new Button(msg.getString("select"));

        initAndFillMainUI();
      

        // Initialize listing
    }

    // tag::listCustomers[]
    private void listDoctors(String text) {
        if (StringUtils.isEmpty(text)) {
            grid.setContainerDataSource(
                    new BeanItemContainer(Doctor.class, repo.findAll()));
        } else {
            grid.setContainerDataSource(new BeanItemContainer(Doctor.class,
                    repo.findByLastNameStartsWithIgnoreCase(text)));
        }
        //Set localized header(column names)
        for (String key : Doctor.FieldsList.valuesAsStrings()) {
            grid.getDefaultHeaderRow().getCell(key).setText(msg.getString(key));
        }

        grid.setColumns(Doctor.FieldsList.valuesAsStrings());
        Grid.Column photoClmn = grid.getColumn(Doctor.FieldsList.photoName.name());
        photoClmn.setWidthUndefined();
        photoClmn.setRenderer(imgRndrr, converter);
    }

    @SuppressWarnings("all")
    private void initAndFillMainUI() {
        grid.setImmediate(true);
        grid.setWidth(100, Unit.PERCENTAGE);
        grid.setHeight(100, Unit.PERCENTAGE);

        filter.setInputPrompt(msg.getString("filter_by_last_name"));
        filter.setIcon(FontAwesome.SEARCH_MINUS);

        HorizontalLayout top = new HorizontalLayout(filter);
        top.setSpacing(true);
        Label emptyLabel=new Label();
        emptyLabel.setWidth(90, Unit.PERCENTAGE);
        
        HorizontalLayout bottom = new HorizontalLayout(emptyLabel,select);
        
        bottom.setExpandRatio(select, 1);
        bottom.setExpandRatio(emptyLabel, 5);
        bottom.setSpacing(true);

        addComponents(top, grid,bottom);
        setHeight(100, Unit.PERCENTAGE);

        setExpandRatio(grid, 5);
        setMargin(true);
        setSpacing(true);
        
        filter.addTextChangeListener(e -> listDoctors(e.getText()));
        listDoctors(null);
        setSizeFull();
    }

    void setSelectionHandler(SelectionHandler h) {
        select.addClickListener(e ->{
            h.handleSelection(grid.getSelectedRow());
                });
    }
}
