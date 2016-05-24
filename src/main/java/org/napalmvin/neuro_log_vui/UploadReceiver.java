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
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import static org.napalmvin.neuro_log_vui.UploadReceiver.Type.IMAGE;

/**
 *
 * @author LOL
 */
class UploadReceiver implements Receiver, SucceededListener, FailedListener {

    private File file;
    private TextField fileName;
    private static final String DEFAULT_IMG_FOLDER = "E:\\!Sources\\NeuroLogVUI\\target\\classes\\resources\\images\\";
    private static final String DEFAULT_ATTACHMENTS_FOLDER = "E:\\!Sources\\NeuroLogVUI\\target\\classes\\resources\\attachments\\";
    private final Embedded image;

    private  Type type;
   
    
    public static enum Type {
        IMAGE, ATTACHMENT;

        public String getFolder() {
            if (this.equals(IMAGE)) {
                return DEFAULT_IMG_FOLDER;
            }
            return DEFAULT_ATTACHMENTS_FOLDER;
        }
    }

    UploadReceiver(TextField fileName, Embedded image, Type type) {
        this.fileName = fileName;
        this.image = image;
        this.type=type;
    }

    public OutputStream receiveUpload(String filename,
            String mimeType) {
        // Create upload stream
        FileOutputStream fos = null; // Stream to write to
        try {
            file = new File(type.getFolder() +System.currentTimeMillis()+"-"+ filename);
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
        fileName.setValue(file.getParentFile().getName()+"//"+file.getName());
        image.setSource(new FileResource(file));
        image.setVisible(true);
    }
    
     @Override
    public void uploadFailed(Upload.FailedEvent event) {
        throw new Error("Error during file upload",event.getReason());
    }

}
