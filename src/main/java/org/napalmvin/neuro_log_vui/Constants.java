/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui;

/**
 *
 * @author LOL
 */
public class Constants {
    private static final String DEFAULT_IMG_FOLDER = "E:\\!Sources\\NeuroLogVUI\\target\\classes\\resources\\images\\";
    private static final String DEFAULT_ATTACHMENTS_FOLDER = "E:\\!Sources\\NeuroLogVUI\\target\\classes\\resources\\attachments\\";
    private static final String DEFAULT_RESOURCES_FOLDER = "E:\\!Sources\\NeuroLogVUI\\target\\classes\\resources\\";
    private static final String IMAGE_FOLDER_NAME = "images//";
    private static final String IMAGE_RESOURCE_NAME = "resource//";
    public static enum Type {
        IMAGE, ATTACHMENT;

        public String getFullFolder() {
            if (this.equals(IMAGE)) {
                return DEFAULT_IMG_FOLDER;
            }
            return DEFAULT_ATTACHMENTS_FOLDER;
        }
        
        public String getParentFolder() {
            if (this.equals(IMAGE)) {
                return IMAGE_FOLDER_NAME;
            }
            return DEFAULT_ATTACHMENTS_FOLDER;
        }
    }
}
