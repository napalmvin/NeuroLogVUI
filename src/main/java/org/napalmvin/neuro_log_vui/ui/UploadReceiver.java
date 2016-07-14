/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.ui;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedListener;
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
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import org.napalmvin.neuro_log_vui.PathConstants.Type;
import org.napalmvin.neuro_log_vui.data.ImageRepository;
import org.napalmvin.neuro_log_vui.entities.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author LOL
 */
public class UploadReceiver implements Receiver, SucceededListener, FailedListener,Upload.StartedListener {

    private void notifyAllAfterHookListeners(SucceededEvent event) {
        for (AfterUploadSuceededListener listener : afterUploadSuceededListeners) {
            listener.afterUploadSuceeded(event,file.getName(),type);
            
        }
    }

    public void addAfterUploadSuceededListener(AfterUploadSuceededListener aThis) {
        afterUploadSuceededListeners.add(aThis);
    }

   public interface AfterUploadSuceededListener{
        public void afterUploadSuceeded(SucceededEvent event,String fileName,Type type);
    }
            
            
    private final ImageRepository imageRepository;

    private final Type type;
    
    private File  file;
    
    private final Logger log=LoggerFactory.getLogger(UploadReceiver.class.getName());
    
    private final List<AfterUploadSuceededListener> afterUploadSuceededListeners=new LinkedList<>();

    public UploadReceiver(ImageRepository imageRepository, Type type) {
        this.imageRepository = imageRepository;
        this.type = type;
    }

    @Override
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
           log.error("Upload Error happened", ex);
        }
        notifyAllAfterHookListeners(event);
    }

    @Override
    public void uploadFailed(Upload.FailedEvent event) {
        throw new Error("Error during file upload", event.getReason());
    }

    @Override
    public void uploadStarted(Upload.StartedEvent event) {
        log.debug("uploadStarted");
    }

}
