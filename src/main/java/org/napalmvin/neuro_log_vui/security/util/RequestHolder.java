/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.security.util;

import javax.servlet.http.HttpServletRequest;

public class RequestHolder {

    private static final ThreadLocal<HttpServletRequest> THREAD_LOCAL = new ThreadLocal<HttpServletRequest>();

    public static HttpServletRequest getRequest() {

        return THREAD_LOCAL.get();
    }

    static void setRequest(HttpServletRequest request) {

        THREAD_LOCAL.set(request);
    }

    static void clean() {

        THREAD_LOCAL.remove();
    }
}
