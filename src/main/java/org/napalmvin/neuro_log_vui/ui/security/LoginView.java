/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.ui.security;

import com.google.common.eventbus.EventBus;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import org.napalmvin.neuro_log_vui.security.events.LoginEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author LOL
 */
@SpringView(name = LoginView.VIEW_NAME)
@SuppressWarnings("serial")
public class LoginView extends FormLayout implements View {

    public static final String VIEW_NAME = "login";
    private TextField loginField = new TextField("Login");
    private PasswordField passwordField = new PasswordField("Password");

    private static final Logger log = LoggerFactory.getLogger(SecuredRoot.class);

    public LoginView(final EventBus eventBus) {
        setMargin(true);
        addComponent(loginField);
        addComponent(passwordField);

        Button button = new Button("Let me in");
        button.addClickListener((clickEvent) -> {
            LoginEvent loginEvent = new LoginEvent(loginField.getValue(), passwordField.getValue());
            eventBus.post(loginEvent);
            loginField.setValue("");
            passwordField.setValue("");
        });

        addComponent(button);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        log.info(VIEW_NAME+"entered");
    }
}
