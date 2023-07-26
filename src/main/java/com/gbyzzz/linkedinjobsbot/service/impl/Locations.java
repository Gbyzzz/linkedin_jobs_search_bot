package com.gbyzzz.linkedinjobsbot.service.impl;

public enum Locations {
    ISRAEL("101620260"),
    HAIFA("118503349"),
    USA("10364427");

    private final String id;

    Locations(String id) {
        this.id = id;
    }
    String getId(){
        return id;
    }

}
