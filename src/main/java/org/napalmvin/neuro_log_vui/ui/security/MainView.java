/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.ui.security;

import com.google.common.eventbus.EventBus;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.napalmvin.neuro_log_vui.security.events.LogoutEvent;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;

@SpringView(name = MainView.VIEW_NAME)
@SuppressWarnings("serial")
public class MainView extends VerticalLayout implements View {
        public static final String VIEW_NAME = "main";
        
	private Label label;

        @Autowired
	public MainView(final EventBus eventBus) {
		label = new Label();
		addComponent(label);
		Button button = new Button("Logout");
		button.addClickListener((event)->eventBus.post(new LogoutEvent()));
		addComponent(button);
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		label.setValue("Welcome to " + user);
	}
}