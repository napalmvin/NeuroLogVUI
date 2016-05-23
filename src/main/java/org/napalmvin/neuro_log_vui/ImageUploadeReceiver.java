/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui;

import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 *
 * @author LOL
 */
class ImageUploadeReceiver implements Receiver, SucceededListener {

    private File file;
    private TextField fileName;
    public static final String DEFAULT_IMG_FOLDER = "E:\\!Sources\\NeuroLogVUI\\target\\classes\\resources\\";
    private final Embedded image;

    ImageUploadeReceiver(TextField fileName, Embedded image) {
        this.fileName=fileName;
        this.image=image;
    }
   

    public OutputStream receiveUpload(String filename,
            String mimeType) {
        // Create upload stream
        FileOutputStream fos = null; // Stream to write to
        try {
            // Open the file for writing.
            file = new File(DEFAULT_IMG_FOLDER + filename);
            fos = new FileOutputStream(file);
        } catch (final java.io.FileNotFoundException e) {
            new Notification("Could not open file<br/>",
                    e.getMessage(),
                    Notification.Type.ERROR_MESSAGE)
                    .show(Page.getCurrent());
            return null;
        }
        return fos; // Return the output stream to write to
    }

    @Override
    public void uploadSucceeded(SucceededEvent event) {
        fileName.setValue(file.getName().toString());
        image.setSource(new FileResource(file));
        image.setVisible(true);
    }

}
