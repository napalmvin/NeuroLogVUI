/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.error;

import com.vaadin.server.AbstractErrorMessage;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorEvent;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.ErrorMessage.ErrorLevel;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author LOL
 */
public class CustomErrorHandler implements com.vaadin.server.ErrorHandler {

    
    private ResourceBundle msg;

    public CustomErrorHandler(ResourceBundle msg) {
        this.msg = msg;
    }
    
    
    
    private static final Logger log = LoggerFactory.getLogger(CustomErrorHandler.class);

    @Override
    public void error(ErrorEvent event) {
        // Finds the original source of the error/exception
        log.error("Some error", event.getThrowable());
        AbstractComponent component = DefaultErrorHandler.findAbstractComponent(event);
        if (component != null) {
            ErrorMessage errorMessage = getErrorMessageForException(event.getThrowable());
            if (errorMessage != null) {
                component.setComponentError(errorMessage);
                new Notification(null, errorMessage.getFormattedHtmlMessage(), Type.WARNING_MESSAGE, true).show(Page.getCurrent());
                return;
            }
        }
        DefaultErrorHandler.doDefault(event);
    }

    private ErrorMessage getErrorMessageForException(Throwable thr) {
        Throwable cause = null;
        Throwable t = thr;
        if (thr == null) {
            return null;
        }
        for (; t != null;
                t = t.getCause()) {
            if (t.getCause() == null) // We're at final cause
            {
                cause = t;
            }
        }
        if (cause != null && (cause instanceof ConstraintViolationException)) {
            StringBuilder msgg = new StringBuilder();
            
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) cause).getConstraintViolations();
            
            for (Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator(); iterator.hasNext();) {
                ConstraintViolationImpl c = (ConstraintViolationImpl) iterator.next();
                String template=c.getMessageTemplate();
                msgg.append(msg.getString(c.getPropertyPath().toString()))
                        .append(":").append(msg.getString(template.substring(1, template.length()-1))).append("<br>");

            }
            return new UserError(msgg.toString(),AbstractErrorMessage.ContentMode.HTML,ErrorLevel.ERROR);
        } else {
            return AbstractErrorMessage.getErrorMessageForException(t);
        }
    }
}
