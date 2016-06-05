/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui;

import com.vaadin.server.ExternalResource;
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
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.napalmvin.neuro_log_vui.Constants.Type;
import org.napalmvin.neuro_log_vui.data.ImageRepository;
import org.napalmvin.neuro_log_vui.entities.Image;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author LOL
 */
class UploadReceiver implements Receiver, SucceededListener, FailedListener {

    private File file;
    private TextField fileName;

    private final Embedded image;
    private ImageRepository imageRepository;

    private Type type;

    public UploadReceiver(TextField fileName, Embedded image, ImageRepository imageRepository, Type type) {
        this.fileName = fileName;
        this.image = image;
        this.imageRepository = imageRepository;
        this.type = type;
    }

    public OutputStream receiveUpload(String filename,
            String mimeType) {
        // Create upload stream
        FileOutputStream fos = null; // Stream to write to
        try {
            file = new File(type.getFullFolder() + System.currentTimeMillis() + "-" + filename);
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
        try {
            Path path = Paths.get(file.getPath());
            byte[] data = Files.readAllBytes(path);
            Image img = new Image(file.getName(), data);
            imageRepository.save(img);
        } catch (IOException ex) {
            Logger.getLogger(UploadReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }

        fileName.setValue(file.getName());
        image.setSource(new ExternalResource(type.getParentFolder()+file));
        image.setVisible(true);
    }

    @Override
    public void uploadFailed(Upload.FailedEvent event) {
        throw new Error("Error during file upload", event.getReason());
    }

}
