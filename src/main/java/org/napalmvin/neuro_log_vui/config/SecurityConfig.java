/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.config;

import com.vaadin.annotations.VaadinServletConfiguration;
import org.napalmvin.neuro_log_vui.ui.security.SecuredRoot;
import org.napalmvin.neuro_log_vui.security.util.RequestHolderApplicationServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(SecuredRoot.class);

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        RequestHolderApplicationServlet reqHolder = new RequestHolderApplicationServlet();
        return new ServletRegistrationBean(reqHolder, "/*","/VAADIN/*");
    }
    
     @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
               // .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

   

//    @Autowired
//    private DataSource dataSource;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers("/css/**").permitAll().anyRequest()
//                .fullyAuthenticated().and().formLogin().loginPage("/login")
//                .failureUrl("/login?error").permitAll().and().logout().permitAll();
//    }
//
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().dataSource(this.dataSource);
//    }
}
