package com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.converter;

import com.gbyzzz.linkedinjobsbot.modules.commons.values.MessageText;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class KeywordConverter implements AttributeConverter<String[], String> {
    @Override
    public String convertToDatabaseColumn(String[] attributes) {
        if (attributes != null && attributes.length > 0) {
            return String.join(MessageText.COMMA, attributes);
        }
        return null;
    }

    @Override
    public String[] convertToEntityAttribute(String dbData) {
        if (dbData != null && dbData.length() > 0) {
            return dbData.split(MessageText.COMMA);
        }
        return new String[0];
    }
}
