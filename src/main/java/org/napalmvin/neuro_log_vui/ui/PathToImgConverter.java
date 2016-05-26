/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.ui;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.napalmvin.neuro_log_vui.Constants.Type;
import static org.napalmvin.neuro_log_vui.Constants.Type.IMAGE;
import org.napalmvin.neuro_log_vui.data.ImageRepository;
import org.napalmvin.neuro_log_vui.entities.Image;
import org.springframework.beans.factory.annotation.Autowired;

public class PathToImgConverter implements Converter<Resource, String> {
    
    @Autowired
    ImageRepository imgRepository;

    @Override
    public String convertToModel(Resource value,
            Class<? extends String> targetType, Locale loc)
            throws Converter.ConversionException {
        return "not needed";
    }

    @Override
    public Resource convertToPresentation(String value,
            Class<? extends Resource> targetType, Locale loc)
            throws Converter.ConversionException {
        Path imgPath = Paths.get(Type.IMAGE.getFullFolder()+value);
        if (!Files.exists(imgPath,LinkOption.NOFOLLOW_LINKS)){
            Image one = imgRepository.getOne(value);
            try {
                Files.write(imgPath, one.getContent(),StandardOpenOption.WRITE);
            } catch (IOException ex) {
                Logger.getLogger(PathToImgConverter.class.getName()).log(Level.SEVERE, value, ex);
            }
        }
        ExternalResource Res = new ExternalResource(IMAGE.getParentFolder()+value);
        return Res;
    }

    @Override
    public Class<String> getModelType() {
        return String.class;
    }

    @Override
    public Class<Resource> getPresentationType() {
        return Resource.class;
    }

}
