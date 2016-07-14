/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.ui;

import com.vaadin.data.util.converter.Converter;
import java.util.Locale;
import org.napalmvin.neuro_log_vui.entities.Person;

/**
 *
 * @author LOL
 */
public class PersonToStringConverter<T extends Person> implements Converter<String, T> {

    private final Class<T> type;

    public PersonToStringConverter(Class<T> type) {
        this.type = type;
    }

    @Override
    public String convertToPresentation(T value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        if (value == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer(value.getLastName());
        sb.append(' ').append(value.getFirstName()).
                append(" ").append(value.getMiddleName());
        return sb.toString();
    }

    @Override
    public Class<T> getModelType() {
        return type;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }

    @Override
    public T convertToModel(String value, Class<? extends T> targetType, Locale locale) throws ConversionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
