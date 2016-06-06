package org.napalmvin.neuro_log_vui.ui.doctor;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Label;

@Theme("mytheme")
@SpringView
public class MainView extends VerticalLayout implements View {
    @Autowired
    public MainView() {
        addComponent(new Label("WHoa"));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

   

}
