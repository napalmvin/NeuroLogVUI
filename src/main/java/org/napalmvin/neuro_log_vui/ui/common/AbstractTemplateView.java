package org.napalmvin.neuro_log_vui.ui.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ImageRenderer;
import java.util.ResourceBundle;
import org.napalmvin.neuro_log_vui.data.LastNameSearchableRepository;
import org.napalmvin.neuro_log_vui.entities.interfaces.FieldListEntity;
import org.napalmvin.neuro_log_vui.entities.Person;
import org.napalmvin.neuro_log_vui.ui.StringPathToImgConverter;
import org.springframework.beans.factory.annotation.Required;

public abstract class AbstractTemplateView<T extends FieldListEntity> extends VerticalLayout implements View {

    @Autowired
    private ResourceBundle msg;

    private LastNameSearchableRepository<T> repo;

    
    private AbstractEntityEditor<T> editor;

    private Window popupWindow;

    private Grid grid;
    private TextField filter;
    private Button addNewBtn;

    private ImageRenderer imgRndrr = new ImageRenderer();
    private StringPathToImgConverter converter = new StringPathToImgConverter();
    private boolean notInitalized = true;

    //Wee need it to get access to instance methods and poymorphism
    private T instance;

    public AbstractTemplateView() {
        this.setImmediate(true);

    }

    public abstract T getNewEmptyInstance();

    @Autowired
    private void setRepository(LastNameSearchableRepository<T> repo) {
        this.repo = repo;
    }

    @Autowired
    private void setEditor(AbstractEntityEditor<T> editor) {
        this.editor = editor;
    }

    // tag::listCustomers[]
    private void listEntities(String text) {
        BeanItemContainer beanItemContainer;
        if (StringUtils.isEmpty(text)) {

            beanItemContainer = new BeanItemContainer(instance.getClass(), repo.findAll());
        } else {
            beanItemContainer = new BeanItemContainer(instance.getClass(),
                    repo.findByLastNameStartsWithIgnoreCase(text));
        }
        grid.setContainerDataSource(beanItemContainer);
        
        //Set localized header(column names)
        for (String key : instance.getFieldList()) {
            grid.getDefaultHeaderRow().getCell(key).setText(msg.getString(key));
        }
        grid.setColumns(instance.getFieldList());

        if (instance instanceof Person) {
            Grid.Column photo = grid.getColumn(Person.PHOTO_NAME);
            photo.setRenderer(imgRndrr, converter);
        }
    }

    private void createAndAddListeners() {
        filter.addTextChangeListener(e -> listEntities(e.getText()));

        grid.addSelectionListener(new SelectionEvent.SelectionListener() {
            @Override
            public void select(SelectionEvent e) {
                if (e.getSelected().isEmpty()) {
                    UI.getCurrent().removeWindow(popupWindow);
                } else {
                    editor.edit((T) grid.getSelectedRow());
                    UI.getCurrent().addWindow(popupWindow);
                }
            }
        });

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> {
            T entity = getNewEmptyInstance();
            editor.edit(entity);
            UI.getCurrent().addWindow(popupWindow);

        });

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            UI.getCurrent().removeWindow(popupWindow);
            listEntities(filter.getValue());
        });
    }

    private void createComponents() {
        this.grid = new Grid();
        this.filter = new TextField();
        this.addNewBtn = new Button(msg.getString(getAddNewButtonCaption()), FontAwesome.PLUS);

        this.popupWindow = new Window();
    }

    @SuppressWarnings("all")
    private void initMainUI() {
        instance = getNewEmptyInstance();

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
        if (notInitalized) {
            createComponents();
            initMainUI();
            initEditorWindow();
            createAndAddListeners();
            notInitalized = false;
        }
        listEntities(null);
    }

    protected abstract  String getAddNewButtonCaption();

}
