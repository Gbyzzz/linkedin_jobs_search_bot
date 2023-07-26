package com.gbyzzz.linkedinjobsbot.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class KeywordConverter implements AttributeConverter<String[], String> {
    @Override
    public String convertToDatabaseColumn(String[] attributes) {
        if(attributes.length>0) {
            return String.join(",", attributes);
        }
        return null;
    }

    @Override
    public String[] convertToEntityAttribute(String dbData) {
        return dbData.split(",");
    }
}
