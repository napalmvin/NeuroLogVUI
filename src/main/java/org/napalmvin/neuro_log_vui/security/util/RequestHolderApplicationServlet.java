/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.security.util;

import com.vaadin.annotations.VaadinServletConfiguration;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.server.VaadinServlet;
import org.napalmvin.neuro_log_vui.ui.security.SecuredRoot;
import org.springframework.boot.context.properties.ConfigurationProperties;

@SuppressWarnings("serial")
@VaadinServletConfiguration(ui = SecuredRoot.class,productionMode = false)
@ConfigurationProperties()
public class RequestHolderApplicationServlet extends VaadinServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
		RequestHolder.setRequest(request);
		super.service(request, response);
		// We remove the request from the thread local, there's no reason to keep it once the work is done
		RequestHolder.clean();
		SecurityContextHolder.clearContext();
	}

    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized(); //To change body of generated methods, choose Tools | Templates.
    }
        
        
}
