package com.gbyzzz.linkedinjobsbot.controller;

import com.gbyzzz.linkedinjobsbot.entity.UserProfile;

import java.util.Objects;

public enum CommandName {

    START ("/start"),
    EXIT ("/exit"),
    ADD_SEARCH("/add_search"),
    MAIN_MENU("/main_menu"),
    MAKE_SEARCH("/make_search"),
    ADD_KEYWORDS(UserProfile.BotState.ADD_KEYWORDS.name()),
    ADD_LOCATION(UserProfile.BotState.ADD_LOCATION.name()),
    ADD_EXPERIENCE(UserProfile.BotState.ADD_EXPERIENCE.name()),
    ADD_JOB_TYPE(UserProfile.BotState.ADD_JOB_TYPE.name()),
    ADD_WORKPLACE(UserProfile.BotState.ADD_WORKPLACE.name()),
    ADD_FILTER_EXCLUDE(UserProfile.BotState.ADD_FILTER_EXCLUDE.name()),
    ADD_FILTER_INCLUDE(UserProfile.BotState.ADD_FILTER_INCLUDE.name()),
    ADD_FILTER_JOB_TYPE(UserProfile.BotState.ADD_FILTER_JOB_TYPE.name()),
    ADD_FILTERS(UserProfile.BotState.ADD_FILTERS.name()),
    ADD_FILTER_WORKPLACE(UserProfile.BotState.ADD_FILTER_WORKPLACE.name());
    private final String value;

    CommandName(final String value1) {
        this.value = value1;
    }

    static CommandName getValue(final String val) {
        for (CommandName e: CommandName.values()) {
            if (Objects.equals(e.value, val)) {
                return e;
            }
        }
        return null;
    }
}
