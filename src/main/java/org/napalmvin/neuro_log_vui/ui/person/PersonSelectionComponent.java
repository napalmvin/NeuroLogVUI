/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.ui.person;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import java.util.ResourceBundle;
import org.napalmvin.neuro_log_vui.data.PersonRepository;
import org.napalmvin.neuro_log_vui.entities.Person;
import org.napalmvin.neuro_log_vui.ui.PersonToStringConverter;

/**
 *
 * @author LOL
 * @param <T>
 */
public  class PersonSelectionComponent<T extends Person> extends CustomField<T> {

    private final Button button = new Button(FontAwesome.SEARCH);
    private boolean isReqired;
    private final TextField textField;
    private final ResourceBundle msg;
    private T person;
    private final Window popupWindow;
    private PersonsListPanel<T> personsList;
    private Class<T>type;
    
    private PersonToStringConverter<T> conv;

    public PersonSelectionComponent(ResourceBundle msg, PersonRepository<T> repo,Class<T>type) {
        this.textField = new TextField();
        this.isReqired = false;
        this.msg = msg;
        this.type=type;
        personsList = new PersonsListPanel<T>(repo, msg,type);

        button.setCaption(msg.getString("find"));
        this.popupWindow = new Window();
        popupWindow.setContent(personsList);
        popupWindow.setModal(true);
        popupWindow.setImmediate(true);
        popupWindow.setHeight(85, Unit.PERCENTAGE);
        popupWindow.setWidth(85, Unit.PERCENTAGE);
        
        textField.setReadOnly(true);
        button.addClickListener(e -> {
            UI.getCurrent().addWindow(popupWindow);
        });
        personsList.setSelectionHandler((value) -> {
            if (value == null) {
                Notification.show(msg.getString("select_row_please"), Notification.Type.WARNING_MESSAGE);
            } else {
                person = (T) value;
                this.setInternalValue(person);
                textField.setReadOnly(false);
                textField.setValue(person.getFirstName()+" "+person.getLastName());
                textField.setReadOnly(true);
                UI.getCurrent().removeWindow(popupWindow);
            }

        });
        conv=new PersonToStringConverter<>(type);
    }

    @Override
    protected Component initContent() {
        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.addComponents(textField, button);
        hLayout.setMargin(new MarginInfo(false, true, false, false)); // Very useful

        // Compose from multiple components
        // Set the size as undefined at all levels
        hLayout.setSizeUndefined();
//        setSizeUndefined();

        return hLayout;

    }

    @Override
    public Class<? extends T> getType() {
        return type;
    }

    @Override
    protected void setInternalValue(T newValue) {
        String presentation = conv.convertToPresentation(newValue,conv.getPresentationType() , null);
        textField.setReadOnly(false);
        textField.setValue(presentation);
        textField.setReadOnly(true);
        super.setInternalValue(newValue); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    

    
    
    

    

}
