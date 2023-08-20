package com.gbyzzz.linkedinjobsbot.entity.converter;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class KeywordConverter implements AttributeConverter<String[], String> {
    @Override
    public String convertToDatabaseColumn(String[] attributes) {
        if(attributes.length>0) {
            return String.join(MessageText.COMMA, attributes);
        }
        return null;
    }

    @Override
    public String[] convertToEntityAttribute(String dbData) {
        return dbData.split(MessageText.COMMA);
    }
}
