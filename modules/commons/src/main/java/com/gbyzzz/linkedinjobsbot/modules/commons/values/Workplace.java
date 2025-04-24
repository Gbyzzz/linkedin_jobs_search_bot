package com.gbyzzz.linkedinjobsbot.modules.commons.values;

import java.util.Objects;

public enum Workplace {
    ON_SITE(1, "urn:li:fs_workplaceType:1"),
    REMOTE(2, "urn:li:fs_workplaceType:2"),
    HYBRID(3, "urn:li:fs_workplaceType:3");

    private int id;
    private String workplaceType;

    Workplace(int id, String workplaceType) {
        this.id = id;
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
    public static String getName(int id){
        for (Workplace e: Workplace.values()) {
            if (e.id  == id) {
                return e.name().toLowerCase();
            }
        }
        return null;
    }
}
