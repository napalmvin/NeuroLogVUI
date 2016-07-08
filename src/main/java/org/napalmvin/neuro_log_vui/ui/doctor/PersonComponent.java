/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.ui.doctor;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import java.util.ResourceBundle;
import org.napalmvin.neuro_log_vui.data.DoctorRepository;
import org.napalmvin.neuro_log_vui.entities.Doctor;

/**
 *
 * @author LOL
 */
public class PersonComponent extends CustomField<Doctor> {

    private final Button button = new Button(FontAwesome.SEARCH);
    private boolean isReqired = false;
    private final TextField textField = new TextField();
    private ResourceBundle msg;
    private Doctor doctor;
    private final Window popupWindow;
    private DoctorsListPanel docList;

    public PersonComponent(ResourceBundle msg, DoctorRepository repo) {
        this.msg = msg;
        docList = new DoctorsListPanel(repo, msg);

        button.setCaption(msg.getString("find"));
        this.popupWindow = new Window();
        popupWindow.setContent(docList);
        popupWindow.setModal(true);
        popupWindow.setImmediate(true);
        popupWindow.setHeight(85, Unit.PERCENTAGE);
        popupWindow.setWidth(85, Unit.PERCENTAGE);
        
        textField.setReadOnly(true);
        button.addClickListener(e -> {
            UI.getCurrent().addWindow(popupWindow);
        });
        docList.setSelectionHandler((value) -> {
            if (value == null) {
                Notification.show(msg.getString("select_row_please"), Notification.Type.WARNING_MESSAGE);
            } else {
                doctor = (Doctor) value;
                textField.setReadOnly(false);
                textField.setValue(doctor.getFirstName()+" "+doctor.getLastName());
                textField.setReadOnly(true);
                UI.getCurrent().removeWindow(popupWindow);
            }

        });
    }

    @Override
    protected Component initContent() {
        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.addComponents(textField, button);
        hLayout.setMargin(new MarginInfo(true, true, true, false)); // Very useful

        // Compose from multiple components
        // Set the size as undefined at all levels
        hLayout.setSizeUndefined();
//        setSizeUndefined();

        return hLayout;

    }

    @Override
    public Class<? extends Doctor> getType() {
        return Doctor.class;
    }

}
