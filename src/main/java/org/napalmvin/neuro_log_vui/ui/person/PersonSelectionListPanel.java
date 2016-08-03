package org.napalmvin.neuro_log_vui.ui.person;

import org.springframework.util.StringUtils;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ImageRenderer;
import java.util.ResourceBundle;
import static org.napalmvin.neuro_log_vui.TextConstants.FILTER_BY_LAST_NAME;
import static org.napalmvin.neuro_log_vui.TextConstants.SELECT;
import org.napalmvin.neuro_log_vui.data.PersonRepository;
import org.napalmvin.neuro_log_vui.entities.Person;
import org.napalmvin.neuro_log_vui.ui.SelectionHandler;
import org.napalmvin.neuro_log_vui.ui.StringPathToImgConverter;
import org.slf4j.LoggerFactory;

public class PersonSelectionListPanel<T extends Person> extends VerticalLayout {

    private ResourceBundle msg;

    private final PersonRepository<T> repo;

    private final Grid grid;
    private final TextField filter;
    private final Button select;

    private final ImageRenderer imgRndrr = new ImageRenderer();
    private final StringPathToImgConverter converter = new StringPathToImgConverter();
    private Class<T> type;
    
     final static org.slf4j.Logger log = LoggerFactory.getLogger(PersonSelectionListPanel.class);

    public PersonSelectionListPanel(PersonRepository<T> repo, ResourceBundle msg, Class<T> type) {
        this.setImmediate(true);
        this.type = type;
        this.msg = msg;
        this.repo = repo;
//        this.editor = editor;
        this.grid = new Grid();
        this.filter = new TextField();
        select = new Button(msg.getString(SELECT));

        initAndFillMainUI();

        // Initialize listing
    }

    // tag::listCustomers[]
    private void fillPersonGrid(String text) throws IllegalAccessException, InstantiationException {
        if (StringUtils.isEmpty(text)) {
            grid.setContainerDataSource(
                    new BeanItemContainer(type, repo.findAll()));
        } else {
            grid.setContainerDataSource(new BeanItemContainer(type,
                    repo.findByLastNameStartsWithIgnoreCase(text)));
        }

        //Set localized header(column names)
        T instance = type.newInstance();
        for (String key : instance.FIELD_LIST) {
            grid.getDefaultHeaderRow().getCell(key).setText(msg.getString(key));
        }

        grid.setColumns(instance.FIELD_LIST);
        Grid.Column photoClmn = grid.getColumn(instance.PHOTO_NAME);
        photoClmn.setWidthUndefined();
        photoClmn.setRenderer(imgRndrr, converter);
    }

    @SuppressWarnings("all")
    private void initAndFillMainUI(){
        grid.setImmediate(true);
        grid.setWidth(100, Unit.PERCENTAGE);
        grid.setHeight(100, Unit.PERCENTAGE);

        filter.setInputPrompt(msg.getString(FILTER_BY_LAST_NAME));
        filter.setIcon(FontAwesome.SEARCH_MINUS);

        HorizontalLayout top = new HorizontalLayout(filter);
        top.setSpacing(true);
        Label emptyLabel = new Label();
        emptyLabel.setWidth(90, Unit.PERCENTAGE);

        HorizontalLayout bottom = new HorizontalLayout(emptyLabel, select);

        bottom.setExpandRatio(select, 1);
        bottom.setExpandRatio(emptyLabel, 5);
        bottom.setSpacing(true);

        addComponents(top, grid, bottom);
        setHeight(100, Unit.PERCENTAGE);

        setExpandRatio(grid, 5);
        setMargin(true);
        setSpacing(true);

        filter.addTextChangeListener(e -> {
            try {
                fillPersonGrid(e.getText());
            } catch (InstantiationException | IllegalAccessException ex) {
                log.error("",ex);
            } 
              
        });

        try {
            fillPersonGrid(null);
        } catch (IllegalAccessException | InstantiationException ex) {
            log.error("",ex);
        } 
        setSizeFull();
    }

    void setSelectionHandler(SelectionHandler h
    ) {
        select.addClickListener(e -> {
            h.handleSelection(grid.getSelectedRow());
        });
    }
}
