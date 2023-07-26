package com.gbyzzz.linkedinjobsbot.service.impl;

import com.gbyzzz.linkedinjobsbot.controller.CommandName;

import java.util.Objects;

public enum Experience {
    INTERNSHIP("1", "Internship", "internshipState"),
    ENTRY_LEVEL("2","Entry level", "entryState"),
    ASSOCIATE("3", "Associate", "associateState"),
    MID_SENIOR_LEVEL("4", "Mid-Senior level", "midSeniorState"),
    DIRECTOR("5", "Director", "directorState"),
    EXECUTIVE("6", "Executive", "executiveState");

    private final String value;
    private final String buttonName;
    private final String stateName;

    Experience(String value, String buttonName, String stateName) {
        this.value = value;
        this.buttonName = buttonName;
        this.stateName = stateName;
    }
    String getValue(){
        return value;
    }
    String getButtonName(){
        return buttonName;
    }
    String getStateName() {
        return stateName;
    }

    public static String getValue(final String stateName) {
        for (Experience e: Experience.values()) {
            if (Objects.equals(e.stateName  , stateName)) {
                return e.value;
            }
        }
        return null;
    }

}
