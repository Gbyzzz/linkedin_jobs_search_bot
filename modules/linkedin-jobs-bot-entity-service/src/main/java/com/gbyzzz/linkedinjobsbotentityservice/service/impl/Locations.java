package com.gbyzzz.linkedinjobsbotentityservice.service.impl;

public enum Locations {
    ISRAEL("101620260"),
    HAIFA("118503349"),
    TEL_AVIV("104243116"),
    JERUSALEM("106832012"),
    USA("103644278");

    private final String id;

    Locations(String id) {
        this.id = id;
    }
    public String getId(){
        return id;
    }

}
