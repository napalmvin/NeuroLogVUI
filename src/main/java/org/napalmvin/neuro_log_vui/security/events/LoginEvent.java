/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.security.events;
//web.​bindery.​event.​shared.Event;

/**
 *
 * @author LOL
 */
public class LoginEvent {

        private final String login;

        private final String password;

        public LoginEvent(String login, String password) {

            this.login = login;
            this.password = password;
        }

        public String getLogin() {

            return login;
        }

        public String getPassword() {

            return password;
        }
    }
