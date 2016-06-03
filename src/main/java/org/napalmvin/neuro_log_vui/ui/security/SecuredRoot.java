/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.ui.security;

import static com.vaadin.ui.Notification.Type.ERROR_MESSAGE;

import org.springframework.security.authentication.BadCredentialsException;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import java.util.Collections;
import java.util.Enumeration;
import org.napalmvin.neuro_log_vui.Application;
import org.napalmvin.neuro_log_vui.security.events.LoginEvent;
import org.napalmvin.neuro_log_vui.security.events.LogoutEvent;
import org.napalmvin.neuro_log_vui.security.service.AuthenticationService;
import org.napalmvin.neuro_log_vui.security.util.RequestHolder;
import org.napalmvin.neuro_log_vui.security.util.RequestHolderApplicationServlet;
import org.napalmvin.neuro_log_vui.security.util.ViewChangeSecurityChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringUI
@Theme("mytheme")
@SuppressWarnings("serial")
public class SecuredRoot extends UI {
    private final EventBus bus = new EventBus();
    private Navigator navigator;
    private static final Logger log = LoggerFactory.getLogger(SecuredRoot.class);
    
    
    @Override
    protected void init(VaadinRequest wrappedRequest) {
        getPage().setTitle("Vaadin Spring Security integration example");
        Panel viewDisplay = new Panel();
        setContent(viewDisplay);

        navigator = new Navigator(this, viewDisplay);
        navigator.addViewChangeListener(new ViewChangeSecurityChecker());

        bus.register(this);
        
        navigator.navigateTo(LoginView.VIEW_NAME);

        
    }

    @Subscribe
    public void login(LoginEvent event) {
        AuthenticationService authHandler = new AuthenticationService();
        try {
            authHandler.handleAuthentication(event.getLogin(), event.getPassword(), RequestHolder.getRequest());
            navigator.navigateTo(MainView.VIEW_NAME);
        } catch (BadCredentialsException e) {
            Notification.show("Bad credentials", ERROR_MESSAGE);
        }
    }

    @Subscribe
    public void logout(LogoutEvent event) {
        AuthenticationService authHandler = new AuthenticationService();
        authHandler.handleLogout(RequestHolder.getRequest());
        navigator.navigateTo(LoginView.VIEW_NAME);
    }
}
