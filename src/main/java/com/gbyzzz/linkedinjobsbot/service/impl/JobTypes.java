package com.gbyzzz.linkedinjobsbot.service.impl;

import com.gbyzzz.linkedinjobsbot.controller.CommandName;

import java.util.Objects;

public enum JobTypes {
    FULL_TIME("F", 0),
    PART_TIME("P", 1),
    CONTRACT("C", 2),
    TEMPORARY("T", 3),
    INTERNSHIP("I", 4);

    private final String id;
    private final Integer stateId;

    JobTypes(String id, Integer stateId) {
        this.id = id;
        this.stateId = stateId;
    }

    public static String getValue(final Integer stateId) {
        for (JobTypes e: JobTypes.values()) {
            if (Objects.equals(e.stateId, stateId)) {
                return e.id;
            }
        }
        return null;
    }
}
