/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.navigator.ViewChangeListener;
import org.napalmvin.neuro_log_vui.ui.security.LoginView;

@SuppressWarnings("serial")
public class ViewChangeSecurityChecker implements ViewChangeListener {
    

	@Override
	public boolean beforeViewChange(ViewChangeEvent event) {
		if (event.getNewView() instanceof LoginView) {
			return true;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication == null ? false : authentication.isAuthenticated();
	}

	@Override
	public void afterViewChange(ViewChangeEvent event) {
	}
}