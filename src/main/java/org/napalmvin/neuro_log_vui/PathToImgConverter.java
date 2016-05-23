/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import java.util.Locale;

public class PathToImgConverter implements Converter<Resource, String> {
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
                return new ExternalResource(value);
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
