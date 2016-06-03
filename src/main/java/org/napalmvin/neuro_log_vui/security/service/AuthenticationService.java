/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.security.service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author LOL
 */
public class AuthenticationService {

	public void handleAuthentication(String login, String password, HttpServletRequest httpRequest) {
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login, password);
		token.setDetails(new WebAuthenticationDetails(httpRequest));
		ServletContext servletContext = httpRequest.getSession().getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		AuthenticationManager authManager = wac.getBean(AuthenticationManager.class);
		Authentication authentication = authManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	public void handleLogout(HttpServletRequest httpRequest) {
		ServletContext servletContext = httpRequest.getSession().getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		LogoutHandler logoutHandler = wac.getBean(LogoutHandler.class);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// Response should not be used?
		logoutHandler.logout(httpRequest, null, authentication);
	}
}
