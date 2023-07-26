package com.gbyzzz.linkedinjobsbot.service.impl;

import java.util.Objects;

public enum Workplace {
    ON_SITE("urn:li:fs_workplaceType:1"),
    REMOTE("urn:li:fs_workplaceType:2"),
    HYBRID("urn:li:fs_workplaceType:3");

    private String workplaceType;

    Workplace(String workplaceType) {
        this.workplaceType = workplaceType;
    }

    public static String getWorkplace(String workplaceType){
        for (Workplace e: Workplace.values()) {
            if (Objects.equals(e.workplaceType  , workplaceType)) {
                return e.name();
            }
        }
        return null;
    }

}
